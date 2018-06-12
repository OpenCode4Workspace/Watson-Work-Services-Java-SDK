package org.opencode4workspace.webhooks;

/**
 * Message annotation added webhook event object. The only field passed is a challenge, which needs echoing back.
 * 
 * @author Paul Withers
 * @since 0.9.0
 *
 */
public class MessageAnnotationAddedEventObj extends AbstractWebhookEventObj {
	private static final long serialVersionUID = 1L;
	private String annotationId;
	private String annotationPayload;
	private String annotationType;
	private String messageId;
	private String spaceId;
	private String spaceName;
	private Long time;
	private String userId;
	private String userName;

	/**
	 * @return String unique id for the annotation
	 */
	public String getAnnotationId() {
		return annotationId;
	}

	/**
	 * @param annotationId unique id for the annotation
	 */
	public void setAnnotationId(String annotationId) {
		this.annotationId = annotationId;
	}

	/**
	 * @return String 
	 */
	public String getAnnotationPayload() {
		return annotationPayload;
	}

	public void setAnnotationPayload(String annotationPayload) {
		this.annotationPayload = annotationPayload;
	}

	public String getAnnotationType() {
		return annotationType;
	}

	public void setAnnotationType(String annotationType) {
		this.annotationType = annotationType;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
