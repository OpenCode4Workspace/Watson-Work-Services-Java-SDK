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
	 *            String, content for Authorization header constructed from {@link WWClient#getAppCredentials()}
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
	 *            String, content for Authorization header constructed from {@link WWClient#getAppCredentials()}
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

	/**
	 * Creates POST request for authorizing user and returns result, if successful, or generates error, if not
	 * 
	 * @param basicAuthApp
	 *            String, content for Authorization header constructed from {@link WWClient#getAppCredentials()}
	 * @param userToken
	 *            String, user token to pass
	 * @param redirectTo
	 *            String, URL to redirect to if successful
	 * @return {@link PeopleToken} response
	 * @throws WWException
	 *             error details
	 * 
	 * @since 0.5.0
	 */
	PeopleToken authorizeUserGetToken(String basicAuthApp, String userToken, String redirectTo) throws WWException;

	/**
	 * @param basicAuthApp
	 *            String, content for Authorization header constructed from {@link WWClient#getAppCredentials()}
	 * @param refreshToken String, refresh token to pass
	 * @param scope String, scope for the token
	 * @return {@link AuthenticationResult} containing response details
	 * @throws WWException
	 *             error details
	 *             
	 * @since 0.8.0
	 */
	AuthenticationResult authorizeUserRefreshToken(String basicAuthApp, String refreshToken, String scope) throws WWException;
	/**
	 * @param basicAuthApp
	 *            String, content for Authorization header constructed from {@link WWClient#getAppCredentials()}
	 * @param refreshToken String, refresh token to pass
	 * @param scope String, scope for the token
	 * @return {@link PeopleToken} containing response details
	 * @throws WWException
	 *             error details
	 *             
	 * @since 0.8.0
	 */
	PeopleToken authorizeUserRefreshTokenGetToken(String basicAuthApp, String refreshToken, String scope) throws WWException;
}
