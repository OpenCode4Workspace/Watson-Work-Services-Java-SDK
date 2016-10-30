package org.opencode4workspace.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.opencode4workspace.authentication.AppToken;
import org.opencode4workspace.json.ResultParser;

public class TokenParserTest {
	public static final String APP_TOKEN_RESULT = "{\"access_token\":\"appJWTToken\",\"token_type\":\"bearer\",\"expires_in\":43199,\"scope\":\"read write\",\"id\":\"appID\",\"jti\":\"jwtTokenID\"}";
	public static final String PEOPLE_TOKEN_RESULT = "{\"access_token\":\"personJWTToken\",\"token_type\":\"bearer\",\"refresh_token\":\"personRefreshToken\",\"expires_in\":43199,\"scope\":\"read write\",\"id\":\"userID\",\"displayName\":\"Max Muster\",\"providerId\":\"BLUE_ID_AUTH_PROVIDER\",\"jti\":\"personJWTTokenID\"}";

	@Test
	public void testParseAppToken() {
		AppToken appToken =  new ResultParser<AppToken>(AppToken.class).parse(APP_TOKEN_RESULT);
		assertNotNull(appToken);
		assertEquals("appJWTToken", appToken.getAccess_Token());
		assertEquals("bearer", appToken.getToken_Type());
		assertEquals(43199, appToken.getExpires_In());
		assertEquals("read write", appToken.getScope());
		assertEquals("appID", appToken.getId());
		assertEquals("jwtTokenID", appToken.getJti());
	}
}
