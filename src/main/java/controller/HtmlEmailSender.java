package util;

import model.DailyReport;
import model.Shift;
import model.Employee;
import model.DailyChecklist;
import model.OpAssignment;

import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.time.format.DateTimeFormatter;

public class HtmlEmailSender {

	/**
	 * Sends an HTML email summarizing the DailyReport and related data.
	 *
	 * @param report The DailyReport model object.
	 * @param shift The Shift object associated with the DailyReport.
	 * @param employeesOnDuty The list of Employees working during this shift.
	 * @param checklist The DailyChecklist for the report.
	 * @param assignments The OpAssignments for the report.
	 * @param smtpHost SMTP Host for sending email.
	 * @param fromAddress The from (sender) email address.
	 * @param username SMTP username for authentication.
	 * @param password SMTP password for authentication.
	 * @throws MessagingException If sending the email fails.
	 */
	public static void sendEmail(
			DailyReport report,
			Shift shift,
			List<Employee> employeesOnDuty,
			DailyChecklist checklist,
			List<OpAssignment> assignments,
			String smtpHost,
			String fromAddress,
			String username,
			String password
	) throws MessagingException {

		// Format date/time for display
		String formattedDate = report.getDailyReportDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

		// Begin constructing HTML
		StringBuilder sb = new StringBuilder("<html><body>"
				+ "<style>body { font-family: Calibri, sans-serif; }"
				+ ".Massive {color:red; font-size:24px} "
				+ ".normal {font-weight: normal;} "
				+ "h2 {color:black; font-size:16px;} "
				+ "h4 {color:red; font-size:16px;} "
				+ "p {color:black; font-size:16px}"
				+ "</style>");

		sb.append("<h1><span style=\"color: #0000ff;\">Shift Report</span></h1><hr />");
		sb.append("<table><tbody><tr>");
		sb.append("<td><strong>Date: </strong>").append(formattedDate).append("</td>");
		sb.append("<td width=\"35%\"><strong>Author: </strong>").append(report.getCreatedBy()).append("</td>");
		sb.append("<td width=\"25%\"><strong>Shift: </strong>").append(shift.getShiftName()).append("</td>");
		sb.append("<td width=\"15%\"><strong>Oncoming Lead:</strong> ").append(report.getOncomingLead()).append("</td>");
		sb.append("</tr></tbody></table><hr />");

		// Passdown Accepted?
		if (report.isPassdownAccepted()) {
			sb.append("<p style=\"color:blue;font-size:20px;\">Passdown Accepted</p>");
		} else {
			sb.append("<h4 class=\"Massive\">Passdown was declined!</h4>");
		}

		// Daily Checklist Results
		sb.append("<h2>Daily Checklist:</h2>");
		boolean allComplete = checklist.isArcITXAccuracy() && checklist.isArcITXVerified() &&
				checklist.isChannelLaunch() && checklist.isWeatherMaps() &&
				checklist.isInteractiveChannel() && checklist.isDailyLeadSweep() &&
				checklist.isMaintenanceTicket() && checklist.isRestoreVerified() &&
				checklist.isStbTurner() && checklist.isPreliminaryKCI() &&
				checklist.isSkdl() && checklist.isTakedown();

		if (allComplete) {
			sb.append("<p style=\"color:blue;font-size:20px;\">All checklist items complete!</p>");
		} else {
			sb.append("<h4 class=\"Massive\">Some checklist items are incomplete!</h4>");
			sb.append("<p>Details:</p><ul>");
			if (!checklist.isArcITXAccuracy()) sb.append("<li>ArcITXAccuracy not complete</li>");
			if (!checklist.isArcITXVerified()) sb.append("<li>ArcITXVerified not complete</li>");
			if (!checklist.isChannelLaunch()) sb.append("<li>ChannelLaunch not complete</li>");
			if (!checklist.isWeatherMaps()) sb.append("<li>WeatherMaps not complete</li>");
			if (!checklist.isInteractiveChannel()) sb.append("<li>InteractiveChannel not complete</li>");
			if (!checklist.isDailyLeadSweep()) sb.append("<li>DailyLeadSweep not complete</li>");
			if (!checklist.isMaintenanceTicket()) sb.append("<li>MaintenanceTicket not complete</li>");
			if (!checklist.isRestoreVerified()) sb.append("<li>RestoreVerified not complete</li>");
			if (!checklist.isStbTurner()) sb.append("<li>STBTurner not complete</li>");
			if (!checklist.isPreliminaryKCI()) sb.append("<li>PreliminaryKCI not complete</li>");
			if (!checklist.isSkdl()) sb.append("<li>SKDL not complete</li>");
			if (!checklist.isTakedown()) sb.append("<li>Takedown not complete</li>");
			sb.append("</ul>");
		}

		// On Duty Staff
		sb.append("<h2>On Duty Staff:</h2>");
		if (employeesOnDuty.isEmpty()) {
			sb.append("<p class=\"normal\">None</p>");
		} else {
			sb.append("<p>");
			for (Employee emp : employeesOnDuty) {
				sb.append(emp.getFirstName()).append(" ").append(emp.getLastName()).append(", ");
			}
			// Remove last comma and space if present
			sb.setLength(sb.length() - 2);
			sb.append("</p>");
		}

		// Operational Assignments
		sb.append("<h2>Operational Assignments:</h2>");
		if (assignments.isEmpty()) {
			sb.append("<p class=\"normal\">None</p>");
		} else {
			for (OpAssignment assign : assignments) {
				sb.append("<h4>").append(assign.getAssignment()).append(" - ")
						.append(assign.getOpAssignmentDate().toString()).append("</h4>");
			}
		}

		// Close HTML
		sb.append("</body></html>");

		// Setup mail properties
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		// Create a Session with authentication
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		// Create the message
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromAddress));
		// Set recipient(s) as needed. For demonstration, we'll use a placeholder:
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("Cheyenne-TOCTeamLeaders@dish.com"));
		message.setSubject("Shift Report - " + shift.getShiftName() + " " + formattedDate);

		// Create a multipart message for HTML content
		Multipart multipart = new MimeMultipart("alternative");

		// HTML part
		MimeBodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(sb.toString(), "text/html; charset=UTF-8");
		multipart.addBodyPart(htmlPart);

		message.setContent(multipart);

		// Send the message
		Transport.send(message);
	}
}
