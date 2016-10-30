package org.opencode4workspace.authentication;

public class PeopleToken extends AppToken {

	private String refresh_token;
	private String displayName;
	private String providerId;

	public String getRefresh_Token() {
		return refresh_token;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getProviderId() {
		return providerId;
	}

}
