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
	}

	public void testMinDate() {
		assertEquals("Unexpected minimum date value", dateMangler.getBeginningOfDay(), minDate.getTime());
	}

	public void testMaxDate() {
		assertEquals("Unexpected maximum date value", dateMangler.getEndOfDay(), maxDate.getTime());
	}

	public void testStringDay() {
		assertEquals("Unexpected day received", dateMangler.getDay(), "02");
	}

	public void testStringMonth() {
		assertEquals("Unexpected month received", dateMangler.getMonth(), "06");
	}

	public void testStringYear() {
		assertEquals("Unexpected year received", dateMangler.getYear(), "2015");
	}

	public void testNumericTriplet() {
		DateMangler dateMangler = new DateMangler(2, 6, 2015);
		assertEquals("Unexpected day received", dateMangler.getDay(), "02");
		assertEquals("Unexpected month received", dateMangler.getMonth(), "06");
		assertEquals("Unexpected year received", dateMangler.getYear(), "2015");
	}

	public void testTomorrow() {
		DateMangler today = new DateMangler(30, 6, 2015);
		DateMangler tomorrow = today.getTomorrow();
		assertEquals("Unexpected day received", tomorrow.getDay(), "01");
		assertEquals("Unexpected month received", tomorrow.getMonth(), "07");
	}

	public void testYesterday() {
		DateMangler today = new DateMangler(1, 6, 2015);
		DateMangler yesterday = today.getYesterday();
		assertEquals("Unexpected day received", yesterday.getDay(), "31");
		assertEquals("Unexpected month received", yesterday.getMonth(), "05");
	}

}
