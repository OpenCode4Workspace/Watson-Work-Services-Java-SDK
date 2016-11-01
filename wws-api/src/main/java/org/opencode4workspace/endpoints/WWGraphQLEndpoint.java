package org.opencode4workspace.endpoints;

import java.util.List;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.json.GraphQLRequest;

public class WWGraphQLEndpoint extends AbstractWWGraphQLEndpoint {

	private static final String GET_SPACES_QUERY = "query getSpaces {spaces(first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage}items {id title description updated updatedBy {id displayName photoUrl email} created createdBy {id displayName photoUrl email} members(first: 100) {items {id photoUrl email displayName} } conversation {id created createdBy {id displayName photoUrl email} updated updatedBy {id displayName photoUrl email} messages(first: 20) { pageInfo {startCursor  endCursor hasPreviousPage hasNextPage} items {contentType content id created updated createdBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email}}}}}}}";

	public WWGraphQLEndpoint(WWClient client) {
		super(client);
	}

	@SuppressWarnings("unchecked")
	public List<? extends Space> getSpaces() throws WWException {
		setRequest(new GraphQLRequest(GET_SPACES_QUERY, null, "getSpaces"));
		executeRequest();
		return (List<? extends Space>) parseResultContainer();
	}

	public Object parseResultContainer() {
		return getResultContainer().getData().getSpaces().getItems();
	}

}
