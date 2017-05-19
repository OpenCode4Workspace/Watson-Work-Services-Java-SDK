package org.opencode4workspace.bo;

import java.io.Serializable;

/**
 * @author Paul Withers
 * @since 0.7.0
 * 
 *        Serializable object corresponding to a response to posting a Photo
 *
 */
public class PhotoResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private boolean success;
	private String photoUrl;

	/**
	 * @return String id of the photo
	 * 
	 * @since 0.7.0
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id String id of the photo
	 * 
	 * @since 0.7.0
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return boolean whether the post was successful
	 * 
	 * @since 0.7.0
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success whether the post was successful
	 * 
	 * @since 0.7.0
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return String the URL with which to retrieve the photo
	 * 
	 * @since 0.7.0
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * @param photoUrl String the URL with which to retrieve the photo
	 * 
	 * @since 0.7.0
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

}
