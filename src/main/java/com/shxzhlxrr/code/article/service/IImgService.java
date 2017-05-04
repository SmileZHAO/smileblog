package com.shxzhlxrr.code.article.service;

import java.io.IOException;

import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.shxzhlxrr.code.user.model.ReturnModel;

/**
 * 
 * @author zxr <br/>
 *         2017年4月14日 下午5:17:37 <br/>
 * @description 图片控制服务
 */
public interface IImgService {
	/**
	 * 上传图片
	 * 
	 * @param multipartReq
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public ReturnModel uploadImg(StandardMultipartHttpServletRequest multipartReq)
			throws IllegalStateException, IOException;

	/**
	 * 下载图片
	 * 
	 * @return
	 */
	public ReturnModel downloadImg(String id);

}
