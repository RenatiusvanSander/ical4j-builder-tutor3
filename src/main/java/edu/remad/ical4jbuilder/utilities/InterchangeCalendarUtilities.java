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
import edu.remad.tutoring3.persistence.models.TutoringAppointmentEntity;
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

	/**
	 * @return generates {@link Uid}
	 */
	public static Uid generateUid() {
		return InterchangeCalendarConstants.UID_GENERATOR.generateUid();
	}

	/**
	 * @return creates {@link InterchangeCalendarProdId}
	 */
	public static InterchangeCalendarProdId createInterchangeCalendarProdId() {
		return new InterchangeCalendarProdId();
	}

	/**
	 * Creates a map of organizer
	 * 
	 * @param organizerData key-value-map for organizer's data
	 * @return key-list-value-map is organizers map
	 */
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

	/**
	 * Creates attendee map
	 * 
	 * @param attendeeData key-value-map of attendee data
	 * @return key-listvalue as map
	 */
	public static Map<String, List<Parameter>> createAttendeeMap(Map<String, String> attendeeData) {
		if (attendeeData.isEmpty()) {
			throw new InterChangeCalendarUtilitiesException("attendee data has to be populated.");
		}

		List<Parameter> parameters = createParameterList(attendeeData, Role.REQ_PARTICIPANT);
		Map<String, List<Parameter>> attendee = new HashMap<>();
		attendee.put(InterchangeCalendarConstants.MAILTO_KEY + attendeeData.get(EMAIL), parameters);

		return attendee;
	}

	/**
	 * Creates calendar data
	 * 
	 * @param appointment appointment as {@link TutoringAppointmentEntity} to create
	 *                    {@link InterchangeCalendarData}
	 * @return {@link InterchangeCalendarData}
	 */
	public static InterchangeCalendarData createCalendarData(TutoringAppointmentEntity appointment) {
		String name = createAppointmentName(appointment.getTutoringAppointmentStartDateTime());
		Map<String, List<Parameter>> organizer = createOrganizerMap(createOraganizerData());
		Map<String, List<Parameter>> attendee = createAttendeeMap(createAttendeeData(appointment));

		return new InterchangeCalendarDataBuilder().organizers(organizer).attendees(attendee)
				.location(InterchangeCalendarConstants.LOCATION)
				.startTime(appointment.getTutoringAppointmentStartDateTime())
				.endTime(appointment.getTutoringAppointmentEndDateTime()).appointmentName(name)
				.prodId(createInterchangeCalendarProdId()).filePath(InterchangeCalendarConstants.FILE_NAME).build();
	}

	/**
	 * @return creates organizer data
	 */
	public static Map<String, String> createOraganizerData() {
		return createOraganizerData("remad@web.de","ReMad");
	}

	/**
	 * Creates organizer data with custom e-mail and username
	 * 
	 * @param email e-mail address to set
	 * @param userName user's name to set
	 * @return key-value-map with organizer's data
	 */
	public static Map<String, String> createOraganizerData(String email, String userName) {
		Map<String, String> organizerData = new HashMap<>();
		organizerData.put(EMAIL, email);
		organizerData.put(CN, userName);

		return organizerData;
	}

	/**
	 * Creates attendees' data
	 * 
	 * @param appointment tutoring appointment data as
	 *                    {@link TutoringAppointmentEntity}
	 * @return
	 */
	public static Map<String, String> createAttendeeData(TutoringAppointmentEntity appointment) {
		String fullName = String.join(StringUtils.SPACE, appointment.getTutoringAppointmentUser().getGivenName(),
				appointment.getTutoringAppointmentUser().getFamilyName());
		Map<String, String> attendeeData = new HashMap<>();
		attendeeData.put(EMAIL, appointment.getTutoringAppointmentUser().getEmail());
		attendeeData.put(CN, fullName);

		return attendeeData;
	}

	/**
	 * Creates appointment name
	 * 
	 * @param startTime start time as {@link LocalDateTime}
	 * @return appointment's name as encoded {@link String}
	 */
	public static String createAppointmentName(LocalDateTime startTime) {
		String convertedTime = convertLocaldateTimeToTime(startTime);

		return InterchangeCalendarConstants.APPOINTMENT_NAME + convertedTime;
	}

	/**
	 * Converts a {@link LocalDateTime} to a formatted time string.
	 * 
	 * @param time time as {@link LocalDateTime}
	 * @return converted string-encoded time
	 */
	public static String convertLocaldateTimeToTime(LocalDateTime time) {
		return time.format(InterchangeCalendarConstants.DATE_AND_TIME_FORMATTER);
	}

	/**
	 * Creates Interchange Calendar File
	 * 
	 * @param appointments will changed
	 * @return list of byte arrays
	 */
	public static List<byte[]> createInterchangeCalendarFile(List<TutoringAppointmentEntity> appointments) {
		List<byte[]> calendarFiles = new ArrayList<>();

		for (TutoringAppointmentEntity appointment : appointments) {
			byte[] calendarFile = createCalendarFile(createCalendarData(appointment));
			calendarFiles.add(calendarFile);
		}

		return calendarFiles;
	}

	/**
	 * Creates ics-file from object of {@link InterchangeCalendarData}
	 * 
	 * @param calendarData interchange calendar data as
	 *                     {@link InterchangeCalendarData}
	 * @return byte array, which is an ics-file
	 */
	public static byte[] createCalendarFile(InterchangeCalendarData calendarData) {
		return new InterchangeCalendarBuilder().setStartTime(calendarData.getStartTime())
				.setEndTime(calendarData.getEndTime()).setAppointmentName(calendarData.getAppointmentName())
				.setAttendees(calendarData.getAttendees()).setOrganizers(calendarData.getOrganizers())
				.setProdId(calendarData.getProdId()).setLocation(new Location(calendarData.getLocation())).build();
	}

}