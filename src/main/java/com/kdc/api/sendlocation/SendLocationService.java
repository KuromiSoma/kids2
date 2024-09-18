package com.kdc.api.sendlocation;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.api.LocationRecordInfoEntity;
import com.kdc.common.entity.api.PushNotificationEntity;
import com.kdc.common.entity.api.UpdateInfoEntity;
import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;
import com.kdc.common.entity.db.UserLocationEntityWrapper;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.enums.ConnectionStatusEnum;
import com.kdc.common.enums.PlaceTypeEnum;
import com.kdc.common.enums.PushNotificationEnum;
import com.kdc.common.entity.db.UserAlertRecordEntity;
import com.kdc.common.entity.db.UserDeviceEntity;
import com.kdc.common.entity.db.PlaceMasterEntityWrapper;
import com.kdc.common.entity.db.ConfigMasterEntity;
import com.kdc.common.entity.db.PlaceAlertRecordEntity;
import com.kdc.common.entity.db.UserLocationRecordEntityWrapper;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.CommonService;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.common.util.PushNotificationCounter;
import com.kdc.common.util.PushNotificationService;
import com.kdc.mybatis.mapper.api.SendLocationMapper;
import com.kdc.mybatis.mapper.common.entity.ConfigMasterMapper;
import com.kdc.mybatis.mapper.common.entity.PlaceAlertRecordMapper;
import com.kdc.mybatis.mapper.common.entity.PlaceMasterMapper;
import com.kdc.mybatis.mapper.common.entity.UserAlertRecordMapper;
import com.kdc.mybatis.mapper.common.entity.UserLocationMapper;
import com.kdc.mybatis.mapper.common.entity.UserLocationRecordMapper;
import com.kdc.mybatis.mapper.common.entity.UserMasterMapper;
import com.kdc.mybatis.mapper.common.util.PushNotificationMapper;

/**
 * 位置情報送信 API Service クラス
 *
 */
@Service
public class SendLocationService {

	private static final Logger logger = LoggerFactory.getLogger(SendLocationService.class);

	@Autowired
	private CommonService commonService;

	@Autowired
	private PushNotificationService pushNotificationService;

	@Autowired
	private ConfigMasterMapper configMasterMapper;

	@Autowired
	private PushNotificationMapper pushNotificationMapper;

	@Autowired
	private UserMasterMapper userMasterMapper;

	@Autowired
	private UserLocationMapper userLocationMapper;

	@Autowired
	private UserLocationRecordMapper userLocationRecordMapper;

	@Autowired
	private UserAlertRecordMapper userAlertRecordMapper;

	@Autowired
	private PlaceMasterMapper placeMasterMapper;

	@Autowired
	private PlaceAlertRecordMapper placeAlertRecordMapper;

	@Autowired
	private SendLocationMapper sendLocationMapper;

	/**
	 * パラメータチェック.
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean parameterCheck(SendLocationRequestEntity reqEntity, ResponseBaseEntity resEntity) {

		// ユーザー・端末情報
		if (!this.commonService.checkBaseParameter(reqEntity, resEntity)) {
			return false;
		}

		// 位置履歴情報
		if (reqEntity.getLocationRecordInfo() == null) {
			resEntity.setMessage("locationRecordInfo is Empty");
			return false;
		} else {
			for (LocationRecordInfoEntity entity : reqEntity.getLocationRecordInfo()) {

				// 記録日時
				if (StringUtils.isEmpty(entity.getRecordDate())) {
					resEntity.setMessage("locationRecordInfo.recordDate is Empty");
					return false;
				}
				// 緯度
				if (StringUtils.isEmpty(entity.getLatitude())) {
					resEntity.setMessage("locationRecordInfo.latitude is Empty");
					return false;
				}
				// 経度
				if (StringUtils.isEmpty(entity.getLongitude())) {
					resEntity.setMessage("locationRecordInfo.longitude is Empty");
					return false;
				}
				// バッテリー残量
				if (StringUtils.isEmpty(entity.getBatteryLevel())) {
					resEntity.setMessage("locationRecordInfo.battertLevel is Empty");
					return false;
				}
				// 電波状況
				if (StringUtils.isEmpty(entity.getReceptionStatus())) {
					resEntity.setMessage("locationRecordInfo.receptionStatus is Empty");
					return false;
				}
				// 日付不正チェック
				if (!KdcCommonUtils.checkDateTimeString(entity.getRecordDate())) {
					resEntity.setMessage("locationRecordInfo.recordDate is Wrong");
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * ユーザ移動履歴登録.
	 * @param reqEntity
	 * @return Boolean
	 */
	public Boolean registerUserLocationRecord(SendLocationRequestEntity reqEntity) {

		// user_location_record
		UserLocationRecordEntityWrapper rec = new UserLocationRecordEntityWrapper();
		rec.setUserid(reqEntity.getUserInfo().getUserId());
		rec.setRegisteruserid(reqEntity.getUserInfo().getUserId());
		rec.setRegisterdate(KdcCommonUtils.getNowTimestamp());

		try {
			for (LocationRecordInfoEntity entity : reqEntity.getLocationRecordInfo()) {

				rec.setReceivedate(KdcCommonUtils.dateTimeStringToTimestamp(entity.getRecordDate()));
				rec.setLatitude(entity.getLatitude());
				rec.setLongitude(entity.getLongitude());
				rec.setBatterylevel(Integer.parseInt(entity.getBatteryLevel()));
				rec.setReceptionstatus(Integer.parseInt(entity.getReceptionStatus()));

				this.userLocationRecordMapper.insert(rec);
			}

			return true;
		} catch (Exception e) {
			logger.info(e.toString());
			return false;
		}
	}

	/**
	 * ユーザ位置登録
	 * @param reqEntity
	 * @return Boolean
	 */
	public Boolean registerUserLocation(SendLocationRequestEntity reqEntity) {

		// 最新の位置情報を格納
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();
		UserLocationEntityWrapper locRec = new UserLocationEntityWrapper();
		locRec.setUserid(reqEntity.getUserInfo().getUserId());
		locRec.setLatitude(reqEntity.getLocationRecordInfo().get(0).getLatitude());
		locRec.setLongitude(reqEntity.getLocationRecordInfo().get(0).getLongitude());
		locRec.setBatterylevel(Integer.parseInt(reqEntity.getLocationRecordInfo().get(0).getBatteryLevel()));
		locRec.setReceptionstatus(Integer.parseInt(reqEntity.getLocationRecordInfo().get(0).getReceptionStatus()));
		locRec.setConnectionstatus(ConnectionStatusEnum.CONNECT.getCode());
		locRec.setLastlocationdate(nowTimestamp);
		locRec.setRegisteruserid(reqEntity.getUserInfo().getUserId());
		locRec.setRegisterdate(nowTimestamp);
		locRec.setUpdateuserid(reqEntity.getUserInfo().getUserId());
		locRec.setUpdatedate(nowTimestamp);

		try {
			// 前回の位置情報
			UserLocationEntityWrapper lastLocation = this.userLocationMapper
					.selectByPk(reqEntity.getUserInfo().getUserId(), CommonConst.FLG_OFF);

			if (lastLocation == null) {

				// レコードなし=>登録
				// user_location
				this.userLocationMapper.insert(locRec);

			} else {
				// 移動距離計算
				String distance = KdcCommonUtils.computeDistance(lastLocation.getLatitude(),
						lastLocation.getLongitude(),
						new BigDecimal(reqEntity.getLocationRecordInfo().get(0).getLatitude()),
						new BigDecimal(reqEntity.getLocationRecordInfo().get(0).getLongitude()));

				// ユーザ位置を更新
				// user_location
				locRec.setMovingdistance(Integer.parseInt(distance)); // 移動距離
				this.userLocationMapper.update(locRec);
			}

			UserInfoEntity userInfo = reqEntity.getUserInfo();
			UserMasterEntity user = this.userMasterMapper.selectByPk(userInfo.getUserId(), CommonConst.FLG_OFF);
			if (user == null) {
				return false;
			}
			userInfo.setGroupId(user.getGroupid());
			userInfo.setAuthLevel(String.valueOf(user.getAuthlevel()));
			userInfo.setUserName(user.getUsername());

			// 設定マスタからPush通知再送信待機時間を取得
			List<ConfigMasterEntity> configList = this.configMasterMapper
					.selectCodeList(CommonConst.CONFIG_ID_PUSH_NOTIFICATION_INTERVAL_TIME,user.getGroupid());
			int intervalMinutes = (configList != null) ? Integer.parseInt(configList.get(0).getConfigparam1()) : 0;
			boolean batteryNotificationOneTime = (configList != null)
					? KdcCommonUtils.isFlgOn(configList.get(0).getConfigparam2()) : false;
			boolean placeNotificationOneTime = (configList != null)
					? KdcCommonUtils.isFlgOn(configList.get(0).getConfigparam4()) : false;

			this.pushNotificationService.setApiKey();

			// バッテリー残量Push通知
			this.sendBatteryAlert(userInfo, reqEntity.getLocationRecordInfo().get(0), nowTimestamp, intervalMinutes,
					batteryNotificationOneTime);

			// 場所Push通知
			this.sendPlaceAlert(userInfo, reqEntity.getLocationRecordInfo().get(0), nowTimestamp, intervalMinutes,
					lastLocation, placeNotificationOneTime);

			return true;
		} catch (Exception e) {
			logger.info(e.toString());
			return false;
		}
	}

	/**
	 * バッテリー残量通知.
	 * @param userInfo
	 * @param locationInfo
	 * @param locationTimestamp
	 * @param intervalMinutes
	 * @param batteryNotificationOneTime
	 */
	private void sendBatteryAlert(UserInfoEntity userInfo, LocationRecordInfoEntity locationInfo,
			Timestamp locationTimestamp, int intervalMinutes, boolean batteryNotificationOneTime) {
		PushNotificationCounter counter = new PushNotificationCounter();
		boolean sendNotify = false;

		UserAlertRecordEntity userAlertRecord = this.userAlertRecordMapper.selectByPk(userInfo.getUserId(),
				CommonConst.FLG_OFF);
		if (userAlertRecord == null || userAlertRecord.getLastbatteryalertdate() == null) {
			// 通知記録が無い＝初回の通知は必ず送信
			sendNotify = true;
		} else if (batteryNotificationOneTime
				&& StringUtils.equals(KdcCommonUtils.timestampToDateString(locationTimestamp),
						KdcCommonUtils.timestampToDateString(userAlertRecord.getLastbatteryalertdate()))) {
			// 1日1回しか通知しない設定
			sendNotify = false;
		} else if (locationTimestamp.after(
				KdcCommonUtils.timestampPlusTime(userAlertRecord.getLastbatteryalertdate(), 0, intervalMinutes, 0))) {
			// 前回の通知時間から、再通知待機時間（分）が経過していれば通知する
			sendNotify = true;
		}

		if (sendNotify) {
			List<PushNotificationEntity> notifyList = this.pushNotificationMapper.selectBatteryAlertTokenIdList(
					userInfo.getUserId(), Integer.parseInt(locationInfo.getBatteryLevel()));
			if (this.pushNotificationService.sendAlert(ApiIdEnum.valueOf(CommonConst.API_ID_SEND_LOCATION),
					PushNotificationEnum.BATTERY, userInfo, notifyList, counter, logger)) {
				// 通知時間をDBに記録
				this.pushNotificationService.upsertUserAlertRecord(PushNotificationEnum.BATTERY, userInfo.getUserId());
			}
		}
		logger.info("バッテリー残量Push通知 OK:" + counter.getSendCompleteCnt() + " NG:" + counter.getSendErrorCnt()
				+ " TokenID未設定:" + counter.getSendExceptCnt());
		for (String token : counter.getErrorTokenList()) {
			logger.info("Send Error:" + token);
		}
	}

	/**
	 * 場所通知.
	 * @param userInfo
	 * @param locationInfo
	 * @param locationTimestamp
	 * @param intervalMinutes
	 * @param lastLocation
	 * @param placeNotificationOneTime
	 */
	private void sendPlaceAlert(UserInfoEntity userInfo, LocationRecordInfoEntity locationInfo,
			Timestamp locationTimestamp, int intervalMinutes, UserLocationEntityWrapper lastLocation,
			boolean placeNotificationOneTime) {
		PushNotificationCounter inCounter = new PushNotificationCounter();
		PushNotificationCounter outCounter = new PushNotificationCounter();
		boolean sendNotify = false;

		List<PlaceMasterEntityWrapper> placeList = this.placeMasterMapper.selectAll(CommonConst.FLG_OFF,userInfo.getGroupId(), true);
		for (PlaceMasterEntityWrapper place : placeList) {
			PlaceAlertRecordEntity placeAlertRecord = this.placeAlertRecordMapper.selectByPk(userInfo.getUserId(),
					place.getPlaceid(), CommonConst.FLG_OFF);
			// 今回と前回の位置情報を重なり判定
			boolean nowLocationInPlace = KdcCommonUtils.userPlaceEngaged(new BigDecimal(locationInfo.getLatitude()),
					new BigDecimal(locationInfo.getLongitude()), place);
			boolean lastLocationInPlace = (lastLocation != null)
					? KdcCommonUtils.userPlaceEngaged(lastLocation.getLatitude(), lastLocation.getLongitude(), place)
					: false;
			PushNotificationEnum pushItem = null;
			if (nowLocationInPlace) {
				// 場所の内側にいる場合
				if (placeAlertRecord == null || placeAlertRecord.getLastplaceinalertdate() == null) {
					// 通知記録が無い＝初回の通知は必ず送信
					sendNotify = true;
				} else if (placeNotificationOneTime
						&& StringUtils.equals(KdcCommonUtils.timestampToDateString(locationTimestamp),
								KdcCommonUtils.timestampToDateString(placeAlertRecord.getLastplaceinalertdate()))) {
					// 1日1回しか通知しない設定
					sendNotify = false;
				} else if (locationTimestamp.after(KdcCommonUtils
						.timestampPlusTime(placeAlertRecord.getLastplaceinalertdate(), 0, intervalMinutes, 0))) {
					// 前回の通知時間から、再通知待機時間（分）が経過していれば通知する
					sendNotify = true;
				}

				if (sendNotify) {
					switch (PlaceTypeEnum.valueOf(place.getPlacetypeflg())) {
					case NORMAL:
						pushItem = PushNotificationEnum.PLACE_NORMAL_IN;
						break;
					case DANGER:
						pushItem = PushNotificationEnum.PLACE_DANGER_IN;
						break;
					}
				}
			} else if (!nowLocationInPlace && lastLocationInPlace) {
				// 場所の内側から外側に移動した場合
				if (placeAlertRecord == null || placeAlertRecord.getLastplaceoutalertdate() == null) {
					// 通知記録が無い＝初回の通知は必ず送信
					sendNotify = true;
				} else if (placeNotificationOneTime
						&& StringUtils.equals(KdcCommonUtils.timestampToDateString(locationTimestamp),
								KdcCommonUtils.timestampToDateString(placeAlertRecord.getLastplaceoutalertdate()))) {
					// 1日1回しか通知しない設定
					sendNotify = false;
				} else if (locationTimestamp.after(KdcCommonUtils
						.timestampPlusTime(placeAlertRecord.getLastplaceoutalertdate(), 0, intervalMinutes, 0))) {
					// 前回の通知時間から、再通知待機時間（分）が経過していれば通知する
					sendNotify = true;
				}

				if (sendNotify) {
					switch (PlaceTypeEnum.valueOf(place.getPlacetypeflg())) {
					case NORMAL:
						pushItem = PushNotificationEnum.PLACE_NORMAL_OUT;
						break;
					case DANGER:
						pushItem = PushNotificationEnum.PLACE_DANGER_OUT;
						break;
					}
				}
			}
			if (pushItem != null) {
				if (this.sendPlaceAlertMain(pushItem, userInfo, place, inCounter, outCounter)) {
					// 通知時間をDBに記録
					this.pushNotificationService.upsertPlaceAlertRecord(pushItem, userInfo.getUserId(),
							place.getPlaceid());
				}
			}
		}
		logger.info("場所進入Push通知 OK:" + inCounter.getSendCompleteCnt() + " NG:" + inCounter.getSendErrorCnt()
				+ " TokenID未設定:" + inCounter.getSendExceptCnt());
		for (String token : inCounter.getErrorTokenList()) {
			logger.info("Send Error:" + token);
		}
		logger.info("場所退出Push通知 OK:" + outCounter.getSendCompleteCnt() + " NG:" + outCounter.getSendErrorCnt()
				+ " TokenID未設定:" + outCounter.getSendExceptCnt());
		for (String token : outCounter.getErrorTokenList()) {
			logger.info("Send Error:" + token);
		}
	}

	/**
	 * 場所通知メイン.
	 * @param pushItem
	 * @param userInfo
	 * @param place
	 * @param inCounter
	 * @param outCounter
	 * @return Boolean
	 */
	private Boolean sendPlaceAlertMain(PushNotificationEnum pushItem, UserInfoEntity userInfo,
			PlaceMasterEntityWrapper place, PushNotificationCounter inCounter, PushNotificationCounter outCounter) {
		List<PushNotificationEntity> notifyList = this.pushNotificationMapper
				.selectPlaceAlertTokenIdList(userInfo.getUserId(), place.getPlaceid());
		Boolean inFlg;
		switch (pushItem) {
		case PLACE_NORMAL_IN:
		case PLACE_DANGER_IN:
			inFlg = true;
			break;
		case PLACE_NORMAL_OUT:
		case PLACE_DANGER_OUT:
			inFlg = false;
			break;
		default:
			return false;
		}
		if (!this.pushNotificationService.sendAlert(ApiIdEnum.valueOf(CommonConst.API_ID_SEND_LOCATION), pushItem,
				userInfo, place, notifyList, inFlg ? inCounter : outCounter, logger)) {
			return false;
		}
		return true;
	}

	/**
	 * ユーザ端末更新
	 * @param reqEntity
	 * @return Boolean
	 */
	public Boolean registerUserDevice(SendLocationRequestEntity reqEntity) {

		UserDeviceEntity devRec = new UserDeviceEntity();

		devRec.setUserid(reqEntity.getUserInfo().getUserId());
		devRec.setUpdateuserid(reqEntity.getUserInfo().getUserId());
		devRec.setUpdatedate(KdcCommonUtils.getNowTimestamp());
		devRec.setTokenid(reqEntity.getDeviceInfo().getTokenId());

		try {
			this.sendLocationMapper.updateUserDevice(devRec);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 更新情報の取得. レスポンス情報の作成.
	 * @param reqEntity
	 * @return UpdateInfoEntity
	 */
	public UpdateInfoEntity getUpdateInfo(SendLocationRequestEntity reqEntity) {

		UpdateInfoEntity entity = new UpdateInfoEntity();

		// 設定同期日
		// user_device設定同期日時
		Timestamp configSyncDate = this.sendLocationMapper.selectConfigSyncDate(reqEntity.getUserInfo().getUserId());

		// 各設定更新日
		// user_master更新日
		Timestamp updateDateUserMaster = this.sendLocationMapper
				.selectUpdateDateUserMaster(reqEntity.getUserInfo().getUserId());
		// user_notification更新日max
		Timestamp maxUpdUserNotification = this.sendLocationMapper
				.selectMaxUpdUserAlertConfig(reqEntity.getUserInfo().getUserId());
		// send_interval更新日max
		Timestamp maxUpdSendIntervalConfig = this.sendLocationMapper
				.selectMaxUpdSendIntervalConfig(reqEntity.getUserInfo().getUserId());
		// user_place_notification更新日max
		Timestamp maxUpdPlaceNotification = sendLocationMapper
				.selectMaxUpdPlaceAlertConfig(reqEntity.getUserInfo().getUserId());
		// config_master更新日max
		Timestamp maxUpdConfigMaster = this.sendLocationMapper.selectMaxUpdConfigMaster(reqEntity.getUserInfo().getGroupId());

		if (configSyncDate != null) {
			if (updateDateUserMaster != null && updateDateUserMaster.compareTo(configSyncDate) >= 0) {
				entity.setUserUpdateFlg(CommonConst.FLG_ON);
			} else {
				entity.setUserUpdateFlg(CommonConst.FLG_OFF);
			}
			if (maxUpdSendIntervalConfig != null && maxUpdSendIntervalConfig.compareTo(configSyncDate) >= 0) {
				entity.setTransmissionUpdateFlg(CommonConst.FLG_ON);
			} else {
				entity.setTransmissionUpdateFlg(CommonConst.FLG_OFF);
			}
			if (maxUpdConfigMaster != null && maxUpdConfigMaster.compareTo(configSyncDate) >= 0) {
				entity.setUtilityUpdateFlg(CommonConst.FLG_ON);
			} else {
				entity.setUtilityUpdateFlg(CommonConst.FLG_OFF);
			}
			if (maxUpdUserNotification != null && maxUpdUserNotification.compareTo(configSyncDate) >= 0) {
				entity.setUserNotificationUpdateFlg(CommonConst.FLG_ON);
			} else {
				entity.setUserNotificationUpdateFlg(CommonConst.FLG_OFF);
			}
			if (maxUpdPlaceNotification != null && maxUpdPlaceNotification.compareTo(configSyncDate) >= 0) {
				entity.setPlaceNotificationUpdateFlg(CommonConst.FLG_ON);
			} else {
				entity.setPlaceNotificationUpdateFlg(CommonConst.FLG_OFF);
			}
		} else {
			entity.setUserUpdateFlg(CommonConst.FLG_ON);
			entity.setTransmissionUpdateFlg(CommonConst.FLG_ON);
			entity.setUtilityUpdateFlg(CommonConst.FLG_ON);
			entity.setUserNotificationUpdateFlg(CommonConst.FLG_ON);
			entity.setPlaceNotificationUpdateFlg(CommonConst.FLG_ON);
		}

		return entity;
	}

}
