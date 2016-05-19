(function(_window){
	var ReportForms = function(){
		var options, maxs = 100;
		var extend = function(args1, args2){
			for(var key in args2){ args1[key] = args2[key]; }
			return args1;
		};
		this.Create = function(args){
			options = {
				chart: { renderTo: 'container', plotBorderColor: "#C0C0C0"},
				credits: { enabled: false },
				title: {
					align: 'left',
					text: '',
					floating: true,
					x: 50,
					y: 25,
					style: { color: '#000000' }
				},
				subtitle: {
					align: 'right',
					text: '',
					floating: true,
					x: -40,
					y: 25,
					style: { color: '#000000' }
				},
				xAxis:[{
					tickColor:'#dddcd5',
					categories: [],
					labels: { style: {color:'#020202',fontWeight:'bold'}}
				}],
				yAxis: [
					{
						gridLineColor:'#cdcdcd',
						max: maxs,
						title: { text: '' },
						labels: {
							formatter: function() { return this.value +'%'; },
							style: { color: '#000000' }
						}
					},
					{
						min: 0,
						title: { text: '' },
						labels: {
							formatter: function() { return this.value +''; },
							style: { color: '#000000' }
						},
						opposite: false
					}
				],
				tooltip: {
					enabled: true,
					formatter: function() {
						return this.y;
					}
				},
				plotOptions: {
					column: {
						dataLabels: {
							enabled: true,
							color: '#000000',
							formatter: function() { return this.y; }
						}
					},
					 line: {
		                    dataLabels: {
		                        enabled: true,
		                        x:30,
		                        color:"#579c48"
		                    },
		                    enableMouseTracking: false
		                }
				},
				legend: { enabled: false },
				series: [
					{
						name: 'Temperature',
						type: 'column',
						data: [
							{ color: '#217E11', y: 56 },
							{ color: '#0575E8', y: 18 },
							{ color: '#FF9900', y: 15 },
							{ color: '#8E1EC9', y: 6 },
							{ color: '#D41405', y: 4 },
							{ color: '#F0F0F0', y: 8 }
						]
					},
					{
						name: 'Rainfall',
						type: 'line',
						color: '#959595',
						yAxis: 1,
						data: [0.9, 1.2, 1.0, 1.8, 1.6, 1.2]
					}
				]
			};
			extend(options.chart, args.chart);
			extend(options.title, args.title);
			extend(options.tooltip, args.tooltip);
			extend(options.subtitle, args.subtitle);
			extend(options.xAxis, args.xAxis);
			if(args.yAxis){ extend(options.yAxis[0].labels, args.yAxis[0].labels); }
			var oColumn = options.plotOptions.column;
			var aColumn = args.plotOptions.column;
			if(aColumn.dataLabels){ extend(oColumn.dataLabels, aColumn.dataLabels); }
			if(aColumn.groupPadding){ oColumn.groupPadding = aColumn.groupPadding; }
			if(aColumn.pointWidth){ oColumn.pointWidth = aColumn.pointWidth; }
			options.series = args.series;
			maxs = options.max;
			
			new Highcharts.Chart(options);
		};		
	};
	_window.ReportForms = ReportForms;
})(window);