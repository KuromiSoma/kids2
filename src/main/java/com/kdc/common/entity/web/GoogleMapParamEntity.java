package com.kdc.common.entity.web;

public class GoogleMapParamEntity {

	private int zoom;
	private String latitude;
	private String longitude;

	/**
	 * @return zoom
	 */
	public int getZoom() {
		return zoom;
	}

	/**
	 * @param zoom
	 *            セットする zoom
	 */
	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	/**
	 * @return latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            セットする latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            セットする longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
