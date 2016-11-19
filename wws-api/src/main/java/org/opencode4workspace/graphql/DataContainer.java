package org.opencode4workspace.graphql;

import java.io.Serializable;

import org.opencode4workspace.bo.Profile;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Top-level serializable container for Watson Workspace
 *
 */
public class DataContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	private SpacesContainer spaces;
	private Profile me;
	private Profile person;
	private ConversationWrapper conversation;

	/**
	 * @return SpacesContainer containing Spaces available for the Application / User
	 */
	public SpacesContainer getSpaces() {
		return spaces;
	}

	public Profile getMe() {
		return me;
	}

	public Profile getProfile() {
		return person;
	}

	public ConversationWrapper getConversation() {
		return conversation;
	}

	public void setConversation(ConversationWrapper conversation) {
		this.conversation = conversation;
	}

}
