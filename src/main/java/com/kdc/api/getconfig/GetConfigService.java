package com.kdc.api.getconfig;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.api.PlaceNotificationConfigInfoEntity;
import com.kdc.common.entity.api.PlaceNotificationInfoEntity;
import com.kdc.common.entity.api.TransmissionConfigInfoEntity;
import com.kdc.common.entity.api.UserNotificationConfigInfoEntity;
import com.kdc.common.entity.api.UtilityConfigInfoEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;
import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.db.ConfigMasterEntity;
import com.kdc.common.entity.db.PlaceAlertConfigEntity;
import com.kdc.common.entity.db.SendIntervalConfigEntity;
import com.kdc.common.entity.db.UserAlertConfigEntity;
import com.kdc.common.entity.db.UserDeviceEntity;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.CommonService;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.mybatis.mapper.api.GetConfigMapper;
import com.kdc.mybatis.mapper.common.entity.ConfigMasterMapper;
import com.kdc.mybatis.mapper.common.entity.UserMasterMapper;

/**
 * 設定取得 API Service クラス
 *
 */
@Service
public class GetConfigService {

	@Autowired
	private GetConfigMapper getConfigMapper;

	@Autowired
	private UserMasterMapper userMasterMapper;

	@Autowired
	private ConfigMasterMapper configMasterMapper;

	@Autowired
	private CommonService commonService;

	/**
	 * パラメータチェック.
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean parameterCheck(GetConfigRequestEntity reqEntity, ResponseBaseEntity resEntity) {

		// 必須チェック
		// ユーザ・端末 基本情報
		if (!this.commonService.checkBaseParameter(reqEntity, resEntity)) {
			return false;
		}

		// (更新情報)
		if (reqEntity.getUpdateInfo() == null) {
			resEntity.setMessage("updateInfo is Empty");
			return false;
		}

		// ユーザ設定更新フラグ
		if (reqEntity.getUpdateInfo().getUserUpdateFlg() == null) {
			resEntity.setMessage("updateInfo.userUpdateFlg is Empty");
			return false;
		}
		// 送信設定更新フラグ
		if (reqEntity.getUpdateInfo().getTransmissionUpdateFlg() == null) {
			resEntity.setMessage("updateInfo.transmissionUpdateFlg is Empty");
			return false;
		}
		// 汎用設定更新フラグ
		if (reqEntity.getUpdateInfo().getUtilityUpdateFlg() == null) {
			resEntity.setMessage("updateInfo.utilityUpdateFlg is Empty");
			return false;
		}
		// ユーザ通知設定更新フラグ
		if (reqEntity.getUpdateInfo().getUserNotificationUpdateFlg() == null) {
			resEntity.setMessage("updateInfo.userNotificationUpdateFlg is Empty");
			return false;
		}
		// 場所通知設定更新フラグ
		if (reqEntity.getUpdateInfo().getPlaceNotificationUpdateFlg() == null) {
			resEntity.setMessage("updateInfo.placeNotificationUpdateFlg is Empty");
			return false;
		}

		return true;
	}

	/**
	 * 設定取得.
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean setConfig(GetConfigRequestEntity reqEntity, GetConfigResponseEntity resEntity) {

		UserMasterEntity usermaster = this.userMasterMapper.selectByPk(reqEntity.getUserInfo().getUserId(),
				CommonConst.FLG_OFF);

		// ユーザ設定取得
		if (KdcCommonUtils.isFlgOn(reqEntity.getUpdateInfo().getUserUpdateFlg())) {

			UserInfoEntity result1 = new UserInfoEntity();
			result1.setUserId(usermaster.getUserid());
			result1.setPassword(usermaster.getPassword());
			result1.setUserName(usermaster.getUsername());
			result1.setIconId(usermaster.getIconid());
			result1.setGroupId(usermaster.getGroupid());
			result1.setAuthLevel(String.valueOf(usermaster.getAuthlevel()));
			result1.setLineColor(usermaster.getLinecolor());
			result1.setMarkerColor(String.valueOf(usermaster.getMarkercolor()));
			resEntity.setUserInfo(result1);

		}

		// ユーザ通知設定取得
		if (KdcCommonUtils.isFlgOn(reqEntity.getUpdateInfo().getUserNotificationUpdateFlg())) {

			List<UserNotificationConfigInfoEntity> result2 = new ArrayList<>();

			List<UserAlertConfigEntity> conflist = this.getConfigMapper
					.getUserAlertConfigList(usermaster.getAuthlevel(),usermaster.getGroupid());

			if (conflist != null) {
				for (UserAlertConfigEntity conf : conflist) {

					UserNotificationConfigInfoEntity userNotificationConfigInfo = new UserNotificationConfigInfoEntity();

					userNotificationConfigInfo.setAuthLevel(String.valueOf(conf.getAuthlevel()));
					userNotificationConfigInfo
							.setNotificationAuthLevel(String.valueOf(conf.getNotificationauthlevel()));

					userNotificationConfigInfo.setLocationDisplayFlg(String.valueOf(conf.getLocationdisplayflg()));
					userNotificationConfigInfo
							.setMovingDistanceDisplayFlg(String.valueOf(conf.getMovingdistancedisplayflg()));
					userNotificationConfigInfo
							.setBatteryLevelDisplayFlg(String.valueOf(conf.getBatteryleveldisplayflg()));
					userNotificationConfigInfo.setAccessTimeDisplayFlg(String.valueOf(conf.getAccesstimedisplayflg()));
					userNotificationConfigInfo
							.setReceptionLevelDisplayFlg(String.valueOf(conf.getReceptionstatusdisplayflg()));

					userNotificationConfigInfo.setBatterylevelAlertFlg(String.valueOf(conf.getBatterylevelalertflg()));
					userNotificationConfigInfo.setBatteryNotification(String.valueOf(conf.getBatterynotification()));
					userNotificationConfigInfo.setConnectionPopup(String.valueOf(conf.getConnectionpopup()));
					userNotificationConfigInfo.setBatteryAlarmTime(String.valueOf(conf.getBatteryalarmtime()));
					userNotificationConfigInfo.setBatteryVibrationTime(String.valueOf(conf.getBatteryvibrationtime()));
					userNotificationConfigInfo.setBatteryReference(String.valueOf(conf.getBatteryreference()));

					userNotificationConfigInfo.setConnectionAlertFlg(String.valueOf(conf.getConnectionalertflg()));
					userNotificationConfigInfo
							.setConnectionNotification(String.valueOf(conf.getConnectionnotification()));
					userNotificationConfigInfo.setConnectionPopup(String.valueOf(conf.getConnectionpopup()));
					userNotificationConfigInfo.setConnectionAlarmTime(String.valueOf(conf.getConnectionalarmtime()));
					userNotificationConfigInfo
							.setConnectionVibrationTime(String.valueOf(conf.getConnectionvibrationtime()));

					userNotificationConfigInfo.setSosAlertFlg(String.valueOf(conf.getSosalertflg()));
					userNotificationConfigInfo.setSosNotification(String.valueOf(conf.getSosnotification()));
					userNotificationConfigInfo.setSosPopup(String.valueOf(conf.getSospopup()));
					userNotificationConfigInfo.setSosAlarmTime(String.valueOf(conf.getSosalarmtime()));
					userNotificationConfigInfo.setSosVibrationTime(String.valueOf(conf.getSosvibrationtime()));

					result2.add(userNotificationConfigInfo);
				}
				resEntity.setUserNotificationConfigInfo(result2);
			}
		}

		// 場所通知設定取得
		if (KdcCommonUtils.isFlgOn(reqEntity.getUpdateInfo().getPlaceNotificationUpdateFlg())) {

			List<PlaceNotificationConfigInfoEntity> result3 = new ArrayList<>();

			List<PlaceAlertConfigEntity> list1 = this.getConfigMapper
					.getPlaceAlertConfigList(usermaster.getAuthlevel(),usermaster.getGroupid());

			List<PlaceNotificationInfoEntity> placelist = new ArrayList<>();
			PlaceNotificationConfigInfoEntity placeNotificationConfigInfo = new PlaceNotificationConfigInfoEntity();

			if (list1 != null) {
				for (int j = 0; j < list1.size(); j++) {
					if (j == 0) {

						placeNotificationConfigInfo.setAuthLevel(String.valueOf(list1.get(j).getAuthlevel()));
						placeNotificationConfigInfo
								.setNotificationAuthLevel(String.valueOf(list1.get(j).getNotificationauthlevel()));

					} else {

						if (placeNotificationConfigInfo.getAuthLevel() != null && Integer
								.parseInt(placeNotificationConfigInfo.getAuthLevel()) != list1.get(j).getAuthlevel()) {

							result3.add(placeNotificationConfigInfo);

							placelist = new ArrayList<>();

							placeNotificationConfigInfo = new PlaceNotificationConfigInfoEntity();

							placeNotificationConfigInfo.setAuthLevel(String.valueOf(list1.get(j).getAuthlevel()));
							placeNotificationConfigInfo
									.setNotificationAuthLevel(String.valueOf(list1.get(j).getNotificationauthlevel()));

						}

					}
					PlaceNotificationInfoEntity place = new PlaceNotificationInfoEntity();

					place.setPlaceId(String.valueOf(list1.get(j).getPlaceid()));
					place.setPlaceAlertFlg(String.valueOf(list1.get(j).getPlacealertflg()));
					place.setPlaceNotification(String.valueOf(list1.get(j).getPlacenotification()));
					place.setPlacePopup(String.valueOf(list1.get(j).getPlacepopup()));
					place.setPlaceAlarmTime(String.valueOf(list1.get(j).getPlacealarmtime()));
					place.setPlaceVibrationTime(String.valueOf(list1.get(j).getPlacevibrationtime()));

					placelist.add(place);

					placeNotificationConfigInfo.setPlaceNotificationInfo(placelist);
				}

				result3.add(placeNotificationConfigInfo);
				resEntity.setPlaceNotificationConfigInfo(result3);
			}
		}

		// 送信間隔取得
		if (KdcCommonUtils.isFlgOn(reqEntity.getUpdateInfo().getTransmissionUpdateFlg())) {

			List<TransmissionConfigInfoEntity> result4 = new ArrayList<>();

			// 送信間隔は全管理レベル分を取得する
			List<SendIntervalConfigEntity> list = this.getConfigMapper.getSendIntervalConfigList(reqEntity.getUserInfo().getGroupId());

			if (list != null) {
				for (SendIntervalConfigEntity entity : list) {
					TransmissionConfigInfoEntity send = new TransmissionConfigInfoEntity();

					send.setAuthLevel(String.valueOf(entity.getAuthlevel()));
					send.setDayOfWeek(String.valueOf(entity.getDayofweek()));
					send.setConfigNo(String.valueOf(entity.getConfigno()));
					send.setStartTime(KdcCommonUtils.timeToHourMinuteString(entity.getStarttime()));
					send.setEndTime(KdcCommonUtils.timeToHourMinuteString(entity.getEndtime()));
					send.setInterval(String.valueOf(entity.getTransmissioninterval()));

					result4.add(send);

				}

				resEntity.setTransmissionConfigInfo(result4);
			}
		}

		// 汎用設定取得
		if (KdcCommonUtils.isFlgOn(reqEntity.getUpdateInfo().getUtilityUpdateFlg())) {

			List<UtilityConfigInfoEntity> result5 = new ArrayList<>();

			List<ConfigMasterEntity> confmasterlist = this.configMasterMapper.selectAll(reqEntity.getUserInfo().getGroupId(), CommonConst.FLG_OFF, true,
					true);

			if (confmasterlist != null) {
				for (ConfigMasterEntity confmaster : confmasterlist) {

					UtilityConfigInfoEntity util = new UtilityConfigInfoEntity();

					util.setConfigId(String.valueOf(confmaster.getConfigid()));
					util.setConfigCode(String.valueOf(confmaster.getConfigcode()));
					util.setConfigName(confmaster.getConfigname());
					util.setConfigParam1(confmaster.getConfigparam1());
					util.setConfigParam2(confmaster.getConfigparam2());
					util.setConfigParam3(confmaster.getConfigparam3());
					util.setConfigParam4(confmaster.getConfigparam4());
					util.setConfigParam5(confmaster.getConfigparam5());

					result5.add(util);

				}
				resEntity.setUtilityConfigInfo(result5);
			}
		}

		// 設定同期日時更新
		if (KdcCommonUtils.isFlgOn(reqEntity.getUpdateInfo().getUserUpdateFlg())
				|| KdcCommonUtils.isFlgOn(reqEntity.getUpdateInfo().getUserNotificationUpdateFlg())
				|| KdcCommonUtils.isFlgOn(reqEntity.getUpdateInfo().getPlaceNotificationUpdateFlg())
				|| KdcCommonUtils.isFlgOn(reqEntity.getUpdateInfo().getTransmissionUpdateFlg())
				|| KdcCommonUtils.isFlgOn(reqEntity.getUpdateInfo().getUtilityUpdateFlg())) {

			Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();
			UserDeviceEntity devrec = new UserDeviceEntity();
			devrec.setUserid(reqEntity.getUserInfo().getUserId());
			devrec.setUpdateuserid(reqEntity.getUserInfo().getUserId());
			devrec.setUpdatedate(nowTimestamp);
			devrec.setConfigsyncdate(nowTimestamp);
			this.getConfigMapper.updateUserDevice(devrec);

		}

		return true;
	}

}