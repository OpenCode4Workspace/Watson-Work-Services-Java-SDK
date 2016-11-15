package org.opencode4workspace.endpoints;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.MessageResponse;
import org.opencode4workspace.json.RequestBuilder;
import org.opencode4workspace.json.ResultParser;

public class MessagePostEndpoint {

	private final WWClient client;

	public MessagePostEndpoint(WWClient client) {
		this.client = client;
	}

	public MessageResponse postMessage(AppMessage message, String spaceId) throws WWException {
		HttpPost post = preparePost(client.getJWTToken(), spaceId);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			StringEntity postPayload = new StringEntity(new RequestBuilder<AppMessage>(AppMessage.class).buildJson(message), "UTF-8");
			System.out.println("Post payload: "+ EntityUtils.toString( postPayload));
			post.setEntity(postPayload);
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 201) {
				String content = EntityUtils.toString(response.getEntity());
				System.out.println(content);
				MessageResponse messageResponse = new ResultParser<MessageResponse>(MessageResponse.class).parse(content);
				return messageResponse;
			} else {
				System.out.println("Response was: "+ EntityUtils.toString(response.getEntity()));
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

	private HttpPost preparePost(String jwtToken, String spaceId) {
		HttpPost post = new HttpPost("https://api.watsonwork.ibm.com//v1/spaces/" + spaceId + "/messages");
		post.addHeader("Authorization", "Bearer " + jwtToken);
		post.addHeader("content-type", "application/json");
		return post;
	}
}
