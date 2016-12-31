package org.opencode4workspace.endpoints;

import java.io.Serializable;
import java.util.List;

import org.opencode4workspace.bo.Annotation;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * 
 *        Message to post to WWS
 *
 */
public class AppMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String type = "appMessage";
	private int version;
	private List<? extends Annotation> annotations;

	/**
	 * @return String type, "appMessage" by default
	 * 
	 * @since 0.5.0
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            String type
	 * 
	 * @since 0.5.0
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param i
	 *            int version of the message
	 * 
	 * @since 0.5.0
	 */
	public void setVersion(int i) {
		version = 1;
	}

	/**
	 * @return int version of the message
	 * 
	 * @since 0.5.0
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @return List of annotations of the message
	 * 
	 * @since 0.5.0
	 */
	public List<? extends Annotation> getAnnotations() {
		return annotations;
	}

	/**
	 * @param annotations
	 *            List of Annotations for the message
	 * 
	 * @since 0.5.0
	 */
	public void setAnnotations(List<? extends Annotation> annotations) {
		this.annotations = annotations;
	}

}
