package org.opencode4workspace.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.opencode4workspace.graphql.builders.GraphQLJsonPropertyHelper;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Serializable object corresponding to a Watson Workspace space
 *
 */
public class Space implements Serializable {

	/**
	 * @author Paul Withers
	 * @since 0.5.0
	 * 
	 *        Enum for scalar properties of a Space. See {@link WWFieldsAttributesInterface}
	 *
	 */
	public enum SpaceFields implements WWFieldsAttributesInterface {
		ID("id", String.class), DESCRIPTION("description", String.class), TITLE("title", String.class), CREATED("created", Date.class), UPDATED("updated",
				Date.class), MEMBERS_UPDATED("membersUpdated", Date.class);

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
		private SpaceFields(String label, Class<?> objectClassType) {
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
	 * @since 0.5.0
	 * 
	 *        Enum for child objects of a Space. See {@link WWChildInterface}
	 *
	 */
	public enum SpaceChildren implements WWChildInterface {
		MEMBERS("members", Person.class), CONVERSATION("conversation", Conversation.class), CREATED_BY("createdBy", Person.class), UPDATED_BY("updatedBy", Person.class);

		private String label;
		private Class<?> childEnumClass;

		/**
		 * Constructor
		 * 
		 * @param label
		 *            String, WWS variable
		 * @param childEnumClass
		 *            Class<?> Java data type expected for passing across
		 */
		private SpaceChildren(String label, Class<?> childEnumClass) {
			this.label = label;
			this.childEnumClass = childEnumClass;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.opencode4workspace.bo.WWChildInterface#getLabel()
		 */
		@Override
		public String getLabel() {
			return label;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.opencode4workspace.bo.WWChildInterface#getEnumClass()
		 */
		@Override
		public Class<?> getEnumClass() {
			return childEnumClass;
		}
	}

	private static final long serialVersionUID = 1L;
	public static final String SPACES_QUERY_OBJECT_NAME = "spaces";
	public static final String ONE_SPACE_QUERY_OBJECT_NAME = "space";
	public static final String CREATE_SPACE_MUTATION_NAME = "createSpace";
	public static final String UPDATE_SPACE_MUTATION_NAME = "updateSpace";
	public static final String DELETE_SPACE_MUTATION_NAME = "deleteSpace";
	public static final String REMOVE_SPACE_MEMBERS_MUTATION_NAME = "removeSpaceMembers";
	public static final String ADD_SPACE_MEMBERS_MUTATION_NAME = "addSpaceMembers";
	private String id;
	private String description;
	private String title;
	private Date created;
	private Person createdBy;
	private Date updated;
	private Person updatedBy;
	private List<Person> memberList;
	private Date membersUpdated;
	@GraphQLJsonPropertyHelper(jsonProperty = "conversation")
	private Conversation conversationContent;

	/**
	 * @return String, id of the space
	 * 
	 * @since 0.5.0
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            String, id of the space
	 * 
	 * @since 0.5.0
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return String, description set for the space
	 * 
	 * @since 0.5.0
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            String, description set for the space
	 * 
	 * @since 0.5.0
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return String, title of the space
	 * 
	 * @since 0.5.0
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            String, title of the space
	 * 
	 * @since 0.5.0
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return Date the space was created
	 * 
	 * @since 0.5.0
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            Date the space was created
	 * 
	 * @since 0.5.0
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return Person who created the space
	 * 
	 * @since 0.5.0
	 */
	public Person getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            Person who created the space
	 * 
	 * @since 0.5.0
	 */
	public void setCreatedBy(Person createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return Date the space was last updated
	 * 
	 * @since 0.5.0
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated
	 *            Date the space was last updated
	 * 
	 * @since 0.5.0
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return Person who last updated the space
	 * 
	 * @since 0.5.0
	 */
	public Person getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            Person who last updated the space
	 * 
	 * @since 0.5.0
	 */
	public void setUpdatedBy(Person updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return List of {@link Person} objects corresponding to the members of the space
	 * 
	 * @since 0.5.0
	 */
	public List<Person> getMembers() {
		return memberList;
	}

	/**
	 * @param members
	 *            List of {@link Person} objects corresponding to the members of the space
	 * 
	 * @since 0.5.0
	 */
	public void setMembers(List<Person> members) {
		this.memberList = members;
	}

	/**
	 * @return Date when members were last updated. Changed in 0.8.0 to Date (was incorrect)
	 * 
	 * @since 0.5.0
	 */
	public Date getMembersUpdated() {
		return membersUpdated;
	}

	/**
	 * @param membersUpdated
	 *            Date of when members were last updated. Changed in 0.8.0 to Date (was incorrect)
	 * 
	 * @since 0.5.0
	 */
	public void setMembersUpdated(Date membersUpdated) {
		this.membersUpdated = membersUpdated;
	}

	/**
	 * @return Conversation, corresponding to all messages in the space
	 * 
	 * @since 0.5.0
	 */
	public Conversation getConversation() {
		return conversationContent;
	}

	/**
	 * @param conversation
	 *            Conversation, corresponding to all messages in the space
	 * 
	 * @since 0.5.0
	 */
	public void setConversation(Conversation conversation) {
		this.conversationContent = conversation;
	}

}
