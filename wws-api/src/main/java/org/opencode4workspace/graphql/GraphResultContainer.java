package org.opencode4workspace.graphql;

import java.util.List;

import org.opencode4workspace.WWException;

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
	 * @throws WWException
	 *             error message if no data is returned
	 * 
	 * @since 0.5.0
	 */
	public DataContainer getData() throws WWException {
		if (null == data) {
			throw new WWException("No data returned from query. Please check the query you are passing and check for errors returned (.getErrors() instead of .getResult())");
		}
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
