package org.opencode4workspace.json;

import java.lang.reflect.Type;
import java.util.Date;

import org.opencode4workspace.authentication.AppToken.TokenScope;
import org.opencode4workspace.authentication.AppToken.TokenType;
import org.opencode4workspace.bo.Annotation.AnnotationType;
import org.opencode4workspace.bo.Person.PresenceStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

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
	 *            Class with which to initialise the ResultParser
	 * 
	 * @since 0.5.0
	 */
	public ResultParser(Class<T> clazz) {
		this.clazz = clazz;
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSZ");
		builder.registerTypeAdapter(TokenType.class, new TokenTypeDeserializer());
		builder.registerTypeAdapter(TokenScope[].class, new TokenScopeDeserializer());
		builder.registerTypeAdapter(PresenceStatus.class, new PersonPresenceDeserializer());
		builder.registerTypeAdapter(AnnotationType.class, new AnnotationTypeDeserializer());
		this.gson = builder.create();
	}

	/**
	 * @param clazz
	 *            Class with which to initialise the ResultParser
	 * @param dateFormat
	 *            String dateFormat to deserialise JSON with, currently only accepts "MILIS"
	 * 
	 * @since 0.5.0
	 */
	public ResultParser(Class<T> clazz, String dateFormat) {
		this.clazz = clazz;
		GsonBuilder builder = new GsonBuilder();
		if ("MILIS".equals(dateFormat)) {
			builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
				public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
					return new Date(json.getAsJsonPrimitive().getAsLong());
				}
			});

		} else {
			builder.setDateFormat(dateFormat);
		}
		builder.registerTypeAdapter(TokenType.class, new TokenTypeDeserializer());
		builder.registerTypeAdapter(TokenScope[].class, new TokenScopeDeserializer());
		builder.registerTypeAdapter(PresenceStatus.class, new PersonPresenceDeserializer());
		builder.registerTypeAdapter(AnnotationType.class, new AnnotationTypeDeserializer());
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
