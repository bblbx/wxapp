<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>昆仑新奥</title>
    <link rel="stylesheet" href="../statics/wx/css/other.css?version=1.0"/>
    <link rel="stylesheet" href="../statics/wx/css/weui.min.css?version=1.0"/>
    <script type="text/javascript" src="../statics/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="../statics/js/common.js"></script>
    <link rel="stylesheet" href="../statics/css/common.css" type="text/css" />
    <script> 
	    var page=2;
	    var now=1;
		var winH = $(window).height(); //页面可视区域高度
		var hasall=false;
		 //滚动触发
	    $(window).scroll(function () {
			var pageH = $(document.body).height();
			var scrollT = $(window).scrollTop(); //滚动条top
			var aa = (pageH - winH - scrollT) / winH;
			//console.log("pageH:"+pageH+",winH:"+winH+",scrollT:"+scrollT+",aa:"+aa+",page:"+page+",now:"+now+""+",hasall:"+hasall);
			if (aa <= 0.01 && !hasall && page != now) {
				now = page;
				var postData = {};
				postData.page = page;
				postData.openid = "${openid}";
				postData.stationids = "${stationids}";
				postData.status = "${status}";
				postData.beginDate = "${beginDate}";
				postData.endDate = "${endDate}";
				
				loadingShow('viewrepairreqdiv');
				$.ajax({
					type : 'POST',
					url : "../repair/queryRepairRequest4View",
					data : postData,
					success : function(serverData) {
						if (serverData.success == 'true') {
							//alert(serverData.msg.length);
							if (serverData.msg.length > 0) {
								for (var i = 0; i < serverData.msg.length; i++) {
									var html = "<div class='weui-form-preview'>";
									html += "<div class='weui-form-preview__hd'>";
									html += "<div class='weui-form-preview__item'>";
									html += "<label class='weui-form-preview__label'>维修单号</label>";
									html += "<em class='weui-form-preview__value'>"+serverData.msg[i].OrderID+"</em>";
									html += "</div>";
									html += "</div>";
									html += "<div class='weui-form-preview__bd'>";
									html += "<div class='weui-form-preview__item'>";
									html += "<label class='weui-form-preview__label'>报修站点</label>";
									html += "<span class='weui-form-preview__value'>"+serverData.msg[i].StationName+"</span>";
									html += "</div>";
									html += "<div class='weui-form-preview__item'>";
									html += "<label class='weui-form-preview__label'>报修人</label>";
									html += "<span class='weui-form-preview__value'>"+serverData.msg[i].ContactPerson+"</span>";
									html += "</div>";
									html += "<div class='weui-form-preview__item'>";
									html += "<label class='weui-form-preview__label'>联系电话</label>";
									html += "<span class='weui-form-preview__value'>"+serverData.msg[i].ContactPhone+"</span>";
									html += "</div>";
									html += "<div class='weui-form-preview__item'>";
									html += "<label class='weui-form-preview__label'>报修时间</label>";
									html += "<span class='weui-form-preview__value'>"+serverData.msg[i].CreateTime+"</span>";
									html += "</div>";
									html += "<div class='weui-form-preview__item'>";
									html += "<label class='weui-form-preview__label'>工单状态</label>";
									html += "<span class='weui-form-preview__value'>"+serverData.msg[i].Status+"</span>";
									html += "</div>";
									html += "<div class='weui-form-preview__item'>";
									html += "<label class='weui-form-preview__label'>故障描述</label>";
									html += "<span class='weui-form-preview__value'>"+serverData.msg[i].FaultDescription+"</span>";
									html += "</div>";
									html += "</div>";
									html += "<div class='weui-form-preview__ft'>";
									html += "<a class='weui-form-preview__btn weui-form-preview__btn_primary' href='../servlet/otherweb?requestpage=repair/DoViewRepairRequest&openid=${openid}&repairorderid="+serverData.msg[i].OrderID+"');>详细信息</a>";
									html += "</div>";
									html += "</div>";
									html += "<br>";
									$("#viewrepairreqdiv").append(html);
								} 
								if (serverData.msg.length < 10) {
									hasall = true;//已经到底
									$("#end").show();
								}
								page++;
							} else {
								hasall = true;//已经到底
								$("#end").show();
							}
							loadingHide();
						} else {
							hasall = true;//已经到底
							$("#end").show();
							loadingHide();
						}
					},
					dataType : "json"
				});
			}
		});
		function selectViewRPStation(){
			var checkbox = document.getElementsByName("viewrpstationcheckbox");
			$('#ViewRPStationTextDiv').html("");
			$('#stationnames0').val('');
			$('#stationids').val('');
			
		    var station_value = [];
		    var count = 0;
		    for(k in checkbox){
		    	if(checkbox[k].checked){
		    		if(count==0){
		    			 $('#stationnames0').val(checkbox[k].value);
		    		} else {
		    			var input = document.createElement('input');  //创建input节点
			  		    input.setAttribute('type', 'text');  //定义类型是文本输入
			  		    input.setAttribute('class', 'weui-input');  //定义类型是文本输入
			  		    input.setAttribute('name', 'stationnames'+checkbox[k].id);  //定义类型是文本输入
			  		 	input.setAttribute('value', checkbox[k].value);  //值
			  			input.setAttribute('readOnly', 'readOnly');  //设置只读
			  		 	document.getElementById('ViewRPStationTextDiv').appendChild(input); //添加到form中显示
		    		}
		    		count = count+1;
		      		station_value.push(checkbox[k].id);
		        }
		    }
		    if(station_value==''){
		    	$('#RepairTipsDialogContent').html('请选择站点！');
				$('#RepairTipsDialog').fadeIn(10);
				return ;
		    }
		    $('#stationids').val(station_value);
		    $('#ViewRPStationSearchDialog').fadeOut(10);
		}
		function searchRepairRequest(){
			var stationids = $('#stationids').val();
			var status = $('#status').val();
			var beginDate = $('#beginDate').val();
			var endDate = $('#endDate').val();
			$(location).attr('href', '../servlet/otherweb?requestpage=ViewRepairRequest&openid=${openid}&stationids='+stationids+'&status='+status+'&beginDate='+beginDate+'&endDate='+endDate);
		};
	</script>
</head>
<body>
	<div class="header">
		<div class="left"><a href="../servlet/webhome?requestpage=other&openid=${openid}"><img src="../statics/img/back.png"></a></div>
		<div class="center position"><b>故障报修单</b></div>
		<div class="right"><a href="../servlet/webhome?requestpage=other&openid=${openid}"><img src="../statics/img/home.png"></a></div>
	</div>
	
	<div class="page__bd" style="margin-top: 55px;" id="viewrepairreqdiv">
		<div class="weui-cell">
        	<div class="weui-cell__bd">
            	<a href="javascript:$('#QueryConditionDialog').fadeIn(10);" class="weui-btn weui-btn_plain-primary" 
         			style="font-size: 12px;padding: 0;line-height:28px;width:100%">高级查询</a>
          	</div>
		</div>
		<c:forEach var="repairRequest"  items="${repairrequestlist}">
			<div class="weui-form-preview">
	            <div class="weui-form-preview__hd">
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">维修单号</label>
	                    <em class="weui-form-preview__value">${repairRequest['OrderID']}</em>
	                </div>
	            </div>
	            <div class="weui-form-preview__bd">
	             	<div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">报修站点</label>
	                    <span class="weui-form-preview__value">${repairRequest['StationName']}</span>
	                </div>
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">报修人</label>
	                    <span class="weui-form-preview__value">${repairRequest['ContactPerson']}</span>
	                </div>
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">联系电话</label>
	                    <span class="weui-form-preview__value">${repairRequest['ContactPhone']}</span>
	                </div>
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">报修时间</label>
	                    <span class="weui-form-preview__value">${repairRequest['CreateTime']}</span>
	                </div>
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">工单状态</label>
	                    <span class="weui-form-preview__value">${repairRequest['Status']}</span>
	                </div>
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">故障描述</label>
	                    <span class="weui-form-preview__value">${repairRequest['FaultDescription']}</span>
	                </div>
	            </div>
	            <div class="weui-form-preview__ft">
	                <a class="weui-form-preview__btn weui-form-preview__btn_primary" href="../servlet/otherweb?requestpage=repair/DoViewRepairRequest&openid=${openid}&repairorderid=${repairRequest['OrderID']}">详细信息</a>
	            </div>
	        </div>
	        <br>
        </c:forEach>
        <br>
    </div>
    <div style="text-align: center; color: #DDDDDD; display: none;" id="end">--已经到底了--</div>
    
    <!-- 查询条件对话框 -->
    <div class="js_dialog" id="QueryConditionDialog" style="display: none;z-index:100;margin-top10px;height:100px;width:100%">
		<div class="weui-mask"></div>
        <div class="weui-dialog" >
       	 	<div class="weui-cells__title">筛选信息</div>
       	 	<div class="weui-cell" >
       	 		<div class="weui-cell__hd"><label class="weui-form-preview__label">站点:</label></div>
	           	<div class="weui-cell__bd">
	          	 	<input type="hidden" name="stationids" id="stationids">
	           		<input class="weui-input" type="text" placeholder="请选择负责站点" readOnly="readOnly" id="stationnames0">
	                <div class="weui-textarea-counter"></div>
	            </div>
	            <div class="icon-box">
	            	<i class="weui-icon-search" onclick="javascript:$('#ViewRPStationSearchDialog').fadeIn(10);"></i>
	        	</div>
			</div>
			<div class="weui-cell">
	           	<div class="weui-cell__hd"><label class="weui-form-preview__label"></label></div>
	           	<div class="weui-cell__bd" id="ViewRPStationTextDiv">
	            </div>
	        </div>
	       	<div class="weui-cell" >
	          	<div class="weui-cell__hd"><label class="weui-form-preview__label">状态:</label></div>
	            <div class="weui-cell__bd">
	            	<select class="lb-select" style="width:100%;" name="status" id="status">
	            		<option value="">全部</option>
				 		<option value="YTJ">待维修</option>
				 		<option value="WXZ">维修中</option>
				 		<option value="DQR">待确认</option>
				 		<option value="WJ">已确认</option>
				 		<option value="ZF">已作废</option>
				 		<option value="BH">已驳回</option>
					</select>
	            </div>
	      	</div>
	       	<div class="weui-cell">
		        <label class="weui-form-preview__label" style="height:30px;text-align: left;">开始时间:</label>
		   	    <input type="date"  class="weui-input" style="height:30px;width:50%;background-color:#F2F2F2" id="beginDate" name="beginDate"  required />
		    </div>
		    <div class="weui-cell">
		        <label class="weui-form-preview__label" style="height:30px;text-align: left;">结束时间:</label>
		        <input type="date" class="weui-input" style="height:30px;width:50%;background-color:#F2F2F2" id="endDate" name="endDate"  required />
		    </div>
           <div class="weui-dialog__ft">
            	<a href="javascript:searchRepairRequest();" type="submit" class="weui-dialog__btn weui-dialog__btn_primary">确定</a>
                <a href="javascript:$('#QueryConditionDialog').fadeOut(10);" class="weui-dialog__btn weui-dialog__btn_default">取消</a>
            </div>
        </div>
	</div>
	
	<!-- 站点查询对话框 -->
    <div class="js_dialog" id="ViewRPStationSearchDialog" style="display: none;z-index:100;margin-top10px;height:100px;width:100%">
		<div class="weui-mask"></div>
        <div class="weui-dialog" >
       	 	<div class="weui-cells__title">站点列表</div>
       	 	<div class="weui-cells weui-cells_checkbox" style="overflow-y:auto; overflow-x:auto; height:300px;">
				<c:forEach var="station"  items="${stationlist}">
					<label class="weui-cell weui-check__label" for= "viewrpstation${station['StationID']}">
		                <div class="weui-cell__hd">
		                    <input type="checkbox" class="weui-check" name="viewrpstationcheckbox" id="viewrpstation${station['StationID']}" value="${station['Name']}"/>
		                    <i class="weui-icon-checked"></i>
		                </div>
		                <div class="weui-cell__bd">
		                    <p>${station['Name']}</p>
		                </div>
		            </label>
		        </c:forEach>
		    </div>
            <div class="weui-dialog__ft">
            	<a href="javascript:selectViewRPStation();" type="submit" class="weui-dialog__btn weui-dialog__btn_primary">确定</a>
                <a href="javascript:$('#ViewRPStationSearchDialog').fadeOut(10);" class="weui-dialog__btn weui-dialog__btn_default">取消</a>
            </div>
        </div>
	</div>
	
    <div class="js_dialog" id="RepairTipsDialog" style="display: none;">
        <div class="weui-mask"></div>
        <div class="weui-dialog">
            <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
            <div class="weui-dialog__bd" id="RepairTipsDialogContent">提示</div>
            <div class="weui-dialog__ft">
                <a href="javascript:$('#RepairTipsDialog').fadeOut(10);" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
            </div>
        </div>
    </div>
</body>
</html>