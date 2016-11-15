package org.opencode4workspace.graphql.builders;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencode4workspace.bo.PageInfo;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;

public class ObjectDataSender implements DataSender, Serializable {

	private static final long serialVersionUID = 1L;
	private String objectName;
	private boolean hasItems;
	private Map<String, Object> attributesList = new HashMap<String, Object>();
	private List<String> fieldsList = new ArrayList<String>();
	private List<DataSender> children = new ArrayList<DataSender>();
	private String dataQuery;
	private ObjectDataSender pageInfo;

	public ObjectDataSender() {

	}

	public ObjectDataSender(String objectName) {
		this(objectName, false);
	}

	public ObjectDataSender(String objectName, boolean hasItems) {
		this.objectName = objectName;
		this.hasItems = hasItems;
	}

	public ObjectDataSender(String objectName, Class<?> clazz, Boolean hasItems, Boolean addAllFields) {
		this.objectName = objectName;
		this.hasItems = hasItems;
		if (addAllFields) {
			for (Field f : clazz.getDeclaredFields()) {
				if (!Modifier.isStatic(f.getModifiers())) {
					String fieldName = extractFieldName(f);
					fieldsList.add(fieldName);
				}
			}
		}
	}

	public ObjectDataSender(String objectName, Class<?> clazz, WWFieldsAttributesInterface[] fieldsEnum) {
		this(objectName, clazz, false, fieldsEnum);
	}

	public ObjectDataSender(String objectName, Class<?> clazz, boolean hasItems, WWFieldsAttributesInterface[] fieldsEnum) {
		this.objectName = objectName;
		this.hasItems = hasItems;
		for (WWFieldsAttributesInterface f : fieldsEnum) {
			fieldsList.add(f.getLabel());
		}
	}

	private String extractFieldName(Field f) {
		String fieldName = f.getName();
		if (f.isAnnotationPresent(GraphQLJsonPropertyHelper.class)) {
			return f.getAnnotation(GraphQLJsonPropertyHelper.class).jsonProperty();
		}
		return fieldName;
	}

	@Override
	public String buildQuery() {
		return buildQuery(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opencode4workspace.graphql.builders.DataBringer#buildQuery(boolean)
	 */
	@Override
	public String buildQuery(boolean pretty) {
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
			s.append(pageInfo.buildQuery(pretty) + " ");
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
		for (DataSender child : getChildren()) {
			if (!isFirst) {
				if (pretty) {
					s.append("\n\r");
				} else {
					s.append(" ");
				}
			} else {
				isFirst = false;
			}
			s.append(child.buildQuery(pretty));
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

	@Override
	public Object getDataQuery() {
		return dataQuery;
	}

	public void setDataQuery(String query) {
		this.dataQuery = query;
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

	public void setHasItems(boolean hasItems) {
		this.hasItems = hasItems;
	}

	public List<String> getFieldsList() {
		return fieldsList;
	}

	public void setFieldsList(List<String> fieldsList) {
		this.fieldsList = fieldsList;
	}

	public List<String> addField(String field) {
		fieldsList.add(field);
		return fieldsList;
	}

	public List<String> removeField(String field) {
		fieldsList.remove(field);
		return fieldsList;
	}

	public Map<String, Object> getAttributesList() {
		return attributesList;
	}

	public void setAttributesList(Map<String, Object> attributesList) {
		this.attributesList = attributesList;
	}

	public Map<String, Object> addAttribute(String key, Object value) {
		attributesList.put(key, value);
		return attributesList;
	}

	public Map<String, Object> removeAttribute(String key) {
		attributesList.remove(key);
		return attributesList;
	}

	public List<DataSender> getChildren() {
		return children;
	}

	public void setChildren(List<DataSender> children) {
		this.children = children;
	}

	public List<DataSender> addChild(DataSender child) {
		children.add(child);
		return children;
	}

	public List<DataSender> removeChild(DataSender child) {
		children.remove(child);
		return children;
	}

	public void addPageInfo() {
		pageInfo = new ObjectDataSender(PageInfo.LABEL, PageInfo.class, false, true);
	}

	public void addPageInfo(ObjectDataSender pageInfoCustom) {
		pageInfo = pageInfoCustom;
	}

	public void removePageInfo() {
		pageInfo = null;
	}

}
