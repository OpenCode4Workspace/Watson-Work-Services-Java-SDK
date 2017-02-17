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
 *        Object for creating a UpdateSpace GraphQL mutation
 *
 */
public class SpaceUpdateGraphQLMutation extends BaseGraphQLMutation {
	private static final String METHOD = "updateSpace";
	public static final String MEMBER_IDS_CHANGED_FIELD = "memberIdsChanged";
	private static final long serialVersionUID = 1L;

	/**
	 * @author Paul Withers
	 * @since 0.6.0
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
	public enum UpdateSpaceFields implements WWFieldsAttributesInterface {
		ID("id", String.class), TITLE("title", String.class), MEMBERS("members", List.class), MEMBER_OPERATION("memberOperation", UpdateSpaceMemberOperation.class);

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
		private UpdateSpaceFields(String label, Class<?> objectClassType) {
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

	public enum UpdateSpaceMemberOperation {
		ADD, REMOVE;
	}

	/**
	 * Easy method to creaate a basic UpdateSpace mutation ObjectDataSenderBuilder for a new Space title
	 * 
	 * @param id
	 *            String id for the Space to update
	 * @param title
	 *            String new title for the Space
	 * @return CreateSpaceGraphQLMutation, the current object
	 * @throws WWException
	 *             if title is missing
	 * 
	 * @since 0.6.0
	 */
	public static SpaceUpdateGraphQLMutation buildUpdateSpaceMutationChangeSpaceTitle(String id, String title) throws WWException {
		if ("".equals(id)) {
			throw new WWException("Space id is mandatory");
		}
		if ("".equals(title)) {
			throw new WWException("title is mandatory");
		}
		InputDataSenderBuilder spaceInput = new InputDataSenderBuilder(Space.UPDATE_SPACE_MUTATION_NAME);
		spaceInput.addField(UpdateSpaceFields.ID, id);
		spaceInput.addField(UpdateSpaceFields.TITLE, title);
		ObjectDataSenderBuilder returnObject = createBasicSpaceReturnObject();
		return new SpaceUpdateGraphQLMutation(spaceInput, returnObject);
	}

	/**
	 * Easy method to create a basic CreateSpace mutation ObjectDataSenderBuilder for a new Space title and list of members
	 * 
	 * @param id
	 *            String id for the Space to update
	 * @param title
	 *            String title of the newly-created Space
	 * @param members
	 *            List of member IDs to add / remove as members
	 * @param addOrRemove
	 *            UpdateSpaceMemberOperation enum whether members should be added to the Space or removed
	 * @return CreateSpaceGraphQLMutation, the current object
	 * @throws WWException
	 *             if title or members are missing
	 * 
	 * @since 0.6.0
	 */
	public static SpaceUpdateGraphQLMutation buildUpdateSpaceMutationChangeTitleAndMembers(String id, String title, List<String> members, UpdateSpaceMemberOperation addOrRemove) throws WWException {
		if ("".equals(id)) {
			throw new WWException("Space id is mandatory");
		}
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
		InputDataSenderBuilder spaceInput = new InputDataSenderBuilder(Space.UPDATE_SPACE_MUTATION_NAME);
		spaceInput.addField(UpdateSpaceFields.ID, id);
		spaceInput.addField(UpdateSpaceFields.TITLE, title);
		spaceInput.addField(UpdateSpaceFields.MEMBERS, members);
		spaceInput.addField(UpdateSpaceFields.MEMBER_OPERATION, addOrRemove);
		ObjectDataSenderBuilder returnObject1 = createBasicSpaceReturnObject();
		ScalarDataSenderBuilder returnObject2 = createBasicMemberIdsReturnObject();
		return new SpaceUpdateGraphQLMutation(spaceInput, returnObject1, returnObject2);
	}

	/**
	 * Easy method to create a basic CreateSpace mutation ObjectDataSenderBuilder for a list of members
	 * 
	 * @param id
	 *            String id for the Space to update
	 * @param members
	 *            List of member IDs to add / remove as members
	 * @param addOrRemove
	 *            UpdateSpaceMemberOperation whether members should be added to the Space or removed
	 * @return CreateSpaceGraphQLMutation, the current object
	 * @throws WWException
	 *             if title or members are missing
	 * 
	 * @since 0.6.0
	 */
	public static SpaceUpdateGraphQLMutation buildUpdateSpaceMutationChangeMembers(String id, List<String> members, UpdateSpaceMemberOperation addOrRemove) throws WWException {
		if ("".equals(id)) {
			throw new WWException("Space id is mandatory");
		}
		if (null == members) {
			throw new WWException("Members are expected for this method");
		} else {
			if (members.isEmpty()) {
				throw new WWException("Members are expected for this method");
			}
		}
		InputDataSenderBuilder spaceInput = new InputDataSenderBuilder(Space.UPDATE_SPACE_MUTATION_NAME);
		spaceInput.addField(UpdateSpaceFields.ID, id);
		spaceInput.addField(UpdateSpaceFields.MEMBERS, members);
		spaceInput.addField(UpdateSpaceFields.MEMBER_OPERATION, addOrRemove);
		IDataSenderBuilder returnObject = createBasicMemberIdsReturnObject();
		return new SpaceUpdateGraphQLMutation(spaceInput, returnObject);
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
	 * @since 0.6.0
	 */
	public SpaceUpdateGraphQLMutation() {
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
	public SpaceUpdateGraphQLMutation(InputDataSenderBuilder input) {
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
	public SpaceUpdateGraphQLMutation(InputDataSenderBuilder input, IDataSenderBuilder returnObject) {
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
	 * @since 0.6.0
	 */
	public SpaceUpdateGraphQLMutation(InputDataSenderBuilder input, IDataSenderBuilder... returnObject) {
		super(METHOD, input, returnObject);
	}

}
