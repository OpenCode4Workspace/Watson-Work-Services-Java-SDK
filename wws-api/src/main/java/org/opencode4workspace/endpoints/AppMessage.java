package org.opencode4workspace.endpoints;

import org.opencode4workspace.bo.Message;

public class AppMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type = "appMessage";
	private int version;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVersion(int i) {
		version = 1;
	}

	public int getVersion() {
		return version;
	}
}
