package org.opencode4workspace.builders;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencode4workspace.bo.PageInfo;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;

public class ObjectDataSenderBuilder implements DataSenderBuilder, Serializable {

	private static final long serialVersionUID = 1L;
	private String objectName;
	private boolean hasItems;
	private Map<String, Object> attributesList = new HashMap<String, Object>();
	private List<String> fieldsList = new ArrayList<String>();
	private List<DataSenderBuilder> children = new ArrayList<DataSenderBuilder>();
	private ObjectDataSenderBuilder pageInfo;

	public ObjectDataSenderBuilder() {

	}

	public ObjectDataSenderBuilder(String objectName) {
		this(objectName, false);
	}

	public ObjectDataSenderBuilder(String objectName, boolean hasItems) {
		this.objectName = objectName;
		this.hasItems = hasItems;
	}

	public ObjectDataSenderBuilder(String objectName, Class<?> clazz, Boolean hasItems, Boolean addAllFields) {
		this.objectName = objectName;
		this.hasItems = hasItems;
		if (addAllFields) {
			for (Field f : clazz.getDeclaredFields()) {
				if (!Modifier.isStatic(f.getModifiers())) {
					fieldsList.add(f.getName());
				}
			}
		}
	}

	public ObjectDataSenderBuilder(String objectName, Class<?> clazz, WWFieldsAttributesInterface[] fieldsEnum) {
		this(objectName, clazz, false, fieldsEnum);
	}

	public ObjectDataSenderBuilder(String objectName, Class<?> clazz, boolean hasItems, WWFieldsAttributesInterface[] fieldsEnum) {
		this.objectName = objectName;
		this.hasItems = hasItems;
		for (WWFieldsAttributesInterface f : fieldsEnum) {
			fieldsList.add(f.getLabel());
		}
	}

	@Override
	public String build() {
		return build(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.graphql.builders.DataBringer#buildQuery(boolean)
	 */
	@Override
	public String build(boolean pretty) {
		boolean isFirst = true;
		StringBuilder s = new StringBuilder();
		// Add object name
		s.append(objectName + " ");

		// Add attributes, if exist
		if (!attributesList.isEmpty()) {
			s.append("(");
			for (String key : attributesList.keySet()) {
				if (!isFirst) {
					s.append(" ");
				} else {
					isFirst = false;
				}
				s.append(key + ": ");
				s.append(attributesList.get(key));
			}
			s.append(") ");
		}

		// Open object
		s.append("{");
		if (pretty) {
			s.append("\n\r");
		}

		// Add pageInfo, if required
		if (null != pageInfo) {
			s.append(pageInfo.build(pretty) + " ");
		}

		// Open "items" container, if required
		if (isHasItems()) {
			s.append("items {");
			if (pretty) {
				s.append("\n\r");
			}
		}

		// Add fields
		isFirst = true;
		for (Object field : fieldsList) {
			if (!isFirst) {
				if (pretty) {
					s.append("\n\r");
				} else {
					s.append(" ");
				}
			} else {
				isFirst = false;
			}
			s.append(field);
		}

		// Add children, if exist
		for (DataSenderBuilder child : getChildren()) {
			if (!isFirst) {
				if (pretty) {
					s.append("\n\r");
				} else {
					s.append(" ");
				}
			} else {
				isFirst = false;
			}
			s.append(child.build(pretty));
		}

		// Close "items" container, if required
		if (isHasItems()) {
			if (pretty) {
				s.append("\n\r");
			}
			s.append("}");
		}

		// Close Object
		if (pretty) {
			s.append("\n\r");
		}
		s.append("}");
		return s.toString();
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public boolean isHasItems() {
		return hasItems;
	}

	public ObjectDataSenderBuilder setHasItems(boolean hasItems) {
		this.hasItems = hasItems;
		return this;
	}

	public List<String> getFieldsList() {
		return fieldsList;
	}

	public void setFieldsList(List<String> fieldsList) {
		this.fieldsList = fieldsList;
	}

	public ObjectDataSenderBuilder addField(String field) {
		fieldsList.add(field);
		return this;
	}

	public ObjectDataSenderBuilder removeField(String field) {
		fieldsList.remove(field);
		return this;
	}

	public Map<String, Object> getAttributesList() {
		return attributesList;
	}

	public ObjectDataSenderBuilder setAttributesList(Map<String, Object> attributesList) {
		this.attributesList = attributesList;
		return this;
	}

	public ObjectDataSenderBuilder addAttribute(String key, Object value) {
		attributesList.put(key, value);
		return this;
	}

	public ObjectDataSenderBuilder removeAttribute(String key) {
		attributesList.remove(key);
		return this;
	}

	public List<DataSenderBuilder> getChildren() {
		return children;
	}

	public ObjectDataSenderBuilder setChildren(List<DataSenderBuilder> children) {
		this.children = children;
		return this;
	}

	public ObjectDataSenderBuilder addChild(DataSenderBuilder child) {
		children.add(child);
		return this;
	}

	public ObjectDataSenderBuilder removeChild(DataSenderBuilder child) {
		children.remove(child);
		return this;
	}

	public ObjectDataSenderBuilder addPageInfo() {
		pageInfo = new ObjectDataSenderBuilder(PageInfo.LABEL, PageInfo.class, false, true);
		return this;
	}

	public ObjectDataSenderBuilder addPageInfo(ObjectDataSenderBuilder pageInfoCustom) {
		pageInfo = pageInfoCustom;
		return this;
	}

	public ObjectDataSenderBuilder removePageInfo() {
		pageInfo = null;
		return this;
	}

}