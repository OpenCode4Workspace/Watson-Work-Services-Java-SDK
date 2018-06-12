package org.opencode4workspace.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.opencode4workspace.bo.Annotation;
import org.opencode4workspace.graphql.MessagesContainer;
import org.opencode4workspace.json.ResultParser;

public class MessageParserTest {

	@Test
	public void testMessageAndAnnotationParsing() throws IOException {
		InputStream is = MessageParserTest.class.getResourceAsStream("/messages.json");
		StringWriter writer = new StringWriter();
        IOUtils.copy(is, writer, "UTF-8");
        String theString = writer.toString();
        ResultParser<MessagesContainer> rp = new ResultParser<MessagesContainer>(MessagesContainer.class);
        MessagesContainer mc = rp.parse(theString);
        assertEquals(2, mc.getItems().size());
        List<Annotation> annotations = mc.getItems().get(0).getGenericAnnotations();
        assertNotNull(annotations);
        
	}
}
