//加载ace

//$(function() {
//	loadAce();
//	// 绑定
//	$("#tab_AceEditer #btn_submit").click(function(e) {
//		debugger;
//		e.preventDefault();
//		upload_file();
//	});
//});

/**
 * 加载ace编辑器
 */
function loadAce(editor_id) {
	require("ace/ext/old_ie");
	ace.require("ace/ext/language_tools");
	var Myeditor = ace.edit(editor_id);// TODO 需要修改
	Myeditor.$blockScrolling = Infinity;
	Myeditor.setFontSize(18);// 设置字体大小
	Myeditor.session.setMode("ace/mode/markdown");// 设置编辑器的语言为markdown
	Myeditor.setOptions({
		enableBasicAutocompletion : true,
		enableSnippets : true,
		enableLiveAutocompletion : true
	});
	Myeditor.setTheme("ace/theme/tomorrow");// 设置主题
	// myUtil.editor = Myeditor;// TODO 需要修改
	return Myeditor;
}

// function consoleVal() {
// console.log(myUtil.editor.getValue());
// }
/**
 * 预览
 */
function privewArticle() {
	var editor = cur_editor();
	if (editor.getValue().trim() == "") {
		myUtil.alert.info("文本内容不能为空");
		return;
	}
	var tabid = cur_tabId();
	var param = {};
	param.content_markdown = editor.getValue();
	$("#" + tabid + " #privewArticleDiv .modal-body").load("privewArticle.do",
			param, function(response, status, xhr) {
				console.log("预览页面加载成功！");
			});
	$("#" + tabid + " #privewArticleDiv > .modal").modal("show").css({
		width : 'auto'
	});
	;
}
/**
 * 显示保存modal
 */
function showSaveModal() {
	var editor = cur_editor();
	if (editor.getValue().trim() == "") {
		myUtil.alert.info("文本内容不能为空");
		return;
	}
	var tabid = cur_tabId();
	var is_push = $("#" + tabid + " #hiddenDiv #is_push").val();
	if (is_push == "1") {
		myUtil.alert.info("已经发布的文章不能保存，只能进行发布操作");
		return;
	}
	$("#" + tabid + " #saveArticleDiv > .modal").modal("show");
}
/**
 * 执行保存操作
 */
function saveArticle() {
	debugger;
	var tabid = cur_tabId();
	var title = $("#" + tabid + " #saveArticleDiv #title").val();
	if (title == "") {
		myUtil.alert.info("标题不能为空");
		return;
	}
	var is_push = $("#" + tabid + " #hiddenDiv #is_push").val();
	if (is_push == "1") {
		myUtil.alert.info("已经发布的文章不能保存，只能进行发布操作");
		return;
	}
	var param = {};
	param.title = title;
	var editor = cur_editor();
	param.content_markdown = editor.getValue();

	$.ajax({
		type : "post",
		url : "saveArticle.do",
		async : true,
		data : param,
		dataType : "json",
		success : function(data) {
			if (data.flag == false) {
				myUtil.alert.warning(data.msg);
			} else {
				myUtil.alert.info(data.msg);
				$("#" + tabid + " #hiddenDiv #article_id").val(data.returnObj);
			}
		},
		error : function(ex) {
			console.log(ex);
		}
	});

	// console.log(param);
	// myUtil.alert.info("保存成功");
}
/**
 * 显示发布modal
 */
function showPushModal() {
	var editor = cur_editor();
	if (editor.getValue().trim() == "") {
		myUtil.alert.info("文本内容不能为空");
		return;
	}
	var tabid = cur_tabId();
	$("#" + tabid + " #pushArticleDiv > .modal").modal("show");
}
/**
 * 发布文档
 */
function pushArticle() {
	// debugger;
	var tabid = cur_tabId();
	var title = $("#" + tabid + " #pushArticleDiv #title").val();
	if (title == "") {
		myUtil.alert.info("标题不能为空");
		return;
	}
	var tag = $("#" + tabid + " #pushArticleDiv #tag").val();
	if (tag == "") {
		myUtil.alert.info("标签不能为空");
		return;
	}
	var abstract_str = $("#" + tabid + " #pushArticleDiv #abstract_str").val();
	if (abstract_str == "") {
		myUtil.alert.info("摘要不能为空");
		return;
	}
	if (abstract_str.length < 10) {
		myUtil.alert.info("请输入10个字符以上的简介");
		return;
	}
	var content_markdown
	var param = {};
	param.title = title;
	param.tag = tag;
	param.abstract_str = abstract_str;
	var editor = cur_editor();
	param.content_markdown = editor.getValue();
	param.article_id = $("#" + tabid + " #hiddenDiv #article_id").val();

	$.ajax({
		type : "post",
		url : "pushArticle.do",
		async : true,
		data : param,
		dataType : "json",
		success : function(data) {
			if (data.flag == false) {
				myUtil.alert.warning(data.msg);
			} else {
				myUtil.alert.info(data.msg);
			}
		},
		error : function(ex) {
			console.log(ex);
		}
	});

	// console.log(param);
	// myUtil.alert.info("保存成功");
}
/**
 * 显示上传modal
 */
function showUploadImgModal() {
	// if (myUtil.editor.getValue().trim() == "") {
	// myUtil.alert.info("文本内容不能为空");
	// return;
	// }
	var tabid = cur_tabId();
	$("#" + tabid + " #uploadImgArticleDiv > .modal").modal("show");
}

function upload_file() {
	var tabid = cur_tabId();
	$("#" + tabid + " #form_submit").ajaxSubmit({
		method : "POST",
		success : function(data) {
			data = JSON.parse(data);
			console.log(data.returnObj);
			var imgIdArray = data.returnObj.split(",");
			$.each(imgIdArray, function(i, v) {
				if (v == "") {
					return false;
				}
				var editor = cur_editor();
				editor.insert("\n\r");
				editor.insert("![](/acticle/downloadImg.do?id=" + v + ")\n\r");
			})
		},
		error : function() {

		}
	});
}
function editor_name() {
	var curId = cur_tabId();
	var name = "editor";
	if (curId == "" || curId == undefined) {

	} else {
		name = curId + "_" + name;
	}
	return name;
}
/**
 * 获取当前编辑器
 */
function cur_editor() {
	var name = editor_name();
	var editor = myUtil[name];
	return editor;
}
function back() {
	var param = {};
	var data_space = cur_tabId();
	param.cur_page = $("#" + data_space + " #hiddenDiv #cur_page").val();
	$("#" + data_space).load("toQuery.do", param,
			function(response, status, xhr) {
				console.log("查询页面加载成功！");
			});
}
