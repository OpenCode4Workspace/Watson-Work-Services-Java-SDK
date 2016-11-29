package org.opencode4workspace.endpoints;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
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

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Abstract default implementation of IWWGraphQLEndpoint interface. In any overloaded object, a result needs constructing and passing into the object. The {@linkplain #parseResultContainer()}
 *        method needs to be overloaded.
 *
 */
public abstract class AbstractWWGraphQLEndpoint implements IWWGraphQLEndpoint {

	private final WWClient client;
	private GraphQLRequest request;
	private GraphResultContainer resultContainer;
	private String resultContent;
	private Boolean profileDump = false;

	/**
	 * @param client
	 *            WWClient containing authentication details and token
	 */
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

	/**
	 * Tests the token against its expireDate.
	 * 
	 * @return boolean, whether or not the token should be valid
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

	/**
	 * @return String raw JSON results as String
	 */
	public String getResultContent() {
		return resultContent;
	}

	/**
	 * @param resultContent
	 *            String raw JSON results as string
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
	 */
	private HttpPost preparePost() {
		HttpPost post = new HttpPost(WWDefinedEndpoints.GRAPHQL);
		post.addHeader("jwt", getClient().getJWTToken());
		post.addHeader("content-type", ContentType.APPLICATION_JSON.toString());
		return post;
	}

	/**
	 * @return Boolean whether or not to dump profile data of query and response time
	 */
	public Boolean getProfileDump() {
		return profileDump;
	}

	/**
	 * @param profileDump
	 *            Boolean whether or not to dump profile data of query and response time
	 */
	public void setProfileDump(Boolean profileDump) {
		this.profileDump = profileDump;
	}

}
