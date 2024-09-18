package com.kdc.common.enums;

import java.util.Calendar;

/**
 * 曜日Enum
 */
public enum DayOfWeekEnum {
	/** 日曜日 */
	SUNDAY(Calendar.SUNDAY, "日"),
	/** 月曜日 */
	MONDAY(Calendar.MONDAY, "月"),
	/** 火曜日 */
	TUESDAY(Calendar.TUESDAY, "火"),
	/** 水曜日 */
	WEDNESDAY(Calendar.WEDNESDAY, "水"),
	/** 木曜日 */
	THURSDAY(Calendar.THURSDAY, "木"),
	/** 金曜日 */
	FRIDAY(Calendar.FRIDAY, "金"),
	/** 土曜日 */
	SATURDAY(Calendar.SATURDAY, "土");

	final int code;
	final String label;

	private DayOfWeekEnum(int code, String label) {
		this.code = code;
		this.label = label;
	}

	public int getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public static DayOfWeekEnum valueOf(int rm) {
		switch (rm) {

		case Calendar.SUNDAY:
			return SUNDAY;

		case Calendar.MONDAY:
			return MONDAY;

		case Calendar.TUESDAY:
			return TUESDAY;

		case Calendar.WEDNESDAY:
			return WEDNESDAY;

		case Calendar.THURSDAY:
			return THURSDAY;

		case Calendar.FRIDAY:
			return FRIDAY;

		case Calendar.SATURDAY:
			return SATURDAY;

		default:
			throw new IllegalArgumentException("argument out of range");
		}
	}

}
