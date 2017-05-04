/**
 * 
 */
'use strict';

$(function() {

});
// 对console进行处理，解决ie报错问题
if (typeof console !== "function") {
	// console = {};
	// console.log = function(){};
}

// var $().showTab = $().tab;//因为editermd的一个方法和该方法冲突，故做处理
/**
 * 关闭Tab
 * 
 * @param curTab
 */
function closeTab(curTab) {
	var isActive = isHasAttr($(curTab).parent(), "class", "active");
	var tabAHref = $($(curTab).parent().find("a")[0]).attr("href");
	var tabId = tabAHref.substring(1, tabAHref.length);
	// console.log("tabId:"+tabId);
	if (isActive == true) {
		var tabNum = $("#tab_content > div").length;
		// console.log("tab的数量："+tabNum);
		if (tabNum != 1) {
			// 如果关闭的是一个激活的Tab，后面有Tab的时候，激活后面的tab，然后关闭当前
			// 后面没有前面有的时候，激活前面的tab，关闭当前
			// 获取next兄弟
			// 激活兄弟Tab
			var brotherTab = $(curTab).parent().next("li");
			if (brotherTab.length == 0) {
				brotherTab = $($(curTab).parent().prev("li")[0]);
			}
			$(brotherTab.find("a")[0]).tab("show");
		} else {// 当只剩最后一个tab的时候先默认的不让删除
			myUtil.alert.warning("删除失败，至少保留一个Tab");
			return;
		}

	}
	// 如果当前关闭的是一个未激活的Tab，直接关闭就ok了
	$(curTab).parent().remove();
	$("#tab_content #" + tabId).remove();
}
/**
 * 新增tab
 * 
 * @param data
 */
function addTab(data) {
	var url = data.url;
	// var tabId = "tab_"+data.id;
	var tabId = "tab_" + url.substring(0, url.indexOf("."));// 修改了tabid
	// 为请求url的名字
	var tabName = data.name;
	if (!isExist(tabId)) {
		var tab_header = "<li ><span class=\"close-tab glyphicon glyphicon-remove\" onclick=\"closeTab(this);\"></span>"
				+ "<a href=\"#"
				+ tabId
				+ "\" data-toggle=\"tab\" >"
				+ tabName
				+ "</a></li>";
		var tab_content = "<div class=\"tab-pane\" id=\"" + tabId + "\">";

		$("#tab_nav_header").append(tab_header);// 添加tab头
		$("#tab_content").append(tab_content);// 添加tab内容
		$("#tab_content #" + tabId).load(url, null,
				function(response, status, xhr) {
					if (status != "success") {
						myUtil.alert.info("请求后台发生错误，请稍后再试！");
					}
				});// tab内容加载
	}
	$("#tab_nav_header a[href='#" + tabId + "']").tab("show");// 显示当前加载的tab
}
/**
 * 判断是否存在
 * 
 * @param tabId
 * @returns {Boolean}
 */
function isExist(tabId) {
	var tab = $("#tab_nav_header a[href='#" + tabId + "']")[0];
	if (tab == undefined) {
		return false;
	}
	return true;
}
/**
 * 根据点击的菜单添加tab
 * 
 * @param menu
 */
function AddShowTab(menu) {
	var id = $(menu).attr("id");
	var url = $(menu).attr("url");
	var name = $(menu).text();
	var data = {};
	data.name = name;
	data.id = id;
	data.url = url;
	// console.log(url);
	addTab(data);
}
/**
 * 获取当前主机的地址
 * 
 * @returns {String}
 */
function getHostHref() {
	var port = window.location.port;
	var protocol = window.location.protocol;
	var host = window.location.host;
	return protocol + "://" + host + "/";
}

/**
 * 对象的某个值是否包含某个字符串
 * 
 * @param obj
 * @param attr
 * @param val
 */
function isHasAttr(obj, attr, val) {
	if (obj == undefined) {
		return false;
	}
	var attrVal = $(obj).attr(attr);
	if (attrVal == undefined) {
		return false;
	}
	if (attrVal.indexOf(val) > -1) {
		return true;
	}
	return false;
}
/**
 * 获取当前显示的tab的id
 */
function cur_tabId() {
	var $tab = $("#tab_content>.active");
	return $tab.attr("id");
}
/**
 * 发表评论
 */
function comment() {

	var user_name = $("#comment_div #user_name").val();
	var user_email = $("#comment_div #user_email").val();
	var user_pwd = $("#comment_div #user_pwd").val();
	var user_comment = $("#comment_div #user_comment").val();
	var article_id = $("#comment_div #article_id").val();

	if (user_name.trim() == "") {
		myUtil.alert.warning("用户名不能为空！");
		return;
	}
	if (user_email.trim() == "") {
		myUtil.alert.warning("用户邮箱不能为空！");
		return;
	}
	if (user_comment.trim() == "") {
		myUtil.alert.warning("用户评论不能为空！");
		return;
	}

	var param = {};
	param.user_name = user_name;
	param.user_email = user_email;
	param.user_pwd = user_pwd;
	param.user_comment = user_comment;
	param.article_id = article_id;

	$.ajax({
		type : "post",
		url : "/article/comment.do",
		async : true,
		data : param,
		dataType : "json",
		success : function(data) {
			myUtil.alert.info(data.msg);
			if (data.flag == true) {
				$("#comment_div #user_name").val("");
				$("#comment_div #user_email").val("");
				$("#comment_div #user_pwd").val("");
				$("#comment_div #user_comment").val("");
			}
		},
		error : function(ex) {
			console.log(ex);
		}
	});
}

function quote(ele) {
	// document.getElementById('here').scrollIntoView()
	var $div_post = $(ele).parents(".post");
	console.log($div_post);
	var user_name = $div_post.find(".user-block strong").text();
	var content = $div_post.find(".content-body").html();
	var $user_comment = $("#comment_div #user_comment");
	var user_comment = $user_comment.val();
	$user_comment.val("<pre><code>\n<strong>@" + user_name + ":</strong>\n"
			+ content + "\n</code></pre>\n");
	document.getElementById('comment_div').scrollIntoView()
}
