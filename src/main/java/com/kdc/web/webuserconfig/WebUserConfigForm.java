package com.kdc.web.webuserconfig;

import org.springframework.web.multipart.MultipartFile;

import com.kdc.web.base.KdsBaseForm;

public class WebUserConfigForm extends KdsBaseForm {
	// 選択index(コンボ)
	private Integer selectedUser;
	private String cmbUser;
	private String selectedUserId;

	private String userId;
	// ユーザ名
	private String userName;
	// パスワード
	private String password;
	// アイコンID
	private String iconId;
	// アイコン画像文字列
	private String iconDataString;
	// グループID
	private String groupId;
	// 管理レベル
	private String authLevel;
	// ライン表示色透過度
	private String lineColorAlpha;
	// ライン表示色
	private String lineColor;
	// マーカー表示色
	private String markerColor;

	// アイコンファイル
	private String iconFileName;
	// ファイルアップロード用
	private MultipartFile iconImg;
	private String selectedIconDataString;
	
	/**
	 * @return selectedUser
	 */
	public Integer getSelectedUser() {
		return selectedUser;
	}

	/**
	 * @param selectedUser
	 *            セットする selectedUser
	 */
	public void setSelectedUser(Integer selectedUser) {
		this.selectedUser = selectedUser;
	}

	/**
	 * @return cmbUser
	 */
	public String getCmbUser() {
		return cmbUser;
	}

	/**
	 * @param cmbUser
	 *            セットする cmbUser
	 */
	public void setCmbUser(String cmbUser) {
		this.cmbUser = cmbUser;
	}

	/**
	 * @return selectedUserId
	 */
	public String getSelectedUserId() {
		return selectedUserId;
	}

	/**
	 * @param selectedUserId
	 *            セットする selectedUserId
	 */
	public void setSelectedUserId(String selectedUserId) {
		this.selectedUserId = selectedUserId;
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
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            セットする password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return iconId
	 */
	public String getIconId() {
		return iconId;
	}

	/**
	 * @param iconId
	 *            セットする iconId
	 */
	public void setIconId(String iconId) {
		this.iconId = iconId;
	}

	/**
	 * @return iconDataString
	 */
	public String getIconDataString() {
		return iconDataString;
	}

	/**
	 * @param iconDataString
	 *            セットする iconDataString
	 */
	public void setIconDataString(String iconDataString) {
		this.iconDataString = iconDataString;
	}

	/**
	 * @return groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            セットする groupId
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

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
	 * @return lineColorAlpha
	 */
	public String getLineColorAlpha() {
		return lineColorAlpha;
	}

	/**
	 * @param lineColorAlpha
	 *            セットする lineColorAlpha
	 */
	public void setLineColorAlpha(String lineColorAlpha) {
		this.lineColorAlpha = lineColorAlpha;
	}

	/**
	 * @return lineColor
	 */
	public String getLineColor() {
		return lineColor;
	}

	/**
	 * @param lineColor
	 *            セットする lineColor
	 */
	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}

	/**
	 * @return markerColor
	 */
	public String getMarkerColor() {
		return markerColor;
	}

	/**
	 * @param markerColor
	 *            セットする markerColor
	 */
	public void setMarkerColor(String markerColor) {
		this.markerColor = markerColor;
	}

	/**
	 * @return iconFileName
	 */
	public String getIconFileName() {
		return iconFileName;
	}

	/**
	 * @param iconFileName セットする iconFileName
	 */
	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
	}

	/**
	 * @return iconImg
	 */
	public MultipartFile getIconImg() {
		return iconImg;
	}

	/**
	 * @param iconImg
	 *            セットする iconImg
	 */
	public void setIconImg(MultipartFile iconImg) {
		this.iconImg = iconImg;
	}

	/**
	 * @return selectedIconDataString
	 */
	public String getSelectedIconDataString() {
		return selectedIconDataString;
	}

	/**
	 * @param selectedIconDataString セットする selectedIconDataString
	 */
	public void setSelectedIconDataString(String selectedIconDataString) {
		this.selectedIconDataString = selectedIconDataString;
	}

}
