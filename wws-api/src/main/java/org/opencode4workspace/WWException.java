package org.opencode4workspace;

public class WWException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WWException(String message) {
		super(message);
	}

	public WWException(Exception e) {
		super(e);
	}

}
