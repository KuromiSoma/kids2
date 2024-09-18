package com.kdc.common.enums;

import com.kdc.common.util.CommonConst;

/**
 * 認証モードEnum
 */
public enum AuthenticationModeEnum {
	/** ログイン */
	LOGIN(CommonConst.AUTHENTICATION_MODE_LOGIN),
	/** ユーザ登録 */
	REGISTER_USER(CommonConst.AUTHENTICATION_MODE_REGISTER_USER),
	/** 新規グループ作成 */
	NEW_GROUP(CommonConst.AUTHENTICATION_MODE_NEW_GROUP);

	final int code;

	private AuthenticationModeEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static AuthenticationModeEnum valueOf(int rm) {
		switch (rm) {

		case CommonConst.AUTHENTICATION_MODE_LOGIN:
			return LOGIN;

		case CommonConst.AUTHENTICATION_MODE_REGISTER_USER:
			return REGISTER_USER;

		case CommonConst.AUTHENTICATION_MODE_NEW_GROUP:
			return NEW_GROUP;

		default:
			throw new IllegalArgumentException("argument out of range");
		}
	}

}
