package org.opencode4workspace.authentication;

import java.util.List;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Application Token Class, specific for application connections to Watson Workspace. Extended by
 *        {@link PeopleToken}
 */
public class AppToken {

	/**
	 * @author Christian Guedemann
	 * @since 0.5.0
	 * 
	 *        Enum for Token types, currently only "bearer". This gets added to the POST / GET request
	 */
	public enum TokenType {
		BEARER("bearer");

		private String value_;

		private TokenType(String type) {
			value_ = type;
		}

		public String getValue() {
			return value_;
		}
	}

	/**
	 * @author Christian Guedemann
	 * @since 0.5.0
	 * 
	 *        Enum for scopes of token. space_create, settings-read, settings_update are only relevant to PeopleTokens.
	 *        space_change is only relevant to AppTokens. The rest are shared.
	 *
	 */
	public enum TokenScope {
		SPACE_CREATE("space_create"), SETTINGS_READ("settings_read"), SETTINGS_UPDATE(
				"settings_update"), TRANSCRIPT_READ("transcript_read"), MESSAGE_CREATE("message_create"), SPACE_LIST(
						"space_list"), FILE_UPLOAD("file_upload"), FILE_DOWNLOAD("file_download"), PROFILE_UPDATE(
								"profile_update"), MESSAGE_READ("message_read"), SPACE_READ(
										"space_read"), MEMBERSHIP_LIST("membership_list"), PROFILE_READ(
												"profile_read"), SPACE_CHANGE("space_change");

		private String value_;

		private TokenScope(String scope) {
			value_ = scope;
		}

		public String getValue() {
			return value_;
		}
	}

	private String access_token;
	private TokenType token_type;
	private int expires_in;
	private TokenScope[] scope;
	private String id;
	private String jti;

	/**
	 * @return String, access token for the authenticated application / user
	 * 
	 * @since 0.5.0
	 */
	public String getAccess_Token() {
		return access_token;
	}

	/**
	 * @return TokenType, get token type for the authenticated application / user. Currently the value is "bearer"
	 * 
	 * @since 0.5.0
	 */
	public TokenType getToken_Type() {
		return token_type;
	}

	/**
	 * @return String value of TokenType enum in use
	 * 
	 * @since 0.5.0
	 */
	public String getToken_TypeAsString() {
		return token_type.getValue();
	}

	/**
	 * @return int, get the duration after which the token will expire
	 * 
	 * @since 0.5.0
	 */
	public int getExpires_In() {
		return expires_in;
	}

	/**
	 * @return AccessScope, the scope of the token, e.g. READ_WRITE
	 * 
	 * @since 0.5.0
	 */
	public TokenScope[] getScope() {
		return scope;
	}

	/**
	 * @return String, the scope of the token as string, e.g. "read write"
	 * 
	 * @since 0.5.0
	 */
	public String getScopeAsString() {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (TokenScope s : scope) {
			if (first) {
				first = !first;
				builder.append(s.getValue());
			} else {
				builder.append(" " + s.getValue());
			}
		}
		return builder.toString();
	}

	/**
	 * @return String, the id associated with the token
	 * 
	 * @since 0.5.0
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return String, the JTI id associated witht he token
	 * 
	 * @since 0.5.0
	 */
	public String getJti() {
		return jti;
	}

}
