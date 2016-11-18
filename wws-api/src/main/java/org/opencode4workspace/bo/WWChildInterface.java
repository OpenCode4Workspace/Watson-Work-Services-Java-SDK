package org.opencode4workspace.bo;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Interface for child objects in WWS objects. The label maps to WWS variable name and the objectClassType maps to the Java data type to pass across.
 * 
 *        This may be merged with {@linkplain WWFieldsAttributesInterface} in the future. Leaving currently, in case differences are required.
 *
 */
public interface WWChildInterface {

	/**
	 * @return String, WWS variable name
	 */
	public String getLabel();

	/**
	 * @return Class, Java data type corresponding to this object
	 */
	public Class<?> getEnumClass();

}
