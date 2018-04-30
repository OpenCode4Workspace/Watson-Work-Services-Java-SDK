package org.opencode4workspace.webhooks;

import org.opencode4workspace.bo.WWFieldsAttributesInterface;

import com.google.gson.annotations.SerializedName;

/**
 * Enum for event Types for Webhook Events
 * 
 * @author Paul Withers
 * @since 0.9.0
 *
 */
public enum WebhookType implements WWFieldsAttributesInterface {
	
	@SerializedName("verification")
	VERIFICATION("verification", String.class),
	@SerializedName("space-updated")
	SPACE_UPDATED("space-updated", String.class),
	@SerializedName("space-deleted")
	SPACE_DELETED("space-deleted", String.class),
	@SerializedName("reaction-added")
	REACTION_ADDED("reaction-added", String.class),
	@SerializedName("reaction-removed")
	REACTION_REMOVED("reaction-removed", String.class),
	@SerializedName("message-deleted")
	MESSAGE_DELETED("message-deleted", String.class),
	@SerializedName("message-annotation-added")
	MESSAGE_ANNOTATION_ADDED("message-annotation-added", String.class),
	@SerializedName("message-annotation-edited")
	MESSAGE_ANNOTATION_EDITED("message-annotation-edited", String.class),
	@SerializedName("message-annotation-removed")
	MESSAGE_ANNOTATION_REMOVED("message-annotation-removed", String.class);
	
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
	private WebhookType(String label, Class<?> objectClassType) {
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
