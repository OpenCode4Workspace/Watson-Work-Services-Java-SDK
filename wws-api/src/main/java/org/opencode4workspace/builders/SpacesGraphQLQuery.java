package org.opencode4workspace.builders;

import java.util.Date;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Conversation.ConversationFields;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.graphql.BasicPaginationEnum;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Object for creating a Spaces query. The only attributes available are from BasicPaginationEnum.
 *
 */
public class SpacesGraphQLQuery extends BaseGraphQLQuery {

	private static final String METHOD = "getSpaces";
	private static final long serialVersionUID = 1L;
	
	/**
	 * @author Paul Withers
	 * @since 0.8.0
	 * 
	 *        <p>
	 *        Enum for filtering a Spaces query (in addition to PageInfo options). See {@link WWFieldsAttributesInterface}.
	 *        </p>
	 *        <ul>
	 *        <li>UPDATED_SINCE expects a Date after which Spaces should have been updated</li>
	 *        </ul>
	 *
	 */
	public enum SpacesAttributes implements WWFieldsAttributesInterface {
		UPDATED_SINCE("updatedSince", Date.class);

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
		private SpacesAttributes(String label, Class<?> objectClassType) {
			this.label = label;
			this.objectClassType = objectClassType;
		}

		/* (non-Javadoc)
		 * @see org.opencode4workspace.bo.WWFieldsAttributesInterface#getLabel()
		 */
		@Override
		public String getLabel() {
			return label;
		}

		/* (non-Javadoc)
		 * @see org.opencode4workspace.bo.WWFieldsAttributesInterface#getObjectClassType()
		 */
		@Override
		public Class<?> getObjectClassType() {
			return objectClassType;
		}
		
	}

	/**
	 * Easy method to create a basic Spaces query ObjectDataSenderBuilder
	 * 
	 * @return SpacesGraphQLQuery, this object
	 * @throws WWException
	 *             if the attributes are invalid
	 * 
	 * @since 0.5.0
	 */
	public static SpacesGraphQLQuery buildStandardGetSpacesQuery() throws WWException {

		ObjectDataSenderBuilder spaces = new ObjectDataSenderBuilder(Space.SPACES_QUERY_OBJECT_NAME, true);
		spaces.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		spaces.addPageInfo();
		spaces.addField(SpaceFields.ID);
		spaces.addField(SpaceFields.TITLE);
		spaces.addField(SpaceFields.DESCRIPTION);
		spaces.addField(SpaceFields.UPDATED);
		spaces.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(SpaceChildren.UPDATED_BY));
		spaces.addField(SpaceFields.CREATED);
		spaces.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(SpaceChildren.CREATED_BY));
		ObjectDataSenderBuilder members = new ObjectDataSenderBuilder(SpaceChildren.MEMBERS.getLabel(), true);
		members.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		members.addField(PersonFields.ID);
		members.addField(PersonFields.PHOTO_URL);
		members.addField(PersonFields.EMAIL);
		members.addField(PersonFields.DISPLAY_NAME);
		spaces.addChild(members);
		ObjectDataSenderBuilder conversation = new ObjectDataSenderBuilder(SpaceChildren.CONVERSATION.getLabel());
		conversation.addField(ConversationFields.ID);
		conversation.addField(ConversationFields.CREATED);
		conversation.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(ConversationChildren.CREATED_BY));
		conversation.addField(ConversationFields.UPDATED);
		conversation.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(ConversationChildren.UPDATED_BY));
		ObjectDataSenderBuilder messages = new ObjectDataSenderBuilder(ConversationChildren.MESSAGES.getLabel(), true);
		messages.addAttribute(BasicPaginationEnum.FIRST, 200);
		messages.addPageInfo();
		messages.addField(MessageFields.CONTENT_TYPE);
		messages.addField(MessageFields.CONTENT);
		messages.addField(MessageFields.CREATED);
		messages.addField(MessageFields.UPDATED);
		messages.addField(MessageFields.ANNOTATIONS);
		messages.addField(MessageFields.ID);
		messages.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(ConversationChildren.CREATED_BY));
		messages.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(ConversationChildren.UPDATED_BY));
		conversation.addChild(messages);
		spaces.addChild(conversation);
		return new SpacesGraphQLQuery(spaces);
	}

	/**
	 * Creates a Spaces query with a blank ObjectDataSenderBuilder query object
	 * 
	 * @since 0.5.0
	 */
	public SpacesGraphQLQuery() {
		super(METHOD, new ObjectDataSenderBuilder(Space.SPACES_QUERY_OBJECT_NAME, true));

	}

	/**
	 * Creates a Spaces query with a pre-populated ObjectDataSenderBuilder query object
	 * 
	 * @param query
	 *            ObjectDataSenderBuilder containing the query settings
	 * 
	 * @since 0.5.0
	 */
	public SpacesGraphQLQuery(ObjectDataSenderBuilder query) {
		super(METHOD, query);
	}

}
