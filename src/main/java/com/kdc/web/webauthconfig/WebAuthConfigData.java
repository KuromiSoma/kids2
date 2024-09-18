package com.kdc.web.webauthconfig;

import java.util.List;
import org.springframework.stereotype.Component;
import com.kdc.common.entity.db.PlaceMasterEntityWrapper;

@Component
public class WebAuthConfigData {

	// 場所リスト
	private List<PlaceMasterEntityWrapper> placeList;

	// 設定テンプレートリスト
	private List<UserDispConfig> userDisp;
	private List<UserAlertConfig> userAlert;
	private List<PlaceDispConfig> placeDisp;
	private List<PlaceAlertConfig> placeAlert;
	private List<TransmissionConfig> trans;

	// 管理レベル別ユーザリスト
	private List<AuthUser> thisLevelUser;
	private List<AuthUser> otherLevelUser;

	/**
	 * @return placeList
	 */
	public List<PlaceMasterEntityWrapper> getPlaceList() {
		return placeList;
	}

	/**
	 * @param placeList
	 *            セットする placeList
	 */
	public void setPlaceList(List<PlaceMasterEntityWrapper> placeList) {
		this.placeList = placeList;
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
	 * @param thisLevelUser
	 *            セットする thisLevelUser
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
	 * @param otherLevelUser
	 *            セットする otherLevelUser
	 */
	public void setOtherLevelUser(List<AuthUser> otherLevelUser) {
		this.otherLevelUser = otherLevelUser;
	}

}
