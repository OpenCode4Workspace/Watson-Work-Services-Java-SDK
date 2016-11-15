package org.opencode4workspace.endpoints;

import java.util.List;

import javax.xml.soap.MessageFactory;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Conversation.ConversationFields;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.graphql.builders.BasicPaginationEnum;
import org.opencode4workspace.graphql.builders.ObjectDataSender;
import org.opencode4workspace.json.GraphQLRequest;

public class WWGraphQLEndpoint extends AbstractWWGraphQLEndpoint {

	/**
	 * @param client
	 *            WWClient containing authentication details and token
	 */
	public WWGraphQLEndpoint(WWClient client) {
		super(client);
	}

	/**
	 * Simplified access method, to load GraphQL query for getting spaces, execute the request, and parse the results
	 * 
	 * @return List<? extending Space> of Space details
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 */
	@SuppressWarnings("unchecked")
	public List<? extends Space> getSpaces() throws WWException {
		// Basic createdBy ObjectDataBringer - same label for all
		ObjectDataSender createdBy = new ObjectDataSender(SpaceChildren.UPDATED_BY.getLabel());
		createdBy.addField(PersonFields.ID.getLabel());
		createdBy.addField(PersonFields.DISPLAY_NAME.getLabel());
		createdBy.addField(PersonFields.PHOTO_URL.getLabel());
		createdBy.addField(PersonFields.EMAIL.getLabel());

		// Basic updatedBy ObjectDataBringer - same label for all
		ObjectDataSender updatedBy = new ObjectDataSender(SpaceChildren.UPDATED_BY.getLabel());
		updatedBy.addField(PersonFields.ID.getLabel());
		updatedBy.addField(PersonFields.DISPLAY_NAME.getLabel());
		updatedBy.addField(PersonFields.PHOTO_URL.getLabel());
		updatedBy.addField(PersonFields.EMAIL.getLabel());

		ObjectDataSender spaces = new ObjectDataSender("spaces", true);
		spaces.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		spaces.addPageInfo();
		spaces.addField(SpaceFields.ID.getLabel());
		spaces.addField(SpaceFields.TITLE.getLabel());
		spaces.addField(SpaceFields.DESCRIPTION.getLabel());
		spaces.addField(SpaceFields.UPDATED.getLabel());
		spaces.addChild(updatedBy);
		spaces.addField(SpaceFields.CREATED.getLabel());
		spaces.addChild(createdBy);
		ObjectDataSender members = new ObjectDataSender(SpaceChildren.MEMBERS.getLabel(), SpaceChildren.MEMBERS.getEnumClass(), true, false);
		members.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		members.addField(PersonFields.ID.getLabel());
		members.addField(PersonFields.PHOTO_URL.getLabel());
		members.addField(PersonFields.EMAIL.getLabel());
		members.addField(PersonFields.DISPLAY_NAME.getLabel());
		spaces.addChild(members);
		ObjectDataSender conversation = new ObjectDataSender(SpaceChildren.CONVERSATION.getLabel());
		conversation.addField(ConversationFields.ID.getLabel());
		conversation.addField(ConversationFields.CREATED.getLabel());
		conversation.addChild(createdBy);
		conversation.addField(ConversationFields.UPDATED.getLabel());
		conversation.addChild(updatedBy);
		ObjectDataSender messages = new ObjectDataSender(ConversationChildren.MESSAGES.getLabel(), true);
		messages.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 200);
		messages.addPageInfo();
		messages.addField(MessageFields.CONTENT_TYPE.getLabel());
		messages.addField(MessageFields.CONTENT.getLabel());
		messages.addField(MessageFields.CREATED.getLabel());
		messages.addField(MessageFields.UPDATED.getLabel());
		messages.addField(MessageFields.ANNOTATIONS.getLabel());
		messages.addChild(createdBy);
		messages.addChild(updatedBy);
		conversation.addChild(messages);
		spaces.addChild(conversation);

		setRequest(new GraphQLRequest(spaces, "getSpaces"));
		executeRequest();
		return (List<? extends Space>) parseResultContainer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.endpoints.AbstractWWGraphQLEndpoint#parseResultContainer()
	 */
	public Object parseResultContainer() {
		return getResultContainer().getData().getSpaces().getItems();
	}

}
