package org.opencode4workspace.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Space implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String description;
	private String title;
	private Date created;
	private Person createdBy;
	private Date updated;
	private Person updatedBy;
	private List<Person> memberList;
	private Conversation conversationContent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Person getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Person createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Person getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Person updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<Person> getMembers() {
		return memberList;
	}

	public void setMembers(List<Person> members) {
		this.memberList = members;
	}

	public Conversation getConversation() {
		return conversationContent;
	}

	public void setConversation(Conversation conversation) {
		this.conversationContent = conversation;
	}

}
