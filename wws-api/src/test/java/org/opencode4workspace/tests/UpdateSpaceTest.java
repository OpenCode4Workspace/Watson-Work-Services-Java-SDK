package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.opencode4workspace.WWException;
import org.opencode4workspace.builders.ScalarDataSenderBuilder;
import org.opencode4workspace.builders.SpaceMembersAddDataSenderBuilder.SpaceMemberObject;
import org.opencode4workspace.builders.SpaceMembersAddGraphQLMutation;
import org.opencode4workspace.builders.SpaceMembersRemoveGraphQLMutation;
import org.opencode4workspace.builders.SpaceUpdateGraphQLMutation;
import org.opencode4workspace.builders.SpaceUpdateGraphQLMutation.UpdateSpaceMemberOperation;
import org.opencode4workspace.graphql.SpaceWrapper;
import org.opencode4workspace.mocks.MockClient;

public class UpdateSpaceTest {
	private static final String UPDATE_SPACE_TITLE = "mutation updateSpace {updateSpace (input: {id: \"589390cfe4b0f86a34bbf4ed\" title: \"Hello New World\"}) {space {title}}}";
	private static final String UPDATE_SPACE_MEMBER = "mutation updateSpace {updateSpace (input: {members: [\"de81bc5d-70fa-4bdb-bab7-29f4df10ddf3z\"] id: \"589390cfe4b0f86a34bbf4ed\" memberOperation: ADD}) {memberIdsChanged}}";
	private static final String UPDATE_SPACE_MEMBER_AND_TITLE = "mutation updateSpace {updateSpace (input: {members: [\"de81bc5d-70fa-4bdb-bab7-29f4df10ddf3z\"] id: \"589390cfe4b0f86a34bbf4ed\" title: \"Hello New World\" memberOperation: ADD}) {space {title} memberIdsChanged}}";
	private static final String UPDATE_SPACE_TITLE_RESPONSE = "{\"data\":{\"updateSpace\":{\"space\":{\"title\":\"Hello World\"}}}}";
	private static final String UPDATE_SPACE_RESPONSE = "{\"data\": {\"updateSpace\": {\"memberIdsChanged\": [\"de81bc5d-70fa-4bdb-bab7-29f4df10ddf3\"],\"space\": {\"title\": \"Hello New World\"}}}}";
	private static final String REMOVE_SPACE_MEMBER = "mutation removeSpaceMembers {removeSpaceMembers (input: {spaceId: \"589390cfe4b0f86a34bbf4ed\" members: [\"de81bc5d-70fa-4bdb-bab7-29f4df10ddf3z\"]}) {memberIdsChanged}}";
	private static final String ADD_SPACE_MEMBER = "mutation addSpaceMembers {addSpaceMembers (input: {spaceId: \"589390cfe4b0f86a34bbf4ed\" members: [{\"id\": \"de81bc5d-70fa-4bdb-bab7-29f4df10ddf3z\", \"permissions\": \"MEMBER\"}]}) {memberIdsChanged}}";
	
	@Test
	public void checkUpdateTitleOutput() throws WWException {
		SpaceUpdateGraphQLMutation mutation = SpaceUpdateGraphQLMutation.buildUpdateSpaceMutationChangeTitle("589390cfe4b0f86a34bbf4ed", "Hello New World");
		assertEquals(UPDATE_SPACE_TITLE, mutation.returnQuery());
	}

	@Test
	public void checkUpdateMemberOutput() throws WWException {
		ScalarDataSenderBuilder memberObj = new ScalarDataSenderBuilder(SpaceUpdateGraphQLMutation.MEMBER_IDS_CHANGED_FIELD);
		assertEquals("memberIdsChanged", memberObj.build());

		ArrayList<String> members = new ArrayList<String>();
		members.add("de81bc5d-70fa-4bdb-bab7-29f4df10ddf3z");
		SpaceUpdateGraphQLMutation mutation = SpaceUpdateGraphQLMutation.buildUpdateSpaceMutationChangeMembers("589390cfe4b0f86a34bbf4ed", members, UpdateSpaceMemberOperation.ADD);
		assertEquals(UPDATE_SPACE_MEMBER, mutation.returnQuery());
	}

	@Test
	public void checkUpdateMemberAndTitleOutput() throws WWException {
		ScalarDataSenderBuilder memberObj = new ScalarDataSenderBuilder(SpaceUpdateGraphQLMutation.MEMBER_IDS_CHANGED_FIELD);
		assertEquals("memberIdsChanged", memberObj.build());

		ArrayList<String> members = new ArrayList<String>();
		members.add("de81bc5d-70fa-4bdb-bab7-29f4df10ddf3z");
		SpaceUpdateGraphQLMutation mutation = SpaceUpdateGraphQLMutation.buildUpdateSpaceMutationChangeTitleAndMembers("589390cfe4b0f86a34bbf4ed", "Hello New World", members,
				UpdateSpaceMemberOperation.ADD);
		assertEquals(UPDATE_SPACE_MEMBER_AND_TITLE, mutation.returnQuery());
	}

	@Test
	public void checkAddSpaceMembersOutput() throws WWException {
		ArrayList<SpaceMemberObject> members = new ArrayList<SpaceMemberObject>();
		SpaceMemberObject user1 = new SpaceMemberObject("de81bc5d-70fa-4bdb-bab7-29f4df10ddf3z", "MEMBER");
		members.add(user1);
		SpaceMembersAddGraphQLMutation mutation = SpaceMembersAddGraphQLMutation.buildAddSpaceMembersMutationChange("589390cfe4b0f86a34bbf4ed", members);
		assertEquals(ADD_SPACE_MEMBER, mutation.returnQuery());
	}

	@Test
	public void checkRemoveSpaceMembersOutput() throws WWException {
		ArrayList<String> members = new ArrayList<String>();
		members.add("de81bc5d-70fa-4bdb-bab7-29f4df10ddf3z");
		SpaceMembersRemoveGraphQLMutation mutation = SpaceMembersRemoveGraphQLMutation.buildRemoveSpaceMembersMutationChange("589390cfe4b0f86a34bbf4ed", members);
		assertEquals(REMOVE_SPACE_MEMBER, mutation.returnQuery());
	}

	@Test
	public void parseUpdateTitleResponse() throws WWException {
		MockClient client = new MockClient(UPDATE_SPACE_TITLE_RESPONSE);
		SpaceWrapper space = client.getData().getUpdateSpaceContainer_SpaceWrapper();
		assertEquals("Hello World", space.getTitle());
	}

	public void parseUpdateResponse() throws WWException {
		MockClient client = new MockClient(UPDATE_SPACE_RESPONSE);
		SpaceWrapper space = client.getData().getUpdateSpaceContainer_SpaceWrapper();
		assertEquals("Hello New World", space.getTitle());
		List<String> membersUpdated = new ArrayList<String>();
		for (String member : client.getData().getUpdateSpaceContainer_MemberIdsChanged()) {
			membersUpdated.add(member);
		}
		assert membersUpdated.size() > 0;
		assertEquals("de81bc5d-70fa-4bdb-bab7-29f4df10ddf3", membersUpdated.get(0));
	}
}
