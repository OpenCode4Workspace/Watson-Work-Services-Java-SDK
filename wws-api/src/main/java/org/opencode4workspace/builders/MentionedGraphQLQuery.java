package org.opencode4workspace.builders;

import org.apache.http.auth.BasicUserPrincipal;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Mentioned;
import org.opencode4workspace.bo.Mentioned.MentionedFields;
import org.opencode4workspace.bo.PageInfo.PageInfoFields;
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
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
		query.addAttribute(BasicPaginationEnum.FIRST, 10);
		query.setObjectName(Mentioned.MENTIONED_QUERY_OBJECT_NAME);
		query.addPageInfo();
		query.addField(MentionedFields.UPDATED);
		query.addField(MentionedFields.UPDATED_BY);
		query.addField(MentionedFields.PERSON);
		query.addField(MentionedFields.SPACE);
		query.addField(MentionedFields.MESSAGE);
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
