package org.opencode4workspace.graphql;

import java.util.List;

import org.opencode4workspace.bo.Person;
import org.opencode4workspace.bo.Space;

public class SpaceWrapper extends Space {

	private MemberItemContainer members;
	private ConversationWrapper conversation;

	@Override
	public List<Person> getMembers() {
		if (members == null) {
			return null;
		}
		return members.getItems();
	}

	public ConversationWrapper getConversation() {
		return conversation;
	}
}
