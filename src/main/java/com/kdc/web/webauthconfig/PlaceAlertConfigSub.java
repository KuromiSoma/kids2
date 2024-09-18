package com.kdc.web.webauthconfig;

import java.util.ArrayList;
import java.util.List;
import com.kdc.common.entity.db.PlaceMasterEntity;
import com.kdc.common.entity.db.PlaceMasterEntityWrapper;
import com.kdc.common.enums.AuthLevelEnum;

/**
 * 場所通知設定クラス
 */
public class PlaceAlertConfigSub {
	private boolean changed;
	private String groupid;
	private String placeId;
	private String placeName;
	private boolean placeAlertFlg;
	private String placeNotification;
	private boolean placePopup;
	// private String placeAlarmTime;
	private String placeVibrationTime;

	/**
	 * @return changed
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * @param changed
	 *            セットする changed
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}


	/**
	 * @return placeId
	 */
	public String getPlaceId() {
		return placeId;
	}

	/**
	 * @param placeId
	 *            セットする placeId
	 */
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	/**
	 * @return placeName
	 */
	public String getPlaceName() {
		return placeName;
	}

	/**
	 * @param placeName
	 *            セットする placeName
	 */
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	/**
	 * @return placeAlertFlg
	 */
	public boolean getPlaceAlertFlg() {
		return placeAlertFlg;
	}

	/**
	 * @param placeAlertFlg
	 *            セットする placeAlertFlg
	 */
	public void setPlaceAlertFlg(boolean placeAlertFlg) {
		this.placeAlertFlg = placeAlertFlg;
	}

	/**
	 * @return placeNotification
	 */
	public String getPlaceNotification() {
		return placeNotification;
	}

	/**
	 * @param placeNotification
	 *            セットする placeNotification
	 */
	public void setPlaceNotification(String placeNotification) {
		this.placeNotification = placeNotification;
	}

	/**
	 * @return placePopup
	 */
	public boolean getPlacePopup() {
		return placePopup;
	}

	/**
	 * @param placePopup
	 *            セットする placePopup
	 */
	public void setPlacePopup(boolean placePopup) {
		this.placePopup = placePopup;
	}

	// /**
	// * @return placeAlarmTime
	// */
	// public String getPlaceAlarmTime() {
	// return placeAlarmTime;
	// }
	//
	// /**
	// * @param placeAlarmTime
	// * セットする placeAlarmTime
	// */
	// public void setPlaceAlarmTime(String placeAlarmTime) {
	// this.placeAlarmTime = placeAlarmTime;
	// }

	/**
	 * @return placeVibrationTime
	 */
	public String getPlaceVibrationTime() {
		return placeVibrationTime;
	}

	/**
	 * @param placeVibrationTime
	 *            セットする placeVibrationTime
	 */
	public void setPlaceVibrationTime(String placeVibrationTime) {
		this.placeVibrationTime = placeVibrationTime;
	}

	public static List<PlaceAlertConfigSub> getNewPlaceAlertConfigList(AuthLevelEnum authLevel,
			List<PlaceMasterEntityWrapper> placeList) {
		// 場所ID別の設定情報格納領域を作成
		List<PlaceAlertConfigSub> dataList = new ArrayList<>();
		for (PlaceMasterEntity placeEntity : placeList) {
			PlaceAlertConfigSub data = new PlaceAlertConfigSub();
			data.setPlaceId(placeEntity.getPlaceid());
			data.setPlaceName(placeEntity.getPlacename());
			dataList.add(data);
		}
		return dataList;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
}