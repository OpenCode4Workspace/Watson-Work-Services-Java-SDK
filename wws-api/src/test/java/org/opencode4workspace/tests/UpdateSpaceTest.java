package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.opencode4workspace.WWException;
import org.opencode4workspace.builders.ScalarDataSenderBuilder;
import org.opencode4workspace.builders.SpaceUpdateGraphQLMutation;

public class UpdateSpaceTest {
	private static final String UPDATE_SPACE_TITLE = "mutation updateSpace {updateSpace (input: {id: \"589390cfe4b0f86a34bbf4ed\" title: \"Hello New World\"}) {space {title}}}";
	private static final String UPDATE_SPACE_MEMBER = "mutation updateSpace {updateSpace (input: {members: [\"de81bc5d-70fa-4bdb-bab7-29f4df10ddf3z\"] id: \"589390cfe4b0f86a34bbf4ed\" memberOperation: ADD}) {memberIdsChanged}}";
	private static final String UPDATE_SPACE_MEMBER_AND_TITLE = "mutation updateSpace {updateSpace (input: {members: [\"de81bc5d-70fa-4bdb-bab7-29f4df10ddf3z\"] id: \"589390cfe4b0f86a34bbf4ed\" title: \"Hello New World\" memberOperation: ADD}) {space {title} memberIdsChanged}}";

	@Test
	public void checkUpdateTitleOutput() throws WWException {
		SpaceUpdateGraphQLMutation mutation = SpaceUpdateGraphQLMutation.buildUpdateSpaceMutationChangeSpaceTitle("589390cfe4b0f86a34bbf4ed", "Hello New World");
		assertEquals(UPDATE_SPACE_TITLE, mutation.returnQuery());
	}

	@Test
	public void checkUpdateMemberOutput() throws WWException {
		ScalarDataSenderBuilder memberObj = new ScalarDataSenderBuilder(SpaceUpdateGraphQLMutation.MEMBER_IDS_CHANGED_FIELD);
		assertEquals("memberIdsChanged", memberObj.build());

		ArrayList<String> members = new ArrayList<String>();
		members.add("de81bc5d-70fa-4bdb-bab7-29f4df10ddf3z");
		SpaceUpdateGraphQLMutation mutation = SpaceUpdateGraphQLMutation.buildUpdateSpaceMutationChangeMembers("589390cfe4b0f86a34bbf4ed", members, true);
		assertEquals(UPDATE_SPACE_MEMBER, mutation.returnQuery());
	}

	@Test
	public void checkUpdateMemberAndTitleOutput() throws WWException {
		ScalarDataSenderBuilder memberObj = new ScalarDataSenderBuilder(SpaceUpdateGraphQLMutation.MEMBER_IDS_CHANGED_FIELD);
		assertEquals("memberIdsChanged", memberObj.build());

		ArrayList<String> members = new ArrayList<String>();
		members.add("de81bc5d-70fa-4bdb-bab7-29f4df10ddf3z");
		SpaceUpdateGraphQLMutation mutation = SpaceUpdateGraphQLMutation.buildUpdateSpaceMutationChangeTitleAndMembers("589390cfe4b0f86a34bbf4ed", "Hello New World", members, true);
		assertEquals(UPDATE_SPACE_MEMBER_AND_TITLE, mutation.returnQuery());
	}
}
