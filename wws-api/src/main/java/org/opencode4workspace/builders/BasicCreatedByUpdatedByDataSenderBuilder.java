package org.opencode4workspace.builders;

import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.WWChildInterface;

public class BasicCreatedByUpdatedByDataSenderBuilder extends ObjectDataSenderBuilder {
	private static final long serialVersionUID = 1L;

	public BasicCreatedByUpdatedByDataSenderBuilder() {
		this("");
	}

	public BasicCreatedByUpdatedByDataSenderBuilder(String objectName) {
		super(objectName);
		addField(PersonFields.ID);
		addField(PersonFields.DISPLAY_NAME);
		addField(PersonFields.PHOTO_URL);
		addField(PersonFields.EMAIL);
	}

	public BasicCreatedByUpdatedByDataSenderBuilder(WWChildInterface objectNameEnum) {
		this(objectNameEnum.getLabel());
	}

}
