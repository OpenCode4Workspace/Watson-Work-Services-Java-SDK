package org.opencode4workspace.builders;

import java.util.List;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;

/**
 * @author Paul Withers
 * @since 0.8.0
 * 
 *        Object for creating a removeSpaceMembers GraphQL mutation
 *
 */
public class SpaceMembersRemoveGraphQLMutation extends BaseGraphQLMutation {
	private static final String METHOD = "removeSpaceMembers";
	public static final String MEMBER_IDS_CHANGED_FIELD = "memberIdsChanged";
	private static final long serialVersionUID = 1L;

	/**
	 * @author Paul Withers
	 * @since 0.8.0
	 * 
	 *        <p>
	 *        Enum for input parameters for updateSpace mutation. See {@link WWFieldsAttributesInterface}.
	 *        </p>
	 *        <ul>
	 *        <li>TITLE expects a String title for the Space</li>
	 *        <li>MEMBERS expects a List of IDs for the people to be added to the Space</li>
	 *        </ul>
	 *
	 */
	public enum RemoveSpaceMembersField implements WWFieldsAttributesInterface {
		SPACE_ID("spaceId", String.class), MEMBERS("members", List.class);

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
		private RemoveSpaceMembersField(String label, Class<?> objectClassType) {
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
	 * Easy method to create a removeSpaceMembers mutation ObjectDataSenderBuilder and return members updated
	 * 
	 * @param spaceId
	 *            String id for the Space to update
	 * @param members
	 *            List<String> of member IDs to remove from the Space
	 * @return SpaceMembersRemoveGraphQLMutation, the current object
	 * @throws WWException
	 *             if is or members is missing
	 * 
	 * @since 0.8.0
	 */
	public static SpaceMembersRemoveGraphQLMutation buildRemoveSpaceMembersMutationChange(String spaceId, List<String> members) throws WWException {
		if ("".equals(spaceId)) {
			throw new WWException("Space id is mandatory");
		}
		if (null == members || members.isEmpty()) {
			throw new WWException("members to update are mandatory");
		}
		InputDataSenderBuilder spaceInput = new InputDataSenderBuilder(Space.REMOVE_SPACE_MEMBERS_MUTATION_NAME);
		spaceInput.addField(RemoveSpaceMembersField.SPACE_ID, spaceId);
		spaceInput.addField(RemoveSpaceMembersField.MEMBERS, members);
		ScalarDataSenderBuilder returnObject = createBasicMemberIdsReturnObject();
		return new SpaceMembersRemoveGraphQLMutation(spaceInput, returnObject);
	}

	/**
	 * Method to create a basic ObjectDataSenderBuilder to return just the Space ID
	 * 
	 * @return ObjectDataSenderBuilder of query to return from mutation
	 */
	private static ObjectDataSenderBuilder createBasicSpaceReturnObject() {
		ObjectDataSenderBuilder spaceObj = new ObjectDataSenderBuilder(Space.ONE_SPACE_QUERY_OBJECT_NAME);
		spaceObj.addField(SpaceFields.TITLE);
		return spaceObj;
	}

	/**
	 * Method to create a basic ObjectDataSenderBuilder to return just the Space ID
	 * 
	 * @return ObjectDataSenderBuilder of query to return from mutation
	 */
	private static ScalarDataSenderBuilder createBasicMemberIdsReturnObject() {
		ScalarDataSenderBuilder memberObj = new ScalarDataSenderBuilder(MEMBER_IDS_CHANGED_FIELD);
		return memberObj;
	}

	/**
	 * Creates a createSpace mutation with empty InputDataSenderBuilder and ObjectDataSenderBuilder objects for the mutation
	 * 
	 * @since 0.8.0
	 */
	public SpaceMembersRemoveGraphQLMutation() {
		super(METHOD, new InputDataSenderBuilder(), new ObjectDataSenderBuilder());
	}

	/**
	 * Creates a createSpace query with pre-populated InputDataSenderBuilder objects
	 * 
	 * @param input
	 *            InputDataSenderBuilder containing the query space creation criteria
	 * 
	 * @since 0.8.0
	 */
	public SpaceMembersRemoveGraphQLMutation(InputDataSenderBuilder input) {
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
	 * @since 0.8.0
	 */
	public SpaceMembersRemoveGraphQLMutation(InputDataSenderBuilder input, IDataSenderBuilder returnObject) {
		super(METHOD, input, returnObject);
	}

	/**
	 * Creates a createSpace query with pre-populated InputDataSenderBuilder and ObjectDataSenderBuilder objects
	 * 
	 * @param input
	 *            InputDataSenderBuilder containing the space creation criteria
	 * @param returnObject
	 *            ObjectDataSenderBuilders containing the query settings
	 * 
	 * @since 0.8.0
	 */
	public SpaceMembersRemoveGraphQLMutation(InputDataSenderBuilder input, IDataSenderBuilder... returnObject) {
		super(METHOD, input, returnObject);
	}

}
