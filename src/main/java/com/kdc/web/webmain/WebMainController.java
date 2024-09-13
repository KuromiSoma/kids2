package com.kdc.web.webmain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.kdc.common.enums.WebPageEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.common.util.UrlUtils;
import com.kdc.web.base.MasterPageController;

import jakarta.servlet.http.HttpSession;

/**
 * メイン画面 Controller クラス
 * 
 * @author Hiasa1604
 *
 */
@Controller
@RequestMapping(CommonConst.WEB_BASE_URL + "/main")
@Transactional(rollbackFor = Exception.class)
public class WebMainController extends MasterPageController {


	@Autowired
	private WebMainService service;

	// ページ名称
	private static final String THISPAGE = WebPageEnum.MAIN.getId();

	@Override
	public String showPage(Model model) {
		return null;
	}

	/**
	 * 初期画面表示
	 * 
	 * @param model
	 * @param session
	 * @param loginId
	 *            ログインユーザID
	 * @return 画面表示
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showPageSend(Model model, HttpSession session, @RequestParam("loginId") String loginId) {
		// アクセス認証
		if (!StringUtils.equals(loginId, super.loginInfoHolder.getLoginId())
				|| !StringUtils.equals(session.getId(), super.loginInfoHolder.getSessionId())) {
			return null;
		}
		
		// 入力項目の初期値を設定する
		WebMainForm form = new WebMainForm();
//		//グループID初期設定
//		form.setCurrentGroupId(CommonConst.GROUP_ID_1);
//		form.setCmbGroupName(CommonConst.GROUP_ID_1);
		// 画面初期処理
		this.service.init(form);

		return this.showThisPage(model, form);
	}

	/**
	 * 画面表示共通処理.
	 * 
	 * @param model
	 * @param form
	 *            メイン画面フォーム
	 * @return 画面表示
	 */
	private String showThisPage(Model model, WebMainForm form) {
		// model.addAttribute("lblTitle", WebPageEnum.MAIN.getPageTitleLabel());

		// 入力項目
		model.addAttribute("form", form);

		model.addAttribute("loginId", super.loginInfoHolder.getLoginId());
		model.addAttribute("sessionId", super.loginInfoHolder.getSessionId());

		// APIキー
		model.addAttribute("googleMapApiKey", KdcCommonUtils.getProperty("googleMapApiKey"));
		// 再描画時間
		model.addAttribute("reloadTime", KdcCommonUtils.getProperty("mainReloadTime"));

		// 表示ユーザの件数によりリスト表示領域サイズ調整
		this.resizeUserListCol(form.getUserList().size());

		// マスタページ処理
		return setMasterPage(model, THISPAGE);
	}

	/**
	 * 表示ユーザの件数によりリスト表示領域サイズ調整.
	 * 
	 * @param userCount
	 *            ユーザ件数
	 */
	private void resizeUserListCol(int userCount) {

		if (userCount <= 10) {
			this.javaScriptHolder.addCssStyle(String.format("#userListViewArea { width: %dpx;}", userCount * 104));
		}
		this.javaScriptHolder.addCssStyle(String.format("#userListView { width: %dpx;}", userCount * 104));

		return;
	}

	/**
	 * リロード処理.
	 * 
	 * @param model
	 * @param form
	 *            メイン画面フォーム
	 * @return 画面表示
	 */
	@RequestMapping(params = "action=doReload", method = RequestMethod.POST)
	public String doReload(Model model, WebMainForm form) {

		//グループID初期設定
		form.setCurrentGroupId(form.getCurrentGroupId());
		form.setCmbGroupName(form.getCmbGroupName());
		// 画面初期処理
		this.service.init(form);

		model.addAttribute("isReload", true);

		return this.showThisPage(model, form);
	}

	/**
	 * 管理レベル別設定メニュー
	 * 
	 * @param model
	 * @param form
	 *            メイン画面フォーム
	 * @return リダイレクト先のパス文字列
	 */
	@RequestMapping(params = "action=doAuthConfig", method = RequestMethod.POST)
	public String doAuthConfig(Model model, WebMainForm form, RedirectAttributes attributes) {

		//遷移先にグループID値を設定
		attributes.addFlashAttribute("grouid", form.getCurrentGroupId());
		
		// 管理レベル別設定画面遷移処理
		return UrlUtils.getRedirectString(WebPageEnum.AUTHCONFIG.getId());
	}

	/**
	 * 場所設定メニュー
	 * 
	 * @param model
	 * @param form
	 *            メイン画面フォーム
	 * @return リダイレクト先のパス文字列
	 */
	@RequestMapping(params = "action=doPlaceConfig", method = RequestMethod.POST)
	public String doPlaceConfig(Model model, WebMainForm form, RedirectAttributes attributes) {

		// 遷移先に値を設定
		attributes.addFlashAttribute("zoomLevel", form.getMapZoomLevel());
		attributes.addFlashAttribute("centerLat", form.getMapLatitude());
		attributes.addFlashAttribute("centerLng", form.getMapLongitude());
		attributes.addFlashAttribute("grouid", form.getCurrentGroupId());

		// 履歴画面遷移処理
		return UrlUtils.getRedirectString(WebPageEnum.PLACECONFIG.getId());
	}

	/**
	 * バッテリー・移動履歴メニュー
	 * 
	 * @param model
	 * @param form
	 *            メイン画面フォーム
	 * @return リダイレクト先のパス文字列
	 */
	@RequestMapping(params = "action=doRecord", method = RequestMethod.POST)
	public String doRecord(Model model, WebMainForm form, RedirectAttributes attributes) {

		// form.getUserIdList(form.getSelectMarker())
		//
		// // 遷移先に値を設定
		// attributes.addFlashAttribute("selectUserId", selectUserId);

		// 履歴画面遷移処理
		return UrlUtils.getRedirectString(WebPageEnum.RECORD.getId());
	}

	/**
	 * ユーザ設定メニュー
	 * 
	 * @param model
	 * @param form
	 *            メイン画面フォーム
	 * @param attributes
	 * @return リダイレクト先のパス文字列
	 */
	@RequestMapping(params = "action=doUserConfig", method = RequestMethod.POST)
	public String doUserConfig(Model model, WebMainForm form, RedirectAttributes attributes) {

		// form.getUserIdList(form.getSelectMarker())
		//
		// // 遷移先に値を設定
		// attributes.addFlashAttribute("selectUserId", selectUserId);

		// ユーザ設定画面遷移処理
		return UrlUtils.getRedirectString(WebPageEnum.USERCONFIG.getId());
	}

}
