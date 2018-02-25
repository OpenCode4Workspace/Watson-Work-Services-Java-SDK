package org.opencode4workspace.authentication;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Object containing details from authentication output
 *
 */
public class AuthenticationResult {

	private final String jwtToken;
	private final int expires;
	private final Date created;
	private final String displayName;
	private final String userId;
	private final String jtiId;
	// TODO: Using a Date may cause problems with DST, when committing to Java 8 use LocalDateTime
	private final Date expireDate;

	/**
	 * Constructs AuthenticationResult from parameters. Typically called from {@link #buildFromToken(AppToken)}
	 * 
	 * @param jwtToken
	 *            String JWT token for future requests
	 * @param expires
	 *            int, length of time until expiry
	 * @param displayName
	 *            String, display name for user or blank for applications
	 * @param userId
	 *            String, user id
	 * @param jtiId
	 *            String, JTI id
	 * 
	 * @since 0.5.0
	 */
	public AuthenticationResult(String jwtToken, int expires, String displayName, String userId, String jtiId) {
		super();
		this.jwtToken = jwtToken;
		this.expires = expires;
		this.displayName = displayName;
		this.userId = userId;
		this.jtiId = jtiId;
		this.created = new Date();
		expireDate = buildExpireDate(created, this.expires);
	}

	/**
	 * Works out the actual expiry date for the token
	 * 
	 * @param created2
	 *            Date, created date passed
	 * @param expires2
	 *            Date, expiry date passed
	 * @return Date, expiry will occur
	 * 
	 * @since 0.5.0
	 */
	private Date buildExpireDate(Date created2, int expires2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(created2);
		cal.add(Calendar.SECOND, expires2);
		return cal.getTime();
	}

	/**
	 * Gets the JWT token returned in authentication result
	 * 
	 * @return String, JWT token required for future requests
	 * 
	 * @since 0.5.0
	 */
	public String getJwtToken() {
		return jwtToken;
	}

	/**
	 * Gets the length of time before the token will expire
	 * 
	 * @return int, time until expiry
	 * 
	 * @since 0.5.0
	 */
	public int getExpires() {
		return expires;
	}

	/**
	 * Gets date when the authentication result was created
	 * 
	 * @return Date authentication result was created
	 * 
	 * @since 0.5.0
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * Gets the display name for the user, not currently in authentication result for applications
	 * 
	 * @return String user's display name
	 * 
	 * @since 0.5.0
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Gets user or application ID returned in authentication result
	 * 
	 * @return String user id
	 * 
	 * @since 0.5.0
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Gets JTI id returned in authentication result
	 * 
	 * @return String, JTI id
	 * 
	 * @since 0.5.0
	 */
	public String getJtId() {
		return jtiId;
	}

	/**
	 * Test JDT token expiry time against current time and return whether or not it's still valid. If not, we need to authenticate again
	 * 
	 * @return boolean, whether or not valid
	 * 
	 * @since 0.5.0
	 */
	public boolean isValid() {
		return expireDate.after(new Date());
	}

	/**
	 * Builds an AuthenticationResult object from the AppToken object constructed from JSON returned by the authentication process
	 * 
	 * @param appToken
	 *            {@link AppToken} object constructed from JSON returned by authentication process
	 * @return new AuthenticationResult object with contents extracted from AppToken
	 * 
	 * @since 0.5.0
	 */
	public static AuthenticationResult buildFromToken(AppToken appToken) {
		return new AuthenticationResult(appToken.getAccess_Token(), appToken.getExpires_In(), "", appToken.getId(), appToken.getJti());
	}

}
