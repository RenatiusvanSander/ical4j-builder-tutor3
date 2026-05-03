package edu.remad.ical4jbuilder.exceptions;

import edu.remad.ical4jbuilder.builders.InterchangeCalendarBuilder;


/**
 * Exception for InterchangeCalendarBuilder
 * 
 * @author edu.remad
 * @since 2026
 */
public class InterchangeCalendarBuilderException extends RuntimeException {

	/** generated serial UID */
	private static final long serialVersionUID = 3961562043013239827L;

	/**
	 * Constructor
	 */
	public InterchangeCalendarBuilderException() {
		super(InterchangeCalendarBuilder.class.getName() + " has errors");
	}

	/**
	 * Constructor
	 * 
	 * @param errorMessage erreor message of exception
	 * @param throwable {@link throwable}
	 */
	public InterchangeCalendarBuilderException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}
	
}
