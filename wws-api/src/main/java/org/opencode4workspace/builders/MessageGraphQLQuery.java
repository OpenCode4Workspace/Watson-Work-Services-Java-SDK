package org.opencode4workspace.builders;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Message;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Object for querying a Message from a Space
 *
 */
public class MessageGraphQLQuery extends BaseGraphQLQuery {
	private static final String METHOD = "getMessage";
	private static final long serialVersionUID = 1L;

	/**
	 * @author Paul Withers
	 * @since 0.5.0
	 * 
	 *        <p>
	 *        Enum for filtering a Message. See {@link WWFieldsAttributesInterface}.
	 *        </p>
	 *        <ul>
	 *        <li>ID expects a String ID for the Message</li>
	 *        </ul>
	 *
	 */
	public enum MessageAttributes implements WWFieldsAttributesInterface {
		ID("id", String.class);

		private String label;
		private Class<?> objectClassType;

		/**
		 * Constructor
		 * 
		 * @param label
		 *            String, WWS variable
		 * @param objectClassType
		 *            Class<?> Java data type expected for passing across
		 */
		private MessageAttributes(String label, Class<?> objectClassType) {
			this.label = label;
			this.objectClassType = objectClassType;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.opencode4workspace.bo.WWFieldsAttributesInterface#getLabel()
		 */
		@Override
		public String getLabel() {
			return label;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.opencode4workspace.bo.WWFieldsAttributesInterface#getObjectClassType()
		 */
		@Override
		public Class<?> getObjectClassType() {
			return objectClassType;
		}

	}

	/**
	 * East method to create a basic Message query ObjectDataSenderBuilder for the relevant Message, filtered on id
	 * 
	 * @param messageId
	 *            String id for the message
	 * @return MessageGraphQLQuery, the current object
	 * @throws WWException
	 *             if the messageId is missing
	 * 
	 * @since 0.5.0
	 */
	public static MessageGraphQLQuery buildMessageGraphQueryWithMessageId(String messageId) throws WWException {
		if ("".equals(messageId)) {
			throw new WWException("messageId is mandatory");
		}
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
		query.setObjectName(Message.ONE_MESSAGE_QUERY_OBJECT_NAME);
		query.addAttribute(MessageAttributes.ID, messageId);
		query.addField(MessageFields.CONTENT_TYPE);
		query.addField(MessageFields.CONTENT);
		query.addField(MessageFields.CREATED);
		query.addField(MessageFields.UPDATED);
		query.addField(MessageFields.ANNOTATIONS);
		query.addField(MessageFields.ID);
		query.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(ConversationChildren.CREATED_BY));
		query.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(ConversationChildren.UPDATED_BY));
		return new MessageGraphQLQuery(query);
	}

	/**
	 * Creates a Message query with a blank ObjectDataSenderBuilder query object
	 * 
	 * @since 0.5.0
	 */
	public MessageGraphQLQuery() {
		super(METHOD, new ObjectDataSenderBuilder(Message.ONE_MESSAGE_QUERY_OBJECT_NAME));
	}

	/**
	 * Creates a Message query with a pre-populated ObjectDataSenderBuilder query object
	 * 
	 * @param query
	 *            ObjectDataSenderBuilder containing the query settings
	 * 
	 * @since 0.5.0
	 */
	public MessageGraphQLQuery(ObjectDataSenderBuilder query) {
		super(METHOD, query);
	}

}
