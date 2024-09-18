package com.kdc.api.settinguser;

import com.kdc.common.entity.api.base.RequestBaseEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;

public class SettingUserRequestEntity extends RequestBaseEntity {

	private UserInfoEntity updatedUserInfo;
	private String iconFile; // BASE64エンコード

	/**
	 * @return updatedUserInfo
	 */
	public UserInfoEntity getUpdatedUserInfo() {
		return updatedUserInfo;
	}

	/**
	 * @param updatedUserInfo
	 *            セットする updatedUserInfo
	 */
	public void setUpdatedUserInfo(UserInfoEntity updatedUserInfo) {
		this.updatedUserInfo = updatedUserInfo;
	}

	/**
	 * @return iconFile
	 */
	public String getIconFile() {
		return iconFile;
	}

	/**
	 * @param iconFile
	 *            セットする iconFile
	 */
	public void setIconFile(String iconFile) {
		this.iconFile = iconFile;
	}

}
