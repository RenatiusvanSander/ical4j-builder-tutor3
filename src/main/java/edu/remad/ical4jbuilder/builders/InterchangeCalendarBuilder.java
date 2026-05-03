package edu.remad.ical4jbuilder.builders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.remad.ical4jbuilder.exceptions.InterchangeCalendarBuilderException;
import edu.remad.ical4jbuilder.utilities.InterchangeCalendarUtilities;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.validate.ValidationException;

/**
 * Builder for Interchange Calendar
 * 
 * @author edu.remad
 * @since 2026
 */
public class InterchangeCalendarBuilder {

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private String appointmentName;

	private Map<String, List<Parameter>> attendees;

	private Map<String, List<Parameter>> organizers;

	private Property prodId;

	private Location location;

	/**
	 * Sets startTime
	 * 
	 * @param startTime start time to set as {@link LocalDateTime}
	 * @return {@link InterchangeCalendarBuilder}
	 */
	public InterchangeCalendarBuilder setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;

		return this;
	}

	/**
	 * Sets endTime
	 * 
	 * @param endTime end time to set as {@link LocalDateTime}
	 * @return {@link InterchangeCalendarBuilder}
	 */
	public InterchangeCalendarBuilder setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;

		return this;
	}

	/**
	 * Sets appointment name
	 * 
	 * @param appointmentName appointment name to set
	 * @return {@link InterchangeCalendarBuilder}
	 */
	public InterchangeCalendarBuilder setAppointmentName(String appointmentName) {
		this.appointmentName = appointmentName;

		return this;
	}

	/**
	 * Sets attendees
	 * 
	 * @param attendees a key-list-map of attendees
	 * @return {@link InterchangeCalendarBuilder}
	 */
	public InterchangeCalendarBuilder setAttendees(Map<String, List<Parameter>> attendees) {
		this.attendees = attendees;

		return this;
	}

	/**
	 * Sets organizers
	 * 
	 * @param organizers a key-list-map o organizers
	 * @return {@link InterchangeCalendarBuilder}
	 */
	public InterchangeCalendarBuilder setOrganizers(Map<String, List<Parameter>> organizers) {
		this.organizers = organizers;

		return this;
	}

	/**
	 * Sets productive identifier
	 * 
	 * @param prodId productive identifier as {@link Property}
	 * @return {@link InterchangeCalendarBuilder}
	 */
	public InterchangeCalendarBuilder setProdId(Property prodId) {
		this.prodId = prodId;

		return this;
	}

	/**
	 * Sets location
	 * 
	 * @param location place as {@link Location}
	 * @return {@link InterchangeCalendarBuilder}
	 */
	public InterchangeCalendarBuilder setLocation(Location location) {
		this.location = location;

		return this;
	}

	/**
	 * @return build ics file as byte array
	 */
	public byte[] build() {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			VEvent event = new VEvent(startTime, endTime, appointmentName);
			event.add(InterchangeCalendarUtilities.generateUid());
			addAttendees(event);
			addOrganizers(event);
			event.add(location);
			event.add(new Method(Method.VALUE_REQUEST));
			event.add(new Status(Status.VALUE_CONFIRMED));

			Calendar icsCalendar = new Calendar();
			icsCalendar.add(prodId);
			icsCalendar.add(event);
			CalendarOutputter outputter = new CalendarOutputter();
			outputter.output(icsCalendar, out);

			return out.toByteArray();
		} catch (ValidationException | IOException e) {
			throw new InterchangeCalendarBuilderException(e.getMessage(), e);
		}
	}

	private void addOrganizers(VEvent event) {
		for (Entry<String, List<Parameter>> organizerEntry : organizers.entrySet()) {
			URI uri = URI.create(organizerEntry.getKey());
			Organizer organizer = new Organizer(uri);

			for (Parameter parameter : organizerEntry.getValue()) {
				organizer.add(parameter);
			}

			event.add(organizer);
		}
	}

	private void addAttendees(VEvent event) {
		for (Entry<String, List<Parameter>> attendeeEntry : attendees.entrySet()) {
			URI uri = URI.create(attendeeEntry.getKey());
			Attendee attendee = new Attendee(uri);

			for (Parameter parameter : attendeeEntry.getValue()) {
				attendee.add(parameter);
			}

			event.add(attendee);
		}
	}

}
