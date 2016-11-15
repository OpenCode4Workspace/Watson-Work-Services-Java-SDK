package org.opencode4workspace.json;

import java.util.HashMap;

import org.opencode4workspace.WWException;
import org.opencode4workspace.builders.BaseGraphQLQuery;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 *
 *        A class for handling GraphQL requests
 * 
 */
public class GraphQLRequest {

	private String query;
	private HashMap<String, String> variables;
	private String operationName;

	/**
	 * Initialises the GraphQLRequest object from all properties
	 * 
	 * @param query
	 *            String, query passed
	 * @param variables
	 *            HashMap, dynamic variables passed with key as variable name, value as variable value
	 * @param operationName
	 *            String, operation name for the query
	 */
	public GraphQLRequest(String query, HashMap<String, String> variables, String operationName) {
		super();
		this.query = query;
		this.variables = variables;
		this.operationName = operationName;
	}

	/**
	 * Initialises the GraphQLRequest object from a BaseGraphQLQuery object. This will have the operation name and will not pass any variables to Watson Work Services
	 * 
	 * @param queryObject
	 *            BaseGraphQLQuery containing an {@linkplain ObjectDataSenderBuilder} which will be parsed to return the full query
	 * @throws WWException
	 *             error if encountered parsing the queryObject
	 */
	public GraphQLRequest(BaseGraphQLQuery queryObject) throws WWException {
		this(queryObject, new HashMap<String, String>());
	}

	/**
	 * Initialises the GraphQLRequest object from a BaseGraphQLQuery object. This will have the operation name.
	 * 
	 * @param queryObject
	 *            BaseGraphQLQuery containing an {@linkplain ObjectDataSenderBuilder} which will be parsed to return the full query
	 * @param variables
	 *            HashMap<String, String> of variables to pass with the query
	 * @throws WWException
	 *             error if encountered parsing the queryObject
	 */
	public GraphQLRequest(BaseGraphQLQuery queryObject, HashMap<String, String> variables) throws WWException {
		try {
			this.operationName = queryObject.getOperationName();
			this.query = queryObject.returnQuery();
			this.variables = variables;
		} catch (Exception e) {
			throw new WWException("Error creating request from query - " + e.getMessage());
		}
	}

	/**
	 * Initialises the GraphQLRequest from an ObjectDataBringer and operation name. Those will be combined to build the String query. This will not pass any variables to Watson Work Services.
	 * 
	 * @param queryObject
	 *            ObjectDataBringer containing the query settings
	 * @param operationName
	 *            String operationName to use
	 * @throws WWException
	 *             error if encountered parsing the queryObject
	 */
	public GraphQLRequest(ObjectDataSenderBuilder queryObject, String operationName) throws WWException {
		this(queryObject, new HashMap<String, String>(), operationName);
	}

	/**
	 * Initialises the GraphQLRequest from an ObjectDataBringer, operation name and variables. Those will be combined to build the String query.
	 * 
	 * @param queryObject
	 *            ObjectDataBringer containing the query settings
	 * @param variables
	 *            HashMap<String, String> of variables to pass with the query
	 * @param operationName
	 *            String operationName to use
	 * @throws WWException
	 *             error if encountered parsing the queryObject
	 */
	public GraphQLRequest(ObjectDataSenderBuilder queryObject, HashMap<String, String> variables, String operationName) throws WWException {
		try {
			this.operationName = operationName;
			this.query = "query " + operationName + " {" + queryObject.build() + "}";
			this.variables = variables;
		} catch (Exception e) {
			throw new WWException("Error creating request from ObjectDataSender - " + e.getMessage());
		}
	}

	/**
	 * @return String, query passed
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query
	 *            String, query passed
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return HashMap, dynamic variables passed with key as variable name, value as variable value
	 */
	public HashMap<String, String> getVariables() {
		return variables;
	}

	/**
	 * @param variables
	 *            HashMap, dynamic variables passed with key as variable name, value as variable value
	 */
	public void setVariables(HashMap<String, String> variables) {
		this.variables = variables;
	}

	/**
	 * @return String, name of operation being performed
	 */
	public String getOperationName() {
		return operationName;
	}

	/**
	 * @param operationName
	 *            String, name of operation being performed
	 */
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

}
