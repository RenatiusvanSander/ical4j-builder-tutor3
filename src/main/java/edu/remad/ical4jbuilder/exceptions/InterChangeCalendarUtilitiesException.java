package edu.remad.ical4jbuilder.exceptions;

import edu.remad.ical4jbuilder.utilities.InterchangeCalendarUtilities;

/**
 * Exception for InterChangeCalendarUtilities
 * 
 * @author edu.remad
 * @since 2026
 */
public class InterChangeCalendarUtilitiesException extends RuntimeException {

	/** generated serial UID */
	private static final long serialVersionUID = -5604209438976198340L;

	/**
	 * Constructor
	 */
	public InterChangeCalendarUtilitiesException() {
		super(InterchangeCalendarUtilities.class.getName() + " has errors");
	}

	/**
	 * Constructor
	 * 
	 * @param errorMessage message of exception
	 * @param throwable {@link Throwable}
	 */
	public InterChangeCalendarUtilitiesException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}

	/**
	 * Constructor
	 * 
	 * @param errorMessage message of exception
	 */
	public InterChangeCalendarUtilitiesException(String errorMessage) {
		super(errorMessage);
	}
	
}
