package org.opencode4workspace.tests;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Profile;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.opencode4workspace.endpoints.WWGraphQLEndpoint;

public class ProfileTest {
	private final String appId = "5d1bf268-c363-4eea-baec-e2dbeaa2fb72";
	private final String appSecret = "o40ej0nad66vk55tgha21l08r9fam79";
	private final String myDisplayName = "Paul Withers";
	private final String profileId = "8bf6c84f-961c-43df-836a-85748766912f";

	@Test
	public void ProfileTest() throws WWException, UnsupportedEncodingException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		Profile profile = ep.getProfile(profileId);
		assert (myDisplayName.equals(profile.getDisplayName()));
	}

}
