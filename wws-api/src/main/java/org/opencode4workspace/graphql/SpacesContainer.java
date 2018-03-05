package org.opencode4workspace.graphql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.opencode4workspace.bo.PageInfo;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Serializable container for SpaceWrappers for Watson Workspace Spaces. This is required because the query contains an items object.
 *
 */
public class SpacesContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<SpaceWrapper> items = new ArrayList<SpaceWrapper>();
	private PageInfo pageInfo;

	/**
	 * @return List of SpaceWrapper objects for Watson Workspace Spaces
	 * 
	 * @since 0.5.0
	 */
	public List<SpaceWrapper> getItems() {
		return items;
	}
	
	/**
	 * @return PageInfo for the current query
	 * 
	 * @since 0.8.0
	 */
	public PageInfo getPageInfo() {
		return pageInfo;
	}

}
