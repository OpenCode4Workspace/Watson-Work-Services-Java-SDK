package org.opencode4workspace.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Serializable object corresponding to a Watson Workspace member
 *
 */
public class Person implements Serializable {

	public enum PersonFields implements WWFieldsAttributesInterface {
		ID("id", String.class), PHOTO_URL("photoUrl", String.class), EMAIL("email", String.class), DISPLAY_NAME("displayName", String.class), EXT_ID("extId",
				String.class), EMAIL_ADDRESSES("emailAddresses", String.class), CUSTOMER_ID("customerId", String.class), CREATED("created", Date.class);

		private String label;
		private Class<?> objectClassType;

		private PersonFields(String label, Class<?> objectClassType) {
			this.label = label;
			this.objectClassType = objectClassType;
		}

		@Override
		public String getLabel() {
			return label;
		}

		@Override
		public Class<?> getObjectClassType() {
			return objectClassType;
		}
	}

	public enum PersonChildren implements WWChildInterface {
		CREATED_BY("createdBy", Person.class), UPDATED_BY("updatedBy", Person.class);

		private String label;
		private Class<?> childEnumClass;

		private PersonChildren(String label, Class<?> childEnumClass) {
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
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            String, id of the Person
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return String, URL for the Person's photo
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * @param photoUrl
	 *            String, URL for the Person's photo
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	/**
	 * @return String, email address associated with the Person
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            String, email address associated with the Person
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return String, display name of the Person
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            String, display name of the Person
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getExtId() {
		return extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
	}

	public String getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(String emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

}
