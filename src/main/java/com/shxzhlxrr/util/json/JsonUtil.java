package com.shxzhlxrr.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author zxr
 */
public class JsonUtil {

	// private static Logger log = Logger.getLogger(JsonUtil.class);

	private static Gson gson = null;

	public static synchronized Gson getGson() {
		if (gson == null) {
			gson = new GsonBuilder().serializeNulls().create();// 解决当map的值是null的时候，不进行输出
		}
		return gson;
	}

	public static <T> T jsonToObj(String json, Class<T> cla) {
		return  getGson().fromJson(json, cla);
	}

	public static String objToJson(Object obj) {
		return getGson().toJson(obj);
	}

//	@SuppressWarnings("unchecked")
//	public static void main(String[] args) {
//		String jsonMap = "{\"name\":\"test\",\"sex\":null,\"age\":\"111\"}";
//		Map<String, Object> map = jsonToObj(jsonMap, Map.class);
//		log.debug(map.get("name"));
//		log.debug(map.get("sex") == null);
//		log.debug(map.get("age"));
//
//		Map<String, Object> hashMap = new HashMap<String, Object>();
//		hashMap.put("salary", "100000.00");
//		hashMap.put("salarys", null);
//
//		String json = objToJson(hashMap);
//
//		log.debug(json);
//
//	}

}
