package org.opencode4workspace.endpoints;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.graphql.GraphResultContainer;
import org.opencode4workspace.json.GraphQLRequest;
import org.opencode4workspace.json.RequestBuilder;
import org.opencode4workspace.json.ResultParser;

public abstract class AbstractWWGraphQLEndpoint implements IWWGraphQLEndpoint {

	private final WWClient client;
	private GraphQLRequest request;
	private GraphResultContainer resultContainer;

	public AbstractWWGraphQLEndpoint(WWClient client) {
		this.client = client;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.endpoints.IWWGraphQLEndpoint#getClient()
	 */
	@Override
	public WWClient getClient() {
		return client;
	}

	private boolean isShouldBeValid() {
		if (getClient().isValid()) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.endpoints.IWWGraphQLEndpoint#getRequest()
	 */
	@Override
	public GraphQLRequest getRequest() {
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.endpoints.IWWGraphQLEndpoint#setRequest(org.opencode4workspace.json.GraphQLRequest)
	 */
	@Override
	public void setRequest(GraphQLRequest request) {
		this.request = request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.endpoints.IWWGraphQLEndpoint#getResultContainer()
	 */
	@Override
	public GraphResultContainer getResultContainer() {
		return resultContainer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.endpoints.IWWGraphQLEndpoint#setResultContainer(org.opencode4workspace.graphql.GraphResultContainer)
	 */
	@Override
	public void setResultContainer(GraphResultContainer resultContainer) {
		this.resultContainer = resultContainer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.endpoints.IWWGraphQLEndpoint#executeRequest()
	 */
	@Override
	public void executeRequest() throws WWException {
		if (null == getRequest()) {
			throw new WWException("A GraphQLRequest object must be loaded before calling the 'execute' method");
		}
		HttpPost post = preparePost(getClient().getJWTToken());
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			StringEntity postPayload = new StringEntity(new RequestBuilder<GraphQLRequest>(GraphQLRequest.class).buildJson(request), "UTF-8");
			post.setEntity(postPayload);
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				// TODO: Handle if we need to re-authenticate
				String content = EntityUtils.toString(response.getEntity());
				setResultContainer(new ResultParser<GraphResultContainer>(GraphResultContainer.class).parse(content));
			} else {
				throw new WWException("Failure during login" + response.getStatusLine().getReasonPhrase());
			}
		} catch (Exception e) {
			throw new WWException(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.endpoints.IWWGraphQLEndpoint#parseResultContainer()
	 */
	@Override
	public Object parseResultContainer() throws WWException {
		if (null == getResultContainer()) {
			throw new WWException("No result returned for query");
		} else {
			throw new UnsupportedOperationException("Method must be overloaded");
		}
	}

	private HttpPost preparePost(String jwtToken) {
		HttpPost post = new HttpPost(WWDefinedEndpoints.GRAPHQL);
		post.addHeader("jwt", jwtToken);
		post.addHeader("content-type", "application/json");
		return post;
	}

}
