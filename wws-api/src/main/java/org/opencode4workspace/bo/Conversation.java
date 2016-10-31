package org.opencode4workspace.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Conversation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Date created;
	private Date updated;
	private Person createdBy;
	private Person updatedBy;
	private List<Message> messages;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Person getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Person createdBy) {
		this.createdBy = createdBy;
	}

	public Person getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Person updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

}
