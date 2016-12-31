package org.opencode4workspace.json;

import java.lang.reflect.Type;

import org.opencode4workspace.authentication.AppToken.TokenScope;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Deserialiser to convert String token scope to Enum
 * 
 * @author Christian Guedemann
 * 
 * @since 0.5.0
 *
 */
public class TokenScopeDeserializer implements JsonDeserializer<TokenScope> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public TokenScope deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		TokenScope[] tokenScopes = TokenScope.values();
		for (TokenScope tokenScope : tokenScopes) {
			if (tokenScope.getValue().equals(json.getAsString())) {
				return tokenScope;
			}
		}
		return null;
	}

}
