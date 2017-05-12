package org.opencode4workspace.json;

import java.lang.reflect.Type;

import org.opencode4workspace.authentication.AppToken.TokenType;
import org.opencode4workspace.bo.Annotation.AnnotationType;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Deserialiser to convert String Annotation Type to Enum
 * 
 * @author Paul Withers
 * 
 * @since 0.7.0
 *
 */
public class AnnotationTypeDeserializer implements JsonDeserializer<AnnotationType> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public AnnotationType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		AnnotationType[] annotationTypes = AnnotationType.values();
		for (AnnotationType annotationType : annotationTypes) {
			if (annotationType.getLabel().equals(json.getAsString())) {
				return annotationType;
			}
		}
		return null;
	}

}
