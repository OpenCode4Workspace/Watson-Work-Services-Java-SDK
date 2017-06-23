package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.Test;
import org.opencode4workspace.WWException;
import org.opencode4workspace.builders.SpaceCreateGraphQLMutation;
import org.opencode4workspace.graphql.SpaceWrapper;
import org.opencode4workspace.mocks.MockClient;

public class CreateSpaceTest {
	private static final String CREATE_SPACE_MUTATION = "mutation createSpace {createSpace (input: {members: [\"8bf6c84f-961c-43df-836a-85748766912f\"] title: \"Hello app\"}) {space {id}}}";
	private static final String CREATE_SPACE_RESPONSE = "{\"data\": {\"createSpace\": {\"space\": {\"id\": \"589c9105e4b01a1ada6a65d2\"}}}}";

	@Test
	public void checkCreateSpaceOutput() throws WWException, UnsupportedEncodingException {
		ArrayList<String> members = new ArrayList<String>();
		members.add("8bf6c84f-961c-43df-836a-85748766912f");
		SpaceCreateGraphQLMutation mutation = SpaceCreateGraphQLMutation.buildCreateSpaceMutationWithSpaceTitleAndMembers("Hello app", members);
		assertEquals(CREATE_SPACE_MUTATION, mutation.returnQuery());
	}

	@Test
	public void checkCreateSpaceResponse() throws WWException {
		MockClient client = new MockClient(CREATE_SPACE_RESPONSE);
		SpaceWrapper space = client.getData().getCreateSpace();
		assertEquals(space.getId(), "589c9105e4b01a1ada6a65d2");
	}

}
