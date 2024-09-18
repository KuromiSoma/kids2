package com.kdc.api.registeruser;

import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;

public class RegisterUserResponseEntity extends ResponseBaseEntity {

	private Integer userIdDuplicatedFlg;
	private UserInfoEntity userInfo;

	/**
	 * @return userIdDuplicatedFlg
	 */
	public Integer getUserIdDuplicatedFlg() {
		return userIdDuplicatedFlg;
	}

	/**
	 * @param userIdDuplicatedFlg
	 *            セットする userIdDuplicatedFlg
	 */
	public void setUserIdDuplicatedFlg(Integer userIdDuplicatedFlg) {
		this.userIdDuplicatedFlg = userIdDuplicatedFlg;
	}

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
