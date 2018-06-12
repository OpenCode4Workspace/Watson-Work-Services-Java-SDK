package org.opencode4workspace.webhooks;

/**
 * Interface as a basic for all Webhook events. Type is the only property consistent to all.
 * 
 * @author Paul Withers
 * @since 0.9.0
 *
 */
public interface IWebhookEventObj {

	/**
	 * @return WebhookType enum matching the event passed, e.g. "verification"
	 */
	WebhookType getType();

	/**
	 * @param type WebhookType enum, matching the event passed, e.g. "verification"
	 */
	void setType(WebhookType type);

}