package org.opencode4workspace.authentication;

public class AppToken {

	private String access_token;
	private String token_type;
	private int expires_in;
	private String scope;
	private String id;
	private String jti;
	
	public String getAccess_Token() {
		return access_token;
	}

	public String getToken_Type() {
		return token_type;
	}

	public int getExpires_In() {
		return expires_in;
	}

	public String getScope() {
		return scope;
	}

	public String getId() {
		return id;
	}

	public String getJti() {
		return jti;
	}
	
}
