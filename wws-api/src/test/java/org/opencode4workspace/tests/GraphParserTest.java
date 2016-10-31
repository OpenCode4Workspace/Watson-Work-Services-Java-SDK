package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.opencode4workspace.bo.Conversation;
import org.opencode4workspace.bo.Space;
import org.opencode4workspace.graphql.ConversationWrapper;
import org.opencode4workspace.graphql.DataContainer;
import org.opencode4workspace.graphql.GraphResultContainer;
import org.opencode4workspace.graphql.SpaceWrapper;
import org.opencode4workspace.graphql.SpacesContainer;
import org.opencode4workspace.json.ResultParser;

public class GraphParserTest {
	private final static String GET_SPACES_FILE = "/getSpacesTest.json";

	@Test
	public void testGetSpace() throws IOException {
		String content = grabContentFromFile(GET_SPACES_FILE);
		assertNotNull(content);
		GraphResultContainer container = new ResultParser<GraphResultContainer>(GraphResultContainer.class).parse(content);
		assertNotNull(container);
		assertNotNull(container.getData());
		DataContainer dataContainer = container.getData();
		SpacesContainer spacesContainer = dataContainer.getSpaces();
		assertEquals(1, spacesContainer.getItems().size());
		Space space = spacesContainer.getItems().get(0);
		assertTrue(space instanceof SpaceWrapper);
		assertEquals(2, space.getMembers().size());
		Conversation conv = space.getConversation();
		assertTrue(conv instanceof ConversationWrapper);
		assertEquals(3, conv.getMessages().size());
	}

	private String grabContentFromFile(String fileName) throws IOException {
		return IOUtils.toString(getClass().getResourceAsStream(fileName), "UTF-8");
	}
}