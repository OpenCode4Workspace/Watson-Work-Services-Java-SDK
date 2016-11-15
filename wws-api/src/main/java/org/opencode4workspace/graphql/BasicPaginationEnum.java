package org.opencode4workspace.graphql;

public enum BasicPaginationEnum {

	FIRST("first"), LAST("last"), AFTER("after"), BEFORE("before");

	private String label;

	private BasicPaginationEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
