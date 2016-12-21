package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Conversation.ConversationFields;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.bo.PageInfo;
import org.opencode4workspace.bo.PageInfo.PageInfoFields;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.builders.BaseGraphQLQuery;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;
import org.opencode4workspace.builders.SpacesGraphQLQuery;
import org.opencode4workspace.graphql.BasicPaginationEnum;
import org.opencode4workspace.json.GraphQLRequest;

public class FieldDataSenderTest {

	private static final String EXPECTED_PAGE_QUERY = "pageInfo {startCursor endCursor}";
	private static final String GET_SPACES_QUERY = "query getSpaces {spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {id title description created updated membersUpdated}}}";
	private static final String GET_SPACES_BIGGER_QUERY = "query getSpaces {spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {id title description created updated membersUpdated updatedBy {id displayName photoUrl email} createdBy {id displayName photoUrl email} members (first: 100) {items {id photoUrl email displayName}}}}}";
	private static final String GET_SPACES_FULL_QUERY = "query getSpaces {spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {id title description created updated membersUpdated updatedBy {id displayName photoUrl email} createdBy {id displayName photoUrl email} members (first: 100) {items {id photoUrl email displayName}} conversation {id created updated createdBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email} messages (first: 20) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {contentType content id created updated createdBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email}}}}}}}";

	@Test
	public void testGetPageInfoFields() {
		ObjectDataSenderBuilder fb = new ObjectDataSenderBuilder(PageInfo.PAGE_QUERY_OBJECT_NAME, false);
		assertEquals(0, fb.getFieldsList().size());
		fb = new ObjectDataSenderBuilder(PageInfo.PAGE_QUERY_OBJECT_NAME, PageInfo.class, false);
		assertEquals(4, fb.getFieldsList().size());
		fb = new ObjectDataSenderBuilder(PageInfo.PAGE_QUERY_OBJECT_NAME, PageInfo.PageInfoFields.values());
		assertEquals(4, fb.getFieldsList().size());
		fb = new ObjectDataSenderBuilder(PageInfo.PAGE_QUERY_OBJECT_NAME);
		fb.addField(PageInfoFields.START_CURSOR);
		fb.addField(PageInfoFields.END_CURSOR);
		assertEquals(EXPECTED_PAGE_QUERY, fb.build());
	}

	@Test
	public void testSpacesObjectQuery() throws WWException {
		ObjectDataSenderBuilder spaces = new ObjectDataSenderBuilder(Space.SPACES_QUERY_OBJECT_NAME, true);
		spaces.addAttribute(BasicPaginationEnum.FIRST, 100);
		spaces.addPageInfo();
		spaces.addField(Space.SpaceFields.ID);
		spaces.addField(SpaceFields.TITLE);
		spaces.addField(SpaceFields.DESCRIPTION);
		spaces.addField(SpaceFields.CREATED);
		spaces.addField(SpaceFields.UPDATED);
		spaces.addField(SpaceFields.MEMBERS_UPDATED);
		SpacesGraphQLQuery query = new SpacesGraphQLQuery(spaces);
		GraphQLRequest request = new GraphQLRequest(query);
		assertEquals(GET_SPACES_QUERY, request.getQuery());
	}

	@Test
	public void testSpacesBiggerObjectQuery() throws WWException {
		ObjectDataSenderBuilder spaces = new ObjectDataSenderBuilder(Space.SPACES_QUERY_OBJECT_NAME, true);
		spaces.addAttribute(BasicPaginationEnum.FIRST, 100);
		spaces.addPageInfo();
		spaces.addField(Space.SpaceFields.ID);
		spaces.addField(SpaceFields.TITLE);
		spaces.addField(SpaceFields.DESCRIPTION);
		spaces.addField(SpaceFields.CREATED);
		spaces.addField(SpaceFields.UPDATED);
		spaces.addField(SpaceFields.MEMBERS_UPDATED);
		ObjectDataSenderBuilder updatedBy = new ObjectDataSenderBuilder(SpaceChildren.UPDATED_BY.getLabel());
		updatedBy.addField(PersonFields.ID);
		updatedBy.addField(PersonFields.DISPLAY_NAME);
		updatedBy.addField(PersonFields.PHOTO_URL);
		updatedBy.addField(PersonFields.EMAIL);
		spaces.addChild(updatedBy);
		ObjectDataSenderBuilder createdBy = new ObjectDataSenderBuilder(SpaceChildren.CREATED_BY.getLabel());
		createdBy.addField(PersonFields.ID);
		createdBy.addField(PersonFields.DISPLAY_NAME);
		createdBy.addField(PersonFields.PHOTO_URL);
		createdBy.addField(PersonFields.EMAIL);
		spaces.addChild(createdBy);
		ObjectDataSenderBuilder members = new ObjectDataSenderBuilder((SpaceChildren.MEMBERS.getLabel()), true);
		members.addAttribute(BasicPaginationEnum.FIRST, 100);
		members.addField(PersonFields.ID);
		members.addField(PersonFields.PHOTO_URL);
		members.addField(PersonFields.EMAIL);
		members.addField(PersonFields.DISPLAY_NAME);
		spaces.addChild(members);
		SpacesGraphQLQuery query = new SpacesGraphQLQuery(spaces);
		assertEquals(GET_SPACES_BIGGER_QUERY, query.returnQuery());
	}

	@Test
	public void testSpacesFullObjectQuery() throws WWException {
		ObjectDataSenderBuilder spaces = new ObjectDataSenderBuilder(Space.SPACES_QUERY_OBJECT_NAME, true);
		spaces.addAttribute(BasicPaginationEnum.FIRST, 100);
		spaces.addPageInfo();
		spaces.addField(Space.SpaceFields.ID);
		spaces.addField(SpaceFields.TITLE);
		spaces.addField(SpaceFields.DESCRIPTION);
		spaces.addField(SpaceFields.CREATED);
		spaces.addField(SpaceFields.UPDATED);
		spaces.addField(SpaceFields.MEMBERS_UPDATED);
		ObjectDataSenderBuilder updatedBy = new ObjectDataSenderBuilder(SpaceChildren.UPDATED_BY.getLabel());
		updatedBy.addField(PersonFields.ID);
		updatedBy.addField(PersonFields.DISPLAY_NAME);
		updatedBy.addField(PersonFields.PHOTO_URL);
		updatedBy.addField(PersonFields.EMAIL);
		spaces.addChild(updatedBy);
		ObjectDataSenderBuilder createdBy = new ObjectDataSenderBuilder(SpaceChildren.CREATED_BY.getLabel());
		createdBy.addField(PersonFields.ID);
		createdBy.addField(PersonFields.DISPLAY_NAME);
		createdBy.addField(PersonFields.PHOTO_URL);
		createdBy.addField(PersonFields.EMAIL);
		spaces.addChild(createdBy);
		ObjectDataSenderBuilder members = new ObjectDataSenderBuilder(SpaceChildren.MEMBERS.getLabel(), true);
		members.addAttribute(BasicPaginationEnum.FIRST, 100);
		members.addField(PersonFields.ID);
		members.addField(PersonFields.PHOTO_URL);
		members.addField(PersonFields.EMAIL);
		members.addField(PersonFields.DISPLAY_NAME);
		spaces.addChild(members);
		ObjectDataSenderBuilder conversation = new ObjectDataSenderBuilder(SpaceChildren.CONVERSATION.getLabel(), false);
		conversation.addField(ConversationFields.ID);
		conversation.addField(ConversationFields.CREATED);
		conversation.addField(ConversationFields.UPDATED);
		createdBy = new ObjectDataSenderBuilder(ConversationChildren.CREATED_BY.getLabel());
		createdBy.addField(PersonFields.ID);
		createdBy.addField(PersonFields.DISPLAY_NAME);
		createdBy.addField(PersonFields.PHOTO_URL);
		createdBy.addField(PersonFields.EMAIL);
		conversation.addChild(createdBy);
		updatedBy = new ObjectDataSenderBuilder(ConversationChildren.UPDATED_BY.getLabel());
		updatedBy.addField(PersonFields.ID);
		updatedBy.addField(PersonFields.DISPLAY_NAME);
		updatedBy.addField(PersonFields.PHOTO_URL);
		updatedBy.addField(PersonFields.EMAIL);
		conversation.addChild(updatedBy);
		ObjectDataSenderBuilder messages = new ObjectDataSenderBuilder(ConversationChildren.MESSAGES.getLabel(), true);
		messages.addAttribute(BasicPaginationEnum.FIRST, 20);
		messages.addPageInfo();
		messages.addField(MessageFields.CONTENT_TYPE);
		messages.addField(MessageFields.CONTENT);
		messages.addField(MessageFields.ID);
		messages.addField(MessageFields.CREATED);
		messages.addField(MessageFields.UPDATED);
		messages.addChild(createdBy);
		messages.addChild(updatedBy);
		conversation.addChild(messages);
		spaces.addChild(conversation);
		SpacesGraphQLQuery graphQuery = new SpacesGraphQLQuery(spaces);
		assertEquals(GET_SPACES_FULL_QUERY, graphQuery.returnQuery());
	}

	@Test
	public void attributesErrorTest() {
		try {
			ObjectDataSenderBuilder spaces = new ObjectDataSenderBuilder(Space.SPACES_QUERY_OBJECT_NAME, true);
			spaces.addAttribute(BasicPaginationEnum.FIRST, "100");
			assertTrue(false);
		} catch (Exception e) {
			assertEquals(e.getClass().getName(), WWException.class.getName());
		}
	}

}
