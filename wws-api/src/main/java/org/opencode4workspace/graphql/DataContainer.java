package org.opencode4workspace.graphql;

import java.io.Serializable;

import org.opencode4workspace.WWException;
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
	private DeleteSpaceContainer deleteSpace;

	/**
	 * @return SpacesContainer containing Spaces available for the Application / User
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public SpacesContainer getSpaces() throws WWException {
		if (null == spaces) {
			throw new WWException("No data returned from query. Please check the query you are passing and check for errors returned (.getErrors() instead of .getResult())");
		}
		return spaces;
	}

	/**
	 * @return boolean, whether or not deleteSpace call was successful
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.6.0
	 */
	public boolean getDeletionSuccessful() throws WWException {
		if (null == deleteSpace) {
			throw new WWException("No data returned from query. Please check the query you are passing and check for errors returned (.getErrors() instead of .getResult())");
		}
		return deleteSpace.getSuccessful();
	}

	/**
	 * @return Person object for current user
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Person getMe() throws WWException {
		if (null == me) {
			throw new WWException("No data returned from query. Please check the query you are passing and check for errors returned (.getErrors() instead of .getResult())");
		}
		return me;
	}

	/**
	 * @return Person object for a given user
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Person getPerson() throws WWException {
		if (null == person) {
			throw new WWException("No data returned from query. Please check the query you are passing and check for errors returned (.getErrors() instead of .getResult())");
		}
		return person;
	}

	/**
	 * @return ConversationWrapper for a given conversation
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public ConversationWrapper getConversation() throws WWException {
		if (null == conversation) {
			throw new WWException("No data returned from query. Please check the query you are passing and check for errors returned (.getErrors() instead of .getResult())");
		}
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
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public SpaceWrapper getSpace() throws WWException {
		if (null == space) {
			throw new WWException("No data returned from query. Please check the query you are passing and check for errors returned (.getErrors() instead of .getResult())");
		}
		return space;
	}

	/**
	 * @return MemberItemContainer for people resulting from a query
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public MembersContainer getPeople() throws WWException {
		if (null == people) {
			throw new WWException("No data returned from query. Please check the query you are passing and check for errors returned (.getErrors() instead of .getResult())");
		}
		return people;
	}

	/**
	 * @return Message resulting from a query
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public Message getMessage() throws WWException {
		if (null == message) {
			throw new WWException("No data returned from query. Please check the query you are passing and check for errors returned (.getErrors() instead of .getResult())");
		}
		return message;
	}

}
