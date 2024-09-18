package com.kdc.api.logout;

import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;

public class LogoutResponseEntity extends ResponseBaseEntity {
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
