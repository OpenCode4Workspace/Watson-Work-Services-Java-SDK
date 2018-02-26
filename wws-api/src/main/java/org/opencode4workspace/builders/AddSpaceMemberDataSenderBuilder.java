package org.opencode4workspace.builders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Paul Withers
 * @since 0.8.0
 * 
 * DataSenderBuilder for adding a member to a Space
 *
 */
public class AddSpaceMemberDataSenderBuilder implements Serializable, IDataSenderBuilder {
	private static final long serialVersionUID = 1L;
	private List<SpaceMemberObject> members;
	
	public class SpaceMemberObject implements Serializable {
		private static final long serialVersionUID = 1L;
		private String id;
		private String permissions;
		
		public SpaceMemberObject(String id, String permissions) {
			this.id = id;
			this.permissions = permissions;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPermissions() {
			return permissions;
		}

		public void setPermissions(String permissions) {
			this.permissions = permissions;
		}
		
	}
	
	public AddSpaceMemberDataSenderBuilder(SpaceMemberObject member) {
		members = new ArrayList<AddSpaceMemberDataSenderBuilder.SpaceMemberObject>();
		members.add(member);
	}
	
	public AddSpaceMemberDataSenderBuilder(List<SpaceMemberObject> members) {
		this.members = members;
	}

	public List<SpaceMemberObject> getMembers() {
		return members;
	}

	public void setMembers(List<SpaceMemberObject> members) {
		this.members = members;
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IDataSenderBuilder#build()
	 */
	@Override
	public String build() {
		return build(false);
	}

	/* (non-Javadoc)
	 * @see org.opencode4workspace.builders.IDataSenderBuilder#build(boolean)
	 */
	@Override
	public String build(boolean pretty) {
		boolean isFirst = true;
		StringBuilder s = new StringBuilder();
		s.append("members: [");
		if (pretty) {
			s.append("\n\r");
		}
		
		for (SpaceMemberObject member : members) {
			if (!isFirst) {
				s.append(",");
				if (pretty) {
					s.append("\n\r");
				}
			}
			s.append("{");
			if (pretty) {
				s.append("\n\r");
			}
			s.append("id: ");
			s.append(member.getId());
			s.append(",");
			if (pretty) {
				s.append("\n\r");
			}
			s.append("permissions:");
			s.append(member.getPermissions());
			s.append("}");
		}
		
		s.append("]");
		return s.toString();
	}

}
