package com.kdc.common.util;

/**
 * 共通定数クラス.
 */
public class CommonConst {

	/** キッズコントロールシステムタイトル */
	public static final String KDC_SYSTEM_TITLE = "kidcontrol";
	/** キッズコントロールシステム表示名 */
	public static final String KDC_SYSTEM_DISP_NAME = "キッズコントロール";

	/** アクセスポイントURL共通部:API */
	public static final String API_BASE_URL = "/api";
	/** アクセスポイントURL共通部:サーバ */
	public static final String WEB_BASE_URL = "/web";

	/** システムモジュールファイル格納ディレクトリパス */
	// TODO:本番環境に合わせて変更
	public static final String SYSTEM_MODULE_PATH = "/Users/963somaformac/work/kids/Shared01/kidcontrol/module/";
	//public static final String SYSTEM_MODULE_PATH = "/Shared01/kidcontrol/module/";

	/** システム設定ファイル名 */
	public static final String SYSTEM_INI_FILE_NAME = "kidcontrol.ini";

	/** アプリインストールパッケージ（APK）ファイル名 */
	public static final String APK_FILE_NAME = "kidcontrol.apk";

	/** ユーザデフォルトアイコン画像格納ディレクトリ */
	public static final String USER_ICON_DIR = "icon/user/";
	/** 場所アイコン画像格納ディレクトリ */
	public static final String PLACE_ICON_DIR = "icon/place/";
	/** マーカーアイコン画像格納ディレクトリ */
	public static final String MARKER_ICON_DIR = "icon/marker/";
	/** アイコン画像表示用（Base64） */
	public static final String ICON_IMG_HEADER = "data:image/png;base64,";
	/** アイコン画像の幅 */
	public static final int ICON_WIDTH = 40;
	/** アイコン画像の高さ */
	public static final int ICON_HEIGHT = 40;

	/** 結果コード:正常処理 */
	public static final Integer RESULT_CD_SUCCESS = 0;
	/** 結果コード:処理失敗 */
	public static final Integer RESULT_CD_FAILED = 1;

	/** IDヘッダ文字:場所ID */
	public static final String ID_HEADER_PLACE = "P";
	/** IDヘッダ文字:設定ID */
	public static final String ID_HEADER_CONFIG = "C";

	/** フラグ項目:ON */
	public static final Integer FLG_ON = 1;
	/** フラグ項目:OFF */
	public static final Integer FLG_OFF = 0;
	/** グループID(初期値：１） */
	public static final String GROUP_ID_1 = "1";

	/*-------- 列挙値 --------*/
	/** API識別ID:ログイン */
	public static final int API_ID_LOGIN = 1;
	/** API識別ID:ユーザ登録 */
	public static final int API_ID_REGISTER_USER = 2;
	/** API識別ID:位置情報送信 */
	public static final int API_ID_SEND_LOCATION = 3;
	/** API識別ID:ユーザ位置情報取得 */
	public static final int API_ID_GET_LOCATIONS = 4;
	/** API識別ID:場所情報取得 */
	public static final int API_ID_GET_PLACES = 5;
	/** API識別ID:ユーザ履歴情報取得 */
	public static final int API_ID_GET_RECORDS = 6;
	/** API識別ID:設定取得 */
	public static final int API_ID_GET_CONFIG = 7;
	/** API識別ID:SOS発信 */
	public static final int API_ID_SEND_SOS_ALERT = 8;
	/** API識別ID:場所登録 */
	public static final int API_ID_REGISTER_PLACE = 9;
	/** API識別ID:ユーザ情報変更 */
	public static final int API_ID_SETTING_USER = 10;
	/** API識別ID:アイコン画像ダウンロード */
	public static final int API_ID_DL_USERICON = 11;
	/** API識別ID:招待コード発行 */
	public static final int API_ID_GET_INVITATION = 12;
	/** API識別ID:招待コード認証 */
	public static final int API_ID_CHECK_INVITATION = 13;
	/** API識別ID:APKダウンロード */
	public static final int API_ID_DL_APK = 14;
	/** API識別ID:ログアウト */
	public static final int API_ID_LOGOUT = 15;
	/** API識別ID:グループ情報取得 */
	public static final int API_ID_GET_GROUPS = 16;
	/** API識別ID:グループ登録 */
	public static final int API_ID_REGISTER_GROUP = 17;
	/** API識別ID:通知情報登録　*/
	public static final int API_ID_REGISTER_NOTIFICATION = 18;
	/** API識別ID:通知情報取得　*/
	public static final int API_ID_GET_NOTIFICATIONS = 19;	
	/** API識別ID:チェックユーザ */
	public static final int API_ID_CHECK_USER = 20;
	/** API識別ID:（管理者用機能）全ユーザ情報取得 */
	public static final int API_ID_ADMIN_GET_ALLUSER = 101;
	/** API識別ID:（管理者用機能）他ユーザ情報変更 */
	public static final int API_ID_ADMIN_SETTING_USER = 102;
	/** API識別ID:（管理者用機能）ユーザ削除 */
	public static final int API_ID_ADMIN_DELUSER = 103;
	/** API識別ID:サーバ処理（API以外でPush通知を返す特殊番号） */
	public static final int API_ID_SERVER_PROCESS = 999;

	/** 管理レベル:ゲスト */
	public static final int AUTH_LEVEL_GUEST = 1;
	/** 管理レベル:子供 */
	public static final int AUTH_LEVEL_CHILD = 2;
	/** 管理レベル:大人 */
	public static final int AUTH_LEVEL_ADULT = 3;
	/** 管理レベル:管理者 */
	public static final int AUTH_LEVEL_ADMIN = 4;

	/** Push通知種別:ユーザアイコン */
	public static final int PUSH_NOFITICATION_USERICON = 1;
	/** Push通知種別:SOS */
	public static final int PUSH_NOFITICATION_SOS = 2;
	/** Push通知種別:バッテリー */
	public static final int PUSH_NOFITICATION_BATTERY = 3;
	/** Push通知種別:接続切断 */
	public static final int PUSH_NOFITICATION_DISCONNECT = 4;
	/** Push通知種別:接続回復 */
	public static final int PUSH_NOFITICATION_RECONNECT = 5;
	/** Push通知種別:場所進入（通常場所） */
	public static final int PUSH_NOFITICATION_PLACE_NORMAL_IN = 6;
	/** Push通知種別:場所進入（危険な場所） */
	public static final int PUSH_NOFITICATION_PLACE_DANGER_IN = 7;
	/** Push通知種別:場所退出（通常場所） */
	public static final int PUSH_NOFITICATION_PLACE_NORMAL_OUT = 8;
	/** Push通知種別:場所退出（危険な場所） */
	public static final int PUSH_NOFITICATION_PLACE_DANGER_OUT = 9;
	/** Push通知種別:設定変更（サーバ処理） */
	public static final int PUSH_NOFITICATION_CHANGE_CONFIG = 10;

	/** 通知タイプ:音のみ */
	public static final int NOTIFICATION_TYPE_SOUND_ONLY = 0;
	/** 通知タイプ:バイブのみ */
	public static final int NOTIFICATION_TYPE_VIBRATION_ONLY = 1;
	/** 通知タイプ:音＋バイブ */
	public static final int NOTIFICATION_TYPE_SOUND_AND_VIBRATION = 2;

	/** 電波状況:Wi-Fi */
	public static final int RECEPTION_STATUS_WIFI = 1;
	/** 電波状況:LTE */
	public static final int RECEPTION_STATUS_LTE = 2;
	/** 電波状況:4G */
	public static final int RECEPTION_STATUS_4G = 3;
	/** 電波状況:3G */
	public static final int RECEPTION_STATUS_3G = 4;
	/** 電波状況:5G */
	public static final int RECEPTION_STATUS_5G = 5;
	/** 電波状況:WiMAX */
	public static final int RECEPTION_STATUS_WIMAX = 6;

	/** 接続可否:切断 */
	public static final int CONNECTION_STATUS_DISCONNECT = 0;
	/** 接続可否:接続 */
	public static final int CONNECTION_STATUS_CONNECT = 1;

	/** 場所登録モード:登録 */
	public static final int REGISTER_PLACE_MODE_INSERT = 1;
	/** 場所登録モード:変更 */
	public static final int REGISTER_PLACE_MODE_UPDATE = 2;
	/** 場所登録モード:削除 */
	public static final int REGISTER_PLACE_MODE_DELETE = 3;

	/** 場所種別:通常場所 */
	public static final int PLACE_TYPE_NORMAL = 0;
	/** 場所種別:危険な場所 */
	public static final int PLACE_TYPE_DANGER = 1;

	/** 認証モード:ログイン */
	public static final int AUTHENTICATION_MODE_LOGIN = 1;
	/** 認証モード:ユーザ登録 */
	public static final int AUTHENTICATION_MODE_REGISTER_USER = 2;
	/** 認証モード:新規グループ作成 */
	public static final int AUTHENTICATION_MODE_NEW_GROUP = 3;
	/** 認証　招待コード：1111111（ユーザーが存在しない場合にスルーさせる） **/
	public static final String INVITATION_CD = "1111111";

	/** グループ登録モード:登録 */
	public static final int REGISTER_GROUP_MODE_INSERT = 1;
	/** グループ登録モード:変更 */
	public static final int REGISTER_GROUP_MODE_UPDATE = 2;
	/** グループ登録モード:削除 */
	public static final int REGISTER_GROUP_MODE_DELETE = 3;
	
	/** 通知情報登録モード:登録 */
	public static final int REGISTER_NOTIFICATION_MODE_INSERT = 1;
	/** グループ登録モード:変更 */
	public static final int REGISTER_NOTIFICATION_MODE_UPDATE = 2;
	/** グループ登録モード:削除 */
	public static final int REGISTER_NOTIFICATION_MODE_DELETE = 3;

	/*-------- 設定区分 --------*/
	/** 設定区分ID:初期パスワード */
	public static final int CONFIG_ID_INIT_PASSWORD = 0;
	/** 設定区分ID:SOSカウントダウン秒数 */
	public static final int CONFIG_ID_SOS_COUNTDOWN = 1;
	/** 設定区分ID:端末切断時間（分） */
	public static final int CONFIG_ID_DISCONNECT_TIME = 2;
	/** 設定区分ID:バージョン情報 */
	public static final int CONFIG_ID_VERSION_NO = 3;
	/** 設定区分ID:Push通知再送信待機時間（分） */
	public static final int CONFIG_ID_PUSH_NOTIFICATION_INTERVAL_TIME = 4;
	/** 設定区分ID:招待コード有効期間（分） */
	public static final int CONFIG_ID_INVITATION_CODE_EXPIRATION_TIME = 5;

	/*-------- デフォルト値 --------*/
	// /** デフォルト値:グループID */
	// public static final String DEFAULT_GROUP_ID = "1";

	/** デフォルト値:通知音時間 */
	public static final Integer DEFAULT_NOTIFICATION_TIME_SOUND = 3;
	/** デフォルト値:通知バイブ時間 */
	public static final Integer DEFAULT_NOTIFICATION_TIME_VIBRATION = 3;
	/** デフォルト値:バッテリー通知基準値 */
	public static final Integer DEFAULT_NOTIFICATION_BATTERY = 30;

	/** デフォルト値:GoogleMap初期表示座標 */
	public static final String DEFAULT_MAP_LATITUDE = "35.632899";
	public static final String DEFAULT_MAP_LONGITUDE = "139.880458";
	public static final String DEFAULT_MAP_ZOOM = "15";

	/** デフォルト値:GoogleMap上に配置する場所の円の半径 */
	public static final Integer DEFAULT_PLACE_RADIUS = 10;
	public static final Integer PLACE_RADIUS_MIN = 10;
	public static final Integer PLACE_RADIUS_MAX = 1000;

	/*-------- その他定数値 --------*/
	/** 招待コードの桁数 */
	public static final int INVITATION_CODE_LENGTH = 7;
	/** ユーザIDの最大文字数 */
	public static final int USER_ID_MAX_LENGTH = 15;
	/** パスワードの最大文字数 */
	public static final int PASSWORD_MAX_LENGTH = 15;
	/** ユーザ名の最大文字数 */
	public static final int USER_NAME_MAX_LENGTH = 15;
	/** グループIDの最大文字数 */
	public static final int GROUP_ID_MAX_LENGTH = 15;
	/** 場所名の最大文字数 */
	public static final int PLACE_NAME_MAX_LENGTH = 15;
	/** グループ名の最大文字数 */
	public static final int GROUP_NAME_MAX_LENGTH = 15;
	/** 通知日の最大文字数 */
	public static final int NOTIFICATION_DATE_MAX_LENGTH = 8;
	/** 通知時間の最大文字数 */
	public static final int NOTIFICATION_TIME_MAX_LENGTH = 4;
	/** 電話番号の桁数（ハイフン無し） */
	public static final int TELEPHONE_NUMBER_LENGTH = 11;

	/** 送信間隔の曜日当たり設定可能件数 */
	public static final int SEND_INTERVAL_CONFIG_COUNT = 5;

	/*-------- 内部処理用 --------*/
	/** PostgreSQL SQLSTATE:一意制約違反 */
	public static final String SQLSTATE_UNIQUE_VIOLATION = "23505";

	/** WGS84測地系 赤道半径 */
	public static final double WGS84_EQU_RAD = 6378137.000000;
	/** WGS84測地系 極半径 */
	public static final double WGS84_POL_RAD = 6356752.314245;

	/*-------- 2018/11/20仕様追加 初期ユーザチェック用 --------*/
	/** 初期ユーザ存在チェック用:存在しない */
	public static final int CHECK_USER_NOT_EXIST = 0;
	/** 初期ユーザ存在チェック用:存在する */
	public static final int CHECK_USER_EXIST = 1;
}