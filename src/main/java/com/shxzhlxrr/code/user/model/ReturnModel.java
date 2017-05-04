package com.shxzhlxrr.code.user.model;

/**
 * 返回使用的信息
 * 
 * @author zxr
 * @param
 *
 */
public class ReturnModel {

	private boolean flag;// 是否成功

	private String msg;// 返回的信息

	private Object returnObj; // 用来返回某些特殊对象

	
	
	public ReturnModel() {
	}

	public ReturnModel(boolean flag, String msg, Object returnObj) {
		this.flag = flag;
		this.msg = msg;
		this.returnObj = returnObj;
	}

	public ReturnModel(boolean flag, String msg) {
		this.flag = flag;
		this.msg = msg;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getReturnObj() {
		return returnObj;
	}

	public void setReturnObj(Object returnObj) {
		this.returnObj = returnObj;
	}

}
