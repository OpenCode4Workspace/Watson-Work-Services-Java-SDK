/**
 * 
 */
package org.opencode4workspace.bo;

import org.opencode4workspace.graphql.ConversationWrapper;
import org.opencode4workspace.graphql.CreateSpaceContainer;
import org.opencode4workspace.graphql.DeleteSpaceContainer;
import org.opencode4workspace.graphql.MembersContainer;
import org.opencode4workspace.graphql.SpaceWrapper;
import org.opencode4workspace.graphql.SpacesContainer;
import org.opencode4workspace.graphql.UpdateSpaceContainer;
import org.opencode4workspace.json.ResultParser;

/**
 * Enum to hold class names to cast aliases to when building a query and classes to convert the corresponding JSON response to
 * 
 * @author Paul Withers
 * @since 0.7.0
 *
 */
public enum WWQueryResponseObjectTypes implements WWQueryResponseObjectInterface {
	SPACES("spaces", SpacesContainer.class), SPACE("space", SpaceWrapper.class), PERSON("person", Person.class), ME("me", Person.class), 
	CONVERSATION("conversation", ConversationWrapper.class), MEMBERS("people", MembersContainer.class), MESSAGE("message", Message.class), 
	CREATE_SPACE("createSpace", CreateSpaceContainer.class), DELETE_SPACE("deleteSpace", DeleteSpaceContainer.class), UPDATE_SPACE("updateSpace", UpdateSpaceContainer.class);
	
	private String queryObjectType;
	private Class<?> returnClass;
	
	private WWQueryResponseObjectTypes(String queryObjectType, Class<?> returnClass) {
		this.queryObjectType = queryObjectType;
		this.returnClass = returnClass;
	}

	@Override
	public String getQueryObjectType() {
		return queryObjectType;
	}

	@Override
	public Class<?> getReturnClass() {
		return returnClass;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object parse(String jsonString) {
		ResultParser parser = new ResultParser(returnClass);
		return parser.parse(jsonString);
	}
	
	

}
