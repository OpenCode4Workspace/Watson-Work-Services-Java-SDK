package org.opencode4workspace.builders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;

/**
 * @author Paul Withers
 * @since 0.6.0
 * 
 *        Abstract object for creating GraphQL Query. Use one of the in-built options
 *
 */

public class BaseGraphQLMutation implements Serializable, IGraphQLMutation {

	private static final long serialVersionUID = 1L;
	private String operationName;
	private List<IDataSenderBuilder> returnObjects;
	private InputDataSenderBuilder inputObject;

	/**
	 * Constructor, passing in everything required to build the query
	 * 
	 * @param operationName
	 *            String, operation name
	 * @param inputObject
	 *            InputDataSenderBuilder containing the input to pass to the mutation
	 * @param returnObject
	 *            IDataSenderBuilder containing the query settings
	 */
	public BaseGraphQLMutation(String operationName, InputDataSenderBuilder inputObject, IDataSenderBuilder returnObject) {
		this.operationName = operationName;
		this.returnObjects = new ArrayList<IDataSenderBuilder>();
		returnObjects.add(returnObject);
		this.inputObject = inputObject;
	}

	/**
	 * Constructor, passing in multiple return objects to build the query
	 * 
	 * @param operationName
	 *            String, operation name
	 * @param inputObject
	 *            InputDataSenderBuilder containing the input to pass to the mutation
	 * @param returnObject
	 *            IDataSenderBuilder containing the query settings
	 */
	public BaseGraphQLMutation(String operationName, InputDataSenderBuilder inputObject, IDataSenderBuilder... returnObject) {
		this.operationName = operationName;
		this.returnObjects = new ArrayList<IDataSenderBuilder>();
		for (IDataSenderBuilder returnObj : returnObject) {
			returnObjects.add(returnObj);
		}
		this.inputObject = inputObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#getOperationName()
	 */
	@Override
	public String getOperationName() {
		return operationName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#getReturnObjects()
	 */
	@Override
	public List<IDataSenderBuilder> getReturnObjects() {
		return returnObjects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#getFirstReturnObject()
	 */
	public IDataSenderBuilder getFirstReturnObject() {
		return returnObjects.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#addReturnObject(org.opencode4workspace.builders.IDataSenderBuilder)
	 */
	public IGraphQLMutation addReturnObject(IDataSenderBuilder returnObject) {
		returnObjects.add(returnObject);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#removeReturnObject(org.opencode4workspace.builders.IDataSenderBuilder)
	 */
	public IGraphQLMutation removeReturnObject(IDataSenderBuilder returnObject) {
		returnObjects.remove(returnObject);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#getInputObject()
	 */
	@Override
	public InputDataSenderBuilder getInputObject() {
		return inputObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#returnQuery()
	 */
	@Override
	public String returnQuery() {
		String retVal = "mutation " + operationName + " {" + getInputObject().build() + " {";
		boolean isFirst = true;
		for (IDataSenderBuilder returnObj : getReturnObjects()) {
			if (isFirst) {
				retVal += returnObj.build();
				isFirst = false;
			} else {
				retVal += " " + returnObj.build();
			}
		}
		retVal += "}}";
		return retVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#getInputFieldsMap()
	 */
	@Override
	public Map<String, Object> getInputFieldsMap() {
		return inputObject.getFieldsMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#setInputFieldsMap(java.util.Map)
	 */
	@Override
	public void setInputFieldsMap(Map<String, Object> fieldsMap) {
		inputObject.setFieldsMap(fieldsMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#addInputField(java.lang.String, java.lang.String)
	 */
	@Override
	public InputDataSenderBuilder addInputField(String field, String value) {
		return inputObject.addField(field, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#addInputField(org.opencode4workspace.bo.WWFieldsAttributesInterface, java.lang.Object)
	 */
	@Override
	public InputDataSenderBuilder addInputField(WWFieldsAttributesInterface field, Object value) throws WWException {
		return inputObject.addField(field, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#removeInputField(java.lang.String)
	 */
	@Override
	public InputDataSenderBuilder removeInputField(String field) {
		return inputObject.removeField(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#removeInputField(org.opencode4workspace.bo.WWFieldsAttributesInterface)
	 */
	@Override
	public InputDataSenderBuilder removeInputField(WWFieldsAttributesInterface field) {
		return inputObject.removeField(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#getReturnObjectName()
	 */
	@Override
	public String getReturnObjectName() {
		return ((ObjectDataSenderBuilder) returnObjects).getObjectName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#setReturnObjectName(java.lang.String)
	 */
	@Override
	public void setReturnObjectName(String objectName) {
		((ObjectDataSenderBuilder) returnObjects).setObjectName(objectName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#getReturnFieldsList()
	 */
	@Override
	public List<String> getReturnFieldsList() {
		return ((ObjectDataSenderBuilder) returnObjects).getFieldsList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#setReturnFieldsList(java.util.List)
	 */
	@Override
	public void setReturnFieldsList(List<String> fieldsList) {
		((ObjectDataSenderBuilder) returnObjects).setFieldsList(fieldsList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#addReturnField(java.lang.String)
	 */
	@Override
	public ObjectDataSenderBuilder addReturnField(String field) {
		return ((ObjectDataSenderBuilder) returnObjects).addField(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#addReturnField(org.opencode4workspace.bo.WWFieldsAttributesInterface)
	 */
	@Override
	public ObjectDataSenderBuilder addReturnField(WWFieldsAttributesInterface field) {
		return ((ObjectDataSenderBuilder) returnObjects).addField(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#removeReturnField(java.lang.String)
	 */
	@Override
	public ObjectDataSenderBuilder removeReturnField(String field) {
		return ((ObjectDataSenderBuilder) returnObjects).removeField(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#removeReturnField(org.opencode4workspace.bo.WWFieldsAttributesInterface)
	 */
	@Override
	public ObjectDataSenderBuilder removeReturnField(WWFieldsAttributesInterface field) {
		return ((ObjectDataSenderBuilder) returnObjects).removeField(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#getReturnChildren()
	 */
	@Override
	public List<IDataSenderBuilder> getReturnChildren() {
		return ((ObjectDataSenderBuilder) returnObjects).getChildren();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#setReturnChildren(java.util.List)
	 */
	@Override
	public ObjectDataSenderBuilder setReturnChildren(List<IDataSenderBuilder> children) {
		return ((ObjectDataSenderBuilder) returnObjects).setChildren(children);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#addReturnChild(org.opencode4workspace.builders.IDataSenderBuilder)
	 */
	@Override
	public ObjectDataSenderBuilder addReturnChild(IDataSenderBuilder child) {
		return ((ObjectDataSenderBuilder) returnObjects).addChild(child);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#removeReturnChild(org.opencode4workspace.builders.IDataSenderBuilder)
	 */
	@Override
	public ObjectDataSenderBuilder removeReturnChild(IDataSenderBuilder child) {
		return ((ObjectDataSenderBuilder) returnObjects).removeChild(child);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#hashCode()
	 */
	@Override
	public int hashCode() {
		return returnObjects.hashCode();
	}

}
