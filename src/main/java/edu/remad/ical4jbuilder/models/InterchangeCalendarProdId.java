package edu.remad.ical4jbuilder.models;

import edu.remad.ical4jbuilder.constants.InterchangeCalendarConstants;
import net.fortuna.ical4j.model.property.ProdId;

/**
 * Model for ProdId of Interchange Calendar
 * 
 * @author edu.remad
 * @since 2026
 */
public class InterchangeCalendarProdId extends ProdId {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor creates instance of {@link InterchangeCalendarProdId} with
	 * prefilled production id
	 */
	public InterchangeCalendarProdId() {
		super(InterchangeCalendarConstants.PRODUCTION_ID_KEY);
	}

	/**
	 * Constructor
	 * 
	 * @param poductionId production identifier to set
	 */
	public InterchangeCalendarProdId(String poductionId) {
		super(poductionId);
	}
	
}
