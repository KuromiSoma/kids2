package com.kdc.api.getconfig;

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
import com.kdc.api.getconfig.GetConfigRequestEntity;
import com.kdc.api.getconfig.GetConfigResponseEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;

/**
 * 設定取得 API Controller クラス
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class GetConfigController {

	private static final Logger logger = LoggerFactory.getLogger(GetConfigController.class);

	@Autowired
	private GetConfigService getConfigService;

	// 設定取得
	@RequestMapping(path = CommonConst.API_BASE_URL
			+ "/config", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GetConfigResponseEntity getConfig(@RequestBody GetConfigRequestEntity bean, HttpServletResponse response) {

		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.GET_CONFIG, bean.getUserInfo(), bean.getDeviceInfo()));

		// レスポンス作成
		GetConfigResponseEntity resEntity = new GetConfigResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_GET_CONFIG);

		// パラメータチェック
		if (!this.getConfigService.parameterCheck(bean, resEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.GET_CONFIG, bean.getUserInfo(), bean.getDeviceInfo(),
					"parameterCheck"));
			return resEntity;
		}

		// 設定情報取得
		if (!this.getConfigService.setConfig(bean, resEntity)) {
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.GET_CONFIG, bean.getUserInfo(), bean.getDeviceInfo(),
					"setConfig"));
			return resEntity;
		}

		// レスポンス作成
		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);

		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.GET_CONFIG, bean.getUserInfo(), bean.getDeviceInfo(),
				response.getStatus(), resEntity.getResultCd()));
		return resEntity;
	}
}
