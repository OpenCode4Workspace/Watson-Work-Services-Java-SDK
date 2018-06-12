package org.opencode4workspace.json;

import java.lang.reflect.Type;

import org.opencode4workspace.authentication.AppToken.TokenType;
import org.opencode4workspace.bo.Focus.Lens;
import org.opencode4workspace.webhooks.WebhookType;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Deserialiser to convert String token type to Enum
 * 
 * @author Paul Withers
 * 
 * @since 0.9.0
 *
 */
public class WebhookTypeDeserializer implements JsonDeserializer<WebhookType> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public WebhookType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		WebhookType[] types = WebhookType.values();
		for (WebhookType type : types) {
			if (type.getLabel().equals(json.getAsString())) {
				return type;
			}
		}
		return null;
	}

}
