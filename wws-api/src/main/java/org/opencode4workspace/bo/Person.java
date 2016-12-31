package org.opencode4workspace.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Serializable object corresponding to a Watson Workspace member
 *
 */
public class Person implements Serializable {

	/**
	 * @author Paul Withers
	 * @since 0.5.0
	 * 
	 *        Enum for scalar properties of a Person. See {@link WWFieldsAttributesInterface}
	 *
	 */
	public enum PersonFields implements WWFieldsAttributesInterface {
		ID("id", String.class), PHOTO_URL("photoUrl", String.class), EMAIL("email", String.class), DISPLAY_NAME("displayName", String.class), EXT_ID("extId",
				String.class), EMAIL_ADDRESSES("emailAddresses", String.class), CUSTOMER_ID("customerId", String.class), CREATED("created", Date.class), UPDATED("updated", Date.class);

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
		private PersonFields(String label, Class<?> objectClassType) {
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
	 *        Enum for scalar properties of a Person. See {@link WWChildInterface}
	 *
	 */
	public enum PersonChildren implements WWChildInterface {
		CREATED_BY("createdBy", Person.class), UPDATED_BY("updatedBy", Person.class);

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
		private PersonChildren(String label, Class<?> childEnumClass) {
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
	public static final String PEOPLE_QUERY_OBJECT_NAME = "people";
	public static final String ONE_PERSON_QUERY_OBJECT_NAME = "person";
	public static final String MY_PROFILE_QUERY_OBJECT_NAME = "me";
	private String id;
	private String photoUrl;
	private String email;
	private String displayName;
	private String extId;
	private String emailAddresses;
	private String customerId;
	private Date created;
	private Person createdBy;
	private Date updated;
	private Person updatedBy;

	/**
	 * @return String, id of the Person
	 * 
	 * @since 0.5.0
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            String, id of the Person
	 * 
	 * @since 0.5.0
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return String, URL for the Person's photo
	 * 
	 * @since 0.5.0
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * @param photoUrl
	 *            String, URL for the Person's photo
	 * 
	 * @since 0.5.0
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	/**
	 * @return String, email address associated with the Person
	 * 
	 * @since 0.5.0
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            String, email address associated with the Person
	 * 
	 * @since 0.5.0
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return String, display name of the Person
	 * 
	 * @since 0.5.0
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            String, display name of the Person
	 * 
	 * @since 0.5.0
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return String external id of the Person
	 * 
	 * @since 0.5.0
	 */
	public String getExtId() {
		return extId;
	}

	/**
	 * @param extId
	 *            String external id of the Person
	 * 
	 * @since 0.5.0
	 */
	public void setExtId(String extId) {
		this.extId = extId;
	}

	/**
	 * @return String email addresses of the Person
	 * 
	 * @since 0.5.0
	 */
	public String getEmailAddresses() {
		return emailAddresses;
	}

	/**
	 * 
	 * @param emailAddresses
	 *            String email addresses of the Person
	 * 
	 * @since 0.5.0
	 */
	public void setEmailAddresses(String emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	/**
	 * @return String customer id of the Person
	 * 
	 * @since 0.5.0
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            String customer id of the Person
	 * 
	 * @since 0.5.0
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return Date the Person signed up to Watson Workspace
	 * 
	 * @since 0.5.0
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            Date the Person signed up to Watson Workspace
	 * 
	 * @since 0.5.0
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return Person who created this Person
	 * 
	 * @since 0.5.0
	 */
	public Person getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            Person who created this Person
	 * 
	 * @since 0.5.0
	 */
	public void setCreatedBy(Person createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return Date the Person was last updated
	 * 
	 * @since 0.5.0
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated
	 *            Date the Person was last updated
	 * 
	 * @since 0.5.0
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return Person who last updated this Person
	 * 
	 * @since 0.5.0
	 */
	public Person getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            Person who last updated this Person
	 * 
	 * @since 0.5.0
	 */
	public void setUpdatedBy(Person updatedBy) {
		this.updatedBy = updatedBy;
	}

}
