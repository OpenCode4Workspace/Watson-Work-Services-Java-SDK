package org.opencode4workspace.it;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Message;
import org.opencode4workspace.bo.Person;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.builders.BasicCreatedByUpdatedByDataSenderBuilder;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;
import org.opencode4workspace.builders.PeopleGraphQLQuery;
import org.opencode4workspace.builders.SpacesGraphQLQuery;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.opencode4workspace.endpoints.WWGraphQLEndpoint;
import org.opencode4workspace.graphql.BasicPaginationEnum;
import org.opencode4workspace.json.GraphQLRequest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ITgraphQL {

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret" })
	public void testGetSpacesAsApp(String appId, String appSecret) throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();

		List<? extends Space> spaces = client.getSpaces();
		assert (spaces.size() > 0);
	}

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret" })
	public void testGetSpacesBasicAsApp(String appId, String appSecret)
			throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();

		ObjectDataSenderBuilder spaces = new ObjectDataSenderBuilder(Space.SPACES_QUERY_OBJECT_NAME, true);
		spaces.addAttribute(BasicPaginationEnum.FIRST, 100);
		spaces.addPageInfo();
		spaces.addField(SpaceFields.ID);
		spaces.addField(SpaceFields.TITLE);
		spaces.addField(SpaceFields.DESCRIPTION);
		spaces.addField(SpaceFields.UPDATED);
		spaces.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(SpaceChildren.UPDATED_BY));
		spaces.addField(SpaceFields.CREATED);
		spaces.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(SpaceChildren.CREATED_BY));
		ObjectDataSenderBuilder members = new ObjectDataSenderBuilder(SpaceChildren.MEMBERS.getLabel(), true);
		members.addAttribute(BasicPaginationEnum.FIRST, 100);
		members.addField(PersonFields.ID);
		members.addField(PersonFields.PHOTO_URL);
		members.addField(PersonFields.EMAIL);
		members.addField(PersonFields.DISPLAY_NAME);
		spaces.addChild(members);
		List<? extends Space> spacesResult = client.getSpacesWithQuery(new SpacesGraphQLQuery(spaces));
		assert (spacesResult.size() > 0);
	}

	@Test(enabled = false)
	@Parameters({ "appId", "appSecret", "myDisplayName" })
	public void testGetMe(String appId, String appSecret, String myDisplayName)
			throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		Person me = client.getMe();
		assert (myDisplayName.equals(me.getDisplayName()));
	}

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret", "profileId", "myDisplayName" })
	public void testGetPerson(String appId, String appSecret, String profileId, String myDisplayName)
			throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		Person person = client.getPersonById(profileId);
		assert (myDisplayName.equals(person.getDisplayName()));
	}

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret", "conversationId" })
	public void testgetConversationAsApp(String appId, String appSecret, String conversationId)
			throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		Conversation conversation = ep.getConversation(conversationId);
		assert (conversation != null);
		assert (conversation.getMessages().size() > 0);
	}

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret" })
	public void testGetSpaceMembers(String appId, String appSecret) throws WWException, UnsupportedEncodingException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);

		SpacesGraphQLQuery queryObject = SpacesGraphQLQuery.buildStandardGetSpacesQuery();
		ep.setRequest(new GraphQLRequest(queryObject));
		ep.executeRequest();
		List<? extends Space> spacesResult = ep.getResultContainer().getData().getSpaces().getItems();
		assert (spacesResult.size() > 0);
		String spaceId = spacesResult.get(0).getId();
		assert (null != spaceId);
		List<Person> members = ep.getSpaceMembers(spaceId);
		assert (members.size() > 0);
		Space space = ep.getSpaceById(spaceId);
		assert (space.getMembers().size() > 0);
	}

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret", "profileId", "myDisplayName", "myAppName" })
	public void testGetPeople(String appId, String appSecret, String profileId, String myDisplayName, String myAppName)
			throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);

		ArrayList<String> ids = new ArrayList<String>();
		ids.add(profileId);
		ids.add(appId);
		PeopleGraphQLQuery queryObject = PeopleGraphQLQuery.buildPersonQueryById(ids);

		ep.setRequest(new GraphQLRequest(queryObject));
		ep.executeRequest();
		List<Person> peopleResult = ep.getResultContainer().getData().getPeople().getItems();

		assert (peopleResult.size() == 2);
		assert (myDisplayName.equals(peopleResult.get(0).getDisplayName()));
		assert (myAppName.equals(peopleResult.get(1).getDisplayName()));
	}

	@Test(enabled = false)
	@Parameters({ "appId", "appSecret" })
	public void testGetPeopleFirstTen(String appId, String appSecret) throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder(Person.PEOPLE_QUERY_OBJECT_NAME, true);
		query.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 10);
		query.addField(PersonFields.DISPLAY_NAME);

		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		ep.setRequest(new GraphQLRequest(new PeopleGraphQLQuery(query)));
		ep.executeRequest();
		System.out.println(ep.getResultContent());
		// Following line throws error
		List<Person> peopleResult = ep.getResultContainer().getData().getPeople().getItems();

		assert (peopleResult.size() == 2);
	}

	@Test(enabled = false)
	@Parameters({ "appId", "appSecret", "profileId", "myDisplayName", "myAppName" })
	public void testGetPeopleByName(String appId, String appSecret, String profileId, String myDisplayName,
			String myAppName) throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);

		PeopleGraphQLQuery queryObject = PeopleGraphQLQuery.buildPersonQueryByName("Paul");

		ep.setRequest(new GraphQLRequest(queryObject));
		System.out.println(queryObject.returnQuery());
		ep.executeRequest();
		List<Person> peopleResult = ep.getResultContainer().getData().getPeople().getItems();

		assert (peopleResult.size() == 1);
		assert (appId.equals(peopleResult.get(0).getId()));
	}

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret", "messageId", "myDisplayName" })
	public void testGetMessage(String appId, String appSecret, String messageId, String myDisplayName)
			throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();

		Message message = client.getMessageById(messageId);
		assert (!"".equals(message.getContentType()));
		assert (myDisplayName.equals(message.getCreatedBy().getDisplayName()));
	}

}
