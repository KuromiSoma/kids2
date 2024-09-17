window.onload = function() {
	if ($('#userLineDisp').val() === '0') {
		$('#dispLinesButton').addClass('_off');
	}
	resizeUserListViewArea();
	setTimeout(function() {
		$('#doReload').click();
	}, document.getElementById('reloadTime').value);
};

var timer = false;
$(window).resize(function() {
	if (timer !== false) {
		clearTimeout(timer);
	}
	timer = setTimeout(function() {
		resizeUserListViewArea();
	}, 100);
});

var canvas;
var map;

var scrX;
var scrY;

// マーカーリスト（すべて）
var userMarkers = [];
var userLastMarkers = [];
var userLines = [];

// マーカーリスト(ユーザごとに保持)
var markerListAll = [];

// マーカーリスト（ユーザの履歴ごとの二次元配列）
var markerList = [];

// 履歴情報ウィンドウリスト
var infoWindow;
var infoWindowSourceList = [];

// マーカーリスト（ユーザごとの最新）
var markerListLatest = [];

// 座標リスト
var pointList = [];

// 半径リスト（既に登録されている半径）(仮）
var radiusList = [];

// 場所種別リスト
var placeTypeList = [];
var placeTypeCircleColorObj = new Object();
var placeTypeEditColorObj = new Object();

// サークルリスト
var circleObjList = [];

// 重なり判定
var zIndexMax;

google.maps.event.addDomListener(window, 'load', function() {
	// 初期表示中心座標
	var defCenterLat = document.getElementById('mapLatitude').value;
	var defCenterLng = document.getElementById('mapLongitude').value;
	// 初期表示ズームレベル
	var zoomLevel = document.getElementById('mapZoomLevel').value;

	var markerListAllValue = document.getElementById('markerListAll').value;
	if (markerListAllValue.length > 0) {
		markerListAll = markerListAllValue.split('/');
	}
	for (var i = 0; i < markerListAll.length; i++) {
		var userMarkerList = [];
		userMarkerList = markerListAll[i].split(';');
		markerList.push(userMarkerList);
	}
	var infoWindowListValue = document.getElementById('infoWindowList').value;
	var infoWindowList = [];
	if (infoWindowListValue.length > 0) {
		infoWindowList = infoWindowListValue.split('/');
	}
	for (var i = 0; i < infoWindowList.length; i++) {
		var userSourceList = [];
		userSourceList = infoWindowList[i].split(';');
		infoWindowSourceList.push(userSourceList);
	}

	// メニューDivを表示するため、マーカークリック時のマウスの位置を保持（最後に移動した位置）
	document.body.addEventListener("mousemove", function(event) {
		scrX = event.pageX; // 水平の位置座標
		scrY = event.pageY; // 垂直の位置座標
	});

	// Map
	canvas = document.getElementById("map-canvas");
	var opts = {
		zoom : Number(zoomLevel),
		center : new google.maps.LatLng(defCenterLat, defCenterLng),
		// zoomControl: false, // ズーム非表示
		// mapTypeControl: false, // マップ切り替え非表示
		streetViewControl : false, // ストリートビュー非表示
		fullscreenControl : false, // 全画面モード非表示
	};
	map = new google.maps.Map(canvas, opts);

	// 座標リスト
	var pointListValue = document.getElementById('pointList').value;
	if (pointListValue.length > 0) {
		pointList = pointListValue.split(';');
	}

	// 半径リスト
	radiusList = document.getElementById('radiusList').value.split(';');

	// 場所名リスト
	var placeNameList = document.getElementById('placeNameList').value.split(';');

	// アイコンパターンリスト
	var placeIconDataList = document.getElementById('placeIconDataList').value.split(';');
	var iconDataObj = new Object();
	// 連想配列に値をセットする
	for (var iconIdx = 0; iconIdx < placeIconDataList.length; iconIdx++) {
		var iconData = placeIconDataList[iconIdx].split(',');
		iconDataObj[iconData[0]] = iconData[1];
	}
	// アイコンリスト
	var placeIconIdList = document.getElementById('placeIconIdList').value.split(';');

	// 場所タイプ（表示色）
	var placeTypeColorList = document.getElementById('placeTypeColorList').value.split(';');
	// 連想配列に値をセットする
	for (var i = 0; i < placeTypeColorList.length; i++) {
		var placeTypeColorData = placeTypeColorList[i].split(',');
		placeTypeCircleColorObj[placeTypeColorData[0]] = placeTypeColorData[1];
		placeTypeEditColorObj[placeTypeColorData[0]] = placeTypeColorData[2];
	}
	placeTypeList = document.getElementById('placeTypeList').value.split(';');

	// 場所circle
	for (var markerIdx = 0; markerIdx < pointList.length; markerIdx++) {
		var point = pointList[markerIdx].split(',');
		var circleMarkerOptions = {
			map : map,
			position : new google.maps.LatLng(point[0], point[1]),
			icon : "data:image/png;base64," + iconDataObj[placeIconIdList[markerIdx]],
			zIndex : markerIdx,
			// クリックイベントを不可
			clickable : true,
			// ドラッグでアイコンを移動不可
			draggable : false,
		};
		var circleMarkerOptionsNoIcon = {
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
		var marker = new google.maps.Marker((iconDataObj[placeIconIdList[markerIdx]] != null) ? circleMarkerOptions : circleMarkerOptionsNoIcon);
		placeInfoDisp(marker, markerIdx);

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
	function placeInfoDisp(marker, cntIndex) {
		google.maps.event.addListener(marker, 'mouseover', function(event) {
			// 吹き出し表示内容
			var content = placeNameList[cntIndex];
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

	// 最新のマーカーのみのリスト
	var markerListLatestValue = document.getElementById('markerListLatest').value;
	if (markerListLatestValue.length > 0) {
		markerListLatest = markerListLatestValue.split(';');
	}
	// 最新のマーカーのみのリストから初期表示の座標、ズームレベルを設定する（初回の表示のみ）
	if (!document.getElementById('isReload').value) {
		bounds = new google.maps.LatLngBounds();
		for (var i = 0; i < markerListLatest.length; i++) {
			var point = markerListLatest[i].split(',');
			var position = new google.maps.LatLng(point[0], point[1]);
			bounds.extend(position);
		}
		map.fitBounds(bounds);
	}

	var lineColorList = document.getElementById('lineColorList').value.split(';');
	var lineOpacityList = document.getElementById('lineOpacityList').value.split(';');
	var markerColorList = document.getElementById('markerColorList').value.split(';');
	var userIconList = document.getElementById('userIconList').value.split(';');
	for (var i = 0; i < markerList.length; i++) {
		var oneUserMarkers = [];
		var oneUserLines = [];
		for (var j = 0; j < markerList[i].length; j++) {
			var point = markerList[i][j].split(',');

			// 履歴表示
			if (j + 1 < markerList[i].length) {
				var pointFrom = markerList[i][j + 1].split(',');

				// 矢印
				var lineSymbol = {
					path : google.maps.SymbolPath.FORWARD_CLOSED_ARROW
				};

				var line = new google.maps.Polyline({
					strokeColor : "#" + lineColorList[i],
					path : [ {
						lat : Number(point[0]),
						lng : Number(point[1])
					}, {
						lat : Number(pointFrom[0]),
						lng : Number(pointFrom[1])
					} ],
					icons : [ {
						icon : lineSymbol,
						offset : '100%'
					} ],
					strokeOpacity : lineOpacityList[i],
					map : map
				});
				oneUserLines.push(line);
			}
			// Marker
			var marker;
			if (j == markerList[i].length - 1) {
				// 最終位置のマーカー
				var markerOptionsLast = {
					map : map,
					position : new google.maps.LatLng(point[0], point[1]),
					icon : "data:image/png;base64," + userIconList[i],
					label : {
						fontSize : "0px",
						text : String(i)
					},
					zIndex : i * 1000000,
					// クリックイベントを有効
					clickable : true,
					// ドラッグでアイコンを移動不可
					draggable : false
				};
				marker = new google.maps.Marker(markerOptionsLast);
				userLastMarkers.push(marker);
				userInfoDisp(marker, i, j, true);
			} else {
				var markerOptions = {
					map : map,
					position : new google.maps.LatLng(point[0], point[1]),
					icon : {
						fillColor : "#" + markerColorList[i], // 塗り潰し色
						path : google.maps.SymbolPath.CIRCLE, // 円を指定
						scale : 5, // 円のサイズ
						strokeColor : "#" + markerColorList[i] // 枠の色
					},
					zIndex : i * 10000 + j,
					// クリックイベントを有効
					clickable : true,
					// ドラッグでアイコンを移動不可
					draggable : false
				};
				marker = new google.maps.Marker(markerOptions);
				oneUserMarkers.push(marker);
				userInfoDisp(marker, i, j, false);
			}
			menuInfoDisp(marker);
		}
		userMarkers.push(oneUserMarkers);
		userLines.push(oneUserLines);
		zIndexMax = i * 1000000;
	}

	// ユーザ選択状態とライン表示状態
	if ($('#userLineDisp').val() == '0') {
		dispLinesOff();
	} else {
		dispLinesOn();
	}

	// MAPの中心座標とズームレベルを保存
	document.getElementById('mapLatitude').value = map.getCenter().lat();
	document.getElementById('mapLongitude').value = map.getCenter().lng();
	document.getElementById('mapZoomLevel').value = map.getZoom();

	// マーカー左クリック（ユーザ情報吹き出し表示）処理
	function userInfoDisp(marker, userIndex, recordIndex, isLastMarker) {
		google.maps.event.addListener(marker, 'click', function(event) {
			if (isLastMarker) {
				// ユーザの最新位置マーカーの場合、ユーザ表示の絞込み
				viewOneUser(userIndex);
				// 選択されたユーザの最新の位置に移動する
				panToUserLastMarker(userIndex);
			}

			// 吹き出しを表示する
			showInfoWindow(marker, userIndex, recordIndex);

			// MAPクリック時に吹き出しを除去する
			google.maps.event.addListener(map, "click", function() {
				infoWindow.close();
			});
		});
	}

	// マーカー右クリック（メニュー表示）処理
	function menuInfoDisp(marker) {

		google.maps.event.addListener(marker, 'rightclick', function(event) {
			// 選択マーカーのIndexを隠し項目に保持
			var response = marker.getLabel();
			try {
				response = typeof response == "object" ? JSON.stringify(response) : response;
			} catch (e) {
			}
			// document.getElementById('selectUserIndex').value =
			// event.latLng.lat() + "," + event.latLng.lng();
			var data = JSON.parse(response);
			document.getElementById('selectUserIndex').value = data["text"];
			// 既にメニューを表示している場合
			var target = document.getElementById('divMenu1');
			if (target != null) {
				target.remove();
			}
			// 右クリックメニューを作成
			var rmenu = document.createElement("div");

			// 右クリックメニューのスタイルを設定。
			rmenu.style.visibility = "absolute";
			rmenu.id = "divMenu1";
			rmenu.style.backgroundColor = "white";
			rmenu.style.border = "2px solid black";
			rmenu.style.padding = "2px";
			rmenu.style.fontSize = "12px";
			rmenu.style.cursor = "pointer";
			rmenu.style.position = "fixed";
			rmenu.style.top = scrY - 30 + "px";
			rmenu.style.left = scrX + 30 + "px";
			rmenu.style.zIndex = "3";
			// 右クリックメニューの内容(HTML）
			rmenu.innerHTML = "<div class="menuRirekiDiv">"
					+ "<button type="button" value="menuRireki" id="menuRireki" class="menuButtonMain" onclick="menuRirekiOpen()">バッテリー・移動履歴</button>"
					+ "<button type="submit" name="action" value="menuRireki" id="menuRirekiSub" class="_hidden"></button></div>"
					+ "<div class="menuUserDiv">"
					+ "<button type="button" value="menuUser" id="menuUser" class="menuButtonMain" onclick="menuUserOpen()">ユーザ設定</button>"
					+ "<button type="submit" name="action" value="menuUser" id="menuUserSub" class="_hidden"></button></div>";
			// 右クリックメニューをmap内に返す
			document.getElementsByTagName("form").item(0).appendChild(rmenu);
		});
	}
	// MAPドラッグ時にメニューDivを除去する
	google.maps.event.addListener(map, "drag", function(event) {
		var target = document.getElementById('divMenu1');
		if (target != null) {
			target.remove();
		}
	});
	// MAPクリック時にメニューDivを除去する
	google.maps.event.addListener(map, "click", function(event) {
		var target = document.getElementById('divMenu1');
		if (target != null) {
			target.remove();
		}
	});
	// MAPの中心座標を保存
	google.maps.event.addListener(map, "center_changed", function(event) {
		document.getElementById('mapLatitude').value = map.getCenter().lat();
		document.getElementById('mapLongitude').value = map.getCenter().lng();
	});
	// MAPのズームレベルを保存
	google.maps.event.addListener(map, "zoom_changed", function(event) {
		document.getElementById('mapZoomLevel').value = map.getZoom();
	});
});

function allMarkerAndLineDisp() {
	// ユーザ選択状態を解除
	$('#selectUserIndex').val(null);
	$('#doReload').click();
}

function onClickUserIcon(userIndex) {
	// メニューDivを除去する
	var target = document.getElementById('divMenu1');
	if (target != null) {
		target.remove();
	}
	// ユーザ情報吹き出し表示を閉じる
	if (infoWindow != null) {
		infoWindow.close();
	}

	// ユーザ表示の絞り込み
	viewOneUser(userIndex);

	// 選択されたユーザの最新の位置に移動する
	panToUserLastMarker(userIndex);

	// 吹き出しを表示する
	showInfoWindow(userLastMarkers[userIndex], userIndex, userMarkers[userIndex].length);
}

function viewOneUser(userIndex) {
	// 選択されたユーザ以外の履歴マーカーをすべて除去
	userMarkers.forEach(function(markerList, idx) {
		if ($('#userLineDisp').val() == '1' && idx == userIndex) {
			markerList.forEach(function(marker, idx) {
				marker.setVisible(true);
			});
		} else {
			markerList.forEach(function(marker, idx) {
				marker.setVisible(false);
			});
		}
	});
	// 選択されたユーザ以外の移動線をすべて除去
	userLines.forEach(function(lineList, idx) {
		if ($('#userLineDisp').val() == '1' && idx == userIndex) {
			lineList.forEach(function(line, idx) {
				line.setVisible(true);
			});
		} else {
			lineList.forEach(function(line, idx) {
				line.setVisible(false);
			});
		}
	});

	// 選択したユーザのアイコンマーカーを一番手前に表示
	userLastMarkers[userIndex].setZIndex(zIndexMax + 1);
	zIndexMax = zIndexMax + 1;

	// 選択ユーザを色づけ
	for (var i = 0; i < markerListLatest.length; i++) {
		if (i == userIndex) {
			$('#userIcon'+i).css('border-color','red');
		} else {
			$('#userIcon'+i).css('border-color','black');
		}
	}
	$('#selectUserIndex').val(userIndex);
}

function panToUserLastMarker(userIndex) {
	// 選択されたユーザの最新の位置に移動する
	var userLastPoint = markerListLatest[userIndex].split(',');
	map.panTo(new google.maps.LatLng(userLastPoint[0],userLastPoint[1]));
	document.getElementById('mapLatitude').value = map.getCenter().lat();
	document.getElementById('mapLongitude').value = map.getCenter().lng();
}

function showInfoWindow(marker, userIndex, recordIndex) {
	// 1個しか表示しない
	if (infoWindow != null) {
		infoWindow.close();
	}

	// 吹き出し内容リスト取得
	var infoWindowSource = infoWindowSourceList[userIndex][recordIndex].split(',');

	// 吹き出し表示内容
	var content = "";
	content += "<div>";
	content += infoWindowSource[0]; // ユーザ名
	content += "<br>";
	content += "　取得した時間：　";
	content += infoWindowSource[1];
	content += "<br>";
	content += "　バッテリー残量：　";
	content += infoWindowSource[2];
	content += "%";
	content += "<br>";
	content += "　電波状況：　";
	content += infoWindowSource[3];
	content += "</div>";

	// 吹き出し生成
	infoWindow = new google.maps.InfoWindow({
		content : content,
		position : marker.position,
		pixelOffset : new google.maps.Size(0, -25)
	});
	// 吹き出し表示
	infoWindow.open(map);
}

function menuRirekiOpen() {
	// userIdを判定するためselectUserIndexよりINDEXを取得
	var selectIndex = document.getElementById('selectUserIndex').value;
	var userIdList = document.getElementById('userIdList').value.split(';');

	window.open("./record?loginId=" + $('#loginId').val() + "&selectUserId=" + userIdList[selectIndex] + "&zoomLevel=" + map.getZoom() + "¢erLat=" + map.getCenter().lat() + "¢erLng=" + map.getCenter().lng(), "_blank");
}

function menuUserOpen() {
	// userIdを判定するためselectUserIndexよりINDEXを取得
	var selectIndex = document.getElementById('selectUserIndex').value;
	var userIdList = document.getElementById('userIdList').value.split(';');

	window.open("./userconfig?loginId=" + $('#loginId').val() + "&selectUserId=" + userIdList[selectIndex], "_blank");
}

function authConfigOpen() {
	//$('#currentGroupId').val(document.getElementById('cmbGroupName').value);
	window.open("./authconfig?loginId=" + $('#loginId').val() + "&groupid=" + $('#currentGroupId').val() , "_blank");
}

function placeconfigOpen() {
	window.open("./placeconfig?loginId=" + $('#loginId').val() + "&zoomLevel=" + map.getZoom() + "¢erLat=" + map.getCenter().lat() + "¢erLng=" + map.getCenter().lng() + "&groupid=" + $('#currentGroupId').val(), "_blank");
}

function changeDispLines() {
	if ($('#userLineDisp').val() == '1') {
		// ライン表示から非表示へ変更
		dispLinesOff();
	} else {
		// ライン非表示から表示へ変更
		dispLinesOn();
	}
}

function dispLinesOn() {
	$('#dispLinesButton').removeClass('_off');
	$('#userLineDisp').val('1');
    if ($('#selectUserIndex').val()) {
    	viewOneUser($('#selectUserIndex').val());
    } else {
    	// 全ユーザの履歴マーカーをすべて表示
    	userMarkers.forEach(function(markerList, idx) {
    		markerList.forEach(function(marker, idx) {
    			marker.setVisible(true);
    		});
    	});
    	// 全ユーザの移動線をすべて表示
    	userLines.forEach(function(lineList, idx) {
    		lineList.forEach(function(line, idx) {
    			line.setVisible(true);
    		});
    	});
    }
}

function dispLinesOff() {
	// ユーザ情報吹き出し表示を閉じる
	if (infoWindow != null) {
		infoWindow.close();
	}
   	$('#dispLinesButton').addClass('_off');
	$('#userLineDisp').val('0');
	// 全ユーザの履歴マーカーをすべて非表示
	userMarkers.forEach(function(markerList, idx) {
		markerList.forEach(function(marker, idx) {
			marker.setVisible(false);
		});
	});
	// 全ユーザの移動線をすべて非表示
	userLines.forEach(function(lineList, idx) {
		lineList.forEach(function(line, idx) {
			line.setVisible(false);
		});
	});
}

function resizeUserListViewArea() {
	var left = $('#userListViewArea').offset().left;
	var count = 10;
	var userCount = $('#userListView').width() / 104;
	while ($(window).width() > left + count * 104 + 40 && count < userCount) {
		count = count + 1;
	}
	while ($(window).width() < left + count * 104 + 40) {
		count = count - 1;
	}
	$('#userListViewArea').css('width', count * 104);
}

//グループコンボボックス選択時
$(function() {
	$('#cmbGroupName').change(function(eObj) {
		$('#currentGroupId').val(document.getElementById('cmbGroupName').value);
		$('#cmbGroup').val($('#currentGroupId').val());
		$('#doReload').click();
	});
});