package com.kdc.common.enums;

import com.kdc.common.util.CommonConst;

/**
 * 場所種別Enum
 */
public enum PlaceTypeEnum {
	/** 通常場所 */
	NORMAL(CommonConst.PLACE_TYPE_NORMAL, "#3CB371", "#006400"),
	/** 危険な場所 */
	DANGER(CommonConst.PLACE_TYPE_DANGER, "#FF0000", "#DC143C");

	final int code;
	final String circleColor;
	final String editColor;

	private PlaceTypeEnum(int code, String circleColor, String editColor) {
		this.code = code;
		this.circleColor = circleColor;
		this.editColor = editColor;
	}

	public int getCode() {
		return code;
	}

	public String getCircleColor() {
		return circleColor;
	}

	public String getEditColor() {
		return editColor;
	}

	public static PlaceTypeEnum valueOf(int rm) {
		switch (rm) {

		case CommonConst.PLACE_TYPE_NORMAL:
			return NORMAL;

		case CommonConst.PLACE_TYPE_DANGER:
			return DANGER;

		default:
			throw new IllegalArgumentException("argument out of range");
		}
	}

}
