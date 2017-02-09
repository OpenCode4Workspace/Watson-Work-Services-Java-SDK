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
import org.opencode4workspace.bo.MessageResponse;
import org.opencode4workspace.json.RequestBuilder;
import org.opencode4workspace.json.ResultParser;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Endpoint for posting a message
 *
 */
public class MessagePostEndpoint extends AbstractWWGraphQLEndpoint {

	/**
	 * @param client
	 *            WWClient containing authentication details and token
	 */
	public MessagePostEndpoint(IWWClient client) {
		super(client);
	}

	/**
	 * String posts the passed message to the passed Space
	 * 
	 * @param message
	 *            AppMessage message to post
	 * @param spaceId
	 *            String id of the space to post to
	 * @return MessageResponse response object for the successful posting
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public MessageResponse postMessage(AppMessage message, String spaceId) throws WWException {
		// TODO: If we pass the message and space id to the constructor, we may be able to make this more generic, and pass the response to resultContainer, then call parseResultContainer
		HttpPost post = preparePost(spaceId);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			StringEntity postPayload = new StringEntity(new RequestBuilder<AppMessage>(AppMessage.class).buildJson(message), "UTF-8");
			post.setEntity(postPayload);
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 201) {
				String content = EntityUtils.toString(response.getEntity());
				MessageResponse messageResponse = new ResultParser<MessageResponse>(MessageResponse.class).parse(content);
				return messageResponse;
			} else {
				throw new WWException("Execution failed: " + response.getStatusLine().getReasonPhrase());
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
	 * @param spaceId
	 *            String id of the space to post to
	 * @return HttpPost containing the relevant headers
	 * 
	 * @since 0.5.0
	 */
	private HttpPost preparePost(String spaceId) {
		HttpPost post = new HttpPost(WWDefinedEndpoints.V1_SPACE_ID + spaceId + "/messages");
		post.addHeader("Authorization", "Bearer " + getClient().getJWTToken());
		post.addHeader("content-type", ContentType.APPLICATION_JSON.toString());
		return post;
	}
}
