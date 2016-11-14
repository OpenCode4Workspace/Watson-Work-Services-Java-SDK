package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Conversation.ConversationFields;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.bo.PageInfo;
import org.opencode4workspace.bo.PageInfo.PageInfoFields;
import org.opencode4workspace.bo.Person.PersonInfoFields;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.graphql.builders.BaseGraphQLQuery;
import org.opencode4workspace.graphql.builders.BasicPaginationEnum;
import org.opencode4workspace.graphql.builders.ObjectDataBringer;
import org.opencode4workspace.graphql.builders.SpacesGraphQLQuery;
import org.opencode4workspace.json.GraphQLRequest;

public class FieldDataBringerTest {

	private static final String EXPECTED_PAGE_QUERY = "pageInfo {startCursor endCursor}";
	private static final String GET_SPACES_QUERY = "query getSpaces {spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {id title description created updated membersUpdated}}}";
	private static final String GET_SPACES_BIGGER_QUERY = "query getSpaces {spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {id title description created updated membersUpdated updatedBy {id displayName photoUrl email} createdBy {id displayName photoUrl email} members (first: 100) {items {id photoUrl email displayName}}}}}";
	private static final String GET_SPACES_FULL_QUERY = "query getSpaces {spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {id title description created updated membersUpdated updatedBy {id displayName photoUrl email} createdBy {id displayName photoUrl email} members (first: 100) {items {id photoUrl email displayName}} conversation {id created updated createdBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email} messages (first: 20) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {contentType content id created updated createdBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email}}}}}}}";

	@Test
	public void testGetPageInfoFields() {
		ObjectDataBringer fb = new ObjectDataBringer(PageInfo.LABEL, false);
		assertEquals(0, fb.getFieldsList().size());
		fb = new ObjectDataBringer(PageInfo.LABEL, PageInfo.class, true);
		assertEquals(4, fb.getFieldsList().size());
		fb = new ObjectDataBringer(PageInfo.LABEL, PageInfo.class, PageInfo.PageInfoFields.values());
		assertEquals(4, fb.getFieldsList().size());
		fb = new ObjectDataBringer(PageInfo.LABEL);
		fb.addField(PageInfoFields.START_CURSOR.getLabel());
		fb.addField(PageInfoFields.END_CURSOR.getLabel());
		assertEquals(EXPECTED_PAGE_QUERY, fb.buildQuery());
	}

	@Test
	public void testSpacesObjectQuery() throws WWException {
		BaseGraphQLQuery query = new BaseGraphQLQuery("getSpaces");
		ObjectDataBringer spaces = new ObjectDataBringer("spaces", true);
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
		ObjectDataBringer spaces = new ObjectDataBringer("spaces", true);
		spaces.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		spaces.addPageInfo();
		spaces.addField(Space.SpaceFields.ID.getLabel());
		spaces.addField(SpaceFields.TITLE.getLabel());
		spaces.addField(SpaceFields.DESCRIPTION.getLabel());
		spaces.addField(SpaceFields.CREATED.getLabel());
		spaces.addField(SpaceFields.UPDATED.getLabel());
		spaces.addField(SpaceFields.MEMBERS_UPDATED.getLabel());
		ObjectDataBringer updatedBy = new ObjectDataBringer(SpaceChildren.UPDATED_BY.getLabel(), SpaceChildren.UPDATED_BY.getEnumClass(), false);
		updatedBy.addField(PersonInfoFields.ID.getLabel());
		updatedBy.addField(PersonInfoFields.DISPLAY_NAME.getLabel());
		updatedBy.addField(PersonInfoFields.PHOTO_URL.getLabel());
		updatedBy.addField(PersonInfoFields.EMAIL.getLabel());
		spaces.addChild(updatedBy);
		ObjectDataBringer createdBy = new ObjectDataBringer(SpaceChildren.CREATED_BY.getLabel(), SpaceChildren.CREATED_BY.getEnumClass(), false);
		createdBy.addField(PersonInfoFields.ID.getLabel());
		createdBy.addField(PersonInfoFields.DISPLAY_NAME.getLabel());
		createdBy.addField(PersonInfoFields.PHOTO_URL.getLabel());
		createdBy.addField(PersonInfoFields.EMAIL.getLabel());
		spaces.addChild(createdBy);
		ObjectDataBringer members = new ObjectDataBringer((SpaceChildren.MEMBERS.getLabel()), true);
		members.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		members.addField(PersonInfoFields.ID.getLabel());
		members.addField(PersonInfoFields.PHOTO_URL.getLabel());
		members.addField(PersonInfoFields.EMAIL.getLabel());
		members.addField(PersonInfoFields.DISPLAY_NAME.getLabel());
		spaces.addChild(members);
		query.setQueryObject(spaces);
		assertEquals(GET_SPACES_BIGGER_QUERY, query.returnQuery());
	}

	@Test
	public void testSpacesFullObjectQuery() {
		SpacesGraphQLQuery graphQuery = new SpacesGraphQLQuery();
		ObjectDataBringer spaces = new ObjectDataBringer("spaces", true);
		spaces.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		spaces.addPageInfo();
		spaces.addField(Space.SpaceFields.ID.getLabel());
		spaces.addField(SpaceFields.TITLE.getLabel());
		spaces.addField(SpaceFields.DESCRIPTION.getLabel());
		spaces.addField(SpaceFields.CREATED.getLabel());
		spaces.addField(SpaceFields.UPDATED.getLabel());
		spaces.addField(SpaceFields.MEMBERS_UPDATED.getLabel());
		ObjectDataBringer updatedBy = new ObjectDataBringer(SpaceChildren.UPDATED_BY.getLabel(), SpaceChildren.UPDATED_BY.getEnumClass(), false);
		updatedBy.addField(PersonInfoFields.ID.getLabel());
		updatedBy.addField(PersonInfoFields.DISPLAY_NAME.getLabel());
		updatedBy.addField(PersonInfoFields.PHOTO_URL.getLabel());
		updatedBy.addField(PersonInfoFields.EMAIL.getLabel());
		spaces.addChild(updatedBy);
		ObjectDataBringer createdBy = new ObjectDataBringer(SpaceChildren.CREATED_BY.getLabel(), SpaceChildren.CREATED_BY.getEnumClass(), false);
		createdBy.addField(PersonInfoFields.ID.getLabel());
		createdBy.addField(PersonInfoFields.DISPLAY_NAME.getLabel());
		createdBy.addField(PersonInfoFields.PHOTO_URL.getLabel());
		createdBy.addField(PersonInfoFields.EMAIL.getLabel());
		spaces.addChild(createdBy);
		ObjectDataBringer members = new ObjectDataBringer(SpaceChildren.MEMBERS.getLabel(), true);
		members.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		members.addField(PersonInfoFields.ID.getLabel());
		members.addField(PersonInfoFields.PHOTO_URL.getLabel());
		members.addField(PersonInfoFields.EMAIL.getLabel());
		members.addField(PersonInfoFields.DISPLAY_NAME.getLabel());
		spaces.addChild(members);
		ObjectDataBringer conversation = new ObjectDataBringer(SpaceChildren.CONVERSATION.getLabel(), false);
		conversation.addField(ConversationFields.ID.getLabel());
		conversation.addField(ConversationFields.CREATED.getLabel());
		conversation.addField(ConversationFields.UPDATED.getLabel());
		createdBy = new ObjectDataBringer(ConversationChildren.CREATED_BY.getLabel(), ConversationChildren.CREATED_BY.getEnumClass(), false);
		createdBy.addField(PersonInfoFields.ID.getLabel());
		createdBy.addField(PersonInfoFields.DISPLAY_NAME.getLabel());
		createdBy.addField(PersonInfoFields.PHOTO_URL.getLabel());
		createdBy.addField(PersonInfoFields.EMAIL.getLabel());
		conversation.addChild(createdBy);
		updatedBy = new ObjectDataBringer(ConversationChildren.UPDATED_BY.getLabel(), ConversationChildren.UPDATED_BY.getEnumClass(), false);
		updatedBy.addField(PersonInfoFields.ID.getLabel());
		updatedBy.addField(PersonInfoFields.DISPLAY_NAME.getLabel());
		updatedBy.addField(PersonInfoFields.PHOTO_URL.getLabel());
		updatedBy.addField(PersonInfoFields.EMAIL.getLabel());
		conversation.addChild(updatedBy);
		ObjectDataBringer messages = new ObjectDataBringer(ConversationChildren.MESSAGES.getLabel(), true);
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
