package com.kdc.api.registernotification;

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
 * 通知情報登録API　Controllerクラス
 * @author ケリム
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class RegisterNotificationController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterNotificationController.class);
	
	@Autowired
	private RegisterNotificationService  registerNotificationService;
	
	//通知情報登録
	@RequestMapping(path = CommonConst.API_BASE_URL + "/register/notification",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
public RegisterNotificationResponseEntity registerNotification(@RequestBody RegisterNotificationRequestEntity bean,HttpServletResponse response) {
		
		//log
		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.REGISTER_NOTIFICATION, bean.getUserInfo(), bean.getDeviceInfo()));
		
		//レスポンス作成
		RegisterNotificationResponseEntity resEntity = new RegisterNotificationResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_REGISTER_NOTIFICATION);
		
		//パラメータチェック
		if(!this.registerNotificationService.parameterCheck(bean, resEntity)){
			// HTTPステータスコードをBad Request(400)に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.REGISTER_NOTIFICATION, bean.getUserInfo(), bean.getDeviceInfo(), "parameterCheck:" + resEntity.getMessage()));
			return resEntity;
		}
		
		//通知情報登録
		if(!this.registerNotificationService.registerNotification(bean,resEntity)) {
		// HTTPステータスコードをInternal Server Error(500)に設定
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.REGISTER_NOTIFICATION, bean.getUserInfo(), bean.getDeviceInfo(), "register:" + resEntity.getMessage()));
			return resEntity;
		}

		//レスポンス作成
		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.REGISTER_NOTIFICATION, bean.getUserInfo(), bean.getDeviceInfo(), response.getStatus(), resEntity.getResultCd()));
		
		return resEntity;
}	
}
