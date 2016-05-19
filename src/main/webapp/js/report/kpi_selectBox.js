function kpi_choose(wcdmalist,gsmlist,ltelist,dd, dd_id ,dd_id_1,dd_id_2, child){

	if(dd_id.length<5){
						for (i in wcdmalist) {
							if ($.inArray(1, dd_id) < 0
									&& wcdmalist[i].rscp > 0) {
								dd_id.push(1);
								dd.push({
									"text" : 'RSCP',
									"id" : '1',
									"selected" : true
								});
								child.push({
									id : 4,
									text : "RSCP"
								});

							}
							if ($.inArray(2, dd_id) < 0
									&& wcdmalist[i].ecno > 0) {
								dd_id.push(2);
								dd.push({
									"text" : 'EcNo',
									"id" : '2'
								});
								child.push({
									id : 5,
									text : "EcNo"
								});
							}
							if ($.inArray(3, dd_id) < 0
									&& wcdmalist[i].txpower > 0) {
								dd_id.push(3);
								dd.push({
									"text" : 'TXPOWER',
									"id" : '3'
								});
								child.push({
									id : 6,
									text : "TXPOWER"
								});
							}
							if ($.inArray(4, dd_id) < 0
									&& wcdmalist[i].ftpSpeed > 0) {
								dd_id.push(4);
								dd.push({
									"text" : 'FTP3G',
									"id" : '4'
								});
								child.push({
									id : 7,
									text : "FTP3G"
								});
							}
							if ($.inArray(20, dd_id) < 0
									&& wcdmalist[i].psc > 0) {
								dd_id.push(20);
								dd.push({
									"text" : 'PSC',
									"id" : '20'
								});
								child.push({
									id : 23,
									text : "PSC"
								});
							}
						}
						}
						if ($("#s_sel2").css("display") == 'none'
								&& dd_id.length > 0) {
							$("#s_sel2").show();
							zNodes.push({
								'id' : 2,
								'text' : "3G",
								'children' : child
							});
							$("#SelectBox2").combobox({
								data : dd,
								valueField : 'id',
								textField : 'text'
							});
						} else {
							$("#SelectBox2").combobox("clear");
						}

						 dd = [],  child = [];
						// 2g下拉

							if(dd_id_1.length<4){
						for (i in gsmlist) {
							if ($.inArray(6, dd_id_1) < 0
									&& gsmlist[i].rxlev > 0) {
								dd_id_1.push(6);
								dd.push({
									"text" : 'RXLEV',
									"id" : '6',
									"selected" : true
								});
								child.push({
									'id' : 9,
									'text' : "RXLEV"
								});
							}
							if ($.inArray(7, dd_id_1) < 0
									&& gsmlist[i].rxqual > 0) {
								dd_id_1.push(7);
								dd.push({
									"text" : 'RXQUAL',
									"id" : '7'
								});
								child.push({
									'id' : 10,
									pId : 1,
									'text' : "RXQUAL"
								});
							}
							if ($.inArray(8, dd_id_1) < 0
									&& gsmlist[i].rxqual > 0) {
								dd_id_1.push(8);
								dd.push({
									"text" : 'C/I',
									"id" : '8'
								});
								child.push({
									'id' : 11,
									'text' : "C/I"
								});
							}
							if ($.inArray(21, dd_id_1) < 0
									&& gsmlist[i].bcch > 0) {
								dd_id_1.push(21);
								dd.push({
									"text" : 'BCCH',
									"id" : '21'
								});
								child.push({
									'id' : 24,
									pId : 1,
									'text' : "BCCH"
								});
							}
						}
						}
						if ($("#s_sel1").css("display") == 'none'
								&& dd_id_1.length > 0) {
							$("#s_sel1").show();
							zNodes.push({
								'id' : 1,
								'text' : "2G",
								'children' : child
							});
							$("#SelectBox1").combobox({
								data : dd,
								valueField : 'id',
								textField : 'text'
							});
						} else {
							$("#SelectBox1").combobox("clear");
						}
						dd = [],  child = [];
						// 4g下拉
							if(dd_id_2.length<5){
						for (i in ltelist) {
							if ($.inArray(23, dd_id_2) < 0
									&& ltelist[i].rsrp > 0) {
								dd_id_2.push(23);
								dd.push({
									"text" : 'RSRP',
									"id" : '23',
									"selected" : true
								});
								child.push({
									'id' : 26,
									'text' : "RSRP"
								});
							}
							if ($.inArray(24, dd_id_2) < 0
									&& ltelist[i].rsrq > 0) {
								dd_id_2.push(24);
								dd.push({
									"text" : 'RSRQ',
									"id" : '24'
								});
								child.push({
									'id' : 27,
									pId : 3,
									'text' : "RSRQ"
								});
							}
							if ($.inArray(25, dd_id_2) < 0
									&& ltelist[i].snr > 0) {
								dd_id_2.push(25);
								dd.push({
									"text" : 'SINR',
									"id" : '25'
								});
								child.push({
									'id' : 28,
									'text' : "SINR"
								});
							}
							if ($.inArray(71, dd_id_2) < 0
									&& ltelist[i].ftpSpeed > 0) {
								dd_id_2.push(71);
								dd.push({
									"text" : 'FTP4G',
									"id" : '71'
								});
								child.push({
									id : 74,
									text : "FTP4G"
								});
							}
							if ($.inArray(26, dd_id_2) < 0
									&& ltelist[i].pci > 0) {
								dd_id_2.push(26);
								dd.push({
									"text" : 'PCI',
									"id" : '26'
								});
								child.push({
									'id' : 29,
									 pId : 3,
									'text' : "PCI"
								});
							}
						}
						}
						if ($("#s_sel3").css("display") == 'none'
								&& dd_id_2.length > 0) {
							$("#s_sel3").show();
							zNodes.push({
								'id' : 3,
								'text' : "4G",
								'children' : child
							});
							$("#SelectBox3").combobox({
								data : dd,
								valueField : 'id',
								textField : 'text'
							});
						} else {
							$("#SelectBox3").combobox("clear");
						}
						
}





/**
 * select的change事件
 */
function select_change(dd_id,dd_id_1,dd_id_2,flowid,flist,wcdmalist,
		gsmlist,ltelist,idooorTag,aaf){
	var sel1, sel2,sel3;
	if (dd_id_1.length > 0) 
		sel1 = $("#SelectBox1").combobox("getValue");
	if (dd_id.length > 0) 
		sel2 = $("#SelectBox2").combobox("getValue");
	if (dd_id_2.length > 0) 
		sel3 = $("#SelectBox3").combobox("getValue");
	var temp = "";
	var str="";
	if (sel1&&dd_id_1.length > 0) {
			str = $("#SelectBox1").combobox("getText");
			temp = str+ ","+ temp;
	}
	if (sel2&&dd_id.length > 0) {
			str = $("#SelectBox2").combobox("getText");
			temp = str+ ","+ temp;
	}
	if (sel3&&dd_id_2.length > 0) {
		str = $("#SelectBox3").combobox("getText");
		temp = str+ ","+ temp;
}
	if ($("#demo_div").html()) {
		choose_demo(
				flowid,
				flist,$(
						"#SelectBox1")
						.combobox(
								"getValue"),
				$(
						"#SelectBox2")
						.combobox(
								"getValue"),$(
								"#SelectBox3")
								.combobox(
										"getValue"),
				$(
						"#SelectBox1")
						.combobox(
								"getText"),
				$(
						"#SelectBox2")
						.combobox(
								"getText"),$(
								"#SelectBox3")
								.combobox(
										"getText"),
				"#demo_div",
				wcdmalist,
				gsmlist,ltelist);
	}
	
	var vs = new Array();
	var vs1 = new Array();
	vs = temp.split(",");
	for (var x=0;x<vs.length;x++){
		if(vs[x].length>0&&$.inArray(vs[x], idooorTag)<0){
			idooorTag.push(vs[x]);
			vs1.push(vs[x]);
		}
	}
	if(vs1.length>0){
		var obj=getindoorName(aaf.dfobj,vs1);
		initData(obj,vs1,aaf.firstId,aaf.firstType,aaf.lastId,aaf.lastType,aaf.wl.wid,aaf.wl.hie,idooorTag);
	}
	display(vs);
}