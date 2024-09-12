package com.kdc.common.enums;

import com.kdc.common.util.CommonConst;

/**
 * 電波状況Enum
 */
public enum ReceptionStatusEnum {
	/** Wi-Fi */
	WIFI(CommonConst.RECEPTION_STATUS_WIFI, "Wi-Fi"),
	/** LTE */
	LTE(CommonConst.RECEPTION_STATUS_LTE, "LTE"),
	/** 4G */
	_4G(CommonConst.RECEPTION_STATUS_4G, "4G"),
	/** 3G */
	_3G(CommonConst.RECEPTION_STATUS_3G, "3G"),
	/** 5G */
	_5G(CommonConst.RECEPTION_STATUS_5G, "5G"),
	/** WiMAX */
	WIMAX(CommonConst.RECEPTION_STATUS_WIMAX, "WiMAX");

	final int code;
	final String label;

	private ReceptionStatusEnum(int code, String label) {
		this.code = code;
		this.label = label;
	}

	public int getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public static ReceptionStatusEnum valueOf(int rm) {
		switch (rm) {

		case CommonConst.RECEPTION_STATUS_WIFI:
			return WIFI;

		case CommonConst.RECEPTION_STATUS_LTE:
			return LTE;

		case CommonConst.RECEPTION_STATUS_4G:
			return _4G;

		case CommonConst.RECEPTION_STATUS_3G:
			return _3G;

		case CommonConst.RECEPTION_STATUS_5G:
			return _5G;

		case CommonConst.RECEPTION_STATUS_WIMAX:
			return WIMAX;

		default:
			throw new IllegalArgumentException("argument out of range");
		}
	}
}
