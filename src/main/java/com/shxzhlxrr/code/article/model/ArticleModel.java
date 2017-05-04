package com.shxzhlxrr.code.article.model;

import com.shxzhlxrr.spring.config.BaseModel;

public class ArticleModel extends BaseModel {
	private String user_id;
	private String title;
	private String content_markdown;
	private String content_html;
	private String abstract_str;
	private String is_push;
	private String is_del;
	private int visit_num;
	private int comment_num;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent_markdown() {
		return content_markdown;
	}

	public void setContent_markdown(String content_markdown) {
		this.content_markdown = content_markdown;
	}

	public String getContent_html() {
		return content_html;
	}

	public void setContent_html(String content_html) {
		this.content_html = content_html;
	}

	public String getAbstract_str() {
		return abstract_str;
	}

	public void setAbstract_str(String abstract_str) {
		this.abstract_str = abstract_str;
	}

	public String getIs_push() {
		return is_push;
	}

	public void setIs_push(String is_push) {
		this.is_push = is_push;
	}

	public String getIs_del() {
		return is_del;
	}

	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}

	public int getVisit_num() {
		return visit_num;
	}

	public void setVisit_num(int visit_num) {
		this.visit_num = visit_num;
	}

	public int getComment_num() {
		return comment_num;
	}

	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}

	@Override
	public String toString() {
		return "ArticleModel [user_id=" + user_id + ", title=" + title + ", content_markdown=" + content_markdown
				+ ", content_html=" + content_html + ", abstract_str=" + abstract_str + ", is_push=" + is_push
				+ ", is_del=" + is_del + ", visit_num=" + visit_num + ", comment_num=" + comment_num + "]";
	}
	
	
}
