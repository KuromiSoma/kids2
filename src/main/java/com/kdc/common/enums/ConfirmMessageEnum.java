package com.kdc.common.enums;

import com.kdc.common.util.KdcCommonUtils;

/**
 * 確認メッセージ一覧
 * 
 * @author umemoto
 * @since 2017/01/11
 * @version
 *
 */
public enum ConfirmMessageEnum {
	// 列挙子(標準メッセージ)
	DEFAULT_COMMIT("登録しますか？"),
	DELETE_USER("このユーザを削除します。よろしいですか？"),
	DELETE_PLACE("選択した場所を削除します。よろしいですか？"),;

	// フィールド
	final String message;

	// コンストラクタ
	private ConfirmMessageEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * 表示する文字列を取得.
	 * 
	 * @return メッセージ文字列、{@code null}の場合、文字列"null"を返す
	 */
	public String getMessageString() {
		if (KdcCommonUtils.isEmpty(this.message)) {
			return "null";
		}
		return new StringBuilder().append("\"").append(this.message).append("\"").toString();
	}

}