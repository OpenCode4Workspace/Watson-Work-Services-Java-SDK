package org.opencode4workspace.builders;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;

/**
 * @author Paul Withers
 * @author Christian Gudemann
 * @since 0.5.0
 * 
 *        Abstract object for creating GraphQL Query. Use one of the in-built
 *        options
 *
 */
public abstract class BaseGraphQLQuery implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String operationName;
	private final ObjectDataSenderBuilder queryObject;


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
	 * @return ObjectDataSenderBuilder containing the query settings
	 */
	public ObjectDataSenderBuilder getQueryObject() {
		return queryObject;
	}


	/**
	 * Returns the full query to be passed to WWS, building the JSON object from
	 * an ObjectDataSenderBuilder
	 * 
	 * @return String query to pass to WWS containing "query", the operation
	 *         name, and a JSON object containing the query settings
	 */
	public String returnQuery() {
		return "query " + operationName + " {" + getQueryObject().build() + "}";
	}

	protected ObjectDataSenderBuilder buildCreatedBy() {
		return new BasicCreatedByUpdatedByDataSenderBuilder(SpaceChildren.CREATED_BY);
	}

	protected ObjectDataSenderBuilder buildUpdatedBy() {
		return new BasicCreatedByUpdatedByDataSenderBuilder(SpaceChildren.UPDATED_BY);
	}
	
	//Delegators

	public String getObjectName() {
		return queryObject.getObjectName();
	}

	public void setObjectName(String objectName) {
		queryObject.setObjectName(objectName);
	}

	public boolean isHasItems() {
		return queryObject.isHasItems();
	}

	public ObjectDataSenderBuilder setHasItems(boolean hasItems) {
		return queryObject.setHasItems(hasItems);
	}

	public List<String> getFieldsList() {
		return queryObject.getFieldsList();
	}

	public void setFieldsList(List<String> fieldsList) {
		queryObject.setFieldsList(fieldsList);
	}

	public ObjectDataSenderBuilder addField(String field) {
		return queryObject.addField(field);
	}

	public ObjectDataSenderBuilder addField(WWFieldsAttributesInterface field) {
		return queryObject.addField(field);
	}

	public ObjectDataSenderBuilder removeField(String field) {
		return queryObject.removeField(field);
	}

	public Map<String, Object> getAttributesList() {
		return queryObject.getAttributesList();
	}

	public ObjectDataSenderBuilder setAttributesList(Map<String, Object> attributesList) {
		return queryObject.setAttributesList(attributesList);
	}

	public ObjectDataSenderBuilder addAttribute(String key, Object value) {
		return queryObject.addAttribute(key, value);
	}

	public ObjectDataSenderBuilder addAttribute(WWFieldsAttributesInterface enumName, Object value) throws WWException {
		return queryObject.addAttribute(enumName, value);
	}

	public ObjectDataSenderBuilder removeAttribute(String key) {
		return queryObject.removeAttribute(key);
	}

	public List<DataSenderBuilder> getChildren() {
		return queryObject.getChildren();
	}

	public ObjectDataSenderBuilder setChildren(List<DataSenderBuilder> children) {
		return queryObject.setChildren(children);
	}

	public ObjectDataSenderBuilder addChild(DataSenderBuilder child) {
		return queryObject.addChild(child);
	}

	public ObjectDataSenderBuilder removeChild(DataSenderBuilder child) {
		return queryObject.removeChild(child);
	}

	public ObjectDataSenderBuilder addPageInfo() {
		return queryObject.addPageInfo();
	}

	public ObjectDataSenderBuilder addPageInfo(ObjectDataSenderBuilder pageInfoCustom) {
		return queryObject.addPageInfo(pageInfoCustom);
	}

	public int hashCode() {
		return queryObject.hashCode();
	}

	public ObjectDataSenderBuilder removePageInfo() {
		return queryObject.removePageInfo();
	}

}
