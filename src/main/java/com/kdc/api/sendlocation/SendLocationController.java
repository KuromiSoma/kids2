package com.kdc.api.sendlocation;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.kdc.api.sendlocation.SendLocationRequestEntity;
import com.kdc.api.sendlocation.SendLocationResponseEntity;
import com.kdc.common.entity.api.UpdateInfoEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;

/**
 * 位置情報送信 API Controller クラス
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class SendLocationController {

	private static final Logger logger = LoggerFactory.getLogger(SendLocationController.class);

	@Autowired
	private SendLocationService sendLocationService;

	// 端末の位置情報送信
	@RequestMapping(path = CommonConst.API_BASE_URL
			+ "/send/location", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendLocationResponseEntity sendLocation(@RequestBody SendLocationRequestEntity bean,
			HttpServletResponse response) {

		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.SEND_LOCATION, bean.getUserInfo(), bean.getDeviceInfo()));

		// レスポンス作成
		SendLocationResponseEntity resEntity = new SendLocationResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_SEND_LOCATION);

		// パラメータチェック
		if (!this.sendLocationService.parameterCheck(bean, resEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.SEND_LOCATION, bean.getUserInfo(), bean.getDeviceInfo(),
					"parameterCheck"));
			this.getRequestParameterLog(bean);
			return resEntity;
		}

		// 位置情報登録
		// ユーザ移動履歴登録
		if (!this.sendLocationService.registerUserLocationRecord(bean)) {
			// HTTPステータスコードを"Internal Server Error(500)"に設定する。
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.SEND_LOCATION, bean.getUserInfo(), bean.getDeviceInfo(),
					"registerUserLocationRecord"));
			this.getRequestParameterLog(bean);
			return resEntity;
		}
		// ユーザ位置登録
		if (!this.sendLocationService.registerUserLocation(bean)) {
			// HTTPステータスコードを"Internal Server Error(500)"に設定する。
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.SEND_LOCATION, bean.getUserInfo(), bean.getDeviceInfo(),
					"registerUserLocation"));
			this.getRequestParameterLog(bean);
			return resEntity;
		}

		// ユーザ端末更新
		if (!this.sendLocationService.registerUserDevice(bean)) {
			// HTTPステータスコードを"Internal Server Error(500)"に設定する。
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.SEND_LOCATION, bean.getUserInfo(), bean.getDeviceInfo(),
					"registerUserDevice"));
			return resEntity;
		}

		// 更新情報の取得
		UpdateInfoEntity updateInfo = this.sendLocationService.getUpdateInfo(bean);

		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		resEntity.setUpdateInfo(updateInfo);

		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.SEND_LOCATION, bean.getUserInfo(), bean.getDeviceInfo(),
				response.getStatus(), resEntity.getResultCd()));
		return resEntity;
	}

	private void getRequestParameterLog(SendLocationRequestEntity bean) {
		logger.info("UserId:" + bean.getUserInfo().getUserId());
		logger.info("UserName:" + bean.getUserInfo().getUserName());
		logger.info("AuthLevel:" + bean.getUserInfo().getAuthLevel());
		logger.info("TelephoneNumber:" + bean.getDeviceInfo().getTelephoneNumber());
		logger.info("DeviceId:" + bean.getDeviceInfo().getDeviceId());
		logger.info("TokenId:" + bean.getDeviceInfo().getTokenId());
		if (bean.getLocationRecordInfo() == null) {
			logger.info("LocationRecordInfo = null");
		} else {
			logger.info("LocationRecordInfo size=" + bean.getLocationRecordInfo().size());
			for (int i = 0; i < bean.getLocationRecordInfo().size(); i++) {
				logger.info("RecordDate:" + bean.getLocationRecordInfo().get(i).getRecordDate());
				logger.info("Latitude:" + bean.getLocationRecordInfo().get(i).getLatitude());
				logger.info("Longitude:" + bean.getLocationRecordInfo().get(i).getLongitude());
				logger.info("BatteryLevel:" + bean.getLocationRecordInfo().get(i).getBatteryLevel());
				logger.info("ReceptionStatus:" + bean.getLocationRecordInfo().get(i).getReceptionStatus());
				logger.info("--------------------");
			}
		}
		return;
	}

}