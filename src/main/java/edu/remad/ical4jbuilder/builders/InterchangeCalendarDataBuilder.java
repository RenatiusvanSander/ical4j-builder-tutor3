package edu.remad.ical4jbuilder.builders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import edu.remad.ical4jbuilder.models.InterchangeCalendarData;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.property.ProdId;

/**
 * Builder for InterchangeCalendarData
 * 
 * @author edu.remad
 * @since 2026
 */
public class InterchangeCalendarDataBuilder {

	private LocalDateTime endTime;
	private LocalDateTime startTime;
	private String appointmentName;
	private String location;
	private Map<String, List<Parameter>> attendees;
	private Map<String, List<Parameter>> organizers;
	private ProdId prodId;
	private String filePath;

	/**
	 * endTime
	 * 
	 * @param endTime end time as {@link LocalDateTime}
	 * @return {@link InterchangeCalendarDataBuilder}
	 */
	public InterchangeCalendarDataBuilder endTime(LocalDateTime endTime) {
		this.endTime = endTime;

		return this;
	}

	/**
	 * startTime
	 * 
	 * @param startTime start time as {@link LocalDateTime}
	 * @return {@link InterchangeCalendarDataBuilder}
	 */
	public InterchangeCalendarDataBuilder startTime(LocalDateTime startTime) {
		this.startTime = startTime;

		return this;
	}

	/**
	 * appointmentName
	 * 
	 * @param appointmentName name of appointment
	 * @return {@link InterchangeCalendarDataBuilder}
	 */
	public InterchangeCalendarDataBuilder appointmentName(String appointmentName) {
		this.appointmentName = appointmentName;
		return this;
	}

	/**
	 * location
	 * 
	 * @param location the location can be remote or a place
	 * @return {@link InterchangeCalendarDataBuilder}
	 */
	public InterchangeCalendarDataBuilder location(String location) {
		this.location = location;

		return this;
	}

	/**
	 * attendees
	 * 
	 * @param attendees attendees are key-list-map
	 * @return {@link InterchangeCalendarDataBuilder}
	 */
	public InterchangeCalendarDataBuilder attendees(Map<String, List<Parameter>> attendees) {
		this.attendees = attendees;
		return this;
	}

	/**
	 * organizers
	 * 
	 * @param organizers list of organizers as {@link Parameter}
	 * @return {@link InterchangeCalendarDataBuilder}
	 */
	public InterchangeCalendarDataBuilder organizers(Map<String, List<Parameter>> organizers) {
		this.organizers = organizers;
		return this;
	}

	/**
	 * prodId
	 * 
	 * @param prodId productive identifier as {@link ProdId}
	 * @return {@link InterchangeCalendarDataBuilder}
	 */
	public InterchangeCalendarDataBuilder prodId(ProdId prodId) {
		this.prodId = prodId;
		return this;
	}

	/**
	 * filePath
	 * 
	 * @param filePath path of file
	 * @return {@link InterchangeCalendarDataBuilder}
	 */
	public InterchangeCalendarDataBuilder filePath(String filePath) {
		this.filePath = filePath;
		return this;
	}

	/**
	 * @return builds {@link InterchangeCalendarData}
	 */
	public InterchangeCalendarData build() {
		return new InterchangeCalendarData(endTime, startTime, appointmentName, location, attendees, organizers, prodId,
				filePath);
	}
	
}
