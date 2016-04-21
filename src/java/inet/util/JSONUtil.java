package inet.util;

import java.lang.reflect.Type;

import inet.util.json.Json;
import inet.util.json.reflect.TypeToken;

public class JSONUtil {

	private static Json json = new Json();

	public static String toJSON(Object src) {
		return json.toJson(src);
	}

	public static <T> T fromJson(String src, Class<T> typeOfT) {
		return json.fromJson(src, getType(typeOfT));
	}

	private static <T> Type getType(Class<T> typeOfT) {
		return TypeToken.get(typeOfT).getType();
	}
}
