package com.kdc.web.common.javascript;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.kdc.common.enums.ConfirmMessageEnum;
import com.kdc.common.util.KdcCommonUtils;

/**
 * 確認メッセージに関するJavaScriptを保持し、マスタページに反映する
 * 
 * @author shimizuh@kksse.co.jp
 * @since 2017/01/11
 * @version
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JavaScriptConfirmHolder {

	private ArrayList<ConfirmItem> confirmList = new ArrayList<>();

	private class ConfirmItem {
		private String buttonId;
		private ConfirmMessageEnum confirmMessage;
		private String callJavaScript;
	}

	/**
	 * 確認メッセージ登録.
	 * 
	 * @param buttonId
	 *            確認メッセージを設定するボタンのID
     * @param confirmMessage 表示する確認メッセージのメッセージID
	 * @param callJavaScript
	 *            ＯＫボタン押下後の追加処理JavaScript/{@code null}の場合は追加処理なし
	 */
	public void addItem(String buttonId, ConfirmMessageEnum confirmMessage, String callJavaScript) {
		ConfirmItem item = new ConfirmItem();
		item.buttonId = buttonId;
		item.confirmMessage = confirmMessage;
		item.callJavaScript = callJavaScript;
		this.confirmList.add(item);
		return;
	}

	/**
	 * 確認メッセージ用のJavaScriptソース文字列を返す.
	 * 
	 * @param prettyPrint
	 *            改行整形(true)/整形なし(false)
	 * @return JavaScriptソース文字列
	 */
	public String getJavaScriptSource(Boolean prettyPrint) {
		StringBuilder js = new StringBuilder();
		// 呼び出し関数定義
		for (ConfirmItem item : this.confirmList) {
			js.append("function fnc_").append(item.buttonId).append("_Confirm(e){");
			js.append(prettyPrint ? "\n\t" : "");
			// 確認メッセージ表示：条件なし
			js.append("if(!confirmNormal(e,");
			js.append(item.confirmMessage.getMessageString());
			js.append(")){return false;}");
			if (!KdcCommonUtils.isEmpty(item.callJavaScript)) {
				js.append(item.callJavaScript);
			}
			js.append("return true;");

			js.append(prettyPrint ? "\n" : "");
			js.append("}");
			js.append(prettyPrint ? "\n" : "");
		}
		// ボタンへイベント登録
		if (this.confirmList.size() != 0) {
			js.append("$(function(){");
			js.append(prettyPrint ? "\n\t" : "");
			for (ConfirmItem item : this.confirmList) {
				js.append("$(\"#").append(item.buttonId).append("\")");
				js.append(".off(\"click.confirm\")");
				js.append(".on(\"click.confirm\",function(e){");

				// 警告メッセージで続行後はメッセージ無視
				js.append("if($('#ignoreAttentionFlg').val() == '1'){return true;}");
				js.append("return fnc_").append(item.buttonId).append("_Confirm(e);");
				js.append("});");
				js.append(prettyPrint ? "\n" : "");
			}
			js.append("});");
			js.append(prettyPrint ? "\n" : "");
		}
		// JavaScript返却
		return js.toString();
	}

}