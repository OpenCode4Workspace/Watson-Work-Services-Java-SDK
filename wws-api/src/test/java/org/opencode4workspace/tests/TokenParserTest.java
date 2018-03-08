package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.opencode4workspace.authentication.AppToken;
import org.opencode4workspace.authentication.AppToken.TokenScope;
import org.opencode4workspace.authentication.AppToken.TokenType;
import org.opencode4workspace.authentication.PeopleToken;
import org.opencode4workspace.json.ResultParser;

public class TokenParserTest {
	public static final String APP_TOKEN_RESULT = "{\"access_token\":\"appJWTToken\",\"token_type\":\"bearer\",\"expires_in\":43199,\"scope\":\"space_change transcript_read\",\"id\":\"appID\",\"jti\":\"jwtTokenID\"}";
	public static final String PEOPLE_TOKEN_RESULT = "{\"access_token\":\"personJWTToken\",\"token_type\":\"bearer\",\"refresh_token\":\"personRefreshToken\",\"expires_in\":43199,\"scope\":\"space_create transcript_read\",\"id\":\"userID\",\"displayName\":\"Max Muster\",\"providerId\":\"BLUE_ID_AUTH_PROVIDER\",\"jti\":\"personJWTTokenID\"}";

	@Test
	public void testParseAppToken() {
		AppToken appToken = new ResultParser<AppToken>(AppToken.class).parse(APP_TOKEN_RESULT);
		assertNotNull(appToken);
		assertEquals("appJWTToken", appToken.getAccess_Token());
		assertEquals(TokenType.BEARER, appToken.getToken_Type());
		assertEquals("bearer", appToken.getToken_TypeAsString());
		assertEquals(43199, appToken.getExpires_In());
		assertEquals(TokenScope.SPACE_CHANGE, appToken.getScope()[0]);
		assertEquals("space_change transcript_read", appToken.getScopeAsString());
		assertEquals("appID", appToken.getId());
		assertEquals("jwtTokenID", appToken.getJti());
	}

	@Test
	public void testPeopleAppToken() {
		PeopleToken peopleToken = new ResultParser<PeopleToken>(PeopleToken.class).parse(PEOPLE_TOKEN_RESULT);
		assertNotNull(peopleToken);
		assertEquals("personJWTToken", peopleToken.getAccess_Token());
		assertEquals(TokenType.BEARER, peopleToken.getToken_Type());
		assertEquals("bearer", peopleToken.getToken_TypeAsString());
		assertEquals(43199, peopleToken.getExpires_In());
		assertEquals(TokenScope.SPACE_CREATE, peopleToken.getScope()[0]);
		assertEquals(TokenScope.TRANSCRIPT_READ, peopleToken.getScope()[1]);
		assertEquals("space_create transcript_read", peopleToken.getScopeAsString());
		assertEquals("userID", peopleToken.getId());
		assertEquals("personJWTTokenID", peopleToken.getJti());
		assertEquals("personRefreshToken", peopleToken.getRefresh_Token());
		assertEquals("Max Muster", peopleToken.getDisplayName());
		assertEquals("BLUE_ID_AUTH_PROVIDER", peopleToken.getProviderId());
	}
}
