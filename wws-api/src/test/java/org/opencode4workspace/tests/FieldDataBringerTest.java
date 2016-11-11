package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.opencode4workspace.bo.PageInfo;
import org.opencode4workspace.bo.PageInfo.PageInfoFields;
import org.opencode4workspace.bo.Person.PersonInfoFields;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.graphql.builders.BasicPaginationEnum;
import org.opencode4workspace.graphql.builders.ObjectDataBringer;
import org.opencode4workspace.graphql.builders.SpacesGraphQLQuery;

public class FieldDataBringerTest {

	private static final String EXPECTED_PAGE_QUERY = "pageInfo {startCursor endCursor}";
	private static final String GET_SPACES_QUERY = "query getSpaces {spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {id title description created updated membersUpdated}}}";
	private static final String GET_SPACES_BIGGER_QUERY = "query getSpaces {spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {id title description created updated membersUpdated updatedBy {id displayName photoUrl email} createdBy {id displayName photoUrl email} members(first: 100) {items {id photoUrl email displayName}}}}}";

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
	public void testSpacesObjectQuery() {
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
		graphQuery.setQueryObject(spaces);
		assertEquals(GET_SPACES_QUERY, graphQuery.returnQuery());
	}

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
		ObjectDataBringer createdBy = new ObjectDataBringer(SpaceChildren.CREATED_BY.getLabel(), SpaceChildren.CREATED_BY.getEnumClass(), true);
		spaces.addChild(createdBy);
		ObjectDataBringer updatedBy = new ObjectDataBringer(SpaceChildren.UPDATED_BY.getLabel(), SpaceChildren.UPDATED_BY.getEnumClass(), true);
		graphQuery.setQueryObject(spaces);
		ObjectDataBringer members = new ObjectDataBringer((SpaceChildren.MEMBERS.getLabel()), true);
		members.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		members.addField(PersonInfoFields.ID.getLabel());
		members.addField(PersonInfoFields.DISPLAY_NAME.getLabel());
		members.addField(PersonInfoFields.EMAIL.getLabel());
		members.addField(PersonInfoFields.PHOTO_URL.getLabel());
		assertEquals(GET_SPACES_QUERY, graphQuery.returnQuery());
	}

}
