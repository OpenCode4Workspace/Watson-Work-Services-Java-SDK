package org.opencode4workspace.json;

import java.lang.reflect.Type;

import org.opencode4workspace.authentication.AppToken.TokenType;

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
public class TokenTypeDeserializer implements JsonDeserializer<TokenType> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public TokenType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		TokenType[] tokenTypes = TokenType.values();
		for (TokenType tokenType : tokenTypes) {
			if (tokenType.getValue().equals(json.getAsString())) {
				return tokenType;
			}
		}
		return null;
	}

}
