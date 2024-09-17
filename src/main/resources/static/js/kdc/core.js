//--------------------------------------------------------------------------
// 指定名称が　id　か　name　か判定する(ajaxで使用)
function getItemKbn(itemIdName) {
	return $("[name=" + itemIdName + "]:first").attr("id") == itemIdName ? "Id" : "Nm";
}
//--------------------------------------------------------------------------
//指定名称の設定内容を取得する（ajaxで使用）
function getItemVal(itemIdName) {
	var val;
	var kbn = getItemKbn(itemIdName);
	if(kbn == "Id") {
		val = $("#" + itemIdName).val();
	} else {
		if($("[name=" + itemIdName + "]:first").attr("type") == "radio") {
			val = $("[name=" + itemIdName + "]:checked").val();
		}else{
			val = $("[name=" + itemIdName + "]:first").val();
		}
	}
	return val;
}

//確認メッセージ表示（CONFIRM_NOMAL）
function confirmNormal(e, msg1) {
	if(!confirm(msg1)) {
		e.preventDefault();
		e.stopPropagation();
		e.stopImmediatePropagation();
		return false;
	}
	return true;
}

function isNumberCheck(target){
    if ($(target).hasClass("_attributeTextNum") || $(target).hasClass("_attributeCode")){
	    if(!isFinite($(target).val())){
	    	$(target).val("");
	    };
    };
}

//半角数値入力制御
$(function(){
	$('input._attributeNum').focusout(function(e) {
		isNumberCheck(this);
	});

	$('input._attributeTime').focusout(function(e) {
		isNumberCheck(this);
	});
});

function isNumberCheck(target){
    if ($(target).hasClass("_attributeNum")){
	    if(!isFinite($(target).val())){
	    	$(target).val("");
	    };
    };
}

function isTimeCheck(target){
    if ($(target).hasClass("_attributeTime")){
	    if(!$(target).val().match(/^\d{1,2}:?\d{2}$/)){
	    	$(target).val("");
	    };
    };
}

// 時刻入力項目編集
$(function() {
	var target  = 'input._attributeTime';
	TimeFormat(target, "");
});
function TimeFormat(target, select) {
	$(target).off('focusin.colon').on('focusin.colon', select, function(){
		$(this).val($(this).val().replace(/:/g, '').replace(/ /g, ''));
	});
	$(target).off('focusout.colon').on('focusout.colon', select, function(){
	    isTimeCheck(this);
		var val = $(this).val().replace(/:/g, '').replace(/ /g, '')
		if(val.length != 0){
			if(isFinite(val)){
				if(val.length < 3 || val.length > 4) return;
				var num = $(this).val();
				num = num.replace(/^(\d{1,2}):?(\d{2})$/, '$1:$2');
				var time = num.split(':');
				var hour = parseInt(time[0]);
				var minute = parseInt(time[1]);
				if(hour < 0 || hour >= 24) return;
				if(minute < 0 || minute >= 60) return;
				$(this).val(parseInt(time[0])+':'+time[1]);
				return;
			}
		}
	});
}