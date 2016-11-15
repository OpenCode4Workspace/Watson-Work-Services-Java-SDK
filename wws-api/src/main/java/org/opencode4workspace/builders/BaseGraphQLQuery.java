package org.opencode4workspace.builders;

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
	private ObjectDataSenderBuilder queryObject;

	public BaseGraphQLQuery() {

	}

	public BaseGraphQLQuery(String operationName) {
		this.operationName = operationName;
	}

	public BaseGraphQLQuery(String operationName, ObjectDataSenderBuilder queryObject) {
		this.operationName = operationName;
		this.queryObject = queryObject;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public ObjectDataSenderBuilder getQueryObject() {
		return queryObject;
	}

	public void setQueryObject(ObjectDataSenderBuilder queryObject) {
		this.queryObject = queryObject;
	}

	public String returnQuery() {
		return "query " + operationName + " {" + getQueryObject().build() + "}";
	}

}
