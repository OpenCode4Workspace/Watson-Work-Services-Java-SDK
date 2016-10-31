package org.opencode4workspace.graphql;

import java.util.List;

import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Message;

public class ConversationWrapper extends Conversation {

	private MessageContainer messages;
	
	@Override
	public List<Message> getMessages() {
		if (messages == null) {
			return null;
		}
		return messages.getItems();
	}
}
