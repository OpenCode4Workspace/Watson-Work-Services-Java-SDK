package org.opencode4workspace.endpoints;

import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.graphql.GraphResultContainer;
import org.opencode4workspace.json.GraphQLRequest;
import org.opencode4workspace.json.RequestBuilder;
import org.opencode4workspace.json.ResultParser;

public class WWGraphQLEndpoint {

	private static final String GET_SPACES_QUERY = "query getSpaces {spaces(first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage}items {id title description updated updatedBy {id displayName photoUrl email} created createdBy {id displayName photoUrl email} members(first: 100) {items {id photoUrl email displayName} } conversation {id created createdBy {id displayName photoUrl email} updated updatedBy {id displayName photoUrl email} messages(first: 20) { pageInfo {startCursor  endCursor hasPreviousPage hasNextPage} items {contentType content id created updated createdBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email}}}}}}}";
	private final WWClient client;

	public WWGraphQLEndpoint(WWClient client) {
		this.client = client;
	}

	public List<? extends Space> getSpaces() throws WWException {
		GraphQLRequest request = new GraphQLRequest(GET_SPACES_QUERY, null, "getSpaces");
		HttpPost post = preparePost(client.getJWTToken());
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			StringEntity postPayload = new StringEntity(new RequestBuilder<GraphQLRequest>(GraphQLRequest.class).buildJson(request), "UTF-8");
			post.setEntity(postPayload);
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(response.getEntity());
				GraphResultContainer resultContainer = new ResultParser<GraphResultContainer>(GraphResultContainer.class).parse(content);
				return resultContainer.getData().getSpaces().getItems();
			} else {
				throw new WWException("Failuer during login" + response.getStatusLine().getReasonPhrase());
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
	
	private HttpPost preparePost(String jwtToken) {
		HttpPost post = new HttpPost("https://api.watsonwork.ibm.com/graphql");
		post.addHeader("jwt", jwtToken);
		post.addHeader("content-type", "application/json");
		return post;
	}

}
