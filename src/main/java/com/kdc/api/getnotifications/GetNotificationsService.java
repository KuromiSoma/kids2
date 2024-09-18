package com.kdc.api.getnotifications;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.db.NotificationInfoEntity;
import com.kdc.common.util.CommonService;
import com.kdc.mybatis.mapper.api.GetNotificationsMapper;

@Service
public class GetNotificationsService {
	
	@Autowired
	private GetNotificationsMapper getNotificationMapper;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * パラメータチェック
	 * @param reqEntity
	 * @param resEntity
	 * @return
	 */
	public Boolean parameterCheck(GetNotificationsRequestEntity reqEntity, ResponseBaseEntity resEntity) {
		
		if(!this.commonService.checkBaseParameter(reqEntity, resEntity)){
			return false;
		}		
		return true;
	}
	
	/**
	 * グループ情報取得
	 * @param reqEntity
	 * @return
	 */
	public List<NotificationInfoEntity> getNotifications(GetNotificationsRequestEntity reqEntity) {
		
		//2018/11/22　仕様変更：通知履歴に日付条件の解除
		reqEntity.setNotificationdate(null);
		List<NotificationInfoEntity> notificationList = this.getNotificationMapper.getNotificationList(reqEntity.getUserInfo().getUserId(),reqEntity.getUserInfo().getGroupId(),reqEntity.getNotificationDate());
		return notificationList;
		
	}

}
