package com.kdc.api.settinguser;

import java.util.Base64;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.mybatis.mapper.api.SettingUserMapper;
import com.kdc.mybatis.mapper.common.util.PushNotificationMapper;
import com.kdc.common.entity.api.PushNotificationEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.enums.PushNotificationEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.CommonService;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.common.util.PushNotificationCounter;
import com.kdc.common.util.PushNotificationService;

/**
 * ユーザ情報変更 API Service クラス
 *
 */
@Service
public class SettingUserService {

	private static final Logger logger = LoggerFactory.getLogger(SettingUserService.class);

	@Autowired
	private SettingUserMapper settingUserMapper;

	@Autowired
	private PushNotificationMapper pushNotificationMapper;

	@Autowired
	private CommonService commonService;

	@Autowired
	private PushNotificationService pushNotificationService;

	/**
	 * パラメータチェック.
	 * @param requestEntity
	 * @param responseEntity
	 * @return Boolean
	 */
	public Boolean parameterCheck(SettingUserRequestEntity requestEntity, SettingUserResponseEntity responseEntity) {

		// 必須チェック
		if (!this.commonService.checkBaseParameter(requestEntity, responseEntity)) {
			return false;
		}

		// 変更後ユーザ情報
		if (requestEntity.getUpdatedUserInfo() == null) {
			responseEntity.setMessage("updatedUserInfo is Empty");
			return false;
		}
		// 変更後ユーザ情報.ユーザID
		if (StringUtils.isEmpty(requestEntity.getUpdatedUserInfo().getUserId())) {
			responseEntity.setMessage("updatedUserInfo.userId is Empty");
			return false;
		}

		// 変更後ユーザ情報.パスワード
		if (StringUtils.length(requestEntity.getUpdatedUserInfo().getPassword()) > CommonConst.PASSWORD_MAX_LENGTH) {
			responseEntity.setMessage("updatedUserInfo.password is too Long");
			return false;
		}
		// 変更後ユーザ情報.ユーザ名
		if (StringUtils.length(requestEntity.getUpdatedUserInfo().getUserName()) > CommonConst.USER_NAME_MAX_LENGTH) {
			responseEntity.setMessage("updatedUserInfo.userName is too Long");
			return false;
		}

		// ユーザ情報.ユーザIDと変更後ユーザ情報.ユーザIDが一致するか
		if (!requestEntity.getUserInfo().getUserId().equals(requestEntity.getUpdatedUserInfo().getUserId())) {
			responseEntity.setMessage("user-updatedUser Unmatch");
			return false;
		}

		return true;
	}

	/**
	 * ユーザ情報重複チェック
	 * @param requestEntity
	 * @return boolean
	 */
	public boolean checkUserInfo(SettingUserRequestEntity requestEntity) {
		UserMasterEntity rec = new UserMasterEntity();
		rec.setUserid(requestEntity.getUpdatedUserInfo().getUserId());
		rec.setUsername(requestEntity.getUpdatedUserInfo().getUserName());
		Integer userCnt = this.settingUserMapper.selectUserInfo(rec);

		if (userCnt > 0) {
			return false;
		}
		return true;
	}

	/**
	 * ユーザー情報登録.
	 * @param requestEntity
	 * @param responseEntity
	 * @return Boolean
	 */
	public Boolean registUserInfo(SettingUserRequestEntity requestEntity, SettingUserResponseEntity responseEntity) {

		try {
			boolean iconUpdate = false;
			if (StringUtils.isNotEmpty(requestEntity.getIconFile())) {
				iconUpdate = true;
			}

			// 条件
			UserMasterEntity condRec = new UserMasterEntity();
			// 変更後ユーザを対象とする
			condRec.setUserid(requestEntity.getUpdatedUserInfo().getUserId());

			// 登録値
			UserMasterEntity regRec = new UserMasterEntity();
			regRec.setPassword(requestEntity.getUpdatedUserInfo().getPassword());
			regRec.setUsername(requestEntity.getUpdatedUserInfo().getUserName());
			if (iconUpdate) {
				regRec.setIconid(requestEntity.getUpdatedUserInfo().getIconId());
				// アイコン用にリサイズして保存
				regRec.setIconfile(KdcCommonUtils.scaleImage(Base64.getDecoder().decode(requestEntity.getIconFile()),
						CommonConst.ICON_WIDTH, CommonConst.ICON_HEIGHT, "png"));
			}
			regRec.setGroupid(requestEntity.getUpdatedUserInfo().getGroupId());
			regRec.setAuthlevel(KdcCommonUtils.nullSafeParseInt(requestEntity.getUpdatedUserInfo().getAuthLevel()));
			regRec.setLinecolor(requestEntity.getUpdatedUserInfo().getLineColor());
			regRec.setMarkercolor(KdcCommonUtils.nullSafeParseInt(requestEntity.getUpdatedUserInfo().getMarkerColor()));
			regRec.setUpdateuserid(requestEntity.getUserInfo().getUserId());
			regRec.setUpdatedate(KdcCommonUtils.getNowTimestamp());

			this.settingUserMapper.updateUserMaster(regRec, condRec);

			if (iconUpdate) {
				PushNotificationCounter counter = new PushNotificationCounter();
				// アイコン登録通知（Push通知）送信
				this.sendPushNotification(requestEntity.getUpdatedUserInfo(), counter);
				responseEntity.setMessage("ユーザアイコン変更Push通知 OK:" + counter.getSendCompleteCnt() + " NG:"
						+ counter.getSendErrorCnt() + " TokenID未設定:" + counter.getSendExceptCnt());
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * アイコン登録Push通知.
	 * @param userInfo
	 * @param counter
	 */
	private void sendPushNotification(UserInfoEntity userInfo, PushNotificationCounter counter) {
		// アイコン変更したユーザの表示を行うユーザに通知する
		List<PushNotificationEntity> notifyList = this.pushNotificationMapper
				.selectSendIconTokenIdList(userInfo.getUserId(), true);
		this.pushNotificationService.setApiKey();
		this.pushNotificationService.sendAlert(ApiIdEnum.valueOf(CommonConst.API_ID_SETTING_USER),
				PushNotificationEnum.USERICON, userInfo, notifyList, counter, logger);
		logger.info("ユーザアイコン変更Push通知 OK:" + counter.getSendCompleteCnt() + " NG:" + counter.getSendErrorCnt()
				+ " TokenID未設定:" + counter.getSendExceptCnt());
		for (String token : counter.getErrorTokenList()) {
			logger.info("Send Error:" + token);
		}
	}

}
