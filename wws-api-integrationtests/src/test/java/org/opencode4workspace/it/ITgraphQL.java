package org.opencode4workspace.it;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Profile;
import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.opencode4workspace.endpoints.WWGraphQLEndpoint;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ITgraphQL {

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret" })
	public void testGetSpacesAsApp(String appId, String appSecret) throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		List<? extends Space> spaces = ep.getSpaces();
		assert (spaces.size() > 0);
	}

	@Test(enabled = false)
	@Parameters({ "appId", "appSecret", "myDisplayName" })
	public void testGetMe(String appId, String appSecret, String myDisplayName)
			throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		Profile me = ep.getMe();
		assert (myDisplayName.equals(me.getDisplayName()));
	}

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret", "profileId", "myDisplayName" })
	public void testGetProfile(String appId, String appSecret, String profileId, String myDisplayName)
			throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		Profile profile = ep.getProfile(profileId);
		assert (myDisplayName.equals(profile.getDisplayName()));
	}
	@Test(enabled = true)
	@Parameters({ "appId", "appSecret", "conversationId" })
	public void testgetConversationAsApp(String appId, String appSecret, String conversationId) throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		Conversation conversation = ep.getConversation(conversationId);
		assert (conversation != null);
		assert (conversation.getMessages().size() > 0);
	}

	

}
