package com.kdc.api.registergroup;

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
 * グループ登録API　Controllerクラス
 * @author umemoto
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class RegisterGroupController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterGroupController.class);
	
	@Autowired
	private RegisterGroupService registerGroupService;
	
	//グループ登録
	@RequestMapping(path = CommonConst.API_BASE_URL + "/register/group",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RegisterGroupResponseEntity registerGroup(@RequestBody RegisterGroupRequestEntity bean,HttpServletResponse response) {
		
		//log
		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.REGISTER_GROUP, bean.getUserInfo(), bean.getDeviceInfo()));
		
		//レスポンス作成
		RegisterGroupResponseEntity resEntity = new RegisterGroupResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_REGISTER_GROUP);
		
		//パラメータチェック
		if(!this.registerGroupService.parameterCheck(bean, resEntity)){
			// HTTPステータスコードをBad Request(400)に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.REGISTER_GROUP, bean.getUserInfo(), bean.getDeviceInfo(), "parameterCheck:" + resEntity.getMessage()));
			return resEntity;
		}
		
		//グループ情報登録
		if(!this.registerGroupService.registerGroup(bean,resEntity)) {
			// HTTPステータスコードをInternal Server Error(500)に設定
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.REGISTER_GROUP, bean.getUserInfo(), bean.getDeviceInfo(), "register:" + resEntity.getMessage()));
			return resEntity;
		}
		
		//レスポンス作成
		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.REGISTER_GROUP, bean.getUserInfo(), bean.getDeviceInfo(), response.getStatus(), resEntity.getResultCd()));

		return resEntity;
		
	}
}
