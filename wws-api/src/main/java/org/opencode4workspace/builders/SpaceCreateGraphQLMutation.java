package org.opencode4workspace.builders;

import java.util.List;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;

/**
 * @author Paul Withers
 * @since 0.6.0
 * 
 *        Object for creating a CreateSpace GraphQL mutation
 *
 */
public class SpaceCreateGraphQLMutation extends BaseGraphQLMutation {
	private static final String METHOD = "createSpace";
	private static final long serialVersionUID = 1L;

	/**
	 * @author Paul Withers
	 * @since 0.6.0
	 * 
	 *        <p>
	 *        Enum for input parameters for createSpace mutation. See {@link WWFieldsAttributesInterface}.
	 *        </p>
	 *        <ul>
	 *        <li>TITLE expects a String title for the Space</li>
	 *        <li>MEMBERS expects a List of IDs for the people to be added to the Space</li>
	 *        </ul>
	 *
	 */
	public enum CreateSpaceFields implements WWFieldsAttributesInterface {
		TITLE("title", String.class), MEMBERS("members", List.class);

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
		private CreateSpaceFields(String label, Class<?> objectClassType) {
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

	/**
	 * Easy method to create a basic CreateSpace mutation ObjectDataSenderBuilder for a relevant Space title
	 * 
	 * @param title
	 *            String title of the newly-created Space
	 * @return CreateSpaceGraphQLMutation, the current object
	 * @throws WWException
	 *             if title is missing
	 * 
	 * @since 0.6.0
	 */
	public static SpaceCreateGraphQLMutation buildCreateSpaceMutationWithSpaceTitle(String title) throws WWException {
		if ("".equals(title)) {
			throw new WWException("title is mandatory");
		}
		InputDataSenderBuilder spaceInput = new InputDataSenderBuilder(Space.CREATE_SPACE_MUTATION_NAME);
		spaceInput.addField(CreateSpaceFields.TITLE, title);
		ObjectDataSenderBuilder returnObject = createBasicSpaceReturnObject();
		return new SpaceCreateGraphQLMutation(spaceInput, returnObject);
	}

	/**
	 * Easy method to create a basic CreateSpace mutation ObjectDataSenderBuilder for a relevant Space title and list of members
	 * 
	 * @param title
	 *            String title of the newly-created Space
	 * @param members
	 *            List of member IDs to add as members
	 * @return CreateSpaceGraphQLMutation, the current object
	 * @throws WWException
	 *             if title or members are missing
	 * 
	 * @since 0.6.0
	 */
	public static SpaceCreateGraphQLMutation buildCreateSpaceMutationWithSpaceTitleAndMembers(String title, List<String> members) throws WWException {
		if ("".equals(title)) {
			throw new WWException("title is mandatory");
		}
		if (null == members) {
			throw new WWException("Members are expected for this method");
		} else {
			if (members.isEmpty()) {
				throw new WWException("Members are expected for this method");
			}
		}
		InputDataSenderBuilder spaceInput = new InputDataSenderBuilder(Space.CREATE_SPACE_MUTATION_NAME);
		spaceInput.addField(CreateSpaceFields.TITLE, title);
		spaceInput.addField(CreateSpaceFields.MEMBERS, members);
		ObjectDataSenderBuilder returnObject = createBasicSpaceReturnObject();
		return new SpaceCreateGraphQLMutation(spaceInput, returnObject);
	}

	/**
	 * Method to create a basic ObjectDataSenderBuilder to return just the Space ID
	 * 
	 * @return ObjectDataSenderBuilder of query to return from mutation
	 */
	private static ObjectDataSenderBuilder createBasicSpaceReturnObject() {
		ObjectDataSenderBuilder spaceObj = new ObjectDataSenderBuilder(Space.ONE_SPACE_QUERY_OBJECT_NAME);
		spaceObj.addField(SpaceFields.ID);
		return spaceObj;
	}

	/**
	 * Creates a createSpace mutation with empty InputDataSenderBuilder and ObjectDataSenderBuilder objects for the mutation
	 * 
	 * @since 0.6.0
	 */
	public SpaceCreateGraphQLMutation() {
		super(METHOD, new InputDataSenderBuilder(), new ObjectDataSenderBuilder());
	}

	/**
	 * Creates a createSpace query with pre-populated InputDataSenderBuilder objects
	 * 
	 * @param input
	 *            InputDataSenderBuilder containing the query space creation criteria
	 * 
	 * @since 0.6.0
	 */
	public SpaceCreateGraphQLMutation(InputDataSenderBuilder input) {
		super(METHOD, input, new ObjectDataSenderBuilder());
	}

	/**
	 * Creates a createSpace query with pre-populated InputDataSenderBuilder and ObjectDataSenderBuilder objects
	 * 
	 * @param input
	 *            InputDataSenderBuilder containing the space creation criteria
	 * @param returnObject
	 *            ObjectDataSenderBuilder containing the query settings
	 * 
	 * @since 0.6.0
	 */
	public SpaceCreateGraphQLMutation(InputDataSenderBuilder input, ObjectDataSenderBuilder returnObject) {
		super(METHOD, input, returnObject);
	}

}
