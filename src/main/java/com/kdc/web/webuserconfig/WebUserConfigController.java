package com.kdc.web.webuserconfig;

import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import com.kdc.common.enums.AuthLevelEnum;
import com.kdc.common.enums.WebPageEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.IconDataSet;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.common.util.KdcJavaScriptUtils;
import com.kdc.common.util.UrlUtils;
import com.kdc.web.base.MasterPageController;
import com.kdc.web.common.error.ComErrorInfoData.ErrorInfo;
import com.kdc.web.common.javascript.JavaScriptHolder;

/**
 * ユーザ設定画面 Controller クラス
 * 
 */
@Controller
@RequestMapping(CommonConst.WEB_BASE_URL + "/userconfig")
@Transactional(rollbackFor = Exception.class)
public class WebUserConfigController extends MasterPageController {

	@Autowired
	private WebUserConfigService service;

	// ページ名称
	private static final String THISPAGE = WebPageEnum.USERCONFIG.getId();

	@ModelAttribute("authLebels")
	public Map<String, String> setAuthLebelsList() {
		Map<String, String> radioItemList = new LinkedHashMap<>();

		for (AuthLevelEnum item : AuthLevelEnum.values()) {
			radioItemList.put(item.getCodeString(), item.getCodeLabel());
		}
		return radioItemList;
	}

	@ModelAttribute("markerColors")
	public Map<String, String> setMarkerColorsList() {
		Map<String, String> radioItemList = new TreeMap<>();

		// マーカーアイコン
		List<IconDataSet> markerIconList = KdcCommonUtils.getMarkerIconList();
		for (IconDataSet markerIcon : markerIconList) {
			String markerId = StringUtils.right(markerIcon.getIconName(), 2);
			String iconStringBase64 = KdcCommonUtils.getIconStringBase64(markerIcon.getIconData());
			radioItemList.put(markerId, CommonConst.ICON_IMG_HEADER + iconStringBase64);
		}

		return radioItemList;
	}

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
	 * @param selectUserId
	 *            選択ユーザID
	 * @return 画面表示
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showPageSend(Model model, HttpSession session, @RequestParam("loginId") String loginId,
			@RequestParam("selectUserId") String selectUserId) {
		// アクセス認証
		if (!StringUtils.equals(loginId, super.loginInfoHolder.getLoginId())
				|| !StringUtils.equals(session.getId(), super.loginInfoHolder.getSessionId())) {
			return null;
		}

		// 入力項目の初期値を設定する
		WebUserConfigForm form = new WebUserConfigForm();

		form.setSelectedUserId(selectUserId);
		// 画面初期処理
		service.initUser(model, form);

		return this.showThisPage(model, form);
	}

	/**
	 * 画面表示
	 * 
	 * @param model
	 * @param form
	 *            ユーザ設定画面フォーム
	 * @return
	 */
	private String showThisPage(Model model, WebUserConfigForm form) {
		model.addAttribute("lblTitle", WebPageEnum.USERCONFIG.getPageTitleLabel());

		// 入力項目
		model.addAttribute("form", form);

		// アイコン変更Ajax登録
		JavaScriptHolder.AjaxItem ajaxChangeIcon = this.javaScriptHolder.getNewItem();
		ajaxChangeIcon.addTrigger("changeIcon", JavaScriptHolder.EventHandlerEnum.onClick);
		this.javaScriptHolder.addAjaxScript(UrlUtils.getWebLinkUrl("/web/userconfig/ajax/onChangeIcon"), false,
				ajaxChangeIcon);

		// // 削除ダイアログ
		// this.javaScriptConfirmHolder.addItem("doDelete",
		// ConfirmMessageEnum.DELETE_USER, null);

		// マスタページ処理
		return setMasterPage(model, THISPAGE);
	}

	/**
	 * ユーザ変更
	 * 
	 * @param model
	 * @param form
	 *            ユーザ設定画面フォーム
	 * @return 画面表示
	 */
	@RequestMapping(params = "action=changeUser", method = RequestMethod.POST)
	public String changeUser(Model model, WebUserConfigForm form) {

		List<ErrorInfo> errorInfoList = new ArrayList<>();
		this.service.errorCheck(form, errorInfoList);
		if (errorInfoList.size() != 0) {
			this.javaScriptHolder.addJavaScript(this.javaScriptHolder.getErrorAjax(errorInfoList));
			service.initUser(model, form);
			return this.showThisPage(model, form);
		}

		// 画面初期処理
		service.initUser(model, form);

		return showThisPage(model, form);
	}

	/**
	 * 
	 * 登録ボタン押下時処理.
	 * 
	 * @param model
	 * @param form
	 *            ユーザ設定画面フォーム
	 * @return 画面表示
	 */
	@RequestMapping(params = "action=doCommit", method = RequestMethod.POST)
	public String doCommit(Model model, WebUserConfigForm webUserForm) {

		List<ErrorInfo> errorInfoList = new ArrayList<>();
		this.service.errorCheck(webUserForm, errorInfoList);
		if (errorInfoList.size() != 0) {
			this.javaScriptHolder.addJavaScript(this.javaScriptHolder.getErrorAjax(errorInfoList));
			service.initUser(model, webUserForm);
			return this.showThisPage(model, webUserForm);
		}

		// 入力値の登録処理
		service.registUserMaster(webUserForm);

		// 画面初期処理
		service.initUser(model, webUserForm);

		return this.showThisPage(model, webUserForm);
	}

	/**
	 * 戻るボタン押下時処理.
	 * 
	 * @param model
	 * @param webUserForm
	 *            ユーザ設定画面フォーム
	 * @return 遷移元画面
	 */
	@RequestMapping(params = "action=doCancel", method = RequestMethod.POST)
	public String doCancel(Model model, WebUserConfigForm webUserForm) {
		// 遷移元画面へ戻る
		return UrlUtils.getRedirectString(WebPageEnum.MAIN.getId());
	}

	/**
	 * ユーザ削除
	 * 
	 * @param model
	 * @param form
	 *            ユーザ設定画面フォーム
	 * @return 画面表示
	 */
	@RequestMapping(params = "action=doDelete", method = RequestMethod.POST)
	public String doDelete(Model model, WebUserConfigForm form) {
		// 削除処理
		this.service.deleteUser(model, form);

		return this.showThisPage(model, form);
	}

	/**
	 * 管理レベル変更Ajax処理.
	 * 
	 * @param request
	 * @param model
	 * @return アイコン画面変更js文字列
	 */
	@RequestMapping(value = "/ajax/onChangeIcon", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxOnChangeIcon(WebRequest request, Model model) {

		// アイコン画像表示用BASE64文字列(リサイズされていない)
		String iconDataString = KdcJavaScriptUtils.getFormSerializeArrayName(request, "selectedIconDataString");

		StringBuilder js = new StringBuilder();

		String iconStringBase64 = KdcCommonUtils
				.getIconStringBase64(Base64.getDecoder().decode(StringUtils.substringAfter(iconDataString, ",")));

		js.append(this.javaScriptHolder.getJsSettingImg("iconPreview", iconStringBase64));
		js.append(this.javaScriptHolder.getJsSetValue("selectedIconDataString", iconStringBase64));

		return js.toString();
	}

}
