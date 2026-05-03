package edu.remad.ical4jbuilder.constants;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import net.fortuna.ical4j.util.RandomUidGenerator;

/**
 * Constants for Interchange Calendar
 * 
 * @author edu.remad
 * @since 2026
 */
public final class InterchangeCalendarConstants {

	public static final String GERMAN_TIME_PATTERN = "dd.MM.yyyy HH:mm";
	public static final String INTERNATIONAL_TIME_PATTERN = "yyyy-MM-dd HH:mm";
	public static final String PRODUCTION_ID_KEY = "-//R.Meier Freelance Nachhilfe//Tutoring 2//EN";
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(GERMAN_TIME_PATTERN,
			Locale.GERMANY);
	public static final DateTimeFormatter DATE_AND_TIME_FORMATTER = DateTimeFormatter
			.ofPattern(INTERNATIONAL_TIME_PATTERN);
	public static final RandomUidGenerator UID_GENERATOR = new RandomUidGenerator();
	public static final String MAILTO_KEY = "mailto:";
	
	public static final String  APPOINTMENT_NAME = "Ihre Nachhilfe heute um ";
	
	public static final String LOCATION = "Remote";
	
	public static final String FILE_NAME = "termin.ics";

	/**
	 * private Constructor
	 */
	private InterchangeCalendarConstants() {
		// do not instantiate
	}
	
}
