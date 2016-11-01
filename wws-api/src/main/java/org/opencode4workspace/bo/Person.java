package org.opencode4workspace.bo;

import java.io.Serializable;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Serializable object corresponding to a Watson Workspace member
 *
 */
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String photoUrl;
	private String email;
	private String displayName;

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

}
