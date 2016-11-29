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
	private SpaceWrapper space;
	private MemberItemContainer people;

	/**
	 * @return SpacesContainer containing Spaces available for the Application / User
	 */
	public SpacesContainer getSpaces() {
		return spaces;
	}

	/**
	 * @return Profile for current user
	 */
	public Profile getMe() {
		return me;
	}

	/**
	 * @return Profile for a given user
	 */
	public Profile getProfile() {
		return person;
	}

	/**
	 * @return ConversationWrapper for a given conversation
	 */
	public ConversationWrapper getConversation() {
		return conversation;
	}

	/**
	 * @param conversation
	 *            ConversationWrapper for a given conversation
	 */
	public void setConversation(ConversationWrapper conversation) {
		this.conversation = conversation;
	}

	/**
	 * @return SpaceWrapper for a given Workspace
	 */
	public SpaceWrapper getSpace() {
		return space;
	}

	/**
	 * @return MemberItemContainer for people resulting from a query
	 */
	public MemberItemContainer getPeople() {
		return people;
	}

}
