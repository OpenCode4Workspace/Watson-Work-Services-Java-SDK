package org.opencode4workspace.authentication;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Application Token Class, specific for application connections to Watson Workspace. Extended by {@linkplain PeopleToken}
 */
public class AppToken {

	// TODO: Make this an enum
	private String access_token;
	private String token_type;
	private int expires_in;
	// TODO: Make this an enum
	private String scope;
	private String id;
	private String jti;

	/**
	 * @return String, access token for the authenticated application / user
	 */
	public String getAccess_Token() {
		return access_token;
	}

	/**
	 * @return String, get token type for the authenticated application / user. Currently the value is "bearer"
	 */
	public String getToken_Type() {
		return token_type;
	}

	/**
	 * @return int, get the duration after which the token will expire
	 */
	public int getExpires_In() {
		return expires_in;
	}

	/**
	 * @return String, the scope of the token, e.g. READ WRITE
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @return String, the id associated with the token
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return String, the JTI id associated witht he token
	 */
	public String getJti() {
		return jti;
	}

}
