package org.opencode4workspace.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Christian Guedemann
 * @since 0.5.0
 *
 *        Serializable object corresponding to an annotation for a message
 */
public class Annotation implements Serializable {

	private static final long serialVersionUID = 1L;
	private Actor actor;
	private String color;
	private Date created;
	private String createdBy;
	private String id;
	private String text;
	private String title;
	private String type;
	private int version;

	/**
	 * @return Actor referenced in the Annotation
	 * 
	 * @since 0.5.0
	 */
	public Actor getActor() {
		return actor;
	}

	/**
	 * @param actor
	 *            Actor referenced in the Annotation
	 * 
	 * @since 0.5.0
	 */
	public void setActor(Actor actor) {
		this.actor = actor;
	}

	/**
	 * @return String hex colour for the Annotation
	 * 
	 * @since 0.5.0
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            String hex colour for the Annotation
	 * 
	 * @since 0.5.0
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return Date the Annotation was created
	 * 
	 * @since 0.5.0
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            Date the Annotation was created
	 * 
	 * @since 0.5.0
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return String id of the creator of the Annotation, auto-generated
	 * 
	 * @since 0.5.0
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            String id of the creator of the Annotation, auto-generated
	 * 
	 * @since 0.5.0
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return String id of the Annotation
	 * 
	 * @since 0.5.0
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            String id of the Annotation
	 * 
	 * @since 0.5.0
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return String body of text to send inside the Annotation. May include a subset of markdown
	 * 
	 * @since 0.5.0
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            String body of text to send inside the Annotation. May include a subset of markdown
	 * 
	 * @since 0.5.0
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return String title of the Annotation. May include a subset of markdown
	 * 
	 * @since 0.5.0
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            String title of the Annotation. May include a subset of markdown
	 * 
	 * @since 0.5.0
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return String type of the Annotation, default is "generic"
	 * 
	 * @since 0.5.0
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            String type of the Annotation, default is "generic"
	 * 
	 * @since 0.5.0
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return int version of the Annotation
	 * 
	 * @since 0.5.0
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            int version of the Annotation
	 * 
	 * @since 0.5.0
	 */
	public void setVersion(int version) {
		this.version = version;
	}

}
