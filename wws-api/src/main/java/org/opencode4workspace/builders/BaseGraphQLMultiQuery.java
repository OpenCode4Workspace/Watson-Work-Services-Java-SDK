package org.opencode4workspace.builders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.conn.OperatedClientConnection;
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
public class BaseGraphQLMultiQuery extends BaseGraphQLQuery {

	private static final long serialVersionUID = 1L;
	private List<ObjectDataSenderBuilder> queryObjects = new ArrayList<ObjectDataSenderBuilder>();

	/**
	 * Constructor, passing in everything required to build the query
	 * 
	 * @param operationName
	 *            String, operation name
	 * @param queryObject
	 *            ObjectDataSenderBuilder containing the query settings
	 */
	public BaseGraphQLMultiQuery(String operationName, ObjectDataSenderBuilder queryObject) {
		super(operationName, queryObject);
		queryObjects.add(queryObject);
	}

	/**
	 * Constructor, passing in everything required to build the query
	 * 
	 * @param operationName
	 *            String, operation name
	 * @param queryObjects
	 *            List of ObjectDataSenderBuilder containing the query settings
	 */
	public BaseGraphQLMultiQuery(String operationName, List<ObjectDataSenderBuilder> queryObjects) {
		super(operationName, queryObjects.get(0));
		queryObjects.addAll(queryObjects);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#getOperationName()
	 */
	@Override
	public String getOperationName() {
		return operationName;
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#getQueryObject()
	 */
	@Override
	public ObjectDataSenderBuilder getQueryObject() {
		return queryObjects.get(0);
	}
	
	/**
	 * Gets all query objects loaded into this query
	 * 
	 * @return List of ObjectDataSenderBuilders for each query
	 */
	public List<ObjectDataSenderBuilder> getQueryObjects() {
		return queryObjects;
	}
	
	/**
	 * Add a query object to the list
	 * 
	 * @param queryObject ObjectDataSenderBuilder containing the query
	 */
	public BaseGraphQLMultiQuery addQueryObject(ObjectDataSenderBuilder queryObject) {
		queryObjects.add(queryObject);
		return this;
	}
	
	/**
	 * Add a query object to the list
	 * 
	 * @param queryObject ObjectDataSenderBuilder containing the query
	 */
	public BaseGraphQLMultiQuery removeQueryObject(ObjectDataSenderBuilder queryObject) {
		queryObjects.remove(queryObject);
		return this;
	}
	
	/**
	 * Changes default query object to another element in the Array
	 * 
	 * @param idx index of the array, or 0 if passed index exceeds bounds
	 */
	public ObjectDataSenderBuilder switchQueryObjectIndex(int idx) {
		if (idx > queryObjects.size()) {
			queryObject = queryObjects.get(0);
		} else  {
			queryObject = queryObjects.get(idx);
		}
		return queryObject;
	}
	
	/**
	 * Changes detauls query object to another query object. If it's not already in the List, it is added
	 * 
	 * @param queryObject ObjectDataSenderBuilder containing the query
	 */
	public ObjectDataSenderBuilder switchQueryObject(ObjectDataSenderBuilder queryObject) {
		this.queryObject = queryObject;
		if (!queryObjects.contains(queryObject)) {
			queryObjects.add(queryObject);
		}
		return queryObject;
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#returnQuery()
	 */
	@Override
	public String returnQuery() {
		String query = "query " + getOperationName() + " {";
		for (ObjectDataSenderBuilder queryObj : queryObjects) {
			query += queryObj.build();
		}
		query += "}";
		return query;
	}

	// Delegators

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#getObjectName()
	 */
	@Override
	public String getObjectName() {
		return queryObject.getObjectName();
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#setObjectName(java.lang.String)
	 */
	@Override
	public void setObjectName(String objectName) {
		queryObject.setObjectName(objectName);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#isHasItems()
	 */
	@Override
	public boolean isHasItems() {
		return queryObject.isHasItems();
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#setHasItems(boolean)
	 */
	@Override
	public ObjectDataSenderBuilder setHasItems(boolean hasItems) {
		return queryObject.setHasItems(hasItems);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#getFieldsList()
	 */
	@Override
	public List<String> getFieldsList() {
		return queryObject.getFieldsList();
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#setFieldsList(java.util.List)
	 */
	@Override
	public void setFieldsList(List<String> fieldsList) {
		queryObject.setFieldsList(fieldsList);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#addField(java.lang.String)
	 */
	@Override
	public ObjectDataSenderBuilder addField(String field) {
		return queryObject.addField(field);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#addField(org.opencode4workspace.bo.WWFieldsAttributesInterface)
	 */
	@Override
	public ObjectDataSenderBuilder addField(WWFieldsAttributesInterface field) {
		return queryObject.addField(field);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#removeField(java.lang.String)
	 */
	@Override
	public ObjectDataSenderBuilder removeField(String field) {
		return queryObject.removeField(field);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#removeField(org.opencode4workspace.bo.WWFieldsAttributesInterface)
	 */
	@Override
	public ObjectDataSenderBuilder removeField(WWFieldsAttributesInterface field) {
		return queryObject.removeField(field);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#getAttributesList()
	 */
	@Override
	public Map<String, Object> getAttributesList() {
		return queryObject.getAttributesList();
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#setAttributesList(java.util.Map)
	 */
	@Override
	public ObjectDataSenderBuilder setAttributesList(Map<String, Object> attributesList) {
		return queryObject.setAttributesList(attributesList);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#addAttribute(java.lang.String, java.lang.Object)
	 */
	@Override
	public ObjectDataSenderBuilder addAttribute(String key, Object value) {
		return queryObject.addAttribute(key, value);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#addAttribute(org.opencode4workspace.bo.WWFieldsAttributesInterface, java.lang.Object)
	 */
	@Override
	public ObjectDataSenderBuilder addAttribute(WWFieldsAttributesInterface enumName, Object value) throws WWException {
		return queryObject.addAttribute(enumName, value);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#removeAttribute(java.lang.String)
	 */
	@Override
	public ObjectDataSenderBuilder removeAttribute(String key) {
		return queryObject.removeAttribute(key);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#removeAttribute(org.opencode4workspace.bo.WWFieldsAttributesInterface)
	 */
	@Override
	public ObjectDataSenderBuilder removeAttribute(WWFieldsAttributesInterface enumName) {
		return queryObject.removeAttribute(enumName);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#getChildren()
	 */
	@Override
	public List<IDataSenderBuilder> getChildren() {
		return queryObject.getChildren();
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#setChildren(java.util.List)
	 */
	@Override
	public ObjectDataSenderBuilder setChildren(List<IDataSenderBuilder> children) {
		return queryObject.setChildren(children);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#addChild(org.opencode4workspace.builders.IDataSenderBuilder)
	 */
	@Override
	public ObjectDataSenderBuilder addChild(IDataSenderBuilder child) {
		return queryObject.addChild(child);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#removeChild(org.opencode4workspace.builders.IDataSenderBuilder)
	 */
	@Override
	public ObjectDataSenderBuilder removeChild(IDataSenderBuilder child) {
		return queryObject.removeChild(child);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#addPageInfo()
	 */
	@Override
	public ObjectDataSenderBuilder addPageInfo() {
		return queryObject.addPageInfo();
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#addPageInfo(org.opencode4workspace.builders.ObjectDataSenderBuilder)
	 */
	@Override
	public ObjectDataSenderBuilder addPageInfo(ObjectDataSenderBuilder pageInfoCustom) {
		return queryObject.addPageInfo(pageInfoCustom);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IGraphQLQuery#removePageInfo()
	 */
	@Override
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
