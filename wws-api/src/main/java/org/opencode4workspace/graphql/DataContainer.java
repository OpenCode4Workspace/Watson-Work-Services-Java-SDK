package org.opencode4workspace.graphql;

import java.io.Serializable;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Top-level serializable container for Watson Workspace
 *
 */
public class DataContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	private SpacesContainer spaces;

	/**
	 * @return SpacesContainer containing Spaces available for the Application / User
	 */
	public SpacesContainer getSpaces() {
		return spaces;
	}

}
