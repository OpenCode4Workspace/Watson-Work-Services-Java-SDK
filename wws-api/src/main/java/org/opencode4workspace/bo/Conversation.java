package org.opencode4workspace.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.opencode4workspace.graphql.builders.GraphQLJsonPropertyHelper;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Serializable object corresponding to a Conversation in a Watson Work Space
 *
 */
public class Conversation implements Serializable {

	public enum ConversationFields implements WWFieldsAttributesInterface {
		ID("id"), CREATED("created"), UPDATED("updated");

		private String label;

		private ConversationFields(String label) {
			this.label = label;
		}

		@Override
		public String getLabel() {
			return label;
		}
	}

	public enum ConversationChildren implements WWChildInterface {
		MESSAGES("messages", Object.class), CREATED_BY("createdBy", Person.class), UPDATED_BY("updatedBy", Person.class);

		private String label;
		private Class<?> childEnumClass;

		private ConversationChildren(String label, Class<?> childEnumClass) {
			this.label = label;
			this.childEnumClass = childEnumClass;
		}

		@Override
		public String getLabel() {
			return label;
		}

		@Override
		public Class<?> getEnumClass() {
			return childEnumClass;
		}

	}

	private static final long serialVersionUID = 1L;
	private String id;
	private Date created;
	private Date updated;
	private Person createdBy;
	private Person updatedBy;
	@GraphQLJsonPropertyHelper(jsonProperty="message")
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
