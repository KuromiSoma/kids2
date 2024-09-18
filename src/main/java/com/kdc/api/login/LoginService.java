package com.kdc.api.login;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class LoginService {

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
	public Boolean parameterCheck(LoginRequestEntity reqEntity, LoginResponseEntity resEntity) {

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
	public Boolean checkRegistration(LoginRequestEntity req, LoginResponseEntity resEntity) {

		// ユーザIDでユーザ情報取得
		UserMasterEntity user = this.userMasterMapper.selectUser(req.getUserInfo().getUserId(),req.getUserInfo().getPassword(),
				CommonConst.FLG_OFF);

		// ユーザID登録状態
		if (user == null) {
			resEntity.setMessage("user not exists");
			return false;
		}
		//強制ログインフラグ取得
		int iForceFlg = req.getForceLogin();

		// 電話番号で端末情報取得
		UserDeviceEntity device = this.userDeviceMapper.selectDeviceExist(req.getDeviceInfo().getTelephoneNumber(),req.getDeviceInfo().getUserId(),
				CommonConst.FLG_OFF);
		if (device != null) {
			// 異なるユーザーIDの場合、エラー
			if (!KdcCommonUtils.nullSafeEquals(req.getDeviceInfo().getUserId(), device.getUserid())) {
				//強制ログインの場合はスルー
				if ( iForceFlg != 1) {
					resEntity.setMessage("user already logged");
					return false;
				}
			}
		}

		// ユーザIDでユーザ情報取得（デバイスID　NULL以外かつ自身のデバイスID以外）
		device = this.userDeviceMapper.selectUserExist(req.getDeviceInfo().getUserId(),req.getDeviceInfo().getDeviceId(),
				CommonConst.FLG_OFF);
		if (device != null) {
			// 同じユーザIDで異なるデバイスIDの場合、エラー
			if (!KdcCommonUtils.nullSafeEquals(req.getDeviceInfo().getDeviceId(), device.getDeviceid())) {
				//強制ログインの場合はスルー
				if ( iForceFlg != 1) {
					resEntity.setMessage("user already logged");
					return false;
				}
			}
		}
		
		req.getUserInfo().setUserName(user.getUsername());
		req.getUserInfo().setIconId(user.getIconid());
		req.getUserInfo().setGroupId(user.getGroupid());
		req.getUserInfo().setAuthLevel(String.valueOf(user.getAuthlevel()));
		req.getUserInfo().setLineColor(user.getLinecolor());
		req.getUserInfo().setMarkerColor(String.valueOf(user.getMarkercolor()));
		resEntity.setUserInfo(req.getUserInfo());

		return true;
	}

	/**
	 * ユーザ端末登録
	 * @param req
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean registerDevice(LoginRequestEntity req, LoginResponseEntity resEntity) {

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
			// 電話番号で端末情報取得
			UserDeviceEntity device = this.userDeviceMapper.selectDevice(req.getDeviceInfo().getTelephoneNumber(),
					CommonConst.FLG_OFF);
			if (device != null) {
				if (KdcCommonUtils.nullSafeEquals(req.getDeviceInfo().getUserId(), device.getUserid())) {
					// 前回ログインと同じ端末の場合、端末情報は更新不要
					return true;
				} else {
//					resEntity.setMessage("user already logged");
//					return false;
					// 別のユーザが同じ電話番号でログイン状態の場合、奪い取る
					this.userDeviceMapper.deleteDevice(rec);
					return true;
				}
			}
			if (this.userDeviceMapper.selectByPk(req.getUserInfo().getUserId(), CommonConst.FLG_OFF) != null) {
				this.userDeviceMapper.update(rec);
			} else {
				this.userDeviceMapper.insert(rec);
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
