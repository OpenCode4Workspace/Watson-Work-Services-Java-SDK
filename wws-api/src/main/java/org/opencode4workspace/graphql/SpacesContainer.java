package org.opencode4workspace.graphql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Serializable container for SpaceWrappers for Watson Workspace Spaces
 *
 */
public class SpacesContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<SpaceWrapper> items = new ArrayList<SpaceWrapper>();

	/**
	 * @return List<SpaceWrapper> List of space wrappers for Watson Workspace Spaces
	 */
	public List<SpaceWrapper> getItems() {
		return items;
	}

}
