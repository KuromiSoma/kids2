package com.kdc.web.webrecord;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.db.UserLocationEntityWrapper;
import com.kdc.common.entity.db.UserLocationRecordEntityWrapper;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.enums.ReceptionStatusEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.IconDataSet;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.common.util.UserColorSet;
import com.kdc.mybatis.mapper.common.entity.UserLocationMapper;
import com.kdc.mybatis.mapper.common.entity.UserLocationRecordMapper;
import com.kdc.mybatis.mapper.common.entity.UserMasterMapper;

/**
 * 履歴画面 Service クラス
 * 
 *
 */
@Service
public class WebRecordService {

	@Autowired
	private UserMasterMapper userMasterMapper;

	@Autowired
	private UserLocationMapper userLocationMapper;

	@Autowired
	private UserLocationRecordMapper userLocationRecordMapper;

	/**
	 * 初期処理.
	 * 
	 * @param receiveDate
	 *            取得日時
	 * @param form
	 *            履歴画面フォーム
	 * @param userId
	 *            ユーザID
	 */
	public void initForm(String receiveDate, WebRecordForm form, String userId) {

		// ユーザ名
		UserMasterEntity userMasterEntity = this.userMasterMapper.selectByPk(userId, CommonConst.FLG_OFF);
		form.setUserName(userMasterEntity.getUsername());

		// DBよりユーザ位置履歴を取得して隠し項目に設定

		// 最終位置リスト
		UserLocationEntityWrapper locationList = userLocationMapper.selectByPk(userId, CommonConst.FLG_OFF);
		// 位置履歴リスト
		List<UserLocationRecordEntityWrapper> userLocationRecordList = userLocationRecordMapper
				.selectByUserIdDate(userId, receiveDate);

		StringBuilder markerListAllStr = new StringBuilder();
		StringBuilder markerListLatestStr = new StringBuilder();
		StringBuilder infoWindowListStr = new StringBuilder();
		StringBuilder iconListStr = new StringBuilder();
		StringBuilder lineColorListStr = new StringBuilder();
		StringBuilder lineOpacityListStr = new StringBuilder();
		StringBuilder markerColorListStr = new StringBuilder();

		// 位置履歴リスト作成
		StringBuffer currentUserId = new StringBuffer();
		for (UserLocationRecordEntityWrapper userLocationRecord : userLocationRecordList) {
			if (!StringUtils.equals(currentUserId.toString(), userLocationRecord.getUserid())) {
				currentUserId.setLength(0);
				currentUserId.append(userLocationRecord.getUserid());

				// ユーザが異なる場合は"/"を追加
				if (markerListAllStr.length() > 0) {
					markerListAllStr.deleteCharAt(markerListAllStr.length() - 1);
					markerListAllStr.append("/");
					infoWindowListStr.deleteCharAt(infoWindowListStr.length() - 1);
					infoWindowListStr.append("/");
				}
			}
			markerListAllStr.append(userLocationRecord.getLatitude()).append(",")
					.append(userLocationRecord.getLongitude()).append(";");

			// 吹き出し情報リスト作成
			infoWindowListStr.append(KdcCommonUtils.nullToEmpty(userMasterEntity.getUsername()));
			infoWindowListStr.append(",");
			infoWindowListStr.append(KdcCommonUtils
					.nullToEmpty(KdcCommonUtils.timeToTimeStringForDisp(userLocationRecord.getReceivedate())));
			infoWindowListStr.append(",");
			infoWindowListStr.append(userLocationRecord.getBatterylevel());
			infoWindowListStr.append(",");
			infoWindowListStr.append(ReceptionStatusEnum.valueOf(userLocationRecord.getReceptionstatus()).getLabel());
			infoWindowListStr.append(";");
		}
		// 末尾の区切り文字は不要
		if (markerListAllStr.length() > 0) {
			markerListAllStr.setLength(markerListAllStr.length() - 1);
			infoWindowListStr.setLength(infoWindowListStr.length() - 1);
		}

		// 最終位置リスト作成
		markerListLatestStr.append(locationList.getLatitude()).append(",").append((locationList.getLongitude()));

		// ユーザ表示色オブジェクト
		UserColorSet colorSet = new UserColorSet(null, userMasterEntity.getLinecolor(),
				userMasterEntity.getMarkercolor());

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

		// アイコンリスト作成
		try {
			byte[] userIconFile;
			if (StringUtils.isNotEmpty(userMasterEntity.getIconid())) {
				userIconFile = userMasterEntity.getIconfile();
			} else {
				userIconFile = userIconMap.get(userMasterEntity.getAuthlevel());
			}
			iconListStr.append(KdcCommonUtils.createUserMarkerIconString(userIconFile,
					markerIconMap.containsKey(colorSet.getMarkerColorId())
							? markerIconMap.get(colorSet.getMarkerColorId()) : markerIconMap.get(1)));

		} catch (IOException e) {
			e.printStackTrace();
		}

		// 移動履歴表示色リスト作成
		lineColorListStr.append(colorSet.getLineColorRGBHex());

		// 移動履歴表示不透明度リスト作成
		lineOpacityListStr.append(colorSet.getLineColorAlpha());

		// 移動地点マーカー表示色リスト作成
		// markerColorListStr.append(colorSet.getMarkerColorRGBHex());
		markerColorListStr.append(colorSet.getLineColorRGBHex());

		form.setMarkerListAll(markerListAllStr.toString());
		form.setMarkerListLatest(markerListLatestStr.toString());
		form.setInfoWindowList(infoWindowListStr.toString());
		form.setIconList(iconListStr.toString());
		form.setLineColorList(lineColorListStr.toString());
		form.setLineOpacityList(lineOpacityListStr.toString());
		form.setMarkerColorList(markerColorListStr.toString());
		// バッテリー履歴
		form.setBatterys(this.getBatteryRecordString(userLocationRecordList));
		// 履歴ステップ
		form.setRecordCount(String.valueOf(userLocationRecordList.size()));
	}

	/**
	 * バッテリ残量履歴表示用データ取得.
	 * 
	 * @param ユーザ位置履歴テーブルクラスリスト
	 * @return バッテリー残量値データ文字列
	 */
	private String getBatteryRecordString(List<UserLocationRecordEntityWrapper> userLocationRecordList) {

		StringBuilder batterysString = new StringBuilder();

		Map<Integer, UserLocationRecordEntityWrapper> hourButteryMap = new LinkedHashMap<>();
		Map<Integer, Map<Integer, UserLocationRecordEntityWrapper>> dayButteryMap = new LinkedHashMap<>();

		// 時間軸に沿ってマップに展開
		Calendar cal = Calendar.getInstance();
		if (userLocationRecordList != null && userLocationRecordList.size() > 0) {
			int nowHour = -1;
			for (UserLocationRecordEntityWrapper loc : userLocationRecordList) {
				cal.setTime(loc.getReceivedate());
				if (nowHour >= 0 && nowHour != cal.get(Calendar.HOUR_OF_DAY)) {
					dayButteryMap.put(nowHour, hourButteryMap);
					hourButteryMap = new LinkedHashMap<>();
				}
				if (!hourButteryMap.containsKey(cal.get(Calendar.MINUTE))) {
					hourButteryMap.put(cal.get(Calendar.MINUTE), loc);
				}
				nowHour = cal.get(Calendar.HOUR_OF_DAY);
			}
			dayButteryMap.put(nowHour, hourButteryMap);

			// 1時間単位でグラフ化
			boolean started = false;
			for (Integer hour = 0; hour < 24; hour++) {
				// データなし
				if (dayButteryMap.get(hour) == null || dayButteryMap.get(hour).isEmpty()) {
					if (started) {
						batterysString.append("nodata").append(",");
					} else {
						batterysString.append(0).append(",");
					}
				} else {
					for (Integer minute = 0; minute < 60; minute++) {
						UserLocationRecordEntityWrapper loc = dayButteryMap.get(hour).get(minute);
						if (loc != null) {
							batterysString.append(loc.getBatterylevel()).append(",");
							started = true;
							break;
						}
					}
				}
			}
			// 最終データより後は切断とは見なさない
			int removeCount = 0;
			while (StringUtils.endsWith(batterysString, "nodata,")) {
				batterysString.setLength(batterysString.length() - StringUtils.length("nodata,"));
				removeCount++;
			}
			for (int i = 0; i < removeCount; i++) {
				batterysString.append("0,");
			}
		} else {
			for (Integer hour = 0; hour < 24; hour++) {
				batterysString.append("0,");
			}
		}

		return StringUtils.removeEnd(batterysString.toString(), ",");
	}

}
