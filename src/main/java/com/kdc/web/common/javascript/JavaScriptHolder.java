package com.kdc.web.common.javascript;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.WebRequest;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.web.common.error.ComErrorInfoData;
import com.kdc.web.common.error.ComErrorInfoData.ErrorInfo;

/**
 * JavaScriptとCSSを保持し、マスタページに反映する
 * 
 * @author shimizuh@kksse.co.jp
 * @since 2016/07/06
 * @version
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JavaScriptHolder {

	// Ajaxで使用するトリガー項目のKey文字列.
	private static final String TRIGGER_ID = "triggerid";
	private static final String TRIGGER_VALUE = "triggerval";

	// JavaScript イベント識別子
	public enum EventHandlerEnum {
		// 識別子
		onBlur("blur"),
		onChange("change"),
		onClick("click"),
		onDblclick("dblclick"),
		onFocus("focus"),
		onKeydown("keydown"),
		onKeypress("keypress"),
		onKeyup("keyup"),
		onLoad("load"),
		onMousedown("mousedown"),
		onMousemove("mousemove"),
		onMouseout("mouseout"),
		onMouseover("mouseover"),
		onMouseup("mouseup"),
		onResize("resize"),
		onScroll("scroll"),
		onSelect("select"),
		onSubmit("submit"),
		onUnload("unload"),
		;

		// フィールド
		private String source;

		// コンストラクタ
		private EventHandlerEnum(String source) {
			this.source = source;
		}

		public String getSource() {
			return this.source;
		}
	}

	/**
	 * Ajax 設定項目.
	 */
	public class AjaxItem {
		// Ajaxトリガー情報.
		private class Trigger {
			public String id;
			public EventHandlerEnum eventHandler;
		}

		// Ajaxパラメータ情報.
		private class Parameter {
			public String id;
			public String keyId;
			public String keyValue;
		}

		// Ajaxパラメータ（固定値）情報.
		private class Constant {
			public String value;
			public String keyValue;
		}

		private String index;
		private String ajaxUrl;
		private Boolean isAsync;
		private String dataType;
		private String callBack;
		private List<Trigger> triggerList = new ArrayList<>();
		private List<Parameter> parameterList = new ArrayList<>();
		private List<Constant> constantList = new ArrayList<>();

		// Ajax用のJavaScript関数名を返す.
		protected String getFuncName() {
			return new StringBuilder("AjaxF").append(this.index).toString();
		}

		/**
		 * Ajaxトリガーを追加する.
		 * 
		 * @param targetId
		 *            トリガーを設定するHTML上のID文字列
		 * @param eventHandler
		 *            設定するトリガー種類
		 */
		public void addTrigger(String targetId, EventHandlerEnum eventHandler) {
			Trigger trigger = new Trigger();
			trigger.id = StringUtils.trim(targetId);
			trigger.eventHandler = eventHandler;
			this.triggerList.add(trigger);
		}

		/**
		 * Ajaxパラメータを追加する.
		 * 
		 * @param paramId
		 *            値を取得するHTML上のID文字列
		 * @param keyId
		 *            呼び出し時の取得Key文字列（HTML上のID文字列)
		 * @param keyValue
		 *            呼び出し時の取得Key文字列(値)
		 */
		public void addParameter(String paramId, String keyId, String keyValue) {
			Parameter parameter = new Parameter();
			parameter.id = StringUtils.trim(paramId);
			parameter.keyId = StringUtils.trim(keyId);
			parameter.keyValue = StringUtils.trim(keyValue);
			this.parameterList.add(parameter);
		}

		/**
		 * Ajaxパラメータ(固定値)を追加する.
		 * 
		 * @param value
		 *            固定値文字列
		 * @param keyValue
		 *            呼び出し時の取得Key文字列(固定値)
		 */
		public void addConstant(String value, String keyValue) {
			Constant constant = new Constant();
			constant.value = StringUtils.trim(value);
			constant.keyValue = StringUtils.trim(keyValue);
			this.constantList.add(constant);
		}
	}

	// 変数定義や関数定義などの JavaScriptリスト
	private List<String> defineList = new ArrayList<>();
	// Document Ready時に実行するJavaScriptのリスト
	private List<String> javaScriptList = new ArrayList<>();
	// 共通ポップアップ画面から親画面に値を戻すJavaScriptのリスト(処理結果)
	private List<String> popupResultList = new ArrayList<>();
	// Ajax設定項目リスト
	private List<AjaxItem> ajaxList = new ArrayList<>();
	// CSSリスト
	private List<String> cssList = new ArrayList<>();

	/**
	 * 新しい Ajax設定項目を返す.
	 * 
	 * @return Ajax設定項目 ({@code not null})
	 */
	public AjaxItem getNewItem() {
		return new AjaxItem();
	}

	/**
	 * Ajax処理を追加する(ResponseがJavaScript).
	 * 
	 * @param ajaxUrl
	 *            Ajaxで呼び出すURL
	 * @param isAsync
	 *            同期(false)/非同期(true)
	 * @param item
	 *            Ajax設定項目
	 * @return 新しいAjax設定項目 ({@code not null})
	 */
	public AjaxItem addAjaxScript(String ajaxUrl, Boolean isAsync, AjaxItem item) {
		if (item.triggerList.size() == 0) {
			throw new IllegalArgumentException();
		}
		item.index = String.format("%03d", this.ajaxList.size() + 1);
		item.ajaxUrl = ajaxUrl;
		item.isAsync = isAsync;
		item.dataType = "script";
		item.callBack = "";
		this.ajaxList.add(item);
		return new AjaxItem();
	}

	/**
	 * Ajax処理を追加する(ResponseがJson).
	 * 
	 * @param ajaxUrl
	 *            Ajaxで呼び出すURL
	 * @param isAsync
	 *            同期(false)/非同期(true)
	 * @param callBack
	 *            Jsonを受け取るJavaScript関数名
	 * @param item
	 *            Ajax設定項目
	 * @return 新しいAjax設定項目 ({@code not null})
	 */
	public AjaxItem addAjaxJson(String ajaxUrl, Boolean isAsync, String callBack, AjaxItem item) {
		if (item.triggerList.size() == 0) {
			throw new IllegalArgumentException();
		}
		item.index = String.format("%04d", this.ajaxList.size() + 1);
		item.ajaxUrl = ajaxUrl;
		item.isAsync = isAsync;
		item.dataType = "json";
		item.callBack = callBack;
		this.ajaxList.add(item);
		return new AjaxItem();
	}

	/**
	 * Document Ready時に実行するJavaScriptを登録する.
	 * 
	 * @param javaScript
	 *            JavaScript文字列
	 */
	public void addJavaScript(String javaScript) {
		this.javaScriptList.add(javaScript);
	}

	/**
	 * 変数定義や関数定義などのJavaScriptを登録する.
	 * 
	 * @param javaScript
	 *            JavaScript文字列
	 */
	public void addDefineScript(String javaScript) {
		this.defineList.add(javaScript);
	}

	/**
	 * 共通ポップアップ画面から親画面に値を戻すJavaScript(処理結果)のリストを登録する.
	 * 
	 * @param javaScript
	 *            JavaScript文字列
	 */
	public void addPopupResultList(String javaScript) {
		this.popupResultList.add(javaScript);
	}

	/**
	 * CSSを登録する.
	 * 
	 * @param css
	 *            CSS文字列
	 */
	public void addCssStyle(String css) {
		this.cssList.add(css);
	}

	/**
	 * 最終的なJavaScriptソース文字列を返す.
	 * 
	 * @param prettyPrint
	 *            改行整形(true)/整形なし(false)
	 * @return JavaScriptソース文字列
	 */
	public String getJavaScriptSource(Boolean prettyPrint) {
		StringBuilder str = new StringBuilder();

		// 変数定義や関数定義などのJavaScriptを出力する
		for (String js : this.defineList) {
			str.append(js);
			str.append(prettyPrint ? "\n" : "");
		}

		// 共通ポップアップ画面から親画面に値を戻すJavaScript
		if (this.popupResultList.size() != 0) {
			str.append("$(function(){");
			str.append(prettyPrint ? "\n" : "");
			for (String js : this.popupResultList) {
				str.append(js);
				str.append(prettyPrint ? "\n" : "");
			}
			str.append("window.close();");
			str.append(prettyPrint ? "\n" : "");
			str.append("});");
			str.append(prettyPrint ? "\n" : "");
		}

		// Ajax用のJavaScriptを出力する
		str.append(this.getAjaxSource(prettyPrint));
		// Document Ready時に実行するJavaScriptを出力する
		if (this.javaScriptList.size() != 0) {
			str.append("$(function(){");
			str.append(prettyPrint ? "\n" : "");
			for (String js : this.javaScriptList) {
				str.append(js);
				str.append(prettyPrint ? "\n" : "");
			}
			str.append("});");
			str.append(prettyPrint ? "\n" : "");
		}

		return str.toString();
	}

	/**
	 * CSS文字列を返す.
	 * 
	 * @return CSS文字列
	 */
	public String getCssStyleSource(Boolean prettyPrint) {
		StringBuilder str = new StringBuilder();

		// CSSを出力する
		for (String css : this.cssList) {
			str.append(css);
			str.append(prettyPrint ? "\n" : "");
		}

		return str.toString();
	}

	// AjaxのJavaScriptソース文字列を取得する
	private String getAjaxSource(Boolean prettyPrint) {
		StringBuilder str = new StringBuilder();

		// 対象が存在しない場合
		if (this.ajaxList.isEmpty()) {
			return "";
		}

		// Trigger の JavaScript 生成
		str.append("$(function(){");
		str.append(prettyPrint ? '\n' : "");
		for (AjaxItem item : this.ajaxList) {
			for (AjaxItem.Trigger trigger : item.triggerList) {
				String eventName = new StringBuilder(trigger.eventHandler.getSource()).append('.')
						.append(item.getFuncName()).toString();
				str.append(prettyPrint ? '\t' : "");
				str.append("$(\"#").append(trigger.id).append("\")");
				str.append(".off(\"").append(eventName).append("\")");
				str.append(".on(\"").append(eventName).append("\",function(){");
				str.append(prettyPrint ? "\n\t\t" : "");
				str.append("isNumberCheck(this);");
				str.append("if (!$(this).prop(\"readonly\")){");
				str.append(prettyPrint ? "\n\t\t\t" : "");
				str.append(item.getFuncName()).append("(\"").append(trigger.id).append("\");");
				str.append(prettyPrint ? "\n\t\t" : "");
				str.append('}');
				str.append(prettyPrint ? "\n\t\t" : "");
				str.append("return false;");
				str.append(prettyPrint ? "\n\t" : "");
				str.append("});");
				str.append(prettyPrint ? "\n" : "");
			}
		}
		str.append("});");
		str.append(prettyPrint ? "\n\n" : "");

		// FunctionのJavaScript 生成
		for (AjaxItem item : this.ajaxList) {
			// AjaxのData部を準備
			LinkedHashMap<String, String> dataList = new LinkedHashMap<>();
			dataList.put(String.format("\"%s\"", TRIGGER_ID), "triggerid");
			dataList.put(String.format("\"%s\"", TRIGGER_VALUE), "getItemVal(triggerid)");
			for (AjaxItem.Parameter param : item.parameterList) {
				dataList.put(String.format("\"%s\"", param.keyId), String.format("\"%s\"", param.id));
				dataList.put(String.format("\"%s\"", param.keyValue), String.format("getItemVal(\"%s\")", param.id));
			}
			for (AjaxItem.Constant constant : item.constantList) {
				dataList.put(String.format("\"%s\"", constant.keyValue), String.format("\"%s\"", constant.value));
			}
			String lastKey = "";
			for (String key : dataList.keySet()) {
				lastKey = key;
			}
			// Ajax関数
			str.append("function ").append(item.getFuncName()).append("(triggerid){");
			str.append(prettyPrint ? "\n\t" : "");
			str.append("var inputdata = $('form').serializeArray();");
			str.append(prettyPrint ? "\n\t" : "");
			str.append("$.ajax({");
			str.append(prettyPrint ? "\n\t\t" : "");
			str.append("url:\"").append(item.ajaxUrl).append("\",");
			str.append(prettyPrint ? "\n\t\t" : "");
			str.append("type:\"POST\",");
			str.append(prettyPrint ? "\n\t\t" : "");
			str.append("async:").append(item.isAsync).append(',');
			str.append(prettyPrint ? "\n\t\t" : "");
			str.append("dataType:\"").append(item.dataType).append("\",");
			str.append(prettyPrint ? "\n\t\t" : "");
			// Data部 設定
			str.append("data:{");
			for (Map.Entry<String, String> entry : dataList.entrySet()) {
				str.append(prettyPrint ? "\n\t\t\t" : "");
				str.append(entry.getKey()).append(':').append(entry.getValue());
				if (!lastKey.equals(entry.getKey())) {
					str.append(',');
				}
			}
			str.append(prettyPrint ? "\n\t\t" : "");
			str.append(",'inputdata':inputdata");
			str.append(prettyPrint ? "\n\t" : "");
			str.append("}");
			str.append(prettyPrint ? "\n\t" : "");
			str.append("})");
			// 成功時の処理
			if (!item.callBack.isEmpty()) {
				str.append(".done(function(data,textStatus,jqXHR){");
				str.append(prettyPrint ? "\n\t\t" : "");
				str.append(item.callBack).append("(data);");
				str.append(prettyPrint ? "\n\t" : "");
				str.append("})");
			}
			// 失敗時の処理
			str.append(".fail(function(data,textStatus,errorThrown){");
			str.append(prettyPrint ? "\n\t\t" : "");
			str.append("if (data.status == 408) {");
			str.append(prettyPrint ? "\n\t\t" : "");
			str.append("alert(\"セッションが切れました。\\n再度ログインして下さい。\");");
			str.append(prettyPrint ? "\n\t\t" : "");
			str.append("return;");
			str.append(prettyPrint ? "\n\t\t" : "");
			str.append("}");
			str.append(prettyPrint ? "\n\t\t" : "");
			str.append("if (data.status == 0) {");
			str.append(prettyPrint ? "\n\t\t" : "");
			str.append("alert(\"一時的にサーバとの通信が切れました。\\n再度操作してください。\");");
			str.append(prettyPrint ? "\n\t\t" : "");
			str.append("return;");
			str.append(prettyPrint ? "\n\t\t" : "");
			str.append("}");
			str.append(prettyPrint ? "\n\t\t" : "");
			str.append("alert(\"内部エラー(\"+data.status+\":\"+textStatus+\":\"+errorThrown+\")\");");
			str.append(prettyPrint ? "\n\t\t" : "");
			str.append("});");
			str.append(prettyPrint ? "\n" : "");
			str.append('}');
			str.append(prettyPrint ? "\n\n" : "");
		}

		return str.toString();
	}

	/**
	 * トリガーが設定された項目のID文字列を取得する.
	 * 
	 * @param request
	 *            WebRequest
	 * @return ID文字列
	 */
	public String getAjaxTriigerId(WebRequest request) {
		return request.getParameter(TRIGGER_ID);
	}

	/**
	 * トリガーが設定された項目の値を取得する.
	 * 
	 * @param request
	 *            WebRequest
	 * @return 値文字列
	 */
	public String getAjaxTriggetValue(WebRequest request) {
		return request.getParameter(TRIGGER_VALUE);
	}

	/**
	 * Ajaxパラメータを取得する.
	 * 
	 * @param request
	 *            WebRequest
	 * @param key
	 *            キー文字列
	 * @return パラメータ情報文字列
	 */
	public String getAjaxParameter(WebRequest request, String key) {
		return request.getParameter(key);
	}

	/**
	 * 表示/非表示を行うJavaScriptを取得する.
	 * 
	 * @param id
	 *            対象項目のID文字列
	 * @param isDisplay
	 *            表示(true)/非表示(false)
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsDisplay(String id, Boolean isDisplay) {
		return new StringBuilder("$(\"#").append(id).append("\").toggle(").append(isDisplay).append(");").toString();
	}

	/**
	 * 有効/無効を行うJavaScriptを取得する.
	 * 
	 * @param id
	 *            対象項目のID文字列
	 * @param isEnabled
	 *            有効(true)/無効(false)
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsEnabled(String id, Boolean isEnabled) {
		return new StringBuilder("$(\"#").append(id).append("\").prop(\"disabled\",").append(!isEnabled).append(");")
				.toString();
	}

	/**
	 * Textを設定するJavaScriptを取得する.
	 * 
	 * @param id
	 *            対象項目のID文字列
	 * @param text
	 *            設定する文字列
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsSetText(String id, String text) {
		return new StringBuilder("$(\"#").append(id).append("\").text(\"").append(text).append("\");").toString();
	}

	/**
	 * Valueを設定するJavaScriptを取得する.
	 * 
	 * @param id
	 *            対象項目のID文字列
	 * @param value
	 *            設定する文字列
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsSetValue(String id, String value) {
		return new StringBuilder("$(\"#").append(id).append("\").val(\"").append(value).append("\");").toString();
	}

	/**
	 * チェックを行うJavaScriptを取得する.
	 * 
	 * @param id
	 *            対象項目のID文字列
	 * @param isEnabled
	 *            有効(true)/無効(false)
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsChecked(String id, Boolean isEnabled) {
		return new StringBuilder("$(\"#").append(id).append("\").prop(\"checked\",").append(isEnabled).append(");")
				.toString();
	}

	/**
	 * ラジオボタンに対してValueを設定して選択状態にするJavaScriptを取得する.
	 * 
	 * @param name
	 *            対象のラジオボタングループのname文字列
	 * @param value
	 *            設定するValue値
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsSetRadioValue(String name, String value) {
		return new StringBuilder("$(\"input[name=\'").append(name).append("\']\").val([\"").append(value)
				.append("\"]);").toString();
	}

	/**
	 * SELECT内の選択項目を更新するJavaScriptを取得する.
	 * 
	 * @param id
	 *            対象項目のID文字列
	 * @param key
	 *            選択されているKey文字列
	 * @param map
	 *            選択項目のMap
	 * @param isNeedNull
	 *            未選択状態が必要(true)/未選択状態は不要(false)
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsSelect(String id, String key, LinkedHashMap<String, String> map, Boolean isNeedNull) {
		StringBuilder str = new StringBuilder();
		if (isNeedNull) {
			str.append("<option value=''></option>");
		}
		for (Entry<String, String> entry : map.entrySet()) {
			str.append("<option value='").append(entry.getKey()).append("'");
			if (entry.getKey().equals(key)) {
				str.append(" selected='selected'");
			}
			str.append(">").append(entry.getValue()).append("</option>");
		}

		return new StringBuilder("$(\"#").append(id).append("\").html(\"").append(str).append("\");").toString();
	}

	/**
	 * 画像(Imgタグ)に画像の設定を行うJavaScriptを取得する.
	 * 
	 * @param id
	 *            対象項目のID文字列
	 * @param imgString
	 *            画像のBase64文字列
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsSettingImg(String id, String imgString) {
		return new StringBuilder("$(\"#").append(id).append("\").attr({'src':\"").append(CommonConst.ICON_IMG_HEADER)
				.append(imgString).append("\"});").toString();
	}

	/**
	 * JavaScriptでURL文字列を組み立てるコードを返す.
	 * 
	 * @param url
	 *            url文字列
	 * @param paramMap
	 *            引数マップ
	 * @return URL文字列組み立てJavaScriptコード文字列 ({@code not null})
	 */
	public static String getJsUrlBuilder(String url, LinkedHashMap<String, String> paramMap) {
		// パラメータ文字列を生成
		StringBuilder param = new StringBuilder();
		for (Map.Entry<String, String> item : paramMap.entrySet()) {
			param.append(param.length() == 0 ? ",\"?\"," : ",\"&\",");
			param.append(String.format("\"%s\"", item.getKey())).append(",\"=\",");
			param.append(item.getValue());
		}
		// URL を生成
		StringBuilder js = new StringBuilder();
		js.append("[");
		js.append(String.format("\"%s\"", url));
		js.append(param);
		js.append("].join(\"\")");
		return js.toString();
	}

	/**
	 * JavaScriptでAjax呼び出し文字列を組み立てるコードを返す.
	 * 
	 * @param url
	 *            url文字列
	 * @param paramMap
	 *            引数マップ
	 * @return Ajax呼び出しJavaScriptコード文字列 ({@code not null})
	 */
	public static String getJsAjaxBuilder(String url, LinkedHashMap<String, String> paramMap) {
		// Ajax呼び出しJavaScriptを生成
		String lastKey = "";
		for (String key : paramMap.keySet()) {
			lastKey = key;
		}
		StringBuilder js = new StringBuilder();
		js.append("if (!$(this).prop(\"readonly\")){");
		js.append("if (!$(this).prop(\"disabled\")){");
		js.append("$.ajax({");
		js.append("url:\"").append(url).append("\",");
		js.append("type:\"POST\",");
		js.append("async:true,");
		js.append("dataType:\"script\",");
		js.append("data:{");
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			String value = entry.getValue();
			js.append(entry.getKey()).append(':').append(StringUtils.isEmpty(value) ? "\"\"" : value);
			if (!lastKey.equals(entry.getKey())) {
				js.append(',');
			}
		}
		js.append("}");
		js.append("})");
		// 失敗時の処理
		js.append(".fail(function(data,textStatus,errorThrown){");
		js.append("if (data.status == 408) {");
		js.append("alert(\"セッションが切れました。\\n再度ログインして下さい。\");");
		js.append("}else{");
		js.append("alert(\"内部エラー(\"+textStatus+\":\"+errorThrown+\")\");");
		js.append("}");
		js.append("});");
		js.append("}");
		js.append("}");
		return js.toString();
	}

	/**
	 * Valueを設定するJavaScriptを取得する.
	 * 
	 * @param fromid
	 *            取得項目のID文字列
	 * @param toid
	 *            設定項目のID文字列
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsSetIdMainWindow(String fromid, String toid) {
		return this.getJsSetIdMainWindow(fromid, toid, null);
	}

	/**
	 * Valueを設定するJavaScriptを取得する(EventFire対応).
	 * 
	 * @param fromid
	 *            設定項目のID文字列
	 * @param toid
	 *            設定項目のID文字列
	 * @param event
	 *            fireするイベント（{@code null}の場合は、fireしない)
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsSetIdMainWindow(String fromid, String toid, EventHandlerEnum event) {
		StringBuilder js = new StringBuilder();
		js.append("window.opener.$('#").append(toid).append("').val(").append("$('#" + fromid + "').val()").append(")");
		if (event != null) {
			js.append(".").append(event.getSource()).append("()");
		}
		return js.append(";").toString();
	}

	/**
	 * Valueを設定するJavaScriptを取得する.
	 * 
	 * @param value
	 *            設定文字列
	 * @param toid
	 *            設定項目のID文字列
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsSetValueMainWindow(String value, String toid) {
		return this.getJsSetValueMainWindow(value, toid, null);
	}

	/**
	 * Valueを設定するJavaScriptを取得する(EventFire対応).
	 * 
	 * @param value
	 *            設定文字列
	 * @param toid
	 *            設定項目のID文字列
	 * @param event
	 *            fireするイベント（{@code null}の場合は、fireしない)
	 * @return JavaScript文字列({@code not null})
	 */
	public String getJsSetValueMainWindow(String value, String toid, EventHandlerEnum event) {
		StringBuilder js = new StringBuilder();
		js.append("window.opener.$('#").append(toid).append("').val('").append(value).append("')");
		if (event != null) {
			js.append(".").append(event.getSource()).append("()");
		}
		if (event != EventHandlerEnum.onChange) {
			// 変更フラグを設定するイベントをFire
			js.append(".not('._noChangeFlg, :disabled')");
			js.append(".trigger('").append(EventHandlerEnum.onChange.getSource()).append(".changeflg')");
		}
		return js.append(";").toString();
	}

	/**
	 * 確認ダイアログを表示するJavaScriptを取得する.
	 * 
	 * @param buttonId
	 *            ボタンID
	 * @param message
	 *            メッセージ
	 * @return JavaScript文字列({@code not null})
	 */
	public String getJsSetConfirm(String buttonId, String message) {
		StringBuilder js = new StringBuilder();

		js.append("$('#").append(buttonId).append("').click(function(){");
		js.append("if(!confirm('").append(message).append("')){");
		js.append("return false;");
		js.append("}");
		js.append("});");

		return js.append(";").toString();
	}

	/**
	 * ダイアログを表示するJavaScriptを取得する.
	 * 
	 * @param message
	 *            メッセージ
	 * @return JavaScript文字列({@code not null})
	 */
	public String getJsSetAlert(String message) {
		StringBuilder js = new StringBuilder();

		js.append("window.alert(\"" + message + "\");");

		return js.toString();
	}

	/**
	 * テーブル列の表示・非表示を表示するCSSを取得する.
	 * 
	 * @param table
	 *            対象テーブル名
	 * @param col
	 *            列番号
	 * @param isDisplay
	 *            表示・非表示
	 * @return Css文字列({@code not null})
	 */
	public String getCssSetTableDisplay(String table, String col, Boolean isDisplay) {
		StringBuilder js = new StringBuilder();

		js.append("#").append(table).append(">thead>tr>th:nth-child(").append(col).append(") { display: ")
				.append(isDisplay ? "block" : "none").append(";}");
		js.append("#").append(table).append(">tbody>tr>td:nth-child(").append(col).append(") { display: ")
				.append(isDisplay ? "block" : "none").append(";}");

		return js.toString();
	}

	/**
	 * テーブル列の表示・非表示を表示するJavaScriptを取得する.
	 * 
	 * @param table
	 *            対象テーブル名
	 * @param col
	 *            列番号
	 * @param isDisplay
	 *            表示・非表示
	 * @return JavaScript文字列({@code not null})
	 */
	public String getJsSetTableDisplay(String table, String col, Boolean isDisplay) {
		StringBuilder js = new StringBuilder();

		js.append("$('#").append(table).append(">thead>tr>th:nth-child(").append(col).append(")').css('display','")
				.append(isDisplay ? "table-cell" : "none").append("');");
		js.append("$('#").append(table).append(">tbody>tr>td:nth-child(").append(col).append(")').css('display','")
				.append(isDisplay ? "table-cell" : "none").append("');");

		return js.toString();
	}

	/**
	 * ボタン強調表示CSSを取得する.
	 * 
	 * @param buttonId
	 *            対象ボタンID
	 * @return Css文字列({@code not null})
	 */
	public String getCssButtonEmphasis(String buttonId) {
		StringBuilder js = new StringBuilder();

		js.append("#").append(buttonId)
				.append(" { outline-style: none; box-shadow: 2px 2px 3px 1px rgba(255,0,0,0.8);}");

		return js.toString();
	}

	/**
	 * Formのinputdata情報を取得する.
	 * 
	 * @param request
	 *            リクエスト情報
	 * @param name
	 *            name
	 */
	public String getFormSerializeArrayName(WebRequest request, String name) {

		int cnt = 0;
		String result = "";
		// 一覧表示複数選択チェックを初期化
		while (!KdcCommonUtils.nullSafeEquals(request.getParameter("inputdata[" + cnt + "][name]"), "")) {
			if (KdcCommonUtils.nullSafeEquals(
					KdcCommonUtils.nullToEmpty(request.getParameter("inputdata[" + cnt + "][name]")), name)) {
				result = request.getParameter("inputdata[" + cnt + "][value]");
				break;
			}
			cnt++;
		}

		return result;
	}

	/**
	 * ポップアップ画面の確認ボタン押下時に親画面のボタンをクリックするjavaScriptを戻す.
	 * 親画面のボタンが存在しない場合はなにもせずに画面を閉じる
	 * 
	 * @param buttonId
	 *            親画面クリックボタンID
	 * @return JavaScript文字列({@code not null})
	 */
	public String getJsPopupOkButton(String buttonId) {
		// JavaScript 生成
		StringBuilder js = new StringBuilder();
		if (StringUtils.isNotEmpty(buttonId)) {
			js.append("window.opener.$('#" + buttonId + "').click();");
		}
		js.append("window.close();");
		return js.toString();
	}

	/**
	 * 対象ＩＤに対して特定のイベントが発生した場合にボタンクリックを行うJavaScriptを取得.
	 * 
	 * @param eventId
	 *            イベント対象のID
	 * @param event
	 *            設定するトリガー種類
	 * @param buttonId
	 *            クリックボタンID
	 * @return JavaScript文字列({@code not null})
	 */
	public String getJsEventOnClick(String eventId, EventHandlerEnum event, String buttonId) {
		StringBuilder js = new StringBuilder();
		js.append("$('#");
		js.append(eventId);
		js.append("').");
		js.append(event.getSource());
		js.append("(function(){");
		js.append("$('#");
		js.append(buttonId);
		js.append("').trigger('click');})");

		return js.append(";").toString();
	}

	/**
	 * カレンダー表示/非表示を行うJavaScriptを取得する.
	 * 
	 * @param id
	 *            対象項目のID文字列
	 * @param isDisplay
	 *            表示(true)/非表示(false)
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsCalendarDisplay(String id, Boolean isDisplay) {
		StringBuilder sb = new StringBuilder();

		sb.append(this.getJsReadable(id, isDisplay));
		if (isDisplay) {
			sb.append("$(\"#").append(id).append("\").datepicker( \"option\", \"showOn\", \"button\");");
		} else {
			sb.append("$(\"#").append(id).append("\").datepicker( \"option\", \"showOn\", \"\");");
		}

		return sb.toString();
	}

	/**
	 * 数値項目のスピナー表示/非表示を行うJavaScriptを取得する.
	 * 
	 * @param id
	 *            対象項目のID文字列
	 * @param isDisplay
	 *            表示(true)/非表示(false)
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsSpinnerDisplay(String id, Boolean isDisplay) {
		if (isDisplay) {
			return new StringBuilder("$(\"#").append(id).append("\").spinner().spinner(\"enable\");").toString();
		} else {
			return new StringBuilder("$(\"#").append(id).append("\").spinner().spinner(\"disable\");").toString();
		}
	}

	/**
	 * readonlyの有効/無効を行うJavaScriptを取得する.
	 * 
	 * @param id
	 *            対象項目のID文字列
	 * @param isReadonly
	 *            編集可(true)/編集不可(false)
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsReadable(String id, Boolean isReadonly) {
		StringBuilder js = new StringBuilder();

		js.append("$(\"#").append(id).append("\").prop(\"readonly\",").append(!isReadonly).append(");");
		if (isReadonly) {
			js.append("$(\"#").append(id).append("\").removeClass(\"_readonly\");");
		} else {
			js.append("$(\"#").append(id).append("\").addClass(\"_readonly\");");
		}

		return js.toString();
	}

	/**
	 * readonlyの有効/無効を行うJavaScriptを取得する.
	 * 
	 * ＜p>
	 * 入力不可の場合はラベル風の表示を行う。
	 * ＜/p>
	 * 
	 * @param id
	 *            対象項目のID文字列
	 * @param isReadonly
	 *            編集可(true)/編集不可(false)
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsReadableToLabel(String id, Boolean isReadonly) {
		StringBuilder js = new StringBuilder();

		js.append("$(\"#").append(id).append("\").prop(\"readonly\",").append(!isReadonly).append(");");
		if (isReadonly) {
			js.append("$(\"#").append(id).append("\").removeClass(\"_attributeCodeDisp\");");
		} else {
			js.append("$(\"#").append(id).append("\").addClass(\"_attributeCodeDisp\");");
		}

		return js.toString();
	}

	/**
	 * 表示/非表示を表示するCSSを取得する(領域を確保).
	 * 
	 * @param id
	 *            項目名
	 * @param isVisibility
	 *            表示・非表示
	 * @return CSS文字列({@code not null})
	 */
	public String getCssSetVisibility(String id, Boolean isVisibility) {
		StringBuilder js = new StringBuilder();
		js.append("#").append(id).append(" { visibility: ").append(isVisibility ? "visible" : "hidden").append(";}");

		return js.toString();
	}

	/**
	 * 有効/無効を行うJavaScriptを取得する(領域を確保).
	 * 
	 * @param id
	 *            対象項目のID文字列
	 * @param isVisibility
	 *            有効(true)/無効(false)
	 * @return JavaScript文字列 ({@code not null})
	 */
	public String getJsVisibility(String id, Boolean isVisibility) {
		StringBuilder str = new StringBuilder();
		str.append("$(function(){");
		str.append("var element = document.getElementById(");
		str.append("'");
		str.append(id);
		str.append("');");
		str.append("element.style.visibility = '");
		str.append(isVisibility ? "visible" : "hidden");
		str.append("';});");
		return str.toString();
	}

	/**
	 * 画面表示時に指定のボタンをクリックするjavaScriptを戻す.
	 * 
	 * @param buttonId
	 *            画面クリックボタンID
	 * @return JavaScript文字列({@code not null})
	 */
	public String getJsButtonClick(String buttonId) {
		// JavaScript 生成
		StringBuilder js = new StringBuilder();
		if (StringUtils.isNotEmpty(buttonId)) {
			js.append("$('#" + buttonId + "').click();");
		}
		return js.toString();
	}

	/**
	 * エラーチェックJavaScript生成.
	 * 
	 * @return
	 */
	public String getErrorAjax(List<ErrorInfo> errorInfoList) {
		// JavaScript 生成
		StringBuilder js = new StringBuilder();

		for (ComErrorInfoData.ErrorInfo errorInfo : errorInfoList) {
			// 画面の色を変更
			js.append("$('#").append(errorInfo.getChangeColorId())
					.append("').closest('td').css('background-color', 'orange');");
		}

		// // 確認処理のJavaScriptを設定
		// js.append(this.javaScriptHolder.getJsPopupOkButton(buttonId));

		// エラー情報を削除
		errorInfoList = new ArrayList<ErrorInfo>();

		return js.toString();
	}

}