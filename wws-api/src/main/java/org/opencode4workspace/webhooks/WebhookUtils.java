package org.opencode4workspace.webhooks;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.opencode4workspace.json.ResultParser;

/**
 * Utility methods for managing webhooks
 * 
 * @author Paul Withers
 * @since 0.9.0
 *
 */
public class WebhookUtils {

	/**
	 * Verifies that the X-OUTBOUND-TOKEN of the WWS request matches the SHA-256 MAC of the request body when encrypted
	 * with the webhook secret.
	 * 
	 * @param requestBody JSON payload in format {"type":"verification","challenge":"WWS_CHALLENGE"}
	 * @param webhookSecret registered with WWS
	 * @param outboundToken X-OUTBOUND-TOKEN header passed from WWS
	 * @return whether or not the challenge is authentic from WWS
	 * 
	 * @since 0.9.0
	 */
	public static boolean verifyChallenge(String requestBody, String webhookSecret, String outboundToken) {
		boolean retVal = false;
		HmacUtils hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, webhookSecret);
		String checkVal = hmac.hmacHex(requestBody);
		if (StringUtils.equals(checkVal, outboundToken)) {
			retVal = true;
		}
		return retVal;
	}
	
	/**
	 * Builds the JSON string for the verification response, with response property set to the challenge passed into the VerificationEvent
	 * 
	 * @param event VerificationEvent containing the challenge
	 * @return String of format {"response": "d461l765ylkinnihekwozl32263ymr19"}
	 */
	public static String buildVerificationResponseJson(VerificationEventObj event) {
		return "{\"response\": \"" + event.getChallenge() + "\"}";
	}
	
	/**
	 * Parses a webhook payload from WWS and extracts the WebhookType
	 * 
	 * @param requestBody payload from WWS
	 * @return WebhookType passed
	 * @since 0.9.0
	 */
	public static WebhookType getWebhookType(String requestBody) {
		ResultParser<AbstractWebhookEventObj> parser = new ResultParser<AbstractWebhookEventObj>(AbstractWebhookEventObj.class);
		AbstractWebhookEventObj tempEvent = parser.parse(requestBody);
		return tempEvent.getType();
	}
	
	/**
	 * Parses a webhook payload from WWS and returns the rellevant {@linkplain IWebhookEventObj} object, based on the webhook type
	 * 
	 * @param requestBody payload from WWS
	 * @return IWebhookType corresponding for the relevant webhook type
	 * @since 0.9.0
	 */
	public static IWebhookEventObj parseWebhookEventObj(String requestBody) {
		WebhookType type = getWebhookType(requestBody);
		switch (type) {
		case VERIFICATION:
			ResultParser<VerificationEventObj> verifyParser = new ResultParser<VerificationEventObj>(VerificationEventObj.class);
			return verifyParser.parse(requestBody);
		default:
			return null;
		}
	}

}
