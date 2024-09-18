package com.kdc.common.entity.db;

public class PlaceMasterEntity extends CommonColumnsEntity {

	private String groupid;
	private String placeid;
	private String placename;
	private Integer placetypeflg;
	private String longitudeandlatitude;
	private Integer radius;
	private String iconid;
	
	/**
	 * @return groupid
	 */
	public String getGroupid() {
		return groupid;
	}
	
	/**
	 * @param groupid セットする groupid
	 */
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	/**
	 * @return placeid
	 */
	public String getPlaceid() {
		return placeid;
	}
	/**
	 * @param placeid セットする placeid
	 */
	public void setPlaceid(String placeid) {
		this.placeid = placeid;
	}
	/**
	 * @return placename
	 */
	public String getPlacename() {
		return placename;
	}
	/**
	 * @param placename セットする placename
	 */
	public void setPlacename(String placename) {
		this.placename = placename;
	}
	/**
	 * @return placetypeflg
	 */
	public Integer getPlacetypeflg() {
		return placetypeflg;
	}
	/**
	 * @param placetypeflg セットする placetypeflg
	 */
	public void setPlacetypeflg(Integer placetypeflg) {
		this.placetypeflg = placetypeflg;
	}
	/**
	 * @return longitudeandlatitude
	 */
	public String getLongitudeandlatitude() {
		return longitudeandlatitude;
	}
	/**
	 * @param longitudeandlatitude セットする longitudeandlatitude
	 */
	public void setLongitudeandlatitude(String longitudeandlatitude) {
		this.longitudeandlatitude = longitudeandlatitude;
	}
	/**
	 * @return radius
	 */
	public Integer getRadius() {
		return radius;
	}
	/**
	 * @param radius セットする radius
	 */
	public void setRadius(Integer radius) {
		this.radius = radius;
	}
	/**
	 * @return iconid
	 */
	public String getIconid() {
		return iconid;
	}
	/**
	 * @param iconid セットする iconid
	 */
	public void setIconid(String iconid) {
		this.iconid = iconid;
	}
	
}
