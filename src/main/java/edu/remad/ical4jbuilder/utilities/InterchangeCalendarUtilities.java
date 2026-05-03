package edu.remad.ical4jbuilder.utilities;

import static net.fortuna.ical4j.model.Parameter.CN;
import static net.fortuna.ical4j.model.Parameter.EMAIL;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import edu.remad.ical4jbuilder.builders.InterchangeCalendarBuilder;
import edu.remad.ical4jbuilder.builders.InterchangeCalendarDataBuilder;
import edu.remad.ical4jbuilder.constants.InterchangeCalendarConstants;
import edu.remad.ical4jbuilder.exceptions.InterChangeCalendarUtilitiesException;
import edu.remad.ical4jbuilder.models.InterchangeCalendarData;
import edu.remad.ical4jbuilder.models.InterchangeCalendarProdId;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Email;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Uid;

/**
 * Utility for Interchange Calendar
 * 
 * @author edu.remad
 * @since 2026
 */
public final class InterchangeCalendarUtilities {

	/**
	 * private Constructor not to instantiate
	 */
	private InterchangeCalendarUtilities() {
		// do not instantiate
	}

	public static Uid generateUid() {
		return InterchangeCalendarConstants.UID_GENERATOR.generateUid();
	}

	public static InterchangeCalendarProdId createInterchangeCalendarProdId() {
		return new InterchangeCalendarProdId();
	}

	public static Map<String, List<Parameter>> createOrganizerMap(Map<String, String> organizerData) {
		if (organizerData.isEmpty()) {
			throw new InterChangeCalendarUtilitiesException("organizer data has to be populated.");
		}

		List<Parameter> parameters = createParameterList(organizerData, Role.CHAIR);
		Map<String, List<Parameter>> organizer = new HashMap<>();
		organizer.put(InterchangeCalendarConstants.MAILTO_KEY + organizerData.get(EMAIL), parameters);

		return organizer;
	}

	private static List<Parameter> createParameterList(Map<String, String> organizerData, Role role) {
		if (organizerData == null || organizerData.isEmpty() || role == null) {

			String errorMessage = null;
			if (organizerData == null) {
				errorMessage = "organizerData is null!";
			} else if (organizerData.isEmpty()) {
				errorMessage = "organizerData shall not be empty!";
			} else if (role == null) {
				errorMessage = "Role shalls not be null!";
			} else {
				errorMessage = "Please check usage of createParameterList(Map<String, String>, Role) !";
			}

			throw new InterChangeCalendarUtilitiesException(errorMessage);
		}

		List<Parameter> parameters = new ArrayList<>();

		for (Map.Entry<String, String> entry : organizerData.entrySet()) {
			switch (entry.getKey()) {
			case EMAIL: {
				parameters.add(new Email(entry.getValue()));
				break;
			}
			case CN: {
				parameters.add(new Cn(entry.getValue()));
				break;
			}
			}
		}
		parameters.add(role);

		return parameters;
	}

	public static Map<String, List<Parameter>> createAttendeeMap(Map<String, String> attendeeData) {
		if (attendeeData.isEmpty()) {
			throw new InterChangeCalendarUtilitiesException("attendee data has to be populated.");
		}

		List<Parameter> parameters = createParameterList(attendeeData, Role.REQ_PARTICIPANT);
		Map<String, List<Parameter>> attendee = new HashMap<>();
		attendee.put(InterchangeCalendarConstants.MAILTO_KEY + attendeeData.get(EMAIL), parameters);

		return attendee;
	}

	public static InterchangeCalendarData createCalendarData(TutoringAppointmentEntity appointment) {
		String name = createAppointmentName(appointment.getTutoringAppointmentStartDateTime());
		Map<String, List<Parameter>> organizer = createOrganizerMap(createOraganizerData());
		Map<String, List<Parameter>> attendee = createAttendeeMap(createAttendeeData(appointment));

		return new InterchangeCalendarDataBuilder().organizers(organizer).attendees(attendee).location(InterchangeCalendarConstants.LOCATION)
				.startTime(appointment.getTutoringAppointmentStartDateTime())
				.endTime(appointment.getTutoringAppointmentEndDateTime()).appointmentName(name)
				.prodId(createInterchangeCalendarProdId()).filePath(InterchangeCalendarConstants.FILE_NAME).build();
	}

	public static Map<String, String> createOraganizerData() {
		Map<String, String> organizerData = new HashMap<>();
		organizerData.put(EMAIL, "remad@web.de");
		organizerData.put(CN, "ReMad");

		return organizerData;
	}

	public static Map<String, String> createAttendeeData(TutoringAppointmentEntity appointment) {
		String fullName = String.join(StringUtils.SPACE, appointment.getTutoringAppointmentUser().getFirstName(),
				appointment.getTutoringAppointmentUser().getLastName());
		Map<String, String> attendeeData = new HashMap<>();
		attendeeData.put(EMAIL, appointment.getTutoringAppointmentUser().getEmail());
		attendeeData.put(CN, fullName);

		return attendeeData;
	}

	public static String createAppointmentName(LocalDateTime startTime) {
		String convertedTime = convertLocaldateTimeToTime(startTime);

		return InterchangeCalendarConstants.APPOINTMENT_NAME + convertedTime;
	}

	public static String convertLocaldateTimeToTime(LocalDateTime time) {
		return time.format(InterchangeCalendarConstants.DATE_AND_TIME_FORMATTER);
	}
	
	// TODO refactor and work with TutoringAppointmentEntity
	public static List<byte[]> createInterchangeCalendarFile(List<ReminderEntity> reminders) {
		List<byte[]> calendarFiles = new ArrayList<>();
		
		for(ReminderEntity reminder : reminders) {
			byte[] calendarFile = createCalendarFile(createCalendarData(reminder.getReminderTutoringAppointment()));
			calendarFiles.add(calendarFile);
		}
		
		return calendarFiles;
	}

	public static byte[] createCalendarFile(InterchangeCalendarData calendarData) {
		return new InterchangeCalendarBuilder().setStartTime(calendarData.getStartTime())
				.setEndTime(calendarData.getEndTime()).setAppointmentName(calendarData.getAppointmentName())
				.setAttendees(calendarData.getAttendees()).setOrganizers(calendarData.getOrganizers())
				.setProdId(calendarData.getProdId()).setLocation(new Location(calendarData.getLocation())).build();
	}
}