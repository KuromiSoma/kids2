function listPlaceAlertScroll() {
	$('#listPlaceAlertRowView').scrollTop($('#listPlaceAlertView').scrollTop());// 縦スクロールを連動させる。
	$('#listPlaceAlertColView').scrollLeft($('#listPlaceAlertView').scrollLeft());// 横スクロールを連動させる。
};

window.onload = function() {
	displayBlock(document.getElementById('dispBlock').value);
}

//ポップアップ
$(function() {
	$('.open-popup-link').magnificPopup({
	  type:'inline'
	});
});

function displayBlock(no) {
	document.getElementById("dispBlock").value = no;
	for (var i = 1; i < 7; i++) {
		document.getElementById("blockArea" + i).style.display = ((no == i) ? "block" : "none");
	}
	for (var i = 1; i < 7; i++) {
		document.getElementById("blockButton" + i).style.backgroundColor = ((no == i) ? "#8FBC8F" : "#F0F8FF");
	}
	if (no==6) {
		$('#cmbAuthLevel').prop('disabled', true);
	} else {
		$('#cmbAuthLevel').prop('disabled', false);
	}
};

function changeDisplayBlock(no) {
	if (!authConfigValidator()) {
		return;
	}
	document.getElementById("nextDispBlock").value = no;
	document.getElementById('doCommit').click();
};

function closeAuthConfig() {
	if (!authConfigValidator()) {
		return;
	}
	document.getElementById('doFinalCommit').click();
};

$(function() {
	$('#cmbAuthLevel').change(function(eObj) {
		if (!authConfigValidator()) {
			$('#cmbAuthLevel').val($('#currentAuthLevel').val());
			displayBlock(document.getElementById('dispBlock').value);
			eObj.preventDefault();
			return;
		}
		document.getElementById('doCommit').click();
	});
});

//グループコンボボックス選択時
$(function() {
	$('#cmbAuthGroup').change(function(eObj) {
		$('#groupName').val($('#cmbAuthGroup option:selected').text());
		document.getElementById('doCommit').click();
	});
});

//グループ名称変更時
function changeGroupName() {
	document.getElementById('newGroupName').value = document.getElementById('groupName').value;
}

function authConfigValidator() {
	var errorFlg = false;

	switch (document.getElementById('dispBlock').value) {
	case '1':
		break;
	case '2':
		if (!checkUserValue()) {
			return false;
		}
		break;
	case '3':
		if (!checkPlaceValue()) {
			return false;
		}
		break;
	case '4':
		if (!checkTransmissionValue()) {
			errorFlg = true;
		}
		break;
	case '5':
		break;
	case '6':
		if (!checkCommonConfigValue()) {
			errorFlg = true;
		}
		break;
	}
	return !errorFlg;
}

function checkUserValue() {
	var errorFlg = false;

	for (var i = 0; i < 4; i++) {
		var alertFlgName;
		var notificationName;
		var alertTimeName;
		var vibrationTimeName;
		var ReferenceName;
		var batteryFlg;

		// バッテリー
		alertFlgName = '#batteryLevelAlertFlg' + i;
		notificationName = 'userAlert[' + i + '].batteryNotification';
//		alertTimeName = '#batteryAlarmTime' + i;
		vibrationTimeName = '#batteryVibrationTime' + i;
		referenceName = '#batteryReference' + i;

		if (!checkAlertValue(alertFlgName, notificationName, alertTimeName, vibrationTimeName)) {
			errorFlg = true;
		}
		$(referenceName).closest('td').css('background-color', '#ffffff');
		if ($(alertFlgName).prop('checked')) {
			// バッテリー基準値
			if (!$(referenceName).val()) {
				$(referenceName).closest('td').css('background-color', 'orange');
				errorFlg = true;
			}
		}

		// 接続
		alertFlgName = '#connectionAlertFlg' + i;
		notificationName = 'userAlert[' + i + '].connectionNotification';
//		alertTimeName = '#connectionAlarmTime' + i;
		vibrationTimeName = '#connectionVibrationTime' + i;

		if (!checkAlertValue(alertFlgName, notificationName, alertTimeName, vibrationTimeName)) {
			errorFlg = true;
		}

		// SOS
		alertFlgName = '#sosAlertFlg' + i;
		notificationName = 'userAlert[' + i + '].sosNotification';
//		alertTimeName = '#sosAlarmTime' + i;
		vibrationTimeName = '#sosVibrationTime' + i;

		if (!checkAlertValue(alertFlgName, notificationName, alertTimeName, vibrationTimeName)) {
			errorFlg = true;
		}
	}
	return !errorFlg;
};

function checkPlaceValue() {
	var errorFlg = false;
	var ReferenceName;
	var batteryFlg;

	var placeCount = parseInt($('#placeCount').val());
	for (var i = 0; i < 4; i++) {
		for (var j = 0; j < placeCount; j++) {
			var alertFlgName = '#placeAlertFlg' + i + j;
			var notificationName = 'placeAlert[' + i + '].alert[' + j + '].placeNotification';
//			var alertTimeName = '#placeAlarmTime' + i + j;
			var alertTimeName;
			var vibrationTimeName = '#placeVibrationTime' + i + j;

			if (!checkAlertValue(alertFlgName, notificationName, alertTimeName, vibrationTimeName)) {
				errorFlg = true;
			}
		}
	}
	return !errorFlg;
};

function checkAlertValue(alertFlgName, notificationName, alertTimeName, vibrationTimeName) {
	var errorFlg = false;

	$('input[name="' + notificationName + '"]').closest('td').css('background-color', '#ffffff');
//	$(alertTimeName).closest('td').css('background-color', '#ffffff');
	$(vibrationTimeName).closest('td').css('background-color', '#ffffff');

	if ($(alertFlgName).prop('checked')) {
		// 通知方法
		if (!$('input[name="' + notificationName + '"]:checked').val()) {
			$('input[name="' + notificationName + '"]').closest('td').css('background-color', 'orange');
			errorFlg = true;
		}
		// 鳴動時間
//		if (!$(alertTimeName).val()) {
//			$(alertTimeName).closest('td').css('background-color', 'orange');
//			errorFlg = true;
//		}
		// バイブ時間
		if (!$(vibrationTimeName).val()) {
			$(vibrationTimeName).closest('td').css('background-color', 'orange');
			errorFlg = true;
		}
	}
	return !errorFlg;
};

function checkTransmissionValue() {
	var errorFlg = false;
	var startTimePrev;
	var endTimePrev;

	for (var i = 0; i < 7; i++) {
		for (var j = 0; j < 5; j++) {
			$('#startTime' + i + j).closest('td').css('background-color', '#ffffff');
			$('#endTime' + i + j).closest('td').css('background-color', '#ffffff');
			$('#transmissionInterval' + i + j).closest('td').css('background-color', '#ffffff');

			var startTimeName = '#startTime' + i + j;
			var endTimeName = '#endTime' + i + j;
			var intervalName = '#transmissionInterval' + i + j;

			// １つでも入力があれば必須
			if ($(startTimeName).val() || $(endTimeName).val() || $(intervalName).val()) {
				// 入力必須
				if (!$(startTimeName).val()) {
					$(startTimeName).closest('td').css('background-color', 'orange');
					errorFlg = true;
				}
				if (!$(endTimeName).val()) {
					$(endTimeName).closest('td').css('background-color', 'orange');
					errorFlg = true;
				}
				if (!$(intervalName).val()) {
					$(intervalName).closest('td').css('background-color', 'orange');
					errorFlg = true;
				}
			}

			// 開始･終了時間の整合性
			if (!checkTimeValue($(startTimeName).val())) {
				$(startTimeName).closest('td').css('background-color', 'orange');
				errorFlg = true;
			}
			if (!checkTimeValue($(endTimeName).val())) {
				$(endTimeName).closest('td').css('background-color', 'orange');
				errorFlg = true;
			}
			var startTime = parseInt($(startTimeName).val().replace(/:/g, ''));
			var endTime = parseInt($(endTimeName).val().replace(/:/g, ''));
			if (endTime == 0) {
				$(endTimeName).closest('td').css('background-color', 'orange');
				errorFlg = true;
			}
			if (startTime >= endTime) {
				$(startTimeName).closest('td').css('background-color', 'orange');
				$(endTimeName).closest('td').css('background-color', 'orange');
				errorFlg = true;
			}

			if (j > 0) {
				// 歯抜け
				if ($(startTimeName).val() && isNaN(endTimePrev)) {
					$(startTimeName).closest('td').css('background-color', 'orange');
					$(endTimeName).closest('td').css('background-color', 'orange');
					$(intervalName).closest('td').css('background-color', 'orange');
					errorFlg = true;
				}

				// 時間かぶり
				if ($(startTimeName).val() && startTime < endTimePrev) {
					$(startTimeName).closest('td').css('background-color', 'orange');
					errorFlg = true;
				}
				if ($(endTimeName).val() && endTime <= endTimePrev) {
					$(endTimeName).closest('td').css('background-color', 'orange');
					errorFlg = true;
				}
			}
			startTimePrev = startTime;
			endTimePrev = endTime;
		}
	}
	return !errorFlg;
}

function checkTimeValue(timeValue) {
	var val = timeValue.replace(/:/g, '');
	if (val.length != 0) {
		if (!isFinite(val))
			return false;
		if (val.length < 3 || val.length > 4)
			return false;
		var num = timeValue.replace(/^(\d{1,2}):?(\d{2})$/, '$1:$2');
		var time = num.split(':');
		if (time.length != 2)
			return false;
		var hour = parseInt(time[0]);
		var minute = parseInt(time[1]);
		if (hour >= 0 && hour < 24 && minute >= 0 && minute < 60) {
			return true;
		}
		return false;
	}
	return true;
};

function checkCommonConfigValue() {
	var errorFlg = false;

	$('#sosCountdown').closest('td').css('background-color', '#ffffff');
	$('#disconnectTime').closest('td').css('background-color', '#ffffff');

	if (!$('#sosCountdown').val()) {
		$('#sosCountdown').closest('td').css('background-color', 'orange');
		errorFlg = true;
	}
	if (!$('#disconnectTime').val()) {
		$('#disconnectTime').closest('td').css('background-color', 'orange');
		errorFlg = true;
	}

	if (parseInt($('#sosCountdown').val()) > 10) {
		$('#sosCountdown').closest('td').css('background-color', 'orange');
		errorFlg = true;
	}

	if(!$('#groupName').val()) {
		$('#groupName').closest('td').css('background-color', 'orange');
		errorFlg = true;
	}

	return !errorFlg;
}

function setUserAlertEditable(code, index, checked) {
	switch (code) {
	case 1:
		// batteryLevelAlertFlg
		$('input[name="userAlert['+index+'].batteryNotification"]').prop('disabled', !checked);
//		$('#batteryAlarmTime'+index).prop('disabled', !checked);
		$('#batteryVibrationTime'+index).prop('disabled', !checked);
		$('#batteryPopup'+index).prop('disabled', !checked);
		$('#batteryReference'+index).prop('disabled', !checked);
		break;
	case 2:
		// connectionAlertFlg
		$('input[name="userAlert['+index+'].connectionNotification"]').prop('disabled', !checked);
//		$('#connectionAlarmTime'+index).prop('disabled', !checked);
		$('#connectionVibrationTime'+index).prop('disabled', !checked);
		$('#connectionPopup'+index).prop('disabled', !checked);
		break;
	case 3:
		// sosAlertFlg
		$('input[name="userAlert['+index+'].sosNotification"]').prop('disabled', !checked);
//		$('#sosAlarmTime'+index).prop('disabled', !checked);
		$('#sosVibrationTime'+index).prop('disabled', !checked);
		$('#sosPopup'+index).prop('disabled', !checked);
		break;
	}
};

function setPlaceAlertEditable(index, placeIndex, checked) {
	$('input[name="placeAlert['+index+'].alert['+placeIndex+'].placeNotification"]').prop('disabled', !checked);
//	$('#placeAlarmTime'+index+placeIndex).prop('disabled', !checked);
	$('#placeVibrationTime'+index+placeIndex).prop('disabled', !checked);
	$('#placePopup'+index+placeIndex).prop('disabled', !checked);
};
