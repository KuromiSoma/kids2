package com.kdc.common.util;

/**
 * アイコン画像データクラス.
 */
public class IconDataSet {

	private String iconName;
	private byte[] iconData;

	/**
	 * @return iconName
	 */
	public String getIconName() {
		return iconName;
	}

	/**
	 * @param iconName
	 *            セットする iconName
	 */
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	/**
	 * @return iconData
	 */
	public byte[] getIconData() {
		return iconData;
	}

	/**
	 * @param iconData
	 *            セットする iconData
	 */
	public void setIconData(byte[] iconData) {
		this.iconData = iconData;
	}
}
