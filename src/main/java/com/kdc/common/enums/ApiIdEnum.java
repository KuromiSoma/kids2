package com.kdc.common.enums;

import com.kdc.common.util.CommonConst;

/**
 * API識別ID Enum
 */
public enum ApiIdEnum {
	/** ログイン */
	LOGIN(CommonConst.API_ID_LOGIN),
	/** ユーザ登録 */
	REGISTER_USER(CommonConst.API_ID_REGISTER_USER),
	/** 位置情報送信 */
	SEND_LOCATION(CommonConst.API_ID_SEND_LOCATION),
	/** ユーザ位置情報取得 */
	GET_LOCATIONS(CommonConst.API_ID_GET_LOCATIONS),
	/** 場所情報取得 */
	GET_PLACES(CommonConst.API_ID_GET_PLACES),
	/** グループ情報取得 */
	GET_GROUPS(CommonConst.API_ID_GET_GROUPS),
	/** ユーザ履歴情報取得 */
	GET_RECORDS(CommonConst.API_ID_GET_RECORDS),
	/** 設定取得 */
	GET_CONFIG(CommonConst.API_ID_GET_CONFIG),
	/** SOS発信 */
	SEND_SOS_ALERT(CommonConst.API_ID_SEND_SOS_ALERT),
	/** 場所登録 */
	REGISTER_PLACE(CommonConst.API_ID_REGISTER_PLACE),
	/** グループ登録 */
	REGISTER_GROUP(CommonConst.API_ID_REGISTER_GROUP),
	/** 通知情報登録 */
	REGISTER_NOTIFICATION(CommonConst.API_ID_REGISTER_NOTIFICATION),
	/** 通知情報取得 */
	GET_NOTIFICATIONS(CommonConst.API_ID_GET_NOTIFICATIONS),
	
	/** ユーザ情報変更 */
	SETTING_USER(CommonConst.API_ID_SETTING_USER),
	/** アイコン画像ダウンロード */
	DL_USERICON(CommonConst.API_ID_DL_USERICON),
	/** 招待コード発行 */
	GET_INVITATION(CommonConst.API_ID_GET_INVITATION),
	/** 招待コード認証 */
	CHECK_INVITATION(CommonConst.API_ID_CHECK_INVITATION),
	/** APKダウンロード */
	DL_APK(CommonConst.API_ID_DL_APK),
	/** （管理者用機能）全ユーザ情報取得 */
	ADMIN_GET_ALLUSER(CommonConst.API_ID_ADMIN_GET_ALLUSER),
	/** （管理者用機能）他ユーザ情報変更 */
	ADMIN_SETTING_USER(CommonConst.API_ID_ADMIN_SETTING_USER),
	/** （管理者用機能）ユーザ削除 */
	ADMIN_DELUSER(CommonConst.API_ID_ADMIN_DELUSER),
	/** サーバ処理（API以外でPush通知を返す特殊番号） */
	SERVER_PROCESS(CommonConst.API_ID_SERVER_PROCESS),
	/** ログアウト */
	LOGOUT(CommonConst.API_ID_LOGOUT),
	/** ユーザチェック */
	CHECK_USER(CommonConst.API_ID_CHECK_USER),;

	final int code;

	private ApiIdEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static ApiIdEnum valueOf(int rm) {
		switch (rm) {

		case CommonConst.API_ID_LOGIN:
			return LOGIN;

		case CommonConst.API_ID_REGISTER_USER:
			return REGISTER_USER;

		case CommonConst.API_ID_SEND_LOCATION:
			return SEND_LOCATION;

		case CommonConst.API_ID_GET_LOCATIONS:
			return GET_LOCATIONS;

		case CommonConst.API_ID_GET_PLACES:
			return GET_PLACES;

		case CommonConst.API_ID_GET_RECORDS:
			return GET_RECORDS;

		case CommonConst.API_ID_GET_CONFIG:
			return GET_CONFIG;

		case CommonConst.API_ID_SEND_SOS_ALERT:
			return SEND_SOS_ALERT;

		case CommonConst.API_ID_REGISTER_PLACE:
			return REGISTER_PLACE;

		case CommonConst.API_ID_SETTING_USER:
			return SETTING_USER;

		case CommonConst.API_ID_DL_USERICON:
			return DL_USERICON;

		case CommonConst.API_ID_GET_INVITATION:
			return GET_INVITATION;

		case CommonConst.API_ID_CHECK_INVITATION:
			return CHECK_INVITATION;

		case CommonConst.API_ID_DL_APK:
			return DL_APK;

		case CommonConst.API_ID_ADMIN_GET_ALLUSER:
			return ADMIN_GET_ALLUSER;

		case CommonConst.API_ID_ADMIN_SETTING_USER:
			return ADMIN_SETTING_USER;

		case CommonConst.API_ID_ADMIN_DELUSER:
			return ADMIN_DELUSER;

		case CommonConst.API_ID_SERVER_PROCESS:
		return SERVER_PROCESS;
			
		case CommonConst.API_ID_LOGOUT:
		return LOGOUT;
		
		case CommonConst.API_ID_CHECK_USER:
		return CHECK_USER;
		
		case CommonConst.API_ID_REGISTER_NOTIFICATION:
			return REGISTER_NOTIFICATION;
		
		case CommonConst.API_ID_GET_NOTIFICATIONS:
			return GET_NOTIFICATIONS;
		

		default:
			throw new IllegalArgumentException("argument out of range");
		}
	}

}
