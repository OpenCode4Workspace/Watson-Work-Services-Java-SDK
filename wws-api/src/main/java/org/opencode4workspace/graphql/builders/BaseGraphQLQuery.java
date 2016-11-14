package org.opencode4workspace.graphql.builders;

import java.io.Serializable;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Abstract object for creating GraphQL Query. Use one of the in-built options
 *
 */
public class BaseGraphQLQuery implements Serializable {

	private static final long serialVersionUID = 1L;
	private String operationName;
	private ObjectDataBringer queryObject;

	public BaseGraphQLQuery() {

	}

	public BaseGraphQLQuery(String operationName) {
		this.operationName = operationName;
	}

	public BaseGraphQLQuery(String operationName, ObjectDataBringer queryObject) {
		this.operationName = operationName;
		this.queryObject = queryObject;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public ObjectDataBringer getQueryObject() {
		return queryObject;
	}

	public void setQueryObject(ObjectDataBringer queryObject) {
		this.queryObject = queryObject;
	}

	public String returnQuery() {
		return "query " + operationName + " {" + getQueryObject().buildQuery() + "}";
	}

}
