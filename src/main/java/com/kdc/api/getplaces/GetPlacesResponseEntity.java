package com.kdc.api.getplaces;

import java.util.List;

import com.kdc.common.entity.api.PlaceInfoEntity;
import com.kdc.common.entity.api.base.ResponseBaseEntity;

public class GetPlacesResponseEntity extends ResponseBaseEntity {

	private List<PlaceInfoEntity> placeInfo;

	/**
	 * @return placeInfo
	 */
	public List<PlaceInfoEntity> getPlaceInfo() {
		return placeInfo;
	}

	/**
	 * @param placeInfo
	 *            セットする placeInfo
	 */
	public void setPlaceInfo(List<PlaceInfoEntity> placeInfo) {
		this.placeInfo = placeInfo;
	}

}
