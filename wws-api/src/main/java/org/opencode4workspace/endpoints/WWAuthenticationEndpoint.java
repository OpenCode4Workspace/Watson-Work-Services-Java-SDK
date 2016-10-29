package org.opencode4workspace.endpoints;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.opencode4workspace.WWException;
import org.opencode4workspace.authentication.AuthenticatenEndpoint;
import org.opencode4workspace.authentication.AuthenticationResult;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class WWAuthenticationEndpoint implements AuthenticatenEndpoint {

	@Override
	public AuthenticationResult authenticateApplication(String basicAuth) throws WWException {
		CloseableHttpClient client = HttpClients.createDefault();
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		HttpPost post = preparePost(basicAuth);
		params.add(new BasicNameValuePair("grant_type", "client_credentials"));
		CloseableHttpResponse response = null;
		try {

			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(response.getEntity());
				System.out.println(content);
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
		return null;
	}

	private HttpPost preparePost(String basicAuth) {
		HttpPost post = new HttpPost("https://api.watsonwork.ibm.com/oauth/token");
		System.out.println(basicAuth);
		post.addHeader("Authorization", basicAuth);
		post.addHeader("content-type", "application/x-www-form-urlencoded");
		return post;
	}

	@Override
	public AuthenticationResult authorizeUser(String basicAuthApp, String userToken, String url) throws WWException {
		CloseableHttpClient client = HttpClients.createDefault();
		List<NameValuePair> params = new ArrayList<NameValuePair>(3);
		HttpPost post = preparePost(basicAuthApp);
		params.add(new BasicNameValuePair("grant_type", "authorization_code"));
		params.add(new BasicNameValuePair("code", userToken));
		params.add(new BasicNameValuePair("redirect_uri", url));
		CloseableHttpResponse response = null;
		try {

			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(response.getEntity());
				JsonParser jp = new JsonParser();
				JsonElement je = jp.parse(content);
			} else {
				throw new WWException("Failuer during login" + response.getStatusLine().getStatusCode());
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
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
