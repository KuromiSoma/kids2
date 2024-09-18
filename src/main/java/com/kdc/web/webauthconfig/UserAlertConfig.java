package com.kdc.web.webauthconfig;

import java.util.ArrayList;
import java.util.List;

import com.kdc.common.enums.AuthLevelEnum;

/**
 * 管理レベル別ユーザ通知設定クラス
 */
public class UserAlertConfig {
	private boolean changed;
	private String authLevel;
	private String authLevelLabel;
	private boolean batteryLevelAlertFlg;
	private String batteryNotification;
	private boolean batteryPopup;
//	private String batteryAlarmTime;
	private String batteryVibrationTime;
	private String batteryReference;
	private boolean connectionAlertFlg;
	private String connectionNotification;
	private boolean connectionPopup;
//	private String connectionAlarmTime;
	private String connectionVibrationTime;
	private boolean sosAlertFlg;
	private String sosNotification;
	private boolean sosPopup;
//	private String sosAlarmTime;
	private String sosVibrationTime;

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
	 * @return authLevelLabel
	 */
	public String getAuthLevelLabel() {
		return authLevelLabel;
	}

	/**
	 * @param authLevelLabel
	 *            セットする authLevelLabel
	 */
	public void setAuthLevelLabel(String authLevelLabel) {
		this.authLevelLabel = authLevelLabel;
	}

	/**
	 * @return batteryLevelAlertFlg
	 */
	public boolean getBatteryLevelAlertFlg() {
		return batteryLevelAlertFlg;
	}

	/**
	 * @param batteryLevelAlertFlg
	 *            セットする batteryLevelAlertFlg
	 */
	public void setBatteryLevelAlertFlg(boolean batteryLevelAlertFlg) {
		this.batteryLevelAlertFlg = batteryLevelAlertFlg;
	}

	/**
	 * @return batteryNotification
	 */
	public String getBatteryNotification() {
		return batteryNotification;
	}

	/**
	 * @param batteryNotification
	 *            セットする batteryNotification
	 */
	public void setBatteryNotification(String batteryNotification) {
		this.batteryNotification = batteryNotification;
	}

	/**
	 * @return batteryPopup
	 */
	public boolean getBatteryPopup() {
		return batteryPopup;
	}

	/**
	 * @param batteryPopup
	 *            セットする batteryPopup
	 */
	public void setBatteryPopup(boolean batteryPopup) {
		this.batteryPopup = batteryPopup;
	}

//	/**
//	 * @return batteryAlarmTime
//	 */
//	public String getBatteryAlarmTime() {
//		return batteryAlarmTime;
//	}
//
//	/**
//	 * @param batteryAlarmTime
//	 *            セットする batteryAlarmTime
//	 */
//	public void setBatteryAlarmTime(String batteryAlarmTime) {
//		this.batteryAlarmTime = batteryAlarmTime;
//	}

	/**
	 * @return batteryVibrationTime
	 */
	public String getBatteryVibrationTime() {
		return batteryVibrationTime;
	}

	/**
	 * @param batteryVibrationTime
	 *            セットする batteryVibrationTime
	 */
	public void setBatteryVibrationTime(String batteryVibrationTime) {
		this.batteryVibrationTime = batteryVibrationTime;
	}

	/**
	 * @return batteryReference
	 */
	public String getBatteryReference() {
		return batteryReference;
	}

	/**
	 * @param batteryReference
	 *            セットする batteryReference
	 */
	public void setBatteryReference(String batteryReference) {
		this.batteryReference = batteryReference;
	}

	/**
	 * @return connectionAlertFlg
	 */
	public boolean getConnectionAlertFlg() {
		return connectionAlertFlg;
	}

	/**
	 * @param connectionAlertFlg
	 *            セットする connectionAlertFlg
	 */
	public void setConnectionAlertFlg(boolean connectionAlertFlg) {
		this.connectionAlertFlg = connectionAlertFlg;
	}

	/**
	 * @return connectionNotification
	 */
	public String getConnectionNotification() {
		return connectionNotification;
	}

	/**
	 * @param connectionNotification
	 *            セットする connectionNotification
	 */
	public void setConnectionNotification(String connectionNotification) {
		this.connectionNotification = connectionNotification;
	}

	/**
	 * @return connectionPopup
	 */
	public boolean getConnectionPopup() {
		return connectionPopup;
	}

	/**
	 * @param connectionPopup
	 *            セットする connectionPopup
	 */
	public void setConnectionPopup(boolean connectionPopup) {
		this.connectionPopup = connectionPopup;
	}

//	/**
//	 * @return connectionAlarmTime
//	 */
//	public String getConnectionAlarmTime() {
//		return connectionAlarmTime;
//	}
//
//	/**
//	 * @param connectionAlarmTime
//	 *            セットする connectionAlarmTime
//	 */
//	public void setConnectionAlarmTime(String connectionAlarmTime) {
//		this.connectionAlarmTime = connectionAlarmTime;
//	}

	/**
	 * @return connectionVibrationTime
	 */
	public String getConnectionVibrationTime() {
		return connectionVibrationTime;
	}

	/**
	 * @param connectionVibrationTime
	 *            セットする connectionVibrationTime
	 */
	public void setConnectionVibrationTime(String connectionVibrationTime) {
		this.connectionVibrationTime = connectionVibrationTime;
	}

	/**
	 * @return sosAlertFlg
	 */
	public boolean getSosAlertFlg() {
		return sosAlertFlg;
	}

	/**
	 * @param sosAlertFlg
	 *            セットする sosAlertFlg
	 */
	public void setSosAlertFlg(boolean sosAlertFlg) {
		this.sosAlertFlg = sosAlertFlg;
	}

	/**
	 * @return sosNotification
	 */
	public String getSosNotification() {
		return sosNotification;
	}

	/**
	 * @param sosNotification
	 *            セットする sosNotification
	 */
	public void setSosNotification(String sosNotification) {
		this.sosNotification = sosNotification;
	}

	/**
	 * @return sosPopup
	 */
	public boolean getSosPopup() {
		return sosPopup;
	}

	/**
	 * @param sosPopup
	 *            セットする sosPopup
	 */
	public void setSosPopup(boolean sosPopup) {
		this.sosPopup = sosPopup;
	}

//	/**
//	 * @return sosAlarmTime
//	 */
//	public String getSosAlarmTime() {
//		return sosAlarmTime;
//	}
//
//	/**
//	 * @param sosAlarmTime
//	 *            セットする sosAlarmTime
//	 */
//	public void setSosAlarmTime(String sosAlarmTime) {
//		this.sosAlarmTime = sosAlarmTime;
//	}

	/**
	 * @return sosVibrationTime
	 */
	public String getSosVibrationTime() {
		return sosVibrationTime;
	}

	/**
	 * @param sosVibrationTime
	 *            セットする sosVibrationTime
	 */
	public void setSosVibrationTime(String sosVibrationTime) {
		this.sosVibrationTime = sosVibrationTime;
	}

	public static List<UserAlertConfig> getNewAuthUserConfigList() {
		// 管理レベル別の設定情報格納領域を作成
		List<UserAlertConfig> dataList = new ArrayList<>();
		for (AuthLevelEnum item : AuthLevelEnum.values()) {
			UserAlertConfig data = new UserAlertConfig();
			data.setAuthLevel(item.getCodeString());
			data.setAuthLevelLabel(item.getLabel());
			dataList.add(data);
		}
		return dataList;
	}
}