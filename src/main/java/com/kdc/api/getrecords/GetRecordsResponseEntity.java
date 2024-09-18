package com.kdc.api.getrecords;

import java.util.List;

import com.kdc.common.entity.api.LocationRecordInfoEntity;
import com.kdc.common.entity.api.base.ResponseBaseEntity;

public class GetRecordsResponseEntity extends ResponseBaseEntity {

	private List<LocationRecordInfoEntity> userRecordInfo;

	/**
	 * @return userRecordInfo
	 */
	public List<LocationRecordInfoEntity> getUserRecordInfo() {
		return userRecordInfo;
	}

	/**
	 * @param userRecordInfo
	 *            セットする userRecordInfo
	 */
	public void setUserRecordInfo(List<LocationRecordInfoEntity> userRecordInfo) {
		this.userRecordInfo = userRecordInfo;
	}

}
