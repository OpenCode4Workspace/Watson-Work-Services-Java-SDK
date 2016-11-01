package org.opencode4workspace.endpoints;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.graphql.GraphResultContainer;
import org.opencode4workspace.json.GraphQLRequest;

public interface IWWGraphQLEndpoint {

	WWClient getClient();

	GraphQLRequest getRequest();

	void setRequest(GraphQLRequest request);

	GraphResultContainer getResultContainer();

	void setResultContainer(GraphResultContainer resultContainer);

	void executeRequest() throws WWException;

	Object parseResultContainer() throws WWException;

}