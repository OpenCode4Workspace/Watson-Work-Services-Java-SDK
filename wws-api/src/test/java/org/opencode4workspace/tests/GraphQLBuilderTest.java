package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Conversation.ConversationFields;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.builders.BasicCreatedByUpdatedByDataSenderBuilder;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;
import org.opencode4workspace.builders.SpaceMembersGraphQLQuery;
import org.opencode4workspace.builders.SpacesGraphQLQuery;
import org.opencode4workspace.graphql.BasicPaginationEnum;

public class GraphQLBuilderTest {

	public final static String SPACES_QUERY = "spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {id title description updated created updatedBy {id displayName photoUrl email} createdBy {id displayName photoUrl email} members (first: 100) {items {id photoUrl email displayName}} conversation {id created updated createdBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email} messages (first: 200) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {contentType content created updated createdBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email}}}}}}";
	public final static String GET_SPACES_QUERY = "spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {id title description updated created updatedBy {id displayName photoUrl email} createdBy {id displayName photoUrl email} members (first: 100) {items {id photoUrl email displayName}} conversation {id created updated createdBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email} messages (first: 200) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {contentType content created updated annotations id createdBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email}}}}}}";
	public final static String SPACES_ALL_QUERY = "spaces {items {id description title created createdBy updated updatedBy memberList membersUpdated conversation}}";

	@Test
	public void testBuilder() throws WWException {

		ObjectDataSenderBuilder spaces = new ObjectDataSenderBuilder(Space.SPACES_QUERY_OBJECT_NAME, true);
		spaces.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		spaces.addPageInfo();
		spaces.addField(SpaceFields.ID);
		spaces.addField(SpaceFields.TITLE);
		spaces.addField(SpaceFields.DESCRIPTION);
		spaces.addField(SpaceFields.UPDATED);
		spaces.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(SpaceChildren.UPDATED_BY));
		spaces.addField(SpaceFields.CREATED);
		spaces.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(SpaceChildren.CREATED_BY));
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
		conversation.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(ConversationChildren.CREATED_BY));
		conversation.addField(ConversationFields.UPDATED);
		conversation.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(ConversationChildren.UPDATED_BY));
		ObjectDataSenderBuilder messages = new ObjectDataSenderBuilder(ConversationChildren.MESSAGES.getLabel(), true);
		messages.addAttribute(BasicPaginationEnum.FIRST, 200);
		messages.addPageInfo();
		messages.addField(MessageFields.CONTENT_TYPE);
		messages.addField(MessageFields.CONTENT);
		messages.addField(MessageFields.CREATED);
		messages.addField(MessageFields.UPDATED);
		messages.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(ConversationChildren.CREATED_BY));
		messages.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(ConversationChildren.UPDATED_BY));
		conversation.addChild(messages);
		spaces.addChild(conversation);
		String result = spaces.build();
		assertEquals(result, SPACES_QUERY);
	}

	@Test
	public void testGetSpacesBuilderDefault() throws WWException {
		SpacesGraphQLQuery spaces = SpacesGraphQLQuery.buildStandardGetSpacesQuery();
		assertEquals(GET_SPACES_QUERY, spaces.getQueryObject().build());
	}

	@Test
	public void testClazzBasedBuilder() {
		ObjectDataSenderBuilder spaces = new ObjectDataSenderBuilder(Space.SPACES_QUERY_OBJECT_NAME, Space.class, true);
		assertEquals(SPACES_ALL_QUERY, spaces.build());
	}

	@Test
	public void getSpaces() throws WWException {
		SpaceMembersGraphQLQuery spaceMembers = SpaceMembersGraphQLQuery.buildSpaceMemberGraphQueryBySpaceId("");
		assertNotNull(spaceMembers.getQueryObject());
	}
}
