package org.opencode4workspace.builders;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Conversation.ConversationFields;
import org.opencode4workspace.bo.Message.MessageChildren;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.graphql.BasicPaginationEnum;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Object for creating a Conversation GraphQL query
 */
public class ConversationGraphQLQuery extends BaseGraphQLQuery {

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
		query.addAttribute(ConversationFields.ID, conversationId);
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
