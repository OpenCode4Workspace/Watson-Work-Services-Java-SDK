package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import org.apache.http.client.methods.HttpPost;
import org.junit.Test;
import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Message.MessageChildren;
import org.opencode4workspace.bo.Person;
import org.opencode4workspace.bo.Person.PersonChildren;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.builders.BaseGraphQLMultiQuery;
import org.opencode4workspace.builders.BaseGraphQLQuery;
import org.opencode4workspace.builders.IGraphQLQuery;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;
import org.opencode4workspace.json.GraphQLRequest;
import org.opencode4workspace.mocks.MockClient;
import org.opencode4workspace.mocks.MockGraphQLEndpoint;

public class HeadersTest {

	@Test
	public void TestBeta() throws WWException {
		MockClient client  = new MockClient(null);
		MockGraphQLEndpoint ep = new MockGraphQLEndpoint(client);
		BaseGraphQLMultiQuery queryObject = new BaseGraphQLMultiQuery("", new ObjectDataSenderBuilder());
		GraphQLRequest request = new GraphQLRequest(queryObject);
		request.setBeta(true);
		ep.setRequest(request);
		HttpPost post = ep.preparePost();
		assertEquals("PUBLIC, BETA", post.getFirstHeader("x-graphql-view").getValue());
	}

}
