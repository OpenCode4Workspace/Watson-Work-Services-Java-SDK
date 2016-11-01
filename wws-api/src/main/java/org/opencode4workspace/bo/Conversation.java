package org.opencode4workspace.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Serializable object corresponding to a Conversation in a Watson Work Space
 *
 */
public class Conversation implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private Date created;
	private Date updated;
	private Person createdBy;
	private Person updatedBy;
	private List<Message> messageList;

	/**
	 * @return String, id of the Conversation
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            String, id of the Conversation
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Date the Conversation was created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            Date the Conversation was created
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return Date the Conversation was last updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated
	 *            Date the Conversation was last updated
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return Person the Conversation was created by
	 */
	public Person getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            Person the Conversation was created by
	 */
	public void setCreatedBy(Person createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return Person the Conversation was last updated by
	 */
	public Person getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            Person the Conversation was last updated by
	 */
	public void setUpdatedBy(Person updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return List<Message>, List of Messages in this Conversation
	 */
	public List<Message> getMessages() {
		return messageList;
	}

	/**
	 * @param messages
	 *            List<Message>, List of Messages in this Conversation
	 */
	public void setMessages(List<Message> messages) {
		this.messageList = messages;
	}

}
