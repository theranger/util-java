package ee.risk.util;

import junit.framework.TestCase;

import java.util.Calendar;

/**
 * This test ensures that DateMangler works correctly
 *
 * @author The Ranger
 */
public class TestDateMangler extends TestCase {

	private Calendar date;
	private Calendar maxDate;
	private Calendar minDate;
	private DateMangler dateMangler;

	@Override
	public void setUp() {
		date = Calendar.getInstance();
		date.set(Calendar.YEAR, 2015);
		date.set(Calendar.MONTH, 5);
		date.set(Calendar.DAY_OF_MONTH, 2);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 40);

		maxDate = Calendar.getInstance();
		maxDate.setTime(date.getTime());
		maxDate.set(Calendar.HOUR_OF_DAY, 23);
		maxDate.set(Calendar.MINUTE, 59);
		maxDate.set(Calendar.SECOND, 59);
		maxDate.set(Calendar.MILLISECOND, 999);

		minDate = date;
		minDate.set(Calendar.HOUR_OF_DAY, 0);
		minDate.set(Calendar.MINUTE, 0);
		minDate.set(Calendar.SECOND, 0);
		minDate.set(Calendar.MILLISECOND, 0);

		dateMangler = new DateMangler(date.getTime());
		dateMangler.setTimeZone("Europe/Tallinn");
	}

	public void testMinDate() {
		assertEquals("Unexpected minimum date value", minDate.getTime(), dateMangler.getBeginningOfDay());
	}

	public void testMaxDate() {
		assertEquals("Unexpected maximum date value", maxDate.getTime(), dateMangler.getEndOfDay());
	}

	public void testStringDay() {
		assertEquals("Unexpected day received", "02", dateMangler.getDay());
	}

	public void testStringMonth() {
		assertEquals("Unexpected month received", "06", dateMangler.getMonth());
	}

	public void testStringYear() {
		assertEquals("Unexpected year received", "2015", dateMangler.getYear());
	}

	public void testNumericTriplet() {
		DateMangler dateMangler = new DateMangler(2, 6, 2015);
		assertEquals("Unexpected day received", "02", dateMangler.getDay());
		assertEquals("Unexpected month received", "06", dateMangler.getMonth());
		assertEquals("Unexpected year received","2015", dateMangler.getYear());
	}

	public void testTomorrow() {
		DateMangler today = new DateMangler(30, 6, 2015);
		DateMangler tomorrow = today.getTomorrow();
		assertEquals("Unexpected day received", "01", tomorrow.getDay());
		assertEquals("Unexpected month received","07", tomorrow.getMonth());
	}

	public void testYesterday() {
		DateMangler today = new DateMangler(1, 6, 2015);
		DateMangler yesterday = today.getYesterday();
		assertEquals("Unexpected day received", "31", yesterday.getDay());
		assertEquals("Unexpected month received", "05", yesterday.getMonth());
	}

}
