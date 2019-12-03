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
    	  	$('#RepairRequestStationSearchDialog').fadeOut(10); 
	     }
    	  	/*$('#loadingToast').fadeIn(10);
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
	        
    	  	
		} 
	    
	  /*   function selectRepairman(){
	    	var radios = document.getElementsByName('repairrequestrepairman');
    	  	for(var i=0;i<radios.length;i++){
    	        if(radios[i].checked){
    	        	$('#repairmanid').val(radios[i].id);
    		    	$('#repairmanname').val(radios[i].value);
    	            break;
    	        }
    	    } 
    	  	$('#RepairmanSearchDialog').fadeOut(10);
		} */
	    
	    function saveCallLiquidRequest(){
			var stationid = document.getElementById('stationid').value;
			if(stationid.length==0){
				$('#RepairRequestTipsDialogContent').html('请选择站点。');
				$('#RepairRequestTipsDialog').fadeIn(10);
				return ;
			}
			var liquidcount = document.getElementById('liquidcount').value;
			if(liquidcount.length==0){
				$('#RepairRequestTipsDialogContent').html('请填写叫液量。');
				$('#RepairRequestTipsDialog').fadeIn(10);
				return ;
			} 
			var liquidremain = document.getElementById('liquidremain').value;
			if(liquidremain.length==0){
				$('#RepairRequestTipsDialogContent').html('请填写剩余液位。');
				$('#RepairRequestTipsDialog').fadeIn(10);
				return ;
			} 
			var usetime = document.getElementById('usetime').value;
			if(usetime.length==0){
				$('#RepairRequestTipsDialogContent').html('请填写使用时间。');
				$('#RepairRequestTipsDialog').fadeIn(10);
				return ;
			} 
			
			var contactperson = document.getElementById('contactperson').value;
			if(contactperson.length==0){
				$('#RepairRequestTipsDialogContent').html('请填写联系人姓名。');
				$('#RepairRequestTipsDialog').fadeIn(10);
				return ;
			} 
			var contacttel = document.getElementById('contacttel').value;
			if(contacttel.length==0){
				$('#RepairRequestTipsDialogContent').html('请输入联系人电话号码。');
				$('#RepairRequestTipsDialog').fadeIn(10);
				return ;
			} else{
				contacttel=contacttel.replace(/[^0-9]*/g,'');
				if(contacttel.length!=11){
					$('#RepairRequestTipsDialogContent').html('请先检查输入的手机号。');
					$('#RepairRequestTipsDialog').fadeIn(10);
					return ;
				}
			}
			var expectedtime = document.getElementById('expectedtime').value;
			if(expectedtime.length==0){
				$('#RepairRequestTipsDialogContent').html('请填写期望到达时间。');
				$('#RepairRequestTipsDialog').fadeIn(10);
				return ;
			} 
			var latesttime = document.getElementById('latesttime').value;
			if(latesttime.length==0){
				$('#RepairRequestTipsDialogContent').html('请填写最晚到达时间。');
				$('#RepairRequestTipsDialog').fadeIn(10);
				return ;
			} 
			var message= document.getElementById('message').value;
			var remark = document.getElementById('remark').value;
			
			
			$('#loadingToast').fadeIn(10);
			var postData = {};
	        postData.stationid = stationid;
	        postData.liquidcount = liquidcount;
	        postData.liquidremain=liquidremain;
	        postData.usetime=usetime;
	        postData.contactperson = contactperson;
	        postData.contacttel = contacttel;
	        postData.expectedtime = expectedtime;
	        postData.latesttime = latesttime;
	        postData.message = message;
	        postData.remark = remark;
	        postData.type='JY';
	        postData.status='0';
	        postData.openid = "${openid }";
	        $.ajax({
	            type: 'POST',
	            url: "../callliquid/saveCallLiquid",
	            data: {jsonString : JSON.stringify(postData)},
	            success: function (serverData) {
	            	$('#loadingToast').fadeOut(10);
					if(serverData.success=="true"){
						$('#SaveRepairRequestTipsDialogContent').html(serverData.msg);
						$('#SaveRepairRequestTipsDialog').fadeIn(10);
					}else{
						$('#RepairRequestTipsDialogContent').html(serverData.msg);
						$('#RepairRequestTipsDialog').fadeIn(10);
					}
	            },
	            error:function(a){
	            	$('#loadingToast').fadeOut(10);
	            },
	            dataType: "json"
	        });
		}
	    function clearAddForm(){
			$('#stationid').val('');
			$('#liquidcount').val('');
			$('#liquidremain').val('');
			$('#contacttel').val('');
			$('#remark').val('');
			$('#stationname').val('');
			$('#contactperson').val('');
        	$('#message').val('');
          	$('#expectedtime').val('');
          	$('#latesttime').val('');
        	$('#usetime').val('');
			$('#SaveRepairRequestTipsDialog').fadeOut(10);
			$(location).attr('href', "../servlet/webhome?requestpage=other&openid=${openid}");
		}
	    function insertLine(t,a){
	    	var element = document.getElementById(t);
	    	vv = element.value;v=event.keyCode;
	    	

	    	if(v>57||v<47){
	    		vv=vv.replace(/[^0-9]*/g,'');
	    		if(a==0){
	    			ss = vv.substring(0,4)+"-"+vv.substring(4,8)+"-"+vv.substring(8,12)+"-"+vv.substring(12,16);
	    		}else if (a==1){
	    			ss = vv.substring(0,3)+"-"+vv.substring(3,7)+"-"+vv.substring(7,11);
	    		}
	    		element.value=ss.replace(/[-]*$/g,'');
	    	}else {
	    		vv=vv.replace(/[^0-9]*/g,'');
	    		if(a==0){
	    			ss = vv.substring(0,4)+"-"+vv.substring(4,8)+"-"+vv.substring(8,12)+"-"+vv.substring(12,16);
	    		}else if (a==1){
	    			ss = vv.substring(0,3)+"-"+vv.substring(3,7)+"-"+vv.substring(7,11);
	    		}
	    		element.value=ss.replace(/[-]*$/g,'');
	    	}
	    }
	</script>
</head>
<body>
	<div class="header">
		<div class="left"><a href="../servlet/webhome?requestpage=other&openid=${openid}"><img src="../statics/img/back.png"></a></div>
		<div class="center position"><b>填写叫液单</b></div>
		<div class="right"><a href="../servlet/webhome?requestpage=other&openid=${openid }"><img src="../statics/img/home.png"></a></div>
	</div>
	<div class="weui-cells weui-cells_form" style="margin-top: 55px;">
		<div class="weui-cell">
        	<div class="weui-cell__hd"><label class="weui-label">站点:</label></div>
           	<div class="weui-cell__bd">
          	 	<input type="hidden" name="stationid" id="stationid">
           		<input class="weui-input" type="text" placeholder="请选择站点" readOnly="readOnly" id="stationname">
            </div>
            <div class="icon-box">
            	<i class="weui-icon-search" onclick="javascript:$('#RepairRequestStationSearchDialog').fadeIn(10);"></i>
        	</div>
        </div>
  		<div class="weui-cell">
       		<div class="weui-cell__hd"><label class="weui-label">叫液量(吨):</label></div>
            <div class="weui-cell__bd">
            	<input class="weui-input" type="number" placeholder="请输入"  name="liquidcount" id="liquidcount" onkeyup="this.value= this.value.match(/\d+(\.\d{0,2})?/) ? this.value.match(/\d+(\.\d{0,2})?/)[0] : ''" ></input>
            </div>
       	</div>
      	<div class="weui-cell" >
          	<div class="weui-cell__hd"><label class="weui-label">剩余液位(%):</label></div>
            <div class="weui-cell__bd">
            	<input class="weui-input" type="number" placeholder="请输入" name="liquidremain" required="required" id="liquidremain" onkeyup="this.value= this.value.match(/\d+(\.\d{0,2})?/) ? this.value.match(/\d+(\.\d{0,2})?/)[0] : ''">
            </div>
      	</div>
      	<div class="weui-cell" >
          	<div class="weui-cell__hd"><label class="weui-label">使用时间(小时):</label></div>
            <div class="weui-cell__bd">
            	<input class="weui-input" type="number" placeholder="请输入" name="usetime" id="usetime" onkeyup="this.value= this.value.match(/\d+(\.\d{0,2})?/) ? this.value.match(/\d+(\.\d{0,2})?/)[0] : ''">
            </div>
      	</div>
      		<div class="weui-cell" >
          	<div class="weui-cell__hd"><label class="weui-label">联系人:</label></div>
            <div class="weui-cell__bd">
            	<input class="weui-input" type="text" placeholder="请输入" name="contactperson" id="contactperson">
            </div>
      	</div>
      		<div class="weui-cell" >
          	<div class="weui-cell__hd"><label class="weui-label">联系方式:</label></div>
            <div class="weui-cell__bd">
            	<input class="weui-input" type="tel" pattern="[0-9]*"  name="contacttel" id="contacttel" maxlength="13" onkeyup="javascript:insertLine('contacttel',1);"> 
            </div>
      	</div>
      		<div class="weui-cell" >
          	<div class="weui-cell__hd"><label class="weui-label">期望到达时间:</label></div>
            <div class="weui-cell__bd">
            	 <input class="weui-input" type="datetime-local" data-toggle='date' id="expectedtime"  placeholder="请选择" />
            </div>
      	</div>
      		<div class="weui-cell" >
          	<div class="weui-cell__hd"><label class="weui-label">最晚到达时间:</label></div>
            <div class="weui-cell__bd">
            	 <input class="weui-input" type="datetime-local" data-toggle='date' id="latesttime"  placeholder="请选择" />
            </div>
      	</div>
  
      	<div class="weui-cell" >
          	<div class="weui-cell__hd"><label class="weui-label">留言:</label></div>
            <div class="weui-cell__bd">
            	<textarea class="weui-textarea" rows="2"  name="message" id="message"></textarea>
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
	  		<a href="javascript:saveCallLiquidRequest();" class="weui-btn weui-btn_primary">保存</a>
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
            <p class="weui-toast__content">保存中</p>
        </div>
    </div>
</body>
</html>