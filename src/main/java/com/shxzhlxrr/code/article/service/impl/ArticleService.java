package com.shxzhlxrr.code.article.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shxzhlxrr.code.article.dao.ArticleDao;
import com.shxzhlxrr.code.article.dao.CommentDao;
import com.shxzhlxrr.code.article.model.ArticleModel;
import com.shxzhlxrr.code.article.model.ArticleTagModel;
import com.shxzhlxrr.code.article.model.TagModel;
import com.shxzhlxrr.code.article.service.IArticleService;
import com.shxzhlxrr.code.user.model.ReturnModel;
import com.shxzhlxrr.spring.config.BaseService;
import com.shxzhlxrr.spring.config.PageModel;
import com.shxzhlxrr.spring.context.ThreadLocalContext;
import com.shxzhlxrr.util.date.DateUtil;
import com.shxzhlxrr.util.markdown.Markdown;
import com.shxzhlxrr.util.string.StringUtil;
import com.shxzhlxrr.util.uuid.UUidUtil;

@Service
public class ArticleService extends BaseService implements IArticleService {

	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private CommentDao commentDao;

	private static final String[] LABEL_COLOR = { "label-danger", "label-success", "label-info", "label-warning",
			"label-primary" };

	@Override
	public ReturnModel saveArticle(Map<String, Object> param) throws IOException {
		ArticleModel article = new ArticleModel();
		article.setContent_markdown(StringUtil.getVal(param, "content_markdown"));
		article.setTitle(StringUtil.getVal(param, "title"));
		article.setUser_id(StringUtil.getVal(param, "user_id"));
		article.setCrt_user(StringUtil.getVal(param, "crt_user"));
		article.setMdf_user(StringUtil.getVal(param, "mdf_user"));
		article.setId(UUidUtil.getId());
		article.setCrt_time(DateUtil.sysTime());
		article.setMdf_time(DateUtil.sysTime());
		article.setIs_push("2");// 默认为未发布
		if (StringUtil.isNull(article.getTitle())) {
			return new ReturnModel(false, "标题不能为空！");
		}
		if (StringUtil.isNull(article.getContent_markdown())) {
			return new ReturnModel(false, "文档内容不能为空！");
		}

		// 把markdown转换成html
		article.setContent_html(new Markdown().toHtml(article.getContent_markdown()));
		int insertNum = 0;
		if (StringUtil.isNull(param.get("article_id"))) {// 当有文章id的时候进行更新的操作
			insertNum = articleDao.saveArticle(article);
		} else {
			insertNum = articleDao.updateSaveArticle(article);
		}

		if (insertNum == 0) {
			return new ReturnModel(false, "保存文档失败");
		} else {
			return new ReturnModel(true, "保存文档成功", article.getId());
		}
	}

	@Override
	public ReturnModel pushArticle(Map<String, Object> param) throws IOException {
		//
		ArticleModel article = new ArticleModel();
		article.setContent_markdown(StringUtil.getVal(param, "content_markdown"));
		article.setTitle(StringUtil.getVal(param, "title"));
		article.setId(StringUtil.getVal(param, "article_id"));
		article.setAbstract_str(StringUtil.getVal(param, "abstract_str"));
		// initBaseInfo(article);
		article.setIs_push("1");// 默认为发布
		article.setUser_id(ThreadLocalContext.getCurUser().getId());
		if (StringUtil.isNull(article.getTitle())) {
			return new ReturnModel(false, "标题不能为空！");
		}
		if (StringUtil.isNull(article.getContent_markdown())) {
			return new ReturnModel(false, "文档内容不能为空！");
		}
		if (StringUtil.isNull(article.getAbstract_str())) {
			return new ReturnModel(false, "文档摘要不能为空！");
		}
		if (StringUtil.isNull(param.get("tag"))) {
			return new ReturnModel(false, "文档标签不能为空！");
		}
		// 把markdown转换成html
		article.setContent_html(new Markdown().toHtml(article.getContent_markdown()));

		// System.out.println(new Markdown4jProcessor());

		if (StringUtil.isNull(param.get("article_id"))) {
			initBaseInfo(article);
			// 保存文档
			articleDao.saveArticle(article);
		} else {
			// 更新文档
			article.setMdf_time(DateUtil.sysTime());
			article.setMdf_user(ThreadLocalContext.getCurUser().getLogin_name());
			articleDao.updateArticle(article);
		}
		List<String> tagIds = saveTag((String) param.get("tag"));
		saveArticleTag(tagIds, article.getId());
		return new ReturnModel(true, "发布成功，请稍后查看");
	}

	/**
	 * 保存标签和文档之间的关系
	 */
	public void saveArticleTag(List<String> tagIds, String article_id) {
		articleDao.delArtcleTagByArticleId(article_id);
		for (String tag_id : tagIds) {
			ArticleTagModel articleTag = new ArticleTagModel();
			articleTag.setArticle_id(article_id);
			articleTag.setTag_id(tag_id);
			initBaseInfo(articleTag);
			articleDao.saveArtcleTag(articleTag);
		}
	}

	/**
	 * 保存标签
	 */
	public List<String> saveTag(String tags) {
		String[] tagArray = tags.split(",");
		List<String> tagIds = new ArrayList<String>();
		for (String tag : tagArray) {
			String isExist = articleDao.isExistTag(tag);
			if (isExist == null) {// 不存在
				TagModel tagModel = new TagModel();
				tagModel.setContent_tag(tag);
				initBaseInfo(tagModel);
				articleDao.saveTag(tagModel);
				tagIds.add(tagModel.getId());
			} else {
				tagIds.add(isExist);
			}
		}
		return tagIds;
	}

	@Override
	public ReturnModel privewArticle(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnModel query(Map<String, Object> param) {
		PageModel page = new PageModel();
		if (!StringUtil.isNull(param.get("cur_page"))) {
			page.setCur_page(Integer.valueOf(StringUtil.getVal(param, "cur_page")));
		}
		if (!StringUtil.isNull(param.get("page_size"))) {
			page.setPage_size(Integer.valueOf(StringUtil.getVal(param, "page_size")));
		}
		param.put("user_id", ThreadLocalContext.getCurUser().getId());
		page = articleDao.queryArticles(param, page);

		List<Map<String, Object>> results = page.getResult();

		for (Map<String, Object> result : results) {
			String is_push = StringUtil.getVal(result, "is_push");
			if ("1".equals(is_push)) {
				result.put("is_push", "已发布");
			}
			if ("2".equals(is_push)) {
				result.put("is_push", "未发布");
			}
		}

		return new ReturnModel(true, "查询成功", page);
	}

	/**
	 * 查询首页显示
	 */
	@Override
	public ReturnModel queryArticle(Map<String, Object> param) {
		PageModel page = new PageModel();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (!StringUtil.isNull(param.get("cur_page"))) {
			page.setCur_page(Integer.valueOf(StringUtil.getVal(param, "cur_page")));
		}
		if (!StringUtil.isNull(param.get("page_size"))) {
			page.setPage_size(Integer.valueOf(StringUtil.getVal(param, "page_size")));
		}
		// 设置不分页
		page.setPage(false);
		param.put("is_push", "1");// 只查询已经发布的
		// param.put("user_id", ThreadLocalContext.getCurUser().getId());
		page = articleDao.queryIndexArticles(param, page);
		returnMap.put("page", page);

		// 查询热门的前10个文章
		List<Map<String, Object>> hotArticles = queryHotArticle();
		returnMap.put("hotArticles", hotArticles);
		// 查询访问量，
		int visit_num = queryVisitNum();
		returnMap.put("visit_num", visit_num);
		// 查询评论数量
		int comment_num = queryCommentNum();
		returnMap.put("comment_num", comment_num);

		// 查询所有的标签
		List<Map<String, Object>> tags = queryTags();
		returnMap.put("tags", tags);
		return new ReturnModel(true, "查询成功", returnMap);
	}

	@Override
	public ReturnModel queryArticleDetail(Map<String, Object> param) {
		Map<String, Object> acticle = articleDao.queryArticle(param);
		List<Map<String, Object>> tags = articleDao.queryTagsByArticleId(StringUtil.getVal(param, "id"));
		StringBuffer tagBuffer = new StringBuffer();
		for (Map<String, Object> tag : tags) {
			tagBuffer.append(StringUtil.getVal(tag, "content_tag") + ",");
		}
		acticle.put("tag", tagBuffer.toString());
		acticle.put("cur_page", StringUtil.getVal(param, "cur_page"));

		if ("true".equals(param.get("is_count"))) {// 是否进行统计
			param.putAll(acticle);
			countArticleNum(param);
			// 查询评论
			List<Map<String, Object>> comments = commentDao.queryCommentByArticleId(StringUtil.getVal(param, "id"));
			acticle.put("comments", comments);
		}


		return new ReturnModel(true, "查询成功", acticle);
	}

	/**
	 * 统计文章的访问次数
	 * 
	 * @param param
	 */
	public void countArticleNum(Map<String, Object> param) {
		String article_ids = StringUtil.getVal(param, "article_ids");
		String id = StringUtil.getVal(param, "id");
		if (article_ids.indexOf(id) == -1) {// 没有进行统计
			int visit_num = Integer.valueOf(StringUtil.getVal(param, "visit_num"));
			visit_num++;
			articleDao.updateVisitNum(visit_num, id);
		}
	}

	@Override
	public ReturnModel delArticle(Map<String, Object> param) {
		String article_id = StringUtil.getVal(param, "article_id");
		int delNum = articleDao.delArticle(article_id);
		if (delNum >= 1) {
			return new ReturnModel(true, "删除成功");
		} else {
			return new ReturnModel(true, "删除成功");
		}
	}

	@Override
	public ReturnModel revokeArticle(Map<String, Object> param) {
		String article_id = StringUtil.getVal(param, "article_id");
		int delNum = articleDao.revokeArticle(article_id);
		if (delNum >= 1) {
			return new ReturnModel(true, "撤销成功");
		} else {
			return new ReturnModel(true, "撤销失败");
		}
	}

	@Override
	public List<Map<String, Object>> queryHotArticle() {
		return articleDao.queryHotArticle();
	}

	@Override
	public int queryVisitNum() {
		return articleDao.countVisitNum();
	}

	@Override
	public int queryCommentNum() {
		return articleDao.countCommentNum();
	}

	/**
	 * 查询所有使用的标签
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryTags() {
		List<Map<String, Object>> resultList = articleDao.queryTags();
		for (int i = 0; i < resultList.size(); i++) {
			int remainder = i % 5;
			resultList.get(i).put("label_color", LABEL_COLOR[remainder]);
		}
		return resultList;
	}

}
