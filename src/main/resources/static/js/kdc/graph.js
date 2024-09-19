// 棒グラフ
(function() {
	var canvas1 = document.getElementById('canvas1');
	// 電波グラフ描写
	var datas = document.getElementById('batterys').value.split(',');
	
	barGraph(canvas1, datas);
	
//  var stroke_opts = {
//    color : '#00FFFF',
//    width : 2
//  };
//
//  barGraph(canvas1, datas, stroke_opts);

//  // 電波未取得グラフ描写
//  var datasNone = document.getElementById('batterysNone').value.split(',');
//
//  var stroke_optsNone = {
//    color : '#FFB6C1',
//    width : 2
//  };
//
//  barGraph(canvas1, datasNone, stroke_optsNone);

	function barGraph(canvas_obj, datas) {
		var c = canvas_obj.getContext('2d');
		c.globalAlpha = 1.0;
		
		// bar
		var pos = 0;
		var bar_width = canvas_obj.width / datas.length;
		for (var i = 0; i < datas.length; i++) {
			if (datas[i] == 'nodata') {
				var barHeight = canvas_obj.height;
				var barPos = {
						x : pos,
						y : 1,
						w : bar_width
				};
				var stroke_optsNone = {
						color : '#FFB6C1',
						width : 2
				};
				drawBar(c, barHeight, barPos, stroke_optsNone);
			} else {
				var barHeight = canvas_obj.height * datas[i] / 100;
				var barPos = {
						x : pos,
						y : canvas_obj.height - barHeight + 1,
						w : bar_width
				};
				var stroke_opts = {
						color : '#00FFFF',
						width : 2
				};
				drawBar(c, barHeight, barPos, stroke_opts);
			}
			pos += bar_width;
		}

		// 描写
		function drawBar(context, data, barPos, stroke_opts) {
			context.strokeStyle = stroke_opts.color;
			context.lineWidth = stroke_opts.width;
			context.strokeRect(barPos.x, barPos.y, barPos.w, data);
			context.fillStyle = stroke_opts.color;
			context.fillRect(barPos.x, barPos.y, barPos.w, data);
		}
	}
})();
