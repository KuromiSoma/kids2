package com.kdc.common.entity.api;

public class PlaceDisplayConfigInfoEntity {

	private String authLevel;
	private PlaceDisplayInfoEntity placeDisplayInfo;

	/**
	 * @return authLevel
	 */
	public String getAuthLevel() {
		return authLevel;
	}

	/**
	 * @param authLevel
	 *            セットする authLevel
	 */
	public void setAuthLevel(String authLevel) {
		this.authLevel = authLevel;
	}

	/**
	 * @return placeDisplayInfo
	 */
	public PlaceDisplayInfoEntity getPlaceDisplayInfo() {
		return placeDisplayInfo;
	}

	/**
	 * @param placeDisplayInfo
	 *            セットする placeDisplayInfo
	 */
	public void setPlaceDisplayInfo(PlaceDisplayInfoEntity placeDisplayInfo) {
		this.placeDisplayInfo = placeDisplayInfo;
	}

}
