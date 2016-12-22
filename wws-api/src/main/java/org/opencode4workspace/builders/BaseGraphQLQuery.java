package org.opencode4workspace.builders;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;

/**
 * @author Paul Withers
 * @author Christian Gudemann
 * @since 0.5.0
 * 
 *        Abstract object for creating GraphQL Query. Use one of the in-built options
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
	 * 
	 * @since 0.5.0
	 */
	public String getOperationName() {
		return operationName;
	}

	/**
	 * @return ObjectDataSenderBuilder containing the query settings
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder getQueryObject() {
		return queryObject;
	}

	/**
	 * Returns the full query to be passed to WWS, building the JSON object from an ObjectDataSenderBuilder
	 * 
	 * @return String query to pass to WWS containing "query", the operation name, and a JSON object containing the query settings
	 * 
	 * @since 0.5.0
	 */
	public String returnQuery() {
		return "query " + operationName + " {" + getQueryObject().build() + "}";
	}

	/**
	 * Builds a basic CreatedByPerson ObjectDataSenderBuilder, for adding as a Child to another {@link ObjectDataSenderBuilder}
	 * 
	 * @return ObjectDataSender for a basic CreatedBy Person object
	 * 
	 * @since 0.5.0
	 */
	protected ObjectDataSenderBuilder buildCreatedBy() {
		return new BasicCreatedByUpdatedByDataSenderBuilder(SpaceChildren.CREATED_BY);
	}

	/**
	 * Builds a basic UpdatedBy ObjectDataSenderBuilder, for adding as a Child to another {@link ObjectDataSenderBuilder}
	 * 
	 * @return ObjectDataSender for a basic UpdatedBy Person object
	 * 
	 * @since 0.5.0
	 */
	protected ObjectDataSenderBuilder buildUpdatedBy() {
		return new BasicCreatedByUpdatedByDataSenderBuilder(SpaceChildren.UPDATED_BY);
	}

	// Delegators

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#getObjectName()}
	 * 
	 * @return String the objectName property from queryObject property
	 * 
	 * @since 0.5.0
	 */
	public String getObjectName() {
		return queryObject.getObjectName();
	}

	/**
	 * Helper method, to give easy to {@link ObjectDataSenderBuilder#setObjectName(String)}
	 * 
	 * @param objectName
	 *            the objectName property from queryObject property
	 * 
	 * @since 0.5.0
	 */
	public void setObjectName(String objectName) {
		queryObject.setObjectName(objectName);
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#isHasItems()}
	 * 
	 * @return boolean whether queryObject includes an items level in returned JSON
	 * 
	 * @since 0.5.0
	 */
	public boolean isHasItems() {
		return queryObject.isHasItems();
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#setHasItems(boolean)}
	 * 
	 * @param hasItems
	 *            whether queryObject includes an items level in returned JSON
	 * 
	 * @return ObjectDataSenderBuilder queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder setHasItems(boolean hasItems) {
		return queryObject.setHasItems(hasItems);
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#getFieldsList()}
	 * 
	 * @return List of String fields queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	public List<String> getFieldsList() {
		return queryObject.getFieldsList();
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#setFieldsList(List)}
	 * 
	 * @param fieldsList
	 *            List of String fields in the queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	public void setFieldsList(List<String> fieldsList) {
		queryObject.setFieldsList(fieldsList);
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#addField(String)}
	 * 
	 * @param field
	 *            String field to add to the queryObject of this Query
	 * @return ObjectDataSenderBuilder queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder addField(String field) {
		return queryObject.addField(field);
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#addField(WWFieldsAttributesInterface)}
	 * 
	 * @param field
	 *            WWFIeldAttributesInterface field to add to the queryObject of this Query
	 * @return ObjectDataSenderBuilder queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder addField(WWFieldsAttributesInterface field) {
		return queryObject.addField(field);
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#removeField(String)}
	 * 
	 * @param field
	 *            String field to remove from the queryObject of this Query
	 * @return ObjectDataSenderBuilder queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder removeField(String field) {
		return queryObject.removeField(field);
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#removeField(WWFieldsAttributesInterface)}
	 * 
	 * @param field
	 *            WWFieldsAttributesInterface field to remove from the queryObject of this Query
	 * @return ObjectDataSenderBuilder queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder removeField(WWFieldsAttributesInterface field) {
		return queryObject.removeField(field);
	}

	/**
	 * @return Map of attributes used to filter this Query, retrieved from the queryObject
	 * 
	 * @since 0.5.0
	 */
	public Map<String, Object> getAttributesList() {
		return queryObject.getAttributesList();
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#setAttributesList(Map)}
	 * 
	 * @param attributesList
	 *            Map of attributes used to filter the queryObject of this Query
	 * @return ObjectDataSenderBuilder queryObject of the queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder setAttributesList(Map<String, Object> attributesList) {
		return queryObject.setAttributesList(attributesList);
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#addAttribute(String, Object)}
	 * 
	 * @param key
	 *            String, attribute name to filter the queryObject of this Query
	 * @param value
	 *            Object, value to filter the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the queryObject object of this Query
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder addAttribute(String key, Object value) {
		return queryObject.addAttribute(key, value);
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#addAttribute(WWFieldsAttributesInterface, Object)}
	 * 
	 * @param enumName
	 *            enum, from which label property will give attribute name to filter the queryObject of this Query
	 * @param value
	 *            Object, value to filter the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the queryObject object of this Query
	 * @throws WWException
	 *             containing an error message, if the value is a different data type to what the enum expects
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder addAttribute(WWFieldsAttributesInterface enumName, Object value) throws WWException {
		return queryObject.addAttribute(enumName, value);
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#removeAttribute(String)}
	 * 
	 * @param key
	 *            String, attribute name to filter the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the queryObject object of this Query
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder removeAttribute(String key) {
		return queryObject.removeAttribute(key);
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#removeAttribute(WWFieldsAttributesInterface)}
	 * 
	 * @param enumName
	 *            enum, from which label property will give attribute name to filter the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the queryObject object of this Query
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder removeAttribute(WWFieldsAttributesInterface enumName) {
		return queryObject.removeAttribute(enumName);
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#getChildren()}
	 * 
	 * @return List of DataSenderBuilders corresponding to the children of the queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	public List<DataSenderBuilder> getChildren() {
		return queryObject.getChildren();
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#setChildren(List)}
	 * 
	 * @param children
	 *            List of DataSenderBuilders corresponding to the children of the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the query object of this Query
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder setChildren(List<DataSenderBuilder> children) {
		return queryObject.setChildren(children);
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#addChild(DataSenderBuilder)}
	 * 
	 * @param child
	 *            DataSenderBuilder for the child to be added to the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the query object of this Query
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder addChild(DataSenderBuilder child) {
		return queryObject.addChild(child);
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#removeChild(DataSenderBuilder)}
	 * 
	 * @param child
	 *            DataSenderBuilder for the child to be removed from the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the query object of this Query
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder removeChild(DataSenderBuilder child) {
		return queryObject.removeChild(child);
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#addPageInfo()}
	 * 
	 * @return ObjectDataSenderBuilder, the query object of this Query
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder addPageInfo() {
		return queryObject.addPageInfo();
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#addPageInfo(ObjectDataSenderBuilder)}
	 * 
	 * @param pageInfoCustom
	 *            ObjectDataSenderBuilder containing the custom pageInfo fields to return from the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the query object of this Query
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder addPageInfo(ObjectDataSenderBuilder pageInfoCustom) {
		return queryObject.addPageInfo(pageInfoCustom);
	}

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#removePageInfo()}
	 * 
	 * @return ObjectDataSenderBuilder, the query object of this Query
	 * 
	 * @since 0.5.0
	 */
	public ObjectDataSenderBuilder removePageInfo() {
		return queryObject.removePageInfo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return queryObject.hashCode();
	}

}
