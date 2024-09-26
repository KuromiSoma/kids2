package com.kdc.web.webauthconfig;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.api.PushNotificationEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;
import com.kdc.common.entity.db.ConfigMasterEntity;
import com.kdc.common.entity.db.GroupInfoEntity;
import com.kdc.common.entity.db.PlaceAlertConfigEntity;
import com.kdc.common.entity.db.PlaceDisplayConfigEntity;
import com.kdc.common.entity.db.PlaceMasterEntityWrapper;
import com.kdc.common.entity.db.SendIntervalConfigEntity;
import com.kdc.common.entity.db.UserAlertConfigEntity;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.enums.AuthLevelEnum;
import com.kdc.common.enums.DayOfWeekEnum;
import com.kdc.common.enums.PushNotificationEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.common.util.PushNotificationCounter;
import com.kdc.common.util.PushNotificationService;
import com.kdc.mybatis.domain.web.webauthconfig.UserInfoDomain;
import com.kdc.mybatis.mapper.common.entity.ConfigMasterMapper;
import com.kdc.mybatis.mapper.common.entity.GroupMasterMapper;
import com.kdc.mybatis.mapper.common.entity.PlaceDisplayConfigMapper;
import com.kdc.mybatis.mapper.common.entity.PlaceMasterMapper;
import com.kdc.mybatis.mapper.common.entity.SendIntervalConfigMapper;
import com.kdc.mybatis.mapper.common.entity.UserMasterMapper;
import com.kdc.mybatis.mapper.common.util.PushNotificationMapper;
import com.kdc.mybatis.mapper.web.WebAuthConfigMapper;
import com.kdc.web.common.WebLoginInfoHolder;

/**
 * 管理レベル設定画面 Service クラス
 * 
 *
 */
@Service
public class WebAuthConfigService {

	private static final Logger logger = LoggerFactory.getLogger(WebAuthConfigService.class);

	@Autowired
	private WebLoginInfoHolder loginInfoHolder;

	@Autowired
	private WebAuthConfigData data;

	@Autowired
	private PlaceMasterMapper placeMasterMapper;

	@Autowired
	private PlaceDisplayConfigMapper placeDisplayConfigMapper;

	@Autowired
	private SendIntervalConfigMapper sendIntervalConfigMapper;

	@Autowired
	private WebAuthConfigMapper webAuthConfigMapper;

	@Autowired
	private UserMasterMapper userMasterMapper;

	@Autowired
	private PushNotificationMapper pushNotificationMapper;

	@Autowired
	private PushNotificationService pushNotificationService;

	@Autowired
	private ConfigMasterMapper configMasterMapper;

	@Autowired
	private GroupMasterMapper groupMasterMapper;

	/**
	 * 初期処理.
	 */
	public void initData(WebAuthConfigForm form) {
		// 場所リスト取得
		List<PlaceMasterEntityWrapper> placeList = this.placeMasterMapper.selectAll(CommonConst.FLG_OFF,form.getCmbAuthGroup(), true);
		this.data.setPlaceList(placeList);
		// 設定テンプレート生成
		this.data.setUserDisp(UserDispConfig.getNewAuthUserConfigList());
		this.data.setUserAlert(UserAlertConfig.getNewAuthUserConfigList());
		this.data.setPlaceDisp(PlaceDispConfig.getNewPlaceDispConfigList(placeList));
		this.data.setPlaceAlert(PlaceAlertConfig.getNewAuthPlaceConfigList(placeList));
		this.data.setTrans(TransmissionConfig.getNewTransmissionConfigList());
		
		//グループ番号取得
		Map<String, String> groupMap = new LinkedHashMap<>();
		Map<String, String> groupNameMap = new LinkedHashMap<>();
		// ユーザマスタからグループＩＤ一覧を取得し、コンボボックスに設定
		List<GroupInfoEntity> groupList = groupMasterMapper.selectAll(CommonConst.FLG_OFF);
		for (int cntCombo = 0; cntCombo < groupList.size(); cntCombo++) {
			groupMap.put(groupList.get(cntCombo).getGroupid(), groupList.get(cntCombo).getGroupname());
			groupNameMap.put(groupList.get(cntCombo).getGroupname(), groupList.get(cntCombo).getGroupname());
		}
		form.setCmbGroup(groupMap);
		form.setGroupName(groupMap.get(form.getCmbAuthGroup()));
		form.setNewGroupName(groupMap.get(form.getCmbAuthGroup()));
		form.setCurrentGroupId("1");


		return;
	}

	/**
	 * 管理設定画面初期処理.
	 * 
	 * @param form
	 * 			管理設定画面フォーム
	 * @param authLevel
	 * 			管理レベル
	 * @param dispBlock
	 * 			表示ブロック
	 */
	public void initAuthConfigForm(WebAuthConfigForm form, AuthLevelEnum authLevel, String dispBlock) {

		form.setCurrentAuthLevel(authLevel.getCodeString());
		form.setCmbAuthLevel(authLevel.getCodeString());
		form.setDispBlock(dispBlock);

		form.setPlaces(this.data.getPlaceList());
		form.setPlaceCount(this.data.getPlaceList().size());

		return;
	}

	/**
	 * 次の画面フォーム用のデータをリロード.
	 * 
	 * @param form
	 * 			管理設定画面フォーム
	 * @param authLevel
	 * 			管理レベル
	 * @param dispBlock
	 * 			表示ブロック
	 */
	public void reloadAuthConfigForm(WebAuthConfigForm form, AuthLevelEnum authLevel, String dispBlock) {

		this.initAuthConfigForm(form, authLevel, dispBlock);

		//グループ番号取得
		Map<String, String> groupMap = new LinkedHashMap<>();
		Map<String, String> groupNameMap = new LinkedHashMap<>();
		// ユーザマスタからグループＩＤ一覧を取得し、コンボボックスに設定
		List<GroupInfoEntity> groupList = groupMasterMapper.selectAll(CommonConst.FLG_OFF);
		for (int cntCombo = 0; cntCombo < groupList.size(); cntCombo++) {
			groupMap.put(groupList.get(cntCombo).getGroupid(), groupList.get(cntCombo).getGroupname());
			groupNameMap.put(groupList.get(cntCombo).getGroupname(), groupList.get(cntCombo).getGroupname());
		}
		form.setCmbGroup(groupMap);
		form.setGroupName(groupMap.get(form.getCmbAuthGroup()));
		form.setNewGroupName(groupMap.get(form.getCmbAuthGroup()));

		//選択画面
		switch (dispBlock) {
		case "1":
			this.initUserForm(form, authLevel);
			break;
		case "2":
			this.initUserForm(form, authLevel);
			break;
		case "3":
			this.initPlaceForm(form, authLevel);
			break;
		case "4":
			this.initTransmissionForm(form, authLevel);
			break;
		case "5":
			this.initLevelUser(form ,authLevel);
			break;
		case "6":
			this.initCommon(form);
			break;
		}

		return;
	}

	/**
	 * ユーザ設定画面初期処理.
	 * 
	 * @param form
	 * 			管理設定画面フォーム
	 * @param authLevel
	 * 			管理レベル
	 */
	public void initUserForm(WebAuthConfigForm form, AuthLevelEnum authLevel) {
		List<UserAlertConfigEntity> list = this.webAuthConfigMapper.selectUserAlertConfig(authLevel.getCode(),form.getCmbAuthGroup());
		for (UserAlertConfigEntity entity : list) {
			AuthLevelEnum item = AuthLevelEnum.valueOf(entity.getAuthlevel());
			// 表示設定
			UserDispConfig dispConf = new UserDispConfig();
			dispConf.setAuthLevel(item.getCodeString());
			dispConf.setAuthLevelLabel(item.getLabel());
			dispConf.setLocationDispFlg(KdcCommonUtils.isFlgOn(entity.getLocationdisplayflg()));
			dispConf.setMovingDistanceDispFlg(KdcCommonUtils.isFlgOn(entity.getMovingdistancedisplayflg()));
			dispConf.setBatteryLevelDispFlg(KdcCommonUtils.isFlgOn(entity.getBatteryleveldisplayflg()));
			dispConf.setAccessTimeDispFlg(KdcCommonUtils.isFlgOn(entity.getAccesstimedisplayflg()));
			dispConf.setReceptionStatusDispFlg(KdcCommonUtils.isFlgOn(entity.getReceptionstatusdisplayflg()));

			// テンプレートの該当箇所にセット
			this.data.getUserDisp().set(entity.getAuthlevel() - 1, dispConf);

			// 通知設定
			UserAlertConfig alertConf = new UserAlertConfig();
			alertConf.setAuthLevel(item.getCodeString());
			alertConf.setAuthLevelLabel(item.getLabel());
			alertConf.setBatteryLevelAlertFlg(KdcCommonUtils.isFlgOn(entity.getBatterylevelalertflg()));
			alertConf.setBatteryNotification(
					KdcCommonUtils.nullToEmpty(String.valueOf(entity.getBatterynotification())));
			alertConf.setBatteryPopup(KdcCommonUtils.isFlgOn(entity.getBatterypopup()));
			// alertConf.setBatteryAlarmTime(KdcCommonUtils.nullToEmpty(String.valueOf(entity.getBatteryalarmtime())));
			alertConf.setBatteryVibrationTime(
					KdcCommonUtils.nullToEmpty(String.valueOf(entity.getBatteryvibrationtime())));
			alertConf.setBatteryReference(KdcCommonUtils.nullToEmpty(String.valueOf(entity.getBatteryreference())));
			alertConf.setConnectionAlertFlg(KdcCommonUtils.isFlgOn(entity.getConnectionalertflg()));
			alertConf.setConnectionNotification(
					KdcCommonUtils.nullToEmpty(String.valueOf(entity.getConnectionnotification())));
			alertConf.setConnectionPopup(KdcCommonUtils.isFlgOn(entity.getConnectionpopup()));
			// alertConf.setConnectionAlarmTime(KdcCommonUtils.nullToEmpty(String.valueOf(entity.getConnectionalarmtime())));
			alertConf.setConnectionVibrationTime(
					KdcCommonUtils.nullToEmpty(String.valueOf(entity.getConnectionvibrationtime())));
			alertConf.setSosAlertFlg(KdcCommonUtils.isFlgOn(entity.getSosalertflg()));
			alertConf.setSosNotification(KdcCommonUtils.nullToEmpty(String.valueOf(entity.getSosnotification())));
			alertConf.setSosPopup(KdcCommonUtils.isFlgOn(entity.getSospopup()));
			// alertConf.setSosAlarmTime(KdcCommonUtils.nullToEmpty(String.valueOf(entity.getSosalarmtime())));
			alertConf.setSosVibrationTime(KdcCommonUtils.nullToEmpty(String.valueOf(entity.getSosvibrationtime())));

			// テンプレートの該当箇所にセット
			this.data.getUserAlert().set(entity.getAuthlevel() - 1, alertConf);
		}
		form.setUserDisp(this.data.getUserDisp());
		form.setUserAlert(this.data.getUserAlert());
	}

	/**
	 * 場所設定画面初期処理.
	 *
	 * @param form
	 * 			管理設定画面フォーム
	 * @param authLevel
	 * 			管理レベル
	 */
	public void initPlaceForm(WebAuthConfigForm form, AuthLevelEnum authLevel) {
		// 表示設定
		List<PlaceDispConfig> dispConfList = this.webAuthConfigMapper.selectPlaceDispConfig(authLevel.getCode(),form.getCmbAuthGroup());
		this.data.setPlaceDisp(dispConfList);
		form.setPlaceDisp(dispConfList);

		// 通知設定
		List<PlaceAlertConfig> alertConfList = new ArrayList<>();
		for (AuthLevelEnum item : AuthLevelEnum.values()) {
			PlaceAlertConfig alertConf = new PlaceAlertConfig();
			alertConf.setAuthLevel(item.getCodeString());
			alertConf.setAuthLevelLabel(item.getLabel());
			List<PlaceAlertConfigSub> subList = this.webAuthConfigMapper.selectPlaceAlertConfig(item.getCode(),authLevel.getCode(),form.getCmbAuthGroup());
			alertConf.setAlert(subList);
			alertConfList.add(alertConf);
		}
		this.data.setPlaceAlert(alertConfList);
		form.setPlaceAlert(alertConfList);
	}

	/**
	 * 更新間隔設定画面初期処理.
	 *
	 * @param form
	 * 			管理設定画面フォーム
	 * @param authLevel
	 * 			管理レベル
	 */
	public void initTransmissionForm(WebAuthConfigForm form, AuthLevelEnum authLevel) {
		List<SendIntervalConfigEntity> list = this.webAuthConfigMapper.selectTransmissionConfig(authLevel.getCode(),form.getCmbAuthGroup());
		for (SendIntervalConfigEntity entity : list) {
			TransmissionConfigSub transSubConf = new TransmissionConfigSub();
			transSubConf.setConfigNo(String.valueOf(entity.getConfigno()));
			transSubConf.setStartTime(
					KdcCommonUtils.nullToEmpty(KdcCommonUtils.timeToHourMinuteStringForDisp(entity.getStarttime())));
			transSubConf.setEndTime(
					KdcCommonUtils.nullToEmpty(KdcCommonUtils.timeToHourMinuteStringForDisp(entity.getEndtime())));
			transSubConf.setTransmissionInterval(
					KdcCommonUtils.nullToEmpty(String.valueOf(entity.getTransmissioninterval())));

			// テンプレートの該当箇所にセット
			this.data.getTrans().get(entity.getDayofweek() - 1).getSub().set(entity.getConfigno() - 1, transSubConf);
		}
		form.setTrans(this.data.getTrans());
	}

	/**
	 * 所属ユーザ画面初期処理.
	 *
	 * @param authLevel
	 * 			管理レベル
	 */
	public void initLevelUser(WebAuthConfigForm form, AuthLevelEnum authLevel) {
		List<AuthUser> thisLevelUserList = new ArrayList<>();
		List<AuthUser> otherLevelUserList = new ArrayList<>();

		List<UserInfoDomain> userList = this.webAuthConfigMapper.selectAllUser(form.getCmbAuthGroup());
		for (UserInfoDomain user : userList) {
			AuthUser authUser = new AuthUser();
			authUser.setUserId(user.getUserid());
			authUser.setUserName(user.getUsername());
			authUser.setAuthLevel(String.valueOf(user.getAuthlevel()));
			authUser.setTelephoneNumber(user.getTelephonenumber());

			if (StringUtils.equals(user.getAuthlevel(), authLevel.getCodeString())) {
				thisLevelUserList.add(authUser);
			} else {
				otherLevelUserList.add(authUser);
			}
		}
		this.data.setThisLevelUser(thisLevelUserList);
		this.data.setOtherLevelUser(otherLevelUserList);
	}

	/**
	 * 初期処理.
	 * 
	 * @param form
	 * 			管理設定画面フォーム
	 */
	public void initCommon(WebAuthConfigForm form) {
		ConfigMasterEntity configEntity = new ConfigMasterEntity();

		// 汎用マスタからSOSカウントダウン時間を取得
		configEntity = this.configMasterMapper.selectCodeList(CommonConst.CONFIG_ID_SOS_COUNTDOWN,form.getCmbAuthGroup()).get(0);
		if (configEntity != null) {
			form.setSosCountdown(configEntity.getConfigparam1());
		} else {
			form.setSosCountdown("");
		}
		// 汎用マスタから端末切断時間を取得
		configEntity = this.configMasterMapper.selectCodeList(CommonConst.CONFIG_ID_DISCONNECT_TIME,form.getCmbAuthGroup()).get(0);
		if (configEntity != null) {
			form.setDisconnectTime(configEntity.getConfigparam1());
		} else {
			form.setDisconnectTime("");
		}
		
	}

	/**
	 * 端末情報画面設定内容登録.
	 * 
	 * @param form
	 * 			管理設定画面フォーム
	 */
	public void registerUserDisp(WebAuthConfigForm form) {
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();

		for (AuthLevelEnum item : AuthLevelEnum.values()) {
			boolean isChanged = false;
			UserDispConfig orgData = this.data.getUserDisp().get(item.getCode() - 1);
			UserDispConfig formData = form.getUserDisp().get(item.getCode() - 1);

			if (orgData.getLocationDispFlg() != formData.getLocationDispFlg()
					|| orgData.getMovingDistanceDispFlg() != formData.getMovingDistanceDispFlg()
					|| orgData.getBatteryLevelDispFlg() != formData.getBatteryLevelDispFlg()
					|| orgData.getAccessTimeDispFlg() != formData.getAccessTimeDispFlg()
					|| orgData.getReceptionStatusDispFlg() != formData.getReceptionStatusDispFlg()) {
				isChanged = true;
			}

			// 変更がある場合のみ登録
			if (isChanged) {
				// ユーザ表示設定
				this.upsertUserDispConfig(formData, item, form.getCurrentAuthLevel(),form.getCurrentGroupId(), nowTimestamp);
			}
		}

		return;
	}

	/**
	 * ユーザ通知設定画面内容登録.
	 * 
	 * @param form
	 * 			管理設定画面フォーム
	 */
	public void registerUserAlert(WebAuthConfigForm form) {
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();

		for (AuthLevelEnum item : AuthLevelEnum.values()) {
			boolean isChanged = false;
			UserAlertConfig orgData = this.data.getUserAlert().get(item.getCode() - 1);
			UserAlertConfig formData = form.getUserAlert().get(item.getCode() - 1);

			// バッテリー通知フラグ
			if (orgData.getBatteryLevelAlertFlg() != formData.getBatteryLevelAlertFlg()) {
				isChanged = true;
			}
			// フラグONの場合のみ項目を判定（OFFだとNULLで戻る）
			if (formData.getBatteryLevelAlertFlg()) {
				// バッテリー通知方法
				if (!KdcCommonUtils.nullSafeEquals(orgData.getBatteryNotification(),
						formData.getBatteryNotification())) {
					isChanged = true;
				}
				// バッテリー通知鳴動時間
				// if
				// (!KdcCommonUtils.nullSafeEquals(orgData.getBatteryAlarmTime(),
				// formData.getBatteryAlarmTime())) {
				// isChanged = true;
				// }
				// バッテリー通知バイブ時間
				if (!KdcCommonUtils.nullSafeEquals(orgData.getBatteryVibrationTime(),
						formData.getBatteryVibrationTime())) {
					isChanged = true;
				}
				// バッテリー通知ポップアップ
				if (orgData.getBatteryPopup() != formData.getBatteryPopup()) {
					isChanged = true;
				}
				// バッテリー通知基準値
				if (!KdcCommonUtils.nullSafeEquals(orgData.getBatteryReference(), formData.getBatteryReference())) {
					isChanged = true;
				}
			}
			// 接続通知フラグ
			if (orgData.getConnectionAlertFlg() != formData.getConnectionAlertFlg()) {
				isChanged = true;
			}
			// フラグONの場合のみ項目を判定（OFFだとNULLで戻る）
			if (formData.getConnectionAlertFlg()) {
				// 接続通知方法
				if (!KdcCommonUtils.nullSafeEquals(orgData.getConnectionNotification(),
						formData.getConnectionNotification())) {
					isChanged = true;
				}
				// 接続通知鳴動時間
				// if
				// (!KdcCommonUtils.nullSafeEquals(orgData.getConnectionAlarmTime(),
				// formData.getConnectionAlarmTime())) {
				// isChanged = true;
				// }
				// 接続通知バイブ時間
				if (!KdcCommonUtils.nullSafeEquals(orgData.getConnectionVibrationTime(),
						formData.getConnectionVibrationTime())) {
					isChanged = true;
				}
				// 接続通知ポップアップ
				if (orgData.getConnectionPopup() != formData.getConnectionPopup()) {
					isChanged = true;
				}
			}
			// SOS通知フラグ
			if (orgData.getSosAlertFlg() != formData.getSosAlertFlg()) {
				isChanged = true;
			}
			// フラグONの場合のみ項目を判定（OFFだとNULLで戻る）
			if (formData.getSosAlertFlg()) {
				// SOS通知方法
				if (!KdcCommonUtils.nullSafeEquals(orgData.getSosNotification(), formData.getSosNotification())) {
					isChanged = true;
				}
				// SOS通知鳴動時間
				// if (!KdcCommonUtils.nullSafeEquals(orgData.getSosAlarmTime(),
				// formData.getSosAlarmTime())) {
				// isChanged = true;
				// }
				// SOS通知バイブ時間
				if (!KdcCommonUtils.nullSafeEquals(orgData.getSosVibrationTime(), formData.getSosVibrationTime())) {
					isChanged = true;
				}
				// SOS通知ポップアップ
				if (orgData.getSosPopup() != formData.getSosPopup()) {
					isChanged = true;
				}
			}

			// 変更がある場合のみ登録
			if (isChanged) {
				// ユーザ通知設定
				this.upsertUserAlertConfig(formData, item, form.getCurrentAuthLevel(),form.getCurrentGroupId(), nowTimestamp);
			}
		}

		return;
	}

	/**
	 * 場所表示設定画面内容登録.
	 * 
	 * @param form
	 * 			管理設定画面フォーム
	 */
	public void registerPlaceDisp(WebAuthConfigForm form) {
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();

		for (int i = 0; i < this.data.getPlaceList().size(); i++) {
			boolean isChanged = false;
			PlaceDispConfig orgData = this.data.getPlaceDisp().get(i);
			PlaceDispConfig formData = form.getPlaceDisp().get(i);

			if (orgData.getPlaceDispFlg() != formData.getPlaceDispFlg()) {
				isChanged = true;
			}

			// 変更がある場合のみ登録
			if (isChanged) {
				// 場所表示設定
				this.upsertPlaceDispConfig(formData, form.getCurrentAuthLevel(), nowTimestamp);
			}
		}

		return;
	}

	/**
	 * 場所通知設定画面内容登録.
	 * 
	 * @param form
	 * 			管理設定画面フォーム
	 */
	public void registerPlaceAlert(WebAuthConfigForm form) {
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();

		for (AuthLevelEnum item : AuthLevelEnum.values()) {
			for (int i = 0; i < this.data.getPlaceList().size(); i++) {
				boolean isChanged = false;
				PlaceAlertConfigSub orgData = this.data.getPlaceAlert().get(item.getCode() - 1).getAlert().get(i);
				PlaceAlertConfigSub formData = form.getPlaceAlert().get(item.getCode() - 1).getAlert().get(i);

				//グループID
				formData.setGroupid(form.getCurrentGroupId());
				
				// 通知フラグ
				if (orgData.getPlaceAlertFlg() != formData.getPlaceAlertFlg()) {
					isChanged = true;
				}
				// フラグONの場合のみ項目を判定（OFFだとNULLで戻る）
				if (formData.getPlaceAlertFlg()) {
					// 通知方法
					if (!KdcCommonUtils.nullSafeEquals(orgData.getPlaceNotification(),
							formData.getPlaceNotification())) {
						isChanged = true;
					}
					// 鳴動時間
					// if
					// (!KdcCommonUtils.nullSafeEquals(orgData.getPlaceAlarmTime(),
					// formData.getPlaceAlarmTime())) {
					// isChanged = true;
					// }
					// バイブ時間
					if (!KdcCommonUtils.nullSafeEquals(orgData.getPlaceVibrationTime(),
							formData.getPlaceVibrationTime())) {
						isChanged = true;
					}
					// ポップアップ
					if (orgData.getPlacePopup() != formData.getPlacePopup()) {
						isChanged = true;
					}
				}

				// 変更がある場合のみ登録
				if (isChanged) {
					// 場所通知設定
					this.upsertPlaceAlertConfig(formData, item, form.getCurrentAuthLevel(), nowTimestamp);
				}
			}
		}

		return;
	}

	/**
	 * 更新間隔設定画面内容登録.
	 * 
	 * @param form
	 * 			管理設定画面フォーム
	 */
	public void registerTransmission(WebAuthConfigForm form) {
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();
		int upsertCount = 0;

		for (DayOfWeekEnum item : DayOfWeekEnum.values()) {
			for (int i = 0; i < CommonConst.SEND_INTERVAL_CONFIG_COUNT; i++) {
				boolean isChanged = false;
				TransmissionConfigSub orgData = this.data.getTrans().get(item.getCode() - 1).getSub().get(i);
				TransmissionConfigSub formData = form.getTrans().get(item.getCode() - 1).getSub().get(i);

				// 開始時間
				if (!KdcCommonUtils.nullSafeEquals(orgData.getStartTime(), formData.getStartTime())) {
					isChanged = true;
				}
				// 終了時間
				if (!KdcCommonUtils.nullSafeEquals(orgData.getEndTime(), formData.getEndTime())) {
					isChanged = true;
				}
				// 送信間隔
				if (!KdcCommonUtils.nullSafeEquals(orgData.getTransmissionInterval(),
						formData.getTransmissionInterval())) {
					isChanged = true;
				}

				// 変更がある場合のみ登録
				if (isChanged) {
					// 送信間隔設定
					this.upsertTransmissionConfig(formData, form.getCurrentAuthLevel(),form.getCurrentGroupId(), item, nowTimestamp);
					upsertCount++;
				}
			}
		}

		if (upsertCount > 0) {
			// 送信間隔変更は即時反映するためプッシュ通知送信
			PushNotificationCounter counter = new PushNotificationCounter();
			this.sendPushNotification(form, counter);
		}

		return;
	}

	/**
	 * 所属ユーザ設定画面内容登録.
	 * 
	 * @param form
	 * 			管理設定画面フォーム
	 */
	public void registerLevelUser(WebAuthConfigForm form) {
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();

		if ( form.getOtherLevelUser() != null ) {
			for (AuthUser user : form.getOtherLevelUser()) {
				if (StringUtils.isNotEmpty(user.getAuthCheck())) {
					this.updateLevelUserConfig(user, form.getCurrentAuthLevel(),form.getCurrentGroupId(), nowTimestamp);
				}
			}
		}

		return;
	}

	/**
	 * 共通設定画面内容登録.
	 * 
	 * @param form
	 * 			管理設定画面フォーム
	 */
	public void registerCommonConfig(WebAuthConfigForm form) {
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();
		ConfigMasterEntity configEntity = new ConfigMasterEntity();
		configEntity.setGroupid(form.getCurrentGroupId());
		
		// SOSカウントダウン秒数
		configEntity.setConfigid(CommonConst.CONFIG_ID_SOS_COUNTDOWN);
		configEntity.setConfigcode(1);
		configEntity.setConfigparam1(form.getSosCountdown());
		configEntity.setUpdateuserid(this.loginInfoHolder.getLoginId());
		configEntity.setUpdatedate(nowTimestamp);

		this.configMasterMapper.update(configEntity);

		configEntity = new ConfigMasterEntity();
		// 端末切断時間
		configEntity.setConfigid(CommonConst.CONFIG_ID_DISCONNECT_TIME);
		configEntity.setConfigcode(1);
		configEntity.setConfigparam1(form.getDisconnectTime());
		configEntity.setUpdateuserid(this.loginInfoHolder.getLoginId());
		configEntity.setUpdatedate(nowTimestamp);

		this.configMasterMapper.update(configEntity);
	}

	/**
	 * グループ名称登録
	 */
	public void registerGroup(WebAuthConfigForm form) {
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();
		GroupInfoEntity groupMasterEntity = new GroupInfoEntity();
		groupMasterEntity.setGroupid(form.getCurrentGroupId());
		groupMasterEntity.setGroupname(form.getNewGroupName());
		groupMasterEntity.setUpdateuserid(this.loginInfoHolder.getLoginId());
		groupMasterEntity.setUpdatedate(nowTimestamp);
		this.groupMasterMapper.update(groupMasterEntity);

	}


	
	/**
	 * ユーザ通知（表示）設定登録.
	 * 
	 * @param userDisp
	 * 			管理レベル別ユーザ表示設定クラス
	 * @param authLevel
	 * 			管理レベル
	 * @param notificationauthlevel
	 * 			通知管理レベル
	 * @param registerdate
	 * 			登録日付
	 */
	private void upsertUserDispConfig(UserDispConfig userDisp, AuthLevelEnum authLevel, String notificationauthlevel,String groupid,
			Timestamp registerdate) {
		UserAlertConfigEntity entity = new UserAlertConfigEntity();

		entity.setGroupid(groupid);
		entity.setAuthlevel(authLevel.getCode());
		entity.setNotificationauthlevel(Integer.parseInt(notificationauthlevel));

		// 表示設定
		entity.setLocationdisplayflg(KdcCommonUtils.valueFlg(userDisp.getLocationDispFlg()));
		entity.setMovingdistancedisplayflg(KdcCommonUtils.valueFlg(userDisp.getMovingDistanceDispFlg()));
		entity.setBatteryleveldisplayflg(KdcCommonUtils.valueFlg(userDisp.getBatteryLevelDispFlg()));
		entity.setAccesstimedisplayflg(KdcCommonUtils.valueFlg(userDisp.getAccessTimeDispFlg()));
		entity.setReceptionstatusdisplayflg(KdcCommonUtils.valueFlg(userDisp.getReceptionStatusDispFlg()));

		entity.setRegisteruserid(this.loginInfoHolder.getLoginId());
		entity.setRegisterdate(registerdate);
		entity.setUpdateuserid(this.loginInfoHolder.getLoginId());
		entity.setUpdatedate(registerdate);

		// 登録
		this.webAuthConfigMapper.upsertUserDisp(entity);
	}

	/**
	 * ユーザ通知設定登録.
	 * 
	 * @param userAlert	
	 * 			管理レベル別ユーザ通知設定クラス
	 * @param authLevel
	 * 			管理レベル
	 * @param notificationauthlevel
	 * 			通知管理レベル
	 * @param registerdate
	 * 			登録日付
	 */
	private void upsertUserAlertConfig(UserAlertConfig userAlert, AuthLevelEnum item,String notificationauthlevel,String groupid,
			Timestamp registerdate) {
		UserAlertConfigEntity entity = new UserAlertConfigEntity();

		entity.setGroupid(groupid);
		entity.setAuthlevel(item.getCode());
		entity.setNotificationauthlevel(Integer.parseInt(notificationauthlevel));

		// 通知設定
		entity.setBatterylevelalertflg(KdcCommonUtils.valueFlg(userAlert.getBatteryLevelAlertFlg()));
		if (userAlert.getBatteryLevelAlertFlg()) {
			// フラグONの場合のみ通知項目を反映（OFFの場合に消さないため）
			entity.setBatterynotification(KdcCommonUtils.nullSafeParseInt(userAlert.getBatteryNotification()));
			entity.setBatterypopup(KdcCommonUtils.valueFlg(userAlert.getBatteryPopup()));
			// entity.setBatteryalarmtime(KdcCommonUtils.nullSafeParseInt(userAlert.getBatteryAlarmTime()));
			entity.setBatteryvibrationtime(KdcCommonUtils.nullSafeParseInt(userAlert.getBatteryVibrationTime()));
			entity.setBatteryreference(KdcCommonUtils.nullSafeParseInt(userAlert.getBatteryReference()));
		}
		entity.setConnectionalertflg(KdcCommonUtils.valueFlg(userAlert.getConnectionAlertFlg()));
		if (userAlert.getConnectionAlertFlg()) {
			// フラグONの場合のみ通知項目を反映（OFFの場合に消さないため）
			entity.setConnectionnotification(KdcCommonUtils.nullSafeParseInt(userAlert.getConnectionNotification()));
			entity.setConnectionpopup(KdcCommonUtils.valueFlg(userAlert.getConnectionPopup()));
			// entity.setConnectionalarmtime(KdcCommonUtils.nullSafeParseInt(userAlert.getConnectionAlarmTime()));
			entity.setConnectionvibrationtime(KdcCommonUtils.nullSafeParseInt(userAlert.getConnectionVibrationTime()));
		}
		entity.setSosalertflg(KdcCommonUtils.valueFlg(userAlert.getSosAlertFlg()));
		if (userAlert.getSosAlertFlg()) {
			// フラグONの場合のみ通知項目を反映（OFFの場合に消さないため）
			entity.setSosnotification(KdcCommonUtils.nullSafeParseInt(userAlert.getSosNotification()));
			entity.setSospopup(KdcCommonUtils.valueFlg(userAlert.getSosPopup()));
			// entity.setSosalarmtime(KdcCommonUtils.nullSafeParseInt(userAlert.getSosAlarmTime()));
			entity.setSosvibrationtime(KdcCommonUtils.nullSafeParseInt(userAlert.getSosVibrationTime()));
		}

		entity.setRegisteruserid(this.loginInfoHolder.getLoginId());
		entity.setRegisterdate(registerdate);
		entity.setUpdateuserid(this.loginInfoHolder.getLoginId());
		entity.setUpdatedate(registerdate);

		// 登録
		this.webAuthConfigMapper.upsertUserAlert(entity);
	}

	/**
	 * 場所表示設定登録.
	 * 
	 * @param placeDisp
	 * 			場所表示設定クラス
	 * @param authLevel
	 * 			管理レベル
	 * @param registerdate
	 * 			登録日付
	 */
	private void upsertPlaceDispConfig(PlaceDispConfig placeDisp, String authLevel, Timestamp registerdate) {
		PlaceDisplayConfigEntity entity = new PlaceDisplayConfigEntity();

		entity.setAuthlevel(Integer.parseInt(authLevel));

		entity.setPlaceid(placeDisp.getPlaceId());
		entity.setPlacedispflg(KdcCommonUtils.valueFlg(placeDisp.getPlaceDispFlg()));

		entity.setRegisteruserid(this.loginInfoHolder.getLoginId());
		entity.setRegisterdate(registerdate);
		entity.setUpdateuserid(this.loginInfoHolder.getLoginId());
		entity.setUpdatedate(registerdate);

		// 登録
		this.placeDisplayConfigMapper.upsert(entity);
	}

	/**
	 * 場所通知設定登録.
	 * 
	 * @param placeAlert
	 * 			場所通知設定クラス
	 * @param authLevel
	 * 			管理レベル
	 * @param notificationauthlevel
	 * 			通知管理レベル
	 * @param registerdate
	 * 			登録日付
	 */
	private void upsertPlaceAlertConfig(PlaceAlertConfigSub placeAlert, AuthLevelEnum authLevel,
			String notificationauthlevel, Timestamp registerdate) {
		PlaceAlertConfigEntity entity = new PlaceAlertConfigEntity();

		entity.setGroupid(placeAlert.getGroupid());
		entity.setAuthlevel(authLevel.getCode());
		entity.setNotificationauthlevel(Integer.parseInt(notificationauthlevel));

		entity.setPlaceid(placeAlert.getPlaceId());
		entity.setPlacealertflg(KdcCommonUtils.valueFlg(placeAlert.getPlaceAlertFlg()));
		if (placeAlert.getPlaceAlertFlg()) {
			// フラグONの場合のみ通知項目を反映（OFFの場合に消さないため）
			entity.setPlacenotification(KdcCommonUtils.nullSafeParseInt(placeAlert.getPlaceNotification()));
			entity.setPlacepopup(KdcCommonUtils.valueFlg(placeAlert.getPlacePopup()));
			// entity.setPlacealarmtime(KdcCommonUtils.nullSafeParseInt(placeAlert.getPlaceAlarmTime()));
			entity.setPlacevibrationtime(KdcCommonUtils.nullSafeParseInt(placeAlert.getPlaceVibrationTime()));
		}

		// ユーザ通知は通知先管理レベルごとの設定
		entity.setRegisteruserid(this.loginInfoHolder.getLoginId());
		entity.setRegisterdate(registerdate);
		entity.setUpdateuserid(this.loginInfoHolder.getLoginId());
		entity.setUpdatedate(registerdate);

		// 登録
		this.webAuthConfigMapper.upsertPlaceAlert(entity);
	}

	/**
	 * 更新間隔設定登録.
	 * 
	 * @param trans
	 * 			送信間隔設定サブクラス
	 * @param authLevel
	 * @param dayOfWeek
	 * @param registerdate
	 */
	private void upsertTransmissionConfig(TransmissionConfigSub trans, String authLevel,String groupid, DayOfWeekEnum dayOfWeek,
			Timestamp registerdate) {
		SendIntervalConfigEntity entity = new SendIntervalConfigEntity();

		entity.setGroupid(groupid);
		entity.setAuthlevel(Integer.parseInt(authLevel));
		entity.setDayofweek(dayOfWeek.getCode());

		entity.setConfigno(Integer.parseInt(trans.getConfigNo()));
		if (StringUtils.isNotEmpty(trans.getStartTime())) {
			entity.setUsingflag(CommonConst.FLG_ON);
		} else {
			entity.setUsingflag(CommonConst.FLG_OFF);
		}
		entity.setStarttime(KdcCommonUtils.hourMinuteStringForDispToTime(trans.getStartTime()));
		entity.setEndtime(KdcCommonUtils.hourMinuteStringForDispToTime(trans.getEndTime()));
		entity.setTransmissioninterval(KdcCommonUtils.nullSafeParseInt(trans.getTransmissionInterval()));

		entity.setRegisteruserid(this.loginInfoHolder.getLoginId());
		entity.setRegisterdate(registerdate);
		entity.setUpdateuserid(this.loginInfoHolder.getLoginId());
		entity.setUpdatedate(registerdate);

		// 登録
		this.sendIntervalConfigMapper.upsert(entity);
	}

	/**
	 * 所属ユーザ設定登録.
	 * 
	 * @param user
	 * 			管理レベル別ユーザクラス
	 * @param authLevel
	 * 			管理レベル
	 * @param registerdate
	 * 			登録日付
	 */
	private void updateLevelUserConfig(AuthUser user, String authLevel,String groupid, Timestamp registerdate) {

		UserMasterEntity entity = new UserMasterEntity();

		entity.setUserid(user.getUserId());
		entity.setAuthlevel(Integer.parseInt(authLevel));
		entity.setGroupid(groupid);
		
		entity.setUpdateuserid(this.loginInfoHolder.getLoginId());
		entity.setUpdatedate(registerdate);

		// 更新のみ
		this.userMasterMapper.update(entity);
	}
	

	/**
	 * 設定更新Push通知.
	 * 
	 * @param form
	 * 			管理設定画面フォーム
	 * @param counter
	 * 			Push通知結果カウントクラス
	 */
	private void sendPushNotification(WebAuthConfigForm form, PushNotificationCounter counter) {

		// 設定変更は全ユーザに通知する
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId("system");
		userInfo.setUserName("system");
		List<PushNotificationEntity> notifyList = this.pushNotificationMapper.selectChangeConfigTokenIdList();
		this.pushNotificationService.setApiKey();
		this.pushNotificationService.sendAlert(ApiIdEnum.valueOf(CommonConst.API_ID_SERVER_PROCESS),
				PushNotificationEnum.CHANGE_CONFIG, userInfo, notifyList, counter, logger);
		logger.info("設定変更Push通知 OK:" + counter.getSendCompleteCnt() + " NG:" + counter.getSendErrorCnt()
				+ " TokenID未設定:" + counter.getSendExceptCnt());
		for (String token : counter.getErrorTokenList()) {
			logger.info("Send Error:" + token);
		}
	}

}