package org.opencode4workspace.builders;

import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Conversation.ConversationFields;
import org.opencode4workspace.bo.Message;
import org.opencode4workspace.bo.Message.MessageFields;
import org.opencode4workspace.bo.Person.PersonFields;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.bo.Space.SpaceChildren;
import org.opencode4workspace.bo.Space.SpaceFields;
import org.opencode4workspace.bo.WWFieldsAttributesInterface;
import org.opencode4workspace.graphql.BasicPaginationEnum;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Object for querying a Space
 *
 */
public class SpaceGraphQLQuery extends BaseGraphQLQuery {
	private static final String METHOD = "getSpace";
	private static final long serialVersionUID = 1L;

	/**
	 * @author Paul Withers
	 * @since 0.5.0
	 * 
	 *        <p>
	 *        Enum for filtering a Space. See {@link WWFieldsAttributesInterface}.
	 *        </p>
	 *        <ul>
	 *        <li>ID expects a String ID for the Space</li>
	 *        </ul>
	 *
	 */
	public enum SpaceAttributes implements WWFieldsAttributesInterface {
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
		private SpaceAttributes(String label, Class<?> objectClassType) {
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
	 * East method to create a basic Space query ObjectDataSenderBuilder for the relevant Space, filtered on id
	 * 
	 * @param spaceId
	 *            String id for the Space to return
	 * @return SpaceGraphQLQuery, the current object
	 * @throws WWException
	 *             if spaceId is missing
	 * 
	 * @since 0.5.0
	 */
	public static SpaceGraphQLQuery buildSpaceGraphQueryWithSpaceId(String spaceId) throws WWException {
		if ("".equals(spaceId)) {
			throw new WWException("messageId is mandatory");
		}
		ObjectDataSenderBuilder query = new ObjectDataSenderBuilder();
		query.setObjectName(Space.ONE_SPACE_QUERY_OBJECT_NAME);
		query.addAttribute(SpaceAttributes.ID, spaceId);
		query.addField(SpaceFields.ID);
		query.addField(SpaceFields.TITLE);
		query.addField(SpaceFields.DESCRIPTION);
		query.addField(SpaceFields.UPDATED);
		query.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(SpaceChildren.UPDATED_BY));
		query.addField(SpaceFields.CREATED);
		query.addChild(new BasicCreatedByUpdatedByDataSenderBuilder(SpaceChildren.CREATED_BY));
		ObjectDataSenderBuilder members = new ObjectDataSenderBuilder(SpaceChildren.MEMBERS.getLabel(), true);
		members.addAttribute(BasicPaginationEnum.FIRST.getLabel(), 100);
		members.addField(PersonFields.ID);
		members.addField(PersonFields.PHOTO_URL);
		members.addField(PersonFields.EMAIL);
		members.addField(PersonFields.DISPLAY_NAME);
		query.addChild(members);
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
		query.addChild(conversation);
		return new SpaceGraphQLQuery(query);
	}

	/**
	 * Creates a Space query with a blank ObjectDataSenderBuilder query object
	 * 
	 * @since 0.5.0
	 */
	public SpaceGraphQLQuery() {
		super(METHOD, new ObjectDataSenderBuilder(Message.ONE_MESSAGE_QUERY_OBJECT_NAME));
	}

	/**
	 * Creates a Space query with a pre-populated ObjectDataSenderBuilder query object
	 * 
	 * @param query
	 *            ObjectDataSenderBuilder containing the query settings
	 * 
	 * @since 0.5.0
	 */
	public SpaceGraphQLQuery(ObjectDataSenderBuilder query) {
		super(METHOD, query);
	}

}
