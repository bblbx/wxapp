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
    <script type="text/javascript" src="../statics/js/jquery-3.2.1.min.js"></script>
    <script> 
	    function searchMaoYiCount(){
			var beginfinancedate = $('#mybeginreportsearchdate').val();
			var endfinancedate = $('#myendreportsearchdate').val();
			$(location).attr('href', '../servlet/otherweb?requestpage=MaoYiReport&openid=${openid}&beginfinancedate='+beginfinancedate+'&endfinancedate='+endfinancedate);
		}
	</script>
</head>
<body>
	<div class="header">
		<div class="left"><a href="../servlet/webhome?requestpage=other&openid=${openid}"><img src="../statics/img/back.png"></a></div>
		<div class="center position"><b>贸易量</b></div>
		<div class="right"><a href="../servlet/webhome?requestpage=other&openid=${openid }"><img src="../statics/img/home.png"></a></div>
	</div>
	
	<div class="weui-cells weui-cells_form" style="margin-top: 55px;">
		<div class="weui-cell">
        	<div class="weui-cell__hd"><label for="" class="weui-label">开始日期</label></div>
            <div class="weui-cell__bd">
            	<input class="weui-input" type="date" id="mybeginreportsearchdate" value="${beginfinancedate}" />
            </div>
        </div>
        
        <div class="weui-cell">
        	<div class="weui-cell__hd"><label for="" class="weui-label">结束日期</label></div>
            <div class="weui-cell__bd">
            	<input class="weui-input" type="date" id="myendreportsearchdate" value="${endfinancedate}" />
            </div>
        </div>
        
        <div class="weui-cell">
        	<div class="weui-cell__bd">
          		<a href="javascript:searchMaoYiCount();" class="weui-btn weui-btn_plain-primary" style="font-size: 12px;padding: 0;line-height:28px;width:100%">查询</a>
          	</div>
        </div>
        <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">汇总：</label></div>
        </div>
        <div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">LPG</label></div>
			<div class="weui-cell__hd"><label class="weui-label">CNG</label></div>
       		<div class="weui-cell__hd"><label class="weui-label">LNG</label></div>
        </div>
        <c:forEach var="maoyisum"  items="${maoyiSumList}">
        	<div class="weui-cell" style="background-color: #F2F2F2; padding: 6px 15px;">
       			<div class="weui-cell__hd"><label class="weui-label" >${maoyisum['LPG']}</label></div>
       			<div class="weui-cell__hd"><label class="weui-label" >${maoyisum['CNG']}</label></div>
       			<div class="weui-cell__hd"><label class="weui-label" >${maoyisum['LNG']}</label></div>
        	</div>
        </c:forEach>
        
        <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">明细：</label></div>
        </div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label" style="width:150px;">类型</label></div>
			<div class="weui-cell__hd"><label class="weui-label">库区</label></div>
       		<div class="weui-cell__hd"><label class="weui-label">贸易量</label></div>
        </div>
        
        <c:forEach var="maoyicount"  items="${maoyicountlist}">
        	<div class="weui-cell" style="background-color: #F2F2F2; padding: 6px 15px;">
       			<div class="weui-cell__hd"><label class="weui-label" style="width:150px;">${maoyicount['Name']}</label></div>
       			<div class="weui-cell__hd"><label class="weui-label" >${maoyicount['KuQuName']}</label></div>
       			<div class="weui-cell__hd"><label class="weui-label" >${maoyicount['Count']}</label></div>
        	</div>
        </c:forEach>
	</div>
</body>
</html>