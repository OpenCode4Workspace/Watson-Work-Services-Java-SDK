package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Annotation.AnnotationType;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Conversation.ConversationFields;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.builders.ObjectDataSenderBuilder;
import org.opencode4workspace.builders.ConversationGraphQLQuery;
import org.opencode4workspace.builders.ConversationGraphQLQuery.ConversationAttributes;
import org.opencode4workspace.builders.ConversationGraphQLQuery.ConversationMessageAttributes;

public class ConversationTest {
	
	private static String CONVO_QUERY = "query getConversation {conversation (id: \"5811aeb9e4b0052629e89bb1\") {id messages (annotationType: \"generic\") {items {content}}}}";
	
	@Test
	public void testConversation() throws WWException {
		ObjectDataSenderBuilder messages = new ObjectDataSenderBuilder(ConversationChildren.MESSAGES.getLabel(), true)
				.addAttribute(ConversationMessageAttributes.ANNOTATION_TYPE, AnnotationType.GENERIC.getLabel())
				.addField(MessageFields.CONTENT);
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder(Conversation.CONVERSATION_QUERY_OBJECT_NAME)
				.addAttribute(ConversationAttributes.ID, "5811aeb9e4b0052629e89bb1")
				.addField(ConversationFields.ID)
				.addChild(messages);
		ConversationGraphQLQuery queryObj = new ConversationGraphQLQuery(query);
		assertEquals(CONVO_QUERY, queryObj.returnQuery());
	}

}
