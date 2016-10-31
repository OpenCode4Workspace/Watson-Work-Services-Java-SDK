package org.opencode4workspace.json;

public class GraphQLRequest {

	private String query;
	private String variables;
	private String operationName;

	public GraphQLRequest(String query, String variables, String operationName) {
		super();
		this.query = query;
		this.variables = variables;
		this.operationName = operationName;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getVariables() {
		return variables;
	}

	public void setVariables(String variables) {
		this.variables = variables;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

}
