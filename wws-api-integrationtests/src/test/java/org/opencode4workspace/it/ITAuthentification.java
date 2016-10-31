package org.opencode4workspace.it;

import java.io.UnsupportedEncodingException;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class ITAuthentification {

	@Test
	@Parameters({ "appId", "appSecret" })
	public void testLoginAsApplication(String appId, String appSecret) throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicaitonAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
	}

	
	@Test(enabled=false)
	@Parameters({ "appId", "appSecret", "userToken" })
	public void testLoginAsUser(String appId, String appSecret, String userToken) throws UnsupportedEncodingException, WWException {
		System.out.println("UserToken: "+ userToken);
		WWClient client = WWClient.buildClientUserAccess(userToken,appId, appSecret, new WWAuthenticationEndpoint(), "https://webgate.biz");
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
	}

}
