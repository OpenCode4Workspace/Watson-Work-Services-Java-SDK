package org.opencode4workspace.graphql;

import java.io.Serializable;
import java.util.List;

import org.opencode4workspace.bo.Person;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Serializable container for Members in a Watson Workspace Space
 *
 */
public class MemberItemContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Person> items;

	/**
	 * @return List of {@linkplain Person} objects corresponding to the Members in a Space
	 */
	public List<Person> getItems() {
		return items;
	}
}
