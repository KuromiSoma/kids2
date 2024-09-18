package com.kdc.api.sendsosalert;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.api.PushNotificationEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.enums.PushNotificationEnum;
import com.kdc.mybatis.mapper.common.entity.UserMasterMapper;
import com.kdc.mybatis.mapper.common.util.PushNotificationMapper;
import groovy.util.logging.Slf4j;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.CommonService;
import com.kdc.common.util.PushNotificationService;
import com.kdc.common.util.PushNotificationCounter;

/**
 * SOS送信 API Service クラス
 *
 */
@Service
@Slf4j
public class SendSosAlertService {

	private static final Logger logger = LoggerFactory.getLogger(SendSosAlertService.class);

	@Autowired
	private PushNotificationService pushNotificationService;

	@Autowired
	private UserMasterMapper userMasterMapper;

	@Autowired
	private PushNotificationMapper pushNotificationMapper;

	@Autowired
	private CommonService commonService;

	/**
	 * パラメータチェック.
	 * @param requestEntity
	 * @param responseEntity
	 * @return Boolean
	 */
	public Boolean parameterCheck(SendSosAlertRequestEntity requestEntity, SendSosAlertResponseEntity responseEntity) {
		// 必須チェック
		if (!this.commonService.checkBaseParameter(requestEntity, responseEntity)) {
			return false;
		}

		return true;
	}

	/**
	 * SOS通知.
	 * @param userInfo
	 * @param responseEntity
	 * @return Boolean
	 */
	public Boolean sendSosAlert(UserInfoEntity userInfo, SendSosAlertResponseEntity responseEntity) {
		PushNotificationCounter counter = new PushNotificationCounter();

		try {
			UserMasterEntity master = this.userMasterMapper.selectByPk(userInfo.getUserId(), CommonConst.FLG_OFF);
			if (master == null) {
				return false;
			}
			userInfo.setGroupId(master.getGroupid());
			userInfo.setAuthLevel(String.valueOf(master.getAuthlevel()));
			userInfo.setUserName(master.getUsername());

			// 再通知待機時間（分）に関わらず通知する
			List<PushNotificationEntity> notifyList = this.pushNotificationMapper
					.selectSosAlertTokenIdList(userInfo.getUserId());
			this.pushNotificationService.setApiKey();
			if (this.pushNotificationService.sendAlert(ApiIdEnum.valueOf(CommonConst.API_ID_SEND_SOS_ALERT),
					PushNotificationEnum.SOS, userInfo, notifyList, counter, logger)) {
				// 通知時間をDBに記録
				this.pushNotificationService.upsertUserAlertRecord(PushNotificationEnum.SOS, userInfo.getUserId());
			}
			logger.info("SOSPush通知 OK:" + counter.getSendCompleteCnt() + " NG:" + counter.getSendErrorCnt()
					+ " TokenID未設定:" + counter.getSendExceptCnt());
			for (String token : counter.getErrorTokenList()) {
				logger.info("Send Error:" + token);
			}
			responseEntity.setMessage("SOSPush通知 OK:" + counter.getSendCompleteCnt() + " NG:"
					+ counter.getSendErrorCnt() + " TokenID未設定:" + counter.getSendExceptCnt());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
