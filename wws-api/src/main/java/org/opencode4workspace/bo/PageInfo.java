package org.opencode4workspace.bo;

import java.io.Serializable;

public class PageInfo implements Serializable {

	// TODO: Is there a smarter way of building this enum. If the developer just wants to retrieve all fields, the FieldDataBringer can use reflection to retrieve all relevant properties. But when
	// they just want to call FieldDataBringer.addField(), we want to make it easy for them to select the fields defined here. An enum does that, but means duplication of setup.
	public enum PageInfoFields implements WWFieldsAttributesInterface {
		START_CURSOR("startCursor"), END_CURSOR("endCursor"), HAS_NEXT_PAGE("hasNextPage"), HAS_PREVIOUS_PAGE("hasPreviousPage");

		private String label;

		private PageInfoFields(String label) {
			this.label = label;
		}

		@Override
		public String getLabel() {
			return label;
		}
	}

	private static final long serialVersionUID = 1L;
	public static final String LABEL = "pageInfo";
	private String startCursor;
	private String endCursor;
	private boolean hasNextPage;
	private boolean hasPreviousPage;

	public PageInfo() {

	}

	public String getStartCursor() {
		return startCursor;
	}

	public void setStartCursor(String startCursor) {
		this.startCursor = startCursor;
	}

	public String getEndCursor() {
		return endCursor;
	}

	public void setEndCursor(String lastCursor) {
		this.endCursor = lastCursor;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

}
