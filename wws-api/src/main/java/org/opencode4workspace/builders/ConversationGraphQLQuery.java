package org.opencode4workspace.builders;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Conversation.ConversationFields;
import org.opencode4workspace.bo.Message.MessageChildren;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.graphql.BasicPaginationEnum;

public class ConversationGraphQLQuery extends BaseGraphQLQuery {

	private static final String METHOD = "getConversation";
	private static final long serialVersionUID = 1L;

	public static ConversationGraphQLQuery buildStandardConversationQueryById(String conversationId) throws WWException {
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

	public ConversationGraphQLQuery() {
		super(METHOD, new ObjectDataSenderBuilder(Conversation.CONVERSATION_QUERY_OBJECT_NAME));
	}

	public ConversationGraphQLQuery(ObjectDataSenderBuilder query) {
		super(METHOD, query);

	}

}
