package org.opencode4workspace.webhooks;

import java.io.Serializable;

/**
 * Abstract webhook event object. The actual one used will depend on the WebhookType passed. The utility method will
 * extract the payload to the relevant subclass.
 * 
 * @author Paul Withers
 * @since 0.9.0
 *
 */
public class AbstractWebhookEventObj implements IWebhookEventObj, Serializable {

	private static final long serialVersionUID = 1L;
	private WebhookType type;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.webhooks.IWebhookType#getType()
	 */
	@Override
	public WebhookType getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencode4workspace.webhooks.IWebhookType#setType(org.opencode4workspace.webhooks.WebhookType)
	 */
	@Override
	public void setType(WebhookType type) {
		this.type = type;
	}

}
