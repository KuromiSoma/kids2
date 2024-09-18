package com.kdc.api.geticon;

import com.kdc.common.entity.api.base.ResponseBaseEntity;

public class GetIconResponseEntity extends ResponseBaseEntity {

	private String iconId;
	private String iconFile; // BASE64でデータ送付

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
	 * @return iconFile
	 */
	public String getIconFile() {
		return iconFile;
	}

	/**
	 * @param iconFile
	 *            セットする iconFile
	 */
	public void setIconFile(String iconFile) {
		this.iconFile = iconFile;
	}

}
