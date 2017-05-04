package com.shxzhlxrr.spring.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author zxr <br/>
 *         2017年4月6日 下午1:01:35 <br/>
 * @description 一个分页的model
 */
public class PageModel {

	private int cur_page = 1;// 当前页面

	private int page_num = 0;// 总的页面个数

	private int page_size = 10;// 页面显示的个数大小

	private int count = 0;// 总共有多少条记录

	private int data_num = 0;// 表示从第几行开始取值

	private List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

	private boolean isPage = true;

	public int getCur_page() {
		return cur_page;
	}

	public void setCur_page(int cur_page) {
		this.cur_page = cur_page;
		this.data_num = (cur_page - 1) * page_size;
	}

	public int getPage_num() {
		return page_num;
	}

	public void setPage_num(int page_num) {
		this.page_num = page_num;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
		this.page_num = count / page_size;
		if (count % page_size != 0) {
			this.page_num++;
		}
	}

	public int getData_num() {
		return data_num;
	}

	public void setData_num(int data_num) {
		this.data_num = data_num;
	}

	public List<Map<String, Object>> getResult() {
		return result;
	}

	public void setResult(List<Map<String, Object>> result) {
		this.result = result;
	}

	public boolean isPage() {
		return isPage;
	}

	public void setPage(boolean isPage) {
		this.isPage = isPage;
	}

}
