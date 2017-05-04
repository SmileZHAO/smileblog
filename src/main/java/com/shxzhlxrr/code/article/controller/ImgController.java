package com.shxzhlxrr.code.article.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.shxzhlxrr.code.article.config.ImgConfig;
import com.shxzhlxrr.code.article.model.ImgModel;
import com.shxzhlxrr.code.article.service.IImgService;
import com.shxzhlxrr.code.user.model.ReturnModel;
import com.shxzhlxrr.spring.config.BaseController;

/**
 * 图片上传控制器
 * 
 * @author zxr <br/>
 *         2017年4月13日 上午11:07:16 <br/>
 * @description
 */
@Controller
public class ImgController extends BaseController {

	@Autowired
	private IImgService imgService;

	/**
	 * 上传图片
	 * 
	 * @param res
	 * @param req
	 * @param map
	 */
	@RequestMapping("/uploadImg.do")
	public void uploadImg(HttpServletResponse res, HttpServletRequest req) {
		try {
			StandardMultipartHttpServletRequest multipartReq = (StandardMultipartHttpServletRequest) req;
			ReturnModel model = imgService.uploadImg(multipartReq);
			returnMsg(res, model);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 下载图片
	 * 
	 * @param res
	 * @param req
	 */
	@RequestMapping("/article/downloadImg.do")
	public void downloadImg(HttpServletResponse res, HttpServletRequest req) {
		try {
			String id = req.getParameter("id");
			ReturnModel model = imgService.downloadImg(id);
			ImgModel imgModel = (ImgModel) model.getReturnObj();
			returnImg(res, imgModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void returnImg(HttpServletResponse res, ImgModel imgModel) throws Exception {
		res.setContentType(ImgConfig.imgType.get(imgModel.getImg_suffix().toLowerCase()));
		OutputStream out = res.getOutputStream();
		File file = new File(imgModel.getImg_path());
		InputStream in = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		try {
			while (in.read(buffer) != -1) {
				out.write(buffer);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}

	}

}
