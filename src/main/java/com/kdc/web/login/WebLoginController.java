package com.kdc.web.login;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kdc.common.enums.WebPageEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.UrlUtils;
import com.kdc.web.base.MasterPageController;

/**
 * ログイン画面 Controller クラス
 * 
 *
 */
@Controller
@RequestMapping(CommonConst.WEB_BASE_URL + "/login")
@Transactional(rollbackFor = Exception.class)
public class WebLoginController extends MasterPageController {

	@Autowired
	private WebLoginService service;

	// ページ名称
	private static final String THISPAGE = WebPageEnum.LOGIN.getId();

	/**
	 * 初期画面表示
	 */
	@RequestMapping(method = RequestMethod.GET)
	@Override
	public String showPage(Model model) {
		model.addAttribute("lblTitle", WebPageEnum.LOGIN.getPageTitleLabel());

		// 入力項目の初期値を設定
		WebLoginForm form = new WebLoginForm();

		// 画面初期化処理
		this.service.init(form);

		model.addAttribute("form", form);

		// マスタページ処理
		return setMasterPage(model, THISPAGE);
	}

	/**
	 * 登録ボタン押下時処理.
	 * 
	 * @param model
	 * @param form
	 * 			ログイン画面フォーム
	 * @param session
	 * @param attributes
	 * @return リダイレクト先のパス文字列
	 */
	@RequestMapping(params = "action=doCommit", method = RequestMethod.POST)
	public String doCommit(Model model, WebLoginForm form, HttpSession session, RedirectAttributes attributes) {

		// 管理者問合せ
		if (!this.service.getAdministrator(form)) {
			form.setLoginErrorFlg(true);

			// login再表示
			model.addAttribute("lblTitle", WebPageEnum.LOGIN.getPageTitleLabel());
			model.addAttribute("form", form);
			return THISPAGE;
		} else {
			super.loginInfoHolder.setLoginId(form.getLoginId());
			super.loginInfoHolder.setSessionId(session.getId());
			// 遷移先に値を設定
			attributes.addAttribute("loginId", form.getLoginId());
			attributes.addAttribute("sessionId", session.getId());

			// mainへ遷移
			return UrlUtils.getRedirectString(WebPageEnum.MAIN.getId());
		}
	}

}