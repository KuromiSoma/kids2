package com.kdc.web.webauthconfig;

import java.util.ArrayList;
import java.util.List;
import com.kdc.common.entity.db.PlaceMasterEntityWrapper;
import com.kdc.common.enums.AuthLevelEnum;

/**
 * 管理レベル別場所設定クラス
 */
public class PlaceAlertConfig {
	private String authLevel;
	private String authLevelLabel;
	private List<PlaceAlertConfigSub> alert;

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
	 * @return alert
	 */
	public List<PlaceAlertConfigSub> getAlert() {
		return alert;
	}

	/**
	 * @param alert
	 *            セットする alert
	 */
	public void setAlert(List<PlaceAlertConfigSub> alert) {
		this.alert = alert;
	}

	public static List<PlaceAlertConfig> getNewAuthPlaceConfigList(List<PlaceMasterEntityWrapper> placeList) {
		// 管理レベル別の設定情報格納領域を作成
		List<PlaceAlertConfig> dataList = new ArrayList<>();
		for (AuthLevelEnum item : AuthLevelEnum.values()) {
			PlaceAlertConfig data = new PlaceAlertConfig();
			data.setAuthLevel(item.getCodeString());
			data.setAuthLevelLabel(item.getLabel());
			data.setAlert(PlaceAlertConfigSub.getNewPlaceAlertConfigList(item, placeList));
			dataList.add(data);
		}
		return dataList;
	}
}