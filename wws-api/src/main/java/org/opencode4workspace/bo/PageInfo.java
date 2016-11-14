package org.opencode4workspace.bo;

import java.io.Serializable;

public class PageInfo implements Serializable {

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
