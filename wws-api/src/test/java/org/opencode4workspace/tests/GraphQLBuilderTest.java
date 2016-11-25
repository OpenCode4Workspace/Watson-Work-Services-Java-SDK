package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Conversation.ConversationFields;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.bo.Profile.PersonFields;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;
import org.opencode4workspace.graphql.BasicPaginationEnum;

public class GraphQLBuilderTest {

	public final static String SPACES_QUERY = "spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {id title description updated created updatedBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email} members (first: 100) {items {id photoUrl email displayName}} conversation {id created updated updatedBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email} messages (first: 200) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {contentType content created updated updatedBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email}}}}}}";
	public final static String SPACES_ALL_QUERY = "spaces {items {id description title created createdBy updated updatedBy memberList membersUpdated conversation}}";

	@Test
	public void testBuilder() throws WWException {
		ObjectDataSenderBuilder createdBy = new ObjectDataSenderBuilder(SpaceChildren.UPDATED_BY.getLabel());
		createdBy.addField(PersonFields.ID);
		createdBy.addField(PersonFields.DISPLAY_NAME);
		createdBy.addField(PersonFields.PHOTO_URL);
		createdBy.addField(PersonFields.EMAIL);

		// Basic updatedBy ObjectDataBringer - same label for all
		ObjectDataSenderBuilder updatedBy = new ObjectDataSenderBuilder(SpaceChildren.UPDATED_BY.getLabel());
		updatedBy.addField(PersonFields.ID);
		updatedBy.addField(PersonFields.DISPLAY_NAME);
		updatedBy.addField(PersonFields.PHOTO_URL);
		updatedBy.addField(PersonFields.EMAIL);

		ObjectDataSenderBuilder spaces = new ObjectDataSenderBuilder(Space.SPACE_QUERY_OBJECT_NAME, true);
		spaces.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		spaces.addPageInfo();
		spaces.addField(SpaceFields.ID);
		spaces.addField(SpaceFields.TITLE);
		spaces.addField(SpaceFields.DESCRIPTION);
		spaces.addField(SpaceFields.UPDATED);
		spaces.addChild(updatedBy);
		spaces.addField(SpaceFields.CREATED);
		spaces.addChild(createdBy);
		ObjectDataSenderBuilder members = new ObjectDataSenderBuilder(SpaceChildren.MEMBERS.getLabel(), true);
		members.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		members.addField(PersonFields.ID);
		members.addField(PersonFields.PHOTO_URL);
		members.addField(PersonFields.EMAIL);
		members.addField(PersonFields.DISPLAY_NAME);
		spaces.addChild(members);
		ObjectDataSenderBuilder conversation = new ObjectDataSenderBuilder(SpaceChildren.CONVERSATION.getLabel());
		conversation.addField(ConversationFields.ID);
		conversation.addField(ConversationFields.CREATED);
		conversation.addChild(createdBy);
		conversation.addField(ConversationFields.UPDATED);
		conversation.addChild(updatedBy);
		ObjectDataSenderBuilder messages = new ObjectDataSenderBuilder(ConversationChildren.MESSAGES.getLabel(), true);
		messages.addAttribute(BasicPaginationEnum.FIRST, 200);
		messages.addPageInfo();
		messages.addField(MessageFields.CONTENT_TYPE);
		messages.addField(MessageFields.CONTENT);
		messages.addField(MessageFields.CREATED);
		messages.addField(MessageFields.UPDATED);
		messages.addChild(createdBy);
		messages.addChild(updatedBy);
		conversation.addChild(messages);
		spaces.addChild(conversation);
		String result = spaces.build();
		assertEquals(result, SPACES_QUERY);
	}

	@Test
	public void testClazzBasedBuilder() {
		ObjectDataSenderBuilder spaces = new ObjectDataSenderBuilder(Space.SPACE_QUERY_OBJECT_NAME, Space.class, true);
		assertEquals(SPACES_ALL_QUERY, spaces.build());
	}
}
