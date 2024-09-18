package com.kdc.api.deluser;

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
 * ユーザ削除 API Controller クラス
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class DelUserController {

	private static final Logger logger = LoggerFactory.getLogger(DelUserController.class);

	@Autowired
	private DelUserService delUserService;

	// ユーザ削除
	@RequestMapping(path = CommonConst.API_BASE_URL
			+ "/admin/deluser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DelUserResponseEntity delUser(@RequestBody DelUserRequestEntity bean, HttpServletResponse response) {

		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.ADMIN_DELUSER, bean.getUserInfo(), bean.getDeviceInfo()));

		// レスポンス作成
		DelUserResponseEntity resEntity = new DelUserResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_ADMIN_DELUSER);

		// パラメータチェック
		if (!this.delUserService.parameterCheck(bean, resEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.ADMIN_DELUSER, bean.getUserInfo(), bean.getDeviceInfo(),
					"parameterCheck"));
			return resEntity;
		}

		// 削除(update)
		if (!this.delUserService.delete(bean, resEntity)) {
			// HTTPステータスコードを"Internal Server Error(500)"に設定する。
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.ADMIN_DELUSER, bean.getUserInfo(), bean.getDeviceInfo(),
					"delete"));
			return resEntity;
		}

		// レスポンス作成
		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.ADMIN_DELUSER, bean.getUserInfo(), bean.getDeviceInfo(),
				response.getStatus(), resEntity.getResultCd()));
		return resEntity;
	}
}
