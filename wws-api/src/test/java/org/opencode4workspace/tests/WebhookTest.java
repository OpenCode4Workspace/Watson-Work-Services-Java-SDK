package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.opencode4workspace.webhooks.VerificationEventObj;
import org.opencode4workspace.webhooks.WebhookType;
import org.opencode4workspace.webhooks.WebhookUtils;

public class WebhookTest {
	private static final String VERIFICATION_BODY = "{\"type\":\"verification\",\"challenge\":\"d461l765ylkinnihekwozl32263ymr19\"}";
	private static final String SECRET = "87uchvr218d4bchuhhevik7ndlkl8nz4";
	private static final String OUTBOUND_TOKEN = "b79d6705e638953a2d2d2ba5b1c4cc884c7e7fc95aac6d4e3738a4eb35368261";
	private static final String RESPONSE = "{\"response\": \"d461l765ylkinnihekwozl32263ymr19\"}";
	
	@Test
	public void testGetType() {
		assertEquals(WebhookType.VERIFICATION, WebhookUtils.getWebhookType(VERIFICATION_BODY));
	}
	
	@Test
	public void testValidVerification() {
		assertTrue(WebhookUtils.verifyChallenge(VERIFICATION_BODY, SECRET, OUTBOUND_TOKEN));
	}
	
	@Test
	public void testVerificationResponse() {
		assertEquals(RESPONSE, WebhookUtils.buildVerificationResponseJson((VerificationEventObj) WebhookUtils.parseWebhookEventObj(VERIFICATION_BODY)));
	}

}
