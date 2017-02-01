package org.opencode4workspace.builders;

import java.io.Serializable;
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
	private IDataSenderBuilder returnObject;
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
		this.returnObject = returnObject;
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
	 * @see org.opencode4workspace.builders.IGraphQLMutation#getReturnObject()
	 */
	@Override
	public IDataSenderBuilder getReturnObject() {
		return returnObject;
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
		return "mutation " + operationName + " {" + getInputObject().build() + " {" + getReturnObject().build() + "}}";
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
		return ((ObjectDataSenderBuilder) returnObject).getObjectName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#setReturnObjectName(java.lang.String)
	 */
	@Override
	public void setReturnObjectName(String objectName) {
		((ObjectDataSenderBuilder) returnObject).setObjectName(objectName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#getReturnFieldsList()
	 */
	@Override
	public List<String> getReturnFieldsList() {
		return ((ObjectDataSenderBuilder) returnObject).getFieldsList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#setReturnFieldsList(java.util.List)
	 */
	@Override
	public void setReturnFieldsList(List<String> fieldsList) {
		((ObjectDataSenderBuilder) returnObject).setFieldsList(fieldsList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#addReturnField(java.lang.String)
	 */
	@Override
	public ObjectDataSenderBuilder addReturnField(String field) {
		return ((ObjectDataSenderBuilder) returnObject).addField(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#addReturnField(org.opencode4workspace.bo.WWFieldsAttributesInterface)
	 */
	@Override
	public ObjectDataSenderBuilder addReturnField(WWFieldsAttributesInterface field) {
		return ((ObjectDataSenderBuilder) returnObject).addField(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#removeReturnField(java.lang.String)
	 */
	@Override
	public ObjectDataSenderBuilder removeReturnField(String field) {
		return ((ObjectDataSenderBuilder) returnObject).removeField(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#removeReturnField(org.opencode4workspace.bo.WWFieldsAttributesInterface)
	 */
	@Override
	public ObjectDataSenderBuilder removeReturnField(WWFieldsAttributesInterface field) {
		return ((ObjectDataSenderBuilder) returnObject).removeField(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#getReturnChildren()
	 */
	@Override
	public List<IDataSenderBuilder> getReturnChildren() {
		return ((ObjectDataSenderBuilder) returnObject).getChildren();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#setReturnChildren(java.util.List)
	 */
	@Override
	public ObjectDataSenderBuilder setReturnChildren(List<IDataSenderBuilder> children) {
		return ((ObjectDataSenderBuilder) returnObject).setChildren(children);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#addReturnChild(org.opencode4workspace.builders.IDataSenderBuilder)
	 */
	@Override
	public ObjectDataSenderBuilder addReturnChild(IDataSenderBuilder child) {
		return ((ObjectDataSenderBuilder) returnObject).addChild(child);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IGraphQLMutation#removeReturnChild(org.opencode4workspace.builders.IDataSenderBuilder)
	 */
	@Override
	public ObjectDataSenderBuilder removeReturnChild(IDataSenderBuilder child) {
		return ((ObjectDataSenderBuilder) returnObject).removeChild(child);
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
		return returnObject.hashCode();
	}

}
