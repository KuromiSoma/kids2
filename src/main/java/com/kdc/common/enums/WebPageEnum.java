package com.kdc.common.enums;

import com.kdc.common.util.CommonConst;

/**
 * 画面一覧Enum
 */
public enum WebPageEnum {
	/** ログイン */
	LOGIN("login", "ログイン"),
	/** メイン */
	MAIN("main", "メイン"),
//	/** 共通設定 */
//	COMMONCONFIG("commonconfig", "共通設定"),
	/** 管理設定 */
	AUTHCONFIG("authconfig", "管理設定"),
	/** ユーザ設定 */
	USERCONFIG("userconfig", "ユーザ設定"),
	/** 場所管理 */
	PLACECONFIG("placeconfig", "場所設定"),
	/** 履歴 */
	RECORD("record", "履歴");

	final String id;
	final String name;

	private WebPageEnum(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPageTitleLabel() {
		return new StringBuilder().append(CommonConst.KDC_SYSTEM_DISP_NAME).append("　　").append(this.name).append("画面")
				.toString();
	}

}
