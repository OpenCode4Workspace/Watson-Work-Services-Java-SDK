package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.opencode4workspace.bo.Focus;
import org.opencode4workspace.bo.Focus.Lens;
import org.opencode4workspace.bo.FocusResponseContainer;
import org.opencode4workspace.json.ResultParser;

import com.google.gson.JsonObject;

public class FocusTestAnalysis {
	private static String TEXT = "Hello, I think we need to reconsider. Let's add a meeting on Tuesday 13th March. Frank, what do you think?";
	private static String PASSED_OBJECT = "{\"text\":\"Hello, I think we need to reconsider. Let's add a meeting on Tuesday 13th March. Frank, what do you think?\"}";
	private static String FOCUS_RESP = "{\"requestId\":\"11c6cc26-c9ac-4daf-86cb-3e105cb9d4b6\",\"focuses\":[{\"lens\":\"ActionRequest\",\"start\":0,\"end\":38,\"phrase\":\"Hello, I think we need to reconsider. \",\"confidence\":1.0,\"applicationId\":\"CLE\",\"lensId\":\"CLE\",\"focusVersion\":5,\"hidden\":false},{\"lens\":\"ActionRequest\",\"start\":38,\"end\":81,\"phrase\":\"Let\\u0027s add a meeting on Tuesday 13th March. \",\"confidence\":1.0,\"applicationId\":\"CLE\",\"lensId\":\"CLE\",\"focusVersion\":5,\"hidden\":false},{\"lens\":\"Question\",\"start\":81,\"end\":106,\"phrase\":\"Frank, what do you think?\",\"confidence\":1.0,\"applicationId\":\"CLE\",\"lensId\":\"CLE\",\"focusVersion\":5,\"hidden\":false}]}";
	
	@Test
	public void checkObj() {
		JsonObject jjo = new JsonObject();
		jjo.addProperty("text", TEXT);
		assertEquals(PASSED_OBJECT, jjo.toString());
	}
	
	@Test
	public void queryResponse() {
		FocusResponseContainer container = new ResultParser<FocusResponseContainer>(FocusResponseContainer.class).parse(FOCUS_RESP);
		List<Focus> focuses = container.getFocuses();
		assertEquals(3, focuses.size());
		assertEquals(1.0, focuses.get(0).getConfidence(), 0);
		assertEquals(Lens.ACTION_REQUEST, focuses.get(0).getLens());
		assertEquals(38, focuses.get(0).getEnd());

		assertEquals(1.0, focuses.get(2).getConfidence(), 0);
		assertEquals(Lens.QUESTION, focuses.get(2).getLens());
		assertEquals(106, focuses.get(2).getEnd());
		
	}

}
