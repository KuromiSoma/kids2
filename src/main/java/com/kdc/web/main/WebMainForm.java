package com.kdc.web.main;

import java.util.List;
import java.util.Map;
import com.kdc.common.entity.web.WebUserViewEntity;
import com.kdc.web.base.KdsBaseForm;

public class WebMainForm extends KdsBaseForm {

	private String mapLatitude;
	private String mapLongitude;
	private String mapZoomLevel;

	private String placeNameList;
	private String placeTypeList;
	private String placeIconIdList;
	private String pointList;
	private String radiusList;

	private String placeIconDataList;
	private String placeTypeColorList;

	private String markerListAll;
	private String markerListLatest;

	private String infoWindowList;
	private String userIconList;
	private String lineColorList;
	private String lineOpacityList;
	private String markerColorList;
	private String selectUserIndex;
	private String userLineDisp;

	private String userIdList;
	private List<WebUserViewEntity> userList;
	// 管理レベルコンボボックス
	private String cmbGroupName;
	private Map<String,String> cmbGroup;
	// 表示中のグループID
	private String currentGroupId;

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
	/**
	 * @return markerListAll
	 */
	public String getMarkerListAll() {
		return markerListAll;
	}
	/**
	 * @param markerListAll セットする markerListAll
	 */
	public void setMarkerListAll(String markerListAll) {
		this.markerListAll = markerListAll;
	}
	/**
	 * @return markerListLatest
	 */
	public String getMarkerListLatest() {
		return markerListLatest;
	}
	/**
	 * @param markerListLatest セットする markerListLatest
	 */
	public void setMarkerListLatest(String markerListLatest) {
		this.markerListLatest = markerListLatest;
	}
	/**
	 * @return infoWindowList
	 */
	public String getInfoWindowList() {
		return infoWindowList;
	}
	/**
	 * @param infoWindowList セットする infoWindowList
	 */
	public void setInfoWindowList(String infoWindowList) {
		this.infoWindowList = infoWindowList;
	}
	/**
	 * @return userIconList
	 */
	public String getUserIconList() {
		return userIconList;
	}
	/**
	 * @param userIconList セットする userIconList
	 */
	public void setUserIconList(String userIconList) {
		this.userIconList = userIconList;
	}
	/**
	 * @return lineColorList
	 */
	public String getLineColorList() {
		return lineColorList;
	}
	/**
	 * @param lineColorList セットする lineColorList
	 */
	public void setLineColorList(String lineColorList) {
		this.lineColorList = lineColorList;
	}
	/**
	 * @return lineOpacityList
	 */
	public String getLineOpacityList() {
		return lineOpacityList;
	}
	/**
	 * @param lineOpacityList セットする lineOpacityList
	 */
	public void setLineOpacityList(String lineOpacityList) {
		this.lineOpacityList = lineOpacityList;
	}
	/**
	 * @return markerColorList
	 */
	public String getMarkerColorList() {
		return markerColorList;
	}
	/**
	 * @param markerColorList セットする markerColorList
	 */
	public void setMarkerColorList(String markerColorList) {
		this.markerColorList = markerColorList;
	}
	/**
	 * @return selectUserIndex
	 */
	public String getSelectUserIndex() {
		return selectUserIndex;
	}
	/**
	 * @param selectUserIndex セットする selectUserIndex
	 */
	public void setSelectUserIndex(String selectUserIndex) {
		this.selectUserIndex = selectUserIndex;
	}
	/**
	 * @return userLineDisp
	 */
	public String getUserLineDisp() {
		return userLineDisp;
	}
	/**
	 * @param userLineDisp セットする userLineDisp
	 */
	public void setUserLineDisp(String userLineDisp) {
		this.userLineDisp = userLineDisp;
	}
	/**
	 * @return userIdList
	 */
	public String getUserIdList() {
		return userIdList;
	}
	/**
	 * @param userIdList セットする userIdList
	 */
	public void setUserIdList(String userIdList) {
		this.userIdList = userIdList;
	}
	/**
	 * @return userList
	 */
	public List<WebUserViewEntity> getUserList() {
		return userList;
	}
	/**
	 * @param userList セットする userList
	 */
	public void setUserList(List<WebUserViewEntity> userList) {
		this.userList = userList;
	}

	/**
	 * @return cmbGroup
	 */
	public Map<String,String> getCmbGroup() {
		return cmbGroup;
	}
	/**
	 * @param cmbGroup セットする cmbGroup
	 */
	public void setCmbGroup(Map<String,String> cmbGroup) {
		this.cmbGroup = cmbGroup;
	}

	/**
	 * @return currentGroupId
	 */
	public String getCurrentGroupId() {
		return currentGroupId;
	}
	/**
	 * @param currentGroupId セットする currentGroupId
	 */
	public void setCurrentGroupId(String currentGroupId) {
		this.currentGroupId = currentGroupId;
	}
	public String getCmbGroupName() {
		return cmbGroupName;
	}
	public void setCmbGroupName(String cmbGroupName) {
		this.cmbGroupName = cmbGroupName;
	}
}