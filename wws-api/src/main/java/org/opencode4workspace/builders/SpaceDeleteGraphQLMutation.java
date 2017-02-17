package org.opencode4workspace.builders;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;

public class SpaceDeleteGraphQLMutation extends BaseGraphQLMutation {
	private static final String METHOD = "deleteSpace";
	private static final long serialVersionUID = 1L;

	/**
	 * @author Paul Withers
	 * @since 0.6.0
	 * 
	 *        <p>
	 *        Enum for input parameters for deleteSpace mutation. See {@link WWFieldsAttributesInterface}.
	 *        </p>
	 *        <ul>
	 *        <li>ID expects a String ID for the Space</li>
	 *        </ul>
	 *
	 */
	public enum DeleteSpaceFields implements WWFieldsAttributesInterface {
		ID("id", String.class);

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
		private DeleteSpaceFields(String label, Class<?> objectClassType) {
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
	 * Easy method to delete a space for a given ID. The mutation query has no options on content or return data, so no extended method required
	 * 
	 * @param id
	 *            String id of the space to delete
	 * @return DeleteSpaceGraphQLMutation, the current object
	 * @throws WWException
	 *             if title is missing
	 * 
	 * @since 0.6.0
	 */
	public static SpaceDeleteGraphQLMutation buildDeleteSpaceMutation(String id) throws WWException {
		if ("".equals(id)) {
			throw new WWException("id is mandatory");
		}
		InputDataSenderBuilder spaceInput = new InputDataSenderBuilder(Space.DELETE_SPACE_MUTATION_NAME);
		spaceInput.addField(DeleteSpaceFields.ID, id);
		ScalarDataSenderBuilder returnObject = new ScalarDataSenderBuilder("successful");
		return new SpaceDeleteGraphQLMutation(spaceInput, returnObject);
	}

	/**
	 * Creates a deleteSpace query with pre-populated InputDataSenderBuilder and ObjectDataSenderBuilder objects
	 * 
	 * @param input
	 *            InputDataSenderBuilder containing the space deletion criteria
	 * @param returnObject
	 *            ObjectDataSenderBuilder containing the query settings
	 * 
	 * @since 0.6.0
	 */
	private SpaceDeleteGraphQLMutation(InputDataSenderBuilder inputObject, IDataSenderBuilder returnObject) {
		super(METHOD, inputObject, returnObject);
	}

}
