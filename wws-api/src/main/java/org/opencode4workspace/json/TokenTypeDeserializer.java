package org.opencode4workspace.json;

import java.lang.reflect.Type;

import org.opencode4workspace.authentication.AppToken.TokenType;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class TokenTypeDeserializer implements JsonDeserializer<TokenType> {

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
