package org.opencode4workspace.builders;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Person;
import org.opencode4workspace.bo.Person.PersonChildren;
import org.opencode4workspace.bo.Person.PersonFields;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Object for creating Me or Person query
 */
public class PersonGraphQLQuery extends BaseGraphQLQuery {

	private static final String METHOD_GET_MYSELF = "getMyself";
	private static final String METHOD_GET_PROFILE = "getProfile";
	private static final long serialVersionUID = 1L;

	public static PersonGraphQLQuery buildPersonQueryById(String personId) throws WWException {
			if ("".equals(personId)) {
				return buildMyProfileQuery();
			}
			ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
			query.setObjectName(Person.ONE_PERSON_QUERY_OBJECT_NAME);
			query.addAttribute(PersonFields.ID, personId);
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
			return new PersonGraphQLQuery(METHOD_GET_PROFILE, query);
	}
	
	public static PersonGraphQLQuery buildMyProfileQuery() {
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
			query.setObjectName(Person.MY_PROFILE_QUERY_OBJECT_NAME);
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
		return new PersonGraphQLQuery(METHOD_GET_MYSELF, query);
	}
	
	/**
	 * Default constructor
	 */
	public PersonGraphQLQuery() {
		super(METHOD_GET_PROFILE, new ObjectDataSenderBuilder(Person.ONE_PERSON_QUERY_OBJECT_NAME));
	}

	public PersonGraphQLQuery(String opersationName, ObjectDataSenderBuilder query) {
		super(opersationName, query);
	}

}
