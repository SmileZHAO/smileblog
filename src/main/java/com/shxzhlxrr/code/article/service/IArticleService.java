package com.shxzhlxrr.code.article.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.shxzhlxrr.code.user.model.ReturnModel;

public interface IArticleService {
	/**
	 * 保存文档
	 */
	public ReturnModel saveArticle(Map<String, Object> param) throws IOException;

	/**
	 * 发布文档
	 * 
	 * @param param
	 * @return
	 * @throws IOException
	 */
	public ReturnModel pushArticle(Map<String, Object> param) throws IOException;

	/**
	 * 预览文章
	 * 
	 * @param param
	 * @return
	 */
	public ReturnModel privewArticle(Map<String, Object> param);


	/**
	 * 查询文章
	 * 
	 * @param param
	 * @return
	 */
	public ReturnModel query(Map<String, Object> param);

	/**
	 * 查询首页显示
	 * 
	 * @param param
	 * @return
	 */
	public ReturnModel queryArticle(Map<String, Object> param);

	/**
	 * 查询网页详情
	 * 
	 * @param param
	 * @return
	 */
	public ReturnModel queryArticleDetail(Map<String, Object> param);

	/**
	 * 删除文章
	 * 
	 * @param param
	 * @return
	 */
	public ReturnModel delArticle(Map<String, Object> param);

	/**
	 * 撤销文章
	 * 
	 * @param param
	 * @return
	 */
	public ReturnModel revokeArticle(Map<String, Object> param);

	/**
	 * 查询热门的文章
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryHotArticle();

	/**
	 * 查询访问数量
	 * 
	 * @return
	 */
	public int queryVisitNum();

	/**
	 * 查询评论数量
	 * 
	 * @return
	 */
	public int queryCommentNum();

}
