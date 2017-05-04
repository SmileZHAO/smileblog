package com.shxzhlxrr.code.article.model;

import com.shxzhlxrr.spring.config.BaseModel;

public class TagModel extends BaseModel {

	private String content_tag;

	public String getContent_tag() {
		return content_tag;
	}

	public void setContent_tag(String content_tag) {
		this.content_tag = content_tag;
	}

	@Override
	public String toString() {
		return "TagModel [content_tag=" + content_tag + ", toString()=" + super.toString() + "]";
	}


}
