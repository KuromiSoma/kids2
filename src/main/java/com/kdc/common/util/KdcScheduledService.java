package com.kdc.common.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.kdc.common.entity.KdcAlertEntity;
import com.kdc.common.entity.api.PushNotificationEntity;
import com.kdc.common.entity.db.ConfigMasterEntity;
import com.kdc.common.entity.db.GroupInfoEntity;
import com.kdc.common.entity.db.UserAlertRecordEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.enums.AuthLevelEnum;
import com.kdc.common.enums.PushNotificationEnum;
import com.kdc.mybatis.mapper.common.entity.ConfigMasterMapper;
import com.kdc.mybatis.mapper.common.entity.GroupMasterMapper;
import com.kdc.mybatis.mapper.common.entity.UserAlertRecordMapper;
import com.kdc.mybatis.mapper.common.util.KdcScheduledMapper;
import com.kdc.mybatis.mapper.common.util.PushNotificationMapper;

/**
 * キッズコントロール 常駐タスク処理.
 */
@Controller
public class KdcScheduledService {

	private static final Logger logger = LoggerFactory.getLogger(KdcScheduledService.class);

	@Autowired
	private ConfigMasterMapper configMasterMapper;

	@Autowired
	private PushNotificationService pushNotificationService;

	@Autowired
	private PushNotificationMapper pushNotificationMapper;

	@Autowired
	private KdcScheduledMapper kdcScheduledMapper;

	@Autowired
	private UserAlertRecordMapper userAlertRecordMapper;
	
	@Autowired
	private GroupMasterMapper groupMasterMapper;

	/**
	 * Push通知処理実行
	 */
	public void doTask() {
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowTimestamp);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

		//グループ番号ごとに判定、通知
		List<GroupInfoEntity> groupList = groupMasterMapper.selectAll(CommonConst.FLG_OFF);
		for ( int i = 0; i < groupList.size(); i++) {
			String groupid = groupList.get(i).getGroupid();
			// 送信間隔設定の対象時間外は何もしない
			if (this.kdcScheduledMapper.searchUsingSendInterval(dayOfWeek, nowTimestamp,groupid) > 0) {

				// 接続切断判定
				this.kdcScheduledMapper.updateDisconnect(dayOfWeek, nowTimestamp, "system", groupid);

				// 接続切断通知対象トークンIDの管理レベル別リスト
				Map<AuthLevelEnum, List<PushNotificationEntity>> notifyMap = new LinkedHashMap<>();
				for (AuthLevelEnum item : AuthLevelEnum.values()) {
					notifyMap.put(item,
							this.pushNotificationMapper.selectConnectionAlertTokenIdListByAuthLevel(item.getCode(),groupid));
				}

				// 設定マスタからパラメータ取得
				List<ConfigMasterEntity> configList = new ArrayList<>();
				// 切断判定の基準時間
				configList = this.configMasterMapper.selectCodeList(CommonConst.CONFIG_ID_DISCONNECT_TIME,groupid);
				int disconnectTime = (configList != null) ? Integer.parseInt(configList.get(0).getConfigparam1()) : 0;
				// Push通知再送信待機時間
				configList = this.configMasterMapper.selectCodeList(CommonConst.CONFIG_ID_PUSH_NOTIFICATION_INTERVAL_TIME,groupid);
				int intervalTime = (configList != null)
						? Integer.parseInt(KdcCommonUtils.nullToZero(configList.get(0).getConfigparam1())) : 0;
				boolean connectionNotificationOneTime = (configList != null)
						? KdcCommonUtils.isFlgOn(configList.get(0).getConfigparam3()) : false;

				this.pushNotificationService.setApiKey();

				// 接続切断Push通知
				this.sendDisconnectionAlert(notifyMap, dayOfWeek, nowTimestamp, disconnectTime, intervalTime,
						connectionNotificationOneTime);
				// 接続回復Push通知
				this.sendReconnectionAlert(notifyMap, dayOfWeek, nowTimestamp);
			}
		}
	}

	/**
	 * 接続切断通知.
	 * 
	 * @param notifyMap
	 * 				通知対象トークンIDの管理レベル別リスト
	 * @param dayOfWeek
	 * 				曜日区分
	 * @param nowTimestamp
	 * 				現在日時
	 * @param disconnectTime
	 * 				切断時間
	 * @param intervalTime
	 * 				再送信待機時間
	 * @param connectionNotificationOneTime
	 * 				再接続判定結果
	 */
	private void sendDisconnectionAlert(Map<AuthLevelEnum, List<PushNotificationEntity>> notifyMap, int dayOfWeek,
			Timestamp nowTimestamp, int disconnectTime, int intervalTime,
			 boolean connectionNotificationOneTime) {
		PushNotificationCounter counter = new PushNotificationCounter();
		boolean sendNotify = false;

		List<KdcAlertEntity> alertList = this.kdcScheduledMapper.searchUserOnDisconnectionAlert(dayOfWeek, nowTimestamp,
				disconnectTime);
		for (KdcAlertEntity alertEntity : alertList) {
			UserAlertRecordEntity userAlertRecord = this.userAlertRecordMapper.selectByPk(alertEntity.getUserid(),
					CommonConst.FLG_OFF);
			if (userAlertRecord == null || userAlertRecord.getLastdisconnectionalertdate() == null) {
				// 通知記録が無い＝初回の通知は必ず送信
				sendNotify = true;
			} else if (connectionNotificationOneTime
					&& StringUtils.equals(KdcCommonUtils.timestampToDateString(nowTimestamp),
							KdcCommonUtils.timestampToDateString(userAlertRecord.getLastdisconnectionalertdate()))) {
				// 1日1回しか通知しない設定
				sendNotify = false;
			} else if (nowTimestamp.after(KdcCommonUtils
					.timestampPlusTime(userAlertRecord.getLastdisconnectionalertdate(), 0, intervalTime, 0))) {
				// 前回の通知時間から、再通知待機時間（分）が経過していれば通知する
				sendNotify = true;
			}

			if (sendNotify) {
				if (this.sendAlert(PushNotificationEnum.DISCONNECT, alertEntity, notifyMap, counter)) {
					// 通知時間をDBに記録
					this.pushNotificationService.upsertUserAlertRecord(PushNotificationEnum.DISCONNECT,
							alertEntity.getUserid());
				}
			}
		}
		logger.info("接続切断Push通知 OK:" + counter.getSendCompleteCnt() + " NG:" + counter.getSendErrorCnt());
		for (String token : counter.getErrorTokenList()) {
			logger.info("Send Error:" + token);
		}
	}

	/**
	 * 接続回復通知.
	 * 
	 * @param notifyMap
	 * 				通知対象トークンIDの管理レベル別リスト
	 * @param dayOfWeek
	 *				曜日区分 
	 * @param nowTimestamp
	 * 				現在日時
	 */
	private void sendReconnectionAlert(Map<AuthLevelEnum, List<PushNotificationEntity>> notifyMap, int dayOfWeek,
			Timestamp nowTimestamp) {
		PushNotificationCounter counter = new PushNotificationCounter();

		List<KdcAlertEntity> alertList = this.kdcScheduledMapper.searchUserOnReconnectionAlert(dayOfWeek, nowTimestamp);
		for (KdcAlertEntity alertEntity : alertList) {
			// 回復通知は再通知待機時間によらず送信する
			if (this.sendAlert(PushNotificationEnum.RECONNECT, alertEntity, notifyMap, counter)) {
				// 通知時間をDBに記録
				this.pushNotificationService.upsertUserAlertRecord(PushNotificationEnum.RECONNECT,
						alertEntity.getUserid());
			}
		}
		logger.info("接続回復Push通知 OK:" + counter.getSendCompleteCnt() + " NG:" + counter.getSendErrorCnt());
		for (String token : counter.getErrorTokenList()) {
			logger.info("Send Error:" + token);
		}
	}

	/**
	 * 通知対象トークンIDに対してPush通知.
	 * 
	 * @param pushItem
	 *				Push通知種別Enum
	 * @param alertEntity
	 * 				通知クラス
	 * @param notifyMap
	 * 				通知対象トークンIDの管理レベル別リスト
	 * @param counter
	 * 				Push通知結果カウントクラス
	 * @return
	 */
	private Boolean sendAlert(PushNotificationEnum pushItem, KdcAlertEntity alertEntity,
			Map<AuthLevelEnum, List<PushNotificationEntity>> notifyMap, PushNotificationCounter counter) {
		boolean completeFlg = false;
		Integer sendCompleteCnt = 0;
		Integer sendErrorCnt = 0;
		Integer sendExceptCnt = 0;

		for (PushNotificationEntity notify : notifyMap.get(AuthLevelEnum.valueOf(alertEntity.getAuthlevel()))) {
			if (StringUtils.isEmpty(notify.getTokenid())) {
				sendExceptCnt++;
				continue;
			}
			// Push通知作成＆送信
			if (!this.pushNotificationService.doPush(ApiIdEnum.SERVER_PROCESS, pushItem, alertEntity.getUserid(),
					alertEntity.getUsername(), false, null, null, notify, logger)) {
				counter.getErrorTokenList().add(notify.getTokenid());
				sendErrorCnt++;
				continue;
			}
			sendCompleteCnt++;
			completeFlg = true;
		}
		counter.setSendCompleteCnt(sendCompleteCnt);
		counter.setSendErrorCnt(sendErrorCnt);
		counter.setSendExceptCnt(sendExceptCnt);
		// 1件でも正常送信できていれば送信済みと判定する
		if (completeFlg) {
			return true;
		} else {
			return false;
		}
	}

}
