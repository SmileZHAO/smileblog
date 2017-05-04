package com.shxzhlxrr.util.markdown;

import java.io.IOException;

import org.markdown4j.Markdown4jProcessor;

/**
 * markdown 工具类，提供markdown转换html工具
 * 
 * @author zxr
 *
 */
public class Markdown {

	public String toHtml(String markdown) throws IOException {
		return new Markdown4jProcessor().process(markdown);
	}

}
