package org.opencode4workspace.builders;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Person.PersonChildren;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.Space.SpaceFields;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Object for querying members from a Workspace
 *
 */
public class SpaceMembersGraphQLQuery extends BaseGraphQLQuery {
	private static final String METHOD = "getSpaceMembers";
	private static final long serialVersionUID = 1L;

	public static SpaceMembersGraphQLQuery buildSpaceMemberGraphQueryBySpaceId(String spaceId) throws WWException {

		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
		query.setObjectName(Space.ONE_SPACE_QUERY_OBJECT_NAME);
		query.addAttribute(SpaceFields.ID, spaceId);
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
	 * Default Constructor
	 */
	public SpaceMembersGraphQLQuery() {
		super(METHOD, new ObjectDataSenderBuilder(Space.ONE_SPACE_QUERY_OBJECT_NAME));
	}

	public SpaceMembersGraphQLQuery(ObjectDataSenderBuilder query) {
		super(METHOD, query);
	}

}
