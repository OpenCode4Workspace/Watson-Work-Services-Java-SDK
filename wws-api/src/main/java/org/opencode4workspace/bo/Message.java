package org.opencode4workspace.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.opencode4workspace.json.ResultParser;

/**
 * @author Christian Guedemann
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Serializable object corresponding to a Message in a Conversation in a Watson Workspace space
 *
 */
public class Message implements Serializable {

	/**
	 * @author Paul Withers
	 * @since 0.5.0
	 * 
	 *        Enum for scalar properties of a Message. See {@linkplain WWFieldsAttributesInterface}
	 *
	 */
	public enum MessageFields implements WWFieldsAttributesInterface {
		ID("id", String.class), CONTENT("content", String.class), CONTENT_TYPE("contentType", String.class), ANNOTATIONS("annotations", String.class), CREATED("created",
				Date.class), UPDATED("updated", Date.class);

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
		private MessageFields(String label, Class<?> objectClassType) {
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
	 *        Enum for scalar properties of a Message. See {@linkplain WWChildInterface}
	 *
	 */
	public enum MessageChildren implements WWChildInterface {
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
		private MessageChildren(String label, Class<?> childEnumClass) {
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
	private String id;
	// TODO: Make this an enum
	private String contentType;
	private String content;
	private Date created;
	private Date updated;
	private Person createdBy;
	private Person updatedBy;
	private List<String> annotations;

	/**
	 * @return String, id of the Message
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            String, id of the Message
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return String, content type of the Message
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType
	 *            String, content type of the Message
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return String, content of the Message
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            String, content of the Message
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return Date the Message was written to the Conversation
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            Date the Message was written to the Conversation
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return Date the Message was last updated in the Conversation
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated
	 *            Date the Message was last updated in the Conversation
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return Person who created the Message
	 */
	public Person getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            Person who created the Message
	 */
	public void setCreatedBy(Person createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return Person who last updated the Message
	 */
	public Person getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            Person who last updated the Message
	 */
	public void setUpdatedBy(Person updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return List<String> of annotations for the Message
	 */
	public List<String> getAnnotations() {
		return annotations;
	}

	/**
	 * @param annotations
	 *            List<String> of annotations for the Message
	 */
	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}

	/**
	 * @return List<String> of basic annotations for the Message
	 */
	public List<Annotation> getGenericAnnotations() {
		List<Annotation> annos = new ArrayList<Annotation>();
		if (annotations != null) {
			ResultParser<Annotation> annoParser = new ResultParser<Annotation>(Annotation.class, "MILIS");
			for (String anno : annotations) {
				Annotation annoObject = annoParser.parse(anno);
				if ("generic".equals(annoObject.getType())) {
					annos.add(annoObject);
				}
			}
		}
		return annos;
	}

}
