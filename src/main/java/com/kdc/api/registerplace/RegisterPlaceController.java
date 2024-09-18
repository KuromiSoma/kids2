package com.kdc.api.registerplace;

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
 * 場所登録 API Controller クラス
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class RegisterPlaceController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterPlaceController.class);

	@Autowired
	private RegisterPlaceService registerPlaceService;

	// 場所登録
	@RequestMapping(path = CommonConst.API_BASE_URL
			+ "/register/place", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RegisterPlaceResponseEntity registerPlace(@RequestBody RegisterPlaceRequestEntity bean,
			HttpServletResponse response) {

		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.REGISTER_PLACE, bean.getUserInfo(), bean.getDeviceInfo()));

		// レスポンス作成
		RegisterPlaceResponseEntity resEntity = new RegisterPlaceResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_REGISTER_PLACE);

		// パラメータチェック
		if (!this.registerPlaceService.parameterCheck(bean, resEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.REGISTER_PLACE, bean.getUserInfo(),
					bean.getDeviceInfo(), "parameterCheck"));
			return resEntity;
		}

		// 場所情報登録
		if (!this.registerPlaceService.register(bean)) {
			// HTTPステータスコードを"Internal Server Error(500)"に設定する。
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.REGISTER_PLACE, bean.getUserInfo(),
					bean.getDeviceInfo(), "register"));
			return resEntity;
		}

		// レスポンス作成
		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.REGISTER_PLACE, bean.getUserInfo(), bean.getDeviceInfo(),
				response.getStatus(), resEntity.getResultCd()));
		return resEntity;
	}
}
