function displayBlock(no) {
	document.getElementById("dispBlock").value = no;
//	document.getElementById("blockArea1").style.visibility = ((no == 1) ? "visible" : "hidden");
//	document.getElementById("blockArea2").style.display = ((no == 2) ? "block" : "none");
	if(no==1){
		scrollTo(0,0);
	}else{
		document.getElementById("blockArea2").scrollIntoView(true);
	}
	for (var i = 1; i <= 2; i++) {
		document.getElementById("blockButton" + i).style.backgroundColor = ((no == i) ? "#8FBC8F" : "#F0F8FF");
	}
}

$(function() {
	$('.calIcon').datepicker({
		dateFormat : 'yy.mm.dd/D',
		dayNamesMin : [ '日', '月', '火', '水', '木', '金', '土' ],
		showOn : "button",
		buttonImageOnly : true,
		buttonImage : "../images/icon/calendar.png",
		onSelect : function(dateText, inst) {
			// カレンダー確定時にフォームに反映
			var dates = dateText.split('/');
			$(this).parent().find('#date').val(dates[0] + ' ' + dates[1]);
			var dates2 = dates[0].split('.');
			$('#date2').val(dates2[0] + dates2[1] + dates2[2]);
			$('#receiveDate').val(dates2[0] + dates2[1] + dates2[2]);
			$('#doReload').click();
		}
	});

});

// loadより前にブロックを選択する
window.addEventListener("DOMContentLoaded", function() {
	displayBlock(document.getElementById("dispBlock").value);
}, false);

var canvas;
var map;

var scrX;
var scrY;

//マーカーリスト（すべて）
var userMarkers = [];

// マーカーリスト(ユーザごとに保持)
var markerListAll = [];
//マーカーリスト（ユーザの履歴ごとの二次元配列）
var markerList = [];
//履歴情報ウィンドウリスト
var infoWindow;
var infoWindowSourceList = [];
//マーカーリスト（ユーザごとの最新）
var markerListLatest = [];

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
//		zoomControl: false,			// ズーム非表示
//		mapTypeControl: false,		// マップ切り替え非表示
		streetViewControl: false,	// ストリートビュー非表示
		fullscreenControl: false,	// 全画面モード非表示
	};
	map = new google.maps.Map(canvas, opts);

	if (markerListAll.length == 0) {
		// ダイアログのメッセージを設定
		$('#noRecordDialog').html('履歴がありません。');

		// ダイアログを作成
		$('#noRecordDialog').dialog({
			modal: true,
			title: '確認',
			buttons: {
				'OK': function() {
					$(this).dialog('close');
				}
			}
		});
		return;
	} else {
		// マーカーのリストを取得して初期表示の座標、ズームレベルを設定する
		bounds = new google.maps.LatLngBounds();
		for (var i = 0; i < markerListAll.length; i++) {
			var pointlatlng = markerListAll[i].split(';');
			for (var j = 0;j < pointlatlng.length; j++) {
				var point = pointlatlng[j].split(',');
				var position = new google.maps.LatLng(point[0], point[1]);
				bounds.extend(position);
			}
		}
		map.fitBounds(bounds);
	}

	var lineColorList = document.getElementById('lineColorList').value.split(';');
	var lineOpacityList = document.getElementById('lineOpacityList').value.split(';');
	var markerColorList = document.getElementById('markerColorList').value.split(';');
	var iconList = document.getElementById('iconList').value.split(';');
	for (var i = 0; i < markerList.length; i++) {
		var oneUserMarkers = [];
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
			}
			// Marker
			var marker;
			if (j == markerList[i].length - 1) {
				// 最終位置のマーカー
				var markerOptionsLast = {
						map : map,
						position : new google.maps.LatLng(point[0], point[1]),
						icon : "data:image/png;base64,"+iconList[i],
						label : {
							fontSize : "0px",
							text : String(i)
						},
						zIndex : j,
						// クリックイベントを有効
						clickable : true,
						// ドラッグでアイコンを移動不可
						draggable : false
				};
				marker = new google.maps.Marker(markerOptionsLast);
				oneUserMarkers.push(marker);
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
						zIndex : j,
						// クリックイベントを有効
						clickable : true,
						// ドラッグでアイコンを移動不可
						draggable : false
				};
				marker = new google.maps.Marker(markerOptions);
				oneUserMarkers.push(marker);
			}
			userInfoDisp(marker,　i, j);
		}
		userMarkers.push(oneUserMarkers);
	}
	// バーの設定
	showRecordStep();
	showInfoWindow(userMarkers[0][$('#recordStep').val()], 0, $('#recordStep').val());
	
	// マーカー左クリック（ユーザ情報吹き出し表示）処理
	function userInfoDisp(marker, userIndex, recordIndex) {
		google.maps.event.addListener(marker, 'click', function(event) {
			showInfoWindow(marker, userIndex, recordIndex);

			// MAPクリック時に吹き出しを除去する
			google.maps.event.addListener(map, "click", function() {
				infoWindow.close();
			});
		});
	}

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

function showInfoWindow(marker, userIndex, recordIndex) {
	// 1個しか表示しない
	if (infoWindow != null) {
		infoWindow.close();
	}
	
	// 吹き出し内容リスト取得
	var infoWindowSource = [];
	infoWindowSource = infoWindowSourceList[userIndex][recordIndex].split(',');
	
	// 吹き出し表示内容
	var content = "";
	content += "<div>";
	content += infoWindowSource[0];	// ユーザ名
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

function showRecordStep() {
	var source = infoWindowSourceList[0][$('#recordStep').val()].split(',');
	// 履歴の取得時刻をrangeの上部に表示
	$('#rangeValText').val(source[1]);
}

function changeRecordStep() {
	var marker = markerList[0][$('#recordStep').val()].split(',');
	// 選択した履歴の座標に地図を移動
	map.panTo(new google.maps.LatLng(marker[0],marker[1]));
	showInfoWindow(userMarkers[0][$('#recordStep').val()], 0, $('#recordStep').val());
}

function stepPrev() {
	if (parseInt($('#recordStep').val()) > parseInt(document.getElementById('recordStep').min)) {
		$('#recordStep').val(parseInt($('#recordStep').val()) - 1);
		showRecordStep();
		changeRecordStep();
	}
}
	
function stepNext() {
	if (parseInt($('#recordStep').val()) < parseInt(document.getElementById('recordStep').max)) {
		$('#recordStep').val(parseInt($('#recordStep').val()) + 1);
		showRecordStep();
		changeRecordStep();
	}
}

