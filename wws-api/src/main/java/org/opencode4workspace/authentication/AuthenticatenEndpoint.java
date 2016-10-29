package org.opencode4workspace.authentication;

import org.opencode4workspace.WWException;

public interface AuthenticatenEndpoint {

	AuthenticationResult authenticate(String basicAuth) throws WWException;

}
