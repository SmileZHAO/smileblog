package com.shxzhlxrr.spring.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shxzhlxrr.spring.config.BaseModel;
import com.shxzhlxrr.spring.config.PageModel;
import com.shxzhlxrr.spring.context.ThreadLocalContext;
import com.shxzhlxrr.util.date.DateUtil;
import com.shxzhlxrr.util.log.LogUtil;
import com.shxzhlxrr.util.string.StringUtil;
import com.shxzhlxrr.util.uuid.UUidUtil;

@Repository
@Transactional
public class JdbcDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	
	public List<Map<String, Object>> queryList(final String sql,final List<Object> params) {
		initSql(sql,params);
		if(sql.toLowerCase().contains("insert")||sql.toLowerCase().contains("update")){
			throw new RuntimeException("查询sql中包含了inser或者update，请使用对应的插入或者更新方法");
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		resultList = jdbc.execute(sql, new PreparedStatementCallback<List<Map<String, Object>>>() {
			@Override
			public List<Map<String, Object>> doInPreparedStatement(PreparedStatement statement)
					throws SQLException, DataAccessException {
				LogUtil.info("执行sql:" + sql);
				LogUtil.info("参数:" + params);
				if (params != null) {
					for (int i = 1; i <= params.size(); i++) {
						statement.setObject(i, params.get(i - 1));
					}
				}
				statement.execute();
				List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
				ResultSet resultSet = statement.getResultSet();
				ResultSetMetaData rsmd = resultSet.getMetaData();
				int columnCount = rsmd.getColumnCount();

				while (resultSet.next()) {
					Map<String, Object> map = new HashMap<String, Object>();
					for (int i = 1; i <= columnCount; i++) {
						Object val = resultSet.getObject(i);
						map.put(rsmd.getColumnName(i).toLowerCase(), resultSet.getObject(i));
						if (val instanceof java.sql.Timestamp) {
							map.put(rsmd.getColumnName(i).toLowerCase() + "_str",
									DateUtil.TimeToString((Timestamp) val));
						}
					}
					result.add(map);
				}
				LogUtil.info("查询的结果:" + result);
				return result;
			}

		});
		return resultList;
		
	}
	
	@Transactional
	public int execute(final String sql,final List<Object> params) {
		initSql(sql,params);
		// if(sql.toLowerCase().contains("update")){
		// throw new RuntimeException("查询sql中包含了update，请使用对应的更新方法");
		// }
		Integer num = jdbc.execute(sql, new PreparedStatementCallback<Integer>() {
			@Override
			public Integer doInPreparedStatement(PreparedStatement statement)
					throws SQLException, DataAccessException {
				LogUtil.info("执行sql:" + sql);
				LogUtil.info("参数:" + params);
				if (params != null) {
					for (int i = 1; i <= params.size(); i++) {
						statement.setObject(i, params.get(i - 1));
					}
				}
				return statement.executeUpdate();
			}
			
		});
		LogUtil.info("执行结果：更新或插入了" + num + "行");
		return num;
		
	}
	
	public int insert(String sql,List<Object> params){
		return execute(sql, params);
	}
	
	public int update(String sql,List<Object> params){
		return execute(sql, params);
	}
	
	private void initSql(String sql,List<Object> params){
		if(sql.contains("'")){
			throw new RuntimeException("sql中包含非法字符，请检查sql");
		}
	}
	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public Map<String, Object> query(String sql,List<Object> params) {
		List<Map<String,Object>> result = queryList(sql,params);
		if(result.size()>0){
			return result.get(0);
		}
		return new HashMap<String,Object>();
	}

	/**
	 * 分页查询
	 * 
	 * @param sql
	 * @param params
	 * @param page
	 * @return
	 */
	public PageModel queryListByPage(String sql, List<Object> params, PageModel page) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (params == null) {
			params = new ArrayList<Object>();
		}
		if (page.isPage()) {
			// 查询总的记录数量
			String countSql = "select count(*) as num from (" + sql + ") count ";
			Map<String, Object> resultMap = this.query(countSql, params);
			page.setCount(Integer.valueOf(StringUtil.getVal(resultMap, "num")));

			// 分页查询
			String pageSql = "select * from (" + sql + ") page limit ?,?";
			params.add(page.getData_num());
			params.add(page.getPage_size());
			resultList = this.queryList(pageSql, params);
		} else {
			resultList = this.queryList(sql, params);
			page.setCount(resultList.size());
		}
		page.setResult(resultList);
		return page;
	}

	/**
	 * 初始化基本信息<br/>
	 * 
	 * 当没有用户信息的时候默认使用"admin"
	 * 
	 * @param baseModel
	 */
	public void initBaseInfo(BaseModel baseModel) {
		baseModel.setCrt_time(DateUtil.sysTime());
		baseModel.setMdf_time(DateUtil.sysTime());
		String user_name = "admin";
		if (ThreadLocalContext.getCurUser() != null) {
			user_name = ThreadLocalContext.getCurUser().getLogin_name();
		}
		baseModel.setCrt_user(user_name);
		baseModel.setMdf_user(user_name);
		if (StringUtil.isNull(baseModel.getId())) {
			baseModel.setId(UUidUtil.getId());
		}
	}

	
}
