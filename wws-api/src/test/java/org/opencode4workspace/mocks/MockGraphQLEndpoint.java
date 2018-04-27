package org.opencode4workspace.mocks;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.opencode4workspace.IWWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.endpoints.AbstractWWGraphQLEndpoint;
import org.opencode4workspace.endpoints.WWDefinedEndpoints;

public class MockGraphQLEndpoint extends AbstractWWGraphQLEndpoint {

	public MockGraphQLEndpoint(IWWClient client) {
		super(client);
	}
	
	@Override
	public void executeRequest() throws WWException {
		throw new UnsupportedOperationException("Method not allowed in mocks");
	}
	
	public HttpPost preparePost() {
		HttpPost post = new HttpPost(WWDefinedEndpoints.GRAPHQL);
		post.addHeader("Authorization", "Bearer " + getClient().getJWTToken());
		post.addHeader("content-type", ContentType.APPLICATION_JSON.toString());
		// When we support experimental and future, change this to "else if" following those checks
		if (getRequest().isBeta()) {
			post.addHeader("x-graphql-view", "PUBLIC, BETA");
		}
		return post;
	}

}
