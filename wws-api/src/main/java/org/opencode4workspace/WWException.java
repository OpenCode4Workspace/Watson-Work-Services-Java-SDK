package org.opencode4workspace;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Watson Workspace Exception class
 *
 */
public class WWException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 *            String, error message to log
	 */
	public WWException(String message) {
		super(message);
	}

	/**
	 * @param e
	 *            Exception to log
	 */
	public WWException(Exception e) {
		super(e);
	}

}
