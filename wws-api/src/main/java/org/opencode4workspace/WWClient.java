package org.opencode4workspace;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.opencode4workspace.authentication.AuthenticationEndpoint;
import org.opencode4workspace.authentication.AuthenticationResult;
import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Message;
import org.opencode4workspace.bo.MessageResponse;
import org.opencode4workspace.bo.Person;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.builders.ConversationGraphQLQuery;
import org.opencode4workspace.builders.MessageGraphQLQuery;
import org.opencode4workspace.builders.PeopleGraphQLQuery;
import org.opencode4workspace.builders.PersonGraphQLQuery;
import org.opencode4workspace.builders.SpaceGraphQLQuery;
import org.opencode4workspace.builders.SpaceMembersGraphQLQuery;
import org.opencode4workspace.builders.SpacesGraphQLQuery;
import org.opencode4workspace.endpoints.AppMessage;
import org.opencode4workspace.endpoints.MessagePostEndpoint;
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
	 *            {@link AuthenticationEndpoint} or any sub-class thereof. Typically {@link WWAuthenticationEndpoint}
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
	 *            {@link AuthenticationEndpoint} or any sub-class thereof. Typically {@link WWAuthenticationEndpoint}
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
	 * @return {@link ClientType#USER} or {@link ClientType#APPLICATON}
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
	 * 
	 * @since 0.5.0
	 */
	public String getAppCredentials() throws UnsupportedEncodingException {
		String base64 = Base64.encodeBase64String((appId + ":" + appSecret).getBytes("UTF-8"));
		return "Basic " + base64;
	}

	/**
	 * Whether or not authentication has successfully occurred. Call {@link #authenticate()} first
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
	 * 
	 * @since 0.5.0
	 */
	public void authenticate() throws UnsupportedEncodingException, WWException {
		if (clientType == ClientType.APPLICATON) {
			authenticationResult = endpoint.authenticateApplication(getAppCredentials());
		} else {
			authenticationResult = endpoint.authorizeUser(getAppCredentials(), userToken, redirectTo);
		}
	}

	/**
	 * Gets JWT token for the user / application from the {@link AuthenticationResult}. The JWT token is an expiring token associated with the appId and appSecret. From the documentation: "This JWT
	 * token is what you have to use in your App to pass to Watson Work Services on API calls so that you can use its services securely"
	 * 
	 * @return String, JWT token
	 * 
	 * @since 0.5.0
	 */
	public String getJWTToken() {
		return authenticationResult.getJwtToken();
	}

	/**
	 * Gets the length of time until the token expires from the {@link AuthenticationResult}
	 * 
	 * @return Object, a long that determines the time until expiry
	 * 
	 * @since 0.5.0
	 */
	public Object getExpiresIn() {
		return authenticationResult.getExpires();
	}

	/**
	 * Tests whether the {@link AuthenticationResult} is valid
	 * 
	 * @return boolean, whether or not valid
	 * 
	 * @since 0.5.0
	 */
	public boolean isValid() {
		return authenticationResult.isValid();
	}

	/**
	 * Easy helper method to get spaces from the WWClient
	 * 
	 * @return List of Space objects
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public List<? extends Space> getSpaces() throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getSpaces();
	}

	/**
	 * Easy helper method to get Spaces with a query
	 * 
	 * @param query
	 *            SpacesGraphQLQuery containing query parameters
	 * @return List of Space objects
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public List<? extends Space> getSpacesWithQuery(SpacesGraphQLQuery query) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getSpacesWithQuery(query);
	}

	/**
	 * Easy helper method to get a Space by a Space id
	 * 
	 * @param spaceId
	 *            id of the Space
	 * @return Space object
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Space getSpaceById(String spaceId) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getSpaceById(spaceId);
	}

	/**
	 * Easy helper method to get a Space by using a SpaceGraphQLQuery
	 * 
	 * @param query
	 *            SpaceGraphQLQuery for the call
	 * @return Space object
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Space getSpaceWithQuery(SpaceGraphQLQuery query) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getSpaceWithQuery(query);
	}

	/**
	 * Easy helper method to get a Conversation and its details by a conversation id
	 * 
	 * @param conversationId
	 *            String id of the conversation
	 * @return Conversation object
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Conversation getConversationById(String conversationId) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getConversation(conversationId);
	}

	/**
	 * 
	 * Easy helper method to get a Conversation and its details with a query
	 * 
	 * @param query
	 *            ConversationGraphQLQuery containing query parameters
	 * @return Conversation object
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Conversation getConversationWithQuery(ConversationGraphQLQuery query) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getConversationWithQuery(query);
	}

	/**
	 * Easy helper method to get a Message and its details by id
	 * 
	 * @param messageId
	 *            String id of the Message
	 * @return Message details of the Message
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Message getMessageById(String messageId) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getMessageById(messageId);
	}

	/**
	 * Easy helper method to get a Message and its details with a query
	 * 
	 * @param query
	 *            MessageGraphQLQuery containing query parameters
	 * @return Message details of the Message
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Message getMessageWithQuery(MessageGraphQLQuery query) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getMessageWithQuery(query);
	}

	/**
	 * Easy helper method to get a Person with a person id. The method gets "me" if id is blank.
	 * 
	 * @param personId
	 *            String id of the person
	 * @return Person object
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Person getPersonById(String personId) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getPersonById(personId);
	}

	/**
	 * Easy helper method to get a Person with a person email. The method gets "me" if email is blank.
	 * 
	 * @param personEmail
	 *            String email of the person
	 * @return Person object
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Person getPersonByEmail(String personEmail) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getPersonById(personEmail);
	}

	/**
	 * Easy helper method to get a Person with a query
	 * 
	 * @param query
	 *            PersonGraphQLQuery containing query parameters
	 * @return Person object
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Person getPersonWithQuery(PersonGraphQLQuery query) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getPersonWithQuery(query);
	}

	/**
	 * Easy helper method to get members for a space with the space id
	 * 
	 * @param spaceId
	 *            String id of the space
	 * @return List of Person objects for the members of the space
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public List<Person> getSpaceMembersById(String spaceId) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getSpaceMembers(spaceId);
	}

	/**
	 * Easy helper method to get members of space with a query
	 * 
	 * @param query
	 *            SpaceMembersGraphQLQuery containing query parameters
	 * @return List of Person objects for the members of the space
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public List<Person> getSpaceMembersWithQuery(SpaceMembersGraphQLQuery query) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getSpaceMembersWithQuery(query);
	}

	/**
	 * Easy helper method to get My Person object. This will not work from a client authenticated as an application.
	 * 
	 * @return Person object relating to current authenticated user
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Person getMe() throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getMe();
	}

	/**
	 * Easy helper method to get Person objects with ids
	 * 
	 * @param ids
	 *            List of String person ids
	 * @return List of Person objects for the ids passed
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public List<Person> getPeople(List<String> ids) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getPeople(ids);
	}

	/**
	 * Easy helper method to get Person objects with part of a name
	 * 
	 * @param name
	 *            String part of a name. This needs to be a single word
	 * @return List of Person objects for the name passed
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public List<Person> getPeopleByName(String name) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getPeopleByName(name);
	}

	/**
	 * Easy helper method to get Person objects with a query
	 * 
	 * @param query
	 *            PeopleGraphQLQuery containing query parameters
	 * @return List of Person objects for the name passed
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public List<Person> getPeopleWithQuery(PeopleGraphQLQuery query) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.getPeopleWithQuery(query);
	}
	
	/**
	 * Easy helper method to post a Application Message to a Space
	 * @param message
	 *              Application Message (use AppMessageBuilder) to post
	 * @param spaceId
	 *              ID of the Space, where the message should be posted
	 * @return MessageResponse
	 * @throws WWException
	 *              contains an error message, if the post was unsuccessful
	 */
	public MessageResponse postMessageToSpace(AppMessage message, String spaceId) throws WWException {
		MessagePostEndpoint ep = new MessagePostEndpoint(this);
		return ep.postMessage(message, spaceId);
	}

}
