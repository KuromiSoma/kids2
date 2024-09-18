package com.kdc.common.enums;

import com.kdc.common.util.CommonConst;

/**
 * 通知タイプEnum
 */
public enum NotificationTypeEnum {
	/** 音のみ */
	SOUND_ONLY(CommonConst.NOTIFICATION_TYPE_SOUND_ONLY, "音のみ"),
	/** バイブのみ */
	VIBRATION_ONLY(CommonConst.NOTIFICATION_TYPE_VIBRATION_ONLY, "バイブのみ"),
	/** 音＋バイブ */
	SOUND_AND_VIBRATION(CommonConst.NOTIFICATION_TYPE_SOUND_AND_VIBRATION, "音＋バイブ");

	final int code;
	final String label;

	private NotificationTypeEnum(int code, String label) {
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

	public static NotificationTypeEnum valueOf(int rm) {
		switch (rm) {

		case CommonConst.NOTIFICATION_TYPE_SOUND_ONLY:
			return SOUND_ONLY;

		case CommonConst.NOTIFICATION_TYPE_VIBRATION_ONLY:
			return VIBRATION_ONLY;

		case CommonConst.NOTIFICATION_TYPE_SOUND_AND_VIBRATION:
			return SOUND_AND_VIBRATION;

		default:
			throw new IllegalArgumentException("argument out of range");
		}
	}
}
