package org.opencode4workspace.it;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.FileResponse;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ITPostFileToSpace {
	
	@Test(enabled = true)
	@Parameters({ "appId", "appSecret", "spaceId" })
	public void postTestMessageToSpace(String appId, String appSecret, String spaceId, String imagePath)
			throws UnsupportedEncodingException, WWException, MalformedURLException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();
		
		URL url = new URL("https://openntf.org/main.nsf/openntf_222222_bg.jpg");
		File photo = new File(url.getFile());
		FileResponse response = client.postFileToSpace(photo, spaceId);
		assert (!"".equals(response.getName()));
		assert (appId.equals(response.getCreatedBy()));
		response = client.postFileToSpace(photo, spaceId, "50x200");
		assert (!"".equals(response.getName()));
		assert (appId.equals(response.getCreatedBy()));
	}

}
