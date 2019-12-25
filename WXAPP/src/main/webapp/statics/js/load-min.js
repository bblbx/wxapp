function addmask(id){
	if ($(".mask[ele=" + id + "]").length > 0) {
        return;
    }
	let $item = $("#"+id);
	var c = '<div class="mask" ele=' + id + 
	' style="width: ' + $item.width() + "px !important; height: " + $item.height() + "px !important; left: " +
	$item.offset().left + "px !important; top: " + $item.offset().top + 'px !important;"><div>数据加载中...</div></div>';
	$item.append(c);
}
function removemask(id){
	 $(".mask[ele=" + id + "]").remove()
}