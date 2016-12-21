package org.opencode4workspace;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.opencode4workspace.authentication.AuthenticationEndpoint;
import org.opencode4workspace.authentication.AuthenticationResult;
import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Profile;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.builders.ConversationGraphQLQuery;
import org.opencode4workspace.builders.ProfileGraphQLQuery;
import org.opencode4workspace.builders.SpaceMembersGraphQLQuery;
import org.opencode4workspace.builders.SpacesGraphQLQuery;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.opencode4workspace.endpoints.WWGraphQLEndpoint;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Watson Workspace Apache HTTP Client class Manages setting up an HTTP Client for both users and applications
 */
public class WWClient implements Serializable {

	/**
	 * @author Christian Guedemann
	 * @since 0.5.0
	 * 
	 *        Two types of authentication are supported - user-level and application-level User is for actions on behalf of a specific user Application is for actions taken on behalf of the
	 *        application, without being associated with a user
	 */
	public enum ClientType {
		USER, APPLICATON;
	}

	private static final long serialVersionUID = 1L;
	private ClientType clientType;
	private String appId;
	private String appSecret;
	private String userToken;
	private AuthenticationEndpoint endpoint;
	private AuthenticationResult authenticationResult;
	private String redirectTo;

	/**
	 * Creates and returns a WWClient for a specific user
	 * 
	 * @param userToken
	 *            String, the JWT user token to be passed, used to authenticate as the specific user
	 * @param appId
	 *            String, the ID for the application the code is being run from
	 * @param appSecret
	 *            String, the secret for the application the code is being run from
	 * @param authenticationEndpoint
	 *            {@linkplain AuthenticationEndpoint} or any sub-class thereof. Typically {@linkplain WWAuthenticationEndpoint}
	 * @param redirectTo
	 *            String, URL to redirect to after authentication
	 * @return WWClient, a Watson Workspace Client contructed with the passed params
	 * 
	 * @since 0.5.0
	 */
	public static WWClient buildClientUserAccess(String userToken, String appId, String appSecret, AuthenticationEndpoint authenticationEndpoint, String redirectTo) {
		WWClient client = new WWClient();
		client.clientType = ClientType.USER;
		client.userToken = userToken;
		client.appId = appId;
		client.appSecret = appSecret;
		client.endpoint = authenticationEndpoint;
		client.redirectTo = redirectTo;
		return client;
	}

	/**
	 * Creates an application-level WWClient, not associated with a specific user
	 * 
	 * @param appId
	 *            String, the ID for the application the code is being run from
	 * @param appSecret
	 *            String, the secret for the application the code is being run from
	 * @param authenticationEndpoint
	 *            {@linkplain AuthenticationEndpoint} or any sub-class thereof. Typically {@linkplain WWAuthenticationEndpoint}
	 * @return WWClient, a Watson Workspace Client contructed with the passed params
	 * 
	 * @since 0.5.0
	 */
	public static WWClient buildClientApplicationAccess(String appId, String appSecret, AuthenticationEndpoint authenticationEndpoint) {
		WWClient client = new WWClient();
		client.clientType = ClientType.APPLICATON;
		client.appId = appId;
		client.appSecret = appSecret;
		client.endpoint = authenticationEndpoint;
		return client;
	}

	/**
	 * Getter for ClientType, dependent upon the initialiser user
	 * 
	 * @return {@linkplain ClientType#USER} or {@linkplain ClientType#APPLICATON}
	 * 
	 * @since 0.5.0
	 */
	public ClientType getClientType() {
		return clientType;
	}

	/**
	 * Converts appId and appSecret into required content for Authorization header
	 * 
	 * @return String, content for Authorization header
	 * @throws UnsupportedEncodingException
	 *             if the encoding option is not supported
	 * @since 0.5.0
	 */
	public String getAppCredentials() throws UnsupportedEncodingException {
		String base64 = Base64.encodeBase64String((appId + ":" + appSecret).getBytes("UTF-8"));
		return "Basic " + base64;
	}

	/**
	 * Whether or not authentication has successfully occurred. Call {@linkplain #authenticate()} first
	 * 
	 * @return boolean, whether or not already authenticated
	 * 
	 * @since 0.5.0
	 */
	public boolean isAuthenticated() {
		return authenticationResult != null;
	}

	/**
	 * Attempt authentication of the WWClient
	 * 
	 * @throws UnsupportedEncodingException
	 *             Authorization header could not be constructed
	 * @throws WWException
	 *             Some other error occurred during authentication
	 */
	public void authenticate() throws UnsupportedEncodingException, WWException {
		if (clientType == ClientType.APPLICATON) {
			authenticationResult = endpoint.authenticateApplication(getAppCredentials());
		} else {
			authenticationResult = endpoint.authorizeUser(getAppCredentials(), userToken, redirectTo);
		}
	}

	/**
	 * Gets JWT token for the user / application from the {@linkplain AuthenticationResult}. The JWT token is an expiring token associated with the appId and appSecret. From the documentation: "This
	 * JWT token is what you have to use in your App to pass to Watson Work Services on API calls so that you can use its services securely"
	 * 
	 * @return String, JWT token
	 */
	public String getJWTToken() {
		return authenticationResult.getJwtToken();
	}

	/**
	 * Gets the length of time until the token expires from the {@linkplain AuthenticationResult}
	 * 
	 * @return Object, a long that determines the time until expiry
	 */
	public Object getExpiresIn() {
		return authenticationResult.getExpires();
	}

	/**
	 * Tests whether the {@linkplain AuthenticationResult} is valid
	 * 
	 * @return boolean, whether or not valild
	 */
	public boolean isValid() {
		return authenticationResult.isValid();
	}

	public List<? extends Space> getSpaces() throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getSpaces();
	}

	public List<? extends Space> getSpacesWithQuery(SpacesGraphQLQuery query) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getSpacesWithQuery(query);
	}

	public Conversation getConversationById(String conversationId) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getConversation(conversationId);
	}

	public Conversation getConversationWithQuery(ConversationGraphQLQuery query) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getConversationWithQuery(query);
	}

	public Profile getProfileById(String profileId) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getProfile(profileId);
	}

	public Profile getProfileWithQuery(ProfileGraphQLQuery query) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getProfileWithQuery(query);
	}

	public List<Profile> getSpaceMembersById(String spaceId) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getSpaceMembers(spaceId);
	}

	public List<Profile> getSpaceMembersWithQuery(SpaceMembersGraphQLQuery query) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getSpaceMembersWithQuery(query);
	}

	public Profile getMe() throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getMe();
	}

	public List<Profile> getPeople(List<String> ids) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getPeople(ids);
	}

}
