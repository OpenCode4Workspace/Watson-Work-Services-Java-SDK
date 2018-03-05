package org.opencode4workspace.graphql;

import java.io.Serializable;
import java.util.List;

import org.opencode4workspace.bo.PageInfo;
import org.opencode4workspace.bo.Person;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Serializable container for Members in a Watson Workspace Space. This is required because the query contains an items object.
 *
 */
public class MembersContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Person> items;
	private PageInfo pageInfo;

	/**
	 * @return List of {@link Person} objects corresponding to the Members in a Space
	 * 
	 * @since 0.5.0
	 */
	public List<Person> getItems() {
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
