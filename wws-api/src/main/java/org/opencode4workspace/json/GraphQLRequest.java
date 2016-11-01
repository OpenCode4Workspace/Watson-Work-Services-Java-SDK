package org.opencode4workspace.json;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 *
 *        A class for handling GraphQL requests
 * 
 */
public class GraphQLRequest {

	private String query;
	private String variables;
	private String operationName;

	/**
	 * Initialises the GraphQLRequest object from all properties
	 * 
	 * @param query
	 *            String, query passed
	 * @param variables
	 *            String, any dynamic variables passed
	 * @param operationName
	 *            String, operation name for the query
	 */
	public GraphQLRequest(String query, String variables, String operationName) {
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
	 * @return String, dynamic variables passed
	 */
	public String getVariables() {
		return variables;
	}

	/**
	 * @param variables
	 *            String, dynamic variables passed
	 */
	public void setVariables(String variables) {
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
