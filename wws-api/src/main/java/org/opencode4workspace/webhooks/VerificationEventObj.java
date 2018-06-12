package org.opencode4workspace.webhooks;

/**
 * Verification webhook event object. The only field passed is a challenge, which needs echoing back.
 * 
 * @author Paul Withers
 * @since 0.9.0
 *
 */
public class VerificationEventObj extends AbstractWebhookEventObj {
	
	private static final long serialVersionUID = 1L;
	public String challenge;

	/**
	 * Unique challenge string
	 * 
	 * @return String challenge code, unique to each verification request
	 */
	public String getChallenge() {
		return challenge;
	}

	/**
	 * 
	 * @param challenge String code, unique to each verification request
	 */
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}
	
	

}
