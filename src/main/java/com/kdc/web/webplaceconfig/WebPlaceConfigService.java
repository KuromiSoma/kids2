package com.kdc.web.webplaceconfig;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.db.PlaceAlertConfigEntity;
import com.kdc.common.entity.db.PlaceDisplayConfigEntity;
import com.kdc.common.entity.db.PlaceMasterEntityWrapper;
import com.kdc.common.enums.AuthLevelEnum;
import com.kdc.common.enums.PlaceTypeEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.CommonService;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.common.util.IconDataSet;
import com.kdc.mybatis.mapper.common.entity.PlaceAlertConfigMapper;
import com.kdc.mybatis.mapper.common.entity.PlaceDisplayConfigMapper;
import com.kdc.mybatis.mapper.common.entity.PlaceMasterMapper;
import com.kdc.web.common.error.ComErrorInfoData.ErrorInfo;
import com.kdc.web.common.WebLoginInfoHolder;
import com.kdc.web.common.error.ComErrorInfoService;

/**
 * 場所設定画面 Service クラス
 * 
 * @author s.hiasa
 *
 */
@Service
public class WebPlaceConfigService {

	@Autowired
	private WebLoginInfoHolder loginInfoHolder;

	@Autowired
	private PlaceMasterMapper placeMasterMapper;

	@Autowired
	private PlaceDisplayConfigMapper placeDisplayConfigMapper;

	@Autowired
	private PlaceAlertConfigMapper placeAlertConfigMapper;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ComErrorInfoService comErrorInfoService;

	/**
	 * 初期処理.
	 * 
	 * @param form
	 * 			場所設定画面フォーム
	 */
	public void init(WebPlaceConfigForm form) {

		form.setNewRadius(String.valueOf(CommonConst.DEFAULT_PLACE_RADIUS));

		// アイコンデータリスト作成
		StringBuilder iconDataListStr = new StringBuilder();
		List<IconDataSet> placeIconList = KdcCommonUtils.getPlaceIconList();
		for (IconDataSet placeIcon : placeIconList) {
			iconDataListStr.append(placeIcon.getIconName()).append(",");
			iconDataListStr.append(Base64.getEncoder().encodeToString(placeIcon.getIconData())).append(";");
		}
		form.setPlaceIconDataList(StringUtils.removeEnd(iconDataListStr.toString(), ";"));

		StringBuilder placeTypeColorListStr = new StringBuilder();
		for (PlaceTypeEnum item : PlaceTypeEnum.values()) {
			placeTypeColorListStr.append(item.getCode()).append(",");
			placeTypeColorListStr.append(item.getCircleColor()).append(",");
			placeTypeColorListStr.append(item.getEditColor()).append(";");
		}
		form.setPlaceTypeColorList(StringUtils.removeEnd(placeTypeColorListStr.toString(), ";"));

		// DBより場所リスト取得
		List<PlaceMasterEntityWrapper> placeList = placeMasterMapper.selectAll(CommonConst.FLG_OFF,form.getGroupid(), true);

		// 場所リストを表示用隠し項目に設定する

		// 場所（座標）リスト文字列
		StringBuilder pointListStr = new StringBuilder();

		// 半径リスト文字列
		StringBuilder radiusListStr = new StringBuilder();

		// 場所IDリスト文字列
		StringBuilder placeIdListStr = new StringBuilder();

		// 場所名リスト文字列
		StringBuilder placeNameListStr = new StringBuilder();

		// アイコンリスト文字列
		StringBuilder placeIconIdListStr = new StringBuilder();

		// 場所タイプリスト
		StringBuilder placeTypeListStr = new StringBuilder();

		for (PlaceMasterEntityWrapper place : placeList) {

			// 場所リスト作成
			pointListStr.append(place.getLatitude()).append(",").append(place.getLongitude()).append(";");

			// 半径リスト作成
			radiusListStr.append(place.getRadius()).append(";");

			// 場所IDリスト作成
			placeIdListStr.append(place.getPlaceid()).append(";");

			// 場所名リスト作成
			placeNameListStr.append(place.getPlacename()).append(";");

			// アイコンリスト作成
			placeIconIdListStr.append(StringUtils.remove(place.getIconid(), " ")).append(";");

			// 場所タイプリスト作成
			placeTypeListStr.append(place.getPlacetypeflg()).append(";");

		}

		// 末尾の区切り文字を切り捨てて格納
		// 場所リスト
		form.setPointList(StringUtils.removeEnd(pointListStr.toString(), ";"));
		// 半径リスト
		form.setRadiusList(StringUtils.removeEnd(radiusListStr.toString(), ";"));
		// 場所IDリスト
		form.setPlaceIdList(StringUtils.removeEnd(placeIdListStr.toString(), ";"));
		// 場所名リスト
		form.setPlaceNameList(StringUtils.removeEnd(placeNameListStr.toString(), ";"));
		// アイコンリスト
		form.setPlaceIconIdList(StringUtils.removeEnd(placeIconIdListStr.toString(), ";"));
		// 場所タイプリスト
		form.setPlaceTypeList(StringUtils.removeEnd(placeTypeListStr.toString(), ";"));

	}

	/**
	 * エラーチェック.
	 * 
	 * @param form
	 * 			場所設定画面フォーム
	 * @param errorInfoList
	 * 			エラー情報クラスリスト
	 */
	public void errorCheck(WebPlaceConfigForm form, List<ErrorInfo> errorInfoList) {
		// エラーチェック処理(共通化できないエラー処理は独自に実装する)
		this.comErrorInfoService.errorInfoInit();
		// 文字数チェック
		this.comErrorInfoService.stringSizeErrorCheck("場所名称", form.getNewPlaceName(), 15, "newPlaceName");

		errorInfoList.addAll(this.comErrorInfoService.getErrorInfoList());
	}

	/**
	 * 新規場所登録処理.
	 * 
	 * @param form
	 * 			場所設定画面フォーム
	 */
	public void insertNewPlace(WebPlaceConfigForm form) {

		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();

		// 新規登録用Entityを用意（場所通知は設定しない）
		PlaceMasterEntityWrapper newPlace = new PlaceMasterEntityWrapper();
		PlaceDisplayConfigEntity newDisp = new PlaceDisplayConfigEntity();

		String placeId = this.commonService.createPlaceId();

		// 場所マスタ
		newPlace.setGroupid(form.getGroupid());
		newPlace.setPlaceid(placeId);
		newPlace.setPlacename(form.getNewPlaceName());
		newPlace.setPlacetypeflg(Integer.parseInt(form.getNewPlaceTypeCd()));
		newPlace.setIconid(form.getNewPlaceIcon());
		newPlace.setLatitude(form.getNewLatitude());
		newPlace.setLongitude(form.getNewLongitude());
		newPlace.setRadius(Integer.parseInt(form.getNewRadius()));
		newPlace.setRegisteruserid(this.loginInfoHolder.getLoginId());
		newPlace.setRegisterdate(nowTimestamp);
		this.placeMasterMapper.insert(newPlace);

		// 場所表示設定
		newDisp.setPlaceid(placeId);
		newDisp.setPlacedispflg(CommonConst.FLG_ON);
		newDisp.setRegisteruserid(this.loginInfoHolder.getLoginId());
		newDisp.setRegisterdate(nowTimestamp);
		for (AuthLevelEnum item : AuthLevelEnum.values()) {
			newDisp.setAuthlevel(item.getCode());
			this.placeDisplayConfigMapper.insert(newDisp);
		}
	}

	/**
	 * 場所更新処理.
	 * 
	 * @param form
	 * 			場所設定画面フォーム
	 */
	public void updatePlace(WebPlaceConfigForm form) {

		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();

		// 登録用Entityを用意
		PlaceMasterEntityWrapper newPlace = new PlaceMasterEntityWrapper();

		// 場所ID設定
		newPlace.setPlaceid(form.getSelectedPlaceId());

		newPlace.setPlacename(form.getNewPlaceName());
		newPlace.setPlacetypeflg(Integer.parseInt(form.getNewPlaceTypeCd()));
		newPlace.setIconid(form.getNewPlaceIcon());
		newPlace.setLatitude(form.getNewLatitude());
		newPlace.setLongitude(form.getNewLongitude());
		newPlace.setRadius(Integer.parseInt(form.getNewRadius()));
		newPlace.setUpdateuserid(this.loginInfoHolder.getLoginId());
		newPlace.setUpdatedate(nowTimestamp);

		// 更新
		this.placeMasterMapper.update(newPlace);
	}

	/**
	 * 場所削除処理.
	 * 
	 * @param form
	 * 			場所設定画面フォーム
	 */
	public void deletePlace(WebPlaceConfigForm form) {

		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();

		// 登録用Entityを用意
		PlaceMasterEntityWrapper newPlace = new PlaceMasterEntityWrapper();
		PlaceDisplayConfigEntity newDisp = new PlaceDisplayConfigEntity();
		PlaceAlertConfigEntity newAlert = new PlaceAlertConfigEntity();

		newPlace.setPlaceid(form.getSelectedPlaceId());
		newPlace.setUpdateuserid(this.loginInfoHolder.getLoginId());
		newPlace.setUpdatedate(nowTimestamp);
		// 論理削除
		this.placeMasterMapper.delete(newPlace);

		// place_display_config
		newDisp.setPlaceid(form.getSelectedPlaceId());
		newDisp.setUpdateuserid(this.loginInfoHolder.getLoginId());
		newDisp.setUpdatedate(nowTimestamp);
		// 論理削除
		this.placeDisplayConfigMapper.delete(newDisp);

		// place_alert_config
		newAlert.setPlaceid(form.getSelectedPlaceId());
		newAlert.setUpdateuserid(this.loginInfoHolder.getLoginId());
		newAlert.setUpdatedate(nowTimestamp);
		// 論理削除
		this.placeAlertConfigMapper.delete(newAlert);
	}
}
