package com.kdc.web.main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.db.GroupInfoEntity;
import com.kdc.common.entity.db.PlaceMasterEntityWrapper;
import com.kdc.common.entity.db.UserLocationEntityWrapper;
import com.kdc.common.entity.db.UserLocationRecordEntityWrapper;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.entity.web.WebUserViewEntity;
import com.kdc.common.enums.PlaceTypeEnum;
import com.kdc.common.enums.ReceptionStatusEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.common.util.IconDataSet;
import com.kdc.common.util.UserColorSet;
import com.kdc.mybatis.mapper.common.entity.GroupMasterMapper;
import com.kdc.mybatis.mapper.common.entity.PlaceMasterMapper;
import com.kdc.mybatis.mapper.common.entity.UserLocationMapper;
import com.kdc.mybatis.mapper.common.entity.UserLocationRecordMapper;
import com.kdc.mybatis.mapper.common.entity.UserMasterMapper;
import com.kdc.mybatis.mapper.web.WebMainMapper;

/**
 * メイン画面 Service クラス
 * 
 * @author s.hiasa
 *
 */
@Service
public class WebMainService {

	@Autowired
	private UserLocationMapper userLocationMapper;

	@Autowired
	private UserLocationRecordMapper userLocationRecordMapper;

	@Autowired
	private WebMainMapper webMainMapper;

	@Autowired
	private PlaceMasterMapper placeMasterMapper;

	@Autowired
	private UserMasterMapper userMasterMapper;
	
	@Autowired
	private GroupMasterMapper groupMasterMapper;

	/**
	 * 初期処理.
	 * 
	 * @param form
	 *            メイン画面フォーム
	 */
	public void init(WebMainForm form) {

		// Map初期表示中心座標
		if (StringUtils.isEmpty(form.getMapLatitude())) {
			form.setMapLatitude(CommonConst.DEFAULT_MAP_LATITUDE);
		}
		if (StringUtils.isEmpty(form.getMapLongitude())) {
			form.setMapLongitude(CommonConst.DEFAULT_MAP_LONGITUDE);
		}
		// Map初期表示ズームレベル
		if (StringUtils.isEmpty(form.getMapZoomLevel())) {
			form.setMapZoomLevel(CommonConst.DEFAULT_MAP_ZOOM);
		}

		//グループID取得（有効なユーザーマスタよりMINグループIDを取得）
		if ( form.getCurrentGroupId() == null) {
			UserMasterEntity userMasterGrpId = userMasterMapper.selectUserGroupId(CommonConst.FLG_OFF);
			if ( userMasterGrpId != null ) {
				form.setCurrentGroupId(userMasterGrpId.getGroupid());
			}
		}

		// DBより場所リスト取得
		List＜PlaceMasterEntityWrapper> placeList = placeMasterMapper.selectAll(CommonConst.FLG_OFF,form.getCurrentGroupId(), true);

		// 場所リストを表示用隠し項目に設定する

		// アイコンパターンリスト作成
		StringBuilder placeIconDataListStr = new StringBuilder();

		List<IconDataSet> placeIconList = KdcCommonUtils.getPlaceIconList();
		for (IconDataSet placeIcon : placeIconList) {
			placeIconDataListStr.append(placeIcon.getIconName()).append(",");
			placeIconDataListStr.append(Base64.getEncoder().encodeToString(placeIcon.getIconData())).append(";");
		}
		form.setPlaceIconDataList(placeIconDataListStr.toString());

		StringBuilder placeTypeColorListStr = new StringBuilder();
		for (PlaceTypeEnum item : PlaceTypeEnum.values()) {
			placeTypeColorListStr.append(item.getCode()).append(",");
			placeTypeColorListStr.append(item.getCircleColor()).append(",");
			placeTypeColorListStr.append(item.getEditColor()).append(";");
		}
		form.setPlaceTypeColorList(StringUtils.removeEnd(placeTypeColorListStr.toString(), ";"));

		// 場所（座標）リスト文字列
		StringBuilder pointListStr = new StringBuilder();

		// 半径リスト文字列
		StringBuilder radiusListStr = new StringBuilder();

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
		// 場所名リスト
		form.setPlaceNameList(StringUtils.removeEnd(placeNameListStr.toString(), ";"));
		// アイコンリスト
		form.setPlaceIconIdList(StringUtils.removeEnd(placeIconIdListStr.toString(), ";"));
		// 場所タイプリスト
		form.setPlaceTypeList(StringUtils.removeEnd(placeTypeListStr.toString(), ";"));

		// ユーザデフォルトアイコン
		List<IconDataSet> userIconList = KdcCommonUtils.getUserDefaultIconList();
		Map<Integer, byte[]> userIconMap = new LinkedHashMap<>();
		for (IconDataSet userIcon : userIconList) {
			Integer authlevel = KdcCommonUtils.nullSafeParseInt(StringUtils.right(userIcon.getIconName(), 2));
			userIconMap.put(authlevel, userIcon.getIconData());
		}

		// マーカーアイコン
		List<IconDataSet> markerIconList = KdcCommonUtils.getMarkerIconList();
		Map<Integer, byte[]> markerIconMap = new LinkedHashMap<>();
		for (IconDataSet markerIcon : markerIconList) {
			Integer markerId = KdcCommonUtils.nullSafeParseInt(StringUtils.right(markerIcon.getIconName(), 2));
			markerIconMap.put(markerId, markerIcon.getIconData());
		}

		// 最新位置をマップ化
		Map<String, UserLocationEntityWrapper> userLocationMap = new LinkedHashMap<>();
		List<UserLocationEntityWrapper> userLocationList = userLocationMapper.selectAll(CommonConst.FLG_OFF,form.getCurrentGroupId(), true);
		for (UserLocationEntityWrapper userLocation : userLocationList) {
			userLocationMap.put(userLocation.getUserid(), userLocation);
		}

		// DBよりユーザ位置履歴を取得して隠し項目に設定
		StringBuilder markerListAllStr = new StringBuilder();
		StringBuilder markerListLatestStr = new StringBuilder();
		StringBuilder infoWindowListStr = new StringBuilder();
		StringBuilder userIconListStr = new StringBuilder();
		StringBuilder lineColorListStr = new StringBuilder();
		StringBuilder lineOpacityListStr = new StringBuilder();
		StringBuilder markerColorListStr = new StringBuilder();
		StringBuilder userIdListStr = new StringBuilder();

		List<WebUserViewEntity> userList = new ArrayList<>();

		// 有効ユーザのみを表示
		List<UserMasterEntity> userMasterList = this.webMainMapper.getValidUser();
		if (userMasterList != null) {
			for (UserMasterEntity userMaster : userMasterList) {
				if (userLocationMap.containsKey(userMaster.getUserid())) {
					// ユーザの変わり目に"/"を追加
					if (markerListAllStr.length() > 0) {
						markerListAllStr.deleteCharAt(markerListAllStr.length() - 1);
						markerListAllStr.append("/");
						infoWindowListStr.deleteCharAt(infoWindowListStr.length() - 1);
						infoWindowListStr.append("/");
					}

					// 当日の位置情報が存在するユーザは最新位置と履歴を表示する
					if (StringUtils.equals(KdcCommonUtils.getNowDateString(), KdcCommonUtils.timestampToDateString(
							userLocationMap.get(userMaster.getUserid()).getLastlocationdate()))) {
						// 最新位置リスト
						UserLocationEntityWrapper userLocation = userLocationMap.get(userMaster.getUserid());
						markerListLatestStr.append(userLocation.getLatitude()).append(",")
								.append((userLocation.getLongitude())).append(";");

						// 位置履歴
						List<UserLocationRecordEntityWrapper> userLocationRecordList = userLocationRecordMapper
								.selectByUserIdDate(userMaster.getUserid(), KdcCommonUtils.getNowDateString());
						for (UserLocationRecordEntityWrapper userLocationRecord : userLocationRecordList) {
							// 位置履歴リスト
							markerListAllStr.append(userLocationRecord.getLatitude()).append(",")
									.append(userLocationRecord.getLongitude()).append(";");
							// 吹き出し情報リスト
							infoWindowListStr.append(KdcCommonUtils.nullToEmpty(userMaster.getUsername()));
							infoWindowListStr.append(",");
							infoWindowListStr.append(KdcCommonUtils.nullToEmpty(
									KdcCommonUtils.timeToTimeStringForDisp(userLocationRecord.getReceivedate())));
							infoWindowListStr.append(",");
							infoWindowListStr.append(userLocationRecord.getBatterylevel());
							infoWindowListStr.append(",");
							infoWindowListStr.append(
									ReceptionStatusEnum.valueOf(userLocationRecord.getReceptionstatus()).getLabel());
							infoWindowListStr.append(";");
						}
					} else {
						// 当日の位置情報が存在しないユーザは過去の位置情報のみを表示
						UserLocationEntityWrapper userLocation = userLocationMap.get(userMaster.getUserid());
						markerListLatestStr.append(userLocation.getLatitude()).append(",")
								.append((userLocation.getLongitude())).append(";");

						// 位置履歴リスト
						markerListAllStr.append(userLocation.getLatitude()).append(",")
								.append(userLocation.getLongitude()).append(";");
						// 吹き出し情報リスト
						infoWindowListStr.append(KdcCommonUtils.nullToEmpty(userMaster.getUsername()));
						infoWindowListStr.append(",");
						infoWindowListStr.append(KdcCommonUtils.nullToEmpty(
								KdcCommonUtils.timeToTimeStringForDisp(userLocation.getLastlocationdate())));
						infoWindowListStr.append(",");
						infoWindowListStr.append(userLocation.getBatterylevel());
						infoWindowListStr.append(",");
						infoWindowListStr
								.append(ReceptionStatusEnum.valueOf(userLocation.getReceptionstatus()).getLabel());
						infoWindowListStr.append(";");
					}
				} else {
					// 位置情報自体が登録されていないユーザは無視する
					continue;
				}

				// ユーザ表示色オブジェクト
				UserColorSet colorSet = new UserColorSet(null, userMaster.getLinecolor(), userMaster.getMarkercolor());

				// アイコンリスト
				try {
					byte[] userIconFile;
					if (StringUtils.isNotEmpty(userMaster.getIconid())) {
						userIconFile = userMaster.getIconfile();
					} else {
						userIconFile = userIconMap.get(userMaster.getAuthlevel());
					}
					userIconListStr
							.append(KdcCommonUtils.createUserMarkerIconString(userIconFile,
									markerIconMap.containsKey(colorSet.getMarkerColorId())
											? markerIconMap.get(colorSet.getMarkerColorId()) : markerIconMap.get(1)))
							.append(";");

				} catch (IOException e) {
					e.printStackTrace();
				}

				// 移動履歴表示色リスト
				lineColorListStr.append(colorSet.getLineColorRGBHex()).append(";");

				// 移動履歴表示不透明度リスト
				lineOpacityListStr.append(colorSet.getLineColorAlpha()).append(";");

				// 移動地点マーカー表示色リスト
				// markerColorListStr.append(colorSet.getMarkerColorRGBHex()).append(";");
				markerColorListStr.append(colorSet.getLineColorRGBHex()).append(";");

				// userIdリスト作成
				userIdListStr.append(userMaster.getUserid()).append(";");

				// ユーザリスト作成
				userList.add(this.makeWebUserViewEntity(userMaster, userIconMap));
			}
		}
		
		//グループ番号取得
		Map<String, String> groupMap = new LinkedHashMap<>();
		Map<String, String> groupNameMap = new LinkedHashMap<>();
		// ユーザマスタからグループＩＤ一覧を取得し、コンボボックスに設定
		List<GroupInfoEntity> groupList = groupMasterMapper.selectAll(CommonConst.FLG_OFF);
		for (int cntCombo = 0; cntCombo < groupList.size(); cntCombo++) {
			groupMap.put(groupList.get(cntCombo).getGroupid(), groupList.get(cntCombo).getGroupname());
			groupNameMap.put(groupList.get(cntCombo).getGroupname(), groupList.get(cntCombo).getGroupname());
		}
		form.setCmbGroup(groupMap);
		

		// 末尾の区切り文字を切り捨てて格納
		form.setMarkerListAll(StringUtils.removeEnd(markerListAllStr.toString(), ";"));
		form.setMarkerListLatest(StringUtils.removeEnd(markerListLatestStr.toString(), ";"));
		form.setInfoWindowList(StringUtils.removeEnd(infoWindowListStr.toString(), ";"));
		form.setUserIconList(StringUtils.removeEnd(userIconListStr.toString(), ";"));
		form.setLineColorList(StringUtils.removeEnd(lineColorListStr.toString(), ";"));
		form.setLineOpacityList(StringUtils.removeEnd(lineOpacityListStr.toString(), ";"));
		form.setMarkerColorList(StringUtils.removeEnd(markerColorListStr.toString(), ";"));
		form.setUserIdList(StringUtils.removeEnd(userIdListStr.toString(), ";"));

		form.setUserList(userList);

	}

	/**
	 * ユーザ表示生成
	 * 
	 * @param source
	 *            ユーザマスタクラス
	 * @param userIconMap
	 *            アイコン画像データマップ
	 * @return ユーザ表示クラス
	 */
	private WebUserViewEntity makeWebUserViewEntity(UserMasterEntity source, Map<Integer, byte[]> userIconMap) {
		WebUserViewEntity entity = new WebUserViewEntity();

		entity.setUserId(source.getUserid());

		// 長いユーザ名は省略＋折り返し表示
		String sourceName = source.getUsername();
		StringBuilder userName = new StringBuilder();
		int tagBytes = 0;
		for (int i = 0; i < sourceName.length(); i++) {
			int nameStringByte = userName.toString().getBytes(Charset.forName("Shift_JIS")).length - tagBytes;
			int nextCharByte = StringUtils.substring(sourceName, i, i + 1)
					.getBytes(Charset.forName("Shift_JIS")).length;
			if (nameStringByte + nextCharByte > 24) {
				// 3バイト使って省略
				while (userName.toString().getBytes(Charset.forName("Shift_JIS")).length - tagBytes + 3 > 24) {
					userName.deleteCharAt(userName.length() - 1);
				}
				userName.append("...");
				break;
			}
			if (nameStringByte > 0 && nextCharByte > 1 && Math.floorMod(nameStringByte + 1, 12) == 0) {
				userName.append(" ＜br>");
				tagBytes += 4;
			} else if (nameStringByte > 0 && Math.floorMod(nameStringByte, 12) == 0) {
				userName.append("＜br>");
				tagBytes += 4;
			}
			userName.append(sourceName.charAt(i));
		}
		entity.setUserName(userName.toString());

		// アイコン画像ファイル
		byte[] iconfile;
		if (StringUtils.isNotEmpty(source.getIconid())) {
			iconfile = source.getIconfile();
		} else {
			iconfile = userIconMap.get(source.getAuthlevel());
		}
		entity.setIconDataString(CommonConst.ICON_IMG_HEADER + KdcCommonUtils.getIconStringBase64(iconfile));

		entity.setGroupId(source.getGroupid());
		entity.setAuthLevel(source.getAuthlevel());

		UserColorSet color = new UserColorSet(null, source.getLinecolor(), source.getMarkercolor());
		entity.setLineColor(color.getLineColorRGBHex());
		entity.setLineColorRed(String.valueOf(color.getLineColorRed()));
		entity.setLineColorGreen(String.valueOf(color.getLineColorGreen()));
		entity.setLineColorBlue(String.valueOf(color.getLineColorBlue()));
		entity.setLineColorAlpha(String.valueOf(color.getLineColorAlpha()));
		entity.setMarkerColor(source.getMarkercolor());

		entity.setDispOrder(source.getDisporder());

		return entity;
	}

}