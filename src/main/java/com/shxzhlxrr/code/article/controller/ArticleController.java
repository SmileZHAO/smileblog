package com.shxzhlxrr.code.article.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shxzhlxrr.code.article.service.IArticleService;
import com.shxzhlxrr.code.user.model.ReturnModel;
import com.shxzhlxrr.code.user.model.UserModel;
import com.shxzhlxrr.spring.config.BaseController;
import com.shxzhlxrr.spring.config.PageModel;
import com.shxzhlxrr.util.log.LogUtil;
import com.shxzhlxrr.util.markdown.Markdown;
import com.shxzhlxrr.util.string.StringUtil;

@Controller
public class ArticleController extends BaseController {

	@Autowired
	private IArticleService articleService;

	/**
	 * 保存文章
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("/saveArticle.do")
	public void saveArticle(HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("content_markdown", req.getParameter("content_markdown"));
		param.put("title", req.getParameter("title"));
		UserModel user = (UserModel) req.getSession().getAttribute("cur_user");
		param.put("user_id", user.getId());
		param.put("crt_user", user.getName());
		param.put("mdf_user", user.getName());

		try {
			ReturnModel model = articleService.saveArticle(param);
			returnMsg(res, model);
		} catch (Exception e) {
			LogUtil.error("保存文档信息错误：" + e.getCause());
			returnMsg(res, new ReturnModel(false, "保存文档失败"));
			e.printStackTrace();
		}

	}

	/**
	 * 发布文档
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("/pushArticle.do")
	public void pushArticle(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("content_markdown", req.getParameter("content_markdown"));
		param.put("title", req.getParameter("title"));
		param.put("tag", req.getParameter("tag"));
		param.put("abstract_str", req.getParameter("abstract_str"));
		param.put("article_id", req.getParameter("article_id"));// 文章的id，用来区分是进行更新还是保存
		try {
			ReturnModel model = articleService.pushArticle(param);
			returnMsg(res, model);
		} catch (Exception e) {
			LogUtil.error("发布文档信息错误：" + e.getCause());
			returnMsg(res, new ReturnModel(false, "发布文档失败，请稍后重试"));
			e.printStackTrace();
		}
	}

	// TODO 在线预览
	@RequestMapping("privewArticle.do")
	public ModelAndView privewArticle(HttpServletRequest req, HttpServletResponse res, Model model) {
		String content_markdown = req.getParameter("content_markdown");
		try {
			String content_html = new Markdown().toHtml(content_markdown);
			model.addAttribute("content_html", content_html);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("content_html", "预览出错，请联系管理员！");
		}
		return new ModelAndView("article/privewArticle");
	}


	/**
	 * 跳转到编辑界面
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/AceEditer.do")
	public ModelAndView index(HttpServletRequest req, HttpServletResponse res) {
		return new ModelAndView("ace/AceEditer");
	}

	@RequestMapping("/toQuery.do")
	public ModelAndView toQuery(HttpServletRequest req, HttpServletResponse res, Model model) {
		String cur_page = req.getParameter("cur_page");
		if(StringUtil.isNull(cur_page)){
			cur_page = "1";
		}
		model.addAttribute("cur_page", cur_page);
		return new ModelAndView("article/query");
	}

	/**
	 * 查询文章集合
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("/queryActicle.do")
	public void query(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cur_page", req.getParameter("cur_page"));
		param.put("page_size", req.getParameter("page_size"));
		param.put("title", req.getParameter("title"));
		param.put("tag", req.getParameter("tag"));
		param.put("article_id", req.getParameter("article_id"));//
		param.put("is_push", req.getParameter("is_push"));//
		try {
			ReturnModel model = articleService.query(param);
			returnMsg(res, model);
		} catch (Exception e) {
			LogUtil.error("查询文章集合错误：" + e.getCause());
			returnMsg(res, new ReturnModel(false, "查询错误，请稍后重试"));
			e.printStackTrace();
		}
	}

	/**
	 * 首页查询使用
	 * 
	 * @param req
	 * @param res
	 */
	// @RequestMapping("/acticle/queryActicle.do")
	public void queryActicle(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cur_page", req.getParameter("cur_page"));
		param.put("page_size", req.getParameter("page_size"));
		try {
			ReturnModel model = articleService.queryArticle(param);
			returnMsg(res, model);
		} catch (Exception e) {
			LogUtil.error("查询文章集合错误：" + e.getCause());
			returnMsg(res, new ReturnModel(false, "查询错误，请稍后重试"));
			e.printStackTrace();
		}
	}

	@RequestMapping("/")
	public ModelAndView home(HttpServletRequest req, HttpServletResponse res,Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cur_page", req.getParameter("cur_page"));
		param.put("page_size", req.getParameter("page_size"));
		param.put("tag_id", req.getParameter("tag_id"));
		try {
			ReturnModel returnModel = articleService.queryArticle(param);
			@SuppressWarnings("unchecked")
			Map<String, Object> returnMap = (Map<String, Object>) returnModel.getReturnObj();
			PageModel page = (PageModel) returnMap.get("page");
			// returnMsg(res, model);
			model.addAttribute("page", page);
			model.addAttribute("hotArticles", returnMap.get("hotArticles"));
			model.addAttribute("visit_num", returnMap.get("visit_num"));
			model.addAttribute("comment_num", returnMap.get("comment_num"));
			model.addAttribute("tags", returnMap.get("tags"));
		} catch (Exception e) {
			LogUtil.error("查询文章集合错误：" + e.getCause());
			// returnMsg(res, new ReturnModel(false, "查询错误，请稍后重试"));
			e.printStackTrace();
		}
		return new ModelAndView("home");
	}

	/**
	 * 查询文章详情
	 * 
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping("/article/articleDetail.html")
	public ModelAndView articleDetail(HttpServletRequest req, HttpServletResponse res, Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", req.getParameter("id"));// 文章的id
		try {
			String id = req.getParameter("id");
			Object article_ids_obj = req.getSession().getAttribute("article_ids");
			String article_ids = "";
			if(article_ids_obj!=null){
				article_ids = String.valueOf(article_ids_obj);
			}
			param.put("is_push", "1");// 只查询发布的
			param.put("is_count", "true");
			param.put("article_ids", article_ids);
			ReturnModel returnModel = articleService.queryArticleDetail(param);
			if (article_ids.indexOf(id) == -1) {// 处理统计访问次数
				article_ids += id + ",";
				req.getSession().setAttribute("article_ids", article_ids);
			}
			model.addAttribute("article", returnModel.getReturnObj());
		} catch (Exception e) {
			LogUtil.error("查询文章详情错误：" + e.getCause());
			e.printStackTrace();
		}
		return new ModelAndView("article/detail");
	}

	/**
	 * 跳转到文章编辑界面
	 * 
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping("/toArticleEdit.do")
	public ModelAndView articleEdit(HttpServletRequest req, HttpServletResponse res, Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", req.getParameter("id"));// 文章的id
		param.put("cur_page", req.getParameter("cur_page"));// 文章的id
		try {
			ReturnModel returnModel = articleService.queryArticleDetail(param);
			model.addAttribute("article", returnModel.getReturnObj());
		} catch (Exception e) {
			LogUtil.error("跳转到文章编辑界面错误：" + e.getCause());
			e.printStackTrace();
		}
		return new ModelAndView("ace/ArticleEditer");
	}

	/**
	 * 删除文章
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("/delArticle.do")
	public void delArticle(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("article_id", req.getParameter("article_id"));// 文章的id
		try {
			ReturnModel returnModel = articleService.delArticle(param);
			returnMsg(res, returnModel);
		} catch (Exception e) {
			LogUtil.error("删除文章出现异常：" + e.getCause());
			e.printStackTrace();
		}
	}

	/**
	 * 撤销文章
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("/revokeArticle.do")
	public void revokeArticle(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("article_id", req.getParameter("article_id"));// 文章的id
		try {
			ReturnModel returnModel = articleService.revokeArticle(param);
			returnMsg(res, returnModel);
		} catch (Exception e) {
			LogUtil.error("撤销文章出现异常：" + e.getCause());
			e.printStackTrace();
		}
	}

}
