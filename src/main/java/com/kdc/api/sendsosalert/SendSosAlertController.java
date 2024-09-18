package com.kdc.api.sendsosalert;

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
import com.kdc.api.sendsosalert.SendSosAlertRequestEntity;
import com.kdc.api.sendsosalert.SendSosAlertResponseEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;
import groovy.util.logging.Slf4j;

/**
 * SOS送信 API Controller クラス
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SendSosAlertController {

	private static final Logger logger = LoggerFactory.getLogger(SendSosAlertController.class);

	@Autowired
	private SendSosAlertService sendSosAlertService;

	// SOS送信
	@RequestMapping(path = CommonConst.API_BASE_URL
			+ "/send/sosalert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendSosAlertResponseEntity sendSosAlert(@RequestBody SendSosAlertRequestEntity requestEntity,
			HttpServletResponse response) {

		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.SEND_SOS_ALERT, requestEntity.getUserInfo(),
				requestEntity.getDeviceInfo()));

		// レスポンス作成
		SendSosAlertResponseEntity responseEntity = new SendSosAlertResponseEntity();
		responseEntity.setApiId(CommonConst.API_ID_SEND_SOS_ALERT);

		// パラメータチェック
		if (!this.sendSosAlertService.parameterCheck(requestEntity, responseEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			responseEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.SEND_SOS_ALERT, requestEntity.getUserInfo(),
					requestEntity.getDeviceInfo(), "parameterCheck"));
			return responseEntity;
		}

		// Push通知
		if (!this.sendSosAlertService.sendSosAlert(requestEntity.getUserInfo(), responseEntity)) {
			// HTTPステータスコードを"Internal Server Error(500)"に設定する。
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			responseEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.SEND_SOS_ALERT, requestEntity.getUserInfo(),
					requestEntity.getDeviceInfo(), "sendSosAlert"));
			return responseEntity;
		}

		// レスポンス情報作成
		responseEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.SEND_SOS_ALERT, requestEntity.getUserInfo(),
				requestEntity.getDeviceInfo(), response.getStatus(), responseEntity.getResultCd()));
		return responseEntity;
	}
}
