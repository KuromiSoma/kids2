package com.kdc.api.login;

import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;

public class LoginResponseEntity extends ResponseBaseEntity {

	private UserInfoEntity userInfo;

	/**
	 * @return userInfo
	 */
	public UserInfoEntity getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo
	 *            セットする userInfo
	 */
	public void setUserInfo(UserInfoEntity userInfo) {
		this.userInfo = userInfo;
	}

}
