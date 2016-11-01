package org.opencode4workspace.graphql;

import java.util.List;

import org.opencode4workspace.bo.Message;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Serializable container for Messages in a Watson Workspace Conversation
 *
 */
public class MessageContainer {

	private List<Message> items;

	/**
	 * @return List<Message> list of Messages items in the Conversation
	 */
	public List<Message> getItems() {
		return items;
	}
}
