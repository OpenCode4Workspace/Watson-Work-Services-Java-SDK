package org.opencode4workspace.json;

import com.google.gson.Gson;

public class RequestBuilder<T> {
	private final Class<T> clazz;
	private final Gson gson = new Gson();
	
	public RequestBuilder(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	public String buildJson(T object) {
		return gson.toJson(object, clazz);
	}
}
