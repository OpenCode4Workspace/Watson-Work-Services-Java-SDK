package org.opencode4workspace.mocks;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencode4workspace.IWWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.FileResponse;
import org.opencode4workspace.bo.WWQueryResponseObjectInterface;
import org.opencode4workspace.builders.BaseGraphQLMutation;
import org.opencode4workspace.graphql.DataContainer;
import org.opencode4workspace.graphql.ErrorContainer;
import org.opencode4workspace.graphql.GraphResultContainer;
import org.opencode4workspace.json.ResultParser;

import org.opencode4workspace.mocks.MockGraphQLEndpoint;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author Paul Withers
 * @since 0.6.0
 * 
 *        A class for mocking WWClient interactions, not designed for requests, only for testing and parsing responses. For testing requests, create an object based on one of the classes extending
 *        {@link BaseGraphQLMutation} and use {@link BaseGraphQLMutation#returnQuery()} to compare against the String copied from the GraphiQL IDE.
 *
 */
public class MockClient implements IWWClient {
	private static final long serialVersionUID = 1L;
	private MockGraphQLEndpoint ep;
	private String resultContent;

	/**
	 * Initialises a MockClient using a {@link MockGraphQLEndpoint} loading the relevant response
	 * 
	 * @param response
	 *            String response copied from the GraphiQL IDE after running te relevant query
	 */
	public MockClient(String response) {
		ep = new MockGraphQLEndpoint(this);
		ep.setResultContent(response);
		ep.setResultContainer(new ResultParser<GraphResultContainer>(GraphResultContainer.class).parse(response));
	}

	/**
	 * Initialises a MockClient using a {@link MockGraphQLEndpoint} loading the relevant response
	 * 
	 * @param response
	 *            String response copied from the GraphiQL IDE after running te relevant query
	 * @throws WWException Error in parsing response
	 */
	public MockClient(String response, Map<String, WWQueryResponseObjectInterface> aliases) throws WWException {
		ep = new MockGraphQLEndpoint(this);
		ep.setResultContent(response);
		ep.setResultContainer(new ResultParser<GraphResultContainer>(GraphResultContainer.class).parse(response));
		Gson gson = new Gson();
		JsonObject resultAsJson = gson.fromJson(getResultContent(), JsonObject.class);
		JsonElement dataAsJson = resultAsJson.get("data").getAsJsonObject();
		Map<String, Object> aliasedChildren = new HashMap<String, Object>();
		for (String alias : aliases.keySet()) {
			JsonElement obj = (JsonObject) dataAsJson.getAsJsonObject().get(alias);
			Object returnObj = null;
			if (null != obj) {
				returnObj = aliases.get(alias).parse(obj.toString());
			}
			aliasedChildren.put(alias, returnObj);
		}
		ep.getResultContainer().getData().setAliasedChildren(aliasedChildren);
	}
	
	/**
	 * Parses the response from posting a file to a space and returns a FileResponse 
	 * 
	 * @param response String response from posting a file
	 * @return FileResponse parsed from parameter
	 * @since 0.7.0
	 */
	public static FileResponse parseFileResponseOutcome(String response) {
		return new ResultParser<FileResponse>(FileResponse.class).parse(response);
	}

	/**
	 * @return MockGraphQLEndpoint mock endpoint
	 */
	public MockGraphQLEndpoint getEndpoint() {
		return ep;
	}

	/**
	 * @return DataContainer loaded into the MockEndpoint
	 * @throws WWException
	 *             should never occur!
	 * 
	 * @since 0.6.0
	 */
	public DataContainer getData() throws WWException {
		return getEndpoint().getResultContainer().getData();
	}

	/**
	 * @return List of ErrorContainer objects loaded into the MockEndpoint
	 * 
	 * @since 0.6.0
	 */
	public List<ErrorContainer> getErrors() {
		return getEndpoint().getResultContainer().getErrors();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.IWWClient#getClientType()
	 */
	@Override
	public ClientType getClientType() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.IWWClient#getAppCredentials()
	 */
	@Override
	public String getAppCredentials() throws UnsupportedEncodingException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.IWWClient#isAuthenticated()
	 */
	@Override
	public boolean isAuthenticated() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.IWWClient#authenticate()
	 */
	@Override
	public void authenticate() throws UnsupportedEncodingException, WWException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.IWWClient#getJWTToken()
	 */
	@Override
	public String getJWTToken() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.IWWClient#getExpiresIn()
	 */
	@Override
	public Object getExpiresIn() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.opencode4workspace.IWWClient#getExpireDate()
	 */
	public Date getExpireDate() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.IWWClient#isValid()
	 */
	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public String getResultContent() {
		return resultContent;
	}

	@Override
	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}

	@Override
	public String getUserRefreshToken() {
		return null;
	}

}
