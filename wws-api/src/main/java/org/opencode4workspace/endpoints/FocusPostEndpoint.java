package org.opencode4workspace.endpoints;

import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.opencode4workspace.IWWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Focus;
import org.opencode4workspace.bo.FocusResponseContainer;
import org.opencode4workspace.json.ResultParser;

import com.google.gson.JsonObject;

/**
 * @author Paul Withers
 * @since 0.8.0
 * 
 *        Endpoint for posting text to allow Watson Work Services to interrogate for focuses
 *
 */
public class FocusPostEndpoint extends AbstractWWGraphQLEndpoint {

	/**
	 * @param client
	 *            WWClient containing authentication details and token
	 */
	public FocusPostEndpoint(IWWClient client) {
		super(client);
	}

	/**
	 * Posts the passed text for analysis of focuses
	 * 
	 * @param text String for Watson Work Services to analyse
	 * @return List of Focus objects based on analysis
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.8.0
	 */
	public List<Focus> postMessage(String text) throws WWException {
		HttpPost post = preparePost();
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			if (!isShouldBeValid()) {
				getClient().authenticate();
			}
			JsonObject jjo = new JsonObject();
			jjo.addProperty("text", text);
			StringEntity postPayload = new StringEntity(jjo.toString(), "UTF-8");
			post.setEntity(postPayload);
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 201) {
				String content = EntityUtils.toString(response.getEntity());
				FocusResponseContainer container = new ResultParser<FocusResponseContainer>(FocusResponseContainer.class).parse(content);
				return container.getFocuses();
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
	 * @return HttpPost containing the relevant headers
	 * 
	 * @since 0.8.0
	 */
	private HttpPost preparePost() {
		HttpPost post = new HttpPost(WWDefinedEndpoints.V1_FOCUS);
		post.addHeader("Authorization", "Bearer " + getClient().getJWTToken());
		post.addHeader("content-type", ContentType.APPLICATION_JSON.toString());
		return post;
	}
}
