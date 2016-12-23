package org.opencode4workspace.graphql;

import java.util.List;

import org.opencode4workspace.bo.Person;
import org.opencode4workspace.bo.Space;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Wrapper for Watson Workspace Space object
 *
 */
public class SpaceWrapper extends Space {

	private static final long serialVersionUID = 1L;
	private MembersContainer members;
	private ConversationWrapper conversation;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.bo.Space#getMembers()
	 */
	@Override
	public List<Person> getMembers() {
		if (members == null) {
			return null;
		}
		return members.getItems();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.bo.Space#getConversation()
	 */
	public ConversationWrapper getConversation() {
		return conversation;
	}
}
