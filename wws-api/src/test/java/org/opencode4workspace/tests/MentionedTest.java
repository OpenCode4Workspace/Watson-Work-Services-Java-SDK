package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Mentioned;
import org.opencode4workspace.bo.Mentioned.MentionedChildren;
import org.opencode4workspace.bo.Mentioned.MentionedFields;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.builders.MentionedGraphQLQuery;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;
import org.opencode4workspace.graphql.BasicPaginationEnum;
import org.opencode4workspace.graphql.GraphResultContainer;
import org.opencode4workspace.graphql.MentionedContainer;
import org.opencode4workspace.json.ResultParser;
import org.opencode4workspace.mocks.MockClient;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MentionedTest {
	private static final String MENTIONED_QUERY = "mentioned (first: 10) {pageInfo {startCursor endCursor hasNextPage hasPreviousPage} items {updated updatedBy message {contentType content created updated annotations id} space {id title description} person {id photoUrl email displayName}}}";
	private static final String MENTIONED_RESPONSE = "{\"data\": {\"mentioned\": {\"pageInfo\": {\"startCursor\": \"MTUxOTQ2OTQ3ODU5MA==\",\"endCursor\": \"MTUxOTI5ODY5NDAyNA==\",\"hasNextPage\": true,\"hasPreviousPage\": false},\"items\": [{\"updated\": 1519469478590,\"updatedBy\": \"ae64ce24-bbe4-4fb3-9d52-0dae2e8fa397\",\"message\": {\"content\": \"<@space> I promised to try to do an org chart now that the dust is settling.  Here's a kind of summarized version.  There are many more people on the chart, especially in engineering but I don't know the right structure for these.  _There are lots of people not on this, but this should be representative of the basic structure_.\",\"created\": \"2018-02-24T10:51:18.590+0000\"},\"space\": {\"title\": \"ICS Champions Chat 🌎\"},\"person\": {\"displayName\": \"ALAN Hamilton\",\"id\": \"ae64ce24-bbe4-4fb3-9d52-0dae2e8fa397\"}},{\"updated\": 1519298694024,\"updatedBy\": \"d0ece959-ee7a-4aca-b916-867402faf8db\",\"message\": {\"content\": \"<@8bf6c84f-961c-43df-836a-85748766912f|Paul Withers> I can't see this happen.. for many reasons.\",\"created\": \"2018-02-22T11:24:54.024+0000\"},\"space\": {\"title\": \"ICS Champions Chat 🌎\"},\"person\": {\"displayName\": \"Daniele Vistalli\",\"id\": \"d0ece959-ee7a-4aca-b916-867402faf8db\"}}]}}}";
	
	@Test
	public void mentionedQueryTest() throws WWException {
		MentionedGraphQLQuery query = MentionedGraphQLQuery.buildFullMentionedGraphQuery();
		assertEquals(MENTIONED_QUERY, query.getQueryObject().build());
	}
	
	@Test
	public void parseMentionedResponseTest() throws WWException {
		GraphResultContainer result = new ResultParser<GraphResultContainer>(GraphResultContainer.class).parse(MENTIONED_RESPONSE);
		MentionedContainer container = result.getData().getMentioned();
		List<Mentioned> mentioned = container.getItems();
		assertEquals(2, mentioned.size());
	}
	
	public static MentionedGraphQLQuery loadQuery() throws WWException {
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder(Mentioned.MENTIONED_QUERY_OBJECT_NAME, true);
		query.addAttribute(BasicPaginationEnum.FIRST, 10);
		query.setObjectName(Mentioned.MENTIONED_QUERY_OBJECT_NAME);
		query.addPageInfo();
		query.addField(MentionedFields.UPDATED);
		query.addField(MentionedFields.UPDATED_BY);
		ObjectDataSenderBuilder message = new ObjectDataSenderBuilder(MentionedChildren.MESSAGE.getLabel());
		message.addField(MessageFields.CONTENT_TYPE);
		message.addField(MessageFields.CONTENT);
		message.addField(MessageFields.CREATED);
		message.addField(MessageFields.UPDATED);
		message.addField(MessageFields.ANNOTATIONS);
		message.addField(MessageFields.ID);
		query.addChild(message);
		ObjectDataSenderBuilder space = new ObjectDataSenderBuilder(MentionedChildren.SPACE.getLabel());
		space.addField(SpaceFields.ID);
		space.addField(SpaceFields.TITLE);
		space.addField(SpaceFields.DESCRIPTION);
		query.addChild(space);
		ObjectDataSenderBuilder person = new ObjectDataSenderBuilder(MentionedChildren.PERSON.getLabel());
		person.addField(PersonFields.ID);
		person.addField(PersonFields.PHOTO_URL);
		person.addField(PersonFields.EMAIL);
		person.addField(PersonFields.DISPLAY_NAME);
		query.addChild(person);
		return new MentionedGraphQLQuery(query);
	}
}
