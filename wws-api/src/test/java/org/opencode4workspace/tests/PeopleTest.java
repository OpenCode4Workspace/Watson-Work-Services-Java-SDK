package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Profile.PersonFields;
import org.opencode4workspace.builders.PeopleGraphQLQuery;
import org.opencode4workspace.builders.PeopleGraphQLQuery.PeopleAttributes;

public class PeopleTest {
	private final String queryString = "people (id: [\"5d1bf268-c363-4eea-baec-e2dbeaa2fb72\", \"8bf6c84f-961c-43df-836a-85748766912f\"]) {items {displayName}}";
	private final String id1 = "5d1bf268-c363-4eea-baec-e2dbeaa2fb72";
	private final String id2 = "8bf6c84f-961c-43df-836a-85748766912f";

	@Test
	public void PeopleTestQuery() throws WWException {
		PeopleGraphQLQuery query = new PeopleGraphQLQuery();
		List<String> ids = new ArrayList<String>();
		ids.add(id1);
		ids.add(id2);
		query.addAttribute(PeopleAttributes.ID, ids);
		query.setHasItems(true);
		query.addField(PersonFields.DISPLAY_NAME);
		assertEquals(query.getQueryObject().build(), queryString);
	}
}
