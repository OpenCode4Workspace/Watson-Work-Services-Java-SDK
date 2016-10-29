package org.opencode4workspace;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.opencode4workspace.authentication.AuthenticatenEndpoint;
import org.opencode4workspace.authentication.AuthenticationResult;

public class WWClient {

	public enum ClientType {
		USER, APPLICATON;
	}

	private ClientType clientType;
	private String userName;
	private String password;
	private AuthenticatenEndpoint endpoint;
	private AuthenticationResult authenticationResult;

	public static WWClient buildClientUserAccess(String userName, String password, AuthenticatenEndpoint authenticationEndpoint) {
		WWClient client = new WWClient();
		client.clientType = ClientType.USER;
		client.userName = userName;
		client.password = password;
		client.endpoint = authenticationEndpoint;
		return client;
	}

	public static WWClient buildClientApplicaitonAccess(String appId, String appSecret, AuthenticatenEndpoint authencticationEndpoint) {
		WWClient client = new WWClient();
		client.clientType = ClientType.APPLICATON;
		client.userName = appId;
		client.password = appSecret;
		return client;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public String getBasicCredentials() throws UnsupportedEncodingException {
		String base64 = Base64.encodeBase64String((userName + ":" + password).getBytes("UTF-8"));
		return "Basic " + base64;
	}

	public boolean isAuthenticated() {
		return false;
	}

	public void authenticate() throws UnsupportedEncodingException, WWException {
		authenticationResult = endpoint.authenticate(getBasicCredentials());
	}

	public String getJWTToken() {
		return authenticationResult.getJwtToken();
	}

	public Object getExpiresIn() {
		return authenticationResult.getExpires();
	}

	public boolean isValid() {
		return authenticationResult.isValid();
	}

}
