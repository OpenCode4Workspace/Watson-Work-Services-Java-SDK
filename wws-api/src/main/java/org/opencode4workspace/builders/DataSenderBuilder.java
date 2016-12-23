package org.opencode4workspace.builders;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        DataSender interface, with basic methods
 *
 */
public interface DataSenderBuilder {

	/**
	 * Builds the JSON stream to send. Calls {@link #build(boolean)}, passing false
	 * 
	 * @return String, JSON data
	 * 
	 * @since 0.5.0
	 */
	public String build();

	/**
	 * Builds the JSON stream to send, optionally adding pretty styling
	 * 
	 * @param pretty
	 *            boolean whether or not to output in a more readable format
	 * @return String, JSON data
	 * 
	 * @since 0.5.0
	 */
	public String build(boolean pretty);

}
