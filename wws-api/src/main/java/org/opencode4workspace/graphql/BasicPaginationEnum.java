package org.opencode4workspace.graphql;

import org.opencode4workspace.bo.WWFieldsAttributesInterface;

public enum BasicPaginationEnum implements WWFieldsAttributesInterface {

	FIRST("first", Integer.class), LAST("last", Integer.class), AFTER("after", String.class), BEFORE("before", String.class);

	private String label;
	private Class<?> objectClassType;

	private BasicPaginationEnum(String label, Class<?> objectClassType) {
		this.label = label;
		this.objectClassType = objectClassType;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public Class<?> getObjectClassType() {
		return objectClassType;
	}

}
