package org.opencode4workspace.json;

import java.lang.reflect.Type;

import org.opencode4workspace.authentication.AppToken.TokenType;
import org.opencode4workspace.bo.Person.PresenceStatus;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Deserialiser to convert String PresenceStatus in Person to Enum
 * 
 * @author Paul Withers
 * 
 * @since 0.7.0
 *
 */
public class PersonPresenceDeserializer implements JsonDeserializer<PresenceStatus> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public PresenceStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		PresenceStatus[] presenceStatuses = PresenceStatus.values();
		for (PresenceStatus presenceStatus : presenceStatuses) {
			if (presenceStatus.getLabel().equals(json.getAsString())) {
				return presenceStatus;
			}
		}
		return null;
	}

}
