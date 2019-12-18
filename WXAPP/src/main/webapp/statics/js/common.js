function loadingShow(ndiv){

	var _LoadingHtml = '<div class="page__bd" id="loading"><div class="weui-loadmore"><i class="weui-loading"></i><span class="weui-loadmore__tips">正在加载</span></div></div>';
	//呈现loading效果
	document.getElementById(ndiv).innerHTML+=_LoadingHtml;
}
function loadingHide(){
	var loadingMask = document.getElementById('loading');
    loadingMask.parentNode.removeChild(loadingMask);
}