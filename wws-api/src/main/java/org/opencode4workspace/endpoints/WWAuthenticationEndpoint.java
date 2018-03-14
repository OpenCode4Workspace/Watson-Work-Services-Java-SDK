package org.opencode4workspace.endpoints;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
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

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Authentication endpoint, generated POST requests for authentication
 *
 */
public class WWAuthenticationEndpoint implements AuthenticationEndpoint {
	
	/* (non-Javadoc)
	 * @see org.opencode4workspace.authentication.AuthenticationEndpoint#authenticateApplication(java.lang.String)
	 */
	@Override
	public AuthenticationResult authenticateApplication(String basicAuthApp) throws WWException {
		CloseableHttpClient client = HttpClients.createDefault();
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		HttpPost post = preparePost(basicAuthApp);
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
				throw new WWException("Failure during login - " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase() + ", response was " + EntityUtils.toString(response.getEntity()));
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
	 *            String, Authorization header constructed from {@link WWClient#getAppCredentials()}
	 * @return HttpPost containing endpoint for authentication and required headers
	 * 
	 * @since 0.5.0
	 */
	private HttpPost preparePost(String basicAuth) {
		HttpPost post = new HttpPost(WWDefinedEndpoints.AUTHENTICATION);
		post.addHeader("Authorization", basicAuth);
		post.addHeader("content-type", ContentType.APPLICATION_FORM_URLENCODED.toString());
		return post;
	}
	
	/* (non-Javadoc)
	 * @see org.opencode4workspace.authentication.AuthenticationEndpoint#authorizeUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	public AuthenticationResult authorizeUser(String basicAuthApp, String userToken, String url) throws WWException {
		PeopleToken peopleToken = authorizeUserGetToken(basicAuthApp, userToken, url);
		return AuthenticationResult.buildFromToken(peopleToken);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.authentication.AuthenticatenEndpoint#authorizeUser (java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public PeopleToken authorizeUserGetToken(String basicAuthApp, String userToken, String url) throws WWException {
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
				peopleToken.setCreatedDate(new Date());
				return peopleToken;
			} else {
				throw new WWException("Failure during login - " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase() + ", response was " + EntityUtils.toString(response.getEntity()));
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
	
	/* (non-Javadoc)
	 * @see org.opencode4workspace.authentication.AuthenticationEndpoint#authorizeUserRefreshToken(java.lang.String, java.lang.String, java.lang.String)
	 */
	public AuthenticationResult authorizeUserRefreshToken(String basicAuthApp, String refreshToken, String scope) throws WWException {
		PeopleToken peopleToken = authorizeUserRefreshTokenGetToken(basicAuthApp, refreshToken, scope);
		return AuthenticationResult.buildFromToken(peopleToken);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.authentication.AuthenticatenEndpoint#authorizeUser (java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public PeopleToken authorizeUserRefreshTokenGetToken(String basicAuthApp, String refreshToken, String scope) throws WWException {
		CloseableHttpClient client = HttpClients.createDefault();
		List<NameValuePair> params = new ArrayList<NameValuePair>(3);
		HttpPost post = preparePost(basicAuthApp);
		params.add(new BasicNameValuePair("grant_type", "refresh_token"));
		params.add(new BasicNameValuePair("refresh_token", refreshToken));
		params.add(new BasicNameValuePair("scope", scope));
		CloseableHttpResponse response = null;
		try {

			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(response.getEntity());
				PeopleToken peopleToken = new ResultParser<PeopleToken>(PeopleToken.class).parse(content);
				peopleToken.setCreatedDate(new Date());
				return peopleToken;
			} else {
				throw new WWException("Failure during login - " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase() + ", response was " + EntityUtils.toString(response.getEntity()));
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
