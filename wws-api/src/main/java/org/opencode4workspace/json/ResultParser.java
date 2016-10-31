package org.opencode4workspace.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResultParser<T> {

	private final Class<T> clazz;
	private final Gson gson;

	public ResultParser(Class<T> clazz) {
		this.clazz = clazz;
		this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSZ").create();
	}

	public T parse(String jsonString) {
		return gson.fromJson(jsonString, clazz);
	}

}
