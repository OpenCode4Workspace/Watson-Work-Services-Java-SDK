package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.Test;
import org.opencode4workspace.WWException;
import org.opencode4workspace.builders.CreateSpaceGraphQLMutation;

public class CreateSpaceTest {
	private static final String CREATE_SPACE_MUTATION = "mutation createSpace {createSpace (input: {members: [\"8bf6c84f-961c-43df-836a-85748766912f\"] title: \"Hello app\"}) {space {id}}}";

	@Test
	public void checkCreateSpaceOutput() throws WWException, UnsupportedEncodingException {
		ArrayList<String> members = new ArrayList<String>();
		members.add("8bf6c84f-961c-43df-836a-85748766912f");
		CreateSpaceGraphQLMutation mutation = CreateSpaceGraphQLMutation.buildCreateSpaceMutationWithSpaceTitle("Hello app", members);
		assertEquals(CREATE_SPACE_MUTATION, mutation.returnQuery());

	}

}
