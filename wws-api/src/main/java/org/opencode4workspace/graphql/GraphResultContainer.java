package org.opencode4workspace.graphql;

import java.util.List;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Serializable ontainer for the result of a Graph Query
 *
 */
public class GraphResultContainer {

	private DataContainer data;
	private List<ErrorContainer> errors;

	/**
	 * @return Data Container containing the contents of the result of the query
	 * 
	 * @since 0.5.0
	 */
	public DataContainer getData() {
		return data;
	}

	/**
	 * @return Errors Container containing any errors of the result of the query
	 * 
	 * @since 0.5.0
	 */
	public List<ErrorContainer> getErrors() {
		return errors;
	}

}
