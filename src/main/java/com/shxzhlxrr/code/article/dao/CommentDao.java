package com.shxzhlxrr.code.article.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shxzhlxrr.code.article.model.CommentModel;
import com.shxzhlxrr.code.article.model.CommentUserModel;
import com.shxzhlxrr.spring.config.BaseModel;
import com.shxzhlxrr.spring.data.JdbcDao;
import com.shxzhlxrr.util.date.DateUtil;
import com.shxzhlxrr.util.string.StringUtil;
import com.shxzhlxrr.util.uuid.UUidUtil;

/**
 * 评论dao
 * 
 * @author zxr <br/>
 *         2017年5月3日 下午2:18:06 <br/>
 * @description
 */
@Repository
@Transactional
public class CommentDao {

	@Autowired
	private JdbcDao jdbc;

	/**
	 * 保存评论用户信息
	 * 
	 * @param user
	 * @return
	 */
	public int saveCommentUser(CommentUserModel user) {
		initBaseInfo(user);
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" insert into t_comment_user ( ");
		bufferSql.append("   id ,  ");
		bufferSql.append("   user_name ,  ");
		bufferSql.append("   email ,  ");
		bufferSql.append("   password ,  ");
		bufferSql.append("   crt_time ,  ");
		bufferSql.append("   crt_user ,  ");
		bufferSql.append("   mdf_time ,  ");
		bufferSql.append("   mdf_user   ");
		bufferSql.append(" ) values (  ? ,? , ? , ? , ? , ? , ? , ? )");
		List<Object> param = new ArrayList<Object>();
		param.add(user.getId());
		param.add(user.getUser_name());
		param.add(user.getEmail());
		param.add(user.getPassword());
		param.add(user.getCrt_time());
		param.add(user.getCrt_user());
		param.add(user.getMdf_time());
		param.add(user.getMdf_user());
		return jdbc.execute(bufferSql.toString(), param);
	}

	/**
	 * 保存用户的评论
	 * 
	 * @param comment
	 * @return
	 */
	public int saveComment(CommentModel comment) {
		initBaseInfo(comment);
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" insert into t_comment ( ");
		bufferSql.append("   id ,  ");
		bufferSql.append("   article_id ,  ");
		bufferSql.append("   user_id ,  ");
		bufferSql.append("   comment_content ,  ");
		bufferSql.append("   crt_time ,  ");
		bufferSql.append("   crt_user ,  ");
		bufferSql.append("   mdf_time ,  ");
		bufferSql.append("   mdf_user   ");
		bufferSql.append(" ) values (  ? ,? , ? , ? , ? , ? , ? , ? )");
		List<Object> param = new ArrayList<Object>();
		param.add(comment.getId());
		param.add(comment.getArticle_id());
		param.add(comment.getUser_id());
		param.add(comment.getComment_content());
		param.add(comment.getCrt_time());
		param.add(comment.getCrt_user());
		param.add(comment.getMdf_time());
		param.add(comment.getMdf_user());
		return jdbc.execute(bufferSql.toString(), param);
	}

	/**
	 * 查询用户的评论信息<br/>
	 * 倒叙显示
	 * 
	 * @param article_id
	 * @return
	 */
	public List<Map<String, Object>> queryCommentByArticleId(String article_id) {
		String sql = " select c.comment_content, u.user_name,c.crt_time from t_comment c ,"
				+ " t_comment_user u where c.article_id = ? and c.user_id = u.id  order by c.crt_time desc ";
		List<Object> params = new ArrayList<Object>();
		params.add(article_id);
		return jdbc.queryList(sql, params);
	}

	/**
	 * 根据用户名查询用户
	 * 
	 * @param user
	 * @return
	 */
	public Map<String, Object> queryCommentUserByName(String user_name) {
		String sql = " select * from t_comment_user where user_name = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(user_name);
		return jdbc.query(sql, params);
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
