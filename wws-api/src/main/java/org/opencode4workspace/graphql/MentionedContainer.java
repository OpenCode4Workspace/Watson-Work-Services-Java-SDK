package org.opencode4workspace.graphql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.opencode4workspace.bo.Mentioned;

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

	/**
	 * @return List of Mentioned items
	 * 
	 * @since 0.8.0
	 */
	public List<Mentioned> getItems() {
		return items;
	}

}
