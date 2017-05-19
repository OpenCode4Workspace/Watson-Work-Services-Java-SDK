package org.opencode4workspace;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.opencode4workspace.authentication.AuthenticationEndpoint;
import org.opencode4workspace.authentication.AuthenticationResult;
import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Message;
import org.opencode4workspace.bo.MessageResponse;
import org.opencode4workspace.bo.Person;
import org.opencode4workspace.bo.PhotoResponse;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.builders.BaseGraphQLQuery;
import org.opencode4workspace.builders.ConversationGraphQLQuery;
import org.opencode4workspace.builders.MessageGraphQLQuery;
import org.opencode4workspace.builders.PeopleGraphQLQuery;
import org.opencode4workspace.builders.PersonGraphQLQuery;
import org.opencode4workspace.builders.SpaceCreateGraphQLMutation;
import org.opencode4workspace.builders.SpaceGraphQLQuery;
import org.opencode4workspace.builders.SpaceMembersGraphQLQuery;
import org.opencode4workspace.builders.SpaceUpdateGraphQLMutation;
import org.opencode4workspace.builders.SpaceUpdateGraphQLMutation.UpdateSpaceMemberOperation;
import org.opencode4workspace.builders.SpacesGraphQLQuery;
import org.opencode4workspace.endpoints.AppMessage;
import org.opencode4workspace.endpoints.MessagePostEndpoint;
import org.opencode4workspace.endpoints.PhotoPostEndpoint;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.opencode4workspace.endpoints.WWGraphQLEndpoint;
import org.opencode4workspace.graphql.GraphResultContainer;
import org.opencode4workspace.graphql.UpdateSpaceContainer;
import org.opencode4workspace.json.GraphQLRequest;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Watson Workspace Apache HTTP Client class Manages setting up an HTTP Client for both users and applications
 */
public class WWClient implements Serializable, IWWClient {

	private static final long serialVersionUID = 1L;
	private ClientType clientType;
	private String appId;
	private String appSecret;
	private String userToken;
	private AuthenticationEndpoint endpoint;
	private AuthenticationResult authenticationResult;
	private String redirectTo;
	private String resultContent;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.IWWClient#getClientType()
	 */
	@Override
	public ClientType getClientType() {
		return clientType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.IWWClient#getAppCredentials()
	 */
	@Override
	public String getAppCredentials() throws UnsupportedEncodingException {
		String base64 = Base64.encodeBase64String((appId + ":" + appSecret).getBytes("UTF-8"));
		return "Basic " + base64;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.IWWClient#isAuthenticated()
	 */
	@Override
	public boolean isAuthenticated() {
		return authenticationResult != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.IWWClient#authenticate()
	 */
	@Override
	public void authenticate() throws UnsupportedEncodingException, WWException {
		if (clientType == ClientType.APPLICATON) {
			authenticationResult = endpoint.authenticateApplication(getAppCredentials());
		} else {
			authenticationResult = endpoint.authorizeUser(getAppCredentials(), userToken, redirectTo);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.IWWClient#getJWTToken()
	 */
	@Override
	public String getJWTToken() {
		return authenticationResult.getJwtToken();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.IWWClient#getExpiresIn()
	 */
	@Override
	public Object getExpiresIn() {
		return authenticationResult.getExpires();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.IWWClient#isValid()
	 */
	@Override
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
	 * Easy helper method to create a Space with a title and list of Members
	 * 
	 * @param title
	 *            String title for the Space
	 * @param members
	 *            List of member IDs to be granted access to the Space
	 * @return Space containing the ID of the newly-created Space
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.6.0
	 */
	public Space createSpace(String title, List<String> members) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.createSpace(title, members);
	}

	/**
	 * Easy helper method to create a Space with a title
	 * 
	 * @param title
	 *            String title for the Space
	 * @return Space containing the ID of the newly-created Space
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.6.0
	 */
	public Space createSpace(String title) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.createSpace(title, null);
	}

	/**
	 * Easy helper method to create a Space with a SpaceCreateGraphQLMutation object
	 * 
	 * @param mutationObject
	 *            SpaceCreateGraphQLMutation containing the details to create
	 * @return Space containing the ID of the newly-created Space
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.6.0
	 */
	public Space createSpaceWithQuery(SpaceCreateGraphQLMutation mutationObject) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.createSpaceWithMutation(mutationObject);
	}

	/**
	 * Easy helper method to delete a Space
	 * 
	 * @param id
	 *            String id for the Space
	 * @return boolean, whether or not the deletion was successful
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.6.0
	 */
	public boolean deleteSpace(String id) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.deleteSpace(id);
	}

	/**
	 * Easy helper method for updating a Space Title returning Space object with updated Title
	 * 
	 * @param id
	 *            String id of the Space to update
	 * @param newTitle
	 *            String new title for the space
	 * @return Space updated
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.6.0
	 */
	public Space updateSpaceTitle(String id, String newTitle) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.updateSpaceTitle(id, newTitle);
	}

	/**
	 * Easy helper method for updating a Space Title / members / both returning an UpdateSpaceContainer with updated Space details / updated members / both
	 * 
	 * @param mutationObject
	 *            SpaceUpdateGraphQLMutation containing the details to update
	 * @return UpdateSpaceContainer containing Array of members updated and Space
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.6.0
	 */
	public UpdateSpaceContainer updateSpaceWithMutation(SpaceUpdateGraphQLMutation mutationObject) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.updateSpaceWithMutation(mutationObject);
	}

	/**
	 * Easy helper method for updating a Space members returning List of member IDs updated
	 * 
	 * @param id
	 *            String id of the space to update
	 * @param members
	 *            List of member IDs to add / remove as members
	 * @param addOrRemove
	 *            boolean whether members should be added to the Space or removed
	 * @return ArrayList of member IDs updated
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.6.0
	 */
	public ArrayList<String> updateSpaceMembers(String id, List<String> members, UpdateSpaceMemberOperation addOrRemove) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.updateSpaceMembers(id, members, addOrRemove);
	}

	/**
	 * Easy helper method for updationg a Space members and title, returning UpdateSpaceContainer with array of member IDs updated and Space object with updated title
	 * 
	 * @param id
	 *            String id for the Space to update
	 * @param title
	 *            String title of the newly-created Space
	 * @param members
	 *            List of member IDs to add / remove as members
	 * @param addOrRemove
	 *            UpdateSpaceMemberOperation enum whether members should be added to the Space or removed
	 * @return UpdateSpaceContainer containing Array of members updated and Space
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.6.0
	 */
	public UpdateSpaceContainer updateSpaceMembersAndTitle(String id, String title, List<String> members, UpdateSpaceMemberOperation addOrRemove) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		return ep.updateSpaceMembersAndTitle(id, title, members, addOrRemove);
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
	 * 
	 * @param message
	 *            Application Message (use AppMessageBuilder) to post
	 * @param spaceId
	 *            ID of the Space, where the message should be posted
	 * @return MessageResponse
	 * @throws WWException
	 *             contains an error message, if the post was unsuccessful
	 */
	public MessageResponse postMessageToSpace(AppMessage message, String spaceId) throws WWException {
		MessagePostEndpoint ep = new MessagePostEndpoint(this);
		return ep.postMessage(message, spaceId);
	}
	
	/**
	 * @param photo File containing a jpeg to post. Should be less than 300Kb
	 * @return PhotoResponse from Watson Workspace 
	 * @throws WWException contains an error message, if the query was unsuccessful
	 * @since 0.7.0
	 */
	public PhotoResponse postPhoto(File photo) throws WWException {
		PhotoPostEndpoint ep = new PhotoPostEndpoint(this);
		return ep.postPhoto(photo);
	}

	/**
	 * Perform a custom GraphQL query returning customised content for one or more fields. Return objects may also be cast to variables
	 * 
	 * @param query BaseGraphQLQuery custom query to run 
	 * @return GraphResultContainer containing Data and Errors
	 * @throws WWException contains an error message, if the query was unsuccessful
	 */
	public GraphResultContainer getCustomQuery(BaseGraphQLQuery query) throws WWException {
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(this);
		ep.setRequest(new GraphQLRequest(query));
		ep.executeRequest();
		return ep.getResultContainer();
	}
	
	/* (non-Javadoc)
	 * @see org.opencode4workspace.IWWClient#getResultContent()
	 */
	public String getResultContent() {
		return resultContent;
	}
	
	/* (non-Javadoc)
	 * @see org.opencode4workspace.IWWClient#setResultContent(java.lang.String)
	 */
	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}

}
