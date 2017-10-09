package org.opencode4workspace.it;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.FileResponse;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ITPostFileToSpace {
	
	@Test(enabled = true)
	@Parameters({ "appId", "appSecret", "spaceId", "imagePath" })
	public void postTestMessageToSpace(String appId, String appSecret, String spaceId, String imagePath)
			throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		
		File photo = new File(imagePath);
		FileResponse response = client.postFileToSpace(photo, spaceId);
		assert (!"".equals(response.getName()));
		assert (appId.equals(response.getCreatedBy()));
		response = client.postFileToSpace(photo, spaceId, "200x200");
		assert (!"".equals(response.getName()));
		assert (appId.equals(response.getCreatedBy()));
	}

}
