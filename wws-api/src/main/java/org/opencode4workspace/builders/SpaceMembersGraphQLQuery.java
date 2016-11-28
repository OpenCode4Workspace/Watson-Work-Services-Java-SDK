package org.opencode4workspace.builders;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Profile.PersonChildren;
import org.opencode4workspace.bo.Profile.PersonFields;
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
	private static final long serialVersionUID = 1L;

	/**
	 * @param spaceId
	 *            String, id for a Workspace
	 * @throws WWException
	 *             if there is an error building the object
	 */
	public SpaceMembersGraphQLQuery(String spaceId) throws WWException {
		setOperationName("getSpaceMembers");

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
		setQueryObject(query);
	}

}
