package com.kdc.api.registeruser;

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
import com.kdc.api.registeruser.RegisterUserRequestEntity;
import com.kdc.api.registeruser.RegisterUserResponseEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.enums.AuthenticationModeEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;

/**
 * ユーザ登録 API Controller クラス
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class RegisterUserController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterUserController.class);

	@Autowired
	private RegisterUserService registerUserService;

	// ユーザ登録
	@RequestMapping(path = CommonConst.API_BASE_URL
			+ "/register/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RegisterUserResponseEntity registerUser(@RequestBody RegisterUserRequestEntity bean,
			HttpServletResponse response) {

		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId(bean.getUserId());
		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.REGISTER_USER, userInfo, bean.getDeviceInfo()));

		// レスポンス作成
		RegisterUserResponseEntity resEntity = new RegisterUserResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_REGISTER_USER);

		// パラメータチェック
		if (!this.registerUserService.checkParameter(bean, resEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.REGISTER_USER, userInfo, bean.getDeviceInfo(),
					"checkParameter"));
			return resEntity;
		}

		// ユーザ重複チェック
		if (!this.registerUserService.checkUserDuplicated(bean, resEntity)) {
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.REGISTER_USER, userInfo, bean.getDeviceInfo(),
					"checkUserDuplicated"));
			return resEntity;
		}

		switch (AuthenticationModeEnum.valueOf(bean.getMode())) {
		case LOGIN:
		case REGISTER_USER:
			// 招待コード適用
			if (!this.registerUserService.useInvitationCode(bean, resEntity)) {
				// HTTPステータスコードを"Internal Server Error(500)"に設定する。
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
				logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.REGISTER_USER, userInfo, bean.getDeviceInfo(),
						"useInvitationCode"));
				return resEntity;
			}
			break;
		case NEW_GROUP:
			// 新規グループ作成時はチェック不要
			break;
		}

		// ユーザ登録
		if (!registerUserService.registerUser(bean, resEntity)) {
			// HTTPステータスコードを"Internal Server Error(500)"に設定する。
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.REGISTER_USER, userInfo, bean.getDeviceInfo(),
					"registerUser"));
			return resEntity;
		}

		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);

		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.REGISTER_USER, userInfo, bean.getDeviceInfo(),
				response.getStatus(), resEntity.getResultCd()));
		return resEntity;
	}
}
