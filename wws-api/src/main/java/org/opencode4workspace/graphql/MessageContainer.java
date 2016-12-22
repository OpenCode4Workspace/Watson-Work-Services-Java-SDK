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
	 * @return List of Message items in the Conversation
	 * 
	 * @since 0.5.0
	 */
	public List<Message> getItems() {
		return items;
	}

	/**
	 * @param messages
	 *            List of Message items in the Conversation
	 * 
	 * @since 0.5.0
	 */
	public void setItems(List<Message> messages) {
		items = messages;
	}
}
