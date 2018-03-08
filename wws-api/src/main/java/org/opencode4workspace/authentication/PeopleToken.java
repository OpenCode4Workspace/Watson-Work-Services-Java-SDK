package org.opencode4workspace.authentication;

import java.util.Date;

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
	private Date createdDate;

	/**
	 * @return String, the refresh token for the user, equivalent to JWT token for application
	 * 
	 * @since 0.5.0
	 */
	public String getRefresh_Token() {
		return refresh_token;
	}

	/**
	 * @return String, the display name for the user
	 * 
	 * @since 0.5.0
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return String, the Identity Provider for the token
	 * 
	 * @since 0.5.0
	 */
	public String getProviderId() {
		return providerId;
	}
	
	/**
	 * @return Date the token was created, used with expiresIn to work out if it's expired
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * @param date Date the token was created, used with expiresIn to work out if it's expired
	 */
	public void setCreatedDate(Date date) {
		createdDate = date;
	}

}
