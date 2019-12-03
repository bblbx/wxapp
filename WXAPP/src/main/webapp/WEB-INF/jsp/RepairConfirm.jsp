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
				
				loadingShow('confirmrepairreqdiv');
				$.ajax({
					type : 'POST',
					url : "../repair/queryRepairRequest4Confirm",
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
									html += "<a class='weui-form-preview__btn weui-form-preview__btn_primary' href='../servlet/otherweb?requestpage=repair/DoRepairConfirm&openid=${openid}&repairorderid="+serverData.msg[i].OrderID+"');>详细信息</a>";
									html += "</div>";
									html += "</div>";
									html += "<br>";
									$("#confirmrepairreqdiv").append(html);
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
	</script>
</head>
<body>
	<div class="header">
		<div class="left"><a href="../servlet/webhome?requestpage=other&openid=${openid}"><img src="../statics/img/back.png"></a></div>
		<div class="center position"><b>故障报修单</b></div>
		<div class="right"><a href="../servlet/webhome?requestpage=other&openid=${openid }"><img src="../statics/img/home.png"></a></div>
	</div>
	
	<div class="page__bd" style="margin-top: 55px;" id="confirmrepairreqdiv">
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
	                <a class="weui-form-preview__btn weui-form-preview__btn_primary" href="../servlet/otherweb?requestpage=repair/DoRepairConfirm&openid=${openid}&repairorderid=${repairRequest['OrderID']}">详细信息</a>
	            </div>
	        </div>
	        <br>
        </c:forEach>
        <br>
    </div>
    <div style="text-align: center; color: #DDDDDD; display: none;" id="end">--已经到底了--</div>
    
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