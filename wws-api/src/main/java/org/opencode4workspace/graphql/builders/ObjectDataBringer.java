package org.opencode4workspace.graphql.builders;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencode4workspace.bo.PageInfo;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;

public class ObjectDataBringer implements DataBringer {

	private String objectName;
	private boolean hasItems;
	private Map<String, Object> attributesList = new HashMap<String, Object>();
	private List<String> fieldsList = new ArrayList<String>();
	private List<DataBringer> children = new ArrayList<DataBringer>();
	private String dataQuery;
	private ObjectDataBringer pageInfo;

	public ObjectDataBringer() {

	}

	public ObjectDataBringer(String objectName) {
		this(objectName, false);
	}

	public ObjectDataBringer(String objectName, boolean hasItems) {
		this.objectName = objectName;
		this.hasItems = hasItems;
	}

	public ObjectDataBringer(String objectName, Class<?> clazz, Boolean addAllFields) {
		this(objectName, clazz, false, addAllFields);
	}

	public ObjectDataBringer(String objectName, Class<?> clazz, boolean hasItems, Boolean addAllFields) {
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

	public ObjectDataBringer(String objectName, Class<?> clazz, WWFieldsAttributesInterface[] fieldsEnum) {
		this(objectName, clazz, false, fieldsEnum);
	}

	public ObjectDataBringer(String objectName, Class<?> clazz, boolean hasItems, WWFieldsAttributesInterface[] fieldsEnum) {
		this.objectName = objectName;
		this.hasItems = hasItems;
		for (WWFieldsAttributesInterface f : fieldsEnum) {
			fieldsList.add(f.getLabel());
		}
	}

	@Override
	public String buildQuery() {
		return buildQuery(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.graphql.builders.DataBringer#buildQuery(boolean)
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
		for (DataBringer child : getChildren()) {
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

	public List<DataBringer> getChildren() {
		return children;
	}

	public void setChildren(List<DataBringer> children) {
		this.children = children;
	}

	public List<DataBringer> addChild(DataBringer child) {
		children.add(child);
		return children;
	}

	public List<DataBringer> removeChild(DataBringer child) {
		children.remove(child);
		return children;
	}

	public void addPageInfo() {
		pageInfo = new ObjectDataBringer(PageInfo.LABEL, PageInfo.class, true);
		;
	}

	public void addPageInfo(ObjectDataBringer pageInfoCustom) {
		pageInfo = pageInfoCustom;
	}

	public void removePageInfo() {
		pageInfo = null;
	}

}
