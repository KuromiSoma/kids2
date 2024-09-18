package com.kdc.api.sendlocation;

import java.util.List;

import com.kdc.common.entity.api.LocationRecordInfoEntity;
import com.kdc.common.entity.api.base.RequestBaseEntity;

public class SendLocationRequestEntity extends RequestBaseEntity {

	private List<LocationRecordInfoEntity> locationRecordInfo;

	/**
	 * @return locationRecordInfo
	 */
	public List<LocationRecordInfoEntity> getLocationRecordInfo() {
		return locationRecordInfo;
	}

	/**
	 * @param locationRecordInfo
	 *            セットする locationRecordInfo
	 */
	public void setLocationRecordInfo(List<LocationRecordInfoEntity> locationRecordInfo) {
		this.locationRecordInfo = locationRecordInfo;
	}

}
