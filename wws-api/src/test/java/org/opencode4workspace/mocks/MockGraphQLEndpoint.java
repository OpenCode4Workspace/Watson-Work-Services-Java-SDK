package org.opencode4workspace.mocks;

import org.opencode4workspace.IWWClient;
import org.opencode4workspace.WWException;
import org.opencode4workspace.endpoints.AbstractWWGraphQLEndpoint;

public class MockGraphQLEndpoint extends AbstractWWGraphQLEndpoint {

	public MockGraphQLEndpoint(IWWClient client) {
		super(client);
	}
	
	@Override
	public void executeRequest() throws WWException {
		throw new UnsupportedOperationException("Method not allowed in mocks");
	}

}
