package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Conversation.ConversationFields;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;
import org.opencode4workspace.graphql.BasicPaginationEnum;

public class GraphQLBuilderTest {

	public final static String SPACES_QUERY = "spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {id title description updated created updatedBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email} members (first: 100) {items {id photoUrl email displayName}} conversation {id created updated updatedBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email} messages (first: 200) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {contentType content created updated updatedBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email}}}}}}";
	public final static String SPACES_ALL_QUERY = "spaces {items {id description title created createdBy updated updatedBy memberList membersUpdated conversation}}";

	@Test
	public void testBuilder() {
		ObjectDataSenderBuilder createdBy = new ObjectDataSenderBuilder(SpaceChildren.UPDATED_BY.getLabel());
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

		ObjectDataSenderBuilder spaces = new ObjectDataSenderBuilder("spaces", true);
		spaces.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		spaces.addPageInfo();
		spaces.addField(SpaceFields.ID.getLabel());
		spaces.addField(SpaceFields.TITLE.getLabel());
		spaces.addField(SpaceFields.DESCRIPTION.getLabel());
		spaces.addField(SpaceFields.UPDATED.getLabel());
		spaces.addChild(updatedBy);
		spaces.addField(SpaceFields.CREATED.getLabel());
		spaces.addChild(createdBy);
		ObjectDataSenderBuilder members = new ObjectDataSenderBuilder(SpaceChildren.MEMBERS.getLabel(), SpaceChildren.MEMBERS.getEnumClass(), true, false);
		members.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
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
		messages.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 200);
		messages.addPageInfo();
		messages.addField(MessageFields.CONTENT_TYPE.getLabel());
		messages.addField(MessageFields.CONTENT.getLabel());
		messages.addField(MessageFields.CREATED.getLabel());
		messages.addField(MessageFields.UPDATED.getLabel());
		messages.addChild(createdBy);
		messages.addChild(updatedBy);
		conversation.addChild(messages);
		spaces.addChild(conversation);
		String result = spaces.build();
		assertEquals(result, SPACES_QUERY);
	}

	@Test
	public void testClazzBasedBuilder() {
		ObjectDataSenderBuilder spaces = new ObjectDataSenderBuilder("spaces", Space.class, true, true);
		assertEquals(SPACES_ALL_QUERY, spaces.build());
	}
}
