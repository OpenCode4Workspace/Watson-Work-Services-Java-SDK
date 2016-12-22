package org.opencode4workspace.builders;

import java.util.List;

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
public class PeopleGraphQLQuery extends BaseGraphQLQuery {

	/**
	 * @author Paul Withers
	 * @since 0.5.0
	 * 
	 *        <p>
	 *        Enum for filtering a . See {@link WWFieldsAttributesInterface}.
	 *        </p>
	 *        <ul>
	 *        <li>ID expects a list of IDs for people</li>
	 *        <li>NAME expects a single word that will be found in a person's name</li>
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

	private static final String METHOD = "getPeople";
	private static final long serialVersionUID = 1L;

	/**
	 * Builds a People query based on a List of IDs of person entries
	 * 
	 * @param personId
	 *            List of String person IDs
	 * @return PeopleGraphQLQuery of basic unfiltered person fields and children
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public static PeopleGraphQLQuery buildPersonQueryById(List<String> personId) throws WWException {
		if ("".equals(personId)) {
			throw new WWException("No Person ID passed");
		}
		PeopleGraphQLQuery query = buildUnfilteredPersonQuery();
		query.addAttribute(PeopleAttributes.ID, personId);
		return query;
	}

	/**
	 * Builds a People query based on a word in a person's name
	 * 
	 * @param personName
	 *            String, part of a person's name
	 * @return PeopleGraphQLQuery of basic unfiltered person fields and children
	 * @throws WWException
	 *             containing an error message, if the request was unsuccessful
	 * 
	 * @since 0.5.0
	 */
	public static PeopleGraphQLQuery buildPersonQueryByName(String personName) throws WWException {
		if ("".equals(personName)) {
			throw new WWException("No Person Name passed");
		}
		PeopleGraphQLQuery query = buildUnfilteredPersonQuery();
		query.addAttribute(PeopleAttributes.NAME, personName);
		return query;
	}

	/**
	 * Builds a query of all fields and children in a Person object
	 * 
	 * @return PeopleGraphQLQuery for all Person fields and children
	 * 
	 * @since 0.5.0
	 */
	private static PeopleGraphQLQuery buildUnfilteredPersonQuery() {
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder(Person.PEOPLE_QUERY_OBJECT_NAME, true);
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
		return new PeopleGraphQLQuery(query);
	}

	/**
	 * Creates a People query with a blank ObjectDataSenderBuilder query object
	 * 
	 * @since 0.5.0
	 */
	public PeopleGraphQLQuery() {
		super(METHOD, new ObjectDataSenderBuilder(Person.PEOPLE_QUERY_OBJECT_NAME));
	}

	/**
	 * Creates a People query with a pre-populated ObjectDataSenderBuilder query object
	 * 
	 * @param query
	 *            ObjectDataSenderBuilder containing query options
	 * 
	 * @since 0.5.0
	 */
	public PeopleGraphQLQuery(ObjectDataSenderBuilder query) {
		super(METHOD, query);
	}

}
