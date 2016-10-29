package org.opencode4workspace.authentication;

import org.opencode4workspace.WWException;

public interface AuthenticatenEndpoint {

	AuthenticationResult authenticateApplication(String basicAuthApp) throws WWException;

	AuthenticationResult authorizeUser(String basicAuthApp, String userToken, String redirectTo) throws WWException;

}
