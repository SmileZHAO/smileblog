package com.shxzhlxrr.code.article.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.shxzhlxrr.code.article.dao.ImgDao;
import com.shxzhlxrr.code.article.model.ImgModel;
import com.shxzhlxrr.code.article.service.IImgService;
import com.shxzhlxrr.code.user.model.ReturnModel;
import com.shxzhlxrr.spring.config.BaseService;
import com.shxzhlxrr.util.date.DateUtil;
import com.shxzhlxrr.util.file.FileUtil;
import com.shxzhlxrr.util.log.LogUtil;
import com.shxzhlxrr.util.uuid.UUidUtil;


/**
 * 图片处理
 * 
 * @author zxr <br/>
 *         2017年4月14日 下午5:22:06 <br/>
 * @description
 */
@Service
@Transactional
public class ImgService extends BaseService implements IImgService {
	@Autowired
	private ImgDao imgDao;

	@Value("${file.img_path}")
	private String imgPath;// 图片存放目录
	@Value("${file.img_size}")
	private int img_size;

	@Override
	public ReturnModel uploadImg(StandardMultipartHttpServletRequest multipartReq)
			throws IllegalStateException, IOException {
		String path = imgPath + DateUtil.DateToString("/yyyy/MM/dd/");
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
		Map<String, MultipartFile> fileMaps = multipartReq.getFileMap();
		StringBuffer buffer = new StringBuffer();
		for (String key : fileMaps.keySet()) {
			MultipartFile multipartFile = fileMaps.get(key);
			ImgModel imgModel = new ImgModel();
			imgModel.setId(UUidUtil.getId());
			imgModel.setOld_name(multipartFile.getOriginalFilename());
			imgModel.setImg_suffix(FileUtil.getSuffix(imgModel.getOld_name()));
			imgModel.setImg_name(imgModel.getId() + "." + imgModel.getImg_suffix());
			imgModel.setImg_path(path + imgModel.getImg_name());
			multipartFile.transferTo(new File(imgModel.getImg_path()));// 写入磁盘
			imgDao.saveImg(imgModel);
			LogUtil.info("插入图片成功：" + imgModel.toString());
			buffer.append(imgModel.getId() + ",");
		}
		return new ReturnModel(true, "插入成功", buffer.toString());
	}

	@Override
	public ReturnModel downloadImg(String id) {
		ImgModel model = imgDao.getImgModelById(id);
		return new ReturnModel(true, "查询成功", model);
	}

}
