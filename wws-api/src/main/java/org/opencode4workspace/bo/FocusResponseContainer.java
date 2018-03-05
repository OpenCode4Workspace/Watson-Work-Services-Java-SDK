package org.opencode4workspace.bo;

import java.io.Serializable;
import java.util.List;

public class FocusResponseContainer implements Serializable {
	private static final long serialVersionUID = 1L;
	private String requestId;
	private List<Focus> focuses;

	/**
	 * @return String unique id for the focus request
	 * 
	 * @since 0.8.0
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId String unique id for the focus request
	 * 
	 * @since 0.8.0
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return List of Focus objects identified for the passed string
	 * 
	 * @since 0.8.0
	 */
	public List<Focus> getFocuses() {
		return focuses;
	}

	/**
	 * @param focuses List of Focus objects identified for the passed string
	 * 
	 * @since 0.8.0
	 */
	public void setFocuses(List<Focus> focuses) {
		this.focuses = focuses;
	}

}
