package org.opencode4workspace.it;

import java.io.UnsupportedEncodingException;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.MessageResponse;
import org.opencode4workspace.builders.AppMessageBuilder;
import org.opencode4workspace.endpoints.AppMessage;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ITPostMessage {

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret", "spaceId" })
	public void postTestMessageToSpace(String appId, String appSecret, String spaceId) throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		
		AppMessageBuilder builder = new AppMessageBuilder();
		builder.setActorAvatar("http://gravatar.com/cgu").setActorName("CGU").setActorUrl("http://openntf.org").setColor("#FF0000");
		builder.setMessage("Message from *build process* - Integration Testing").setMessageTitle("IT-Testing");
		AppMessage message = builder.build();
		MessageResponse response = client.postMessageToSpace(message, spaceId);
		assert (response != null);
		assert (!"".equals(response.getId()));
	}
}
