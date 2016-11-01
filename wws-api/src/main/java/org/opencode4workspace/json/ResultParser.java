package org.opencode4workspace.json;

import org.opencode4workspace.authentication.AppToken.TokenScope;
import org.opencode4workspace.authentication.AppToken.TokenType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 *
 * @param <T>
 * 
 *            ResultParser for parsing JSON result into an object
 */
public class ResultParser<T> {

	private final Class<T> clazz;
	private final Gson gson;

	/**
	 * @param clazz
	 *            Class with which to initialitse the ResultParser
	 * 
	 * @since 0.5.0
	 */
	public ResultParser(Class<T> clazz) {
		this.clazz = clazz;
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSZ");
		builder.registerTypeAdapter(TokenType.class, new TokenTypeDeserializer());
		builder.registerTypeAdapter(TokenScope.class, new TokenScopeDeserializer());
		this.gson = builder.create();
	}

	/**
	 * Converts the JSON string passed into an instance of the relevant class
	 * 
	 * @param jsonString
	 *            String, to convert to an object
	 * @return instance of class {@link #clazz}
	 * 
	 * @since 0.5.0
	 */
	public T parse(String jsonString) {
		return gson.fromJson(jsonString, clazz);
	}

}
