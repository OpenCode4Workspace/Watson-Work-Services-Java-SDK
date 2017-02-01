package org.opencode4workspace.builders;

import java.util.List;
import java.util.Map;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;

/**
 * @author Paul Withers
 * @since 0.6.0
 * 
 *        Interface for BaseGraphQLMutation and concrete classes from that
 *
 */
public interface IGraphQLMutation {

	/**
	 * @return String, operation name
	 * 
	 * @since 0.6.0
	 */
	String getOperationName();

	/**
	 * @return ObjectDataSenderBuilder containing the query settings for the result of the mutation
	 * 
	 * @since 0.6.0
	 */
	ObjectDataSenderBuilder getReturnObject();

	/**
	 * @return InputDataSenderBuilder containing the details to update via the mutation
	 * 
	 * @since 0.6.0
	 */
	InputDataSenderBuilder getInputObject();

	/**
	 * Returns the full query to be passed to WWS, building the JSON object from the InputDataSenderBuilder and ObjectDataSenderBuilder
	 * 
	 * @return String query to pass to WWS containing "mutation", the operation name, a JSON object containing the update settings and a JSON object containing the query settings
	 * 
	 * @since 0.6.0
	 */
	String returnQuery();

	/**
	 * Helper method, to give easy access to {@link InputDataSenderBuilder#getFieldsMap()}
	 * 
	 * @return Map of fields and values to set via the mutation
	 * 
	 * @since 0.6.0
	 */
	Map<String, Object> getInputFieldsMap();

	/**
	 * Helper method, to give easy access to {@link InputDataSenderBuilder#setFieldsMap(Map)}
	 * 
	 * @param fieldsMap
	 *            Map of fields and values to set via the mutation
	 * 
	 * @since 0.6.0
	 */
	void setInputFieldsMap(Map<String, Object> fieldsMap);

	/**
	 * Helper method, to give easy access to {@link InputDataSenderBuilder#addField(String, Object)}
	 * 
	 * @param key
	 *            String field name
	 * @param value
	 *            Object value to set
	 * @return InputDataSender, current object
	 * 
	 * @since 0.6.0
	 */
	InputDataSenderBuilder addInputField(String key, String value);

	/**
	 * Helper method, to give easy access to {@link InputDataSenderBuilder#addField(WWFieldsAttributesInterface, Object)}
	 * 
	 * @param enumName
	 *            WWFieldsAttributesInterface enum, whose label property is the name of the field to set via the mutation
	 * @param value
	 *            Object value to set
	 * @return InputDataSender, current object
	 * @throws WWException
	 *             containing an error message, if the value is a different data type to what the enum expects
	 * 
	 * @since 0.6.0
	 */
	InputDataSenderBuilder addInputField(WWFieldsAttributesInterface enumName, Object value) throws WWException;

	/**
	 * Helper method, to give easy access to {@link InputDataSenderBuilder#removeField(String)}
	 * 
	 * @param key
	 *            String field name
	 * @return InputDataSender, current object
	 * 
	 * @since 0.6.0
	 */
	InputDataSenderBuilder removeInputField(String key);

	/**
	 * Helper method, to give easy access to {@link InputDataSenderBuilder#removeField(WWFieldsAttributesInterface)}
	 * 
	 * @param enumName
	 *            WWFieldsAttributesInterface enum, whose label property is the name of the field to set via the mutation
	 * @return InputDataSender, current object
	 * 
	 * @since 0.6.0
	 */
	InputDataSenderBuilder removeInputField(WWFieldsAttributesInterface enumName);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#getObjectName()}
	 * 
	 * @return String the objectName property from queryObject property
	 * 
	 * @since 0.6.0
	 */
	String getReturnObjectName();

	/**
	 * Helper method, to give easy to {@link ObjectDataSenderBuilder#setObjectName(String)}
	 * 
	 * @param objectName
	 *            the objectName property from queryObject property
	 * 
	 * @since 0.6.0
	 */
	void setReturnObjectName(String objectName);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#getFieldsList()}
	 * 
	 * @return List of String fields queryObject of this Query
	 * 
	 * @since 0.6.0
	 */
	List<String> getReturnFieldsList();

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#setFieldsList(List)}
	 * 
	 * @param fieldsList
	 *            List of String fields in the queryObject of this Query
	 * 
	 * @since 0.6.0
	 */
	void setReturnFieldsList(List<String> fieldsList);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#addField(String)}
	 * 
	 * @param key
	 *            String field to add to the queryObject of this Query
	 * @return ObjectDataSenderBuilder queryObject of this Query
	 * 
	 * @since 0.6.0
	 */
	ObjectDataSenderBuilder addReturnField(String key);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#addField(WWFieldsAttributesInterface)}
	 * 
	 * @param enumName
	 *            WWFIeldAttributesInterface field to add to the queryObject of this Query
	 * @return ObjectDataSenderBuilder queryObject of this Query
	 * 
	 * @since 0.6.0
	 */
	ObjectDataSenderBuilder addReturnField(WWFieldsAttributesInterface enumName);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#removeField(String)}
	 * 
	 * @param key
	 *            String field to remove from the queryObject of this Query
	 * @return ObjectDataSenderBuilder queryObject of this Query
	 * 
	 * @since 0.6.0
	 */
	ObjectDataSenderBuilder removeReturnField(String key);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#removeField(WWFieldsAttributesInterface)}
	 * 
	 * @param enumName
	 *            WWFieldsAttributesInterface field to remove from the queryObject of this Query
	 * @return ObjectDataSenderBuilder queryObject of this Query
	 * 
	 * @since 0.6.0
	 */
	ObjectDataSenderBuilder removeReturnField(WWFieldsAttributesInterface enumName);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#getChildren()}
	 * 
	 * @return List of DataSenderBuilders corresponding to the children of the queryObject of this Query
	 * 
	 * @since 0.6.0
	 */
	List<IDataSenderBuilder> getReturnChildren();

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#setChildren(List)}
	 * 
	 * @param children
	 *            List of DataSenderBuilders corresponding to the children of the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the query object of this Query
	 * 
	 * @since 0.6.0
	 */
	ObjectDataSenderBuilder setReturnChildren(List<IDataSenderBuilder> children);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#addChild(IDataSenderBuilder)}
	 * 
	 * @param child
	 *            DataSenderBuilder for the child to be added to the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the query object of this Query
	 * 
	 * @since 0.6.0
	 */
	ObjectDataSenderBuilder addReturnChild(IDataSenderBuilder child);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#removeChild(IDataSenderBuilder)}
	 * 
	 * @param child
	 *            DataSenderBuilder for the child to be removed from the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the query object of this Query
	 * 
	 * @since 0.6.0
	 */
	ObjectDataSenderBuilder removeReturnChild(IDataSenderBuilder child);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	int hashCode();

}