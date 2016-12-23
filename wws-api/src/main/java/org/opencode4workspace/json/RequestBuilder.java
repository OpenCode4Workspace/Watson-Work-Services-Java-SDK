package org.opencode4workspace.json;

import com.google.gson.Gson;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 *
 * @param <T>
 * 
 *            Class for converting GraphQL queries to JSON to pass to POST request
 */
public class RequestBuilder<T> {
	private final Class<T> clazz;
	private final Gson gson = new Gson();

	/**
	 * @param clazz
	 *            Class with which to initialitse the RequestBuilder
	 * 
	 * @since 0.5.0
	 */
	public RequestBuilder(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	/**
	 * Builds JSON string from the object passed
	 * 
	 * @param object
	 *            Object, instance of {@link #clazz}
	 * @return String, JSON conversion of the parameter passed
	 * 
	 * @since 0.5.0
	 */
	public String buildJson(T object) {
		return gson.toJson(object, clazz);
	}
}
