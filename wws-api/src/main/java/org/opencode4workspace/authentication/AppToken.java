package org.opencode4workspace.authentication;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Application Token Class, specific for application connections to Watson Workspace. Extended by {@linkplain PeopleToken}
 */
public class AppToken {

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

	public enum TokenScope {
		READ_WRITE("read write");

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
	private TokenScope scope;
	private String id;
	private String jti;

	/**
	 * @return String, access token for the authenticated application / user
	 */
	public String getAccess_Token() {
		return access_token;
	}

	/**
	 * @return TokenType, get token type for the authenticated application / user. Currently the value is "bearer"
	 */
	public TokenType getToken_Type() {
		return token_type;
	}

	public String getToken_TypeAsString() {
		return token_type.getValue();
	}

	/**
	 * @return int, get the duration after which the token will expire
	 */
	public int getExpires_In() {
		return expires_in;
	}

	/**
	 * @return AccessScope, the scope of the token, e.g. READ_WRITE
	 */
	public TokenScope getScope() {
		return scope;
	}

	/**
	 * @return String, the scope of the token as string, e.g. "read write"
	 */
	public String getScopeAsString() {
		return scope.getValue();
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
