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
	 * 
	 * @since 0.5.0
	 */
	public WWException(String message) {
		super(message);
	}

	/**
	 * @param e
	 *            Exception to log
	 * 
	 * @since 0.5.0
	 */
	public WWException(Exception e) {
		super(e);
	}

}
