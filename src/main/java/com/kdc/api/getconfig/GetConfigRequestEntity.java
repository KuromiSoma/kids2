package com.kdc.api.getconfig;

import com.kdc.common.entity.api.UpdateInfoEntity;
import com.kdc.common.entity.api.base.RequestBaseEntity;

public class GetConfigRequestEntity extends RequestBaseEntity {

	private UpdateInfoEntity updateInfo;

	/**
	 * @return updateInfo
	 */
	public UpdateInfoEntity getUpdateInfo() {
		return updateInfo;
	}

	/**
	 * @param updateInfo
	 *            セットする updateInfo
	 */
	public void setUpdateInfo(UpdateInfoEntity updateInfo) {
		this.updateInfo = updateInfo;
	}

}
