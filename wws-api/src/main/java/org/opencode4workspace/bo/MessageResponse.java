package org.opencode4workspace.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Serializable object corresponding to a response to posting a Message
 *
 */
public class MessageResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String createdBy;
	private Date created;
	private String id;
	private int version;

	/**
	 * @return String id of the creator of the Message
	 * 
	 * @since 0.5.0
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            String id of the creator of the Message
	 * 
	 * @since 0.5.0
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return Date the Message was created
	 * 
	 * @since 0.5.0
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            Date the Message was created
	 * 
	 * @since 0.5.0
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return String id of the Message
	 * 
	 * @since 0.5.0
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            String of the Message
	 * 
	 * @since 0.5.0
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return int version of the Message
	 * 
	 * @since 0.5.0
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            int version of the Message
	 * 
	 * @since 0.5.0
	 */
	public void setVersion(int version) {
		this.version = version;
	}
}
