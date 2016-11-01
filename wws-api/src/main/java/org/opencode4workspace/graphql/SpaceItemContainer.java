package org.opencode4workspace.graphql;

import java.io.Serializable;
import java.util.List;

import org.opencode4workspace.bo.Space;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Serializable container for a Watson Workspace Space item
 *
 */
public class SpaceItemContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Space> items;

}
