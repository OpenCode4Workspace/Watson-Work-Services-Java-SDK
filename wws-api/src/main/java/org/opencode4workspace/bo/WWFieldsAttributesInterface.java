package org.opencode4workspace.bo;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Interface for scalar fields in WWS objects, which can also be used for setting query attributes. The label maps to WWS variable name and the objectClassType maps to the Java data type to
 *        pass across
 *
 */
public interface WWFieldsAttributesInterface {

	/**
	 * @return String, WWS variable name
	 * 
	 * @since 0.5.0
	 */
	public String getLabel();

	/**
	 * @return Class, Java data type expected when using this field as an attribute for a WWS query
	 * 
	 * @since 0.5.0
	 */
	public Class<?> getObjectClassType();

}
