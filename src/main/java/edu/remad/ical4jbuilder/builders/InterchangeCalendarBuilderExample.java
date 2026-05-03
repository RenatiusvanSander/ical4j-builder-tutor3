package edu.remad.ical4jbuilder.builders;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.remad.ical4jbuilder.constants.InterchangeCalendarConstants;
import edu.remad.ical4jbuilder.exceptions.InterchangeCalendarBuilderException;
import edu.remad.ical4jbuilder.models.InterchangeCalendarData;
import edu.remad.ical4jbuilder.utilities.InterchangeCalendarUtilities;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.validate.ValidationException;

/**
 * Example for Interchange Calendar Builder
 * 
 * @author edu.remad
 * @since 2026
 */
public class InterchangeCalendarBuilderExample {

	/**
	 * Runs sample for {@link InterchangeCalendarBuilder}
	 * 
	 * @param args arguments
	 */
	public static void main(String[] args) {
		String startTimeString = "2024-04-20 10:00";
		String endTimeString = "2024-04-20 11:00";
		String appointmentName = "Nachhilfe";
		String filePath = "rmeier_freelace_nachhilfe_termin.ics";
		String email = "remad@web.de";
		String attendeeEmail = "pamela@hotty.de";
		String userName = "Remy" + " " + "Meier";
		String location = "Remote Teams";
		String attendeeUsername1 = "Pamela 1";

		Map<String, String> organizerData = new HashMap<>();
		organizerData.put(Parameter.EMAIL, email);
		organizerData.put(Parameter.CN, userName);
		Map<String, List<Parameter>> organizers = InterchangeCalendarUtilities.createOrganizerMap(organizerData);

		Map<String, String> attendeeData = new HashMap<>();
		attendeeData.put(Parameter.EMAIL, attendeeEmail);
		attendeeData.put(Parameter.CN, attendeeUsername1);
		Map<String, List<Parameter>> attendees = InterchangeCalendarUtilities.createAttendeeMap(attendeeData);

		InterchangeCalendarData calendarData = new InterchangeCalendarData();
		calendarData.setStartTime(
				LocalDateTime.parse(startTimeString, InterchangeCalendarConstants.DATE_AND_TIME_FORMATTER));
		calendarData
				.setEndTime(LocalDateTime.parse(endTimeString, InterchangeCalendarConstants.DATE_AND_TIME_FORMATTER));
		calendarData.setAppointmentName(appointmentName);
		calendarData.setLocation(location);
		calendarData.setAttendees(attendees);
		calendarData.setOrganizers(organizers);
		calendarData.setProdId(InterchangeCalendarUtilities.createInterchangeCalendarProdId());
		calendarData.setFilePath(filePath);

		byte[] icsFile = new InterchangeCalendarBuilder().setStartTime(calendarData.getStartTime())
				.setEndTime(calendarData.getEndTime()).setAppointmentName(calendarData.getAppointmentName())
				.setAttendees(calendarData.getAttendees()).setOrganizers(calendarData.getOrganizers())
				.setProdId(calendarData.getProdId()).setLocation(new Location(calendarData.getLocation())).build();

		try (FileOutputStream fis = new FileOutputStream(new File(calendarData.getFilePath()))) {
			fis.write(icsFile);
		} catch (ValidationException | IOException e) {
			throw new InterchangeCalendarBuilderException(e.getMessage(), e);
		}
	}

}
