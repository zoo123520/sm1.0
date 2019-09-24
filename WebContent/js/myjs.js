function mouseOver(obj){
	obj.style.backgroundColor = "#ccc";
}
function mouseOut(obj){
	obj.style.backgroundColor = "";
}
function deleteById(sp,id){
	if(confirm("您确定要删除编号为"+id+"这条记录吗？")){
		location.href = "StudentServlet?opType=delete&currentPage="+sp+"&id="+id;
	}
}
function checkAll(){
	$("input[name='student']").prop("checked",$("#all").prop("checked"));
}
function deleteMore(sp){
	//获取被选中的复选框个数
	var num = $("input[name='student']:checked").size();
	if(num==0){
		alert("请选择删除的记录。");
		return;
	}
	if(confirm("您确定要删除这"+num+"条数据吗？")){
		var array = new Array();
		$("input[name='student']:checked").each(function(i){
			//循环把选中的复选框的值添加到数组尾部
			array.push($(this).val());
		});
		//把数组中所有元素转成字符串，用逗号隔开。
		var ids = array.join();
		//发送请求地址到后台执行删除操作
		location.href = "StudentServlet?opType=deleteMore&currentPage="+sp+"&ids="+ids;
	}
}