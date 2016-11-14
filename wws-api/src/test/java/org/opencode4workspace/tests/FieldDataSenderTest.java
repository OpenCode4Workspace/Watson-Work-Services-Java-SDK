package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

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
import org.opencode4workspace.graphql.builders.BaseGraphQLQuery;
import org.opencode4workspace.graphql.builders.BasicPaginationEnum;
import org.opencode4workspace.graphql.builders.ObjectDataSender;
import org.opencode4workspace.graphql.builders.SpacesGraphQLQuery;
import org.opencode4workspace.json.GraphQLRequest;

public class FieldDataSenderTest {

	private static final String EXPECTED_PAGE_QUERY = "pageInfo {startCursor endCursor}";
	private static final String GET_SPACES_QUERY = "query getSpaces {spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {id title description created updated membersUpdated}}}";
	private static final String GET_SPACES_BIGGER_QUERY = "query getSpaces {spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {id title description created updated membersUpdated updatedBy {id displayName photoUrl email} createdBy {id displayName photoUrl email} members (first: 100) {items {id photoUrl email displayName}}}}}";
	private static final String GET_SPACES_FULL_QUERY = "query getSpaces {spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {id title description created updated membersUpdated updatedBy {id displayName photoUrl email} createdBy {id displayName photoUrl email} members (first: 100) {items {id photoUrl email displayName}} conversation {id created updated createdBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email} messages (first: 20) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {contentType content id created updated createdBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email}}}}}}}";

	@Test
	public void testGetPageInfoFields() {
		ObjectDataSender fb = new ObjectDataSender(PageInfo.LABEL, false);
		assertEquals(0, fb.getFieldsList().size());
		fb = new ObjectDataSender(PageInfo.LABEL, PageInfo.class, false, true);
		assertEquals(4, fb.getFieldsList().size());
		fb = new ObjectDataSender(PageInfo.LABEL, PageInfo.class, PageInfo.PageInfoFields.values());
		assertEquals(4, fb.getFieldsList().size());
		fb = new ObjectDataSender(PageInfo.LABEL);
		fb.addField(PageInfoFields.START_CURSOR.getLabel());
		fb.addField(PageInfoFields.END_CURSOR.getLabel());
		assertEquals(EXPECTED_PAGE_QUERY, fb.buildQuery());
	}

	@Test
	public void testSpacesObjectQuery() throws WWException {
		BaseGraphQLQuery query = new BaseGraphQLQuery("getSpaces");
		ObjectDataSender spaces = new ObjectDataSender("spaces", true);
		spaces.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		spaces.addPageInfo();
		spaces.addField(Space.SpaceFields.ID.getLabel());
		spaces.addField(SpaceFields.TITLE.getLabel());
		spaces.addField(SpaceFields.DESCRIPTION.getLabel());
		spaces.addField(SpaceFields.CREATED.getLabel());
		spaces.addField(SpaceFields.UPDATED.getLabel());
		spaces.addField(SpaceFields.MEMBERS_UPDATED.getLabel());
		query.setQueryObject(spaces);
		GraphQLRequest request = new GraphQLRequest(query);
		assertEquals(GET_SPACES_QUERY, request.getQuery());
	}

	@Test
	public void testSpacesBiggerObjectQuery() {
		BaseGraphQLQuery query = new BaseGraphQLQuery();
		query.setOperationName("getSpaces");
		ObjectDataSender spaces = new ObjectDataSender("spaces", true);
		spaces.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		spaces.addPageInfo();
		spaces.addField(Space.SpaceFields.ID.getLabel());
		spaces.addField(SpaceFields.TITLE.getLabel());
		spaces.addField(SpaceFields.DESCRIPTION.getLabel());
		spaces.addField(SpaceFields.CREATED.getLabel());
		spaces.addField(SpaceFields.UPDATED.getLabel());
		spaces.addField(SpaceFields.MEMBERS_UPDATED.getLabel());
		ObjectDataSender updatedBy = new ObjectDataSender(SpaceChildren.UPDATED_BY.getLabel());
		updatedBy.addField(PersonFields.ID.getLabel());
		updatedBy.addField(PersonFields.DISPLAY_NAME.getLabel());
		updatedBy.addField(PersonFields.PHOTO_URL.getLabel());
		updatedBy.addField(PersonFields.EMAIL.getLabel());
		spaces.addChild(updatedBy);
		ObjectDataSender createdBy = new ObjectDataSender(SpaceChildren.CREATED_BY.getLabel());
		createdBy.addField(PersonFields.ID.getLabel());
		createdBy.addField(PersonFields.DISPLAY_NAME.getLabel());
		createdBy.addField(PersonFields.PHOTO_URL.getLabel());
		createdBy.addField(PersonFields.EMAIL.getLabel());
		spaces.addChild(createdBy);
		ObjectDataSender members = new ObjectDataSender((SpaceChildren.MEMBERS.getLabel()), true);
		members.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		members.addField(PersonFields.ID.getLabel());
		members.addField(PersonFields.PHOTO_URL.getLabel());
		members.addField(PersonFields.EMAIL.getLabel());
		members.addField(PersonFields.DISPLAY_NAME.getLabel());
		spaces.addChild(members);
		query.setQueryObject(spaces);
		assertEquals(GET_SPACES_BIGGER_QUERY, query.returnQuery());
	}

	@Test
	public void testSpacesFullObjectQuery() {
		SpacesGraphQLQuery graphQuery = new SpacesGraphQLQuery();
		ObjectDataSender spaces = new ObjectDataSender("spaces", true);
		spaces.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		spaces.addPageInfo();
		spaces.addField(Space.SpaceFields.ID.getLabel());
		spaces.addField(SpaceFields.TITLE.getLabel());
		spaces.addField(SpaceFields.DESCRIPTION.getLabel());
		spaces.addField(SpaceFields.CREATED.getLabel());
		spaces.addField(SpaceFields.UPDATED.getLabel());
		spaces.addField(SpaceFields.MEMBERS_UPDATED.getLabel());
		ObjectDataSender updatedBy = new ObjectDataSender(SpaceChildren.UPDATED_BY.getLabel());
		updatedBy.addField(PersonFields.ID.getLabel());
		updatedBy.addField(PersonFields.DISPLAY_NAME.getLabel());
		updatedBy.addField(PersonFields.PHOTO_URL.getLabel());
		updatedBy.addField(PersonFields.EMAIL.getLabel());
		spaces.addChild(updatedBy);
		ObjectDataSender createdBy = new ObjectDataSender(SpaceChildren.CREATED_BY.getLabel());
		createdBy.addField(PersonFields.ID.getLabel());
		createdBy.addField(PersonFields.DISPLAY_NAME.getLabel());
		createdBy.addField(PersonFields.PHOTO_URL.getLabel());
		createdBy.addField(PersonFields.EMAIL.getLabel());
		spaces.addChild(createdBy);
		ObjectDataSender members = new ObjectDataSender(SpaceChildren.MEMBERS.getLabel(), true);
		members.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		members.addField(PersonFields.ID.getLabel());
		members.addField(PersonFields.PHOTO_URL.getLabel());
		members.addField(PersonFields.EMAIL.getLabel());
		members.addField(PersonFields.DISPLAY_NAME.getLabel());
		spaces.addChild(members);
		ObjectDataSender conversation = new ObjectDataSender(SpaceChildren.CONVERSATION.getLabel(), false);
		conversation.addField(ConversationFields.ID.getLabel());
		conversation.addField(ConversationFields.CREATED.getLabel());
		conversation.addField(ConversationFields.UPDATED.getLabel());
		createdBy = new ObjectDataSender(ConversationChildren.CREATED_BY.getLabel());
		createdBy.addField(PersonFields.ID.getLabel());
		createdBy.addField(PersonFields.DISPLAY_NAME.getLabel());
		createdBy.addField(PersonFields.PHOTO_URL.getLabel());
		createdBy.addField(PersonFields.EMAIL.getLabel());
		conversation.addChild(createdBy);
		updatedBy = new ObjectDataSender(ConversationChildren.UPDATED_BY.getLabel());
		updatedBy.addField(PersonFields.ID.getLabel());
		updatedBy.addField(PersonFields.DISPLAY_NAME.getLabel());
		updatedBy.addField(PersonFields.PHOTO_URL.getLabel());
		updatedBy.addField(PersonFields.EMAIL.getLabel());
		conversation.addChild(updatedBy);
		ObjectDataSender messages = new ObjectDataSender(ConversationChildren.MESSAGES.getLabel(), true);
		messages.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 20);
		messages.addPageInfo();
		messages.addField(MessageFields.CONTENT_TYPE.getLabel());
		messages.addField(MessageFields.CONTENT.getLabel());
		messages.addField(MessageFields.ID.getLabel());
		messages.addField(MessageFields.CREATED.getLabel());
		messages.addField(MessageFields.UPDATED.getLabel());
		messages.addChild(createdBy);
		messages.addChild(updatedBy);
		conversation.addChild(messages);
		spaces.addChild(conversation);
		graphQuery.setQueryObject(spaces);
		assertEquals(GET_SPACES_FULL_QUERY, graphQuery.returnQuery());
	}

}
