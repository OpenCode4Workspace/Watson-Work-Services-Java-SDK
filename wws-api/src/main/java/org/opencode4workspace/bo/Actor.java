package org.opencode4workspace.bo;

import java.io.Serializable;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Serializable object corresponding to a Watson Workspace actor
 *
 */
public class Actor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String avatar;
	private String url;

	/**
	 * @return String, name of the actor
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            String, name of the actor
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return String, avatar url of the actor
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar
	 *            String, avatar url of the actor
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * @return String, url of the actor
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            String, url of the actor
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
