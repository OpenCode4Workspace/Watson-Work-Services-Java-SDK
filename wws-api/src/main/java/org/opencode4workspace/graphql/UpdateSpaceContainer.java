package org.opencode4workspace.graphql;

import java.io.Serializable;

/**
 * @author Paul Withers
 * @since 0.6.0
 * 
 *        Container for response to an updateSpace mutation
 *
 */
public class UpdateSpaceContainer implements Serializable {
	private static final long serialVersionUID = 1L;
	private String[] memberIdsChanged;
	private SpaceWrapper space;

	/**
	 * @return String array of the member IDs changed by this mutation (if any)
	 * @since 0.6.0
	 */
	public String[] getMemberIdsChanged() {
		return memberIdsChanged;
	}

	/**
	 * @return SpaceWrapper for the Space updated by this mutation (if requested)
	 * @since 0.6.0
	 */
	public SpaceWrapper getSpace() {
		return space;
	}

}
