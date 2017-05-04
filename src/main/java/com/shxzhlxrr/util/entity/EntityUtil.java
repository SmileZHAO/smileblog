package com.shxzhlxrr.util.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.shxzhlxrr.util.cla.ClassUtil;
import com.shxzhlxrr.util.string.StringUtil;

/**
 * 实体工具类
 * 
 * @author zxr
 * @date 2016年12月28日 上午10:15:43
 */
public class EntityUtil {
	/**
	 * 将一个map转换成对应的实体类
	 * 
	 * @param map
	 * @param cla
	 * @return
	 */
	public static <T> T getInstance(Map<String, Object> map, Class<T> cla) {
		T t = null;
		try {
			t = cla.newInstance();
			List<Field> fields = ClassUtil.getClaFields(cla);
			for (Field field : fields) {
				String name = field.getName();
				if(map.get(name)!=null){
					Method m = cla.getDeclaredMethod("set" + StringUtil.firstUpper(name), field.getType());
					if (m == null)
						continue;
					m.setAccessible(true);
					m.invoke(t, map.get(name));
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}


}
