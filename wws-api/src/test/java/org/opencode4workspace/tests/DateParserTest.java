package org.opencode4workspace.tests;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Ignore;
import org.junit.Test;

public class DateParserTest {
	private static String dateTest = "2016-10-22T20:04:47.000+0100";

	@Ignore
	@Test
	public void dateToString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Date dt = new Date(116, 9, 22, 20, 4, 47);
		TimeZone tz = TimeZone.getDefault();
		df.setTimeZone(tz);
		assertEquals(df.format(dt), dateTest);
	}

}
