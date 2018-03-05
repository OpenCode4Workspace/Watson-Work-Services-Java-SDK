package org.opencode4workspace.json;

import java.lang.reflect.Type;

import org.opencode4workspace.authentication.AppToken.TokenType;
import org.opencode4workspace.bo.Focus.Lens;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Deserialiser to convert String token type to Enum
 * 
 * @author Christian Guedemann
 * 
 * @since 0.5.0
 *
 */
public class LensDeserializer implements JsonDeserializer<Lens> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public Lens deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Lens[] lenses = Lens.values();
		for (Lens lens : lenses) {
			if (lens.getValue().equals(json.getAsString())) {
				return lens;
			}
		}
		return null;
	}

}
