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
	private String appId;
	private String appSecret;
	private String userToken;
	private AuthenticatenEndpoint endpoint;
	private AuthenticationResult authenticationResult;
	private String redirectTo;

	public static WWClient buildClientUserAccess(String userToken, String appId, String appSecret, AuthenticatenEndpoint authenticationEndpoint, String redirectTo) {
		WWClient client = new WWClient();
		client.clientType = ClientType.USER;
		client.userToken = userToken;
		client.appId = appId;
		client.appSecret = appSecret;
		client.endpoint = authenticationEndpoint;
		client.redirectTo = redirectTo;
		return client;
	}

	public static WWClient buildClientApplicationAccess(String appId, String appSecret, AuthenticatenEndpoint authenticationEndpoint) {
		WWClient client = new WWClient();
		client.clientType = ClientType.APPLICATON;
		client.appId = appId;
		client.appSecret = appSecret;
		client.endpoint = authenticationEndpoint;
		return client;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public String getAppCredentials() throws UnsupportedEncodingException {
		String base64 = Base64.encodeBase64String((appId + ":" + appSecret).getBytes("UTF-8"));
		return "Basic " + base64;
	}

	public boolean isAuthenticated() {
		return authenticationResult != null;
	}

	public void authenticate() throws UnsupportedEncodingException, WWException {
		if (clientType == ClientType.APPLICATON) {
		authenticationResult = endpoint.authenticateApplication(getAppCredentials());
		} else {
			authenticationResult = endpoint.authorizeUser(getAppCredentials(), userToken, redirectTo);			
		}
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
