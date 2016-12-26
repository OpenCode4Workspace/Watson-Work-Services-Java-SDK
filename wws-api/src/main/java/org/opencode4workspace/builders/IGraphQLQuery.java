package org.opencode4workspace.builders;

import java.util.List;
import java.util.Map;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;

public interface IGraphQLQuery {

	/**
	 * @return String, operation name
	 * 
	 * @since 0.5.0
	 */
	String getOperationName();

	/**
	 * @return ObjectDataSenderBuilder containing the query settings
	 * 
	 * @since 0.5.0
	 */
	ObjectDataSenderBuilder getQueryObject();

	/**
	 * Returns the full query to be passed to WWS, building the JSON object from an ObjectDataSenderBuilder
	 * 
	 * @return String query to pass to WWS containing "query", the operation name, and a JSON object containing the query settings
	 * 
	 * @since 0.5.0
	 */
	String returnQuery();

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#getObjectName()}
	 * 
	 * @return String the objectName property from queryObject property
	 * 
	 * @since 0.5.0
	 */
	String getObjectName();

	/**
	 * Helper method, to give easy to {@link ObjectDataSenderBuilder#setObjectName(String)}
	 * 
	 * @param objectName
	 *            the objectName property from queryObject property
	 * 
	 * @since 0.5.0
	 */
	void setObjectName(String objectName);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#isHasItems()}
	 * 
	 * @return boolean whether queryObject includes an items level in returned JSON
	 * 
	 * @since 0.5.0
	 */
	boolean isHasItems();

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
	ObjectDataSenderBuilder setHasItems(boolean hasItems);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#getFieldsList()}
	 * 
	 * @return List of String fields queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	List<String> getFieldsList();

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#setFieldsList(List)}
	 * 
	 * @param fieldsList
	 *            List of String fields in the queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	void setFieldsList(List<String> fieldsList);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#addField(String)}
	 * 
	 * @param field
	 *            String field to add to the queryObject of this Query
	 * @return ObjectDataSenderBuilder queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	ObjectDataSenderBuilder addField(String field);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#addField(WWFieldsAttributesInterface)}
	 * 
	 * @param field
	 *            WWFIeldAttributesInterface field to add to the queryObject of this Query
	 * @return ObjectDataSenderBuilder queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	ObjectDataSenderBuilder addField(WWFieldsAttributesInterface field);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#removeField(String)}
	 * 
	 * @param field
	 *            String field to remove from the queryObject of this Query
	 * @return ObjectDataSenderBuilder queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	ObjectDataSenderBuilder removeField(String field);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#removeField(WWFieldsAttributesInterface)}
	 * 
	 * @param field
	 *            WWFieldsAttributesInterface field to remove from the queryObject of this Query
	 * @return ObjectDataSenderBuilder queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	ObjectDataSenderBuilder removeField(WWFieldsAttributesInterface field);

	/**
	 * @return Map of attributes used to filter this Query, retrieved from the queryObject
	 * 
	 * @since 0.5.0
	 */
	Map<String, Object> getAttributesList();

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#setAttributesList(Map)}
	 * 
	 * @param attributesList
	 *            Map of attributes used to filter the queryObject of this Query
	 * @return ObjectDataSenderBuilder queryObject of the queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	ObjectDataSenderBuilder setAttributesList(Map<String, Object> attributesList);

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
	ObjectDataSenderBuilder addAttribute(String key, Object value);

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
	ObjectDataSenderBuilder addAttribute(WWFieldsAttributesInterface enumName, Object value) throws WWException;

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#removeAttribute(String)}
	 * 
	 * @param key
	 *            String, attribute name to filter the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the queryObject object of this Query
	 * 
	 * @since 0.5.0
	 */
	ObjectDataSenderBuilder removeAttribute(String key);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#removeAttribute(WWFieldsAttributesInterface)}
	 * 
	 * @param enumName
	 *            enum, from which label property will give attribute name to filter the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the queryObject object of this Query
	 * 
	 * @since 0.5.0
	 */
	ObjectDataSenderBuilder removeAttribute(WWFieldsAttributesInterface enumName);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#getChildren()}
	 * 
	 * @return List of DataSenderBuilders corresponding to the children of the queryObject of this Query
	 * 
	 * @since 0.5.0
	 */
	List<IDataSenderBuilder> getChildren();

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#setChildren(List)}
	 * 
	 * @param children
	 *            List of DataSenderBuilders corresponding to the children of the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the query object of this Query
	 * 
	 * @since 0.5.0
	 */
	ObjectDataSenderBuilder setChildren(List<IDataSenderBuilder> children);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#addChild(IDataSenderBuilder)}
	 * 
	 * @param child
	 *            DataSenderBuilder for the child to be added to the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the query object of this Query
	 * 
	 * @since 0.5.0
	 */
	ObjectDataSenderBuilder addChild(IDataSenderBuilder child);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#removeChild(IDataSenderBuilder)}
	 * 
	 * @param child
	 *            DataSenderBuilder for the child to be removed from the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the query object of this Query
	 * 
	 * @since 0.5.0
	 */
	ObjectDataSenderBuilder removeChild(IDataSenderBuilder child);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#addPageInfo()}
	 * 
	 * @return ObjectDataSenderBuilder, the query object of this Query
	 * 
	 * @since 0.5.0
	 */
	ObjectDataSenderBuilder addPageInfo();

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#addPageInfo(ObjectDataSenderBuilder)}
	 * 
	 * @param pageInfoCustom
	 *            ObjectDataSenderBuilder containing the custom pageInfo fields to return from the queryObject of this Query
	 * @return ObjectDataSenderBuilder, the query object of this Query
	 * 
	 * @since 0.5.0
	 */
	ObjectDataSenderBuilder addPageInfo(ObjectDataSenderBuilder pageInfoCustom);

	/**
	 * Helper method, to give easy access to {@link ObjectDataSenderBuilder#removePageInfo()}
	 * 
	 * @return ObjectDataSenderBuilder, the query object of this Query
	 * 
	 * @since 0.5.0
	 */
	ObjectDataSenderBuilder removePageInfo();

}