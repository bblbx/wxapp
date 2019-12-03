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
	    function selectRepairRequestStation(){
	    	var radios = document.getElementsByName('repairrequeststation');
    	  	for(var i=0;i<radios.length;i++){
    	        if(radios[i].checked){
    	        	$('#stationid').val(radios[i].id);
    		    	$('#stationname').val(radios[i].value);
    	            break;
    	        }
    	    } 
    	  	$('#loadingToast').fadeIn(10);
			var postData = {};
	        postData.stationid = $('#stationid').val();
	        postData.openid = "${openid}";
	        $.ajax({
	            type: 'POST',
	            url: "../repair/queryRepairmanByStation",
	            data: {jsonString : JSON.stringify(postData)},
	            success: function (serverData) {
	            	$('#loadingToast').fadeOut(10);
	            	$('#repairmanid').val(serverData.repairmanid);
	            	$('#repairmanname').val(serverData.repairmanname);
	            },
	            error:function(a){
	            	$('#loadingToast').fadeOut(10);
	            },
	            dataType: "json"
	        });
	        
    	  	$('#RepairRequestStationSearchDialog').fadeOut(10);
		}
	    
	    function selectRepairman(){
	    	var radios = document.getElementsByName('repairrequestrepairman');
    	  	for(var i=0;i<radios.length;i++){
    	        if(radios[i].checked){
    	        	$('#repairmanid').val(radios[i].id);
    		    	$('#repairmanname').val(radios[i].value);
    	            break;
    	        }
    	    } 
    	  	$('#RepairmanSearchDialog').fadeOut(10);
		}
	    
	    function saveRepairRequest(){
			var stationid = document.getElementById('stationid').value;
			if(stationid.length==0){
				$('#RepairRequestTipsDialogContent').html('请选择站点。');
				$('#RepairRequestTipsDialog').fadeIn(10);
				return ;
			}
			var faultdescription = document.getElementById('faultdescription').value;
			if(faultdescription.length==0){
				$('#RepairRequestTipsDialogContent').html('请填写故障描述。');
				$('#RepairRequestTipsDialog').fadeIn(10);
				return ;
			} 
			var contactperson = document.getElementById('contactperson').value;
			if(contactperson.length==0){
				$('#RepairRequestTipsDialogContent').html('请填写联系人姓名。');
				$('#RepairRequestTipsDialog').fadeIn(10);
				return ;
			} 
			var contactphone = document.getElementById('contactphone').value;
			if(contactphone.length==0){
				$('#RepairRequestTipsDialogContent').html('请输入联系人电话号码。');
				$('#RepairRequestTipsDialog').fadeIn(10);
				return ;
			} else {
				var reg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;
				var checkphone = reg.test(contactphone);
				if(!checkphone){
					$('#RepairRequestTipsDialogContent').html('电话号码格式不正确');
					$('#RepairRequestTipsDialog').fadeIn(10);
					return ;
				}
			}
			var repairmanid = document.getElementById('repairmanid').value;
			if(repairmanid.length==0){
				$('#RepairRequestTipsDialogContent').html('请选择维修人员。');
				$('#RepairRequestTipsDialog').fadeIn(10);
				return ;
			} 
			var remark = document.getElementById('remark').value;
			
			
			$('#loadingToast').fadeIn(10);
			var postData = {};
	        postData.stationid = stationid;
	        postData.faultdescription = faultdescription;
	        postData.contactperson = contactperson;
	        postData.contactphone = contactphone;
	        postData.repairmanid = repairmanid;
	        postData.remark = remark;
	        postData.openid = "${openid }";
	        $.ajax({
	            type: 'POST',
	            url: "../repair/submitRepairRequest",
	            data: {jsonString : JSON.stringify(postData)},
	            success: function (serverData) {
	            	$('#loadingToast').fadeOut(10);
        			$('#SaveRepairRequestTipsDialogContent').html(serverData.msg);
        			$('#SaveRepairRequestTipsDialog').fadeIn(10);
	            },
	            error:function(a){
	            	$('#loadingToast').fadeOut(10);
	            },
	            dataType: "json"
	        });
		}
	    function clearAddForm(){
			$('#stationid').val('');
			$('#faultdescription').val('');
			$('#contactperson').val('');
			$('#contactphone').val('');
			$('#remark').val('');
			$('#stationname').val('');
			$('#repairmanid').val('');
        	$('#repairmanname').val('');
			$('#SaveRepairRequestTipsDialog').fadeOut(10);
		}
	</script>
</head>
<body>
	<div class="header">
		<div class="left"><a href="../servlet/webhome?requestpage=other&openid=${openid}"><img src="../statics/img/back.png"></a></div>
		<div class="center position"><b>故障报修</b></div>
		<div class="right"><a href="../servlet/webhome?requestpage=other&openid=${openid }"><img src="../statics/img/home.png"></a></div>
	</div>
	<div class="weui-cells weui-cells_form" style="margin-top: 55px;">
		<div class="weui-cell">
        	<div class="weui-cell__hd"><label class="weui-label">报修站点:</label></div>
           	<div class="weui-cell__bd">
          	 	<input type="hidden" name="stationid" id="stationid">
           		<input class="weui-input" type="text" placeholder="请选择站点" readOnly="readOnly" id="stationname">
            </div>
            <div class="icon-box">
            	<i class="weui-icon-search" onclick="javascript:$('#RepairRequestStationSearchDialog').fadeIn(10);"></i>
        	</div>
        </div>
  		<div class="weui-cell">
       		<div class="weui-cell__hd"><label class="weui-label">故障描述:</label></div>
            <div class="weui-cell__bd">
            	<textarea class="weui-textarea" placeholder="请输入故障描述(50个汉字以内)" rows="2"  name="faultdescription" id="faultdescription"></textarea>
            </div>
       	</div>
      	<div class="weui-cell" >
          	<div class="weui-cell__hd"><label class="weui-label">报修人:</label></div>
            <div class="weui-cell__bd">
            	<input class="weui-input" type="text" placeholder="请输入报修人姓名" name="contactperson" id="contactperson">
            </div>
      	</div>
      	<div class="weui-cell" >
          	<div class="weui-cell__hd"><label class="weui-label">联系方式:</label></div>
            <div class="weui-cell__bd">
            	<input class="weui-input" type="text" placeholder="请输入手机号" name="contactphone" id="contactphone">
            </div>
      	</div>
      	<div class="weui-cell">
        	<div class="weui-cell__hd"><label class="weui-label">维修人员:</label></div>
           	<div class="weui-cell__bd">
          	 	<input type="hidden" name="repairmanid" id="repairmanid">
           		<input class="weui-input" type="text" readOnly="readOnly" id="repairmanname">
            </div>
            <div class="icon-box">
            	<i class="weui-icon-search" onclick="javascript:$('#RepairmanSearchDialog').fadeIn(10);"></i>
        	</div>
        </div>
      	<div class="weui-cell" >
          	<div class="weui-cell__hd"><label class="weui-label">备注:</label></div>
            <div class="weui-cell__bd">
            	<textarea class="weui-textarea" rows="2"  name="remark" id="remark"></textarea>
            </div>
      	</div>
    </div> 
    <div class="weui-btn-area">
		<div style="margin: 0 auto;padding: 15px 0;width: 95%;">
	  		<a href="javascript:saveRepairRequest();" class="weui-btn weui-btn_primary">提交</a>
	  	</div>
	</div>
	
	
	<!-- 站点查询对话框 -->
    <div class="js_dialog" id="RepairRequestStationSearchDialog" style="display: none;z-index:100;margin-top10px;height:100px;width:100%">
		<div class="weui-mask"></div>
        <div class="weui-dialog" >
       	 	<div class="weui-cells__title">站点列表</div>
       	 	<div class="weui-cells weui-cells_radio" style="overflow-y:auto; overflow-x:auto; height:300px;">
       	 		<c:forEach var="station"  items="${stationlist}">
					<label class="weui-cell weui-check__label" for= "repairrequeststation${station['StationID']}">
		                <div class="weui-cell__hd">
		                	<p>${station['Name']}</p>
		                </div>
		                <div class="weui-cell__ft">
		                    <input type="radio" class="weui-check" name="repairrequeststation" id="repairrequeststation${station['StationID']}" value="${station['Name']}" onclick ="javascript:selectRepairRequestStation();"/>
		                    <span class="weui-icon-checked"></span>
		                </div>
		            </label>
		        </c:forEach>
	        </div>
	        <div class="weui-dialog__ft">
	            <a href="javascript:$('#RepairRequestStationSearchDialog').fadeOut(10);" class="weui-dialog__btn weui-dialog__btn_primary">取消</a>
	        </div>
        </div>
	</div>
	
	<!-- 维修员查询对话框 -->
    <div class="js_dialog" id="RepairmanSearchDialog" style="display: none;z-index:100;margin-top10px;height:100px;width:100%">
		<div class="weui-mask"></div>
        <div class="weui-dialog" >
       	 	<div class="weui-cells__title">维修员列表</div>
       	 	<div class="weui-cells weui-cells_radio" style="overflow-y:auto; overflow-x:auto; height:300px;">
       	 		<c:forEach var="repairman"  items="${repairmanlist}">
					<label class="weui-cell weui-check__label" for= "repairrequestrepairman${repairman['ID']}">
		                <div class="weui-cell__hd">
		                	<p>${repairman['Name']}</p>
		                </div>
		                <div class="weui-cell__ft">
		                    <input type="radio" class="weui-check" name="repairrequestrepairman" id="repairrequestrepairman${repairman['ID']}" value="${repairman['Name']}" onclick ="javascript:selectRepairman();"/>
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
	
	<div class="js_dialog" id="RepairRequestTipsDialog" style="display: none;">
        <div class="weui-mask"></div>
        <div class="weui-dialog">
            <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
            <div class="weui-dialog__bd" id="RepairRequestTipsDialogContent">提示</div>
            <div class="weui-dialog__ft">
                <a href="javascript:$('#RepairRequestTipsDialog').fadeOut(10);" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
            </div>
        </div>
    </div>
    
	<div class="js_dialog" id="SaveRepairRequestTipsDialog" style="display: none;">
        <div class="weui-mask"></div>
        <div class="weui-dialog">
            <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
            <div class="weui-dialog__bd" id="SaveRepairRequestTipsDialogContent">提示</div>
            <div class="weui-dialog__ft">
                <a href="javascript:clearAddForm()" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
            </div>
        </div>
    </div>
    
    <div id="loadingToast" style="display:none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <i class="weui-loading weui-icon_toast"></i>
            <p class="weui-toast__content">提交中</p>
        </div>
    </div>
</body>
</html>