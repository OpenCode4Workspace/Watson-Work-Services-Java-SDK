package org.opencode4workspace.authentication;

import java.util.Calendar;
import java.util.Date;

public class AuthenticationResult {

	private final String jwtToken;
	private final int expires;
	private final Date created;
	private final String displayName;
	private final String userId;
	private final String jtId;
	private final Date expireDate;

	public AuthenticationResult(String jwtToken, int expires, String displayName, String userId, String jtId) {
		super();
		this.jwtToken = jwtToken;
		this.expires = expires;
		this.displayName = displayName;
		this.userId = userId;
		this.jtId = jtId;
		this.created = new Date();
		expireDate = buildExpireDate(created, this.expires);
	}

	private Date buildExpireDate(Date created2, int expires2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(created2);
		cal.add(Calendar.SECOND, expires2);
		return cal.getTime();
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public int getExpires() {
		return expires;
	}

	public Date getCreated() {
		return created;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getUserId() {
		return userId;
	}

	public String getJtId() {
		return jtId;
	}

	public boolean isValid() {
		return expireDate.after(new Date());
	}

	public static AuthenticationResult buildFromToken(AppToken appToken) {
		return new AuthenticationResult(appToken.getAccess_Token(), appToken.getExpires_In(), "", appToken.getId(), appToken.getJti());
	}

}
