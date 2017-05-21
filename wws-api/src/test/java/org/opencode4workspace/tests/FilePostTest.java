package org.opencode4workspace.tests;

import org.junit.Test;
import org.opencode4workspace.bo.FileResponse;
import org.opencode4workspace.bo.FileResponse.UrlTypes;
import org.opencode4workspace.mocks.MockClient;

public class FilePostTest {
	private static String FILE_POST_RESPONSE = "{\"id\": \"ibm0@default@58ab8375e4b052591e45e7ca@file-7f96e0d7-5a20-4311-b33b-8dc158ed9de3\", \"name\": \"PaulWConnect13.jpg\", \"size\": 6472, \"urls\": {\"metadata\": \"https://api.watsonwork.ibm.com/files/api/v1/files/file/ibm0@default@58ab8375e4b052591e45e7ca@file-7f96e0d7-5a20-4311-b33b-8dc158ed9de3\", \"noredirect_download\": \"https://api.watsonwork.ibm.com/files/api/v1/files/file/ibm0@default@58ab8375e4b052591e45e7ca@file-7f96e0d7-5a20-4311-b33b-8dc158ed9de3/content/noredirect/PaulWConnect13.jpg\", \"redirect_download\": \"https://api.watsonwork.ibm.com/files/api/v1/files/file/ibm0@default@58ab8375e4b052591e45e7ca@file-7f96e0d7-5a20-4311-b33b-8dc158ed9de3/content/redirect/PaulWConnect13.jpg\"}, \"created\": 1495386383000, \"createdBy\": \"8bf6c84f-961c-43df-836a-85748766912f\", \"contentType\": \"image/jpeg\"}";

	@Test
	public void checkPostFileResponse() {
		FileResponse response = MockClient.parseFileResponseOutcome(FILE_POST_RESPONSE);
		assert("PaulWConnect13.jpg".equals(response.getName()));
		assert("https://api.watsonwork.ibm.com/files/api/v1/files/file/ibm0@default@58ab8375e4b052591e45e7ca@file-7f96e0d7-5a20-4311-b33b-8dc158ed9de3".equals(response.getUrls().get(UrlTypes.METADATA.getValue())));
	}
}
