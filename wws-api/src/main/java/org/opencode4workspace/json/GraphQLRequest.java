package org.opencode4workspace.json;

import java.util.HashMap;

import org.opencode4workspace.WWException;
import org.opencode4workspace.graphql.builders.BaseGraphQLQuery;

/**
 * @author Christian Guedemann
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

	public GraphQLRequest(BaseGraphQLQuery queryObject) throws WWException {
		this(queryObject, new HashMap<String, String>());
	}

	public GraphQLRequest(BaseGraphQLQuery queryObject, HashMap<String, String> variables) throws WWException {
		try {
			operationName = queryObject.getOperationName();
			this.query = queryObject.returnQuery();
			this.variables = variables;
		} catch (Exception e) {
			throw new WWException("Error creating request from query - " + e.getMessage());
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
