package edu.remad.ical4jbuilder.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.property.ProdId;

/**
 * Model for Data of Interchange Calendar
 * 
 * @author edu.remad
 * @since 2026
 */
public class InterchangeCalendarData {

	private LocalDateTime endTime;
	private LocalDateTime startTime;
	private String appointmentName;
	private String location;
	private Map<String, List<Parameter>> attendees;
	private Map<String, List<Parameter>> organizers;
	private ProdId prodId;
	private String filePath;

	/**
	 * Constructor
	 */
	public InterchangeCalendarData() {
	}

	/**
	 * Constructor
	 * 
	 * @param endTime
	 * @param startTime
	 * @param appointmentName
	 * @param location
	 * @param attendees
	 * @param organizers
	 * @param prodId
	 * @param filePath
	 */
	public InterchangeCalendarData(LocalDateTime endTime, LocalDateTime startTime, String appointmentName,
			String location, Map<String, List<Parameter>> attendees, Map<String, List<Parameter>> organizers,
			ProdId prodId, String filePath) {
		super();
		this.endTime = endTime;
		this.startTime = startTime;
		this.appointmentName = appointmentName;
		this.location = location;
		this.attendees = attendees;
		this.organizers = organizers;
		this.prodId = prodId;
		this.filePath = filePath;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTimestamp) {
		this.endTime = endTimestamp;
	}

	public void setStartTime(LocalDateTime endTimeString) {
		this.startTime = endTimeString;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setAppointmentName(String appointmentName) {
		this.appointmentName = appointmentName;
	}

	public String getAppointmentName() {
		return appointmentName;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setAttendees(Map<String, List<Parameter>> attendees) {
		this.attendees = attendees;
	}

	public Map<String, List<Parameter>> getAttendees() {
		return attendees;
	}

	public void setOrganizers(Map<String, List<Parameter>> organizers) {
		this.organizers = organizers;
	}

	public Map<String, List<Parameter>> getOrganizers() {
		return organizers;
	}

	public void setProdId(ProdId prodId) {
		this.prodId = prodId;
	}

	public ProdId getProdId() {
		return prodId;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}
}
