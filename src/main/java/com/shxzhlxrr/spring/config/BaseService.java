package com.shxzhlxrr.spring.config;

import org.springframework.transaction.annotation.Transactional;

import com.shxzhlxrr.spring.context.ThreadLocalContext;
import com.shxzhlxrr.util.date.DateUtil;
import com.shxzhlxrr.util.uuid.UUidUtil;

@Transactional
public class BaseService {

	/**
	 * 为基本信息赋值
	 * 
	 * @param base
	 */
	public void initBaseInfo(BaseModel base) {
		base.setId(UUidUtil.getId());
		base.setCrt_time(DateUtil.sysTime());
		base.setMdf_time(DateUtil.sysTime());
		base.setCrt_user(ThreadLocalContext.getCurUser().getLogin_name());
		base.setMdf_user(ThreadLocalContext.getCurUser().getLogin_name());
	}

}
