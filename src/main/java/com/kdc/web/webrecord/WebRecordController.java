package com.kdc.web.webrecord;

import java.sql.Timestamp;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import com.kdc.common.enums.WebPageEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.common.util.UrlUtils;
import com.kdc.web.base.MasterPageController;

/**
 * 履歴画面 Controller クラス
 * 
 *
 */
@Controller
@RequestMapping(CommonConst.WEB_BASE_URL + "/record")
public class WebRecordController extends MasterPageController {

	@Autowired
	private WebRecordService service;

	// ページ名称
	private static final String THISPAGE = WebPageEnum.RECORD.getId();

	@Override
	public String showPage(Model model) {
		return null;
	}

	/**
	 * 初期画面表示.
	 * 
	 * @param model
	 * @param session
	 * @param loginId
	 *            ログインユーザID
	 * @param selectUserId
	 *            選択ユーザID
	 * @param zoomLevel
	 *            ズームレベル
	 * @param centerLat
	 *            中心緯度座標
	 * @param centerLng
	 *            中心経度座標
	 * @return 画面表示
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showPageSend(Model model, HttpSession session, @RequestParam("loginId") String loginId,
			@RequestParam("selectUserId") String selectUserId, @RequestParam("zoomLevel") String zoomLevel,
			@RequestParam("centerLat") String centerLat, @RequestParam("centerLng") String centerLng) {
		// アクセス認証
		if (!StringUtils.equals(loginId, super.loginInfoHolder.getLoginId())
				|| !StringUtils.equals(session.getId(), super.loginInfoHolder.getSessionId())) {
			return null;
		}

		WebRecordForm form = new WebRecordForm();
		// mainから受け取った初期表示情報を設定する
		form.setUserId(selectUserId);
		// Map初期表示中心座標
		form.setMapLatitude(centerLat);
		form.setMapLongitude(centerLng);
		// Map初期表示ズームレベル
		form.setMapZoomLevel(zoomLevel);

		// 日付表示
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();
		form.setViewDate(KdcCommonUtils.timestampToCalendarDateString(nowTimestamp));
		form.setDate(KdcCommonUtils.timestampToDateString(nowTimestamp));

		// 入力項目の初期値を設定
		form.setDispBlock("1");
		this.service.initForm(form.getDate(), form, selectUserId);

		return this.showThisPage(model, form);
	}

	/**
	 * 画面表示共通処理.
	 * 
	 * @param model
	 * @param form
	 *            履歴画面フォーム
	 * @return 画面表示
	 */
	private String showThisPage(Model model, WebRecordForm form) {
		model.addAttribute("lblTitle", WebPageEnum.RECORD.getPageTitleLabel());

		// 画面初期化処理
		// 入力項目
		model.addAttribute("form", form);

		// APIキー
		model.addAttribute("googleMapApiKey", KdcCommonUtils.getProperty("googleMapApiKey"));

		// マスタページ処理
		return setMasterPage(model, THISPAGE);
	}

	/**
	 * カレンダー切替え=submit処理.
	 * 
	 * @param request
	 * @param model
	 * @param form
	 *            履歴画面フォーム
	 * @return 画面表示
	 * @throws Exception
	 */
	@RequestMapping(params = "action=doReload", method = RequestMethod.POST)
	public String doReload(WebRequest request, Model model, WebRecordForm form) throws Exception {

		// 日付表示
		Timestamp nowTimestamp = KdcCommonUtils.dateTimeStringToTimestamp(form.getReceiveDate() + "000000");
		form.setViewDate(KdcCommonUtils.timestampToCalendarDateString(nowTimestamp));
		form.setDate(KdcCommonUtils.timestampToDateString(nowTimestamp));

		// 入力項目の初期値を設定
		this.service.initForm(form.getReceiveDate(), form, form.getUserId());

		return this.showThisPage(model, form);
	}

	/**
	 * 戻るボタン押下時処理.
	 * 
	 * @param model
	 * @param form
	 *            履歴画面フォーム
	 * @return 遷移元画面
	 */
	@RequestMapping(params = "action=doCancel", method = RequestMethod.POST)
	public String doClose(Model model, WebRecordForm form) {
		// 遷移元画面へ戻る
		return UrlUtils.getRedirectString(WebPageEnum.MAIN.getId());
	}
}
