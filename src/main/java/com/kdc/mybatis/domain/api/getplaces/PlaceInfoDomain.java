package com.kdc.mybatis.domain.api.getplaces;

import com.kdc.common.entity.db.PlaceMasterEntityWrapper;

public class PlaceInfoDomain extends PlaceMasterEntityWrapper {
	
	private String placedispflg;

	/**
	 * @return placedispflg
	 */
	public String getPlacedispflg() {
		return placedispflg;
	}

	/**
	 * @param placedispflg セットする placedispflg
	 */
	public void setPlacedispflg(String placedispflg) {
		this.placedispflg = placedispflg;
	}
	
	

}
