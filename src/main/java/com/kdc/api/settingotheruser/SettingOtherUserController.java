package com.kdc.api.settingotheruser;

import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.kdc.api.settinguser.SettingUserRequestEntity;
import com.kdc.api.settinguser.SettingUserResponseEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;

/**
 * 他ユーザ情報変更 API Controller クラス
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class SettingOtherUserController {

	private static final Logger logger = LoggerFactory.getLogger(SettingOtherUserController.class);

	@Autowired
	private SettingOtherUserService settingOtherUserService;

	// 他ユーザ情報変更
	@RequestMapping(path = CommonConst.API_BASE_URL
			+ "/admin/setting/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SettingUserResponseEntity sendOtherUser(@RequestBody SettingUserRequestEntity requestEntity,
			HttpServletResponse response) {

		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.ADMIN_SETTING_USER, requestEntity.getUserInfo(),
				requestEntity.getDeviceInfo()));

		SettingUserResponseEntity responseEntity = new SettingUserResponseEntity();
		responseEntity.setApiId(CommonConst.API_ID_ADMIN_SETTING_USER);

		// パラメータチェック
		if (!this.settingOtherUserService.parameterCheck(requestEntity, responseEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			responseEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.ADMIN_SETTING_USER, requestEntity.getUserInfo(),
					requestEntity.getDeviceInfo(), "parameterCheck"));
			return responseEntity;
		}

		// 変更後ユーザ情報が設定されている場合、ユーザ情報の更新を行う
		if (StringUtils.isNotEmpty(requestEntity.getUpdatedUserInfo().getUserId())) {
			// ユーザ情報の重複チェック
			if (!this.settingOtherUserService.checkUserInfo(requestEntity)) {
				responseEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
				responseEntity.setMessage("userName Duplicate");
				responseEntity.setUserNameDuplicatedFlg(CommonConst.FLG_ON);
				logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.ADMIN_SETTING_USER, requestEntity.getUserInfo(),
						requestEntity.getDeviceInfo(), "checkUserInfo"));
				return responseEntity;
			}
			if (!this.settingOtherUserService.registUserInfo(requestEntity, responseEntity)) {
				// HTTPステータスコードを"Internal Server Error(500)"に設定する。
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				responseEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
				logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.ADMIN_SETTING_USER, requestEntity.getUserInfo(),
						requestEntity.getDeviceInfo(), "registUserInfo"));
				return responseEntity;
			}
		}
		// レスポンス情報作成
		responseEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.ADMIN_SETTING_USER, requestEntity.getUserInfo(),
				requestEntity.getDeviceInfo(), response.getStatus(), responseEntity.getResultCd()));
		return responseEntity;
	}

}
