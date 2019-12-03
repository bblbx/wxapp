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
    <script> 
	    function repairArriveStation(repairRequest){
	    	repairRequest =repairRequest.replace(/=/g,'":"');
	    	repairRequest =repairRequest.replace(/, /g,'","');
	    	repairRequest =repairRequest.replace(/{/g,'{"');
	    	repairRequest =repairRequest.replace(/}/g,'"}');
	    	var repairRequestJson = $.parseJSON(repairRequest);
	 		
			$('#loadingToast').fadeIn(10);
	        repairRequestJson.openid = "${openid }";
	        $.ajax({
	            type: 'POST',
	            url: "../repair/arriveStation",
	            data: {jsonString : JSON.stringify(repairRequestJson)},
	            success: function (serverData) {
	            	$('#loadingToast').fadeOut(10);
        			$('#RepairArriveTipsDialogContent').html(serverData.msg);
        			$('#RepairArriveTipsDialog').fadeIn(10);
	            },
	            error:function(a){
	            	$('#loadingToast').fadeOut(10);
	            },
	            dataType: "json"
	        });
		}
	    function repairCheckArrive(repairorderid, arriveTime){
	    	if(arriveTime==''){
		    	$('#RepairTipsDialogContent').html('请先进行签到操作！');
				$('#RepairTipsDialog').fadeIn(10);
				return ;
		    }
	    	$(location).attr('href', '../servlet/otherweb?requestpage=repair/DoRepair&openid=${openid}&repairorderid='+repairorderid+'&type=XC');
		}
	    function selectDistributeRepairman(){
	    	var radios = document.getElementsByName('repairman');
		  	for(var i=0;i<radios.length;i++){
		        if(radios[i].checked){
		        	$('#hiddenrepairmanid').val(radios[i].id);
			    	$('#repairmanname').val(radios[i].value);
		            break;
		        }
		    } 
		  	$('#RepairmanSearchDialog').fadeOut(10);
			$('#RepairDistributeSearchDialog').fadeIn(10);
		}
	    function saveDistributeRepairman(){
	    	var repairmanid = $('#hiddenrepairmanid').val();
			var repairmanname = $('#repairmanname').val();
			var repairorderid = $('#hiddenrepairorderid').val();
			var remark = $('distributeremark').val();
			if(repairmanid==''){
		    	$('#RepairTipsDialogContent').html('请选择维修人员！');
				$('#RepairTipsDialog').fadeIn(10);
				return ;
		    }
			
			if(remark==''){
		    	$('#RepairTipsDialogContent').html('请输入委派原因！');
				$('#RepairTipsDialog').fadeIn(10);
				return ;
		    }
			
			$('#loadingToast').fadeIn(10);
	        var postData = {};
	        postData.repairmanid = repairmanid;
	        postData.repairmanname = repairmanname;
	        postData.orderid = repairorderid;
	        postData.remark = remark;
	        postData.openid = "${openid}";
	        $.ajax({
	            type: 'POST',
	            url: "../repair/distributeRepairman",
	            data: {jsonString : JSON.stringify(postData)},
	            success: function (serverData) {
	            	$('#loadingToast').fadeOut(10);
        			$('#RepairTipsRefreshDialogContent').html(serverData.msg);
        			$('#RepairTipsRefreshDialog').fadeIn(10);
        			$('#RepairDistributeSearchDialog').fadeOut(10);
	            },
	            error:function(a){
	            	$('#loadingToast').fadeOut(10);
	            },
	            dataType: "json"
	        });
		}
	    function repairRequstRefresh(){
	    	$('#RepairTipsRefreshDialog').fadeOut(10);
	    	$(location).attr('href', '../servlet/otherweb?requestpage=Repair&openid=${openid}');
		}
	    function repairRequstRefresh4Arrive(){
	    	$('#RepairArriveTipsDialog').fadeOut(10);
	    	$(location).attr('href', '../servlet/otherweb?requestpage=Repair&openid=${openid}');
		}
	</script>
</head>
<body>
	<div class="header">
		<div class="left"><a href="../servlet/webhome?requestpage=other&openid=${openid}"><img src="../statics/img/back.png"></a></div>
		<div class="center position"><b>故障报修单</b></div>
		<div class="right"><a href="../servlet/webhome?requestpage=other&openid=${openid }"><img src="../statics/img/home.png"></a></div>
	</div>
	
	<div class="page__bd" style="margin-top: 55px;">
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
	                <a class="weui-form-preview__btn weui-form-preview__btn_default" href="javascript:$('#RepairmanSearchDialog').fadeIn(10);$('#hiddenrepairorderid').val('${repairRequest['OrderID']}');">委派</a>
	                <a class="weui-form-preview__btn weui-form-preview__btn_primary" href="javascript:repairArriveStation('${repairRequest}');">到达</a>
	                <a class="weui-form-preview__btn weui-form-preview__btn_primary" href="../servlet/otherweb?requestpage=repair/DoRepair&openid=${openid}&repairorderid=${repairRequest['OrderID']}&type=YC">远程</a>
	                <a class="weui-form-preview__btn weui-form-preview__btn_primary" href="javascript:repairCheckArrive('${repairRequest['OrderID']}','${repairRequest['ArriveStationTime']}');">现场</a>
	            </div>
	        </div>
	        <br>
        </c:forEach>
        <br>
    </div>
	    
    <!-- 维修员查询对话框 -->
    <div class="js_dialog" id="RepairmanSearchDialog" style="display: none;z-index:100;margin-top10px;height:100px;width:100%">
		<div class="weui-mask"></div>
        <div class="weui-dialog" >
       	 	<div class="weui-cells__title">选择将要委派的人员</div>
       	 	<div class="weui-cells weui-cells_radio" style="overflow-y:auto; overflow-x:auto; height:300px;">
       	 		<c:forEach var="repairman"  items="${repairmanlist}">
					<label class="weui-cell weui-check__label" for= "repairman${repairman['ID']}">
		                <div class="weui-cell__hd">
		                	<p>${repairman['Name']}</p>
		                </div>
		                <div class="weui-cell__ft">
		                    <input type="radio" class="weui-check" name="repairman" id="repairman${repairman['ID']}" value="${repairman['Name']}" onclick ="javascript:selectDistributeRepairman();"/>
		                    <span class="weui-icon-checked"></span>
		                </div>
		            </label>
		        </c:forEach>
	        </div>
	        <div class="weui-dialog__ft">
                <a href="javascript:$('#RepairmanSearchDialog').fadeOut(10);" class="weui-dialog__btn weui-dialog__btn_primary">取消</a>
            </div>
        </div>
	</div>
    
    <!-- 维修人员查询对话框 -->
    <div class="js_dialog" id="RepairDistributeSearchDialog" style="display: none;z-index:100;margin-top10px;height:100px;width:100%">
		<div class="weui-mask"></div>
        <div class="weui-dialog" >
       	 	<div class="weui-cells__title">填写委派信息</div>
       	 	<div class="weui-cell" >
       	 		<div class="weui-cell__hd"><label class="weui-label">维修人员:</label></div>
	           	<div class="weui-cell__bd">
	          	 	<input type="hidden" name="hiddenrepairmanid" id="hiddenrepairmanid">
				    <input type="hidden" name="hiddenrepairorderid" id="hiddenrepairorderid">
	           		<input class="weui-input" type="text" readOnly="readOnly" id="repairmanname">
	            </div>
			</div>
			<div class="weui-cell">
	       		<div class="weui-cell__hd"><label class="weui-label">备注:</label></div>
	            <div class="weui-cell__bd">
	            	<textarea class="weui-textarea" placeholder="请输入委派原因" rows="2"  name="distributeremark" id="distributeremark"></textarea>
	            </div>
	       	</div>
            <div class="weui-dialog__ft">
            	<a href="javascript:saveDistributeRepairman();" type="submit" class="weui-dialog__btn weui-dialog__btn_primary">确定</a>
                <a href="javascript:$('#RepairDistributeSearchDialog').fadeOut(10);" class="weui-dialog__btn weui-dialog__btn_default">取消</a>
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
    
    <div class="js_dialog" id="RepairArriveTipsDialog" style="display: none;">
        <div class="weui-mask"></div>
        <div class="weui-dialog">
            <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
            <div class="weui-dialog__bd" id="RepairArriveTipsDialogContent">提示</div>
            <div class="weui-dialog__ft">
                <a href="javascript:repairRequstRefresh4Arrive();" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
            </div>
        </div>
    </div>
    
    <div class="js_dialog" id="RepairTipsRefreshDialog" style="display: none;">
        <div class="weui-mask"></div>
        <div class="weui-dialog">
            <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
            <div class="weui-dialog__bd" id="RepairTipsRefreshDialogContent">提示</div>
            <div class="weui-dialog__ft">
                <a href="javascript:repairRequstRefresh();" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
            </div>
        </div>
    </div>
    
	<div id="loadingToast" style="display:none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <i class="weui-loading weui-icon_toast"></i>
            <p class="weui-toast__content">保存中</p>
        </div>
    </div>
</body>
</html>