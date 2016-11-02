package org.opencode4workspace.json;

import java.util.HashMap;

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
