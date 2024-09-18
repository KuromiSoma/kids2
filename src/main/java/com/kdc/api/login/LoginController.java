package com.kdc.api.login;

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
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;

/**
 * ログイン API Controller クラス
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	// ログイン
	@RequestMapping(path = CommonConst.API_BASE_URL
			+ "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public LoginResponseEntity login(@RequestBody LoginRequestEntity bean, HttpServletResponse response) {

		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.LOGIN, bean.getUserInfo(), bean.getDeviceInfo()));

		// レスポンス作成
		LoginResponseEntity resEntity = new LoginResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_LOGIN);

		// パラメータチェック
		if (!this.loginService.parameterCheck(bean, resEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.LOGIN, bean.getUserInfo(), bean.getDeviceInfo(),
					"parameterCheck"));
			return resEntity;
		}

		// 登録状態チェック
		if (!this.loginService.checkRegistration(bean, resEntity)) {
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.LOGIN, bean.getUserInfo(), bean.getDeviceInfo(),
					"checkRegistration"));
			return resEntity;
		}

		// 端末登録
		if (!loginService.registerDevice(bean, resEntity)) {
			// HTTPステータスコードを"Internal Server Error(500)"に設定する。
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.LOGIN, bean.getUserInfo(), bean.getDeviceInfo(),
					"registerDevice"));
			return resEntity;
		}

		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.LOGIN, bean.getUserInfo(), bean.getDeviceInfo(),
				response.getStatus(), resEntity.getResultCd()));
		return resEntity;
	}
}
