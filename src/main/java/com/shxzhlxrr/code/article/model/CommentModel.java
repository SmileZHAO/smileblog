package com.shxzhlxrr.code.article.model;

import com.shxzhlxrr.spring.config.BaseModel;

/**
 * 评论信息
 * 
 * @author zxr <br/>
 *         2017年5月3日 下午3:45:30 <br/>
 * @description
 */
public class CommentModel extends BaseModel {

	private String article_id;
	private String user_id;// 评论用户的信息
	private String comment_content;

	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	@Override
	public String toString() {
		return "CommentModel [article_id=" + article_id + ", user_id=" + user_id + ", comment_content="
				+ comment_content + ", toString()=" + super.toString() + "]";
	}

}
