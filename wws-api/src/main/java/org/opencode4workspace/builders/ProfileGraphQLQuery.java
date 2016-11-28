package org.opencode4workspace.builders;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Profile.PersonChildren;
import org.opencode4workspace.bo.Profile.PersonFields;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Object for creating Me or Profile query
 */
public class ProfileGraphQLQuery extends BaseGraphQLQuery {

	private static final long serialVersionUID = 1L;

	/**
	 * @param profileId
	 *            String, id of the profile required or blank for "me"
	 * @throws WWException
	 *             if there is an error building the object
	 */
	public ProfileGraphQLQuery(String profileId) throws WWException {
		try {
			boolean isMe = false;
			if ("".equals(profileId)) {
				isMe = true;
				setOperationName("getMyself");
			} else {
				setOperationName("getProfile");
			}

			ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
			if (isMe) {
				query.setObjectName("me");
			} else {
				query.setObjectName("person");
				query.addAttribute(PersonFields.ID, profileId);
			}
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
			setQueryObject(query);
		} catch (Exception e) {
			throw new WWException(e);
		}
	}

}
