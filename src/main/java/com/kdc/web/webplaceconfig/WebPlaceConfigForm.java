package com.kdc.web.webplaceconfig;

import com.kdc.web.base.KdsBaseForm;

public class WebPlaceConfigForm extends KdsBaseForm {

	private String groupid;
	private String mapLatitude;
	private String mapLongitude;
	private String mapZoomLevel;

	private String selectedPlaceId;
	private String selectedPlaceName;
	private String selectedPlaceTypeCd;
	private String selectedPlaceIcon;

	private String placeIdList;
	private String placeNameList;
	private String placeTypeList;
	private String placeIconIdList;
	private String pointList;
	private String radiusList;
	
	private String newPlaceName;
	private String newPlaceTypeCd;
	private String newPlaceIcon;
	private String newLatitude;
	private String newLongitude;
	private String newRadius;

	private String placeIconDataList;
	private String placeTypeColorList;

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
	 * @return mapLatitude
	 */
	public String getMapLatitude() {
		return mapLatitude;
	}
	/**
	 * @param mapLatitude セットする mapLatitude
	 */
	public void setMapLatitude(String mapLatitude) {
		this.mapLatitude = mapLatitude;
	}
	/**
	 * @return mapLongitude
	 */
	public String getMapLongitude() {
		return mapLongitude;
	}
	/**
	 * @param mapLongitude セットする mapLongitude
	 */
	public void setMapLongitude(String mapLongitude) {
		this.mapLongitude = mapLongitude;
	}
	/**
	 * @return mapZoomLevel
	 */
	public String getMapZoomLevel() {
		return mapZoomLevel;
	}
	/**
	 * @param mapZoomLevel セットする mapZoomLevel
	 */
	public void setMapZoomLevel(String mapZoomLevel) {
		this.mapZoomLevel = mapZoomLevel;
	}
	/**
	 * @return selectedPlaceId
	 */
	public String getSelectedPlaceId() {
		return selectedPlaceId;
	}
	/**
	 * @param selectedPlaceId セットする selectedPlaceId
	 */
	public void setSelectedPlaceId(String selectedPlaceId) {
		this.selectedPlaceId = selectedPlaceId;
	}
	/**
	 * @return selectedPlaceName
	 */
	public String getSelectedPlaceName() {
		return selectedPlaceName;
	}
	/**
	 * @param selectedPlaceName セットする selectedPlaceName
	 */
	public void setSelectedPlaceName(String selectedPlaceName) {
		this.selectedPlaceName = selectedPlaceName;
	}
	/**
	 * @return selectedPlaceTypeCd
	 */
	public String getSelectedPlaceTypeCd() {
		return selectedPlaceTypeCd;
	}
	/**
	 * @param selectedPlaceTypeCd セットする selectedPlaceTypeCd
	 */
	public void setSelectedPlaceTypeCd(String selectedPlaceTypeCd) {
		this.selectedPlaceTypeCd = selectedPlaceTypeCd;
	}
	/**
	 * @return selectedPlaceIcon
	 */
	public String getSelectedPlaceIcon() {
		return selectedPlaceIcon;
	}
	/**
	 * @param selectedPlaceIcon セットする selectedPlaceIcon
	 */
	public void setSelectedPlaceIcon(String selectedPlaceIcon) {
		this.selectedPlaceIcon = selectedPlaceIcon;
	}
	/**
	 * @return placeIdList
	 */
	public String getPlaceIdList() {
		return placeIdList;
	}
	/**
	 * @param placeIdList セットする placeIdList
	 */
	public void setPlaceIdList(String placeIdList) {
		this.placeIdList = placeIdList;
	}
	/**
	 * @return placeNameList
	 */
	public String getPlaceNameList() {
		return placeNameList;
	}
	/**
	 * @param placeNameList セットする placeNameList
	 */
	public void setPlaceNameList(String placeNameList) {
		this.placeNameList = placeNameList;
	}
	/**
	 * @return placeTypeList
	 */
	public String getPlaceTypeList() {
		return placeTypeList;
	}
	/**
	 * @param placeTypeList セットする placeTypeList
	 */
	public void setPlaceTypeList(String placeTypeList) {
		this.placeTypeList = placeTypeList;
	}
	/**
	 * @return placeIconIdList
	 */
	public String getPlaceIconIdList() {
		return placeIconIdList;
	}
	/**
	 * @param placeIconIdList セットする placeIconIdList
	 */
	public void setPlaceIconIdList(String placeIconIdList) {
		this.placeIconIdList = placeIconIdList;
	}
	/**
	 * @return pointList
	 */
	public String getPointList() {
		return pointList;
	}
	/**
	 * @param pointList セットする pointList
	 */
	public void setPointList(String pointList) {
		this.pointList = pointList;
	}
	/**
	 * @return radiusList
	 */
	public String getRadiusList() {
		return radiusList;
	}
	/**
	 * @param radiusList セットする radiusList
	 */
	public void setRadiusList(String radiusList) {
		this.radiusList = radiusList;
	}
	/**
	 * @return newPlaceName
	 */
	public String getNewPlaceName() {
		return newPlaceName;
	}
	/**
	 * @param newPlaceName セットする newPlaceName
	 */
	public void setNewPlaceName(String newPlaceName) {
		this.newPlaceName = newPlaceName;
	}
	/**
	 * @return newPlaceTypeCd
	 */
	public String getNewPlaceTypeCd() {
		return newPlaceTypeCd;
	}
	/**
	 * @param newPlaceTypeCd セットする newPlaceTypeCd
	 */
	public void setNewPlaceTypeCd(String newPlaceTypeCd) {
		this.newPlaceTypeCd = newPlaceTypeCd;
	}
	/**
	 * @return newPlaceIcon
	 */
	public String getNewPlaceIcon() {
		return newPlaceIcon;
	}
	/**
	 * @param newPlaceIcon セットする newPlaceIcon
	 */
	public void setNewPlaceIcon(String newPlaceIcon) {
		this.newPlaceIcon = newPlaceIcon;
	}
	/**
	 * @return newLatitude
	 */
	public String getNewLatitude() {
		return newLatitude;
	}
	/**
	 * @param newLatitude セットする newLatitude
	 */
	public void setNewLatitude(String newLatitude) {
		this.newLatitude = newLatitude;
	}
	/**
	 * @return newLongitude
	 */
	public String getNewLongitude() {
		return newLongitude;
	}
	/**
	 * @param newLongitude セットする newLongitude
	 */
	public void setNewLongitude(String newLongitude) {
		this.newLongitude = newLongitude;
	}
	/**
	 * @return newRadius
	 */
	public String getNewRadius() {
		return newRadius;
	}
	/**
	 * @param newRadius セットする newRadius
	 */
	public void setNewRadius(String newRadius) {
		this.newRadius = newRadius;
	}
	/**
	 * @return placeIconDataList
	 */
	public String getPlaceIconDataList() {
		return placeIconDataList;
	}
	/**
	 * @param placeIconDataList セットする placeIconDataList
	 */
	public void setPlaceIconDataList(String placeIconDataList) {
		this.placeIconDataList = placeIconDataList;
	}
	/**
	 * @return placeTypeColorList
	 */
	public String getPlaceTypeColorList() {
		return placeTypeColorList;
	}
	/**
	 * @param placeTypeColorList セットする placeTypeColorList
	 */
	public void setPlaceTypeColorList(String placeTypeColorList) {
		this.placeTypeColorList = placeTypeColorList;
	}
}
