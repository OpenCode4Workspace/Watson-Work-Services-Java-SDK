package org.opencode4workspace.it;

import java.io.UnsupportedEncodingException;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ITAuthentification {

	@Test
	@Parameters({ "userName", "userPassword" })
	public void testLoginAsUser(String userName, String userPassword) throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientUserAccess(userName, userPassword, new WWAuthenticationEndpoint());
		assert client.isAuthentitacted();
		client.authenticate();
	}
}
