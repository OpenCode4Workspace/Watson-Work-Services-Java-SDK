package org.opencode4workspace.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Serializable object corresponding to a Watson Workspace space
 *
 */
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

	/**
	 * @return String, id of the space
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            String, id of the space
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return String, description set for the space
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            String, description set for the space
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return String, title of the space
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            String, title of the space
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return Date the space was created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            Date the space was created
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return Person who created the space
	 */
	public Person getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            Person who created the space
	 */
	public void setCreatedBy(Person createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return Date the space was last updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated
	 *            Date the space was last updated
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return Person who last updated the space
	 */
	public Person getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            Person who last updated the space
	 */
	public void setUpdatedBy(Person updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return List<Person>, members of the space
	 */
	public List<Person> getMembers() {
		return memberList;
	}

	/**
	 * @param members
	 *            List<Person>, members of the space
	 */
	public void setMembers(List<Person> members) {
		this.memberList = members;
	}

	/**
	 * @return Conversation, corresponding to all messages in the space
	 */
	public Conversation getConversation() {
		return conversationContent;
	}

	/**
	 * @param conversation
	 *            Conversation, corresponding to all messages in the space
	 */
	public void setConversation(Conversation conversation) {
		this.conversationContent = conversation;
	}

}
