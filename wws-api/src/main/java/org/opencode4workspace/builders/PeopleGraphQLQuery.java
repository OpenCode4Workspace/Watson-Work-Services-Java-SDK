package org.opencode4workspace.builders;

import java.util.List;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Profile;
import org.opencode4workspace.bo.Profile.PersonChildren;
import org.opencode4workspace.bo.Profile.PersonFields;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Object for creating Me or Profile query
 */
public class PeopleGraphQLQuery extends BaseGraphQLQuery {

	/**
	 * @author Paul Withers
	 * @since 0.5.0
	 * 
	 *        <p>
	 * 		Enum for filtering a . See {@linkplain WWFieldsAttributesInterface}.
	 *        </p>
	 *        <ul>
	 *        <li>ID expects a list of IDs for profiles</li>
	 *        <li>NAME expects a single word that will be found in a profile's name</li>
	 *        </ul>
	 *
	 */
	public enum PeopleAttributes implements WWFieldsAttributesInterface {
		ID("id", List.class), NAME("name", String.class);

		private String label;
		private Class<?> objectClassType;

		/**
		 * Constructor
		 * 
		 * @param label
		 *            String, WWS variable
		 * @param objectClassType
		 *            Class<?> Java data type expected for passing across
		 */
		private PeopleAttributes(String label, Class<?> objectClassType) {
			this.label = label;
			this.objectClassType = objectClassType;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.opencode4workspace.bo.WWFieldsAttributesInterface#getLabel()
		 */
		@Override
		public String getLabel() {
			return label;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.opencode4workspace.bo.WWFieldsAttributesInterface#getObjectClassType()
		 */
		@Override
		public Class<?> getObjectClassType() {
			return objectClassType;
		}
	}

	private static final String METHOD_GET_PEOPLE = "getPeople";
	private static final long serialVersionUID = 1L;

	public static PeopleGraphQLQuery buildProfileQueryById(List<String> profileId) throws WWException {
		if ("".equals(profileId)) {
			throw new WWException("No Profile ID passed");
		}
		PeopleGraphQLQuery query = buildUnfilteredProfileQuery();
		query.addAttribute(PeopleAttributes.ID, profileId);
		return query;
	}

	public static PeopleGraphQLQuery buildProfileQueryByName(String profileName) throws WWException {
		if ("".equals(profileName)) {
			throw new WWException("No Profile Name passed");
		}
		PeopleGraphQLQuery query = buildUnfilteredProfileQuery();
		query.addAttribute(PeopleAttributes.NAME, profileName);
		return query;
	}

	private static PeopleGraphQLQuery buildUnfilteredProfileQuery() {
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder(Profile.PROFILES_QUERY_OBJECT_NAME, true);
		query.addPageInfo();
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
		return new PeopleGraphQLQuery(METHOD_GET_PEOPLE, query);
	}

	/**
	 * Default constructor
	 */
	public PeopleGraphQLQuery() {
		super(METHOD_GET_PEOPLE, new ObjectDataSenderBuilder(Profile.PROFILES_QUERY_OBJECT_NAME));
	}

	public PeopleGraphQLQuery(String operationName, ObjectDataSenderBuilder query) {
		super(operationName, query);
	}

}
