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
		<div class="center position"><b>LPG销量</b></div>
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
				            	<input class="weui-input" type="date" id="lpgsalesreportsearchdate" value="${financedate}" onchange="javascript:selectDate();"/>
				            </div>
				        </div>
				     	<!-- <div class="weui-cell"> -->
				        	<div id="lpgsalesbydate" style="width: 600px;height:400px;"></div>
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
				            	<i class="weui-icon-search" onclick="javascript:$('#LPGReportStationSearchDialog').fadeIn(10);"></i>
				        	</div>
				        </div>
				        <!-- <div class="weui-cell"> -->
				        	<div id="lpgsalesbystationdiv" style="width: 600px;height:400px;"></div>
				      	<!-- </div> -->
				      	<div class="weui-cell">
				        	<div id="lpgsalesbystationbaselinediv" style="width: 600px;height:100px;"></div>
				      	</div>
	             	</div>
	            </div>
	        </div>
	    </div>
	</div>
	
	<!-- 站点查询对话框 -->
    <div class="js_dialog" id="LPGReportStationSearchDialog" style="display: none;z-index:100;margin-top10px;height:100px;width:100%">
		<div class="weui-mask"></div>
        <div class="weui-dialog" >
       	 	<div class="weui-cells__title">站点列表</div>
       	 	<div class="weui-cells weui-cells_radio" style="overflow-y:auto; overflow-x:auto; height:300px;">
       	 		<c:forEach var="station"  items="${stationlist}">
					<label class="weui-cell weui-check__label" for= "lpgreportstation${station['StationID']}">
		                <div class="weui-cell__hd">
		                	<p>${station['Name']}</p>
		                </div>
		                <div class="weui-cell__ft">
		                    <input type="radio" class="weui-check" name="lpgreportstation" id="lpgreportstation${station['StationID']}" value="${station['Name']}" onclick ="javascript:selectStation();"/>
		                    <span class="weui-icon-checked"></span>
		                </div>
		            </label>
		        </c:forEach>
	        </div>
	        <div class="weui-dialog__ft">
                <a href="javascript:$('#LPGReportStationSearchDialog').fadeOut(10);" class="weui-dialog__btn weui-dialog__btn_primary">取消</a>
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
    		var financedate = $('#lpgsalesreportsearchdate').val();
    		$(location).attr('href', '../servlet/otherweb?requestpage=LPGSalesReport&openid=${openid}&searchtype=Date&financedate='+financedate);
		}
	    
	    function baselineChange(chart, baselineValue){
	    	chart.yAxis[0].removePlotLine('plot-line-lpgbaseline'); 
	    	chart.yAxis[0].addPlotLine({
	  	        dashStyle:'solid',
	  	        value:parseFloat(baselineValue),
	  	        width:2,
	  			color: 'red',
	  			id: 'plot-line-lpgbaseline'
	    	});
    	}
	    
		var chart = null;
	    function selectStation(){
	    	var radios = document.getElementsByName('lpgreportstation');
    	  	for(var i=0;i<radios.length;i++){
    	        if(radios[i].checked){
    	        	$('#stationid').val(radios[i].id);
    		    	$('#stationname').val(radios[i].value);
    	            break;
    	        }
    	    }
    	  	var stationid = $('#stationid').val().replace(/lpgreportstation/g, "");
    	  	var stationname = $('#stationname').val();
    	  	$('#LPGReportStationSearchDialog').fadeOut(10);
    	  	
    	  	$('#loadingToast').fadeIn(10);
			var postData = {};
	        postData.stationid = stationid;
	        postData.openid = "${openid }";
	        $.ajax({
	            type: 'POST',
	            url: "../report/queryLPGSalesByStationId",
	            data: {jsonString : JSON.stringify(postData)},
	            success: function (serverData) {
	            	$('#loadingToast').fadeOut(10);
	            	if (serverData.success=='true') {
	            		var financeDates = serverData.msg.financeDates;
	            		var salesAmount = serverData.msg.salesAmount;
	            		var salesAmountStrArray = salesAmount.split(',');
	            		var salesAmountFloatArray = [];
	            		for(var k=0;k<salesAmountStrArray.length;k++){
	            			var sheng = parseFloat(salesAmountStrArray[k].split(':')[0]);
	            			var dun = parseFloat(salesAmountStrArray[k].split(':')[1]);
	            			salesAmountFloatArray.push({y:sheng,ext:dun});
	            		}
	            		chart = Highcharts.chart('lpgsalesbystationdiv', {
	        				chart: {
	        					type: 'line'
	        				},
	        				title: {
	        					text: 'LPG销量'
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
	        	        		pointFormat: '<tr><td style="color:{series.color};padding:0">升: </td>' +
	        	        		'<td style="padding:0"><b>{point.y:.2f} </b></td></tr>'+
	        	        		'<tr><td style="color:{series.color};padding:0">吨: </td>' +
	        	        		'<td style="padding:0"><b>{point.ext:.2f} </b></td></tr>',
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
	        					name: 'LPG',
	        					data: salesAmountFloatArray
	        				}]
	        			});
	        			var baselinehtml = 	'<div class="weui-cell" >';
	        			baselinehtml += '<div class="weui-cell__hd"><label class="weui-label">基线值:</label></div>';
	        			baselinehtml += '<div class="weui-cell__bd">';
	        			baselinehtml += '<input class="weui-input" id="lpgsalesbystationbaseline" type="number" pattern="[0-9]*" value="0" placeholder="请输入数量" onchange="javascript:baselineChange(chart, this.value);"/>';
	        			baselinehtml += '</div>';
	        			baselinehtml += '</div>';
	        			$('#lpgsalesbystationbaselinediv').append(baselinehtml);
	            	} else {
	            	  	$('#lpgsalesbystationdiv').html("");
	            		$('#lpgsalesbystationbaselinediv').html("");
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
	        chart = Highcharts.chart('lpgsalesbydate', {
	        	chart: {
	        		type: 'column'
	        	},
	        	title: {
	        		text: 'LPG销量'
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
	        	tooltip: {
	        		// head + 每个 point + footer 拼接成完整的 table
	        		headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	        		pointFormat: '<tr><td style="color:{series.color};padding:0">升: </td>' +
	        		'<td style="padding:0"><b>{point.y:.2f} </b></td></tr>'+
	        		'<tr><td style="color:{series.color};padding:0">吨: </td>' +
	        		'<td style="padding:0"><b>{point.ext:.2f} </b></td></tr>',
	        		footerFormat: '</table>',
	        		shared: true,
	        		useHTML: true
	        	},
	        	plotOptions: {
	        		column: {
	        			borderWidth: 0
	        		}
	        	},
	        	series: [{
	        		name: 'LPG',
	        		data: [${salesamount}]
	        	}]
	        });
		}
	</script>
</body>
</html>