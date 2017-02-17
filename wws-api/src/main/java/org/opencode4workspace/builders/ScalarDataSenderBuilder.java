package org.opencode4workspace.builders;

import java.io.Serializable;

/**
 * @author Paul Withers
 * @since 0.6.0
 * 
 *        IDataSenderBuilder for use when only a scalar field needs to be sent, e.g. for deleteSpace mutation, where return query is just "{successful}"
 */
public class ScalarDataSenderBuilder implements Serializable, IDataSenderBuilder {

	private static final long serialVersionUID = 1L;
	private String fieldName;

	@Override
	public String build() {
		return build(false);
	}

	@Override
	public String build(boolean pretty) {
		return fieldName;
	}

	public ScalarDataSenderBuilder(String fieldName) {
		this.fieldName = fieldName;
	}

}
