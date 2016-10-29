package org.opencode4workspace.tests;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Test;
import org.opencode4workspace.WWClient;
import org.opencode4workspace.authentication.AuthenticatenEndpoint;

public class AuthenticationTests {

	@Test
	public void testBuildAuthenticationClient() {
		AuthenticatenEndpoint authencticationEndpoint = EasyMock.createNiceMock(AuthenticatenEndpoint.class);
		WWClient client = WWClient.buildClient("Max Muster", "mypassword", authencticationEndpoint);
		assertNotNull(client);
	}
}
