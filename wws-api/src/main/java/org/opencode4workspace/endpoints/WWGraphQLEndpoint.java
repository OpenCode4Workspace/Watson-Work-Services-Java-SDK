package org.opencode4workspace.endpoints;

import java.util.List;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.builders.SpacesGraphQLQuery;
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
	 * @return List<? extending Space> of Space details
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 */
	@SuppressWarnings("unchecked")
	public List<? extends Space> getSpaces() throws WWException {

		SpacesGraphQLQuery queryObject = new SpacesGraphQLQuery();
		setRequest(new GraphQLRequest(queryObject));
		executeRequest();
		return (List<? extends Space>) parseResultContainer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.endpoints.AbstractWWGraphQLEndpoint#parseResultContainer()
	 */
	public Object parseResultContainer() {
		return getResultContainer().getData().getSpaces().getItems();
	}

}
