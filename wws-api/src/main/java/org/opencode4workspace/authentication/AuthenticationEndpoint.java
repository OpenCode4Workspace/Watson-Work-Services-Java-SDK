package org.opencode4workspace.authentication;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Interface for handlign authentications
 */
public interface AuthenticationEndpoint {

	/**
	 * Creates POST request for authentication and returns result, if successful, or generates error, if not
	 * 
	 * @param basicAuthApp
	 *            String, content for Authroization header constructed from {@link WWClient#getAppCredentials()}
	 * @return {@link AuthenticationResult} containing response details
	 * @throws WWException
	 *             error details
	 * 
	 * @since 0.0.5
	 */
	AuthenticationResult authenticateApplication(String basicAuthApp) throws WWException;

	/**
	 * Creates POST request for authorizing user and returns result, if successful, or generates error, if not
	 * 
	 * @param basicAuthApp
	 *            String, content for Authroization header constructed from {@link WWClient#getAppCredentials()}
	 * @param userToken
	 *            String, user token to pass
	 * @param redirectTo
	 *            String, URL to redirect to if successful
	 * @return {@link AuthenticationResult} containing response details
	 * @throws WWException
	 *             error details
	 * 
	 * @since 0.5.0
	 */
	AuthenticationResult authorizeUser(String basicAuthApp, String userToken, String redirectTo) throws WWException;

}
