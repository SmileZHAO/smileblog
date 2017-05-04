package com.shxzhlxrr.code.article.model;

import com.shxzhlxrr.spring.config.BaseModel;

/**
 * @author zxr <br/>
 *         2017年4月14日 下午5:01:29 <br/>
 * @description
 */
public class ImgModel extends BaseModel {

	private String old_name;// 图片原始的名字

	private String img_name;// 图片现在的名字

	private String img_suffix;// 图片后缀

	private String img_path;// 图片存放路径

	public String getOld_name() {
		return old_name;
	}

	public void setOld_name(String old_name) {
		this.old_name = old_name;
	}

	public String getImg_name() {
		return img_name;
	}

	public void setImg_name(String img_name) {
		this.img_name = img_name;
	}

	public String getImg_suffix() {
		return img_suffix;
	}

	public void setImg_suffix(String img_suffix) {
		this.img_suffix = img_suffix;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	@Override
	public String toString() {
		return "ImgModel [old_name=" + old_name + ", img_name=" + img_name + ", img_suffix=" + img_suffix
				+ ", img_path=" + img_path + ", toString()=" + super.toString() + "]";
	}

}
