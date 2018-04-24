package org.opencode4workspace.bo;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author Christian Guedemann
 * @since 0.5.0
 *
 *        Serializable object corresponding to an annotation for a message
 */
public class Annotation implements Serializable {
	
	/**
	 * Enum for Annotation types
	 * 
	 * @author Paul Withers
	 * @since 0.7.0
	 *
	 */
	public enum AnnotationType implements WWFieldsAttributesInterface {
		@SerializedName("generic")
		GENERIC("generic", String.class),
		@SerializedName("conversation-moment")
		MOMENT("conversation-moment", String.class),
		@SerializedName("message-focus")
		FOCUS("message-focus", String.class),
		@SerializedName("message-nlp-entities")
		NLP_ENTITY("mesage-nlp-entities", String.class),
		@SerializedName("message-nlp-keywords")
		NLP_KEYWORD("message-nlp-keywords", String.class),
		@SerializedName("nlp-docSentiment")
		NLP_DOCSENTIMENT("nlp-docSentiment", String.class),
		@SerializedName("message-nlp-relations")
		NLP_RELATION("message-nlp-relations", String.class),
		@SerializedName("message-nlp-conceptsc")
		NLP_CONCEPT("message-nlp-concepts", String.class),
		@SerializedName("message-nlp-taxonomy")
		NLP_TAXONOMY("message-nlp-taxonomy", String.class),
		@SerializedName("message-nlp-dates")
		NLP_DATE("message-nlp-dates", String.class);

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
		private AnnotationType(String label, Class<?> objectClassType) {
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
		 * @see org.opencode4workspace.bo.WWFieldsAttributesInterface#
		 * getObjectClassType()
		 */
		@Override
		public Class<?> getObjectClassType() {
			return objectClassType;
		}
		
	}

	private static final long serialVersionUID = 1L;
	private Actor actor;
	private String color;
	private Date created;
	private String createdBy;
	private String id;
	private String text;
	private String title;
	private AnnotationType type;
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
	public AnnotationType getType() {
		return type;
	}

	/**
	 * @param type
	 *            String type of the Annotation, default is "generic"
	 * 
	 * @since 0.5.0
	 */
	public void setType(AnnotationType type) {
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
