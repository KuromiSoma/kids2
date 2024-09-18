package com.kdc.api.sendlocation;

import com.kdc.common.entity.api.UpdateInfoEntity;
import com.kdc.common.entity.api.base.ResponseBaseEntity;

public class SendLocationResponseEntity extends ResponseBaseEntity {

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