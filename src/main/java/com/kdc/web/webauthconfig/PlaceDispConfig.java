package com.kdc.web.webauthconfig;

import java.util.ArrayList;
import java.util.List;

import com.kdc.common.entity.db.PlaceMasterEntity;
import com.kdc.common.entity.db.PlaceMasterEntityWrapper;

/**
 * 場所表示設定クラス
 */
public class PlaceDispConfig {
	private boolean changed;
	private String placeId;
	private String placeName;
	private boolean placeDispFlg;

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
	 * @return placeDispFlg
	 */
	public boolean getPlaceDispFlg() {
		return placeDispFlg;
	}

	/**
	 * @param placeDispFlg
	 *            セットする placeDispFlg
	 */
	public void setPlaceDispFlg(boolean placeDispFlg) {
		this.placeDispFlg = placeDispFlg;
	}

	public static List<PlaceDispConfig> getNewPlaceDispConfigList(List<PlaceMasterEntityWrapper> placeList) {
		// 場所ID別の設定情報格納領域を作成
		List<PlaceDispConfig> dataList = new ArrayList<>();
		for (PlaceMasterEntity placeEntity : placeList) {
			PlaceDispConfig data = new PlaceDispConfig();
			data.setPlaceId(placeEntity.getPlaceid());
			dataList.add(data);
		}
		return dataList;
	}
}