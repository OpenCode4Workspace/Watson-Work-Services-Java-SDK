package org.opencode4workspace.graphql;

import java.io.Serializable;

import org.opencode4workspace.bo.Message;
import org.opencode4workspace.bo.Person;

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
	private Person me;
	private Person person;
	private ConversationWrapper conversation;
	private SpaceWrapper space;
	private MembersContainer people;
	private Message message;

	/**
	 * @return SpacesContainer containing Spaces available for the Application / User
	 * 
	 * @since 0.5.0
	 */
	public SpacesContainer getSpaces() {
		return spaces;
	}

	/**
	 * @return Person object for current user
	 * 
	 * @since 0.5.0
	 */
	public Person getMe() {
		return me;
	}

	/**
	 * @return Person object for a given user
	 * 
	 * @since 0.5.0
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @return ConversationWrapper for a given conversation
	 * 
	 * @since 0.5.0
	 */
	public ConversationWrapper getConversation() {
		return conversation;
	}

	/**
	 * @param conversation
	 *            ConversationWrapper for a given conversation
	 * 
	 * @since 0.5.0
	 */
	public void setConversation(ConversationWrapper conversation) {
		this.conversation = conversation;
	}

	/**
	 * @return SpaceWrapper for a given Workspace
	 * 
	 * @since 0.5.0
	 */
	public SpaceWrapper getSpace() {
		return space;
	}

	/**
	 * @return MemberItemContainer for people resulting from a query
	 * 
	 * @since 0.5.0
	 */
	public MembersContainer getPeople() {
		return people;
	}

	/**
	 * @return Message resulting from a query
	 * 
	 * @since 0.5.0
	 */
	public Message getMessage() {
		return message;
	}

}
