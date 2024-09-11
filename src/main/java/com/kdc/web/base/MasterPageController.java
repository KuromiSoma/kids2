package com.kdc.web.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.kdc.web.common.WebLoginInfoHolder;
import com.kdc.web.common.javascript.JavaScriptConfirmHolder;
import com.kdc.web.common.javascript.JavaScriptHolder;
import com.kdc.web.masterpage.MasterPage;

/**
 * マスタページクラス
 */
public abstract class MasterPageController {

	// ログイン情報保持
	@Autowired
	protected WebLoginInfoHolder loginInfoHolder;

	// JavaScript 情報保持
	@Autowired
	protected JavaScriptHolder javaScriptHolder;

	// 確認メッセージ情報保持
	@Autowired
	protected JavaScriptConfirmHolder javaScriptConfirmHolder;

	// マスタページ処理
	@Autowired
	private MasterPage masterPage;

	/**
	 * 初期画面表示.
	 * 
	 * @see org.springframework.web
	 */
	public abstract String showPage(Model model);

	/**
	 * マスターページ処理.
	 * 
	 * @param model
	 *            org.springframework.ui.Model
	 */
	protected String setMasterPage(Model model, String page) {
		this.masterPage.showPage(model);
		return page;
	}

}