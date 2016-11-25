package org.opencode4workspace.it;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Profile;
import org.opencode4workspace.bo.Profile.PersonFields;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.builders.BaseGraphQLQuery;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;
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
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		List<? extends Space> spaces = ep.getSpaces();
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
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);

		ObjectDataSenderBuilder createdBy = new ObjectDataSenderBuilder(SpaceChildren.CREATED_BY.getLabel());
		createdBy.addField(PersonFields.ID);
		createdBy.addField(PersonFields.DISPLAY_NAME);
		createdBy.addField(PersonFields.PHOTO_URL);
		createdBy.addField(PersonFields.EMAIL);

		// Basic updatedBy ObjectDataBringer - same label for all
		ObjectDataSenderBuilder updatedBy = new ObjectDataSenderBuilder(SpaceChildren.UPDATED_BY.getLabel());
		updatedBy.addField(PersonFields.ID);
		updatedBy.addField(PersonFields.DISPLAY_NAME);
		updatedBy.addField(PersonFields.PHOTO_URL);
		updatedBy.addField(PersonFields.EMAIL);

		ObjectDataSenderBuilder spaces = new ObjectDataSenderBuilder(Space.SPACE_QUERY_OBJECT_NAME, true);
		spaces.addAttribute(BasicPaginationEnum.FIRST, 100);
		spaces.addPageInfo();
		spaces.addField(SpaceFields.ID);
		spaces.addField(SpaceFields.TITLE);
		spaces.addField(SpaceFields.DESCRIPTION);
		spaces.addField(SpaceFields.UPDATED);
		spaces.addChild(updatedBy);
		spaces.addField(SpaceFields.CREATED);
		spaces.addChild(createdBy);
		ObjectDataSenderBuilder members = new ObjectDataSenderBuilder(SpaceChildren.MEMBERS.getLabel(), true);
		members.addAttribute(BasicPaginationEnum.FIRST, 100);
		members.addField(PersonFields.ID);
		members.addField(PersonFields.PHOTO_URL);
		members.addField(PersonFields.EMAIL);
		members.addField(PersonFields.DISPLAY_NAME);
		spaces.addChild(members);

		BaseGraphQLQuery queryObject = new BaseGraphQLQuery("getSpaces", spaces);
		ep.setRequest(new GraphQLRequest(queryObject));
		ep.executeRequest();
		List<? extends Space> spacesResult = ep.getResultContainer().getData().getSpaces().getItems();
		System.out.println("Total spaces found - " + spacesResult.size());
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
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		Profile me = ep.getMe();
		assert (myDisplayName.equals(me.getDisplayName()));
	}

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret", "profileId", "myDisplayName" })
	public void testGetProfile(String appId, String appSecret, String profileId, String myDisplayName)
			throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client);
		Profile profile = ep.getProfile(profileId);
		assert (myDisplayName.equals(profile.getDisplayName()));
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

}
