package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Message.MessageChildren;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.builders.MessageGraphQLQuery;
import org.opencode4workspace.builders.MessageGraphQLQuery.MessageAttributes;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;
import org.opencode4workspace.builders.PeopleGraphQLQuery;
import org.opencode4workspace.builders.PeopleGraphQLQuery.PeopleAttributes;

public class PeopleAndMessageTest {
	private final String queryString = "people (id: [\"5d1bf268-c363-4eea-baec-e2dbeaa2fb72\", \"8bf6c84f-961c-43df-836a-85748766912f\"]) {items {displayName}}";
	private final String id1 = "5d1bf268-c363-4eea-baec-e2dbeaa2fb72";
	private final String id2 = "8bf6c84f-961c-43df-836a-85748766912f";
	private final String messageQueryString = "message (id: \"585be595e4b015e29f2b6072\") {annotations content contentType createdBy {displayName extId id}}";
	private final String id3 = "585be595e4b015e29f2b6072";

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

	@Test
	public void MessageTestQuery() throws WWException {
		MessageGraphQLQuery query = new MessageGraphQLQuery();
		query.addAttribute(MessageAttributes.ID, id3);
		query.addField(MessageFields.ANNOTATIONS);
		query.addField(MessageFields.CONTENT);
		query.addField(MessageFields.CONTENT_TYPE);
		ObjectDataSenderBuilder createdBy = new ObjectDataSenderBuilder(MessageChildren.CREATED_BY.getLabel());
		createdBy.addField(PersonFields.DISPLAY_NAME);
		createdBy.addField(PersonFields.EXT_ID);
		createdBy.addField(PersonFields.ID);
		query.addChild(createdBy);
		assertEquals(query.getQueryObject().build(), messageQueryString);
	}
}
