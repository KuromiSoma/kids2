package com.kdc.web.webplaceconfig;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.kdc.common.enums.WebPageEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.common.util.UrlUtils;
import com.kdc.web.base.MasterPageController;
import com.kdc.web.common.error.ComErrorInfoData.ErrorInfo;

/**
 * 場所設定画面 Controller クラス
 * 
 * @author Hiasa1604
 *
 */
@Controller
@RequestMapping(CommonConst.WEB_BASE_URL + "/placeconfig")
@Transactional(rollbackFor = Exception.class)
public class WebPlaceConfigController extends MasterPageController {

	@Autowired
	private WebPlaceConfigService service;

	// ページ名称
	private static final String THISPAGE = WebPageEnum.PLACECONFIG.getId();

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
	 * 			ログインユーザID
	 * @param zoomLevel
	 * 			ズームレベル
	 * @param centerLat
	 * 			中心緯度座標
	 * @param centerLng
	 * 			中心経度座標
	 * @return 画面表示
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showPageSend(Model model, HttpSession session, @RequestParam("loginId") String loginId,
			@RequestParam("zoomLevel") String zoomLevel, @RequestParam("centerLat") String centerLat,
			@RequestParam("centerLng") String centerLng, @RequestParam("groupid") String groupid) {
		// アクセス認証
		if (!StringUtils.equals(loginId, super.loginInfoHolder.getLoginId())
				|| !StringUtils.equals(session.getId(), super.loginInfoHolder.getSessionId())) {
			return null;
		}

		// 入力項目の初期値を設定する
		WebPlaceConfigForm form = new WebPlaceConfigForm();

		// mainから受け取った初期表示情報を設定する
		// Map初期表示中心座標
		form.setMapLatitude(centerLat);
		form.setMapLongitude(centerLng);
		// Map初期表示ズームレベル
		form.setMapZoomLevel(zoomLevel);
		// グループID
		form.setGroupid(groupid);

		// 画面初期処理
		this.service.init(form);

		return showThisPage(model, form);
	}

	/**
	 * 画面表示
	 * @param model
	 * @param form
	 * 			場所設定画面フォーム
	 * @return マスタページ処理
	 */
	private String showThisPage(Model model, WebPlaceConfigForm form) {
		model.addAttribute("lblTitle", WebPageEnum.PLACECONFIG.getPageTitleLabel());

		// 入力項目
		model.addAttribute("form", form);
		model.addAttribute("radiusMin", CommonConst.PLACE_RADIUS_MIN);
		model.addAttribute("radiusMax", CommonConst.PLACE_RADIUS_MAX);
		model.addAttribute("radiusDefault", CommonConst.DEFAULT_PLACE_RADIUS);

		// APIキー
		model.addAttribute("googleMapApiKey", KdcCommonUtils.getProperty("googleMapApiKey"));

		// // 削除ダイアログ
		// this.javaScriptConfirmHolder.addItem("doDelete",
		// ConfirmMessageEnum.DELETE_PLACE, null);

		// マスタページ処理
		return setMasterPage(model, THISPAGE);
	}

	/**
	 * 登録ボタン押下時処理.
	 * 
	 * @param model
	 * @param form
	 * 			場所設定画面フォーム
	 * @param zoomLevel
	 * 			ズームレベル
	 * @param centerLat
	 * 			中心緯度座標
	 * @param centerLng
	 * 			中心経度座標
	 * @return 画面表示
	 */
	@RequestMapping(params = "action=doCommit", method = RequestMethod.POST)
	public String doCommit(Model model, WebPlaceConfigForm form, @RequestParam("mapZoomLevel") String zoomLevel,
			@RequestParam("mapLatitude") String centerLat, @RequestParam("mapLongitude") String centerLng) {

		// Map初期表示中心座標
		form.setMapLatitude(centerLat);
		form.setMapLongitude(centerLng);
		// Map初期表示ズームレベル
		form.setMapZoomLevel(zoomLevel);

		List<ErrorInfo> errorInfoList = new ArrayList<>();
		this.service.errorCheck(form, errorInfoList);
		if (errorInfoList.size() != 0) {
			this.javaScriptHolder.addJavaScript(this.javaScriptHolder.getErrorAjax(errorInfoList));

			return showThisPage(model, form);
		}

		if (StringUtils.isNotEmpty(form.getSelectedPlaceId())) {
			this.service.updatePlace(form);
		} else {
			this.service.insertNewPlace(form);
		}

		// 場所リストをDBより再取得して表示する
		this.service.init(form);

		return showThisPage(model, form);
	}

	/**
	 * 場所削除処理.
	 * 
	 * @param model
	 * @param form
	 * 			場所設定画面フォーム
	 * @param zoomLevel
	 * 			ズームレベル
	 * @param centerLat
	 * 			中心緯度座標
	 * @param centerLng
	 * 			中心経度座標
	 * @return 画面表示
	 */
	@RequestMapping(params = "action=doDelete", method = RequestMethod.POST)
	public String doDelete(Model model, WebPlaceConfigForm form, @RequestParam("mapZoomLevel") String zoomLevel,
			@RequestParam("mapLatitude") String centerLat, @RequestParam("mapLongitude") String centerLng) {

		// Map初期表示中心座標
		form.setMapLatitude(centerLat);
		form.setMapLongitude(centerLng);
		// Map初期表示ズームレベル
		form.setMapZoomLevel(zoomLevel);

		// 場所削除
		this.service.deletePlace(form);

		// 場所リストをDBより再取得して表示する
		this.service.init(form);

		return showThisPage(model, form);
	}

	/**
	 * 戻るボタン押下時処理.
	 * 
	 * @param model
	 * @param form
	 * 			場所設定画面フォーム
	 * @return 遷移元画面
	 */
	@RequestMapping(params = "action=doCancel", method = RequestMethod.POST)
	public String doClose(Model model, WebPlaceConfigForm form) {

		// 遷移元画面へ戻る
		return UrlUtils.getRedirectString(WebPageEnum.MAIN.getId());
	}
}
