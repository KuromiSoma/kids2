package com.kdc.web.webrecord;

import com.kdc.common.entity.api.base.UserInfoEntity;
import com.kdc.web.base.KdsBaseForm;

public class WebRecordForm extends KdsBaseForm {
	UserInfoEntity userInfo;

	private String dispBlock;
	private String receiveDate;

	private String userId;
	private String mapLatitude;
	private String mapLongitude;
	private String mapZoomLevel;

	private String userName;
	private String viewDate;
	private String date;

	private String batterys;

	private String markerListAll;
	private String markerListLatest;

	private String infoWindowList;
	private String iconList;
	private String lineColorList;
	private String lineOpacityList;
	private String markerColorList;
	private String selectMarker;
	private String recordCount;

	/**
	 * @return userInfo
	 */
	public UserInfoEntity getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo
	 *            セットする userInfo
	 */
	public void setUserInfo(UserInfoEntity userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return dispBlock
	 */
	public String getDispBlock() {
		return dispBlock;
	}

	/**
	 * @param dispBlock
	 *            セットする dispBlock
	 */
	public void setDispBlock(String dispBlock) {
		this.dispBlock = dispBlock;
	}

	/**
	 * @return receiveDate
	 */
	public String getReceiveDate() {
		return receiveDate;
	}

	/**
	 * @param receiveDate
	 *            セットする receiveDate
	 */
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            セットする userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return mapLatitude
	 */
	public String getMapLatitude() {
		return mapLatitude;
	}

	/**
	 * @param mapLatitude
	 *            セットする mapLatitude
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
	 * @param mapLongitude
	 *            セットする mapLongitude
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
	 * @param mapZoomLevel
	 *            セットする mapZoomLevel
	 */
	public void setMapZoomLevel(String mapZoomLevel) {
		this.mapZoomLevel = mapZoomLevel;
	}

	/**
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            セットする userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return viewDate
	 */
	public String getViewDate() {
		return viewDate;
	}

	/**
	 * @param viewDate
	 *            セットする viewDate
	 */
	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}

	/**
	 * @return date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            セットする date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return batterys
	 */
	public String getBatterys() {
		return batterys;
	}

	/**
	 * @param batterys
	 *            セットする batterys
	 */
	public void setBatterys(String batterys) {
		this.batterys = batterys;
	}

	/**
	 * @return markerListAll
	 */
	public String getMarkerListAll() {
		return markerListAll;
	}

	/**
	 * @param markerListAll
	 *            セットする markerListAll
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
	 * @param markerListLatest
	 *            セットする markerListLatest
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
	 * @param infoWindowList
	 *            セットする infoWindowList
	 */
	public void setInfoWindowList(String infoWindowList) {
		this.infoWindowList = infoWindowList;
	}

	/**
	 * @return iconList
	 */
	public String getIconList() {
		return iconList;
	}

	/**
	 * @param iconList
	 *            セットする iconList
	 */
	public void setIconList(String iconList) {
		this.iconList = iconList;
	}

	/**
	 * @return lineColorList
	 */
	public String getLineColorList() {
		return lineColorList;
	}

	/**
	 * @param lineColorList
	 *            セットする lineColorList
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
	 * @param lineOpacityList
	 *            セットする lineOpacityList
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
	 * @param markerColorList
	 *            セットする markerColorList
	 */
	public void setMarkerColorList(String markerColorList) {
		this.markerColorList = markerColorList;
	}

	/**
	 * @return selectMarker
	 */
	public String getSelectMarker() {
		return selectMarker;
	}

	/**
	 * @param selectMarker
	 *            セットする selectMarker
	 */
	public void setSelectMarker(String selectMarker) {
		this.selectMarker = selectMarker;
	}

	/**
	 * @return recordCount
	 */
	public String getRecordCount() {
		return recordCount;
	}

	/**
	 * @param recordCount
	 *            セットする recordCount
	 */
	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}
}
