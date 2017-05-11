package org.opencode4workspace.bo;

import org.opencode4workspace.json.ResultParser;

/**
 * @author Paul Withers
 * @since 0.7.0
 * 
 * Interface to manage class name to case an alias as in query and class to convert to from response
 */
public interface WWQueryResponseObjectInterface {
	
	/**
	 * @return String, object type to case the alias to when building the query
	 * 
	 * @since 0.7.0
	 */
	public String getQueryObjectType();
	
	/**
	 * @return Class to convert the response JSON to
	 * 
	 * @since 0.7.0
	 */
	public Class<?> getReturnClass();
	
	public Object parse(String jsonString);

}
