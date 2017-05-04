package com.shxzhlxrr.code.article.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shxzhlxrr.code.article.model.ImgModel;
import com.shxzhlxrr.spring.data.JdbcDao;
import com.shxzhlxrr.util.cla.ClassUtil;

/**
 * 
 * @author zxr <br/>
 *         2017年4月14日 下午6:18:22 <br/>
 * @description
 */
@Repository
@Transactional
public class ImgDao {

	@Autowired
	private JdbcDao jdbc;

	/**
	 * 保存图片
	 * 
	 * @param imgModel
	 * @return
	 */
	public int saveImg(ImgModel imgModel) {
		jdbc.initBaseInfo(imgModel);
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" insert into t_img ( ");
		bufferSql.append("   id ,  ");
		bufferSql.append("   old_name ,  ");
		bufferSql.append("   img_name ,  ");
		bufferSql.append("   img_path ,  ");
		bufferSql.append("   img_suffix ,  ");
		bufferSql.append("   crt_time ,  ");
		bufferSql.append("   crt_user ,  ");
		bufferSql.append("   mdf_time ,  ");
		bufferSql.append("   mdf_user   ");
		bufferSql.append(" ) values ( ? , ? , ? , ? , ? , ? ,? ,? , ? )");
		List<Object> param = new ArrayList<Object>();
		param.add(imgModel.getId());
		param.add(imgModel.getOld_name());
		param.add(imgModel.getImg_name());
		param.add(imgModel.getImg_path());
		param.add(imgModel.getImg_suffix());
		param.add(imgModel.getCrt_time());
		param.add(imgModel.getCrt_user());
		param.add(imgModel.getMdf_time());
		param.add(imgModel.getMdf_user());
		return jdbc.execute(bufferSql.toString(), param);
	}

	public ImgModel getImgModelById(String id) {
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" select * from t_img where id = ? ");
		List<Object> param = new ArrayList<Object>();
		param.add(id);
		Map<String, Object> result = jdbc.query(bufferSql.toString(), param);
		ImgModel imgModel = ClassUtil.mapToObjFiled(result, new ImgModel());
		return imgModel;
	}

}
