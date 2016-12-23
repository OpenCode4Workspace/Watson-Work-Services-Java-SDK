package org.opencode4workspace.builders;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Person.PersonChildren;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.builders.SpaceGraphQLQuery.SpaceAttributes;

/**
 * @author Paul Withers
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Object for querying members from a Space
 *
 */
public class SpaceMembersGraphQLQuery extends BaseGraphQLQuery {
	private static final String METHOD = "getSpaceMembers";
	private static final long serialVersionUID = 1L;

	/**
	 * Easy method to create a basic Conversation query ObjectDataSenderBuilder for a relevant conversation ID
	 * 
	 * @param spaceId
	 *            String ID for the space. This is mandatory for WWS.
	 * @return SpaceMembersGraphQLQuery, this object
	 * @throws WWException
	 *             if space id is missing
	 * 
	 * @since 0.5.0
	 */
	public static SpaceMembersGraphQLQuery buildSpaceMemberGraphQueryBySpaceId(String spaceId) throws WWException {
		if ("".equals(spaceId)) {
			throw new WWException("spaceId is mandatory");
		}
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
		query.setObjectName(Space.ONE_SPACE_QUERY_OBJECT_NAME);
		query.addAttribute(SpaceAttributes.ID, spaceId);
		ObjectDataSenderBuilder members = new ObjectDataSenderBuilder(SpaceChildren.MEMBERS.getLabel(), true);
		members.addField(PersonFields.ID);
		members.addField(PersonFields.PHOTO_URL);
		members.addField(PersonFields.EMAIL);
		members.addField(PersonFields.DISPLAY_NAME);
		members.addField(PersonFields.CREATED);
		members.addField(PersonFields.UPDATED);
		members.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(PersonChildren.CREATED_BY));
		members.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(PersonChildren.UPDATED_BY));
		query.addChild(members);
		return new SpaceMembersGraphQLQuery(query);

	}

	/**
	 * Creates a Space query with a blank ObjectDataSenderBuilder query object
	 * 
	 * @since 0.5.0
	 */
	public SpaceMembersGraphQLQuery() {
		super(METHOD, new ObjectDataSenderBuilder(Space.ONE_SPACE_QUERY_OBJECT_NAME));
	}

	/**
	 * Creates a Space query with a pre-populated ObjectDataSenderBuilder query object
	 * 
	 * @param query
	 *            ObjectDataSenderBuilder containing the query settings
	 * 
	 * @since 0.5.0
	 */
	public SpaceMembersGraphQLQuery(ObjectDataSenderBuilder query) {
		super(METHOD, query);
	}

}
