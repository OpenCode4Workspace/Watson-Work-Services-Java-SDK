package org.opencode4workspace.graphql;

import java.util.List;

import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Message;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Serializable wrapper for a Conversation
 *
 */
public class ConversationWrapper extends Conversation {

	private static final long serialVersionUID = 1L;
	private MessageContainer messages = new MessageContainer();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.bo.Conversation#getMessages()
	 */
	@Override
	public List<Message> getMessages() {
		if (messages == null) {
			return null;
		}
		return messages.getItems();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.bo.Conversation#setMessages(java.util.List)
	 */
	@Override
	public void setMessages(List<Message> messagelist) {
		if (messages != null) {
			messages.setItems(messagelist);
		}
	}
}
