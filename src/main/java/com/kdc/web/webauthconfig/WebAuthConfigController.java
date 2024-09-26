package com.kdc.web.webauthconfig;

import java.util.List;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.kdc.common.enums.AuthLevelEnum;
import com.kdc.common.enums.NotificationTypeEnum;
import com.kdc.common.enums.WebPageEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.web.base.MasterPageController;

/**
 * 管理レベル設定画面 Controller クラス
 * 
 *
 */
@Controller
@RequestMapping(CommonConst.WEB_BASE_URL + "/authconfig")
@Transactional(rollbackFor = Exception.class)
public class WebAuthConfigController extends MasterPageController {

	@Autowired
	private WebAuthConfigService service;

	@Autowired
	private WebAuthConfigData data;

	// ページ名称
	private static final String THISPAGE = WebPageEnum.AUTHCONFIG.getId();

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
	 * 		ログインユーザID
	 * @return 画面表示
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showPageSend(Model model, HttpSession session, @RequestParam("loginId") String loginId, @RequestParam("groupid") String groupid) {
		// アクセス認証
		if (!StringUtils.equals(loginId, super.loginInfoHolder.getLoginId())
				|| !StringUtils.equals(session.getId(), super.loginInfoHolder.getSessionId())) {
			return null;
		}

		// 入力項目の初期値を設定する
		WebAuthConfigForm form = new WebAuthConfigForm();
		// mainから受け取った初期表示情報を設定する
		// 表示グループID
		form.setCurrentGroupId(groupid);
		form.setCmbAuthGroup(groupid);

		// 画面初期処理
		this.service.initData(form);
		this.service.initAuthConfigForm(form, AuthLevelEnum.GUEST, "1");
		this.service.initUserForm(form, AuthLevelEnum.GUEST);
		this.service.initPlaceForm(form, AuthLevelEnum.GUEST);
		this.service.initTransmissionForm(form, AuthLevelEnum.GUEST);
		this.service.initLevelUser(form,AuthLevelEnum.GUEST);
		this.service.initCommon(form);

		return this.showThisPage(model, form);
	}

	/**
	 * 画面表示共通処理.
	 * 
	 * @param model
	 * @param authForm
	 * 			管理設定画面フォーム
	 * @return 画面表示
	 */
	private String showThisPage(Model model, WebAuthConfigForm authForm) {
		model.addAttribute("lblTitle", WebPageEnum.AUTHCONFIG.getPageTitleLabel());

		// 入力項目
		model.addAttribute("form", authForm);

		model.addAttribute("authLevelEnums", AuthLevelEnum.values());
		model.addAttribute("notificationTypeEnums", NotificationTypeEnum.values());

		// ユーザ一覧のHTML文字列を出力
		model.addAttribute("tblThisLevelUserBody", this.createTblThisLevelUserBody(this.data.getThisLevelUser(), null));
		model.addAttribute("tblOtherLevelUserBody",
				this.createTblOtherLevelUserBody(this.data.getOtherLevelUser(), null));

		// 場所の件数によりテーブル列サイズ調整
		this.resizePlaceTableCol();

		// マスタページ処理
		return setMasterPage(model, THISPAGE);
	}

	/**
	 * 登録ボタン押下時処理.
	 * 
	 * @param model
	 * @param form
	 * 			管理設定画面フォーム
	 * @return 画面表示
	 */
	@RequestMapping(params = "action=doCommit", method = RequestMethod.POST)
	public String doCommit(Model model, WebAuthConfigForm form) {
		// 画面別の登録処理
		switch (form.getDispBlock()) {
		case "1":
			this.service.registerUserDisp(form);
			break;
		case "2":
			this.service.registerUserAlert(form);
			break;
		case "3":
			this.service.registerPlaceDisp(form);
			this.service.registerPlaceAlert(form);
			break;
		case "4":
			this.service.registerTransmission(form);
			break;
		case "5":
			this.service.registerLevelUser(form);
			break;
		case "6":
			this.service.registerCommonConfig(form);
			this.service.registerGroup(form);
			form.setCmbAuthLevel(form.getCurrentAuthLevel());
			break;
		}

		AuthLevelEnum nextAuthLevel = AuthLevelEnum.valueOf(Integer.parseInt(form.getCmbAuthLevel()));
		if (( StringUtils.equals(form.getCmbAuthLevel(), form.getCurrentAuthLevel()) && 
				( StringUtils.equals(form.getCmbAuthGroup(), form.getCurrentGroupId()))) ) {
			// 次の画面フォーム用のデータをリロード
			this.service.reloadAuthConfigForm(form, nextAuthLevel, form.getNextDispBlock());
		} else {
			// 変更後管理レベルのデータをロード
			this.service.initData(form);
			this.service.initAuthConfigForm(form, nextAuthLevel, form.getDispBlock());
			this.service.initUserForm(form, nextAuthLevel);
			this.service.initPlaceForm(form, nextAuthLevel);
			this.service.initTransmissionForm(form, nextAuthLevel);
			this.service.initLevelUser(form,nextAuthLevel);
			this.service.initCommon(form);
			//現在のグループ番号設定
			form.setCurrentGroupId(form.getCmbAuthGroup());
			form.setNewGroupName(form.getGroupName());
		}

		return this.showThisPage(model, form);
	}

	/**
	 * 戻るボタン押下時処理.
	 * 
	 * @param model
	 * @param form
	 * 			管理設定画面フォーム
	 * @return 遷移元画面表示
	 */
	@RequestMapping(params = "action=doFinalCommit", method = RequestMethod.POST)
	public String doFinalCommit(Model model, WebAuthConfigForm form) {
		// 画面別の登録処理
		switch (form.getDispBlock()) {
		case "1":
			this.service.registerUserDisp(form);
			break;
		case "2":
			this.service.registerUserAlert(form);
			break;
		case "3":
			this.service.registerPlaceDisp(form);
			this.service.registerPlaceAlert(form);
			break;
		case "4":
			this.service.registerTransmission(form);
			break;
		case "5":
			this.service.registerLevelUser(form);
			break;
		case "6":
			this.service.registerCommonConfig(form);
			form.setCmbAuthLevel(form.getCurrentAuthLevel());
			break;
		}

		// 遷移元画面へ戻る
		return this.javaScriptHolder.getJsButtonClick("doClose");
	}

	/**
	 * 場所の件数によりテーブル列サイズ調整.
	 */
	private void resizePlaceTableCol() {
		int placeCounts = this.data.getPlaceList().size();

		if (placeCounts == 0) {
			this.javaScriptHolder.addCssStyle(String.format("#listPlaceAlertColView { width: %dpx;}", 0));
			this.javaScriptHolder.addCssStyle(String.format("#listPlaceAlertView { width: %dpx;}", 0));
		} else if (placeCounts <= 3) {
			this.javaScriptHolder
					.addCssStyle(String.format("#listPlaceAlertColView { width: %dpx;}", placeCounts * 330));
			this.javaScriptHolder
					.addCssStyle(String.format("#listPlaceAlertView { width: %dpx;}", placeCounts * 330 + 17));
			this.javaScriptHolder.addCssStyle(String.format("#listPlaceAlertView { height: %dpx;}", 481));
		} else {
			this.javaScriptHolder.addCssStyle(String.format("#listPlaceAlertColView { width: %dpx;}", 990));
			this.javaScriptHolder.addCssStyle(String.format("#listPlaceAlertView { width: %dpx;}", 1007));
		}
		this.javaScriptHolder.addCssStyle(String.format("#listPlaceDispBody { width: %dpx;}", placeCounts * 280));
		this.javaScriptHolder.addCssStyle(String.format("#listPlaceAlertColHeader { width: %dpx;}", placeCounts * 330));
		this.javaScriptHolder.addCssStyle(String.format("#listPlaceAlertBody { width: %dpx;}", placeCounts * 330));

		return;

	}

	/**
	 * 所属ユーザ一覧作成.
	 * 
	 * @param userList
	 * 			管理レベル別ユーザクラス
	 * @param funcName
	 * @return html
	 */
	private String createTblThisLevelUserBody(List<AuthUser> userList, String funcName) {
		StringBuilder html = new StringBuilder();

		// ユーザリストより、一覧表の行を生成
		int row = 0;
		for (AuthUser user : userList) {
			row++;
			html.append(this.createTblThisLevelUserBodyLine(row, user, funcName));
		}

		return html.toString();
	}

	/**
	 * 所属ユーザ一覧の一行作成処理.
	 * 
	 * @param row
	 * 			行数カウント
	 * @param user
	 * 			管理レベル別ユーザクラス
	 * @param funcName
	 * @return html
	 */
	private StringBuilder createTblThisLevelUserBodyLine(int row, AuthUser user, String funcName) {
		StringBuilder html = new StringBuilder("<tr>");
		html.append("<td>");
		// ユーザ名
		html.append("<span>");
		if (user != null) {
			html.append(user.getUserName());
		}
		html.append("</span>");
		// ユーザID（隠し項目）
		html.append("<input type=\"hidden\"");
		html.append(" name=\"thisLevelUser[").append(row - 1).append("].userId\"");
		html.append(" id=\"thisLevelUserId").append(row - 1).append("\"");
		html.append(" value=\"").append(user.getUserId()).append("\"");
		html.append(" />");
		html.append("</td>");
		html.append("<td>");
		if (StringUtils.isNotEmpty(user.getTelephoneNumber())) {
			html.append(user.getTelephoneNumber());
		}
		html.append("</td>");
		html.append("</tr>");
		return html;
	}

	/**
	 * 他レベル所属ユーザ一覧作成.
	 * 
	 * @param userList
	 * 			管理レベル別ユーザクラスリスト
	 * @param funcName
	 * @return html
	 */
	private String createTblOtherLevelUserBody(List<AuthUser> userList, String funcName) {
		StringBuilder html = new StringBuilder();

		// ユーザリストより、一覧表の行を生成
		int row = 0;
		for (AuthUser user : userList) {
			row++;
			html.append(this.createTblOtherLevelUserBodyLine(row, user, funcName));
		}

		return html.toString();
	}

	/**
	 * 他レベル所属ユーザ一覧の一行作成処理.
	 * 
	 * @param row
	 * 			行数カウント
	 * @param user
	 * 			管理レベル別ユーザクラス
	 * @param funcName
	 * @return html
	 */
	private StringBuilder createTblOtherLevelUserBodyLine(int row, AuthUser user, String funcName) {
		StringBuilder html = new StringBuilder("<tr>");
		html.append("<td>");
		// ユーザ名
		html.append("<span>");
		if (user != null) {
			html.append(user.getUserName());
		}
		html.append("</span>");
		// ユーザID（隠し項目）
		html.append("<input type=\"hidden\"");
		html.append(" name=\"otherLevelUser[").append(row - 1).append("].userId\"");
		html.append(" id=\"otherLevelUserId").append(row - 1).append("\"");
		html.append(" value=\"").append(user.getUserId()).append("\"");
		html.append(" />");
		html.append("</td>");
		// 選択
		html.append("<td>");
		html.append("<input type=\"checkbox\"");
		html.append(" name=\"otherLevelUser[").append(row - 1).append("].authCheck\"");
		html.append(" id=\"authCheck").append(row - 1).append("\"");
		html.append(" value=\"").append(user.getUserId()).append("\"");
		html.append(" />");
		html.append("</td>");
		return html.append("</tr>");
	}

}
