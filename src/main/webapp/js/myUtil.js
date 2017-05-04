/**
 * 自定义的工具类，依赖jqeury
 */
(function (root, factory) {
    if (typeof define === 'function' && define.amd) { // AMD
        define(['jquery'], factory);
    } else if (typeof exports === "object" && typeof module.exports === "object") {// Node, CommonJS之类的
        module.exports = factory(require('jquery'));
    } else {
        root.myUtil = factory(root.$);
    }
}(typeof window !== "undefined" ? window : this, function ($) {//
	/**
	 * 根据传入的元素名字动态的创建元素
	 * @param eleName
	 * @returns
	 */
	function newEle(eleName){
		return document.createElement(eleName);
	}
	/**
	 * 根据id获取元素
	 * @param id
	 * @returns
	 */
	function getById(id){
		return document.getElementById(id);
	}
	
	
	/**
	 * 弹出提示信息
	 */
	var alert = {};
	var modal_div = " <div class=\"modal\" >"
        +" <div class=\"modal-dialog\">"
        +"  <div class=\"modal-content\">"
        +"      <div class=\"modal-header\" id=\"modal-header\">"
        +"        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">"
        +"	          <span aria-hidden=\"true\">&times;</span></button>"
        +"        <h4 class=\"modal-title\">提示信息</h4>"
        +"      </div>"
        +"    	<div class=\"modal-body\" id=\"modal-body\"></div>"
        +"      <div class=\"modal-footer\" id=\"modal-footer\">"
        +"          <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">确定</button>"
        +"      </div>"
        +"  </div>"
        +" </div>"
        +"</div>";
	function initModal(){
		var modalInfo = {};
		var modal = $("#modal_div div[class='modal']")[0];
		if(modal==undefined){
			$("#modal_div").append(modal_div);
			modal = $("#modal_div div[class='modal']")[0];
		}
		modalInfo.modal_body = $("#modal_div #modal-body")[0];
		$(modalInfo.modal_body).empty();
		modalInfo.modal_header = $("#modal_div #modal-header")[0];
		modalInfo.modal_header_h4 = $("#modal_div #modal-header h4")[0];
		modalInfo.modal_footer = $("#modal_div #modal-footer")[0];
		modalInfo.modal = modal;
		return modalInfo;
	}
	
	/**
	 * 普通的提示信息
	 */
	alert.info = function(info){
		var modalInfo = initModal();
		$(modalInfo.modal_header_h4).empty();
		$(modalInfo.modal_header_h4).append("提示信息");
		$($("#modal_div #modal-body")[0]).append("<p><li class=\"icon fa fa-info font-big\"></li>"+info+"</p>");
		$(modalInfo.modal).modal("show");
	}
	/**
	 * 警告信息
	 */
	alert.warning = function(info){
		var modalInfo = initModal();
		$(modalInfo.modal_header_h4).empty();
		$(modalInfo.modal_header_h4).append("警告信息");
		$($("#modal_div #modal-body")[0]).append("<p><li class=\"icon fa fa-warning warning font-big\"></li>"+info+"</p>");
		$(modalInfo.modal).modal("show");
	}
	
	var confirm = {}
	/**
	 * @info 需要提示的信息
	 * @success 当点击确认的时候执行的操作
	 * @fail 点击取消执行的操作
	 */
	confirm.info = function(info,success,fail){
		var modalInfo = initModal();
		$(modalInfo.modal_header_h4).empty();
		$(modalInfo.modal_header_h4).append("确认信息");
		$($("#modal_div #modal-body")[0]).append("<p><li class=\"icon fa fa-info font-big\"></li>"+info+"</p>");
		
		$(modalInfo.modal_footer).append("<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">取消</button>");
		var failBtn = $(modalInfo.modal_footer).find("button")[1];
		
		var successBtn = $(modalInfo.modal_footer).find("button")[0];
		if(successBtn!=undefined&&(typeof success==="function")){
			$(successBtn).click(function(){
				success();
				$(successBtn).unbind("click");//因为使用的是同一个按钮，所以取消掉
				if(failBtn!=undefined){
					$(failBtn).remove();
				}
			});
		}
		//添加取消按钮
		
		if(failBtn!=undefined){
			$(failBtn).click(function(){
				if(typeof fail==="function"){
					fail();
				}
				$(modalInfo.modal).modal("hide");
				$(failBtn).remove();
			});
		}
		$(modalInfo.modal).modal("show");
	}
	/**
	 * 渲染表格数据
	 */
	var renderTable = function(data,data_space){
		var table = $("#"+data_space+" div[class*='row'] table");
		if(table.length>0){
			table = $(table[0]);
		}else{
			return ;
		}
		var ths = $(table).find("thead tr th");
		
		var html = "";
		var order = 1;
		$.each(data.result,function(key,val){
			var tr_html = "<tr>";
			$.each(ths,function(i,v){
				var $hiddenDiv = $(v).find("div");
				var htmlStr = "";
				if($hiddenDiv.length!=0){
					var hiddenInputs = $($hiddenDiv).children("input");
					var inputStr = "";
					for(var j=0;j<hiddenInputs.length;j++){
						var hiddenInput = hiddenInputs[j];
						var inputName = $(hiddenInput).attr("name");
						var inputVal = val[inputName];
						if(inputVal==undefined){
							inputVal = "";
						}
						inputStr+="<input type='hidden' name='"+inputName+"' value='"+inputVal+"'></input>";
					}
					if(inputStr!=""){
						htmlStr = inputStr;
					}else{
						htmlStr = $($hiddenDiv).html();
					}
				}
				var id = $(v).attr("id");
				tr_html+="<td name="+id+">";
				var valStr = val[id];
				if(valStr==undefined){
					valStr = "";
				}
				if(id=="order"){//序号
					tr_html+= order;
				}else{
					tr_html+= valStr;
				}
				if(htmlStr!=""){
					tr_html+=htmlStr;
				}
				tr_html+="</td>";
			});
			tr_html+="</tr>";
			html += tr_html;
			order++;
		});
		//去除掉表头区域隐藏的div
//		$.each(ths,function(i,v){
//			var $hiddenDiv = $(v).find("div");
//			if($hiddenDiv!=undefined){
//				$hiddenDiv.remove();
//			}
//		});
		
		$(table).find("tbody").empty();
		$(table).find("tbody").append(html);
	}
	/**
	 * 获取条件区的查询条件
	 */
	var getParam  = function(data_space){
		var inputs = $("#"+data_space+" div[class*='box'] form input");
		var param = {};
		$.each(inputs,function(i,v){
			var id = $(v).attr("id");
			var val = $(v).val();
			param[id] = val;
		});
		
		var selects = $("#"+data_space+" div[class*='box'] form select");
		
		$.each(selects,function(i,v){
			var id = $(v).attr("id");
			var val = $(v).val();
			param[id] = val;
		});
		var cur_page =  $("#"+data_space +" #cur_page ").val();
		if(cur_page==undefined||cur_page==""){
			cur_page = 1;
		}
		param.cur_page = cur_page;
		return param;
	}
	
	var queryTable = function (btn,space,before){
		var data_space = "";
		if(btn==null){
			data_space = "tab_"+space;
		}else{
			data_space ="tab_"+$(btn).attr("data_space");//命名空间
		}
		
		var param = getParam(data_space);
	    var url = $(btn).attr("url");
		if(url==undefined||url==""){
			throw new Error("url不能为空");
		}
		$.ajax({
			type : "post",
			url : url,
			async : true,
			data : param,
			dataType : "json",
			success : function(data) {
				console.log(data);
				if(typeof before === "function"){
					before(data);
				}
				renderTable(data.returnObj,data_space);
				renderNav(data,data_space,url,btn);
			},
			error : function(ex) {
				console.log(ex);
			}
		});
		
	}
	
	var query = {
		getParam : getParam,
		renderTable : renderTable,
		query:queryTable
	};
	/**
	 * 获取按钮上的命名空间
	 */
	query.getDataSpace = function(btn){
		return "tab_"+$(btn).attr("data_space");
	}
	
	var renderNav = function(data,data_space,url,btn){
		var ul = $("#"+data_space +" #pagination nav ul");
		if(ul.length==0){
			return ;
		}
		var page = data.returnObj;
		var page_num = page.page_num;//
		var cur_page = page.cur_page;
		$("#"+data_space +" #page_num ").val(page_num);
		$("#"+data_space +" #cur_page ").val(cur_page);
		
		var pre = '<li name="pre"><span  aria-label="Previous"><span  aria-hidden="true">&laquo;</span></span></li>';
		var next = '<li name="next"><span  aria-label="Next"> <span aria-hidden="true">&raquo;</span></span></li>';
		//渲染 分页元素
		$(ul).empty();
		$(ul).append(pre);
		var start_index = 1;
		if((cur_page-2)>1&&(cur_page+2)<=page_num){//保证当前页面是居中的
			start_index = cur_page-2;
		}
		if((cur_page+2)>page_num&&(page_num>=5)){//页面是最后几个的时候
	        start_index = page_num-4;
	    }
		
		for(var i = start_index;i<=page_num;i++){
			$(ul).append('<li name="index_'+i+'" ><span >'+i+'</span></li>');
			if((i-start_index)==4){//只显示5个
				break;
			}
		}
		$(ul).append(next);
		
		//禁用前进或后退按钮
		var $pre = $(ul).find("li[name='pre']");
		var $next = $(ul).find("li[name='next']");
		if(cur_page==1){
			$($pre).addClass("disabled");
		}else if(cur_page==page_num){
			$($next).addClass("disabled");
		}else{
			$($pre).removeClass("disabled");
			$($next).removeClass("disabled");
		}
		
		//选中页面添加样式
		var page_li = $(ul).find("li[name^='index']");
		$.each(page_li,function(i,v){
			var name_attr = $(v).attr("name");
			var name_array = name_attr.split("_");
			var index = name_array[1];
			if(index==cur_page){
				$(v).addClass("active");
			}else{
				$(v).removeClass("active");
			}
		});
		
		$.each($(ul).find("li"),function(i,v){
			if($(v).hasClass("disabled")){
				return true;
			}
			$(v).click(function(){
				var name_attr = $(v).attr("name");
				var index = 1;
				var cur_page = $("#"+data_space +" #cur_page ").val()
				var page_num = $("#"+data_space +" #page_num ").val()
				if(name_attr.indexOf("index")!=-1){
					var name_array = name_attr.split("_");
					index = name_array[1];//当前页面
				}else if(name_attr=="pre"){
					if(cur_page!=1){
						index = parseInt(cur_page)-1;
					}
				}else if(name_attr=="next"){
					if(cur_page!=page_num){
						index = parseInt(cur_page)+1;
					}
				}
				$("#"+data_space +" #cur_page ").val(index);//修改当前页面
				queryTable(btn);//查询数据
			});
		});
		
		
		
		
		
		
	}
	
	/**
	 * 重置查询条件
	 * 支持的元素 input
	 */
	query.reset = function(btn){
		var dataspace = query.getDataSpace(btn);
		var inputs = $("#"+dataspace+" div[class*='box'] form input");
		$.each(inputs,function(i,v){
			$(v).val("");
		})
		var selects = $("#"+dataspace+" div[class*='box'] form select");
		$.each(selects,function(i,v){
			$(v).val("");
		})
	}
	
	
    var myUtil = {
       alert:alert,
       query:query,
       confirm:confirm
    }
    return myUtil;

}));