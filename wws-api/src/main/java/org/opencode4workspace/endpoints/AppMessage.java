package org.opencode4workspace.endpoints;

import java.io.Serializable;
import java.util.List;

import org.opencode4workspace.bo.Annotation;

public class AppMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type = "appMessage";
	private int version;
	private List<? extends Annotation> annotations;

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

	public List<? extends Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<? extends Annotation> annotations) {
		this.annotations = annotations;
	}
	
	
}
