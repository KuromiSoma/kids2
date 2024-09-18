package com.kdc.api.settinguser;

import com.kdc.common.entity.api.base.ResponseBaseEntity;

public class SettingUserResponseEntity extends ResponseBaseEntity {

	private Integer userNameDuplicatedFlg;
	private Integer userColorDuplicatedFlg;

	/**
	 * @return userNameDuplicatedFlg
	 */
	public Integer getUserNameDuplicatedFlg() {
		return userNameDuplicatedFlg;
	}

	/**
	 * @param userNameDuplicatedFlg
	 *            セットする userNameDuplicatedFlg
	 */
	public void setUserNameDuplicatedFlg(Integer userNameDuplicatedFlg) {
		this.userNameDuplicatedFlg = userNameDuplicatedFlg;
	}

	/**
	 * @return userColorDuplicatedFlg
	 */
	public Integer getUserColorDuplicatedFlg() {
		return userColorDuplicatedFlg;
	}

	/**
	 * @param userColorDuplicatedFlg
	 *            セットする userColorDuplicatedFlg
	 */
	public void setUserColorDuplicatedFlg(Integer userColorDuplicatedFlg) {
		this.userColorDuplicatedFlg = userColorDuplicatedFlg;
	}

}
