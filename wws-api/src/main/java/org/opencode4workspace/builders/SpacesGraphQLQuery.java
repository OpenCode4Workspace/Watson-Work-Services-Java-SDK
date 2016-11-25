package org.opencode4workspace.builders;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Conversation.ConversationFields;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.bo.Profile.PersonFields;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.graphql.BasicPaginationEnum;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Object for creating a Spaces query
 *
 */
public class SpacesGraphQLQuery extends BaseGraphQLQuery {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @throws WWException
	 *             if there is an error building the object
	 */
	public SpacesGraphQLQuery() throws WWException {
		try {
			setOperationName("getSpaces");

			// Basic createdBy ObjectDataBringer - same label for all
			ObjectDataSenderBuilder createdBy = new ObjectDataSenderBuilder(SpaceChildren.CREATED_BY.getLabel());
			createdBy.addField(PersonFields.ID.getLabel());
			createdBy.addField(PersonFields.DISPLAY_NAME.getLabel());
			createdBy.addField(PersonFields.PHOTO_URL.getLabel());
			createdBy.addField(PersonFields.EMAIL.getLabel());

			// Basic updatedBy ObjectDataBringer - same label for all
			ObjectDataSenderBuilder updatedBy = new ObjectDataSenderBuilder(SpaceChildren.UPDATED_BY.getLabel());
			updatedBy.addField(PersonFields.ID.getLabel());
			updatedBy.addField(PersonFields.DISPLAY_NAME.getLabel());
			updatedBy.addField(PersonFields.PHOTO_URL.getLabel());
			updatedBy.addField(PersonFields.EMAIL.getLabel());

			ObjectDataSenderBuilder spaces = new ObjectDataSenderBuilder(Space.SPACE_QUERY_OBJECT_NAME, true);
			spaces.addAttribute(BasicPaginationEnum.FIRST, 100);
			spaces.addPageInfo();
			spaces.addField(SpaceFields.ID.getLabel());
			spaces.addField(SpaceFields.TITLE.getLabel());
			spaces.addField(SpaceFields.DESCRIPTION.getLabel());
			spaces.addField(SpaceFields.UPDATED.getLabel());
			spaces.addChild(updatedBy);
			spaces.addField(SpaceFields.CREATED.getLabel());
			spaces.addChild(createdBy);
			ObjectDataSenderBuilder members = new ObjectDataSenderBuilder(SpaceChildren.MEMBERS.getLabel(), true);
			members.addAttribute(BasicPaginationEnum.FIRST, 100);
			members.addField(PersonFields.ID.getLabel());
			members.addField(PersonFields.PHOTO_URL.getLabel());
			members.addField(PersonFields.EMAIL.getLabel());
			members.addField(PersonFields.DISPLAY_NAME.getLabel());
			spaces.addChild(members);
			ObjectDataSenderBuilder conversation = new ObjectDataSenderBuilder(SpaceChildren.CONVERSATION.getLabel());
			conversation.addField(ConversationFields.ID.getLabel());
			conversation.addField(ConversationFields.CREATED.getLabel());
			conversation.addChild(createdBy);
			conversation.addField(ConversationFields.UPDATED.getLabel());
			conversation.addChild(updatedBy);
			ObjectDataSenderBuilder messages = new ObjectDataSenderBuilder(ConversationChildren.MESSAGES.getLabel(), true);
			messages.addAttribute(BasicPaginationEnum.FIRST, 200);
			messages.addPageInfo();
			messages.addField(MessageFields.CONTENT_TYPE.getLabel());
			messages.addField(MessageFields.CONTENT.getLabel());
			messages.addField(MessageFields.CREATED.getLabel());
			messages.addField(MessageFields.UPDATED.getLabel());
			messages.addField(MessageFields.ANNOTATIONS.getLabel());
			messages.addField(MessageFields.ID.getLabel());
			messages.addChild(createdBy);
			messages.addChild(updatedBy);
			conversation.addChild(messages);
			spaces.addChild(conversation);
			setQueryObject(spaces);
		} catch (Exception e) {
			throw new WWException(e);
		}

	}

}
