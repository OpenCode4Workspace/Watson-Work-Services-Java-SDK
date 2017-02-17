package org.opencode4workspace.endpoints;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.opencode4workspace.IWWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.graphql.ErrorContainer;
import org.opencode4workspace.graphql.GraphResultContainer;
import org.opencode4workspace.json.GraphQLRequest;
import org.opencode4workspace.json.RequestBuilder;
import org.opencode4workspace.json.ResultParser;

/**
 * @author Paul Withers
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Abstract default implementation of IWWGraphQLEndpoint interface. In any overloaded object, a result needs constructing and passing into the object. The {@link #parseResultContainer()} method
 *        needs to be overloaded.
 *
 */
public abstract class AbstractWWGraphQLEndpoint implements IWWGraphQLEndpoint {

	private final IWWClient client;
	private GraphQLRequest request;
	private GraphResultContainer resultContainer;
	private String resultContent;
	private Boolean profileDump = false;

	/**
	 * @param client
	 *            WWClient containing authentication details and token
	 * 
	 * @since 0.5.0
	 */
	public AbstractWWGraphQLEndpoint(IWWClient client) {
		this.client = client;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.endpoints.IWWGraphQLEndpoint#getClient()
	 */
	@Override
	public IWWClient getClient() {
		return client;
	}

	/**
	 * Tests the token against its expireDate.
	 * 
	 * @return boolean, whether or not the token should be valid
	 * 
	 * @since 0.5.0
	 */
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
		HttpPost post = preparePost();
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			StringEntity postPayload = new StringEntity(new RequestBuilder<GraphQLRequest>(GraphQLRequest.class).buildJson(request), "UTF-8");
			post.setEntity(postPayload);
			if (getProfileDump()) {
				System.out.println("[WWS Profiler] Query is " + postPayload);
			}
			long start = System.nanoTime();
			response = client.execute(post);
			if (getProfileDump()) {
				long elapsed = System.nanoTime() - start;
				System.out.println("[WWS Profiler] Query took " + elapsed / 1000000 + "ms");
			}
			if (response.getStatusLine().getStatusCode() == 200) {
				// TODO: Handle if we need to re-authenticate
				String content = EntityUtils.toString(response.getEntity());
				setResultContent(content);
				setResultContainer(new ResultParser<GraphResultContainer>(GraphResultContainer.class).parse(content));
				if (null != getResultContainer().getErrors()) {
					ErrorContainer error = getResultContainer().getErrors().get(0);
					if ("403 Forbidden".equals(error.getMessage())) {
						throw new WWException("The operation was disallowed: " + error.getField().get("name"));
					}
				}
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
	 * @see org.opencode4workspace.endpoints.IWWGraphQLEndpoint#getResultContent()
	 */
	public String getResultContent() {
		return resultContent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.endpoints.IWWGraphQLEndpoint#setResultContent(java.lang.String)
	 */
	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
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

	/**
	 * Prepares the HttpPost with relevant headers - JWT Token and Content-Type
	 * 
	 * @return HttpPost containing the relevant headers
	 * 
	 * @since 0.5.0
	 */
	private HttpPost preparePost() {
		HttpPost post = new HttpPost(WWDefinedEndpoints.GRAPHQL);
		post.addHeader("Authorization", "Bearer " + getClient().getJWTToken());
		post.addHeader("content-type", ContentType.APPLICATION_JSON.toString());
		return post;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.endpoints.IWWGraphQLEndpoint#getProfileDump()
	 */
	public Boolean getProfileDump() {
		return profileDump;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.endpoints.IWWGraphQLEndpoint#setProfileDump(java.lang.Boolean)
	 */
	public void setProfileDump(Boolean profileDump) {
		this.profileDump = profileDump;
	}

}
