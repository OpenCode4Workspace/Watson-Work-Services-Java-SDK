package org.opencode4workspace.graphql;

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

	/**
	 * @return Data Container containing the contents of the result of the query
	 */
	// TODO: This works for spaces, may need interfaces below Containers etc to handle queres for e.g. Me or a Person
	public DataContainer getData() {
		return data;
	}

}
