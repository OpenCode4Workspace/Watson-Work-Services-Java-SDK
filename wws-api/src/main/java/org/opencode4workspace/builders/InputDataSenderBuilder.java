package org.opencode4workspace.builders;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;

/**
 * @author Paul Withers
 * @since 0.6.0
 * 
 *        DataSenderBuilder for sending GraphQL mutation content. The object will have a name and fields to pass. Consult WWS Graph QL Builder for details of what attributes etc at
 *        https://workspace.ibm.com/graphql
 *
 */
public class InputDataSenderBuilder implements Serializable, IDataSenderBuilder {
	private static final long serialVersionUID = 1L;
	private String mutationName;
	private Map<String, Object> fieldsMap = new HashMap<String, Object>();

	/**
	 * Raw constructor. Recommendation is to use overloaded methods
	 */
	public InputDataSenderBuilder() {

	}

	/**
	 * Base constructor, defining the mutation name
	 * 
	 * @param mutationName
	 *            String name of the mutation, e.g. createSpace
	 */
	public InputDataSenderBuilder(String mutationName) {
		this.mutationName = mutationName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IDataSenderBuilder#build()
	 */
	public String build() {
		return build(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.builders.IDataSenderBuilder#build(boolean)
	 */
	public String build(boolean pretty) {
		boolean isFirst = true;
		StringBuilder s = new StringBuilder();
		// Add object name
		s.append(mutationName + " (input: {");
		if (pretty) {
			s.append("\n\r");
		}

		for (String key : getFieldsMap().keySet()) {
			if (!isFirst) {
				s.append(" ");
			} else {
				isFirst = false;
			}
			s.append(key);
			s.append(": ");
			Object obj = getFieldsMap().get(key);
			convertMapValue(s, obj);
		}
		if (pretty) {
			s.append("\n\r");
		}

		s.append("})");
		return s.toString();
	}

	/**
	 * Converts a value for a field to the correct JSON format
	 * 
	 * @param s
	 *            StringBuilder containing the JSON for the query
	 * @param obj
	 *            Object value for the field
	 * 
	 * @since 0.6.0
	 */
	private void convertMapValue(StringBuilder s, Object obj) {
		if (obj instanceof Date) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			Date dt = (Date) obj;
			TimeZone tz = TimeZone.getDefault();
			df.setTimeZone(tz);
			s.append(df.format(dt));
		} else if (obj instanceof String) {
			s.append("\"");
			s.append(obj);
			s.append("\"");
		} else if (obj instanceof List) {
			s.append("[");
			boolean isFirst = true;
			for (Object innerObj : ((List) obj)) {
				if (!isFirst) {
					s.append(", ");
				} else {
					isFirst = false;
				}
				convertMapValue(s, innerObj);
			}
			s.append("]");
		} else if (obj.getClass().isEnum()) {
			s.append(obj.toString());
		} else {
			s.append(obj);
		}
	}

	/**
	 * @return String, name of the mutation object
	 * 
	 * @since 0.6.0
	 */
	public String getMutationName() {
		return mutationName;
	}

	/**
	 * @param mutationName
	 *            String, name of the mutation object
	 * 
	 * @since 0.6.0
	 */
	public void setMutationName(String mutationName) {
		this.mutationName = mutationName;
	}

	/**
	 * @return Map of fields and values to set via the mutation
	 * 
	 * @since 0.6.0
	 */
	public Map<String, Object> getFieldsMap() {
		return fieldsMap;
	}

	/**
	 * @param fieldsMap
	 *            Map of fields to set via the mutation
	 * 
	 * @since 0.6.0
	 */
	public void setFieldsMap(Map<String, Object> fieldsMap) {
		this.fieldsMap = fieldsMap;
	}

	/**
	 * Adds a field and its value to set via the mutation
	 * 
	 * @param key
	 *            String field name
	 * @param value
	 *            Object value to set
	 * @return InputDataSender, current object
	 * 
	 * @since 0.6.0
	 */
	public InputDataSenderBuilder addField(String key, Object value) {
		fieldsMap.put(key, value);
		return this;
	}

	/**
	 * Adds a field and its value to set via the mutation
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
	public InputDataSenderBuilder addField(WWFieldsAttributesInterface enumName, Object value) throws WWException {
		if (value.getClass().equals(enumName.getObjectClassType())) {
			fieldsMap.put(enumName.getLabel(), value);
		} else {
			if ("java.util.List".equals(enumName.getObjectClassType().getName())) {
				if (value instanceof List || value instanceof Set) {
					fieldsMap.put(enumName.getLabel(), value);
				}
			} else if ("java.util.Map".equals(enumName.getObjectClassType().getName())) {
				if (value instanceof Map) {
					fieldsMap.put(enumName.getLabel(), value);
				}
			} else {
				throw new WWException("Watson Work Services expects a " + enumName.getObjectClassType().getName() + " for this attribute. Object supplied is " + value.getClass().getName());
			}
		}
		return this;
	}

	/**
	 * Removes a field to set via the mutation
	 * 
	 * @param key
	 *            String field name
	 * @return InputDataSender, current object
	 * 
	 * @since 0.6.0
	 */
	public InputDataSenderBuilder removeField(String key) {
		fieldsMap.remove(key);
		return this;
	}

	/**
	 * Removes a field to set via the mutation
	 * 
	 * @param enumName
	 *            WWFieldsAttributesInterface enum, whose label property is the name of the field to set via the mutation
	 * @return InputDataSender, current object
	 * 
	 * @since 0.6.0
	 */
	public InputDataSenderBuilder removeField(WWFieldsAttributesInterface enumName) {
		fieldsMap.remove(enumName.getLabel());
		return this;
	}

}
