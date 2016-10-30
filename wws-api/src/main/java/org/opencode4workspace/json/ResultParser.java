package org.opencode4workspace.json;

import org.opencode4workspace.authentication.AppToken;

import com.google.gson.Gson;

public class ResultParser<T> {

	private final Class<T> clazz;
	private final Gson gson;

	public ResultParser(Class<T> clazz) {
		this.clazz = clazz;
		this.gson = new Gson();
	}

	public T parse(String appTokenResult) {
		return gson.fromJson(appTokenResult, clazz);
	}

}
