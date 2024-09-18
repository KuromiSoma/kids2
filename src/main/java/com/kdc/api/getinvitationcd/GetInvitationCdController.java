package com.kdc.api.getinvitationcd;

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
 * 招待コード発行 API Controller クラス
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class GetInvitationCdController {

	private static final Logger logger = LoggerFactory.getLogger(GetInvitationCdController.class);

	@Autowired
	private GetInvitationCdService getInvitationCdService;

	// 招待コード発行
	@RequestMapping(path = CommonConst.API_BASE_URL
			+ "/invitation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GetInvitationCdResponseEntity getInvitationCd(@RequestBody GetInvitationCdRequestEntity bean,
			HttpServletResponse response) {

		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.GET_INVITATION, bean.getUserInfo(), bean.getDeviceInfo()));

		// レスポンス作成
		GetInvitationCdResponseEntity resEntity = new GetInvitationCdResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_GET_INVITATION);

		// チェック関連
		if (!this.getInvitationCdService.parameterCheck(bean, resEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.GET_INVITATION, bean.getUserInfo(),
					bean.getDeviceInfo(), "parameterCheck"));
			return resEntity;
		}

		// 招待コード取得
		GetInvitationCdResponseEntity res = this.getInvitationCdService.getCd(bean);

		resEntity.setInvitationCd(res.getInvitationCd());
		resEntity.setExpirationDate(res.getExpirationDate());
		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);

		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.GET_INVITATION, bean.getUserInfo(), bean.getDeviceInfo(),
				response.getStatus(), resEntity.getResultCd()));
		return resEntity;
	}

}
