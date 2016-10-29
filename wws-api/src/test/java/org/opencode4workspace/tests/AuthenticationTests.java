package org.opencode4workspace.tests;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.easymock.EasyMock;
import org.junit.Test;
import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.authentication.AuthenticatenEndpoint;
import org.opencode4workspace.authentication.AuthenticationResult;

public class AuthenticationTests {

	private static final String JWT_TOKEN = "99119181";
	private static final String BASIC_MM_CRED = "Basic TWF4IE11c3RlcjpteXBhc3N3b3Jk";

	@Test
	public void testBuildAuthenticationClient4User() {
		AuthenticatenEndpoint authenticationEndpoint = EasyMock.createNiceMock(AuthenticatenEndpoint.class);
		WWClient client = WWClient.buildClientUserAccess("Max Muster", "mypassword", authenticationEndpoint);
		assertNotNull(client);
		assertEquals(WWClient.ClientType.USER, client.getClientType());
	}

	@Test
	public void testBuildAuthenticationClient4Application() {
		AuthenticatenEndpoint authenticationEndpoint = EasyMock.createNiceMock(AuthenticatenEndpoint.class);
		WWClient client = WWClient.buildClientApplicaitonAccess("201830181", "appsecretekey", authenticationEndpoint);
		assertNotNull(client);
		assertEquals(WWClient.ClientType.APPLICATON, client.getClientType());
	}

	@Test
	public void testGeneareteBasicCredentials() throws UnsupportedEncodingException {
		AuthenticatenEndpoint authenticationEndpoint = EasyMock.createNiceMock(AuthenticatenEndpoint.class);
		WWClient client = WWClient.buildClientUserAccess("Max Muster", "mypassword", authenticationEndpoint);
		assertNotNull(client);
		assertEquals(BASIC_MM_CRED, client.getBasicCredentials());
	}

	@Test
	public void testGetJWTTokenFromEndpoint() throws InterruptedException, UnsupportedEncodingException, WWException {
		AuthenticatenEndpoint authenticationEndpoint = EasyMock.createNiceMock(AuthenticatenEndpoint.class);
		EasyMock.expect(authenticationEndpoint.authenticate(BASIC_MM_CRED)).andReturn(new AuthenticationResult(JWT_TOKEN, 2, "", "", ""));
		EasyMock.replay(authenticationEndpoint);
		WWClient client = WWClient.buildClientUserAccess("Max Muster", "mypassword", authenticationEndpoint);
		
		assertNotNull(client);
		assertFalse(client.isAuthenticated());
		client.authenticate();

		assertEquals(JWT_TOKEN, client.getJWTToken());
		assertEquals(2, client.getExpiresIn());
		assertTrue(client.isValid());
		Thread.sleep(3000);
		assertFalse(client.isValid());
		EasyMock.verify(authenticationEndpoint);
	}
	
	@Test
	public void testCheckAuthentificationFailsException() throws WWException, UnsupportedEncodingException {
		AuthenticatenEndpoint authenticationEndpoint = EasyMock.createNiceMock(AuthenticatenEndpoint.class);
		EasyMock.expect(authenticationEndpoint.authenticate(BASIC_MM_CRED)).andStubThrow(new WWException("Authentification Failed"));
		EasyMock.replay(authenticationEndpoint);
		WWClient client = WWClient.buildClientUserAccess("Max Muster", "mypassword", authenticationEndpoint);
		
		assertNotNull(client);
		assertFalse(client.isAuthenticated());
		try {
			client.authenticate();
		} catch(WWException ex) {
			assertTrue(true);
			return;
		}
		assertTrue("No exception thrown!",false);
		
	}
}
