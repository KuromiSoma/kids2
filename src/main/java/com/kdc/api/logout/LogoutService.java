package com.kdc.api.logout;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.api.logout.LogoutRequestEntity;
import com.kdc.api.logout.LogoutResponseEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;
import com.kdc.common.entity.db.UserDeviceEntity;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.CommonService;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.mybatis.mapper.common.entity.UserDeviceMapper;
import com.kdc.mybatis.mapper.common.entity.UserMasterMapper;

/**
 * ログイン API Service クラス
 *
 */
@Service
public class LogoutService {

	@Autowired
	private UserMasterMapper userMasterMapper;

	@Autowired
	private UserDeviceMapper userDeviceMapper;

	@Autowired
	private CommonService commonService;

	/**
	 * パラメータチェック.
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean parameterCheck(LogoutRequestEntity reqEntity, LogoutResponseEntity resEntity) {

		// ユーザー情報
		if (reqEntity.getUserInfo() == null) {
			resEntity.setMessage("userInfo is Empty");
			return false;
		}
		// ID・パスワードのみ必須チェック
		if (StringUtils.isEmpty(reqEntity.getUserInfo().getUserId())) {
			resEntity.setMessage("userInfo.userId is Empty");
			return false;
		}
		if (StringUtils.isEmpty(reqEntity.getUserInfo().getPassword())) {
			resEntity.setMessage("userInfo.password is Empty");
			return false;
		}
		// 端末
		if (reqEntity.getDeviceInfo() == null) {
			resEntity.setMessage("deviceInfo is Empty");
			return false;
		}
		// 単項目チェック
		if (!this.commonService.checkDeviceInfoValues(reqEntity.getDeviceInfo(), resEntity)) {
			return false;
		}

		return true;
	}

	/**
	 * 登録状態チェック
	 * @param userInfo
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean checkRegistration(UserInfoEntity userInfo, LogoutResponseEntity resEntity) {

		// ユーザIDでユーザ情報取得
		UserMasterEntity user = this.userMasterMapper.selectUser(userInfo.getUserId(), userInfo.getPassword(),
				CommonConst.FLG_OFF);

		// ユーザID登録状態
		if (user == null) {
			resEntity.setMessage("user not exists");
			return false;
		}

		userInfo.setUserName(user.getUsername());
		userInfo.setIconId(user.getIconid());
		userInfo.setGroupId(user.getGroupid());
		userInfo.setAuthLevel(String.valueOf(user.getAuthlevel()));
		userInfo.setLineColor(user.getLinecolor());
		userInfo.setMarkerColor(String.valueOf(user.getMarkercolor()));
		resEntity.setUserInfo(userInfo);

		return true;
	}

	/**
	 * ユーザ端末登録
	 * @param req
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean registerDevice(LogoutRequestEntity req, LogoutResponseEntity resEntity) {

		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();
		UserDeviceEntity rec = new UserDeviceEntity();
		rec.setUserid(req.getUserInfo().getUserId());
		rec.setTelephonenumber(req.getDeviceInfo().getTelephoneNumber());
		rec.setDeviceid(req.getDeviceInfo().getDeviceId());
		rec.setRegisteruserid(req.getUserInfo().getUserId());
		rec.setRegisterdate(nowTimestamp);
		rec.setUpdateuserid(req.getUserInfo().getUserId());
		rec.setUpdatedate(nowTimestamp);

		try {
			// ユーザIDで端末情報取得
			UserDeviceEntity device = this.userDeviceMapper.selectByPk(req.getDeviceInfo().getUserId(),
					CommonConst.FLG_OFF);
			if (device != null) {
				if (KdcCommonUtils.nullSafeEquals(req.getDeviceInfo().getUserId(), device.getUserid())) {
					// 端末情報初期化
					this.userDeviceMapper.deleteDevice(rec);
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
