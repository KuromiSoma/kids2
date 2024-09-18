package com.kdc.web.webauthconfig;

import java.util.ArrayList;
import java.util.List;

import com.kdc.common.enums.AuthLevelEnum;

/**
 * 管理レベル別ユーザ表示設定クラス
 */
public class UserDispConfig {
	private boolean changed;
	private String authLevel;
	private String authLevelLabel;
	private boolean locationDispFlg;
	private boolean movingDistanceDispFlg;
	private boolean batteryLevelDispFlg;
	private boolean accessTimeDispFlg;
	private boolean receptionStatusDispFlg;

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
	 * @return locationDispFlg
	 */
	public boolean getLocationDispFlg() {
		return locationDispFlg;
	}

	/**
	 * @param locationDispFlg
	 *            セットする locationDispFlg
	 */
	public void setLocationDispFlg(boolean locationDispFlg) {
		this.locationDispFlg = locationDispFlg;
	}

	/**
	 * @return movingDistanceDispFlg
	 */
	public boolean getMovingDistanceDispFlg() {
		return movingDistanceDispFlg;
	}

	/**
	 * @param movingDistanceDispFlg
	 *            セットする movingDistanceDispFlg
	 */
	public void setMovingDistanceDispFlg(boolean movingDistanceDispFlg) {
		this.movingDistanceDispFlg = movingDistanceDispFlg;
	}

	/**
	 * @return batteryLevelDispFlg
	 */
	public boolean getBatteryLevelDispFlg() {
		return batteryLevelDispFlg;
	}

	/**
	 * @param batteryLevelDispFlg
	 *            セットする batteryLevelDispFlg
	 */
	public void setBatteryLevelDispFlg(boolean batteryLevelDispFlg) {
		this.batteryLevelDispFlg = batteryLevelDispFlg;
	}

	/**
	 * @return accessTimeDispFlg
	 */
	public boolean getAccessTimeDispFlg() {
		return accessTimeDispFlg;
	}

	/**
	 * @param accessTimeDispFlg
	 *            セットする accessTimeDispFlg
	 */
	public void setAccessTimeDispFlg(boolean accessTimeDispFlg) {
		this.accessTimeDispFlg = accessTimeDispFlg;
	}

	/**
	 * @return receptionStatusDispFlg
	 */
	public boolean getReceptionStatusDispFlg() {
		return receptionStatusDispFlg;
	}

	/**
	 * @param receptionStatusDispFlg
	 *            セットする receptionStatusDispFlg
	 */
	public void setReceptionStatusDispFlg(boolean receptionStatusDispFlg) {
		this.receptionStatusDispFlg = receptionStatusDispFlg;
	}

	public static List<UserDispConfig> getNewAuthUserConfigList() {
		// 管理レベル別の設定情報格納領域を作成
		List<UserDispConfig> dataList = new ArrayList<>();
		for (AuthLevelEnum item : AuthLevelEnum.values()) {
			UserDispConfig data = new UserDispConfig();
			data.setAuthLevel(item.getCodeString());
			data.setAuthLevelLabel(item.getLabel());
			dataList.add(data);
		}
		return dataList;
	}
}