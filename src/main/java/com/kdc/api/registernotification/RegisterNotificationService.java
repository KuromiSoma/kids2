package com.kdc.api.registernotification;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.db.NotificationInfoEntity;
import com.kdc.common.enums.RegisterNotificationModeEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.CommonService;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.mybatis.mapper.common.entity.NotificationInfoMapper;

/**
 * 登録通知API Serviceクラス
 * @author ケリム
 *
 */
@Service
public class RegisterNotificationService {

	@Autowired
	private NotificationInfoMapper notificationInfoMapper;
		
	@Autowired
	private CommonService commonService;
	
	
	/**
	 * パラメータチェック
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean parameterCheck(RegisterNotificationRequestEntity reqEntity, ResponseBaseEntity resEntity) {
		
		//端末情報
		if(!this.commonService.checkBaseParameter(reqEntity, resEntity)){
			return false;
		}
		
		//モード
		
		if(reqEntity.getMode() == null ) {
			resEntity.setMessage("mode is Empty");
			return false;
		}
		//通知情報情報
		
		//モード
		try {
			RegisterNotificationModeEnum.valueOf(reqEntity.getMode());
			if ( reqEntity.getMode() == 1) {
				//新規モード
				//ユーザーID
				if ( StringUtil.isEmpty(reqEntity.getNotificationInfo().getUserId())){
					resEntity.setMessage("notificationInfo.userid is Empty");
					return false;
				}
				//グループID
				if ( StringUtil.isEmpty(reqEntity.getNotificationInfo().getGroupId())){
					resEntity.setMessage("notificationInfo.groupid is Empty");
					return false;
				}
				//通知日
				if ( StringUtil.isEmpty(reqEntity.getNotificationInfo().getNotificationDate())){
					resEntity.setMessage("notificationInfo.NotificationDate is Empty");
					return false;
				}
				//通知時間
				if ( StringUtil.isEmpty(reqEntity.getNotificationInfo().getNotificationTime())){
					resEntity.setMessage("notificationInfo.NotificationTime is Empty");
					return false;
				}
			} /**else if ( reqEntity.getMode() == 2) {
				//更新
				//ユーザーID
				if ( StringUtil.isEmpty(reqEntity.getNotificationInfo().getUserid())){
					resEntity.setMessage("notificationInfo.userid is Empty");
					return false;
				}
				//グループ名
				if ( StringUtil.isEmpty(reqEntity.getNotificationInfo().getGroupname())){
					resEntity.setMessage("notificationInfo.groupName is Empty");
					return false;
				}
			} **/else if ( reqEntity.getMode() == 3 ) {
				//削除
				//ユーザーID
				if ( StringUtil.isEmpty(reqEntity.getNotificationInfo().getUserId())){
					resEntity.setMessage("notificationInfo.userid is Empty");
					return false;
				}
				//通知日
				if ( StringUtil.isEmpty(reqEntity.getNotificationInfo().getNotificationDate())){
					resEntity.setMessage("notificationInfo.NotificationDate is Empty");
					return false;
				}
			}
			
		} catch (IllegalArgumentException e) {
			resEntity.setMessage("mode is Wrong");
			return false;
		}
		
		//グループID　MAX LENGTH
		if ( StringUtils.length(reqEntity.getNotificationInfo().getGroupId()) > CommonConst.GROUP_ID_MAX_LENGTH) {
			resEntity.setMessage("getNotificationInfo.groupid is too Long");
			return false;
		}
				
		//ユーザーID MAX LENGTH
		if ( StringUtils.length(reqEntity.getNotificationInfo().getUserId()) > CommonConst.USER_ID_MAX_LENGTH) {
			resEntity.setMessage("getNotificationInfo.userid is too Long");
			return false;
		}
				
		//通知日MAX LENGTH
		if ( StringUtils.length(reqEntity.getNotificationInfo().getNotificationDate()) > CommonConst.NOTIFICATION_DATE_MAX_LENGTH) {
			resEntity.setMessage("getNotificationInfo.NotificationDate is too Long");
			return false;
		}
		
		//通知日MAX LENGTH
		if ( StringUtils.length(reqEntity.getNotificationInfo().getNotificationTime()) > CommonConst.NOTIFICATION_TIME_MAX_LENGTH) {
			resEntity.setMessage("getNotificationInfo.NotificationTime is too Long");
			return false;
		}	
		return true;
}
	/**
	 * 通知情報登録
	 * @param reqEntity
	 * @return
	 */
	public Boolean registerNotification(RegisterNotificationRequestEntity reqEntity, RegisterNotificationResponseEntity resEntity) {
		NotificationInfoEntity rec = new NotificationInfoEntity();
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();
		
		switch(RegisterNotificationModeEnum.valueOf(reqEntity.getMode())) {
		case INSERT:
		/******************************************************
		case UPDATE:
			// 
			rec.setGroupid(reqEntity.getNotificationInfo().getGroupid());
			rec.setUserid(reqEntity.getNotificationInfo().getUserid());
			rec.setGroupname(reqEntity.getNotificationInfo().getGroupname());
			rec.setConfigparam1(reqEntity.getNotificationInfo().getConfigparam1());
			rec.setConfigparam2(reqEntity.getNotificationInfo().getConfigparam2());
			rec.setConfigparam3(reqEntity.getNotificationInfo().getConfigparam3());
			rec.setConfigparam4(reqEntity.getNotificationInfo().getConfigparam4());
			rec.setConfigparam5(reqEntity.getNotificationInfo().getConfigparam5());
			break;
			**************************************************/
		case DELETE:
			break;
		}
		
		try {
			switch(RegisterNotificationModeEnum.valueOf(reqEntity.getMode())){
			case INSERT: //新規
				//通知情報登録
				rec.setGroupId(reqEntity.getNotificationInfo().getGroupId());
				rec.setUserId(reqEntity.getNotificationInfo().getUserId());
				rec.setNotificationDate(reqEntity.getNotificationInfo().getNotificationDate());
				rec.setNotificationTime(reqEntity.getNotificationInfo().getNotificationTime());
				rec.setRegisteruserid(reqEntity.getUserInfo().getUserId());
				rec.setRegisterdate(nowTimestamp);
				//初期値０で設定				
				rec.setAuthLevel(reqEntity.getNotificationInfo().getAuthLevel());
				rec.setNotificationLevel(reqEntity.getNotificationInfo().getNotificationLevel());
				rec.setNotificationFlg(reqEntity.getNotificationInfo().getNotificationFlg());
				rec.setMessage(reqEntity.getNotificationInfo().getMessage());
				resEntity.setMessage("notificationInfoMapper.insert");
				this.notificationInfoMapper.insert(rec);
				/***********************************
				resEntity.setMessage("notificationInfoMapper.insertSendIntervalConfig");
				this.notificationInfoMapper.insertSendIntervalConfig(rec);
				resEntity.setMessage("notificationInfoMapper.insertUserAlertConfig");
				this.notificationInfoMapper.insertUserAlertConfig(rec);
				resEntity.setMessage("notificationInfoMapper.insertConfigMasterg");
				this.notificationInfoMapper.insertConfigMasterg(rec);
				***************************************/
				break;
			/***************************************************
			case UPDATE: //更新
				rec.setGroupid(reqEntity.getNotificationInfo().getGroupid());
				rec.setUserid(reqEntity.getNotificationInfo().getUserid());
				rec.setGroupname(reqEntity.getNotificationInfo().getGroupname());
				
				rec.setUpdatedate(nowTimestamp);
				this.notificationInfoMapper.update(rec);				
				break;
			***********************************************************/
			case DELETE: //削除
				rec.setUserId(reqEntity.getNotificationInfo().getUserId());
				rec.setNotificationDate(reqEntity.getNotificationInfo().getNotificationDate());
				rec.setUpdateuserid(reqEntity.getUserInfo().getUserId());
				rec.setUpdatedate(nowTimestamp);
				this.notificationInfoMapper.delete(rec);
				break;
			default:
				return false;
			}

			return true;

		}catch ( Exception e ) {
			resEntity.setMessage(e.getMessage() + " ApiId:" + resEntity.getApiId());
			return false;
		}		
	}
}