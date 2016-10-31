package org.opencode4workspace.it;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.opencode4workspace.endpoints.WWGraphQLEndpoint;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ITgraphQL {

	@Test(enabled=true)
	@Parameters({ "appId", "appSecret" })
	public void testgetSpacesAsApp(String appId, String appSecret) throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		List<? extends Space> spaces = ep.getSpaces();
		assert (spaces.size() > 0);
	}

}
