$(function() {
	$('#iconImg').change(function(){
		if (this.files.length > 0) {
			var file = $(this).prop('files')[0];
			var fr = new FileReader();
			fr.onload = function() {
				$('#selectedIconDataString').val(fr.result);
				document.getElementById("changeIcon").click();
			}
			fr.readAsDataURL(file);
		}
	});
});

$(function() {
	$('#cmbUser').change(function(eObj) {
		if (!checkInputData()) {
			$('#cmbUser').val($('#selectedUserId').val());
			eObj.preventDefault();
			return;
		}
		$('#selectedUserId').val($('#cmbUser').val());
		document.getElementById("changeUser").click();
	});
});

function checkInputData() {
	$('#userName').closest('td').css('background-color', '#ffffff');
	$('#password').closest('td').css('background-color', '#ffffff');
	if(!$('#userName').val()){
		$('#userName').closest('td').css('background-color', 'orange');
		return false;
	}
	if(!$('#password').val()){
		$('#password').closest('td').css('background-color', 'orange');
		return false;
	}
	return true;
}

window.onload = function() {
	changeMarkerColor();
};

function changeLineColor() {
	var color = document.getElementById('lineColorSelect').value;
	document.getElementById('lineColorSelect').value = color;
	document.getElementById('lineColor').value = color;
}

function changeMarkerColor() {
	var color = 'rgb(' + hsvToRgb(document.getElementById('markerColor').value, 1, 1).join(",") + ')';
	document.getElementById('dispMarkerColor').style.backgroundColor = color;
}

function doDeleteUser() {
	// ダイアログのメッセージを設定
	$('#deleteUserDialog').html('このユーザを削除します。<br/>よろしいですか？');

	// ダイアログを作成
	$('#deleteUserDialog').dialog({
		modal: true,
		title: '確認',
		buttons: {
			'OK': function() {
				$(this).dialog('close');
				// 削除実行
				document.getElementById('doDelete').click();
			},
			'キャンセル': function() {
				$(this).dialog('close');
			}
		}
	});
}

function hsvToRgb(H, S, V) {

	var C = V * S;
	var Hp = H / 60;
	var X = C * (1 - Math.abs(Hp % 2 - 1));

	var R, G, B;
	if (0 <= Hp && Hp < 1) {
		[ R, G, B ] = [ C, X, 0 ]
	}
	;
	if (1 <= Hp && Hp < 2) {
		[ R, G, B ] = [ X, C, 0 ]
	}
	;
	if (2 <= Hp && Hp < 3) {
		[ R, G, B ] = [ 0, C, X ]
	}
	;
	if (3 <= Hp && Hp < 4) {
		[ R, G, B ] = [ 0, X, C ]
	}
	;
	if (4 <= Hp && Hp < 5) {
		[ R, G, B ] = [ X, 0, C ]
	}
	;
	if (5 <= Hp && Hp < 6) {
		[ R, G, B ] = [ C, 0, X ]
	}
	;

	var m = V - C;
	[ R, G, B ] = [ R + m, G + m, B + m ];

	R = Math.floor(R * 255);
	G = Math.floor(G * 255);
	B = Math.floor(B * 255);

	return [ R, G, B ];
}