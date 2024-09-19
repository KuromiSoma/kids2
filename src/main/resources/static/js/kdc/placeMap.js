var canvas;
var map;

// 選択座標
var selectPos;

// 選択中マーカーのインデックス値
var selectedMarkerIdx;

// 新規追加用マーカーオブジェクト
var newMarker;

// 新規追加用サークルオブジェクト
var newCircle;

// 座標リスト
var pointList = [];

// 半径リスト（既に登録されている半径）
var radiusList = [];

// 場所IDリスト
var placeIdList = [];

// 場所名リスト
var placeNameList = [];

// 場所種別リスト
var placeTypeList = [];
var placeTypeCircleColorObj = new Object();
var placeTypeEditColorObj = new Object();

// アイコンリスト
var placeIconIdList = [];

// サークルリスト
var circleObjList = [];

// アイコンデータリスト
var placeIconDataList = [];

// 登録メニュー用座標
var scrX;
var scrY;

google.maps.event.addDomListener(window, 'load', function() {
	// メニューDivを表示するため、マーカークリック時のマウスの位置を保持（最後に移動した位置）
	document.body.addEventListener("mousemove", function(event) {
		scrX = event.pageX;
		scrY = event.pageY;
	});
	// 隠し項目取得

	// 初期表示中心座標
	var defCenterLat = document.getElementById('mapLatitude').value;
	var defCenterLng = document.getElementById('mapLongitude').value;
	// 初期表示ズームレベル
	var mapZoomLevel = document.getElementById('mapZoomLevel').value;

	// 座標リスト
	var pointListValue = document.getElementById('pointList').value;
	if (pointListValue.length > 0) {
		pointList = pointListValue.split(';');
	}

	// 半径リスト
	radiusList = document.getElementById('radiusList').value.split(';');

	// 場所名リスト
	placeIdList = document.getElementById('placeIdList').value.split(';');
	placeNameList = document.getElementById('placeNameList').value.split(';');

	// アイコンデータリスト
	placeIconDataList = document.getElementById('placeIconDataList').value.split(';');
	var iconDataObj = new Object();
	// 連想配列に値をセットする
	for (var iconIdx = 0; iconIdx < placeIconDataList.length; iconIdx++) {
		var iconData = placeIconDataList[iconIdx].split(',');
		iconDataObj[iconData[0]] = iconData[1];
	}
	// アイコンリスト
	placeIconIdList = document.getElementById('placeIconIdList').value.split(';');

	// 場所タイプ（表示色）
	var placeTypeColorList = document.getElementById('placeTypeColorList').value.split(';');
	// 連想配列に値をセットする
	for (var i = 0; i < placeTypeColorList.length; i++) {
		var placeTypeColorData = placeTypeColorList[i].split(',');
		placeTypeCircleColorObj[placeTypeColorData[0]] = placeTypeColorData[1];
		placeTypeEditColorObj[placeTypeColorData[0]] = placeTypeColorData[2];
	}
	placeTypeList = document.getElementById('placeTypeList').value.split(';');

	// Map
	canvas = document.getElementById('map-canvas');
	var opts = {
		zoom : Number(mapZoomLevel),
		center : new google.maps.LatLng(defCenterLat, defCenterLng),
//		zoomControl: false,			// ズーム非表示
//		mapTypeControl: false,		// マップ切り替え非表示
		streetViewControl: false,	// ストリートビュー非表示
		fullscreenControl: false,	// 全画面モード非表示
	};
	map = new google.maps.Map(canvas, opts);

	// Marker
	for (var markerIdx = 0; markerIdx < pointList.length; markerIdx++) {
		var point = pointList[markerIdx].split(',');
		var markerOptions = {
			map : map,
			position : new google.maps.LatLng(point[0], point[1]),
			icon : "data:image/png;base64," + iconDataObj[placeIconIdList[markerIdx]],
			zIndex : markerIdx,
			// クリックイベントを有効
			clickable : true,
			// ドラッグでアイコンを移動不可
			draggable : false,
		};
		var markerOptionsNoIcon = {
			map : map,
			position : new google.maps.LatLng(point[0], point[1]),
			icon : {
				fillColor : "#ff0000", // 塗り潰し色
				fillOpacity: 1.0, //塗り潰し透過率
				path : google.maps.SymbolPath.CIRCLE, // 円を指定
				scale : 4, // 円のサイズ
				strokeColor : "#ff0000", // 枠の色
				strokeWeight: 1.0 //枠の透過率
			},
			zIndex : markerIdx,
			clickable : true,
			draggable : false,
		};
		var marker = new google.maps.Marker((iconDataObj[placeIconIdList[markerIdx]] != null) ? markerOptions : markerOptionsNoIcon);
		showPlaceInfo(marker, markerIdx);
		showPlaceMenu(marker, markerIdx);
		// 一つのマーカーに対して一つのサークルオブジェクトを生成
		var circleopts = {
			map : map,
			center : new google.maps.LatLng(point[0], point[1]),
			radius : Number(radiusList[markerIdx]),
			// サークルのクリック無効（サークル内に新規マーカーを設置できるようにするため）
			clickable : false,
			strokeOpacity : 0,
			fillColor : placeTypeCircleColorObj[placeTypeList[markerIdx]],
		};
		circleObjList.push(new google.maps.Circle(circleopts));
	}

	// マーカーオンフォーカス（場所名表示）処理
	function showPlaceInfo(marker, markerIdx) {
		google.maps.event.addListener(marker, 'mouseover', function(event) {
			// 吹き出し表示内容
			var content = placeNameList[markerIdx];
			// 吹き出し生成
			var infoWindow = new google.maps.InfoWindow({
				content : content,
				position : marker.position,
				pixelOffset : new google.maps.Size(0, -25)
			});
			// 吹き出し表示
			infoWindow.open(map);

			// フォーカスアウト時に吹き出しを除去する
			google.maps.event.addListener(marker, 'mouseout', function() {
				infoWindow.close();
			});
		});
	}

	// マーカー右クリック
	function showPlaceMenu(marker) {
		google.maps.event.addListener(marker, 'rightclick', function(event) {
			selectPos = marker.getPosition();
			for (var markerIdx = 0; markerIdx < pointList.length; markerIdx++) {
				var point = pointList[markerIdx].split(',');
				if (selectPos.lat() == point[0] && selectPos.lng() == point[1]) {
					selectedMarkerIdx = markerIdx;
				}
			}

			// 登録メニューリセット
			var placeMenu = document.getElementById('divNewPlaceMenu');
			if (placeMenu != null) {
				placeMenu.remove();
			}
			// 既にメニューを表示している場合
			var target = document.getElementById('divClickMenu');
			if (target != null) {
				target.remove();
			}
			// 右クリックメニューを作成
			var clickMenu = document.createElement("div");

			// 右クリックメニューのスタイルを設定
			clickMenu.style.visibility = "absolute";
			clickMenu.id = "divClickMenu";
			clickMenu.style.backgroundColor = "white";
			clickMenu.style.border = "2px solid black";
			clickMenu.style.padding = "2px";
			clickMenu.style.fontSize = "12px";
			clickMenu.style.cursor = "pointer";
			clickMenu.style.position = "fixed";
			clickMenu.style.top = scrY - 30 + "px";
			clickMenu.style.left = scrX + 30 + "px";
			clickMenu.style.zIndex = "3";
			// 右クリックメニューの内容(HTML）
			clickMenu.innerHTML = "<div class='updatePlaceDiv'>" + "<button type='button' value='updatePlace' id='updatePlace' class='menuButtonMain' onclick='doUpdatePlace()'>場所変更</button>" + "</div>" + "<div class='deletePlaceDiv'>"
					+ "<button type='button' value='deletePlace' id='deletePlace' class='menuButtonMain' onclick='doDeletePlace()'>場所削除</button>" + "</div>"
					+ "<div id='deletePlaceDialog'></div>";
			// 右クリックメニューをmap内に返す
			document.getElementsByTagName("form").item(0).appendChild(clickMenu);
		});
	}

	// MAPクリック時に新規マーカーを作成する
	google.maps.event.addListener(map, 'click', function(event) {
		var newRadius = 10;
		var newPlaceType = 0;
		// 既に新規マーカーが作成されている場合は現在の半径と色を保持
		if (newMarker != null) {
			newRadius = newCircle.getRadius();
			newPlaceType = document.getElementById('newPlaceTypeCd').value;
		}
		makeNewMarker(event.latLng, newRadius, newPlaceType);
	});

	// MAPドラッグ時にメニューDivを非表示にする
	google.maps.event.addListener(map, "drag", function(event) {
		var target = document.getElementById('divClickMenu');
		if (target != null) {
			target.style.display = "none";
		}
	});
	// MAPクリック時にメニューDivを非表示にする
	google.maps.event.addListener(map, "click", function(event) {
		var target = document.getElementById('divClickMenu');
		if (target != null) {
			target.style.display = "none";
		}
	});
});

function makeNewMarker(markerPos, radius, placeType) {
	// 既に新規マーカーが作成されている場合は削除する
	if (newMarker != null) {
		newMarker.setMap(null);
		newCircle.setMap(null);
	}
	// rangeに保持した半径を設定
	document.getElementById('radius').value = radius;
	// 選択座標に新規マーカーの座標をセット
	selectPos = markerPos;

	if (!document.getElementById('newPlaceName').value) {
		placename = document.getElementById('newPlaceName').value = document.getElementById('selectedPlaceName').value;
	}
	if (!document.getElementById('newPlaceTypeCd').value) {
		document.getElementById('newPlaceTypeCd').value = placeType;
	}
	if (!document.getElementById('newPlaceIcon').value) {
		document.getElementById('newPlaceIcon').value = document.getElementById('selectedPlaceIcon').value;
	}
	document.getElementById('newLatitude').value = selectPos.lat();
	document.getElementById('newLongitude').value = selectPos.lng();
	document.getElementById('newRadius').value = radius;

	// 半径文字列をrangeの上部に表示
	document.getElementById('rangeValText').value = editRangeValText(radius);

	var markerOptions = {
		map : map,
		position : new google.maps.LatLng(selectPos.lat(), selectPos.lng()),
		icon : {
			fillColor : '#FF0000', // 塗り潰し色
			path : google.maps.SymbolPath.CIRCLE, // 円を指定
			scale : 7, // 円のサイズ
			strokeColor : '#FF0000', // 枠の色
		},
		// クリックイベントを有効
		clickable : true,
		// ドラッグでアイコンを移動可
		draggable : true
	};
	newMarker = new google.maps.Marker(markerOptions);

	// 新規サークルオブジェクトを生成
	var circleopts = {
		map : map,
		center : new google.maps.LatLng(selectPos.lat(), selectPos.lng()),
		radius : radius,
		// サークルのクリック無効（サークル内に新規マーカーを設置できるようにするため）
		clickable : false,
		fillColor : placeTypeEditColorObj[placeType],
		strokeOpacity : 0,
	};
	newCircle = new google.maps.Circle(circleopts);

	// 場所名とアイコンを設定する登録メニュー
	var target = document.getElementById('divNewPlaceMenu');
	if (target != null) {
		// 再表示
		target.style.display = "block";
		target.style.top = scrY - 30 + 'px';
		target.style.left = scrX + 30 + 'px';
	} else {
		// 場所名とアイコンを設定する登録メニューを作成する
		var menu = document.createElement('div');
		var placeName = document.getElementById('newPlaceName').value;
		var placeTypeCd = document.getElementById('newPlaceTypeCd').value;
		var placeIcon = document.getElementById('newPlaceIcon').value;

		var placeTypeFormat = "";
		placeTypeFormat += "<label class='placeTypeLabel' id='placeTypeLabel' for='placeType0'>";
		placeTypeFormat += "<input type='radio' id='placeType0' name='placeTypeCd' value='0'";
		if (placeTypeCd == 0) {
			placeTypeFormat += " checked = 'checked' ";
		}
		placeTypeFormat += " onclick='changePlaceTypeCd()'/> 通常の場所";
		placeTypeFormat += "</label>";
		placeTypeFormat += "<label class='placeTypeLabel' id='placeTypeLabel' for='placeType1'>";
		placeTypeFormat += "<input type='radio' id='placeType1' name='placeTypeCd' value='1'";
		if (placeTypeCd == 1) {
			placeTypeFormat += " checked = 'checked' ";
		}
		placeTypeFormat += " onclick='changePlaceTypeCd()'/> 危険な場所";
		placeTypeFormat += "</label>";

		var placeIconFormat = "<div>";
		placeIconFormat += "<label class='placeIconLabel' id='placeIconLabel" + iconIdx + "' for='placeIcon" + iconIdx + "'>";
		placeIconFormat += "<input type='radio' id='placeIcon" + iconIdx + "' name='placeIcon' value=''";
		if (placeIcon == '') {
			placeIconFormat += " checked = 'checked' ";
		}
		placeIconFormat += " onclick='changePlaceIcon()'/>";
		placeIconFormat += "なし";
		placeIconFormat += "</label>";
		placeIconFormat += "</div>";
		placeIconFormat += "<div>";
		var rowItems = 0;
		for (var iconIdx = 1; iconIdx <= placeIconDataList.length; iconIdx++) {
			if (rowItems >= 4) {
				placeIconFormat += "</div>";
				placeIconFormat += "<div>";
				rowItems = 0;
			}
			var iconData = placeIconDataList[iconIdx-1].split(',');
			placeIconFormat += "<label class='placeIconLabel' id='placeIconLabel" + iconIdx + "' for='placeIcon" + iconIdx + "'>";
			placeIconFormat += "<input type='radio' id='placeIcon" + iconIdx + "' name='placeIcon' value='" + iconData[0] + "'";
			if (placeIcon == iconData[0]) {
				placeIconFormat += " checked = 'checked' ";
			}
			placeIconFormat += " onclick='changePlaceIcon()'/>";
			placeIconFormat += "<img id='icon" + iconIdx + "' src='data:image/png;base64," + iconData[1] + "' width='40px' height='40px' alt='" + iconData[0] + "' />";
			placeIconFormat += "</label>";
			rowItems++;
		}
		placeIconFormat += "</div>";

		// 登録メニューのスタイルを設定。
		menu.style.visibility = 'absolute';
		menu.id = 'divNewPlaceMenu';
		menu.style.backgroundColor = 'white';
		menu.style.border = '2px solid black';
		menu.style.padding = '2px';
		menu.style.fontSize = '12px';
		menu.style.cursor = 'pointer';
		menu.style.position = 'fixed';
		menu.style.top = scrY - 30 + 'px';
		menu.style.left = scrX + 30 + 'px';
		menu.style.zIndex = '3';
		// 新規登録メニューの内容(HTML）
		menu.innerHTML = "<div align='right'><button type='button' value='hiddenMenu' id='hiddenMenu' onclick='cancelEditPlace()'>×</button></div>"
				+ "<div id='placeMenuError'></div>"
				+ "<table><tr><td>場所名：</td></tr>"
				+ "<tr><td><input type='text' size='30' maxlength='30' id='placeName' name='placeName' value='" + placeName + "' onChange='changePlaceName()'/>"
				+ "</td></tr></table>"
				+ "<table><tr><td>場所種別：</td></tr>"
				+ "<tr><td>" + placeTypeFormat + "</td></tr></table>"
				+ "<table><tr><td>場所アイコン：</td></tr>"
				+ "<tr><td>" + placeIconFormat + "</td></tr></table>";
		// 新規登録メニューをmap内に返す
		document.body.appendChild(menu);
	}

	// マーカードラッグ中にサークルを一緒に動かす
	newMarker.addListener('drag', function(event) {
		newCircle.setCenter(new google.maps.LatLng(event.latLng.lat(), event.latLng.lng()));
	});

	// マーカードラッグ終了時に座標を確定させる
	newMarker.addListener('dragend', function(event) {
		// 選択座標に新規マーカーの座標をセット
		selectPos = event.latLng;
		document.getElementById('newLatitude').value = selectPos.lat();
		document.getElementById('newLongitude').value = selectPos.lng();
	});
}

function cancelEditPlace() {
	// 登録メニューリセット
	var target = document.getElementById('divNewPlaceMenu');
	if (target != null) {
		target.remove();
	}
	newMarker.setMap(null);
	newCircle.setMap(null);
	// 場所登録用パラメータとメニューをリセット
	document.getElementById('selectedPlaceId').value = null;
	document.getElementById('selectedPlaceName').value = null;
	document.getElementById('selectedPlaceTypeCd').value = null;
	document.getElementById('selectedPlaceIcon').value = null;
	document.getElementById('newPlaceName').value = null;
	document.getElementById('newPlaceTypeCd').value = null;
	document.getElementById('newPlaceIcon').value = null;
	document.getElementById('newLatitude').value = null;
	document.getElementById('newLongitude').value = null;
	document.getElementById('newRadius').value = null;
	newMarker.remove();
	newCircle.remove();
}

function doUpdatePlace() {
	// 編集中の場所ID
	document.getElementById('selectedPlaceId').value = placeIdList[selectedMarkerIdx];
	document.getElementById('selectedPlaceName').value = placeNameList[selectedMarkerIdx];
	document.getElementById('selectedPlaceTypeCd').value = placeTypeList[selectedMarkerIdx];
	document.getElementById('selectedPlaceIcon').value = placeIconIdList[selectedMarkerIdx];
	// 場所登録用パラメータとメニューをリセット
	document.getElementById('newPlaceName').value = null;
	document.getElementById('newPlaceTypeCd').value = null;
	document.getElementById('newPlaceIcon').value = null;
	document.getElementById('newLatitude').value = null;
	document.getElementById('newLongitude').value = null;
	document.getElementById('newRadius').value = null;
	var target = document.getElementById('divNewPlaceMenu');
	if (target != null) {
		target.remove();
	}

	// メニューを非表示にする
	var target = document.getElementById('divClickMenu');
	if (target != null) {
		target.style.display = "none";
	}
	// 選択したマーカーのサークルのみを非表示
	for (var i = 0; i < circleObjList.length; i++) {
		circleObjList[i].setVisible(true);
	}
	circleObjList[selectedMarkerIdx].setVisible(false);
	// rangeに選択されたサークルの半径を設定
	document.getElementById('radius').value = circleObjList[selectedMarkerIdx].getRadius();

	// 新規編集用マーカー作成
	makeNewMarker(selectPos, circleObjList[selectedMarkerIdx].getRadius(), placeTypeList[selectedMarkerIdx]);
}

function doDeletePlace() {
	// ダイアログのメッセージを設定
	$('#deletePlaceDialog').html('選択した場所を削除します。<br/>よろしいですか？');
	
	// ダイアログを作成
	$('#deletePlaceDialog').dialog({
		modal: true,
		title: '確認',
		buttons: {
			'OK': function() {
				$(this).dialog('close');
				// 編集中の場所ID
				document.getElementById('selectedPlaceId').value = placeIdList[selectedMarkerIdx];
				document.getElementById('mapLatitude').value = map.getCenter().lat();
				document.getElementById('mapLongitude').value = map.getCenter().lng();
				document.getElementById('mapZoomLevel').value = map.getZoom();
				// 削除実行
				document.getElementById('doDelete').click();
			},
			'キャンセル': function() {
				$(this).dialog('close');
			}
		}
	});
	var target = document.getElementById('divClickMenu');
	if (target != null) {
		target.remove();
	}
}

// 新規登録場所名変更時
function changePlaceName() {
	document.getElementById('newPlaceName').value = document.getElementById('placeName').value;
}
// 新規登録場所種別変更時
function changePlaceTypeCd() {
	document.getElementById('newPlaceTypeCd').value = $('input[name=placeTypeCd]:checked').val();
}
// 新規登録アイコン変更時
function changePlaceIcon() {
	document.getElementById('newPlaceIcon').value = $('input[name=placeIcon]:checked').val();
}

// サークル描画処理(range変更時)
function setCircle() {

	// 半径文字列をrangeの上部に表示
	document.getElementById('rangeValText').value = editRangeValText(document.getElementById('radius').value);

	if (newCircle != null) {
		newCircle.setRadius(Number(document.getElementById('radius').value));
	}
}

// サークルリスト更新処理(range変更後確定時)
function changeCircle() {

	// 半径文字列をrangeの上部に表示
	document.getElementById('rangeValText').value = editRangeValText(document.getElementById('radius').value);

	document.getElementById('newRadius').value = document.getElementById('radius').value;
}

function dummyCommit() {
	if (!document.getElementById('newPlaceName').value) {
		$('#placeMenuError').text('場所名を入力してください。');
		$('#placeMenuError').css('color', 'red');
		return;
	}

	document.getElementById('mapLatitude').value = map.getCenter().lat();
	document.getElementById('mapLongitude').value = map.getCenter().lng();
	document.getElementById('mapZoomLevel').value = map.getZoom();
	document.getElementById('doCommit').click();
}

function editRangeValText(rangeVal) {
	return rangeVal + ' メートル';
}
