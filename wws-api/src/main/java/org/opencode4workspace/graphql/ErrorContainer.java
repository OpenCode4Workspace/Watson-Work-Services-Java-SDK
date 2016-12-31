package org.opencode4workspace.graphql;

import java.util.Map;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Class to handle errors returned by Watson Work Services, e.g.
 * 
 *        <pre>
 * {"data":{"people":null},"errors":[{"message":"500 Internal Server Error","field":{"name":"people","type":"PersonCollection"}}]}
 *        </pre>
 *
 */
public class ErrorContainer {
	private String message;
	private Map<String, Object> field;

	/**
	 * @return String message object, e.g. "500 Internal Server Error"
	 * 
	 * @since 0.5.0
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return Map of error field details, e.g. name="People", type="PersonCollection"
	 * 
	 * @since 0.5.0
	 */
	public Map<String, Object> getField() {
		return field;
	}
}
