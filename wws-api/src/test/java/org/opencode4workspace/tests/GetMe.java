package org.opencode4workspace.tests;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.Test;
import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.FileResponse;
import org.opencode4workspace.bo.Person;
import org.opencode4workspace.bo.PhotoResponse;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.builders.SpaceUpdateGraphQLMutation.UpdateSpaceMemberOperation;
import org.opencode4workspace.endpoints.FilePostToSpaceEndpoint;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.opencode4workspace.endpoints.WWGraphQLEndpoint;
import org.opencode4workspace.graphql.ErrorContainer;
import org.opencode4workspace.graphql.GraphResultContainer;
import org.opencode4workspace.graphql.UpdateSpaceContainer;

public class GetMe {
	// URL = https://api.watsonwork.ibm.com/oauth/authorize?client_id=5d1bf268-c363-4eea-baec-e2dbeaa2fb72&state=3322&scope=ibmid&redirect_uri=https://openntf.org&response_type=code
	private static String userToken = "atT1Z6";
	
	@Test
	public void getMe() throws WWException, UnsupportedEncodingException {
		WWClient client = WWClient.buildClientUserAccess(userToken, "5d1bf268-c363-4eea-baec-e2dbeaa2fb72", "o40ej0nad66vk55tgha21l08r9fam79", new WWAuthenticationEndpoint(), "https://openntf.org");
		client.authenticate();
		Person me = client.getMe();
		assert("Paul Withers".equals(me.getDisplayName()));
		File photo = new File("C:/Users/withersp/Dropbox/PaulWConnect13.jpg");
		PhotoResponse response = client.postPhoto(photo);
		assert(response.isSuccess());

		FileResponse fileResponse1 = client.postFileToSpace(photo, "5811aeb9e4b0052629e89bb2");
		assert ("PaulWConnect13.jpg".equals(fileResponse1.getName()));
		FileResponse fileResponse2 = client.postFileToSpace(photo, "5811aeb9e4b0052629e89bb2", "200x200");
		assert ("PaulWConnect13.jpg".equals(fileResponse2.getName()));
		//Space newSpace = client.createSpace("Hello");

		WWClient client2 = WWClient.buildClientApplicationAccess("5d1bf268-c363-4eea-baec-e2dbeaa2fb72", "o40ej0nad66vk55tgha21l08r9fam79", new WWAuthenticationEndpoint());
		client2.authenticate();
		WWGraphQLEndpoint ep = new WWGraphQLEndpoint(client2);
		try {
			ArrayList<String> members = new ArrayList<String>();
			members.add("de81bc5d-70fa-4bdb-bab7-29f4df10ddf3");
			UpdateSpaceContainer space = ep.updateSpaceMembersAndTitle("59db95e9e4b0e8babadb97c4", "Hello New World", members, UpdateSpaceMemberOperation.ADD);
			assert "Hello New World".equals(space.getSpace().getTitle());
			boolean success = client2.deleteSpace("59db95e9e4b0e8babadb97c4");
			assert success;
		} catch (Exception e) {
			GraphResultContainer results = ep.getResultContainer();
			assert (null != results.getErrors());
			ErrorContainer errors = results.getErrors().get(0);
			assert "403 Forbidden".equals(errors.getMessage());
		}
	}

}
