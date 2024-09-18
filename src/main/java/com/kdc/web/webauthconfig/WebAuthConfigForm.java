package com.kdc.web.webauthconfig;

import java.util.List;
import java.util.Map;
import com.kdc.common.entity.db.PlaceMasterEntityWrapper;
import com.kdc.web.base.KdsBaseForm;

public class WebAuthConfigForm extends KdsBaseForm {

	// 表示中の管理グループ
	private String currentGroupId;
	// 新管理グループ名
	private String newGroupName;
	// 管理グループコンボボックス
	private String cmbAuthGroup;
	private Map<String,String> cmbGroup;
	
	// 表示中の管理レベル
	private String currentAuthLevel;
	// 管理レベルコンボボックス
	private String cmbAuthLevel;
	// 表示ブロック
	private String dispBlock;
	private String nextDispBlock;

	// 場所リスト
	private List<PlaceMasterEntityWrapper> places;
	private int placeCount;

	// ユーザ表示設定リスト
	private List<UserDispConfig> userDisp;

	// ユーザ通知設定リスト
	private List<UserAlertConfig> userAlert;

	// 場所表示設定リスト
	private List<PlaceDispConfig> placeDisp;
	// 場所設定リスト（通知先別）
	private List<PlaceAlertConfig> placeAlert;

	// 送信間隔設定リスト
	private List<TransmissionConfig> trans;

	// 管理レベル別ユーザリスト
	private List<AuthUser> thisLevelUser;
	private List<AuthUser> otherLevelUser;

	// SOSカウントダウン秒数
	private String sosCountdown;
	// 接続切断と判断する時間
	private String disconnectTime;
	// 管理グループ名
	private String groupName;

	/**
	 * @return currentAuthLevel
	 */
	public String getCurrentAuthLevel() {
		return currentAuthLevel;
	}

	/**
	 * @param currentAuthLevel
	 *            セットする currentAuthLevel
	 */
	public void setCurrentAuthLevel(String currentAuthLevel) {
		this.currentAuthLevel = currentAuthLevel;
	}

	/**
	 * @return cmbAuthLevel
	 */
	public String getCmbAuthLevel() {
		return cmbAuthLevel;
	}

	/**
	 * @param cmbAuthLevel
	 *            セットする cmbAuthLevel
	 */
	public void setCmbAuthLevel(String cmbAuthLevel) {
		this.cmbAuthLevel = cmbAuthLevel;
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
	 * @return nextDispBlock
	 */
	public String getNextDispBlock() {
		return nextDispBlock;
	}

	/**
	 * @param nextDispBlock
	 *            セットする nextDispBlock
	 */
	public void setNextDispBlock(String nextDispBlock) {
		this.nextDispBlock = nextDispBlock;
	}

	/**
	 * @return places
	 */
	public List<PlaceMasterEntityWrapper> getPlaces() {
		return places;
	}

	/**
	 * @param places
	 *            セットする places
	 */
	public void setPlaces(List<PlaceMasterEntityWrapper> places) {
		this.places = places;
	}

	/**
	 * @return placeCount
	 */
	public int getPlaceCount() {
		return placeCount;
	}

	/**
	 * @param placeCount
	 *            セットする placeCount
	 */
	public void setPlaceCount(int placeCount) {
		this.placeCount = placeCount;
	}

	/**
	 * @return userDisp
	 */
	public List<UserDispConfig> getUserDisp() {
		return userDisp;
	}

	/**
	 * @param userDisp
	 *            セットする userDisp
	 */
	public void setUserDisp(List<UserDispConfig> userDisp) {
		this.userDisp = userDisp;
	}

	/**
	 * @return userAlert
	 */
	public List<UserAlertConfig> getUserAlert() {
		return userAlert;
	}

	/**
	 * @param userAlert
	 *            セットする userAlert
	 */
	public void setUserAlert(List<UserAlertConfig> userAlert) {
		this.userAlert = userAlert;
	}

	/**
	 * @return placeDisp
	 */
	public List<PlaceDispConfig> getPlaceDisp() {
		return placeDisp;
	}

	/**
	 * @param placeDisp
	 *            セットする placeDisp
	 */
	public void setPlaceDisp(List<PlaceDispConfig> placeDisp) {
		this.placeDisp = placeDisp;
	}

	/**
	 * @return placeAlert
	 */
	public List<PlaceAlertConfig> getPlaceAlert() {
		return placeAlert;
	}

	/**
	 * @param placeAlert
	 *            セットする placeAlert
	 */
	public void setPlaceAlert(List<PlaceAlertConfig> placeAlert) {
		this.placeAlert = placeAlert;
	}

	/**
	 * @return trans
	 */
	public List<TransmissionConfig> getTrans() {
		return trans;
	}

	/**
	 * @param trans
	 *            セットする trans
	 */
	public void setTrans(List<TransmissionConfig> trans) {
		this.trans = trans;
	}

	/**
	 * @return thisLevelUser
	 */
	public List<AuthUser> getThisLevelUser() {
		return thisLevelUser;
	}

	/**
	 * @param thisLevelUser セットする thisLevelUser
	 */
	public void setThisLevelUser(List<AuthUser> thisLevelUser) {
		this.thisLevelUser = thisLevelUser;
	}

	/**
	 * @return otherLevelUser
	 */
	public List<AuthUser> getOtherLevelUser() {
		return otherLevelUser;
	}

	/**
	 * @param otherLevelUser セットする otherLevelUser
	 */
	public void setOtherLevelUser(List<AuthUser> otherLevelUser) {
		this.otherLevelUser = otherLevelUser;
	}

	/**
	 * @return sosCountdown
	 */
	public String getSosCountdown() {
		return sosCountdown;
	}

	/**
	 * @param sosCountdown
	 *            セットする sosCountdown
	 */
	public void setSosCountdown(String sosCountdown) {
		this.sosCountdown = sosCountdown;
	}

	/**
	 * @return disconnectTime
	 */
	public String getDisconnectTime() {
		return disconnectTime;
	}

	/**
	 * @param disconnectTime
	 *            セットする disconnectTime
	 */
	public void setDisconnectTime(String disconnectTime) {
		this.disconnectTime = disconnectTime;
	}

	/**
	 * @return currentAuthGroup
	 */
	public String getCurrentGroupId() {
		return currentGroupId;
	}

	/**
	 * @param currentGroupId
	 *            セットする currentGroupId
	 */
	public void setCurrentGroupId(String currentGroupId) {
		this.currentGroupId = currentGroupId;
	}

	/**
	 * @return cmbAuthGroup
	 */
	public String getCmbAuthGroup() {
		return cmbAuthGroup;
	}

	/**
	 * @param cmbAuthGroup
	 *            セットする cmbAuthGroup
	 */
	public void setCmbAuthGroup(String cmbAuthGroup) {
		this.cmbAuthGroup = cmbAuthGroup;
	}

	/**
	 * @return cmbGroup
	 */
	public Map<String,String> getCmbGroup() {
		return cmbGroup;
	}

	/**
	 * @param cmbGroup
	 *            セットする cmbGroup
	 */
	public void setCmbGroup(Map<String,String> cmbGroup) {
		this.cmbGroup = cmbGroup;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getNewGroupName() {
		return newGroupName;
	}

	public void setNewGroupName(String newGroupName) {
		this.newGroupName = newGroupName;
	}

}
