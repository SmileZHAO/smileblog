package com.shxzhlxrr.code.article.model;

import com.shxzhlxrr.spring.config.BaseModel;

/**
 * 标签和文章的关联表
 * 
 * @author zxr
 *
 */
public class ArticleTagModel extends BaseModel {

	private String article_id;

	private String tag_id;

	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}

}
