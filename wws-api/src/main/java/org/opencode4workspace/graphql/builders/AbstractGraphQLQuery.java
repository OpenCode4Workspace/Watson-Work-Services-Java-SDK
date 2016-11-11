package org.opencode4workspace.graphql.builders;

import java.io.Serializable;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Abstract object for creating GraphQL Query. Use one of the in-built options
 *
 */
public class AbstractGraphQLQuery implements Serializable {

	private static final long serialVersionUID = 1L;
	private String introduction;
	private String operationName;
	private ObjectDataBringer queryObject;

	public AbstractGraphQLQuery() {

	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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
