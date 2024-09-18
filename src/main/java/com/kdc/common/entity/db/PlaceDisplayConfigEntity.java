package com.kdc.common.entity.db;

public class PlaceDisplayConfigEntity extends CommonColumnsEntity {

	private Integer authlevel;
	private String placeid;
	private Integer placedispflg;
	
	/**
	 * @return authlevel
	 */
	public Integer getAuthlevel() {
		return authlevel;
	}
	/**
	 * @param authlevel セットする authlevel
	 */
	public void setAuthlevel(Integer authlevel) {
		this.authlevel = authlevel;
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
	 * @return placedispflg
	 */
	public Integer getPlacedispflg() {
		return placedispflg;
	}
	/**
	 * @param placedispflg セットする placedispflg
	 */
	public void setPlacedispflg(Integer placedispflg) {
		this.placedispflg = placedispflg;
	}

}
