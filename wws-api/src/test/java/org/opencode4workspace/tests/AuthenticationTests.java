package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import org.easymock.EasyMock;
import org.junit.Test;
import org.opencode4workspace.IWWClient;
import org.opencode4workspace.IWWClient.ClientType;
import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.authentication.AuthenticationEndpoint;
import org.opencode4workspace.authentication.AuthenticationResult;

public class AuthenticationTests {

	private static final String MY_APP_SECRET = "myAppSecret";
	private static final String MY_APP_ID = "myAppId";
	private static final String USER_TOKEN = "userToken";
	private static final String JWT_TOKEN = "99119181";
	private static final String BASIC_MM_CRED = "Basic bXlBcHBJZDpteUFwcFNlY3JldA==";

	@Test
	public void testBuildAuthenticationClient4User() {
		AuthenticationEndpoint authenticationEndpoint = EasyMock.createNiceMock(AuthenticationEndpoint.class);
		IWWClient client = WWClient.buildClientUserAccess(USER_TOKEN, MY_APP_ID, MY_APP_SECRET, authenticationEndpoint, "http://localhost");
		assertNotNull(client);
		assertEquals(ClientType.USER, client.getClientType());
	}

	@Test
	public void testBuildAuthenticationClient4Application() {
		AuthenticationEndpoint authenticationEndpoint = EasyMock.createNiceMock(AuthenticationEndpoint.class);
		IWWClient client = WWClient.buildClientApplicationAccess("201830181", "appsecretekey", authenticationEndpoint);
		assertNotNull(client);
		assertEquals(ClientType.APPLICATON, client.getClientType());
	}

	@Test
	public void testGeneareteBasicCredentials() throws UnsupportedEncodingException {
		AuthenticationEndpoint authenticationEndpoint = EasyMock.createNiceMock(AuthenticationEndpoint.class);
		IWWClient client = WWClient.buildClientUserAccess(USER_TOKEN, MY_APP_ID, MY_APP_SECRET, authenticationEndpoint, "http://localhost");
		assertNotNull(client);
		assertEquals(BASIC_MM_CRED, client.getAppCredentials());
	}

	@Test
	public void testGetJWTTokenFromEndpointApplication() throws InterruptedException, UnsupportedEncodingException, WWException {
		AuthenticationEndpoint authenticationEndpoint = EasyMock.createNiceMock(AuthenticationEndpoint.class);
		EasyMock.expect(authenticationEndpoint.authenticateApplication(BASIC_MM_CRED)).andReturn(new AuthenticationResult(JWT_TOKEN, 2, "", "", "", ""));
		EasyMock.replay(authenticationEndpoint);
		IWWClient client = WWClient.buildClientApplicationAccess(MY_APP_ID, MY_APP_SECRET, authenticationEndpoint);

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
	public void testGetJWTTokenFromEndpointUser() throws InterruptedException, UnsupportedEncodingException, WWException {
		AuthenticationEndpoint authenticationEndpoint = EasyMock.createNiceMock(AuthenticationEndpoint.class);
		EasyMock.expect(authenticationEndpoint.authorizeUser(BASIC_MM_CRED, USER_TOKEN, "http://localhost")).andReturn(new AuthenticationResult(JWT_TOKEN, 2, "", "", "", ""));
		EasyMock.replay(authenticationEndpoint);
		IWWClient client = WWClient.buildClientUserAccess(USER_TOKEN, MY_APP_ID, MY_APP_SECRET, authenticationEndpoint, "http://localhost");

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
		AuthenticationEndpoint authenticationEndpoint = EasyMock.createNiceMock(AuthenticationEndpoint.class);
		EasyMock.expect(authenticationEndpoint.authorizeUser(BASIC_MM_CRED, USER_TOKEN, "http://localhost")).andStubThrow(new WWException("Authentification Failed"));
		EasyMock.replay(authenticationEndpoint);
		IWWClient client = WWClient.buildClientUserAccess(USER_TOKEN, MY_APP_ID, MY_APP_SECRET, authenticationEndpoint, "http://localhost");

		assertNotNull(client);
		assertFalse(client.isAuthenticated());
		try {
			client.authenticate();
		} catch (WWException ex) {
			assertTrue(true);
			return;
		}
		assertTrue("No exception thrown!", false);

	}
}
