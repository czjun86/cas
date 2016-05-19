
var SVGCircle = function(args){
	
	var private, public, fn, handle, result;
	
	private = {
		//地球半径(单位：米)
		radius: 6371229,
		//比例大小(这里是通过计算出来的)
		ratio: 0,
		//比例倍数
		multiple: 3,
		//基站原点(坐标)
		origin: {x:0, y:0},
		//基站与原点距离
		staRange: [],
		//第一组点的数据
		point: [],
		//基站与原点的最小距离
		minRange: 0,
		//点的间距
		space: 10,
		//基站与原点最小坐标
		minCoor: null,
		//当前样式
		style: {top:0, left:0, width:433, height:400},
		//零时样式
		tempStyle: {top:0, left:0, width:433, height:400},
		//当前显示点的组数据
		datas: {},
		flag: false,
		//基站是否已创建
		isStation: false
	};
	
	
	
	public = {
//		//原点的经纬度
//		origin: [80, 50],
//		//基站的经纬度{基站名称：[经度，纬度]}
//		station: {
//			sta1: {
//				//基站类型
//				type: 1,
//				//基站的经纬度
//				landl: [111, 51],
//				//基站上的角度
//				Angle: [[10,70,'sta1_key1','#FF5500'],[130,190,'sta1_key2','#FF0000'],[250,310,'sta1_key3','#03429F']]
//			},
//			sta2: {
//				//基站类型
//				type: null,
//				landl: [11, 51],
//				Angle: [[10,70,'sta2_key1','#B848FF'],[130,190,'sta2_key2','#3496D3'],[250,310,'sta3_key3','#3A7C1A']]
//			}
//		},
//		//点的数据
//		datas: {
//			RSCP: [
//				{x:12,y:19,color:'#FFBD1E', id: 'RSCP_Id_01', type: 'RSCP_Type_01'},
//				{x:20,y:90,color:'#0480FF'},
//				{x:52,y:21,color:'#0DA60D'},
//				{x:82,y:80,color:'#0480FF'},
//				{x:42,y:77,color:'#FFBD1E'}
//			],
//			EcNo: [
//				{x:15,y:22,color:'#FFBD1E'},
//				{x:22,y:92,color:'#0480FF'},
//				{x:55,y:25,color:'#0DA60D'},
//				{x:85,y:83,color:'#0480FF'},
//				{x:45,y:80,color:'#FFBD1E'}
//			],
//			TxPower: [
//				{x:12,y:19,color:'#FFBD1E'},
//				{x:20,y:90,color:'#0480FF'},
//				{x:52,y:21,color:'#0DA60D'},
//				{x:82,y:80,color:'#0480FF'},
//				{x:42,y:77,color:'#FFBD1E'}
//			],
//			PSC:[
//				{x:12,y:19,color:'#FFBD1E'},
//				{x:20,y:90,color:'#0480FF'},
//				{x:52,y:21,color:'#0DA60D'},
//				{x:82,y:80,color:'#0480FF'},
//				{x:42,y:77,color:'#FFBD1E'}
//			]
//		}
	};
	
	fn = {
		/**
		 * 计算初始值
		 */
		init: function(){
			var svg = $('#SVG');
			private.R = Raphael('SVG');
			//获取宽、高、left、top
			var style = private.style;
			style.width = svg.width(), style.height = svg.height();
			style.left = parseInt(svg.css('left')), style.top = parseInt(svg.css('top'));
			style.left = isNaN(style.left)?0:style.left, style.top = isNaN(style.top)?0:style.top;
			//获取基站的原点
			var origin = private.origin;
			origin.x = style.width/2, origin.y = style.height/2;
			//计算当前比例
			private.ratio = (private.multiple*(((style.height/2)+(style.width/2))/2))/private.radius;
		},
		//创建点
		circle: function(ID, data){
			private.point = [];
			//[0,0]点
			var o = [100, 100];
			//点的半径
			var r = 6;
			var R = private.R
			var cir = R.set();
			var i, len = data.length, items;
			var w = private.style.width, h = private.style.height;
			var ws = (w-(2*o[0]))/100, hs = (h-(2*o[1]))/100;
			var x, y;
			for(i=0;i<len;i++){
				items = data[i];
				x = (ws*(items.x))-(r/2)+o[0], y = (hs*(items.y))-(r/2)+o[1];
				private.point.push([x, y]);
				cir.push(R.circle(x, y, r));
				cir[i].attr({fill: items.color, 'stroke-width': 0, 'fill-opacity': 100});
			}
			cir.mouseover(function(){
				this.attr('stroke-width', 3);
			}).mouseout(function(){
				this.attr('stroke-width', 0);
			}).click(function(event){
				var x = parseInt(this.attr('cx'))-r-1, y = parseInt(this.attr('cy'))-r-1;
				event = event || window.event;
				var target = $(event.target || event.srcElement);
				//获取当前点击的点的数据
				var ID = target.attr('data'), TYPE = target.attr('type');
				//alert('ID:'+ID+'-----TYPE:'+TYPE);
				clickPoint(TYPE,ID);
			});
			var top = (hs*data[0].y)+(o[1]+2), left = (ws*data[0].x)+(o[0]-12);
			left = left < 0 ? 0 : left;
			var html = [];
			var mTop = window.attachEvent ? -17 : -13;
			html.push('<label id="SVG_'+ID+'" ');
			html.push('style="float:left;font-size:12px;width:auto;height:auto;position:absolute;overflow:hidden;');
			html.push('top:'+top+'px;left:'+left+'px;">');
			html.push('<label style="float:left;">'+ID.replace('EcNo', 'Ec/No')+'</label>');
			html.push('<label style="float:left;height:12px;margin-top:'+mTop+'px;font-size:40px;color:#888888;">&rarr;</label>');
			html.push('</label>');
			svg = $('#SVG');
			svg.append(html.join('\r\n'));
			svg = svg.children().children();
			var id, datas_id, datas_type;
			svg.each(function(index, element){
				element = $(element);
				id = element.attr('id');
				//datas_id = data[index].id;
				if(!id){ element.attr('id', 'SVG_'+ID); }
			});
			//IE和firefox的标签不一样，通过取不同标签，获取要设置数据的元素
			var circle = $('#SVG').find('circle');
			var shape = $('#SVG').find('shape');
			var eachElem = circle.length > 0 ? circle : shape;
			//遍历元素，给每一个元素邦数据
			eachElem.each(function(index, element){
				element = $(element);
				//获取数据的ID
				datas_id = data[index].id;
				datas_id = datas_id ? datas_id : '';
				//获取数据的Type
				datas_type = data[index].type;
				datas_type = datas_type ? datas_type : '';
				//绑定数据
				element.attr('data', datas_id).attr('type', datas_type);
		    });
			if(!private.isStation){
				fn.maxLen();fn.station();
			}
		},
		//遍历数据
		formatData: function(ID){
			var datas = public.datas;
			private.datas[ID] = datas[ID];
			fn.circle(ID, datas[ID]);
		},
		/**
		 * 计算点距原点的最大值
		 */
		maxLen: function(){
			var points = private.point;
			var origin = private.origin;
			var i, len = points.length, point, px, py, x = [], y = [];
			for(i=0;i<len;i++){
				point = points[i];
				px = point[0], py = point[1];
				x.push(Math.abs(parseInt(origin.x)-parseInt(px)));
				y.push(Math.abs(parseInt(origin.y)-parseInt(py)));
			}
			var maxX = SJ.maxValue(x);
			var maxY = SJ.maxValue(y);
			private.pointLen = Math.sqrt((maxX*maxX)+(maxY*maxY));
		},
		/**
		 * 通过角度，计算点的坐标，首先定位第一个点的坐标
		 * @params datas <JSON> 当前的所有点的数据
		 */
		formatAngle: function(ID, datas){
			var key, data, i, len, items, angle, x, y;
			len = datas.length;
			private.datas[ID] = datas;
			for(i=0;i<len;i++){
				items = datas[i];
				angle = items.angle;
				if(i == 0){
					x = private.origin.x + Math.sin(angle*Math.PI/180)*private.space;
					y = private.origin.y + Math.cos(angle*Math.PI/180)*private.space;
				}
				else{
					x = x + Math.sin(angle*Math.PI/180)*private.space;
					y = y + Math.cos(angle*Math.PI/180)*private.space;
				}
				y = y - (2*(y - private.origin.y))-14;
				items.x = x, items.y = y;
			}
			fn.circle(ID, datas);
		},
		/**
		 * 放大
		 */
		magnify: function(args){
			var svg = $('#SVG');
			var style = private.style;
			var tempStyle = private.tempStyle;
			var w = style.width, h = style.height, top = tempStyle.top, left = tempStyle.left;
			w = style.width = w+200, h = style.height = h+200;
			top = style.top = tempStyle.top = top - 100, left = style.left = tempStyle.left = left - 100;
			private.space += 10;
			svg.css('width', (w)+'px').css('height', (h)+'px').css('left', (left)+'px').css('top', (top)+'px');
			var origin = private.origin;
			origin.x = w/2, origin.y = h/2;
			//fn.station();
			private.flag = false;
			if(!args){
				var child = svg.children().eq(0);
				if(child[0].nodeName == 'DIV'){ child.css('width', (w)+'px').css('height', (h)+'px'); }
				else{ child.attr('width', w).attr('height', h); }
				//从新排列点坐标
				var key, data, datas = private.datas;
				for(key in datas){
					if(datas[key]){
						//1.移出所有点
						$(".svg-div").find('#SVG_'+key).remove();
						//2.从新绑定
						private.isStation = false;
						fn.circle(key, datas[key]);
						//fn.formatAngle(key, datas[key]);
					}
				}
			}
		},
		/**
		 * 缩小
		 */
		reduce: function(args){
			var svg = $('#SVG');
			var style = private.style;
			var tempStyle = private.tempStyle;
			var w = style.width, h = style.height, top = tempStyle.top, left = tempStyle.left;
			var svgW = $('.svg-div').width(), svgH = $('.svg-div').height()-40;
			if(w > svgW){
				w = style.width = w-200, h = style.height = h-200;
				private.flag = true;
				if(w == svgW){ top = style.top, left = style.left;private.flag = false; }
				top = style.top = tempStyle.top = top + 100, left = style.left = tempStyle.left = left + 100;
				private.space -= 10;
				if(w-svgW >= 0 && !args){
					left = style.left = tempStyle.left = left - (left + (w-svgW));
					top = style.top = tempStyle.top = top - (top + (h-svgH));
					left = left/2, top = top/2;
					private.flag = true;
				}
				svg.css('width', (w)+'px').css('height', (h)+'px').css('left', (left)+'px').css('top', (top)+'px');
				var origin = private.origin;
				origin.x = w/2, origin.y = h/2;
				//fn.station();
				if(!args){
					svg.find('svg').attr('width', w).attr('height', h);
					//从新排列点坐标
					var key, data, datas = private.datas;
					for(key in datas){
						if(datas[key]){
							//1.移出所有点
							$(".svg-div").find('#SVG_'+key).remove();
							//2.从新绑定
							private.isStation = false;
							fn.circle(key, datas[key]);
							//fn.formatAngle(key, datas[key]);
						}
					}
				}
			}
		},
		/**
		 * 拖动
		 */
		darg: function(){
			var svg = $('#SVG');
			var label = $('#label'), tempx, tempy, flag = 2;
			var svgW = $('.svg-div').width(), svgH = $('.svg-div').height()-40;
			svg.unbind('mousedown').mousedown(function(event){
				event = event || window.event;
				var tl = SJ.getElementTL(svg[0]);
				var top = private.style.top, left = private.style.left;
				var width = private.style.width, height = private.style.height;
				ts = private.flag ? tempy : top, ls = private.flag ? tempx : left;
				var x = event.clientX - tl.left;
				var y = event.clientY - tl.top;
				if(svgW - private.style.width == left){ flag = 1; }else{ flag = 2; }
				label.html('down:'+ls+'*'+ts);
				var tsm, lsm;
				$(document).unbind('mousemove').mousemove(function(event){
					event = event || window.event;
					var mx = event.clientX - tl.left;
					var my = event.clientY - tl.top;
					var jlx = mx-x, jly = my-y;
					tsm = ts + jly, lsm = ls + jlx;
					lsm = lsm > 0 ? 0 : lsm;
					tsm = tsm > 0 ? 0 : tsm;
					lsm = lsm < svgW-width ? svgW-width: lsm;
					tsm = tsm < svgH-height ? svgH-height : tsm;
					label.html('move:'+lsm+'*'+tsm);
					svg.css('left', (lsm)+'px').css('top', (tsm)+'px');
				});
				$(document).unbind('mouseup').mouseup(function(event){
					private.tempStyle.top = tempy = tsm ? tsm : parseInt(svg.css('top'));
					private.tempStyle.left = tempx = lsm ? lsm : parseInt(svg.css('left'));
					$(document).unbind();
					private.flag = true;
				});
			});
		},
		/**
		 * 复选框点击事件
		 * @param $this <Element> 当前事件触发元素
		 */
		checkClick: function($this){
			setTimeout(function(){
				$this = $($this);
				var checked = $this.attr('checked');
				var ID = $this.attr('id');
				if(checked){ fn.formatData(ID);/*fn.magnify(true);fn.reduce(true);*/ }
				else{ $(".svg-div").find('#SVG_'+ID).remove();private.datas[ID] = null; }	
			}, 10);
		},
		/**
		 * 度数转弧度
		 */
		angToRad: function(angle_d){
			var Pi = 3.1415926535898;
            return angle_d * Pi / 180;
		},
		/**
		 * 计算角度
		 * @param station <Json> 基站数据(经纬度)
		 */
		getAngle: function(station){
			var origin = public.origin;
			var lng1 = origin[0], lat1 = origin[1];
            var lng2 = station[0], lat2 = station[1];
			var azimuth = 0, averageLat=(lat1+lat2)/2;
            if(lat1-lat2==0){ azimuth=90;}
            else{ azimuth = Math.atan((lng1-lng2)*Math.cos(fn.angToRad(averageLat))/(lat1-lat2))*180/Math.PI; }
            if (lat1 > lat2){ azimuth = azimuth + 180; }
            if (azimuth < 0){ azimuth = 360 + azimuth; }
			return azimuth;
		},
		/**
		 * 计算距离
		 * @param station <Json> 基站数据(经纬度)
		 */
		getrRange: function(station){
			var origin = public.origin;
			var lng1 = origin[0], lat1 = origin[1];
            var lng2 = station[0], lat2 = station[1];
			//地球半径(单位：米)
			var r = private.radius;
            var dis = Math.sqrt(Math.pow((lng1-lng2)*Math.PI*r*Math.cos((lat1+lat2)/2*Math.PI/180)/180,2)+Math.pow((lat1-lat2)*Math.PI*r/180,2));
            return dis;
		},
		/**
		 * chart
		 */
		chart: function(container, datas){
			var args = {
				chart: {
					width:60,height:60,
					renderTo: container,
					plotBackgroundColor: '#000000',
					plotBorderWidth: null,
					plotShadow: false
				},
				legend: { enabled: false },
				credits: { enabled: false },
				tooltip: { enabled: false },
				exporting: { enabled: false },
				title: { text: '' },
				plotOptions: {
					pie: {
						allowPointSelect: true,
						cursor: 'pointer',
						dataLabels: { enabled: false },
						showInLegend: true
					}
				},
				series: [{
					type: 'pie',
					name: 'Browser share',
					data: [
						{
							name: 'Chrome',
							y: 50,
							sliced: true,
							color: 'red',
							selected: true
						}
					]
				}]
			};
			var len1 = datas.length, len2, i, d1, d2, d, array = [], arys = [], items;
			for(i=0;i<len1;i++){
				items = datas[i];
				d1 = items[0], d2 = items[1], key = items[2], color = items[3];
				d = d2-d1;
				if(i == 0){
					len2 = array.length;
					array.push({name:'KEY'+(len2),y:d1});
					arys.push(len2);
					len2 = array.length;
					array.push({name:key,y:d,color:color});
				}
				else{
					len2 = array.length;
					array.push({name:'KEY'+(len2),y:d1-datas[i-1][1]});
					arys.push(len2);
					len2 = array.length;
					array.push({name:key,y:d,color:color});
					if(i == len1 - 1){
						len2 = array.length;
						array.push({name:'KEY'+(len2),y:(360-d2)});
						arys.push(len2);
					}
				}
			}
			args.series[0].data = array;			
			var chart = new Highcharts.Chart(args);
			for(i=0,len1=arys.length;i<len1;i++){
				chart.series[0].data[arys[i]].setVisible(false);
			}
		},
		/**
		 * 创建基站
		 * @param station <Json> 基站数据(经纬度)
		 * @param origin <Array> 原点经纬度
		 */
		station: function(station, origin){
			//计算当前比例
			private.ratio = (private.multiple*(private.style.height/2))/private.radius;
			station = station ? station : public.station;
			origin = origin ? origin : public.origin;
			var svg = $('#SVG');
			var o = svg.find('#origin');
			if(o.length == 0){
				var o = $('<div id="origin" style="font-size:0px;width:5px;height:5px;position:absolute;"></div>');
				svg.append(o);
			}
			private.origin.y -= 2.5, private.origin.x -= 2.5;
			o.css('top', (private.origin.y)+'px').css('left', (private.origin.x)+'px');
			//if(!private.isStation){
				//var svg = $('#SVG');	
				var sta, sHtml, angle, range, sta_xy, tempElem;
				private.staRange = [];
				var pointLen = private.pointLen;
				var x, y, w = private.style.width, h = private.style.height, count = 0;
				for(var key in station){
					sHtml = [], sta_xy = [];
					sta = station[key];
					//移出原来的
					//tempElem = svg.find('#'+key);
					//if(tempElem.length == 0){
						svg.find('#'+key).remove();
						sHtml.push('<div id="'+key+'" style="width:60px;height:60px;position:absolute;font-size:0px;"></div>');
						sHtml = $(sHtml.join('\r\n'));
						svg.append(sHtml);
					//}
					//else{sHtml = tempElem;}
					//计算角度
					angle = fn.getAngle(sta.landl);
					//计算距离
					range = fn.getrRange(sta.landl);
					range = range*private.ratio;
					range = range*(pointLen/range);
					//alert(range*private.ratio)
					//转换为xy坐标(需要相对于原点定位)
					sta_xy.push(private.origin.y + Math.sin(angle*Math.PI/180)*range);
					sta_xy.push(private.origin.x + Math.cos(angle*Math.PI/180)*range);
					private.staRange.push(SJ.Round(range));
					x = sta_xy[0], y = sta_xy[1];
					x = x > w ? w : x, y = y > h ? h : y;
					//根据基站不同类型，定位不同位置
					if(sta.type == 1){
						x = count*60, y = private.style.height - 60;
						count += 1;
					}
					sHtml.css('top', (y)+"px").css('left', (x)+"px")
						.attr('coor', '{y:'+y+',x:'+x+'}')
						.attr('Range', SJ.Round(range))
						.addClass('station');
					fn.chart(key, sta.Angle);
					sHtml.find('svg>rect').eq(0).remove();
					sHtml.find('.highcharts-container shape').eq(0).remove();
				}
				//基站与原点的最小距离
				private.minRange = SJ.minValue(private.staRange);
				//最小距离基站的坐标
				var _range, coor;
				var div_sta = svg.find('>div.station').each(function(){
					_range = $(this).attr('Range');
					if(_range == private.minRange){ private.minCoor = eval('('+$(this).attr('coor')+')'); }
				});
				private.isStation = true;
			//}
		}
	};
	
	handle = {
		/**
		 * 初始化函数
		 */
		initArgs: function(args){
			if(args){ SJ.extend(public, args); }
			fn.init();
			//fn.cOrigin();
			handle.bindEvent();
			SJ.Component["JCheckBoxs"]();
		},
		/**
		 * 绑定事件
		 */
		bindEvent: function(){
			//放大
			$('#magnify').unbind('click').click(function(){ fn.magnify(); });
			//缩小
			$('#reduce').unbind('click').click(function(){ fn.reduce(); });
			//复选框点击事件
			$('.svg-div-title').find('input[type=checkbox]').unbind('click').click(function(){ fn.checkClick(this); });
			//拖动
			fn.darg();
		}
	};
	handle.initArgs(args);
	
	result = {};
	SJ.extend(this, result);
};