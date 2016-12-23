package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Person;
import org.opencode4workspace.bo.Person.PersonChildren;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.builders.BasicCreatedByUpdatedByDataSenderBuilder;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;
import org.opencode4workspace.builders.PersonGraphQLQuery;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.opencode4workspace.endpoints.WWGraphQLEndpoint;
import org.opencode4workspace.graphql.BasicPaginationEnum;
import org.opencode4workspace.graphql.DataContainer;
import org.opencode4workspace.graphql.ErrorContainer;
import org.opencode4workspace.graphql.GraphResultContainer;
import org.opencode4workspace.json.GraphQLRequest;

public class PersonTest {
	private final String appId = "5d1bf268-c363-4eea-baec-e2dbeaa2fb72";
	private final String appSecret = "o40ej0nad66vk55tgha21l08r9fam79";
	private final String myDisplayName = "Paul Withers";
	private final String personId = "8bf6c84f-961c-43df-836a-85748766912f";
	private static final String PEOPLE_QUERY = "people (first: 10) {items {displayName email extId created updated createdBy {id displayName photoUrl email} updatedBy {id displayName photoUrl email}}}";

	@Test
	public void personTestWithId() throws WWException, UnsupportedEncodingException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		PersonGraphQLQuery queryObject = PersonGraphQLQuery.buildPersonQueryById(personId);
		ep.setRequest(new GraphQLRequest(queryObject));
		ep.executeRequest();
		assert (null == ep.getResultContainer().getErrors());
		DataContainer container = ep.getResultContainer().getData();
		assert (myDisplayName.equals(container.getPerson().getDisplayName()));
	}

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

	@Test
	public void peopleTest() throws WWException, UnsupportedEncodingException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		client.authenticate();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		ObjectDataSenderBuilder personNames = new ObjectDataSenderBuilder(Person.PEOPLE_QUERY_OBJECT_NAME, true);
		personNames.addAttribute(BasicPaginationEnum.FIRST, 10)
				.addField(PersonFields.DISPLAY_NAME)
				.addField(PersonFields.EMAIL)
				.addField(PersonFields.EXT_ID)
				.addField(PersonFields.CREATED)
				.addField(PersonFields.UPDATED)
				.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(PersonChildren.CREATED_BY))
				.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(PersonChildren.UPDATED_BY));
		ep.setRequest(new GraphQLRequest(personNames, "getProfileNames"));
		ep.executeRequest();
		GraphResultContainer results = ep.getResultContainer();
		assert (null != results.getErrors());
		ErrorContainer errors = results.getErrors().get(0);
		assertEquals("500 Internal Server Error", errors.getMessage());
		assertEquals("people", errors.getField().get("name"));
		assertEquals("PersonCollection", errors.getField().get("type"));
	}

}