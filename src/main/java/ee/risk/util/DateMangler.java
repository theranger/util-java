package ee.risk.util;


import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Helps to handle dates that contain only year, month and day.
 * Uses UTC timezone by default. To use localtime, set it explicitly via timezone argument.
 *
 * @author The Ranger
 */
public class DateMangler {

	private Calendar date;
	private Date dayStart;
	private Date dayEnd;

	private DateMangler(Calendar date) {
		this.date = date;
	}

	/**
	 * Initialize with classical Java date object
	 * @param date Date to be mangled
	 */
	public DateMangler(Date date) {
		this.date = Calendar.getInstance();
		this.date.setTime(date);
		this.date.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	/**
	 * Initialize with classical Java date object and timezone
	 * @param date Date to be mangled
	 * @param timeZone String representation of timezone (see Java TimeZone class)
	 */
	public DateMangler(Date date, String timeZone) {
		this(date);
		setTimeZone(timeZone);
	}

	/**
	 * Initialize with year-month-day triplet
	 * @param day Integer value of day (starting from 1)
	 * @param month Integer value of month (starting from 1 - January)
	 * @param year Integer value of year
	 */
	public DateMangler(int day, int month, int year) {
		date = Calendar.getInstance();
		date.set(Calendar.YEAR, year);
		date.set(Calendar.MONTH, month - 1);
		date.set(Calendar.DAY_OF_MONTH, day);
		date.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	/**
	 * Initialize with year-month-day triplet and timezone
	 * @param day Integer value of day (starting from 1)
	 * @param month Integer value of month (starting from 1 - January)
	 * @param year Integer value of year
	 * @param timeZone String representation of timezone (see Java TimeZone class)
	 */
	public DateMangler(int day, int month, int year, String timeZone) {
		this(year, month, day);
		setTimeZone(timeZone);
	}

	/**
	 * Set timezone for already initialized date
	 * @param timeZone String representation of timezone (see Java TimeZone class)
	 */
	public void setTimeZone(String timeZone) {
		if(timeZone == null || timeZone.isEmpty()) return;

		TimeZone tz = TimeZone.getTimeZone(timeZone);
		date.setTimeZone(tz);
	}

	/**
	 * Get timestamp for the beginning of day
	 * @return Timestamp for the beginning of day
	 */
	public Date getBeginningOfDay() {
		if(dayStart != null) return dayStart;

		Calendar c = date;
		c.set(Calendar.HOUR_OF_DAY, c.getMinimum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, c.getMinimum(Calendar.MINUTE));
		c.set(Calendar.SECOND, c.getMinimum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getMinimum(Calendar.MILLISECOND));
		dayStart = c.getTime();

		return dayStart;
	}

	/**
	 * Get timestamp for the end of day
	 * @return Timestamp for the end of the day
	 */
	public Date getEndOfDay() {
		if(dayEnd != null) return dayEnd;

		Calendar c = date;
		c.set(Calendar.HOUR_OF_DAY, c.getMaximum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, c.getMaximum(Calendar.MINUTE));
		c.set(Calendar.SECOND, c.getMaximum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getMaximum(Calendar.MILLISECOND));
		dayEnd = c.getTime();

		return dayEnd;
	}

	/**
	 * Get string representation of the day (01, 02, ...)
	 * @return String representation of the day
	 */
	public String getDay() {
		return String.format("%02d", date.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * Get string representation of the month starting from 01 - January (01, 02, ...)
	 * @return String representation of the month
	 */
	public String getMonth() {
		return String.format("%02d", date.get(Calendar.MONTH) + 1);
	}

	/**
	 * Get string representation of the year
	 * @return String representation of the year
	 */
	public String getYear() {
		return String.format("%04d", date.get(Calendar.YEAR));
	}


	/**
	 * Get previous day
	 * @return New mangler object referring to yesterday
	 */
	public DateMangler getYesterday() {
		Calendar c = date;
		c.add(Calendar.DAY_OF_MONTH, -1);
		return new DateMangler(c);
	}

	/**
	 * Get next day
	 * @return New mangler object referring to tomorrow
	 */
	public DateMangler getTomorrow() {
		Calendar c = date;
		c.add(Calendar.DAY_OF_MONTH, 1);
		return new DateMangler(c);
	}
}
