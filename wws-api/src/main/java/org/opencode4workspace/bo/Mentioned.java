package org.opencode4workspace.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Paul Withers
 * @since 0.8.0
 * 
 *        Serializable object corresponding to a Mentioned in a Conversation in a Watson Workspace space
 */
public class Mentioned implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @author Paul Withers
	 * @since 0.8.0
	 * 
	 *        Enum for scalar properties of a Mentioned. See {@link WWFieldsAttributesInterface}
	 *
	 */
	public enum MentionedFields implements WWFieldsAttributesInterface {
		UPDATED("updated", Long.class), UPDATED_BY("updatedBy", String.class);

		private String label;
		private Class<?> objectClassType;

		/**
		 * Constructor
		 * 
		 * @param label
		 *            String, WWS variable
		 * @param objectClassType
		 *            Class<?> Java data type expected for passing across
		 */
		private MentionedFields(String label, Class<?> objectClassType) {
			this.label = label;
			this.objectClassType = objectClassType;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.opencode4workspace.bo.WWFieldsAttributesInterface#getLabel()
		 */
		@Override
		public String getLabel() {
			return label;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.opencode4workspace.bo.WWFieldsAttributesInterface#getObjectClassType()
		 */
		@Override
		public Class<?> getObjectClassType() {
			return objectClassType;
		}
	}

	/**
	 * @author Paul Withers
	 * @since 0.8.0
	 * 
	 *        Enum for scalar properties of a Mentioned. See {@link WWFieldsAttributesInterface}
	 *
	 */
	public enum MentionedChildren implements WWFieldsAttributesInterface {
		SPACE("space", Space.class), MESSAGE("message", Message.class), PERSON("person", Person.class);

		private String label;
		private Class<?> objectClassType;

		/**
		 * Constructor
		 * 
		 * @param label
		 *            String, WWS variable
		 * @param objectClassType
		 *            Class<?> Java data type expected for passing across
		 */
		private MentionedChildren(String label, Class<?> objectClassType) {
			this.label = label;
			this.objectClassType = objectClassType;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.opencode4workspace.bo.WWFieldsAttributesInterface#getLabel()
		 */
		@Override
		public String getLabel() {
			return label;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.opencode4workspace.bo.WWFieldsAttributesInterface#getObjectClassType()
		 */
		@Override
		public Class<?> getObjectClassType() {
			return objectClassType;
		}
	}

	private Long updated;
	private String updatedBy;
	private Space space;
	private Message message;
	private Person person;
	public static final String MENTIONED_QUERY_OBJECT_NAME = "mentioned";

	/**
	 * @return Date the Message the user was Mentioned in was last updated
	 * @since 0.8.0
	 */
	public Long getUpdated() {
		return updated;
	}

	/**
	 * @param Date
	 *            the Message the user was Mentioned in was last updated
	 * @since 0.8.0
	 */
	public void setUpdated(Long updated) {
		this.updated = updated;
	}

	/**
	 * @return String id of the Person who last updated the Message
	 * @since 0.8.0
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            String id of the Person who last updated the Message
	 * @since 0.8.0
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return Space in which the current user was Mentioned
	 * @since 0.8.0
	 */
	public Space getSpace() {
		return space;
	}

	/**
	 * @param space
	 *            Space in which the current user was Mentioned
	 * @since 0.8.0
	 */
	public void setSpace(Space space) {
		this.space = space;
	}

	/**
	 * @return Message in which the current user was Mentioned
	 * @since 0.8.0
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            Message in which the current user was Mentioned
	 * @since 0.8.0
	 */
	public void setMessage(Message message) {
		this.message = message;
	}

	/**
	 * @return Person who Mentioned the current user
	 * @since 0.8.0
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person
	 *            Person who Mentioned the current user
	 * @since 0.8.0
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

}
