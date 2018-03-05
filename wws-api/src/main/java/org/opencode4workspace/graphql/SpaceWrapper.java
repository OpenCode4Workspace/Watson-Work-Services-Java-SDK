package org.opencode4workspace.graphql;

import java.util.List;

import org.opencode4workspace.bo.Person;
import org.opencode4workspace.bo.Space;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Wrapper for Watson Workspace Space object. This is required for two reasons:<br>
 *        <ol>
 *        <li>To navigate down to the Members - getMembers() will skip the "items" child of the resulting JSON for the Space</li>
 *        <li>To convert the result of {@link #getConversation()} to a ConversationWrapper (so it can navigate down to the Messages)</li>
 *        </ol>
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
	
	/**
	 * @return MembersContainer, allowing access to PageInfo object
	 * 
	 * @since 0.8.0
	 */
	public MembersContainer getMembersContainer() {
		return members;
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
