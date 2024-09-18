package com.kdc.api.registerplace;

import com.kdc.common.entity.api.PlaceInfoEntity;
import com.kdc.common.entity.api.base.RequestBaseEntity;

public class RegisterPlaceRequestEntity extends RequestBaseEntity {

	private Integer mode;
	private PlaceInfoEntity placeInfo;

	/**
	 * @return mode
	 */
	public Integer getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            セットする mode
	 */
	public void setMode(Integer mode) {
		this.mode = mode;
	}

	/**
	 * @return placeInfo
	 */
	public PlaceInfoEntity getPlaceInfo() {
		return placeInfo;
	}

	/**
	 * @param placeInfo
	 *            セットする placeInfo
	 */
	public void setPlaceInfo(PlaceInfoEntity placeInfo) {
		this.placeInfo = placeInfo;
	}

}
