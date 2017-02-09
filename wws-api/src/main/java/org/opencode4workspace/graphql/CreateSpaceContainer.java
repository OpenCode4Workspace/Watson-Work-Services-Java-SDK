package org.opencode4workspace.graphql;

import java.io.Serializable;

public class CreateSpaceContainer implements Serializable {
	private static final long serialVersionUID = 1L;
	private SpaceWrapper space;

	/**
	 * @return SpaceWrapper corresponding to newly created Space
	 */
	public SpaceWrapper getSpace() {
		return space;
	}

}
