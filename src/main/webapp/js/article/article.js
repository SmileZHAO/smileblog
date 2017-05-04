/**
 * 文章查询js
 */

$(function() {
	// debugger;
	myUtil.query.query($("#tab_toQuery #queryArticle"), "toQuery");
});

function articleBefore(data) {
}

function getTrHiddenParam(element) {
	var $inputs = $(element).parents("tr").find("td[name='order']").children(
			"input");
	var param = {};
	$.each($inputs, function(i, v) {
		param[$(v).attr("name")] = $(v).val();
	});
	return param;
}
/**
 * 删除文章
 */
function articleDel(element) {
	var param = getTrHiddenParam(element);
	param.article_id = param.id;
	myUtil.confirm.info("删除之后不可恢复，请确认是否删除！", function() {
		$.ajax({
			type : "post",
			url : "delArticle.do",
			async : true,
			data : param,
			dataType : "json",
			success : function(data) {
				myUtil.alert.info(data.msg);
				if (data.flag == true) {
					myUtil.query.query($("#tab_toQuery #queryArticle"),
							"toQuery");
				}
			},
			error : function(ex) {
				console.log(ex);
			}
		});
	});

}
/**
 * 撤销文章
 * 
 * @param element
 */
function remokeArticle(element) {
	var param = getTrHiddenParam(element);
	param.article_id = param.id;
	myUtil.confirm.info("撤销之后将看不到该文章，需要重新发布，确认是否撤销！", function() {
		$.ajax({
			type : "post",
			url : "revokeArticle.do",
			async : true,
			data : param,
			dataType : "json",
			success : function(data) {
				myUtil.alert.info(data.msg);
				if (data.flag == true) {
					myUtil.query.query($("#tab_toQuery #queryArticle"),
							"toQuery");
				}
			},
			error : function(ex) {
				console.log(ex);
			}
		});
	});

}

/**
 * 编辑文章
 */
function articleEdit(element) {
	debugger;
	var param = getTrHiddenParam(element);
	if (param.id == "" || param.id == undefined) {
		myUtil.alert.info("唯一标识丢失，请联系管理员！");
		return;
	}
	var data_space = cur_tabId();
	var cur_page = $("#" + data_space + " #cur_page ").val();
	param.cur_page = cur_page;
	console.log(param);
	$("#tab_toQuery").load("toArticleEdit.do", param,
			function(response, status, xhr) {
				console.log("加载编辑页面成功！");
			});

}