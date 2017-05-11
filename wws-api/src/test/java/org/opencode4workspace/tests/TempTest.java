package org.opencode4workspace.tests;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;
import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.WWQueryResponseObjectTypes;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.builders.BaseGraphQLMultiQuery;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.opencode4workspace.endpoints.WWGraphQLEndpoint;
import org.opencode4workspace.graphql.DataContainer;
import org.opencode4workspace.graphql.SpaceWrapper;
import org.opencode4workspace.json.GraphQLRequest;

public class TempTest {
	@Test
	public void getTwoSpaces() throws WWException, UnsupportedEncodingException {
		WWClient client = WWClient.buildClientApplicationAccess("5d1bf268-c363-4eea-baec-e2dbeaa2fb72", "o40ej0nad66vk55tgha21l08r9fam79", new WWAuthenticationEndpoint());
		client.authenticate();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
		query.setReturnType(WWQueryResponseObjectTypes.SPACE);
		query.setObjectName("space1");
		query.addAttribute(SpaceFields.ID, "5811aeb9e4b0052629e89bb2");
		query.addField(SpaceFields.TITLE);
		query.addField(SpaceFields.DESCRIPTION);
		query.addField(SpaceFields.ID);
		query.addField(SpaceFields.CREATED);
		query.addField(SpaceFields.UPDATED);
		BaseGraphQLMultiQuery twoSpaceQuery = new BaseGraphQLMultiQuery("getTwoSpaces", query);
		ObjectDataSenderBuilder query2 = new ObjectDataSenderBuilder();
		query2.setObjectName("space2");
		query2.setReturnType(WWQueryResponseObjectTypes.SPACE);
		query2.addAttribute(SpaceFields.ID, "58ab8375e4b052591e45e7ca");
		query2.addField(SpaceFields.TITLE);
		query2.addField(SpaceFields.DESCRIPTION);
		query2.addField(SpaceFields.ID);
		query2.addField(SpaceFields.CREATED);
		query2.addField(SpaceFields.UPDATED);
		twoSpaceQuery.addQueryObject(query2);
		ep.setRequest(new GraphQLRequest(twoSpaceQuery));
		ep.executeRequest();
		
		DataContainer data = ep.getResultContainer().getData();
		SpaceWrapper space1 = (SpaceWrapper) data.getAliasedChildren().get("space1");
		assertEquals("Paul Withers Refs", space1.getTitle());
		SpaceWrapper space2 = (SpaceWrapper) data.getAliasedChildren().get("space2");
		assertEquals("Watson WhereSpace", space2.getTitle());
	}
}
