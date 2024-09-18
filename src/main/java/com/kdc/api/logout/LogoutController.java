package com.kdc.api.logout;

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
import com.kdc.api.logout.LogoutRequestEntity;
import com.kdc.api.logout.LogoutResponseEntity;
import com.kdc.api.logout.LogoutService;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;

/**
 * ログアウト API Controller クラス
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class LogoutController {
	private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);

	@Autowired
	private LogoutService logoutService;

	// ログアウト
	@RequestMapping(path = CommonConst.API_BASE_URL
			+ "/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public LogoutResponseEntity logout(@RequestBody LogoutRequestEntity bean, HttpServletResponse response) {

		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.LOGOUT, bean.getUserInfo(), bean.getDeviceInfo()));

		// レスポンス作成
		LogoutResponseEntity resEntity = new LogoutResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_LOGIN);

		// パラメータチェック
		if (!this.logoutService.parameterCheck(bean, resEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.LOGIN, bean.getUserInfo(), bean.getDeviceInfo(),
					"parameterCheck"));
			return resEntity;
		}

		// 登録状態チェック
		if (!this.logoutService.checkRegistration(bean.getUserInfo(), resEntity)) {
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.LOGIN, bean.getUserInfo(), bean.getDeviceInfo(),
					"checkRegistration"));
			return resEntity;
		}

		// 端末登録
		if (!logoutService.registerDevice(bean, resEntity)) {
			// HTTPステータスコードを"Internal Server Error(500)"に設定する。
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.LOGIN, bean.getUserInfo(), bean.getDeviceInfo(),
					"registerDevice"));
			return resEntity;
		}

		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.LOGOUT, bean.getUserInfo(), bean.getDeviceInfo(),
				response.getStatus(), resEntity.getResultCd()));
		return resEntity;
	}

}
