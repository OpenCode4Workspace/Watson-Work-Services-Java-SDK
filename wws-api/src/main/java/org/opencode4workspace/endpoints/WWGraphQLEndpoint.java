package org.opencode4workspace.endpoints;

import java.util.List;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Profile;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.builders.ConversationGraphQLQuery;
import org.opencode4workspace.builders.ProfileGraphQLQuery;
import org.opencode4workspace.builders.SpaceMembersGraphQLQuery;
import org.opencode4workspace.builders.SpacesGraphQLQuery;
import org.opencode4workspace.graphql.DataContainer;
import org.opencode4workspace.json.GraphQLRequest;

public class WWGraphQLEndpoint extends AbstractWWGraphQLEndpoint {

	/**
	 * @param client
	 *            WWClient containing authentication details and token
	 */
	public WWGraphQLEndpoint(WWClient client) {
		super(client);
	}

	/**
	 * Simplified access method, to load GraphQL query for getting spaces, execute the request, and parse the results
	 * 
	 * @return List of Space details
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 */
	public List<? extends Space> getSpaces() throws WWException {

		SpacesGraphQLQuery queryObject = SpacesGraphQLQuery.buildStandardGetSpacesQuery();
		setRequest(new GraphQLRequest(queryObject));
		executeRequest();
		return (List<? extends Space>) getResultContainer().getData().getSpaces().getItems();
	}

	/**
	 * Simplified access method, to load GraphQL query for getting Me object
	 * 
	 * @return Profile for me
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 */
	public Profile getMe() throws WWException {
		ProfileGraphQLQuery queryObject = ProfileGraphQLQuery.buildMyProfilleQuery();
		setRequest(new GraphQLRequest(queryObject));
		executeRequest();
		return (Profile) getResultContainer().getData().getMe();
	}

	/**
	 * Simplified access method, to load GraphQL query for getting Me object
	 * 
	 * @param profileId
	 *            String, WWS id of the Profile to return
	 * @return Profile for me
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 */
	public Profile getProfile(String profileId) throws WWException {
		ProfileGraphQLQuery queryObject = ProfileGraphQLQuery.buildProfileQueryById(profileId);
		setRequest(new GraphQLRequest(queryObject));
		executeRequest();
		DataContainer container = getResultContainer().getData();
		return (Profile) container.getProfile();
	}

	public Conversation getConversation(String conversationId) throws WWException {
		ConversationGraphQLQuery queryObject = ConversationGraphQLQuery.buildStandardConversationQueryById(conversationId);
		setRequest(new GraphQLRequest(queryObject));
		executeRequest();
		DataContainer container = getResultContainer().getData();
		return container.getConversation();
	}

	public List<Profile> getSpaceMembers(String spaceId) throws WWException {
		SpaceMembersGraphQLQuery queryObject = SpaceMembersGraphQLQuery.buildSpaceMemberGraphQueryBySpaceId(spaceId);
		setRequest(new GraphQLRequest(queryObject));
		executeRequest();
		DataContainer container = getResultContainer().getData();
		return container.getSpace().getMembers();
	}

}
