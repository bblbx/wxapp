<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>昆仑新奥</title>
    <link rel="stylesheet" href="../statics/wx/css/weui.min.css?version=1.0"/> 
    <link rel="stylesheet" href="../statics/wx/css/other.css"/> 
    <style>
    	.weui_tab_bd_item{height: 100%;overflow: auto;display:none;}
		.weui_tab_bd_item.weui_tab_bd_item_active{display:block;}
    </style>
</head>
<body>
	<div class="header">
		<div class="left"><a href="../servlet/webhome?requestpage=other&openid=${openid}"><img src="../statics/img/back.png"></a></div>
		<div class="center position"><b>油品销量汇总</b></div>
		<div class="right"><a href="../servlet/webhome?requestpage=other&openid=${openid }"><img src="../statics/img/home.png"></a></div>
	</div>
	<div class="page" style="margin-top: 55px;">
	    <div class="page__bd" style="height: 100%;">
	    	<div class="weui-tab">
	            <div class="weui-navbar">
	                <div class="weui-navbar__item weui-bar__item_on">按日期</div>
	                <div class="weui-navbar__item">按站点</div>
	            </div>
	            <div class="weui-tab__panel">
	           		<div class="weui_tab_bd_item weui_tab_bd_item_active">
	           			<div class="weui-cell">
				        	<div class="weui-cell__hd"><label for="" class="weui-label">查询日期</label></div>
				            <div class="weui-cell__bd">
				            	<input class="weui-input" type="date" id="oilsalesreportsearchdate" value="${financedate}" onchange="javascript:selectDate();"/>
				            </div>
				        </div>
				     	<!-- <div class="weui-cell"> -->
				        	<div id="oilsalesbydate" style="width: 600px;height:400px;"></div>
				      	<!-- </div> -->
	             	</div>
	             	<div class="weui_tab_bd_item">
	           			<div class="weui-cell">
				        	<div class="weui-cell__hd"><label class="weui-label">查询站点:</label></div>
				           	<div class="weui-cell__bd">
				           		<input type="hidden" name="stationid" id="stationid">
				           		<input class="weui-input" type="text" placeholder="请选择站点" readOnly="readOnly" id="stationname">
				            </div>
				            <div class="icon-box">
				            	<i class="weui-icon-search" onclick="javascript:$('#OilReportStationSearchDialog').fadeIn(10);"></i>
				        	</div>
				        </div>
				        <!-- <div class="weui-cell"> -->
				        	<div id="oilsalesbystation" style="width: 600px;height:400px;"></div>
				      	<!-- </div> -->
				      	<div class="weui-cell">
				        	<div id="oilsalesbystationbaselinediv" style="width: 600px;height:100px;"></div>
				      	</div>
	             	</div>
	            </div>
	        </div>
	    </div>
	</div>
	
	<!-- 站点查询对话框 -->
    <div class="js_dialog" id="OilReportStationSearchDialog" style="display: none;z-index:100;margin-top10px;height:100px;width:100%">
		<div class="weui-mask"></div>
        <div class="weui-dialog" >
       	 	<div class="weui-cells__title">站点列表</div>
       	 	<div class="weui-cells weui-cells_radio" style="overflow-y:auto; overflow-x:auto; height:300px;">
       	 		<c:forEach var="station"  items="${stationlist}">
					<label class="weui-cell weui-check__label" for= "oilreportstation${station['StationID']}">
		                <div class="weui-cell__hd">
		                	<p>${station['Name']}</p>
		                </div>
		                <div class="weui-cell__ft">
		                    <input type="radio" class="weui-check" name="oilreportstation" id="oilreportstation${station['StationID']}" value="${station['Name']}" onclick ="javascript:selectStation();"/>
		                    <span class="weui-icon-checked"></span>
		                </div>
		            </label>
		        </c:forEach>
	        </div>
	        <div class="weui-dialog__ft">
                <a href="javascript:$('#OilReportStationSearchDialog').fadeOut(10);" class="weui-dialog__btn weui-dialog__btn_primary">取消</a>
            </div>
        </div>
	</div>
	
	<div id="loadingToast" style="display:none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <i class="weui-loading weui-icon_toast"></i>
            <p class="weui-toast__content">加载中</p>
        </div>
    </div>
	
	<script type="text/javascript" src="../statics/js/jquery-3.2.1.min.js"></script>
    <script src="http://cdn.highcharts.com.cn/highcharts/highcharts.js"></script>
    <script> 
     	//此方法用于navbar切换
	    $(function(){
	        $('.weui-navbar__item').on('click', function () {
	            $(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
	            var index=$(this).index();
	            $(".weui-tab__panel .weui_tab_bd_item").eq(index).addClass("weui_tab_bd_item_active").siblings().removeClass("weui_tab_bd_item_active");
	        });
	    });
     	
	    function selectDate(){
    		var financedate = $('#oilsalesreportsearchdate').val();
    		$(location).attr('href', '../servlet/otherweb?requestpage=OilSalesReport&openid=${openid}&searchtype=Date&financedate='+financedate);
		}
	    
	    function baselineChange(chart, baselineValue){
	    	chart.yAxis[0].removePlotLine('plot-line-oilbaseline'); 
	    	chart.yAxis[0].addPlotLine({
	  	        dashStyle:'solid',
	  	        value:parseFloat(baselineValue),
	  	        width:2,
	  			color: 'red',
	  			id: 'plot-line-oilbaseline'
	    	});
    	}
	    
	    var chart = null;
	    function selectStation(){
	    	var radios = document.getElementsByName('oilreportstation');
    	  	for(var i=0;i<radios.length;i++){
    	        if(radios[i].checked){
    	        	$('#stationid').val(radios[i].id);
    		    	$('#stationname').val(radios[i].value);
    	            break;
    	        }
    	    }
    	  	var stationid = $('#stationid').val().replace(/oilreportstation/g, "");
    	  	var stationname = $('#stationname').val();
    	  	$('#OilReportStationSearchDialog').fadeOut(10);
    	  	
    	  	$('#loadingToast').fadeIn(10);
			var postData = {};
	        postData.stationid = stationid;
	        postData.openid = "${openid}";
	        $.ajax({
	            type: 'POST',
	            url: "../report/queryOilSalesByStationId",
	            data: {jsonString : JSON.stringify(postData)},
	            success: function (serverData) {
	            	$('#loadingToast').fadeOut(10);
	            	if (serverData.success=='true') {
	            		var financeDates = serverData.msg.financeDates;
	            		var salesAmount92 = serverData.msg.salesAmount92;
	            		var salesAmount95 = serverData.msg.salesAmount95;
	            		var salesAmount0 = serverData.msg.salesAmount0;
	            		var salesAmount_10 = serverData.msg.salesAmount_10;
	            		var salesAmountTotal = serverData.msg.salesAmountTotal;
	            		
	            		var salesAmount92StrArray = salesAmount92.split(',');
	            		var salesAmount95StrArray = salesAmount95.split(',');
	            		var salesAmount0StrArray = salesAmount0.split(',');
	            		var salesAmount_10StrArray = salesAmount_10.split(',');
	            		var salesAmountTotalStrArray = salesAmountTotal.split(',');
	            		
	            		var salesAmount92FloatArray = [];
	            		var salesAmount95FloatArray = [];
	            		var salesAmount0FloatArray = [];
	            		var salesAmount_10FloatArray = [];
	            		var salesAmountTotalFloatArray = [];
	            		
	            		for(var k=0;k<salesAmount92StrArray.length;k++){
	            			var sheng = parseFloat(salesAmount92StrArray[k].split(':')[0]);
	            			var dun = parseFloat(salesAmount92StrArray[k].split(':')[1]);
	            			salesAmount92FloatArray.push({y:sheng,ext:dun});
	            		}
	            		for(var k=0;k<salesAmount95StrArray.length;k++){
	            			var sheng = parseFloat(salesAmount95StrArray[k].split(':')[0]);
	            			var dun = parseFloat(salesAmount95StrArray[k].split(':')[1]);
	            			salesAmount95FloatArray.push({y:sheng,ext:dun});
	            		}
	            		for(var k=0;k<salesAmount0StrArray.length;k++){
	            			var sheng = parseFloat(salesAmount0StrArray[k].split(':')[0]);
	            			var dun = parseFloat(salesAmount0StrArray[k].split(':')[1]);
	            			salesAmount0FloatArray.push({y:sheng,ext:dun});
	            		}
	            		for(var k=0;k<salesAmount_10StrArray.length;k++){
	            			var sheng = parseFloat(salesAmount_10StrArray[k].split(':')[0]);
	            			var dun = parseFloat(salesAmount_10StrArray[k].split(':')[1]);
	            			salesAmount_10FloatArray.push({y:sheng,ext:dun});
	            		}
	            		for(var k=0;k<salesAmountTotalStrArray.length;k++){
	            			var sheng = parseFloat(salesAmountTotalStrArray[k].split(':')[0]);
	            			var dun = parseFloat(salesAmountTotalStrArray[k].split(':')[1]);
	            			salesAmountTotalFloatArray.push({y:sheng,ext:dun});
	            		}
	            		chart = Highcharts.chart('oilsalesbystation', {
	        				chart: {
	        					type: 'line'
	        				},
	        				title: {
	        					text: '油品销量'
	        				},
	        				subtitle: {
	        					text: '日期：'+serverData.msg.beginDate+'-'+serverData.msg.endDate
	        				},
	        				xAxis: {
	        					categories: financeDates.split(',')
	        				},
	        				yAxis: {
	        					title: {
	        						text: '销量 (升)'
	        					}
	        				},
	        	        	tooltip: {
	        	        		// head + 每个 point + footer 拼接成完整的 table
	        	        		headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	        	        		pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td></tr>' +
	        	        		'<tr><td style="color:{series.color};padding:0">升: </td>' +
	        	        		'<td style="color:{series.color};padding:0"><b>{point.y:.1f} </b></td>'+
	        	        		'<td style="color:{series.color};color:{series.color};padding:0">吨: </td>' +
	        	        		'<td style="color:{series.color};padding:0"><b>{point.ext:.1f} </b></td></tr>',
	        	        		footerFormat: '</table>',
	        	        		shared: true,
	        	        		useHTML: true
	        	        	},
	        				plotOptions: {
	        					line: {
	        						dataLabels: {
	        							// 开启数据标签
	        							enabled: true          
	        						},
	        						// 开启鼠标跟踪，对应的提示框、点击事件会生效
	        						enableMouseTracking: true
	        					}
	        				},
	        				series: [{
	        	        		name: '92#',
	        	        		data: salesAmount92FloatArray
	        	        	}, {
	        	        		name: '95#',
	        	        		data: salesAmount95FloatArray
	        	        	}, {
	        	        		name: '0#',
	        	        		data: salesAmount0FloatArray
	        	        	}, {
	        	        		name: '-10#',
	        	        		data: salesAmount_10FloatArray
	        	        	}, {
	        	        		name: '合计',
	        	        		data: salesAmountTotalFloatArray
	        	        	}]
	        			});
	            		var baselinehtml = 	'<div class="weui-cell" >';
	        			baselinehtml += '<div class="weui-cell__hd"><label class="weui-label">基线值:</label></div>';
	        			baselinehtml += '<div class="weui-cell__bd">';
	        			baselinehtml += '<input class="weui-input" id="lpgsalesbystationbaseline" type="number" pattern="[0-9]*" value="0" placeholder="请输入数量" onchange="javascript:baselineChange(chart, this.value);"/>';
	        			baselinehtml += '</div>';
	        			baselinehtml += '</div>';
	        			$('#oilsalesbystationbaselinediv').append(baselinehtml);
	            	} else {
	            	  	$('#oilsalesbystation').html("");
	            		$('#oilsalesbystationbaselinediv').html("");
	            	}
	            },
	            error:function(a){
	            	$('#loadingToast').fadeOut(10);
	            },
	            dataType: "json"
	        });
		}
	    
	    var searchType= '${searchtype}';
		if(searchType=='Date'){
			var financeDate= '${financedate}';
			// 图表初始化函数
	        chart = Highcharts.chart('oilsalesbydate', {
	        	chart: {
	        		type: 'column'
	        	},
	        	title: {
	        		text: '油品销量'
	        	},
	        	subtitle: {
	        		text: '日期：'+financeDate
	        	},
	        	xAxis: {
	        		categories: [
						${stationnames}
	        		],
	        		crosshair: true
	        	},
	        	yAxis: {
	        		min: 0,
	        		title: {
	        			text: '销量 (升)'
	        		}
	        	},
	        	legend: {
	        		shadow: false
	        	},
	        	tooltip: {
	        		// head + 每个 point + footer 拼接成完整的 table
	        		headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	        		pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	        		'<td style="padding:0"><b>升:{point.y:.1f} </b></td>'+
	        		'<td style="padding:0"><b>吨:{point.ext:.1f} </b></td></tr>',
	        		footerFormat: '</table>',
	        		shared: true,
	        		useHTML: true
	        	},
	        	plotOptions: {
	        		column: {
	        			grouping: false,
	        			shadow: false,
	        			borderWidth: 0
	        		}
	        	},
	        	series: [{
	        		name: '合计',
	        		data: [${salesamounttotal}],
	        		pointPadding: -0.2
	        	}, {
	        		name: '92#',
	        		data: [${salesamount92}],
	        		pointPadding: 0.4,
	        		pointPlacement: -0.3
	        	}, {
	        		name: '95#',
	        		data: [${salesamount95}],
	        		pointPadding: 0.4,
	        		pointPlacement: -0.1
	        	}, {
	        		name: '0#',
	        		data: [${salesamount0}],
	        		pointPadding: 0.4,
	        		pointPlacement: 0.1
	        	}, {
	        		name: '-10#',
	        		data: [${salesamount_10}],
	        		pointPadding: 0.4,
	        		pointPlacement: 0.3
	        	}]
	        });
		}
	</script>
</body>
</html>