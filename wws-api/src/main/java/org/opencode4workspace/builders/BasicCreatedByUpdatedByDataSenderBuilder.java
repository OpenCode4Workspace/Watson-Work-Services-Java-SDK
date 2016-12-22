package org.opencode4workspace.builders;

import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.WWChildInterface;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        ObjectDataSenderBuilder containing basic fields to retrieve for a CreatedBy or UpdateBy Person object
 *
 */
public class BasicCreatedByUpdatedByDataSenderBuilder extends ObjectDataSenderBuilder {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public BasicCreatedByUpdatedByDataSenderBuilder() {
		this("");
	}

	/**
	 * Overloaded constructor, with object name to define, e.g. "createdBy" or "updatedBy"
	 * 
	 * @param objectName
	 *            String "createdBy" or "updatedBy" currently expected
	 * 
	 * @since 0.5.0
	 */
	public BasicCreatedByUpdatedByDataSenderBuilder(String objectName) {
		super(objectName);
		addField(PersonFields.ID);
		addField(PersonFields.DISPLAY_NAME);
		addField(PersonFields.PHOTO_URL);
		addField(PersonFields.EMAIL);
	}

	/**
	 * Overloaded constructor, with enum for obejct name to define, e.g. SpaceChildren.CREATEDBY
	 * 
	 * @param objectNameEnum
	 *            WWChildInterface enum tat maps to "createdBy" or "updatedBy"
	 * 
	 * @since 0.5.0
	 */
	public BasicCreatedByUpdatedByDataSenderBuilder(WWChildInterface objectNameEnum) {
		this(objectNameEnum.getLabel());
	}

}
