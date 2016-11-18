package org.opencode4workspace.graphql;

import org.opencode4workspace.bo.WWFieldsAttributesInterface;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Enum for basic pagination
 * 
 */
public enum BasicPaginationEnum implements WWFieldsAttributesInterface {

	FIRST("first", Integer.class), LAST("last", Integer.class), AFTER("after", String.class), BEFORE("before", String.class);

	private String label;
	private Class<?> objectClassType;

	/**
	 * Constructor
	 * 
	 * @param label
	 *            String, WWS variable
	 * @param objectClassType
	 *            Class<?> Java data type expected for passing across
	 */
	private BasicPaginationEnum(String label, Class<?> objectClassType) {
		this.label = label;
		this.objectClassType = objectClassType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.bo.WWFieldsAttributesInterface#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.bo.WWFieldsAttributesInterface#getObjectClassType()
	 */
	@Override
	public Class<?> getObjectClassType() {
		return objectClassType;
	}

}
