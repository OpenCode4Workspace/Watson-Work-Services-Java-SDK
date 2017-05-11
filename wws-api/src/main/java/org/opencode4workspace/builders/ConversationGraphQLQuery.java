package org.opencode4workspace.builders;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Conversation.ConversationFields;
import org.opencode4workspace.bo.Message.MessageChildren;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;
import org.opencode4workspace.graphql.BasicPaginationEnum;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Object for creating a Conversation GraphQL query
 */
public class ConversationGraphQLQuery extends BaseGraphQLQuery {
	
	/**
	 * @author Paul Withers
	 * @since 0.5.0
	 * 
	 *        <p>
	 *        Enum for filtering a Conversation. See {@link WWFieldsAttributesInterface}.
	 *        </p>
	 *        <ul>
	 *        <li>ID expects a String ID for the Conversation</li>
	 *        </ul>
	 *
	 */
	public enum ConversationAttributes implements WWFieldsAttributesInterface {
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
		private ConversationAttributes(String label, Class<?> objectClassType) {
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
	 * @author Paul Withers
	 * @since 0.5.0
	 * 
	 *        <p>
	 *        Enum for filtering a MessageList. See {@link WWFieldsAttributesInterface}.
	 *        </p>
	 *        <ul>
	 *        <li>OLDEST_TIMESTAMP expects a Long for the time</li>
	 *        <li>MOST_RECENT_TIMESTAMP expects a Long for the time</li>
	 *        <li>ANNOTATION_TYPE expects a String for the type of annotation</li>
	 *        </ul>
	 *
	 */
	public enum MessageAttributes implements WWFieldsAttributesInterface {
		OLDEST_TIMESTAMP("oldestTimestamp", Long.class), MOST_RECENT_TIMESTAMP("mostRecentTimestamp", Long.class), ANNOTATION_TYPE("annotationType", String.class);

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

	private static final String METHOD = "getConversation";
	private static final long serialVersionUID = 1L;

	/**
	 * Easy method to create a basic Conversation query ObjectDataSenderBuilder for a relevant conversation ID
	 * 
	 * @param conversationId
	 *            String ID for the conversation. This is mandatory for WWS.
	 * @return ConversationGraphQLQuery, the current object
	 * @throws WWException
	 *             if conversation id is missing
	 * 
	 * @since 0.5.0
	 */
	public static ConversationGraphQLQuery buildStandardConversationQueryById(String conversationId) throws WWException {
		if ("".equals(conversationId)) {
			throw new WWException("conversationId is mandatory");
		}
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
		query.setObjectName(Conversation.CONVERSATION_QUERY_OBJECT_NAME);
		query.addAttribute(ConversationAttributes.ID, conversationId);
		query.addField(ConversationFields.ID);
		query.addField(ConversationFields.CREATED);
		query.addField(ConversationFields.UPDATED);
		ObjectDataSenderBuilder messages = new ObjectDataSenderBuilder(ConversationChildren.MESSAGES.getLabel(), true);
		messages.addAttribute(BasicPaginationEnum.FIRST, 50);
		messages.addPageInfo();
		messages.addField(MessageFields.CONTENT_TYPE);
		messages.addField(MessageFields.CONTENT);
		messages.addField(MessageFields.CREATED);
		messages.addField(MessageFields.UPDATED);
		messages.addField(MessageFields.ANNOTATIONS);
		messages.addField(MessageFields.ID);
		messages.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(MessageChildren.CREATED_BY));
		messages.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(MessageChildren.UPDATED_BY));
		query.addChild(messages);
		return new ConversationGraphQLQuery(query);
	}

	/**
	 * Creates a Conversation query with a blank ObjectDataSenderBuilder query object
	 * 
	 * @since 0.5.0
	 */
	public ConversationGraphQLQuery() {
		super(METHOD, new ObjectDataSenderBuilder(Conversation.CONVERSATION_QUERY_OBJECT_NAME));
	}

	/**
	 * Creates a Conversation query with a pre-populated ObjectDataSenderBuilder query object
	 * 
	 * @param query
	 *            ObjectDataSenderBuilder containing the query settings
	 * 
	 * @since 0.5.0
	 */
	public ConversationGraphQLQuery(ObjectDataSenderBuilder query) {
		super(METHOD, query);

	}

}
