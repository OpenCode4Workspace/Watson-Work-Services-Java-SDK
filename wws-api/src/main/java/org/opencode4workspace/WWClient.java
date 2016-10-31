package org.opencode4workspace;

import java.io.UnsupportedEncodingException;

import javax.print.attribute.standard.Sides;

import org.apache.commons.codec.binary.Base64;
import org.opencode4workspace.authentication.AuthenticationEndpoint;
import org.opencode4workspace.authentication.AuthenticationResult;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Watson Work Services Apache HTTP Client class Manages setting up an
 *        HTTP Client for both users and applications
 */
public class WWClient {

	/**
	 * @author Christian Guedemann
	 * @since 0.5.0
	 * 
	 *        Two types of authentication are supported - user-level and
	 *        application-level User is for actions on behalf of a specific user
	 *        Application is for actions taken on behalf of the application,
	 *        without being associated with a user
	 */
	public enum ClientType {
		USER, APPLICATON;
	}

	private ClientType clientType;
	private String appId;
	private String appSecret;
	private String userToken;
	private AuthenticationEndpoint endpoint;
	private AuthenticationResult authenticationResult;
	private String redirectTo;

	/**
	 * Creates and returns a WWClient for a specific user
	 * 
	 * @param userToken
	 *            String, the JWT user token to be passed, used to authenticate
	 *            as the specific user
	 * @param appId
	 *            String, the ID for the application the code is being run from
	 * @param appSecret
	 *            String, the secret for the application the code is being run
	 *            from
	 * @param authenticationEndpoint
	 *            {@linkplain AuthenticationEndpoint} or any sub-class thereof.
	 *            Typically {@linkplain WWAuthenticationEndpoint}
	 * @param redirectTo
	 *            String, URL to redirect to after authentication
	 * @return WWClient, a Watson Work Services Client contructed with the
	 *         passed params
	 * 
	 * @since 0.5.0
	 */
	public static WWClient buildClientUserAccess(String userToken, String appId, String appSecret,
			AuthenticationEndpoint authenticationEndpoint, String redirectTo) {
		WWClient client = new WWClient();
		client.clientType = ClientType.USER;
		client.userToken = userToken;
		client.appId = appId;
		client.appSecret = appSecret;
		client.endpoint = authenticationEndpoint;
		client.redirectTo = redirectTo;
		return client;
	}

	/**
	 * Creates an application-level WWClient, not associated with a specific
	 * user
	 * 
	 * @param appId
	 *            String, the ID for the application the code is being run from
	 * @param appSecret
	 *            String, the secret for the application the code is being run
	 *            from
	 * @param authenticationEndpoint
	 *            {@linkplain AuthenticationEndpoint} or any sub-class thereof.
	 *            Typically {@linkplain WWAuthenticationEndpoint}
	 * @return WWClient, a Watson Work Services Client contructed with the
	 *         passed params
	 * 
	 * @since 0.5.0
	 */
	public static WWClient buildClientApplicationAccess(String appId, String appSecret,
			AuthenticationEndpoint authenticationEndpoint) {
		WWClient client = new WWClient();
		client.clientType = ClientType.APPLICATON;
		client.appId = appId;
		client.appSecret = appSecret;
		client.endpoint = authenticationEndpoint;
		return client;
	}

	/**
	 * Getter for ClientType, dependent upon the initialiser user
	 * 
	 * @return {@linkplain ClientType#USER} or
	 *         {@linkplain ClientType#APPLICATON}
	 * 
	 * @since 0.5.0
	 */
	public ClientType getClientType() {
		return clientType;
	}

	/**
	 * Converts appId and appSecret into required content for Authorization
	 * header
	 * 
	 * @return String, content for Authorization header
	 * @throws UnsupportedEncodingException
	 * @since 0.5.0
	 */
	public String getAppCredentials() throws UnsupportedEncodingException {
		String base64 = Base64.encodeBase64String((appId + ":" + appSecret).getBytes("UTF-8"));
		return "Basic " + base64;
	}

	/**
	 * Whether or not authentication has successfully occurred. Call
	 * {@linkplain #authenticate()} first
	 * 
	 * @return boolean, whether or not already authenticated
	 * 
	 * @since 0.5.0
	 */
	public boolean isAuthenticated() {
		return authenticationResult != null;
	}

	/**
	 * Attempt authentication of the WWClient
	 * 
	 * @throws UnsupportedEncodingException
	 *             Authorization header could not be constructed
	 * @throws WWException
	 *             Some other error occurred during authentication
	 */
	public void authenticate() throws UnsupportedEncodingException, WWException {
		if (clientType == ClientType.APPLICATON) {
			authenticationResult = endpoint.authenticateApplication(getAppCredentials());
		} else {
			authenticationResult = endpoint.authorizeUser(getAppCredentials(), userToken, redirectTo);
		}
	}

	/**
	 * Gets JWT token for the user / application from the
	 * {@linkplain AuthenticationResult}. The JWT token is an expiring token
	 * associated with the appId and appSecret. From the documentation: "This
	 * JWT token is what you have to use in your App to pass to Watson Work
	 * Services on API calls so that you can use its services securely"
	 * 
	 * @return String, JWT token
	 */
	public String getJWTToken() {
		return authenticationResult.getJwtToken();
	}

	/**
	 * Gets the length of time until the token expires from the
	 * {@linkplain AuthenticationResult}
	 * 
	 * @return Object, a long that determines the time until expiry
	 */
	public Object getExpiresIn() {
		return authenticationResult.getExpires();
	}

	/**
	 * Tests whether the {@linkplain AuthenticationResult} is valid
	 * 
	 * @return boolean, whether or not valild
	 */
	public boolean isValid() {
		return authenticationResult.isValid();
	}

}
