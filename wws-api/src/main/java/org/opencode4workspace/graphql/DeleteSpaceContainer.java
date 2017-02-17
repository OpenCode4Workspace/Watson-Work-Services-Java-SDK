package org.opencode4workspace.graphql;

/**
 * @author Paul Withers
 * @since 0.6.0
 * 
 *        Container for response to a deleteSpace mutation
 *
 */
public class DeleteSpaceContainer {
	private boolean successful;

	/**
	 * @return boolean, whether or not the deleteSpace request was successful
	 * @since 0.6.0
	 */
	public boolean getSuccessful() {
		return successful;
	}

}
