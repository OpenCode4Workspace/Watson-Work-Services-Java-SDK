package org.opencode4workspace.graphql;

import java.io.Serializable;

/**
 * @author Paul Withers
 * @since 0.6.0
 * 
 *        Contaainer for the response to a createSpace mutation
 *
 */
public class CreateSpaceContainer implements Serializable {
	private static final long serialVersionUID = 1L;
	private SpaceWrapper space;

	/**
	 * @return SpaceWrapper corresponding to newly created Space
	 * @since 0.6.0
	 */
	public SpaceWrapper getSpace() {
		return space;
	}

}
