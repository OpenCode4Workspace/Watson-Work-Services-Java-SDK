package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.opencode4workspace.bo.Conversation.ConversationChildren;
import org.opencode4workspace.bo.Message.MessageChildren;
import org.opencode4workspace.bo.Profile;
import org.opencode4workspace.bo.Profile.PersonChildren;
import org.opencode4workspace.bo.Space.SpaceChildren;

public class EnumTypeTest {

	@Test
	public void TestPersonEnums() {
		assertEquals(SpaceChildren.CREATED_BY.getEnumClass().getName(), Profile.class.getName());
		assertEquals(SpaceChildren.UPDATED_BY.getEnumClass().getName(), Profile.class.getName());
		assertEquals(PersonChildren.CREATED_BY.getEnumClass().getName(), Profile.class.getName());
		assertEquals(PersonChildren.UPDATED_BY.getEnumClass().getName(), Profile.class.getName());
		assertEquals(ConversationChildren.CREATED_BY.getEnumClass().getName(), Profile.class.getName());
		assertEquals(ConversationChildren.UPDATED_BY.getEnumClass().getName(), Profile.class.getName());
		assertEquals(MessageChildren.CREATED_BY.getEnumClass().getName(), Profile.class.getName());
		assertEquals(MessageChildren.UPDATED_BY.getEnumClass().getName(), Profile.class.getName());
	}

}
