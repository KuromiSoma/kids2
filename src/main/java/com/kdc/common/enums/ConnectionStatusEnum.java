package com.kdc.common.enums;

import com.kdc.common.util.CommonConst;

/**
 * 接続可否Enum
 */
public enum ConnectionStatusEnum {
	/** 切断 */
	DISCONNECT(CommonConst.CONNECTION_STATUS_DISCONNECT),
	/** 接続 */
	CONNECT(CommonConst.CONNECTION_STATUS_CONNECT);

	final int code;

	private ConnectionStatusEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static ConnectionStatusEnum valueOf(int rm) {
		switch (rm) {

		case CommonConst.CONNECTION_STATUS_DISCONNECT:
			return DISCONNECT;

		case CommonConst.CONNECTION_STATUS_CONNECT:
			return CONNECT;

		default:
			throw new IllegalArgumentException("argument out of range");
		}
	}
}
