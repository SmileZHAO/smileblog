package com.shxzhlxrr.spring;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
/**
 * 解决编码是乱码的问题</br>
 * spring 默认是使用ISO-8859-1 进行编码的
 * @author zxr
 * 
 */ 
@Component
public class StringHttpMessageConverterImpl extends StringHttpMessageConverter {
	
	public StringHttpMessageConverterImpl(){
//		System.out.println("初始化编码为UTF-8");
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		supportedMediaTypes.add(new MediaType("text", "html", Charset.forName("UTF-8")));
		this.setSupportedMediaTypes(supportedMediaTypes);
	}

}
