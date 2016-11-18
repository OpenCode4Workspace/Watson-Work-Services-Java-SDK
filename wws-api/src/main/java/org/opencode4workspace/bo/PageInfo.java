package org.opencode4workspace.bo;

import java.io.Serializable;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        Page Information object passed from WWS for e.g. Spaces, used for pagination in subsequent queries
 *
 */
public class PageInfo implements Serializable {

	/**
	 * @author Paul Withers
	 * @since 0.5.0
	 * 
	 *        Enum for scalar properties of PageInfo. See {@linkplain WWFieldsAttributesInterface}
	 *
	 */
	public enum PageInfoFields implements WWFieldsAttributesInterface {
		START_CURSOR("startCursor", String.class), END_CURSOR("endCursor", String.class), HAS_NEXT_PAGE("hasNextPage", Boolean.class), HAS_PREVIOUS_PAGE("hasPreviousPage", Boolean.class);

		private String label;
		private Class<?> objectClassType;

		/**
		 * Constructor
		 * 
		 * @param label
		 *            String, WWS variable
		 * @param objectClassType
		 *            Class<?> Java data type expected for passing across
		 */
		private PageInfoFields(String label, Class<?> objectClassType) {
			this.label = label;
			this.objectClassType = objectClassType;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.opencode4workspace.bo.WWFieldsAttributesInterface#getLabel()
		 */
		@Override
		public String getLabel() {
			return label;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.opencode4workspace.bo.WWFieldsAttributesInterface#getObjectClassType()
		 */
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

	/**
	 * Constructor
	 */
	public PageInfo() {

	}

	/**
	 * @return String, cursor relating to the Object at the start of this pagination
	 */
	public String getStartCursor() {
		return startCursor;
	}

	/**
	 * @param startCursor
	 *            String, cursor relating to the Object at the start of this pagination
	 */
	public void setStartCursor(String startCursor) {
		this.startCursor = startCursor;
	}

	/**
	 * @return String, cursor relating to the Object at the end of this pagination
	 */
	public String getEndCursor() {
		return endCursor;
	}

	/**
	 * @param endCursor
	 *            String, cursor relating to the Object at the start of this pagination
	 */
	public void setEndCursor(String endCursor) {
		this.endCursor = endCursor;
	}

	/**
	 * @return boolean, whether there are any more elements after this page
	 */
	public boolean isHasNextPage() {
		return hasNextPage;
	}

	/**
	 * @param hasNextPage
	 *            boolean, whether there are any more elements after this page
	 */
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	/**
	 * @return boolean, whether there are any more elements before this page
	 */
	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	/**
	 * @param hasPreviousPage
	 *            boolean, whether there are any more elements before this page
	 */
	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

}
