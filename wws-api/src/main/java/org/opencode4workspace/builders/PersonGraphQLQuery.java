package org.opencode4workspace.builders;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Person;
import org.opencode4workspace.bo.Person.PersonChildren;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Object for creating Me or Person query
 */
public class PersonGraphQLQuery extends BaseGraphQLQuery {
	/**
	 * @author Paul Withers
	 * @since 0.5.0
	 * 
	 *        <p>
	 *        Enum for filtering a Person. See {@link WWFieldsAttributesInterface}.
	 *        </p>
	 *        <ul>
	 *        <li>ID expects a String ID for the Person</li>
	 *        </ul>
	 *
	 */
	public enum PersonAttributes implements WWFieldsAttributesInterface {
		ID("id", String.class), EMAIL("email", String.class);

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
		private PersonAttributes(String label, Class<?> objectClassType) {
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

	private static final String METHOD_GET_MYSELF = "getMyself";
	private static final String METHOD_GET_PROFILE = "getProfile";
	private static final long serialVersionUID = 1L;

	/**
	 * East method to create a basic Person query ObjectDataSenderBuilder for the relevant Person, filtered on id
	 * 
	 * @param personId
	 *            String id for the Person to retrieve. If blank, returns "me"
	 * @return PersonGraphQLQuery, the current object
	 * @throws WWException
	 *             if the personId attribute is invalid
	 * 
	 * @since 0.5.0
	 */
	public static PersonGraphQLQuery buildPersonQueryById(String personId) throws WWException {
		if ("".equals(personId)) {
			return buildMyProfileQuery();
		}
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
		query.setObjectName(Person.ONE_PERSON_QUERY_OBJECT_NAME);
		query.addAttribute(PersonAttributes.ID, personId);
		addPersonFieldsAndChildren(query);
		return new PersonGraphQLQuery(METHOD_GET_PROFILE, query);
	}

	/**
	 * East method to create a basic Person query ObjectDataSenderBuilder for the relevant Person filtered on email
	 * 
	 * @param email
	 *            String email for the Person to retrieve. If blank, returns "me"
	 * @return PersonGraphQLQuery, the current object
	 * @throws WWException
	 *             if the email attribute is invalid
	 * 
	 * @since 0.5.0
	 */
	public static PersonGraphQLQuery buildPersonQueryByEmail(String email) throws WWException {
		if ("".equals(email)) {
			return buildMyProfileQuery();
		}
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
		query.setObjectName(Person.ONE_PERSON_QUERY_OBJECT_NAME);
		query.addAttribute(PersonAttributes.ID, email);
		addPersonFieldsAndChildren(query);
		return new PersonGraphQLQuery(METHOD_GET_PROFILE, query);
	}

	/**
	 * East method to create a basic Person query ObjectDataSenderBuilder for "me"
	 * 
	 * @return PersonGraphQLQuery, the current object
	 * 
	 * @since 0.5.0
	 */
	public static PersonGraphQLQuery buildMyProfileQuery() {
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
		query.setObjectName(Person.MY_PROFILE_QUERY_OBJECT_NAME);
		addPersonFieldsAndChildren(query);
		return new PersonGraphQLQuery(METHOD_GET_MYSELF, query);
	}

	/**
	 * Adds all Person fields plus CreatedBy and UpdatedBy
	 * 
	 * @param ObjectDataSenderBuilder containing the query
	 */
	private static ObjectDataSenderBuilder addPersonFieldsAndChildren(ObjectDataSenderBuilder query) {
		query.addField(PersonFields.ID);
		query.addField(PersonFields.DISPLAY_NAME);
		query.addField(PersonFields.EMAIL);
		query.addField(PersonFields.PHOTO_URL);
		query.addField(PersonFields.EXT_ID);
		query.addField(PersonFields.CUSTOMER_ID);
		query.addField(PersonFields.CREATED);
		query.addField(PersonFields.UPDATED);
		query.addField(PersonFields.PRESENCE);
		query.addField(PersonFields.IBM_UNIQUE_ID);
		query.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(PersonChildren.CREATED_BY));
		query.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(PersonChildren.UPDATED_BY));
		return query;
	}

	/**
	 * Creates a PersonGraphQLQuery with a blank ObjectDataSenderBuilder query object using the "getProfile" method name
	 * 
	 * @since 0.5.0
	 */
	public PersonGraphQLQuery() {
		super(METHOD_GET_PROFILE, new ObjectDataSenderBuilder(Person.ONE_PERSON_QUERY_OBJECT_NAME));
	}

	/**
	 * Creates a PersonGraphQLQuery with a blank ObjectDataSenderBuilder query object for the method passed
	 * 
	 * @param operationName
	 *            String operation name, e.g. METHOD_GET_MYSELF or METHOD_GET_PROFILE
	 */
	public PersonGraphQLQuery(String operationName) {
		super(operationName, new ObjectDataSenderBuilder(Person.ONE_PERSON_QUERY_OBJECT_NAME));
	}

	/**
	 * Creates a PersonGraphQLQuery with a passed ObjectDataSenderBuilder query object for the method passed
	 * 
	 * @param operationName
	 *            String operation name, e.g. METHOD_GET_MYSELF or METHOD_GET_PROFILE
	 * @param query
	 *            ObjectDataSenderBuilder containing the query settings
	 * 
	 * @since 0.5.0
	 */
	public PersonGraphQLQuery(String operationName, ObjectDataSenderBuilder query) {
		super(operationName, query);
	}

}
