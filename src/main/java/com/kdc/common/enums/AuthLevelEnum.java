package com.kdc.common.enums;

import com.kdc.common.util.CommonConst;

/**
 * 管理レベルEnum
 */
public enum AuthLevelEnum {
	/** ゲスト */
	GUEST(CommonConst.AUTH_LEVEL_GUEST, "ゲスト"),
	/** 子供 */
	CHILD(CommonConst.AUTH_LEVEL_CHILD, "子供"),
	/** 大人 */
	ADULT(CommonConst.AUTH_LEVEL_ADULT, "大人"),
	/** 管理者 */
	ADMIN(CommonConst.AUTH_LEVEL_ADMIN, "管理者");

	final int code;
	final String label;

	private AuthLevelEnum(int code, String label) {
		this.code = code;
		this.label = label;
	}

	public int getCode() {
		return code;
	}

	public String getCodeString() {
		return Integer.toString(this.code);
	}

	public String getLabel() {
		return label;
	}

	public String getCodeLabel() {
		return new StringBuilder(getCodeString()).append(":").append(getLabel()).toString();
	}

	public static AuthLevelEnum valueOf(int rm) {
		switch (rm) {

		case CommonConst.AUTH_LEVEL_GUEST:
			return GUEST;

		case CommonConst.AUTH_LEVEL_CHILD:
			return CHILD;

		case CommonConst.AUTH_LEVEL_ADULT:
			return ADULT;

		case CommonConst.AUTH_LEVEL_ADMIN:
			return ADMIN;

		default:
			throw new IllegalArgumentException("argument out of range");
		}
	}

}
