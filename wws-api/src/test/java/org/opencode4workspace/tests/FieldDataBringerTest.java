package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.opencode4workspace.bo.PageInfo;
import org.opencode4workspace.bo.PageInfo.PageInfoFields;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.graphql.builders.ObjectDataBringer;
import org.opencode4workspace.graphql.builders.SpacesGraphQLQuery;

public class FieldDataBringerTest {

	private static final String EXPECTED_PAGE_QUERY = "pageInfo {startCursor endCursor}";
	private static final String GET_SPACES_QUERY = "query getSpaces {spaces (first: 100) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage}items {id title description created updated membersUpdated}}}";

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
		spaces.addAttribute("first", 100);
		ObjectDataBringer pageInfo = new ObjectDataBringer(PageInfo.LABEL, PageInfo.class, true);
		spaces.addChild(pageInfo);
		spaces.addField(Space.SpaceFields.ID.getLabel());
		spaces.addField(SpaceFields.TITLE.getLabel());
		spaces.addField(SpaceFields.DESCRIPTION.getLabel());
		spaces.addField(SpaceFields.CREATED.getLabel());
		spaces.addField(SpaceFields.UPDATED.getLabel());
		spaces.addField(SpaceFields.MEMBERS_UPDATED.getLabel());
		graphQuery.setQueryObject(spaces);
		assertEquals(GET_SPACES_QUERY, graphQuery.returnQuery());
	}

}
