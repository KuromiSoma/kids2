package com.kdc.api.checkinvitationcd;

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
import com.kdc.common.entity.api.base.UserInfoEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.enums.AuthenticationModeEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;

/**
 * 招待コード認証 API Controller クラス
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class CheckInvitationCdController {

	private static final Logger logger = LoggerFactory.getLogger(CheckInvitationCdController.class);

	@Autowired
	private CheckInvitationCdService checkInvitationCdService;

	@RequestMapping(path = CommonConst.API_BASE_URL
			+ "/check/invitation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public CheckInvitationCdResponseEntity checkInvitationCd(@RequestBody CheckInvitationCdRequestEntity bean,
			HttpServletResponse response) {

		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId(bean.getInvitationCd());
		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.CHECK_INVITATION, userInfo, bean.getDeviceInfo()));

		// レスポンス作成
		CheckInvitationCdResponseEntity resEntity = new CheckInvitationCdResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_CHECK_INVITATION);

		// チェック関連
		if (!this.checkInvitationCdService.parameterCheck(bean, resEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.CHECK_INVITATION, userInfo, bean.getDeviceInfo(),
					"parameterCheck"));
			return resEntity;
		}

		switch (AuthenticationModeEnum.valueOf(bean.getMode())) {
		case LOGIN:
		case REGISTER_USER:
			// 招待管理table更新
			if (!this.checkInvitationCdService.updateInvitationManagement(bean, resEntity)) {
				// HTTPステータスコードを"Internal Server Error(500)"に設定する。
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
				logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.CHECK_INVITATION, userInfo, bean.getDeviceInfo(),
						"updateInvitationManagement"));
				return resEntity;
			}
			break;
		case NEW_GROUP:
			// 新規グループ作成時は招待コード不要
			break;
		}

		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);

		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.CHECK_INVITATION, userInfo, bean.getDeviceInfo(),
				response.getStatus(), resEntity.getResultCd()));
		return resEntity;
	}

}
