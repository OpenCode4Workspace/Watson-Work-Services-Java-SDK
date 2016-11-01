package org.opencode4workspace.authentication;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        People Token class, for user-specific token extensions
 *
 */
public class PeopleToken extends AppToken {

	private String refresh_token;
	private String displayName;
	private String providerId;

	/**
	 * @return String, the refresh token for the user, equivalent to JWT token for application
	 */
	public String getRefresh_Token() {
		return refresh_token;
	}

	/**
	 * @return String, the display name for the user
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return String, the Identity Provider for the token
	 */
	public String getProviderId() {
		return providerId;
	}

}
