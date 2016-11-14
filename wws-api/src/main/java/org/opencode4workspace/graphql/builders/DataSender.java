package org.opencode4workspace.graphql.builders;

public interface DataSender {

	public String buildQuery();

	public String buildQuery(boolean pretty);

	public Object getDataQuery();

}
