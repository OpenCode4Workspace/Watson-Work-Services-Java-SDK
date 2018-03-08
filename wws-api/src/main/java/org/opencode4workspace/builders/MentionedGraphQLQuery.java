package org.opencode4workspace.builders;

import org.apache.http.auth.BasicUserPrincipal;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Mentioned;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Mentioned.MentionedChildren;
import org.opencode4workspace.bo.Mentioned.MentionedFields;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.bo.PageInfo.PageInfoFields;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.graphql.BasicPaginationEnum;

/**
 * @author Paul Withers
 * @since 0.8.0
 * 
 *        Object for querying Mentions. This query is only when running as a user, and can only be used to get mentions
 *        for the current user. Attributes are {@link PageInfoFields}
 *
 */
public class MentionedGraphQLQuery extends BaseGraphQLQuery {
	private static final String METHOD = "getMentioned";
	private static final long serialVersionUID = 1L;

	/**
	 * East method to create a full Mentioned query ObjectDataSenderBuilder for the first 10 mentions for the current
	 * user
	 * 
	 * @return MentionedGraphQLQuery, this object
	 * @throws WWException
	 *             if the attributes are invalid
	 * 
	 * @since 0.8.0
	 */
	public static MentionedGraphQLQuery buildFullMentionedGraphQuery() throws WWException {
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder(Mentioned.MENTIONED_QUERY_OBJECT_NAME, true);
		query.addAttribute(BasicPaginationEnum.FIRST, 10);
		query.setObjectName(Mentioned.MENTIONED_QUERY_OBJECT_NAME);
		query.addPageInfo();
		query.addField(MentionedFields.UPDATED);
		query.addField(MentionedFields.UPDATED_BY);
		ObjectDataSenderBuilder message = new ObjectDataSenderBuilder(MentionedChildren.MESSAGE.getLabel());
		message.addField(MessageFields.CONTENT_TYPE);
		message.addField(MessageFields.CONTENT);
		message.addField(MessageFields.CREATED);
		message.addField(MessageFields.UPDATED);
		message.addField(MessageFields.ANNOTATIONS);
		message.addField(MessageFields.ID);
		query.addChild(message);
		ObjectDataSenderBuilder space = new ObjectDataSenderBuilder(MentionedChildren.SPACE.getLabel());
		space.addField(SpaceFields.ID);
		space.addField(SpaceFields.TITLE);
		space.addField(SpaceFields.DESCRIPTION);
		query.addChild(space);
		ObjectDataSenderBuilder person = new ObjectDataSenderBuilder(MentionedChildren.PERSON.getLabel());
		person.addField(PersonFields.ID);
		person.addField(PersonFields.PHOTO_URL);
		person.addField(PersonFields.EMAIL);
		person.addField(PersonFields.DISPLAY_NAME);
		query.addChild(person);
		return new MentionedGraphQLQuery(query);
	}

	/**
	 * Creates a Mentioned query with a blank ObjectDataSenderBuilder query object
	 * 
	 * @since 0.8.0
	 */
	public MentionedGraphQLQuery() {
		super(METHOD, new ObjectDataSenderBuilder(Mentioned.MENTIONED_QUERY_OBJECT_NAME));
	}

	/**
	 * Creates a Mentioned query with a pre-populated ObjectDataSenderBuilder query object
	 * 
	 * @param query
	 *            ObjectDataSenderBuilder containing the query settings
	 * 
	 * @since 0.8.0
	 */
	public MentionedGraphQLQuery(ObjectDataSenderBuilder query) {
		super(METHOD, query);
	}

}
