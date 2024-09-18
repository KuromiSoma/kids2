package com.kdc.common.enums;

import com.kdc.common.util.CommonConst;

/**
 * Push通知種別Enum
 */
public enum PushNotificationEnum {
	/** ユーザアイコン */
	USERICON(CommonConst.PUSH_NOFITICATION_USERICON),
	/** SOS */
	SOS(CommonConst.PUSH_NOFITICATION_SOS),
	/** バッテリー */
	BATTERY(CommonConst.PUSH_NOFITICATION_BATTERY),
	/** 接続切断 */
	DISCONNECT(CommonConst.PUSH_NOFITICATION_DISCONNECT),
	/** 接続回復 */
	RECONNECT(CommonConst.PUSH_NOFITICATION_RECONNECT),
	/** 場所進入（通常場所） */
	PLACE_NORMAL_IN(CommonConst.PUSH_NOFITICATION_PLACE_NORMAL_IN),
	/** 場所進入（危険な場所） */
	PLACE_DANGER_IN(CommonConst.PUSH_NOFITICATION_PLACE_DANGER_IN),
	/** 場所退出（通常場所） */
	PLACE_NORMAL_OUT(CommonConst.PUSH_NOFITICATION_PLACE_NORMAL_OUT),
	/** 場所退出（危険な場所） */
	PLACE_DANGER_OUT(CommonConst.PUSH_NOFITICATION_PLACE_DANGER_OUT),
	/** 設定変更（サーバ処理） */
	CHANGE_CONFIG(CommonConst.PUSH_NOFITICATION_CHANGE_CONFIG),;

	final int code;

	private PushNotificationEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static PushNotificationEnum valueOf(int rm) {
		switch (rm) {

		case CommonConst.PUSH_NOFITICATION_USERICON:
			return USERICON;

		case CommonConst.PUSH_NOFITICATION_SOS:
			return SOS;

		case CommonConst.PUSH_NOFITICATION_BATTERY:
			return BATTERY;

		case CommonConst.PUSH_NOFITICATION_DISCONNECT:
			return DISCONNECT;

		case CommonConst.PUSH_NOFITICATION_RECONNECT:
			return RECONNECT;

		case CommonConst.PUSH_NOFITICATION_PLACE_NORMAL_IN:
			return PLACE_NORMAL_IN;

		case CommonConst.PUSH_NOFITICATION_PLACE_DANGER_IN:
			return PLACE_DANGER_IN;

		case CommonConst.PUSH_NOFITICATION_PLACE_NORMAL_OUT:
			return PLACE_NORMAL_OUT;

		case CommonConst.PUSH_NOFITICATION_PLACE_DANGER_OUT:
			return PLACE_DANGER_OUT;

		case CommonConst.PUSH_NOFITICATION_CHANGE_CONFIG:
			return CHANGE_CONFIG;

		default:
			throw new IllegalArgumentException("argument out of range");
		}
	}

}
