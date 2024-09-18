package com.kdc.api.checkuser;

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
 * ユーザーチェック API Controller クラス
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class CheckUserController {
	private static final Logger logger = LoggerFactory.getLogger(CheckUserController.class);
	
	@Autowired
	private CheckUserService checkUserService;
	
	//チェックユーザー
	@RequestMapping(path = CommonConst.API_BASE_URL + "/checkuser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE )
	public CheckUserResponseEntity checkUser(@RequestBody CheckUserRequestEntity bean, HttpServletResponse response) {

		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.CHECK_USER, bean.getUserInfo(), bean.getDeviceInfo()));

		//レスポンス作成
		CheckUserResponseEntity resEntity = new CheckUserResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_CHECK_USER);
		
		//パラメータチェック
		if (!this.checkUserService.parameterCheck(bean, resEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.CHECK_USER, bean.getUserInfo(), bean.getDeviceInfo(),
					"parameterCheck"));
			return resEntity;
		}
		
		
		//登録存在チェック（true：存在しない　false：存在する）
		if(this.checkUserService.checkRegistration(bean, resEntity)) {
			//ユーザー存在する
			resEntity.setUserExistsFlg(CommonConst.CHECK_USER_EXIST);
		} else {
			//ユーザー存在しない
			resEntity.setUserExistsFlg(CommonConst.CHECK_USER_NOT_EXIST);
			//初期パスワード取得
			String password = this.checkUserService.getPassword(bean, resEntity);
			//パスワード設定
			resEntity.setPassword(password);
		}
		
		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.CHECK_USER, bean.getUserInfo(), bean.getDeviceInfo(),
				response.getStatus(), resEntity.getResultCd()));
		return resEntity;
	}
}
