package com.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

public final class JsonUtils {
	/** ObjectMapper */
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * ����ʵ����
	 */
	private JsonUtils() {
	}

	/**
	 * ������ת��ΪJSON�ַ���
	 * 
	 * @param value
	 *            ����
	 * @return JSOn�ַ���
	 */
	public static String toJson(Object value) {
		try {
			return mapper.writeValueAsString(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��JSON�ַ���ת��Ϊ����
	 * 
	 * @param json
	 *            JSON�ַ���
	 * @param valueType
	 *            ��������
	 * @return ����
	 */
	public static <T> T toObject(String json, Class<T> valueType) {
		Assert.hasText(json);
		Assert.notNull(valueType);
		try {
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			return mapper.readValue(json, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��JSON�ַ���ת��Ϊ����
	 * 
	 * @param json
	 *            JSON�ַ���
	 * @param typeReference
	 *            ��������
	 * @return ����
	 */
	public static <T> T toObject(String json, TypeReference<?> typeReference) {
		Assert.hasText(json);
		Assert.notNull(typeReference);
		try {
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			return mapper.readValue(json, typeReference);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��JSON�ַ���ת��Ϊ����
	 * 
	 * @param json
	 *            JSON�ַ���
	 * @param javaType
	 *            ��������
	 * @return ����
	 */
	public static <T> T toObject(String json, JavaType javaType) {
		Assert.hasText(json);
		Assert.notNull(javaType);
		try {
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			return mapper.readValue(json, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ������ת��ΪJSON��
	 * 
	 * @param writer
	 *            writer
	 * @param value
	 *            ����
	 */
	public static void writeValue(Writer writer, Object value) {
		try {
			mapper.writeValue(writer, value);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// ��������
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// ����ڲ㻹������Ļ�����������
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	/**
	 * ������ת��ΪJSON�ַ���
	 * 
	 * @param value
	 *            ����
	 * @return JSOn�ַ���
	 */
	public static String objectToJson(Object value) {
		JSONObject json = JSONObject.fromObject(value);
		return json.toString();
	}

	/**
	 * ��JSON�ַ���ת��Ϊ����
	 * 
	 * @param json
	 *            JSON�ַ���
	 * @param clazz
	 *            ��������
	 * @return ����
	 */
	public static Object jsonToObject(String json, Class clazz) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		return JSONObject.toBean(jsonObject, clazz);
	}
}
