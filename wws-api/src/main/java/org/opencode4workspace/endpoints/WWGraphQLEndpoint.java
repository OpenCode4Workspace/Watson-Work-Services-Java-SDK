package org.opencode4workspace.endpoints;

import java.util.List;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Person;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.builders.ConversationGraphQLQuery;
import org.opencode4workspace.builders.PeopleGraphQLQuery;
import org.opencode4workspace.builders.PeopleGraphQLQuery.PeopleAttributes;
import org.opencode4workspace.builders.PersonGraphQLQuery;
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
	 */
	public List<? extends Space> getSpacesWithQuery(SpacesGraphQLQuery query) throws WWException {
		setRequest(new GraphQLRequest(query));
		executeRequest();
		return (List<? extends Space>) getResultContainer().getData().getSpaces().getItems();
	}

	/**
	 * Simplified access method, to load GraphQL query for getting Me object
	 * 
	 * @return Person object relating to "me"
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 */
	public Person getMe() throws WWException {
		PersonGraphQLQuery queryObject = PersonGraphQLQuery.buildMyProfileQuery();
		setRequest(new GraphQLRequest(queryObject));
		executeRequest();
		return (Person) getResultContainer().getData().getMe();
	}

	/**
	 * Simplified access method, to load GraphQL query for getting Me object
	 * 
	 * @param personId
	 *            String, WWS id of the Person to return
	 * @return Person for relevant ID
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 */
	public Person getPerson(String personId) throws WWException {
		PersonGraphQLQuery queryObject = PersonGraphQLQuery.buildPersonQueryById(personId);
		return getPersonWithQuery(queryObject);
	}

	/**
	 * Get Person Object with GraphQL Query
	 * 
	 * @param query
	 *            PersonGraphQLQuery with selection and return criterias
	 * @return Person for relevant query
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 */
	public Person getPersonWithQuery(PersonGraphQLQuery query) throws WWException {
		setRequest(new GraphQLRequest(query));
		executeRequest();
		DataContainer container = getResultContainer().getData();
		return (Person) container.getPerson();
	}

	public Conversation getConversation(String conversationId) throws WWException {
		ConversationGraphQLQuery queryObject = ConversationGraphQLQuery.buildStandardConversationQueryById(conversationId);
		return getConversationWithQuery(queryObject);
	}

	public Conversation getConversationWithQuery(ConversationGraphQLQuery query) throws WWException {
		setRequest(new GraphQLRequest(query));
		executeRequest();
		DataContainer container = getResultContainer().getData();
		return container.getConversation();
	}

	public List<Person> getSpaceMembers(String spaceId) throws WWException {
		SpaceMembersGraphQLQuery queryObject = SpaceMembersGraphQLQuery.buildSpaceMemberGraphQueryBySpaceId(spaceId);
		return getSpaceMembersWithQuery(queryObject);
	}

	public List<Person> getSpaceMembersWithQuery(SpaceMembersGraphQLQuery query) throws WWException {
		setRequest(new GraphQLRequest(query));
		executeRequest();
		DataContainer container = getResultContainer().getData();
		return container.getSpace().getMembers();
	}

	public List<Person> getPeople(List<String> ids) throws WWException {
		PeopleGraphQLQuery query = new PeopleGraphQLQuery();
		query.addAttribute(PeopleAttributes.ID, ids);
		setRequest(new GraphQLRequest(query));
		DataContainer container = getResultContainer().getData();
		return (List<Person>) container.getPeople();
	}
}
