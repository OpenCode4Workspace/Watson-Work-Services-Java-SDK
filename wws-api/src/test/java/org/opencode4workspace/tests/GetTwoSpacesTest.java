package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.Test;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.builders.BaseGraphQLMultiQuery;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;
import org.opencode4workspace.builders.SpaceCreateGraphQLMutation;
import org.opencode4workspace.builders.SpaceGraphQLQuery;
import org.opencode4workspace.graphql.DataContainer;
import org.opencode4workspace.graphql.SpaceWrapper;
import org.opencode4workspace.json.ResultParser;
import org.opencode4workspace.mocks.MockClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GetTwoSpacesTest {
	private static final String GET_TWO_SPACES_QUERY = "query getTwoSpaces {space1:space (id: \"5811aeb9e4b0052629e89bb2\") {title description id created updated}space2:space (id: \"58ab8375e4b052591e45e7ca\") {title description id created updated}}";
	private static final String GET_TWO_SPACES_RESPONSE = "{\"data\": {\"space1\": {\"title\": \"Paul Withers Refs\",\"description\": null,\"id\": \"5811aeb9e4b0052629e89bb2\",\"created\": \"2016-10-27T07:37:29.224+0000\",\"updated\": \"2017-05-03T08:34:58.482+0000\"},\"space2\": {\"title\": \"Watson WhereSpace\",\"description\": null,\"id\": \"58ab8375e4b052591e45e7ca\",\"created\": \"2017-02-21T00:01:57.906+0000\",\"updated\": \"2017-05-03T11:44:51.645+0000\"}}}";
	BaseGraphQLMultiQuery twoSpaceQuery;
	
	@Test
	public void checkGetTwoSpacesOutput() throws WWException, UnsupportedEncodingException {
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
		query.setReturnType(Space.class);
		query.setObjectName("space1");
		query.addAttribute(SpaceFields.ID, "5811aeb9e4b0052629e89bb2");
		query.addField(SpaceFields.TITLE);
		query.addField(SpaceFields.DESCRIPTION);
		query.addField(SpaceFields.ID);
		query.addField(SpaceFields.CREATED);
		query.addField(SpaceFields.UPDATED);
		twoSpaceQuery = new BaseGraphQLMultiQuery("getTwoSpaces", query);
		ObjectDataSenderBuilder query2 = new ObjectDataSenderBuilder();
		query2.setObjectName("space2");
		query2.setReturnType(Space.class);
		query2.addAttribute(SpaceFields.ID, "58ab8375e4b052591e45e7ca");
		query2.addField(SpaceFields.TITLE);
		query2.addField(SpaceFields.DESCRIPTION);
		query2.addField(SpaceFields.ID);
		query2.addField(SpaceFields.CREATED);
		query2.addField(SpaceFields.UPDATED);
		twoSpaceQuery.addQueryObject(query2);
		assertEquals(GET_TWO_SPACES_QUERY, twoSpaceQuery.returnQuery());
	}

	@Test
	public void checkGetTwoSpacesResponse() throws WWException, UnsupportedEncodingException {
		MockClient client = new MockClient(GET_TWO_SPACES_RESPONSE);
		String result = client.getEndpoint().getResultContent();

		Gson gson = new Gson();
		JsonObject data = gson.fromJson(result, JsonObject.class);
		JsonElement dataResp = data.get("data").getAsJsonObject();
		JsonElement obj = (JsonObject) dataResp.getAsJsonObject().get("space1");
		assertNotNull(obj);
		SpaceWrapper space1 = new ResultParser<SpaceWrapper>(SpaceWrapper.class).parse(obj.toString());
		assertEquals("Paul Withers Refs", space1.getTitle());
		obj = (JsonObject) dataResp.getAsJsonObject().get("space2");
		assertNotNull(obj);
		SpaceWrapper space2 = new ResultParser<SpaceWrapper>(SpaceWrapper.class).parse(obj.toString());
		assertEquals("Watson WhereSpace", space2.getTitle());
	}

}
