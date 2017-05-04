package com.shxzhlxrr.code.article.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shxzhlxrr.code.article.model.ArticleModel;
import com.shxzhlxrr.code.article.model.ArticleTagModel;
import com.shxzhlxrr.code.article.model.TagModel;
import com.shxzhlxrr.spring.config.PageModel;
import com.shxzhlxrr.spring.context.ThreadLocalContext;
import com.shxzhlxrr.spring.data.JdbcDao;
import com.shxzhlxrr.util.date.DateUtil;
import com.shxzhlxrr.util.string.StringUtil;

/**
 * 
 * @author zxr <br/>
 *         2017年4月5日 上午11:19:24 <br/>
 * @description
 */
@Repository
@Transactional
public class ArticleDao {

	@Autowired
	private JdbcDao jdbc;

	/**
	 * 保存文档
	 * 
	 * @param model
	 */
	public int saveArticle(ArticleModel model) {
		String sql = "insert into t_article (id,user_id,title,content_markdown,    "
				+ " content_html,abstract_str,crt_time,mdf_time,crt_user,mdf_user,is_push) "
				+ " values(?,?,?,?,?,?,?,?,?,?,?) ";
		List<Object> param = new ArrayList<Object>();
		param.add(model.getId());
		param.add(model.getUser_id());
		param.add(model.getTitle());
		param.add(model.getContent_markdown());
		param.add(model.getContent_html());
		param.add(model.getAbstract_str());
		param.add(model.getCrt_time());
		param.add(model.getMdf_time());
		param.add(model.getCrt_user());
		param.add(model.getMdf_user());
		param.add(model.getIs_push());

		return jdbc.execute(sql, param);
	}

	/**
	 * 更新文档
	 * 
	 * @param model
	 * @return
	 */
	public int updateArticle(ArticleModel model) {
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" update t_article set ");
		bufferSql.append("	 title = ?, ");
		bufferSql.append("	 content_markdown = ?, ");
		bufferSql.append("	 content_html = ?, ");
		bufferSql.append("	 abstract_str = ?, ");
		bufferSql.append("	 mdf_time = ?, ");
		bufferSql.append("	 mdf_user = ?, ");
		bufferSql.append("	 is_push = ?");
		bufferSql.append(" where id = ? ");

		List<Object> param = new ArrayList<Object>();
		param.add(model.getTitle());
		param.add(model.getContent_markdown());
		param.add(model.getContent_html());
		param.add(model.getAbstract_str());
		param.add(model.getMdf_time());
		param.add(model.getMdf_user());
		param.add(model.getIs_push());
		param.add(model.getId());

		return jdbc.execute(bufferSql.toString(), param);
	}

	/**
	 * 当是编辑保存的时候进行更新 ，去掉了标题和摘要还有是否发布
	 * 
	 * @param model
	 * @return
	 */
	public int updateSaveArticle(ArticleModel model) {
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" update t_article set ");
		bufferSql.append("	 content_markdown = ?, ");
		bufferSql.append("	 content_html = ?, ");
		bufferSql.append("	 mdf_time = ?, ");
		bufferSql.append("	 mdf_user = ?, ");
		bufferSql.append(" where id = ? ");

		List<Object> param = new ArrayList<Object>();
		param.add(model.getContent_markdown());
		param.add(model.getContent_html());
		param.add(model.getMdf_time());
		param.add(model.getMdf_user());
		param.add(model.getId());

		return jdbc.execute(bufferSql.toString(), param);
	}

	/**
	 * 删除文章
	 * 
	 * @param article_id
	 * @return
	 */
	public int delArticle(String article_id) {
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" update t_article set ");
		bufferSql.append("	 is_del = ?, ");
		bufferSql.append("	 mdf_time = ?, ");
		bufferSql.append("	 mdf_user = ? ");
		bufferSql.append(" where id = ? ");

		List<Object> param = new ArrayList<Object>();
		param.add("1");
		param.add(DateUtil.sysTime());
		param.add(ThreadLocalContext.getCurUser().getLogin_name());
		param.add(article_id);

		return jdbc.execute(bufferSql.toString(), param);
	}

	/**
	 * 撤销文章的发布
	 * 
	 * @param article_id
	 * @return
	 */
	public int revokeArticle(String article_id) {
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" update t_article set ");
		bufferSql.append("	 is_push = ?, ");
		bufferSql.append("	 mdf_time = ?, ");
		bufferSql.append("	 mdf_user = ? ");
		bufferSql.append(" where id = ? ");

		List<Object> param = new ArrayList<Object>();
		param.add("2");
		param.add(DateUtil.sysTime());
		param.add(ThreadLocalContext.getCurUser().getLogin_name());
		param.add(article_id);

		return jdbc.execute(bufferSql.toString(), param);
	}

	/*
	 * 保存标签
	 */
	public int saveTag(TagModel tag) {
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" insert into t_tag ( ");
		bufferSql.append("   id ,  ");
		bufferSql.append("   content_tag ,  ");
		bufferSql.append("   crt_time ,  ");
		bufferSql.append("   crt_user ,  ");
		bufferSql.append("   mdf_time ,  ");
		bufferSql.append("   mdf_user   ");
		bufferSql.append(" ) values (  ? ,? , ? , ? , ? , ? )");
		List<Object> param = new ArrayList<Object>();
		param.add(tag.getId());
		param.add(tag.getContent_tag());
		param.add(tag.getCrt_time());
		param.add(tag.getCrt_user());
		param.add(tag.getMdf_time());
		param.add(tag.getMdf_user());
		return jdbc.execute(bufferSql.toString(), param);
	}

	/**
	 * 保存文章和标签之前的关系
	 * 
	 * @param tag
	 * @return
	 */
	public int saveArtcleTag(ArticleTagModel articleTag) {
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" insert into t_article_tag ( ");
		bufferSql.append("   id ,  ");
		bufferSql.append("   article_id ,  ");
		bufferSql.append("   tag_id ,  ");
		bufferSql.append("   crt_time ,  ");
		bufferSql.append("   crt_user ,  ");
		bufferSql.append("   mdf_time ,  ");
		bufferSql.append("   mdf_user   ");
		bufferSql.append(" ) values ( ? , ? , ? , ? , ? , ? , ? )");
		List<Object> param = new ArrayList<Object>();
		param.add(articleTag.getId());
		param.add(articleTag.getArticle_id());
		param.add(articleTag.getTag_id());
		param.add(articleTag.getCrt_time());
		param.add(articleTag.getCrt_user());
		param.add(articleTag.getMdf_time());
		param.add(articleTag.getMdf_user());
		return jdbc.execute(bufferSql.toString(), param);
	}

	/**
	 * 根据文章id删除关联关系
	 * 
	 * @param article_id
	 * @return
	 */
	public int delArtcleTagByArticleId(String article_id) {
		String sql = " delete from  t_article_tag  where article_id = ? ";
		List<Object> param = new ArrayList<Object>();
		param.add(article_id);
		return jdbc.execute(sql, param);
	}

	/**
	 * 根据关联id删除某一个关联关系
	 * 
	 * @param id
	 * @return
	 */
	public int delArtcleTagById(String id) {
		String sql = " delete from  t_article_tag  where id = ? ";
		List<Object> param = new ArrayList<Object>();
		param.add(id);
		return jdbc.execute(sql, param);
	}

	/**
	 * 是否存在该标签，存在的话返回标签的id
	 * 
	 * @param content_tag
	 * @return
	 */
	public String isExistTag(String content_tag) {
		String sql = " select id from  t_tag  where content_tag = ? ";
		List<Object> param = new ArrayList<Object>();
		param.add(content_tag);
		Map<String, Object> result = jdbc.query(sql, param);
		if (result.isEmpty()) {
			return null;
		}
		return (String) result.get("id");
	}

	/**
	 * 根据文章id 查询对应的tag
	 * 
	 * @param articleId
	 * @return
	 */
	public List<Map<String, Object>> queryTagsByArticleId(String article_id) {
		String sql = "select tag.* from t_tag tag,t_article_tag ari_tag where tag.id = ari_tag.tag_id and ari_tag.article_id = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(article_id);
		List<Map<String, Object>> result = jdbc.queryList(sql, param);
		return result;
	}

	/**
	 * 查询文章
	 * 
	 * @return
	 */
	public PageModel queryArticles(Map<String, Object> params, PageModel page) {
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" select t.title, ");
		bufferSql.append(" 		  t.id, ");
		// bufferSql.append(" t.content_markdown, ");
		// bufferSql.append(" t.content_html, ");
		bufferSql.append(" 		  t.abstract_str, ");
		bufferSql.append(" 		  t.is_push, ");
		bufferSql.append(" 		  t.crt_user, ");
		bufferSql.append(" 		  t.crt_time, ");
		bufferSql.append(" 		  t.mdf_time, ");
		bufferSql.append(" 		  t.mdf_user ");
		bufferSql.append(" from t_article t  ");
		bufferSql.append(" where t.is_del = ?   ");

		List<Object> param = new ArrayList<Object>();
		param.add("2");
		// 查询条件
		if (!StringUtil.isNull(params.get("user_id"))) {
			bufferSql.append(" and t.user_id = ?   ");
			param.add(StringUtil.getVal(params, "user_id"));
		}
		if (!StringUtil.isNull(params.get("article_id"))) {
			bufferSql.append(" and t.id = ?  ");
			param.add(StringUtil.getVal(params, "article_id"));
		}
		if (!StringUtil.isNull(params.get("is_push"))) {
			bufferSql.append(" and t.is_push = ?  ");
			param.add(StringUtil.getVal(params, "is_push"));
		}
		if (!StringUtil.isNull(params.get("title"))) {
			bufferSql.append(" and t.title like  ?  ");
			param.add("%" + StringUtil.getVal(params, "title") + "%");
		}
		
		bufferSql.append(" order by t.mdf_time desc ");
		page = jdbc.queryListByPage(bufferSql.toString(), param, page);
		return page;
	}

	/**
	 * 查询首页显示文章
	 * 
	 * @param params
	 * @param page
	 * @return
	 */
	public PageModel queryIndexArticles(Map<String, Object> params, PageModel page) {
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" select t.title, ");
		bufferSql.append(" 		  t.id, ");
		bufferSql.append(" 		  t.abstract_str, ");
		bufferSql.append(" 		  t.is_push, ");
		bufferSql.append(" 		  u.name, ");
		bufferSql.append(" 		  t.visit_num, ");
		bufferSql.append(" 		  t.comment_num, ");
		bufferSql.append(" 		  t.crt_user, ");
		bufferSql.append(" 		  t.crt_time, ");
		bufferSql.append(" 		  t.mdf_time, ");
		bufferSql.append(" 		  t.mdf_user ");
		bufferSql.append(" from t_article t ,t_user u ");
		bufferSql.append(" where t.user_id = u.id and t.is_del = ?  ");

		List<Object> param = new ArrayList<Object>();
		param.add("2");
		// 查询条件
		if (!StringUtil.isNull(params.get("is_push"))) {
			bufferSql.append(" and t.is_push = ?  ");
			param.add(StringUtil.getVal(params, "is_push"));
		}

		if (!StringUtil.isNull(params.get("tag_id"))) {
			bufferSql.append(" and t.id in (select article_id from t_article_tag where tag_id = ? ) ");
			param.add(StringUtil.getVal(params, "tag_id"));
		}

		bufferSql.append(" order by t.crt_time desc ");

		page = jdbc.queryListByPage(bufferSql.toString(), param, page);

		return page;
	}

	/**
	 * 根据文章id查询单个文档
	 * 
	 * @param article_id
	 * @return
	 */
	public Map<String, Object> queryArticle(Map<String, Object> param) {
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" select t.title, ");
		bufferSql.append(" 		  t.id, ");
		bufferSql.append(" 		  t.content_markdown, ");
		bufferSql.append("        t.content_html, ");
		bufferSql.append(" 		  t.abstract_str, ");
		bufferSql.append(" 		  t.is_push, ");
		bufferSql.append(" 		  t.crt_user, ");
		bufferSql.append(" 		  t.crt_time, ");
		bufferSql.append(" 		  t.mdf_time, ");
		bufferSql.append(" 		  t.visit_num, ");// 文章的访问次数
		bufferSql.append(" 		  t.comment_num, ");// 文章的评论次数
		bufferSql.append("        t.mdf_user ");
		bufferSql.append(" from t_article t  ");
		bufferSql.append(" where t.id = ?    ");
		List<Object> params = new ArrayList<Object>();
		params.add(param.get("id"));
		if (!StringUtil.isNull(param.get("is_push"))) {
			bufferSql.append(" and t.is_push = ? ");
			params.add(param.get("is_push"));
		}
		return jdbc.query(bufferSql.toString(), params);
	}

	/**
	 * 查询热门的文章
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryHotArticle() {
		String sql = "select t.title,t.comment_num,t.visit_num,t.id from t_article t where t.is_push=? order by t.visit_num desc";
		PageModel page = new PageModel();
		page.setPage_size(10);
		List<Object> params = new ArrayList<Object>();
		params.add("1");
		page = jdbc.queryListByPage(sql, params, page);
		return page.getResult();
	}

	/**
	 * 更新文章的访问次数
	 * 
	 * @param visit_num
	 * @param id
	 */
	public int updateVisitNum(int visit_num, String id) {
		String sql = "update t_article set visit_num = ?,mdf_time = ?,mdf_user = ? where id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(visit_num);
		params.add(DateUtil.sysTime());
		params.add("admin");
		params.add(id);
		return jdbc.execute(sql, params);
	}

	public int countVisitNum() {
		String sql = "select count(id) as num  from t_visit_user ";
		List<Object> params = new ArrayList<Object>();
		Map<String, Object> resultMap = jdbc.query(sql, params);
		String num_str = StringUtil.getVal(resultMap, "num");
		int num = Integer.valueOf(num_str);
		return num;
	}

	public int countCommentNum() {
		String sql = "select count(id) as num  from t_comment ";
		List<Object> params = new ArrayList<Object>();
		Map<String, Object> resultMap = jdbc.query(sql, params);
		String num_str = StringUtil.getVal(resultMap, "num");
		int num = Integer.valueOf(num_str);
		return num;
	}

	/**
	 * 查询所有的有效标签 对应的文章没有发布或者已经删除的不进行显示
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryTags() {
		String sql = "select distinct t.id ,t.content_tag from t_article_tag a_t ,t_tag t,t_article a "
				+ " where a_t.tag_id = t.id and a.id = a_t.article_id and a.is_push = ? and a.is_del = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add("1");// 已经发布的
		params.add("2");// 没有删除的
		return jdbc.queryList(sql, params);
	}

	/**
	 * 更新评论次数
	 * 
	 * @param comment_num
	 * @param id
	 * @return
	 */
	public int updateCommentNum(int comment_num, String id) {
		String sql = "update t_article set comment_num = ?,mdf_time = ?,mdf_user = ? where id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(comment_num);
		params.add(DateUtil.sysTime());
		params.add("admin");
		params.add(id);
		return jdbc.execute(sql, params);
	}

}
