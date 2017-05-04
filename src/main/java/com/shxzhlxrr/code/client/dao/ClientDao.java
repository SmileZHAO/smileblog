package com.shxzhlxrr.code.client.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shxzhlxrr.code.client.model.VisitUserModel;
import com.shxzhlxrr.spring.config.BaseModel;
import com.shxzhlxrr.spring.data.JdbcDao;
import com.shxzhlxrr.util.date.DateUtil;
import com.shxzhlxrr.util.string.StringUtil;
import com.shxzhlxrr.util.uuid.UUidUtil;

/**
 * 记录客户端的用户信息
 * 
 * @author zxr <br/>
 *         2017年4月26日 下午3:30:22 <br/>
 * @description
 */
@Repository
public class ClientDao {

	@Autowired
	private JdbcDao jdbc;

	public int insertClientInfo(VisitUserModel user) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" insert into t_visit_user (");
		buffer.append(" id , url , local_name, ");
		buffer.append(" ip_address , crt_time , crt_user, ");
		buffer.append("  mdf_time , mdf_user ");
		buffer.append(" ) values (");
		buffer.append(" ? ,? ,? ,? ,? ,? ,? ,? ");
		buffer.append(" ) ");
		initBaseInfo(user);
		List<Object> params = new ArrayList<Object>();
		params.add(user.getId());
		params.add(user.getUrl());
		params.add(user.getLocal_name());
		params.add(user.getIp_address());
		params.add(user.getCrt_time());
		params.add(user.getCrt_user());
		params.add(user.getMdf_time());
		params.add(user.getMdf_user());
		return jdbc.execute(buffer.toString(), params);
	}

	public void initBaseInfo(BaseModel baseModel) {
		baseModel.setCrt_time(DateUtil.sysTime());
		baseModel.setMdf_time(DateUtil.sysTime());
		baseModel.setCrt_user("admin");
		baseModel.setMdf_user("admin");
		if (StringUtil.isNull(baseModel.getId())) {
			baseModel.setId(UUidUtil.getId());
		}
	}

}
