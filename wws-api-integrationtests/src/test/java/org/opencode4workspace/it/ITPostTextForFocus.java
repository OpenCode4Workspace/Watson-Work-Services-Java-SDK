package org.opencode4workspace.it;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.opencode4workspace.WWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Focus;
import org.opencode4workspace.bo.Focus.Lens;
import org.opencode4workspace.endpoints.WWAuthenticationEndpoint;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ITPostTextForFocus {
	private static String TEXT = "Hello, I think we need to reconsider. Let's add a meeting on Tuesday 13th March. Frank, what do you think?";

	@Test(enabled = true)
	@Parameters({ "appId", "appSecret" })
	public void postTestTextForFocus(String appId, String appSecret) throws UnsupportedEncodingException, WWException {
		WWClient client = WWClient.buildClientApplicationAccess(appId, appSecret, new WWAuthenticationEndpoint());
		assert !client.isAuthenticated();
		client.authenticate();
		assert client.isAuthenticated();

		List<Focus> focuses = client.postTextForFocusAnalysis(TEXT);
		assert (focuses.size() == 3);
		assert (focuses.get(0).getLens().equals(Lens.ACTION_REQUEST));
		assert (focuses.get(2).getLens().equals(Lens.QUESTION));
		assert (38 == focuses.get(0).getEnd());
		assert (1.0 == focuses.get(1).getConfidence());
	}
}
