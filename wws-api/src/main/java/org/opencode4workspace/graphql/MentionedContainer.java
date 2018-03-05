package org.opencode4workspace.graphql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.opencode4workspace.bo.Mentioned;
import org.opencode4workspace.bo.PageInfo;

/**
 * @author Paul Withers
 * @since 0.8.0
 * 
 *        Serializable container for Mentioned items. This is required because the query contains an items object.
 *
 */
public class MentionedContainer implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Mentioned> items = new ArrayList<Mentioned>();
	private PageInfo pageInfo;

	/**
	 * @return List of Mentioned items
	 * 
	 * @since 0.8.0
	 */
	public List<Mentioned> getItems() {
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
