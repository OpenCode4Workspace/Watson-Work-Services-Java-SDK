package org.opencode4workspace.it;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.opencode4workspace.IWWClient;
import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Annotation.AnnotationType;
import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Conversation.ConversationFields;
import org.opencode4workspace.bo.Message;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.bo.Person;
import org.opencode4workspace.bo.Person.PersonChildren;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.Person.PresenceStatus;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.bo.WWQueryResponseObjectTypes;
import org.opencode4workspace.builders.BaseGraphQLMultiQuery;
import org.opencode4workspace.builders.BasicCreatedByUpdatedByDataSenderBuilder;
import org.opencode4workspace.builders.ConversationGraphQLQuery;
import org.opencode4workspace.builders.ConversationGraphQLQuery.ConversationAttributes;
import org.opencode4workspace.builders.ConversationGraphQLQuery.ConversationMessageAttributes;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;
import org.opencode4workspace.builders.PeopleGraphQLQuery;
import org.opencode4workspace.builders.PersonGraphQLQuery;
import org.opencode4workspace.builders.SpacesGraphQLQuery;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.opencode4workspace.endpoints.WWGraphQLEndpoint;
import org.opencode4workspace.graphql.BasicPaginationEnum;
import org.opencode4workspace.graphql.DataContainer;
import org.opencode4workspace.graphql.ErrorContainer;
import org.opencode4workspace.graphql.GraphResultContainer;
import org.opencode4workspace.graphql.SpaceWrapper;
import org.opencode4workspace.json.GraphQLRequest;
import org.opencode4workspace.json.ResultParser;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.remote.strprotocol.BaseMessageSender;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
		members.addField(PersonFields.PRESENCE);
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
		assert (null != person.getPresence());
	}

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret", "conversationId" })
	public void testGetConversationAsApp(String appId, String appSecret, String conversationId)
			throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();

		Conversation conversation = client.getConversationById(conversationId);
		assert (conversation != null);
		assert (conversation.getMessages().size() > 0);
	}

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret", "conversationId" })
	public void testGetConversationGenericMessagesOnly(String appId, String appSecret, String conversationId)
			throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();

		ObjectDataSenderBuilder messages = new ObjectDataSenderBuilder(ConversationChildren.MESSAGES.getLabel(), true)
				.addAttribute(ConversationMessageAttributes.ANNOTATION_TYPE, AnnotationType.GENERIC.getLabel())
				.addField(MessageFields.CONTENT);
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder(Conversation.CONVERSATION_QUERY_OBJECT_NAME)
				.addAttribute(ConversationAttributes.ID, conversationId)
				.addField(ConversationFields.ID)
				.addChild(messages);
		Conversation conversation = client.getConversationWithQuery(new ConversationGraphQLQuery(query));
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
		List<Person> members = client.getSpaceMembersById(spaceId);
		assert (members.size() > 0);
		Space space = client.getSpaceById(spaceId);
		assert (space.getMembers().size() > 0);
	}

	@Test(enabled = false)
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

		List<Person> peopleResult = client.getPeople(ids);

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
		List<Person> peopleResult = client.getPeopleByName("Paul");

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

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret", "profileId", "myDisplayName" })
	public void personTestWithId(String appId, String appSecret, String personId, String myDisplayName)
			throws WWException, UnsupportedEncodingException {
		IWWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
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

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret", "profileId", "myDisplayName" })
	public void peopleTest(String appId, String appSecret) throws WWException, UnsupportedEncodingException {
		IWWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
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
		assert "500 Internal Server Error".equals(errors.getMessage());
		assert "people".equals(errors.getField().get("name"));
		assert "PersonCollection".equals(errors.getField().get("type"));
	}

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret" })
	public void createSpace(String appId, String appSecret) throws WWException, UnsupportedEncodingException {
		IWWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		client.authenticate();
		ArrayList<String> members = new ArrayList<String>();
		members.add("8bf6c84f-961c-43df-836a-85748766912f");
		members.add("5d1bf268-c363-4eea-baec-e2dbeaa2fb72");
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		try {
			// This is forbidden for App access
			Space space = ep.createSpace("Hello World", members);
		} catch (Exception e) {
			GraphResultContainer results = ep.getResultContainer();
			assert (null != results.getErrors());
			ErrorContainer errors = results.getErrors().get(0);
			assert "403 Forbidden".equals(errors.getMessage());
		}
	}

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret" })
	public void updateSpace(String appId, String appSecret) throws WWException, UnsupportedEncodingException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		client.authenticate();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		try {
			Space space = ep.updateSpaceTitle("589390cfe4b0f86a34bbf4ed", "Hello New World");
			assert "Hello New World".equals(space.getTitle());
		} catch (Exception e) {
			GraphResultContainer results = ep.getResultContainer();
			assert (null != results.getErrors());
			ErrorContainer errors = results.getErrors().get(0);
			assert "403 Forbidden".equals(errors.getMessage());
		}
	}
	
	@Test(enabled = true)
	@Parameters({"appId", "appSecret", "spaceId", "spaceName", "space2Id", "space2Name"})
	public void getTwoSpace(String appId, String appSecret, String spaceId, String spaceName, String space2Id, String space2Name) throws WWException, UnsupportedEncodingException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		client.authenticate();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
		query.setReturnType(WWQueryResponseObjectTypes.SPACE);
		query.setObjectName("space1");
		query.addAttribute(SpaceFields.ID, spaceId);
		query.addField(SpaceFields.TITLE);
		query.addField(SpaceFields.DESCRIPTION);
		query.addField(SpaceFields.ID);
		query.addField(SpaceFields.CREATED);
		query.addField(SpaceFields.UPDATED);
		BaseGraphQLMultiQuery twoSpaceQuery = new BaseGraphQLMultiQuery("getTwoSpaces", query);
		ObjectDataSenderBuilder query2 = new ObjectDataSenderBuilder();
		query2.setObjectName("space2");
		query2.setReturnType(WWQueryResponseObjectTypes.SPACE);
		query2.addAttribute(SpaceFields.ID, space2Id);
		query2.addField(SpaceFields.TITLE);
		query2.addField(SpaceFields.DESCRIPTION);
		query2.addField(SpaceFields.ID);
		query2.addField(SpaceFields.CREATED);
		query2.addField(SpaceFields.UPDATED);
		twoSpaceQuery.addQueryObject(query2);
		ep.setRequest(new GraphQLRequest(twoSpaceQuery));
		ep.executeRequest();
		
		DataContainer data = ep.getResultContainer().getData();
		assert (null != data);
		SpaceWrapper space1 = (SpaceWrapper) data.getAliasedChildren().get("space1");
		assert(null != space1);
		assert (spaceName.equals(space1.getTitle()));
		SpaceWrapper space2 = (SpaceWrapper) data.getAliasedChildren().get("space2");
		assert(null != space2);
		assert (space2Name.equals(space2.getTitle()));
	}
}
