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

	/**
	 * Constructor
	 */
	public BaseGraphQLQuery() {

	}

	/**
	 * Constructor, passing in the operation name for the query
	 * 
	 * @param operationName
	 *            String, operation name
	 */
	public BaseGraphQLQuery(String operationName) {
		this.operationName = operationName;
	}

	/**
	 * Constructor, passing in everything required to build the query
	 * 
	 * @param operationName
	 *            String, operation name
	 * @param queryObject
	 *            ObjectDataSenderBuilder containing the query settings
	 */
	public BaseGraphQLQuery(String operationName, ObjectDataSenderBuilder queryObject) {
		this.operationName = operationName;
		this.queryObject = queryObject;
	}

	/**
	 * @return String, operation name
	 */
	public String getOperationName() {
		return operationName;
	}

	/**
	 * @param operationName
	 *            String, operation name
	 */
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	/**
	 * @return ObjectDataSenderBuilder containing the query settings
	 */
	public ObjectDataSenderBuilder getQueryObject() {
		return queryObject;
	}

	/**
	 * @param queryObject
	 *            ObjectDataSenderBuilder containing the query settings
	 */
	public void setQueryObject(ObjectDataSenderBuilder queryObject) {
		this.queryObject = queryObject;
	}

	/**
	 * Returns the full query to be passed to WWS, building the JSON object from an ObjectDataSenderBuilder
	 * 
	 * @return String query to pass to WWS containing "query", the operation name, and a JSON object containing the query settings
	 */
	public String returnQuery() {
		return "query " + operationName + " {" + getQueryObject().build() + "}";
	}

}
