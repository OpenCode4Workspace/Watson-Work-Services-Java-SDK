package org.opencode4workspace;

import java.io.UnsupportedEncodingException;

import org.opencode4workspace.authentication.AuthenticationResult;

public interface IWWClient {

	/**
	 * @author Christian Guedemann
	 * @since 0.5.0
	 * 
	 *        Two types of authentication are supported - user-level and application-level User is for actions on behalf of a specific user Application is for actions taken on behalf of the
	 *        application, without being associated with a user
	 */
	public enum ClientType {
		USER, APPLICATON;
	}

	/**
	 * Getter for ClientType, dependent upon the initialiser user
	 * 
	 * @return {@link ClientType#USER} or {@link ClientType#APPLICATON}
	 * 
	 * @since 0.5.0
	 */
	ClientType getClientType();

	/**
	 * Converts appId and appSecret into required content for Authorization header
	 * 
	 * @return String, content for Authorization header
	 * @throws UnsupportedEncodingException
	 *             if the encoding option is not supported
	 * 
	 * @since 0.5.0
	 */
	String getAppCredentials() throws UnsupportedEncodingException;

	/**
	 * Whether or not authentication has successfully occurred. Call {@link #authenticate()} first
	 * 
	 * @return boolean, whether or not already authenticated
	 * 
	 * @since 0.5.0
	 */
	boolean isAuthenticated();

	/**
	 * Attempt authentication of the WWClient
	 * 
	 * @throws UnsupportedEncodingException
	 *             Authorization header could not be constructed
	 * @throws WWException
	 *             Some other error occurred during authentication
	 * 
	 * @since 0.5.0
	 */
	void authenticate() throws UnsupportedEncodingException, WWException;

	/**
	 * Gets JWT token for the user / application from the {@link AuthenticationResult}. The JWT token is an expiring token associated with the appId and appSecret. From the documentation: "This JWT
	 * token is what you have to use in your App to pass to Watson Work Services on API calls so that you can use its services securely"
	 * 
	 * @return String, JWT token
	 * 
	 * @since 0.5.0
	 */
	String getJWTToken();

	/**
	 * Gets the length of time until the token expires from the {@link AuthenticationResult}
	 * 
	 * @return Object, a long that determines the time until expiry
	 * 
	 * @since 0.5.0
	 */
	Object getExpiresIn();

	/**
	 * Tests whether the {@link AuthenticationResult} is valid
	 * 
	 * @return boolean, whether or not valid
	 * 
	 * @since 0.5.0
	 */
	boolean isValid();
	
	/**
	 * Getter for resultContent from relevant endpoint
	 * 
	 * @return result content (data / errors) as JSON String
	 */
	String getResultContent();

	/**
	 * Setter for resultContent from relevant endpoint
	 * 
	 * @param resultContent result content (data / errors) as JSON String
	 */
	void setResultContent(String resultContent);

}