package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Person;
import org.opencode4workspace.bo.Person.PersonChildren;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.builders.BasicCreatedByUpdatedByDataSenderBuilder;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;
import org.opencode4workspace.graphql.BasicPaginationEnum;

public class PersonTest {
	private static final String PEOPLE_QUERY = "people (first: 10) {items {displayName email extId created updated createdBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email}}}";

	

	@Test
	public void peopleQueryTest() throws WWException {
		ObjectDataSenderBuilder personNames = new ObjectDataSenderBuilder(Person.PEOPLE_QUERY_OBJECT_NAME, true);
		personNames.addAttribute(BasicPaginationEnum.FIRST, 10)
				.addField(PersonFields.DISPLAY_NAME)
				.addField(PersonFields.EMAIL)
				.addField(PersonFields.EXT_ID)
				.addField(PersonFields.CREATED)
				.addField(PersonFields.UPDATED)
				.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(PersonChildren.CREATED_BY))
				.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(PersonChildren.UPDATED_BY));
		assertEquals(PEOPLE_QUERY, personNames.build());
	}



}
