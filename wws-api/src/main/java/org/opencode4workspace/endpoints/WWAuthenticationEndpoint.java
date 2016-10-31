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
import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.authentication.AppToken;
import org.opencode4workspace.authentication.AuthenticationEndpoint;
import org.opencode4workspace.authentication.AuthenticationResult;
import org.opencode4workspace.authentication.PeopleToken;
import org.opencode4workspace.json.ResultParser;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Authentication endpoint, generated POST requests for authentication
 *
 */
public class WWAuthenticationEndpoint implements AuthenticationEndpoint {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.authentication.AuthenticatenEndpoint#
	 * authenticateApplication(java.lang.String)
	 */
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
				AppToken appToken = new ResultParser<AppToken>(AppToken.class).parse(content);
				return AuthenticationResult.buildFromToken(appToken);
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
	 * Creates a Post request and adds required header details
	 * 
	 * @param basicAuth
	 *            String, Authorization header constructed from
	 *            {@linkplain WWClient#getAppCredentials()}
	 * @return HttpPost containing endpoint for authentication and required
	 *         headers
	 * 
	 * @since 0.5.0
	 */
	private HttpPost preparePost(String basicAuth) {
		HttpPost post = new HttpPost("https://api.watsonwork.ibm.com/oauth/token");
		post.addHeader("Authorization", basicAuth);
		post.addHeader("content-type", "application/x-www-form-urlencoded");
		return post;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opencode4workspace.authentication.AuthenticatenEndpoint#authorizeUser
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
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
				PeopleToken peopleToken = new ResultParser<PeopleToken>(PeopleToken.class).parse(content);
				return AuthenticationResult.buildFromToken(peopleToken);
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
	}

}
