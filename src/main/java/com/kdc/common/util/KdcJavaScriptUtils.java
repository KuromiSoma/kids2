package com.kdc.common.util;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import com.kdc.common.entity.web.GoogleMapParamEntity;

/**
 * JavaString操作ユーティリティクラス.
 */
@Component
public class KdcJavaScriptUtils {

	/**
	 * JavaScriptでURL文字列を組み立てるコードを返す.
	 * 
	 * @param url
	 *            url文字列
	 * @param paramMap
	 *            引数マップ
	 * @return URL文字列組み立てJavaScriptコード文字列 ({@code not null})
	 */
	public static String getJsUrlBuilder(String url, LinkedHashMap<String, String> paramMap) {
		// パラメータ文字列を生成
		StringBuilder param = new StringBuilder();
		for (Map.Entry<String, String> item : paramMap.entrySet()) {
			param.append(param.length() == 0 ? ",\"?\"," : ",\"&\",");
			param.append(String.format("\"%s\"", item.getKey())).append(",\"=\",");
			param.append(item.getValue());
		}
		// URL を生成
		StringBuilder js = new StringBuilder();
		js.append("[");
		js.append(String.format("\"%s\"", url));
		js.append(param);
		js.append("].join(\"\")");
		return js.toString();
	}

	/**
	 * GoogleMap Javascript設定
	 */
	public static String getJsGoogleMap(String mapCanvasName, GoogleMapParamEntity defaultMapParam, String eventJs) {
		StringBuilder js = new StringBuilder();
		js.append("var mapCanvas;\n");
		js.append("var mapObj;\n");
		js.append("var mapOps;\n");
		js.append("var originalMarkerObj;\n");
		js.append("var originalCircleObj;\n");
		js.append("google.maps.event.addDomListener(window, 'load', function() {\n");
		// Map初期設定
		js.append(getJsInitMap(mapCanvasName, defaultMapParam)).append("\n");
		// Mapイベント追加
		js.append(eventJs);
		js.append("});\n");
		return js.toString();
	}

	/**
	 * GoogleMap 初期設定
	 * 
	 * @param mapCanvasName
	 * @param mapParam
	 * @return js文字列
	 */
	public static String getJsInitMap(String mapCanvasName, GoogleMapParamEntity mapParam) {
		StringBuilder js = new StringBuilder();
		js.append("mapCanvas = ");
		js.append(String.format("document.getElementById('%s');\n", mapCanvasName));
		js.append("mapOps = {");
		js.append("zoom: ");
		js.append(String.format("%d,", mapParam.getZoom()));
		js.append("center: ");
		js.append(String.format("new google.maps.LatLng(%s, %s),", mapParam.getLatitude(), mapParam.getLongitude()));
		js.append("mapTypeId: google.maps.MapTypeId.ROADMAP");
		js.append("};\n");
		js.append("mapObj = new google.maps.Map(mapCanvas, mapOps);\n");
		return js.toString();
	}

	/**
	 * GoogleMap クリックイベント追加
	 * 
	 * @param eventJs
	 * @return js文字列
	 */
	public static String getJsAddEventClick(String eventJs) {
		StringBuilder js = new StringBuilder();
		js.append("google.maps.event.addListener(mapObj, 'click', function(e) {\n");
		js.append(eventJs);
		js.append("})\n");
		return js.toString();
	}

	/**
	 * マーカーの座標をGoogleMapに渡す.
	 * 
	 * @return 座標設定js文字列
	 */
	public static String getJsSetMarkerOnClick() {
		StringBuilder js = new StringBuilder();
		// js.append("markerObj =");
		// js.append("new google.maps.Marker({");
		// js.append("position: e.latLng,");
		// js.append("map: mapObj");
		// js.append("});\n");
		js.append("var lat = e.latLng.lat();\n");
		js.append("var lng = e.latLng.lng();\n");
		js.append("originalMarkerObj.setPosition(new google.maps.LatLng(lat, lng));\n");
		js.append("originalMarkerObj.setMap(mapObj);\n");
		js.append("document.getElementById('latitude').value = lat;\n");
		js.append("document.getElementById('longitude').value = lng;\n");
		return js.toString();
	}

	/**
	 * 地図の中心座標をGoogleMapに渡す.
	 * @param editable
	 * @return 座標設定js文字列
	 */
	public static String getJsSetCircleOnClick(boolean editable) {
		StringBuilder js = new StringBuilder();
		// js.append("var circleObj =");
		// js.append("new google.maps.Circle({");
		// js.append("center: e.latLng,");
		// js.append("radius: 108,");
		// js.append("draggable: true,");
		// js.append("editable: true,");
		// js.append("map: mapObj");
		// js.append("});\n");
		js.append("var lat = e.latLng.lat();\n");
		js.append("var lng = e.latLng.lng();\n");
		js.append("if(originalCircleObj){\n");
		js.append("originalCircleObj.setCenter(new google.maps.LatLng(lat, lng));\n");
		js.append("}else{\n");
		js.append("var circleOpts = {");
		js.append("center: new google.maps.LatLng(lat, lng),");
		js.append(String.format("radius: %d,", CommonConst.DEFAULT_PLACE_RADIUS));
		if (editable) {
			js.append("draggable: true,");
			js.append("editable: true,");
		}
		js.append("map: mapObj");
		js.append("};\n");
		js.append("originalCircleObj = new google.maps.Circle(circleOpts);\n");
		if (editable) {
			js.append("originalCircleObj.addListener(\"center_changed\", function(argument) {\n");
			js.append("var lat = originalCircleObj.latLng.lat();\n");
			js.append("var lng = originalCircleObj.latLng.lng();\n");
			js.append("document.getElementById('latitude').value = lat;\n");
			js.append("document.getElementById('longitude').value = lng;\n");
			js.append("});\n");
			js.append("originalCircleObj.addListener(\"radius_changed\", function(argument) {\n");
			js.append("var rad = originalCircleObj.getRadius();\n");
			js.append("document.getElementById('radius').value = rad;\n");
			js.append("});\n");
		}
		js.append("};\n");
		// js.append("originalCircleObj.setCenter(new google.maps.LatLng(lat,
		// lng));\n");
		// js.append(String.format("originalCircleObj.setRadius(%d);\n",
		// CommonConst.DEFAULT_PLACE_RADIUS));
		// js.append("originalCircleObj.setMap(mapObj);\n");
		js.append("document.getElementById('latitude').value = lat;\n");
		js.append("document.getElementById('longitude').value = lng;\n");
		return js.toString();
	}

	/**
	 * Formのinputdata情報を取得する.
	 * 
	 * @param request
	 *            リクエスト情報
	 * @param name
	 *            name
	 */
	public static String getFormSerializeArrayName(WebRequest request, String name) {

		int cnt = 0;
		String result = "";
		// 一覧表示複数選択チェックを初期化
		while (!KdcCommonUtils.nullSafeEquals(request.getParameter("inputdata[" + cnt + "][name]"), "")) {
			if (KdcCommonUtils.nullSafeEquals(
					KdcCommonUtils.nullToEmpty(request.getParameter("inputdata[" + cnt + "][name]")), name)) {
				result = request.getParameter("inputdata[" + cnt + "][value]");
				break;
			}
			cnt++;
		}

		return result;
	}

	/**
	 * Formのチェックがonか比較する.
	 * @param formItem
	 * @return
	 */
	public static boolean getFormItemChecked(String formItem) {
		return StringUtils.equals(formItem, "on");
	}

}
