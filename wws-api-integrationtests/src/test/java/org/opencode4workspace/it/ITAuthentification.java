package org.opencode4workspace.it;

import java.io.UnsupportedEncodingException;

import org.opencode4workspace.IWWClient;
import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class ITAuthentification {

	@Test
	@Parameters({ "appId", "appSecret" })
	public void testLoginAsApplication(String appId, String appSecret) throws UnsupportedEncodingException, WWException {
		IWWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		System.out.println(client.getExpiresIn());
		assert "".equals(client.getUserRefreshToken());
	}

	
	@Test(enabled=false)
	@Parameters({ "appId", "appSecret", "userToken" })
	public void testLoginAsUser(String appId, String appSecret, String userToken) throws UnsupportedEncodingException, WWException {
		System.out.println("UserToken: "+ userToken);
		IWWClient client = WWClient.buildClientUserAccess(userToken,appId, appSecret, new WWAuthenticationEndpoint(), "https://openntf.org");
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
	}

}
