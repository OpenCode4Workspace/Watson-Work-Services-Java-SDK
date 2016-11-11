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

	public enum SpaceFields implements WWFieldsAttributesInterface {
		ID("id"), DESCRIPTION("description"), TITLE("title"), CREATED("created"), UPDATED("updated"), MEMBERS_UPDATED("membersUpdated");

		private String label;

		private SpaceFields(String label) {
			this.label = label;
		}

		@Override
		public String getLabel() {
			return label;
		}
	}

	public enum SpaceChildren implements WWChildInterface {
		MEMBERS_LIST("membersList", Object.class), CONVERSATION_CONTENT("conversationContent", Conversation.class), CREATED_BY("createdBy", Person.class), UPDATED_BY("updatedBy", Person.class);

		private String label;
		private Class<?> childClass;

		private SpaceChildren(String label, Class<?> childClass) {
			this.label = label;
			this.childClass = childClass;
		}

		@Override
		public String getLabel() {
			return label;
		}

		@Override
		public Class<?> getChildClass() {
			return childClass;
		}
	}

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
	private String membersUpdated;
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
	 * @return String JSON date of when members were last updated
	 */
	public String getMembersUpdated() {
		return membersUpdated;
	}

	/**
	 * @param membersUpdated
	 *            String JSON date of when members were last updated
	 */
	public void setMembersUpdated(String membersUpdated) {
		this.membersUpdated = membersUpdated;
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
