package org.opencode4workspace.endpoints;

import java.util.List;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Message;
import org.opencode4workspace.bo.Person;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.builders.ConversationGraphQLQuery;
import org.opencode4workspace.builders.SpaceCreateGraphQLMutation;
import org.opencode4workspace.builders.SpaceDeleteGraphQLMutation;
import org.opencode4workspace.builders.MessageGraphQLQuery;
import org.opencode4workspace.builders.PeopleGraphQLQuery;
import org.opencode4workspace.builders.PeopleGraphQLQuery.PeopleAttributes;
import org.opencode4workspace.builders.PersonGraphQLQuery;
import org.opencode4workspace.builders.SpaceGraphQLQuery;
import org.opencode4workspace.builders.SpaceMembersGraphQLQuery;
import org.opencode4workspace.builders.SpacesGraphQLQuery;
import org.opencode4workspace.graphql.DataContainer;
import org.opencode4workspace.json.GraphQLRequest;

public class WWGraphQLEndpoint extends AbstractWWGraphQLEndpoint {

	/**
	 * @param client
	 *            WWClient containing authentication details and token
	 * 
	 * @since 0.5.0
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
	 * 
	 * @since 0.5.0
	 */
	public List<? extends Space> getSpaces() throws WWException {

		SpacesGraphQLQuery queryObject = SpacesGraphQLQuery.buildStandardGetSpacesQuery();
		return getSpacesWithQuery(queryObject);
	}

	/**
	 * getSpaces by using a SpacesGraphQLQuery
	 * 
	 * @param query
	 *            GraphQLQuery for the call
	 * @return List of Space details
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public List<? extends Space> getSpacesWithQuery(SpacesGraphQLQuery query) throws WWException {
		setRequest(new GraphQLRequest(query));
		executeRequest();
		return (List<? extends Space>) getResultContainer().getData().getSpaces().getItems();
	}

	/**
	 * Create a space with a title and list of members
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
		SpaceCreateGraphQLMutation mutationObject = SpaceCreateGraphQLMutation.buildCreateSpaceMutationWithSpaceTitle(title, members);
		setRequest(new GraphQLRequest(mutationObject));
		executeRequest();
		return (Space) getResultContainer().getData().getSpace();
	}

	/**
	 * Delete a space for a given ID
	 * 
	 * @param id
	 *            String id of the space to delete
	 * @return boolean whether or not the deletion attempt was successful
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.6.0
	 */
	public boolean deleteSpace(String id) throws WWException {
		SpaceDeleteGraphQLMutation mutationObject = SpaceDeleteGraphQLMutation.buildDeleteSpaceMutationString(id);
		setRequest(new GraphQLRequest(mutationObject));
		executeRequest();
		return getResultContainer().getData().getDeletionSuccessful();
	}

	/**
	 * Get a Space by using a spaceId
	 * 
	 * @param spaceId
	 *            String id for the Space
	 * @return Space details
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Space getSpaceById(String spaceId) throws WWException {
		SpaceGraphQLQuery queryObject = SpaceGraphQLQuery.buildSpaceGraphQueryWithSpaceId(spaceId);
		setRequest(new GraphQLRequest(queryObject));
		executeRequest();
		return (Space) getResultContainer().getData().getSpace();
	}

	/**
	 * getSpace by using a SpaceGraphQLQuery
	 * 
	 * @param query
	 *            GraphQLQuery for the call
	 * @return Space details
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Space getSpaceWithQuery(SpaceGraphQLQuery query) throws WWException {
		setRequest(new GraphQLRequest(query));
		executeRequest();
		return (Space) getResultContainer().getData().getSpace();
	}

	/**
	 * Simplified access method, to load GraphQL query for getting Me object
	 * 
	 * @return Person object relating to "me"
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Person getMe() throws WWException {
		PersonGraphQLQuery queryObject = PersonGraphQLQuery.buildMyProfileQuery();
		setRequest(new GraphQLRequest(queryObject));
		executeRequest();
		return (Person) getResultContainer().getData().getMe();
	}

	/**
	 * Simplified access method, to load GraphQL query for getting Person by ID or "me" if personId is blank
	 * 
	 * @param personId
	 *            String, WWS id of the Person to return
	 * @return Person for relevant ID
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Person getPersonById(String personId) throws WWException {
		PersonGraphQLQuery queryObject = PersonGraphQLQuery.buildPersonQueryById(personId);
		return getPersonWithQuery(queryObject);
	}

	/**
	 * Simplified access method, to load GraphQL query for getting Person by email or "me" if personId is blank
	 * 
	 * @param personEmail
	 *            String, WWS email of the Person to return
	 * @return Person for relevant email
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Person getPersonByEmail(String personEmail) throws WWException {
		PersonGraphQLQuery queryObject = PersonGraphQLQuery.buildPersonQueryById(personEmail);
		return getPersonWithQuery(queryObject);
	}

	/**
	 * Get Person Object with GraphQL Query
	 * 
	 * @param query
	 *            PersonGraphQLQuery with selection and return criteria
	 * @return Person for relevant query
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Person getPersonWithQuery(PersonGraphQLQuery query) throws WWException {
		setRequest(new GraphQLRequest(query));
		executeRequest();
		DataContainer container = getResultContainer().getData();
		return (Person) container.getPerson();
	}

	/**
	 * Get basic data Conversation object for relevant Conversation
	 * 
	 * @param conversationId
	 *            String conversation id
	 * @return Conversation for relevant query
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Conversation getConversation(String conversationId) throws WWException {
		ConversationGraphQLQuery queryObject = ConversationGraphQLQuery.buildStandardConversationQueryById(conversationId);
		return getConversationWithQuery(queryObject);
	}

	/**
	 * Get Conversation object with query
	 * 
	 * @param query
	 *            ConversationGraphQLQuery with selection and return criteria
	 * @return Conversation for relevant query
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Conversation getConversationWithQuery(ConversationGraphQLQuery query) throws WWException {
		setRequest(new GraphQLRequest(query));
		executeRequest();
		DataContainer container = getResultContainer().getData();
		return container.getConversation();
	}

	/**
	 * Get a Message by using a messageId
	 * 
	 * @param messageId
	 *            String id of the message
	 * @return Message details
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Message getMessageById(String messageId) throws WWException {
		MessageGraphQLQuery queryObject = MessageGraphQLQuery.buildMessageGraphQueryWithMessageId(messageId);
		setRequest(new GraphQLRequest(queryObject));
		executeRequest();
		return (Message) getResultContainer().getData().getMessage();
	}

	/**
	 * Get a Message by using a messageId
	 * 
	 * @param query
	 *            MessageGraphQLQuery with selection and return criteria
	 * @return Message details
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Message getMessageWithQuery(MessageGraphQLQuery query) throws WWException {
		setRequest(new GraphQLRequest(query));
		executeRequest();
		return (Message) getResultContainer().getData().getMessage();
	}

	/**
	 * Get basic data for Space Members for relevant Sapce
	 * 
	 * @param spaceId
	 *            String id for the Space
	 * @return Space for relevant ID
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public List<Person> getSpaceMembers(String spaceId) throws WWException {
		SpaceMembersGraphQLQuery queryObject = SpaceMembersGraphQLQuery.buildSpaceMemberGraphQueryBySpaceId(spaceId);
		return getSpaceMembersWithQuery(queryObject);
	}

	/**
	 * Get Space Members with query
	 * 
	 * @param query
	 *            SpaceMembersGraphQLQuery with selection and return criteria
	 * @return Space Members for relevant query
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public List<Person> getSpaceMembersWithQuery(SpaceMembersGraphQLQuery query) throws WWException {
		setRequest(new GraphQLRequest(query));
		executeRequest();
		DataContainer container = getResultContainer().getData();
		return container.getSpace().getMembers();
	}

	/**
	 * Gets People matching the passed ids
	 * 
	 * @param ids
	 *            List of String ids
	 * @return People matching the ids
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public List<Person> getPeople(List<String> ids) throws WWException {
		PeopleGraphQLQuery query = new PeopleGraphQLQuery();
		query.addAttribute(PeopleAttributes.ID, ids);
		setRequest(new GraphQLRequest(query));
		executeRequest();
		DataContainer container = getResultContainer().getData();
		return (List<Person>) container.getPeople();
	}

	/**
	 * Gets People matching the single word name passed. This query does not work when authenticated as an application
	 * 
	 * @param name
	 *            String single word corresponding to part of a name
	 * @return People matching the name
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public List<Person> getPeopleByName(String name) throws WWException {
		PeopleGraphQLQuery query = new PeopleGraphQLQuery();
		query.addAttribute(PeopleAttributes.NAME, name);
		setRequest(new GraphQLRequest(query));
		executeRequest();
		DataContainer container = getResultContainer().getData();
		return (List<Person>) container.getPeople();
	}

	/**
	 * Get People with query
	 * 
	 * @param query
	 *            PeopleGraphQLQuery with selection and return criteria
	 * @return Space Members for relevant query
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public List<Person> getPeopleWithQuery(PeopleGraphQLQuery query) throws WWException {
		setRequest(new GraphQLRequest(query));
		executeRequest();
		DataContainer container = getResultContainer().getData();
		return (List<Person>) container.getPeople();
	}
}
