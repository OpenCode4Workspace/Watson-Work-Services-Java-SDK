package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.opencode4workspace.WWException;
import org.opencode4workspace.bo.Annotation;
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
	public void testGetSpace() throws IOException, WWException {
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
		assertEquals(5, conv.getMessages().get(2).getAnnotations().size());
	}

	private String grabContentFromFile(String fileName) throws IOException {
		return IOUtils.toString(getClass().getResourceAsStream(fileName), "UTF-8");
	}

	@Test
	public void testGrabAnnotations() throws IOException, WWException {
		String content = grabContentFromFile(GET_SPACES_FILE);
		assertNotNull(content);
		GraphResultContainer container = new ResultParser<GraphResultContainer>(GraphResultContainer.class).parse(content);
		assertNotNull(container);
		assertNotNull(container.getData());
		DataContainer dataContainer = container.getData();
		SpacesContainer spacesContainer = dataContainer.getSpaces();
		assertEquals(1, spacesContainer.getItems().size());
		Space space = spacesContainer.getItems().get(0);
		List<Annotation> annoList = space.getConversation().getMessages().get(2).getGenericAnnotations();
		assertEquals(1, annoList.size());
		assertEquals("Ready to Code?", annoList.get(0).getText());
		assertNotNull(annoList.get(0).getActor());
	}
}