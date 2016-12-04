package org.opencode4workspace.builders;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Profile;
import org.opencode4workspace.bo.Profile.PersonChildren;
import org.opencode4workspace.bo.Profile.PersonFields;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Object for creating Me or Profile query
 */
public class ProfileGraphQLQuery extends BaseGraphQLQuery {

	private static final String METHOD_GET_MYSELF = "getMyself";
	private static final String METHOD_GET_PROFILE = "getProfile";
	private static final long serialVersionUID = 1L;

	public static ProfileGraphQLQuery buildProfileQueryById(String profileId) throws WWException {
			if ("".equals(profileId)) {
				return buildMyProfilleQuery();
			}
			ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
			query.setObjectName(Profile.ONE_PROFILE_QUERY_OBJECT_NAME);
			query.addAttribute(PersonFields.ID, profileId);
			query.addField(PersonFields.ID);
			query.addField(PersonFields.DISPLAY_NAME);
			query.addField(PersonFields.EMAIL);
			query.addField(PersonFields.PHOTO_URL);
			query.addField(PersonFields.EXT_ID);
			query.addField(PersonFields.EMAIL_ADDRESSES);
			query.addField(PersonFields.CUSTOMER_ID);
			query.addField(PersonFields.CREATED);
			query.addField(PersonFields.UPDATED);
			query.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(PersonChildren.CREATED_BY));
			query.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(PersonChildren.UPDATED_BY));
			return new ProfileGraphQLQuery(METHOD_GET_PROFILE, query);
	}
	
	public static ProfileGraphQLQuery buildMyProfilleQuery() {
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
			query.setObjectName(Profile.MY_PROFILE_QUERY_OBJECT_NAME);
		query.addField(PersonFields.ID);
		query.addField(PersonFields.DISPLAY_NAME);
		query.addField(PersonFields.EMAIL);
		query.addField(PersonFields.PHOTO_URL);
		query.addField(PersonFields.EXT_ID);
		query.addField(PersonFields.EMAIL_ADDRESSES);
		query.addField(PersonFields.CUSTOMER_ID);
		query.addField(PersonFields.CREATED);
		query.addField(PersonFields.UPDATED);
		query.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(PersonChildren.CREATED_BY));
		query.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(PersonChildren.UPDATED_BY));
		return new ProfileGraphQLQuery(METHOD_GET_MYSELF, query);
	}
	
	/**
	 * Default constructor
	 */
	public ProfileGraphQLQuery() {
		super(METHOD_GET_PROFILE, new ObjectDataSenderBuilder(Profile.ONE_PROFILE_QUERY_OBJECT_NAME));
	}

	public ProfileGraphQLQuery(String opersationName, ObjectDataSenderBuilder query) {
		super(opersationName, query);
	}

}
