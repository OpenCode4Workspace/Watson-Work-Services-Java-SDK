package org.opencode4workspace.bo;

import java.io.Serializable;

public class PageInfo implements Serializable {

	public enum PageInfoFields implements WWFieldsAttributesInterface {
		START_CURSOR("startCursor", String.class), END_CURSOR("endCursor", String.class), HAS_NEXT_PAGE("hasNextPage", Boolean.class), HAS_PREVIOUS_PAGE("hasPreviousPage", Boolean.class);

		private String label;
		private Class<?> objectClassType;

		private PageInfoFields(String label, Class<?> objectClassType) {
			this.label = label;
			this.objectClassType = objectClassType;
		}

		@Override
		public String getLabel() {
			return label;
		}

		@Override
		public Class<?> getObjectClassType() {
			return objectClassType;
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
