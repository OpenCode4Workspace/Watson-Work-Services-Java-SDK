package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.opencode4workspace.WWException;
import org.opencode4workspace.builders.SpaceDeleteGraphQLMutation;

public class DeleteSpaceTest {
	private static final String DELETE_SPACE_MUTATION = "mutation deleteSpace {deleteSpace (input: {id: \"58938f40e4b0f86a34bbf40f\"}) {successful}}";

	@Test
	public void deleteSpace() throws WWException {
		SpaceDeleteGraphQLMutation mutation = SpaceDeleteGraphQLMutation.buildDeleteSpaceMutationString("58938f40e4b0f86a34bbf40f");
		assertEquals(DELETE_SPACE_MUTATION, mutation.returnQuery());
	}

}
