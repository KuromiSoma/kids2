package com.kdc.api.getnotifications;

import java.util.List;

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

import com.kdc.common.entity.db.NotificationInfoEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;

/**
 * 通知情報取得　API　コントローラクラス
 * @author kerimu
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class GetnotificationsController {
	
	private static final Logger logger = LoggerFactory.getLogger(GetnotificationsController.class);
	
	@Autowired
	private GetNotificationsService getNotificationsService;
	
	//通知情報取得
	@RequestMapping(path = CommonConst.API_BASE_URL + "/notification/list" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GetNotificationsResponseEntity getNotifications(@RequestBody GetNotificationsRequestEntity bean,HttpServletResponse response ){
	
		//log
		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.GET_NOTIFICATIONS, bean.getUserInfo(), bean.getDeviceInfo()));
		
		//レスポンス作成
		GetNotificationsResponseEntity resEntity = new GetNotificationsResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_GET_NOTIFICATIONS);

		//チェック関連
		if(!this.getNotificationsService.parameterCheck(bean, resEntity)) {
			//HTTPステータスコードをBad Request(400)に設定する
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.GET_NOTIFICATIONS, bean.getUserInfo(), bean.getDeviceInfo(), "parameterCheck"));
			return resEntity;
		}
		
		// DBから取得
		List<NotificationInfoEntity> notificationInfo = this.getNotificationsService.getNotifications(bean);
		
		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		resEntity.setMessage("Get records from DB is success.");
		resEntity.setNotificationInfo(notificationInfo);
		
		//log
		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.GET_NOTIFICATIONS, bean.getUserInfo(), bean.getDeviceInfo(),response.getStatus(),resEntity.getResultCd()));
		return resEntity;
	}
}