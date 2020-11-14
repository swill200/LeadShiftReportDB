package forms;

import org.eclipse.swt.SWT;
//import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Shell;

import main.DataObject;

public class HtmlEmailSender {

	public static void sendEMail(DataObject obj) {
		Shell shell = new Shell();
		OleFrame frame = new OleFrame(shell, SWT.NONE);

		// This should start outlook if it is not running yet
//      OleClientSite site = new OleClientSite(frame, SWT.NONE, "OVCtl.OVCtl");
//      site.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);

		// Now get the outlook application
		OleClientSite site2 = new OleClientSite(frame, SWT.NONE, "Outlook.Application");
		OleAutomation outlook = new OleAutomation(site2);

		OleAutomation mail = invoke(outlook, "CreateItem", 0 /* Mail item */).getAutomation();

		StringBuilder sb = new StringBuilder(
				"<style>  body { font-family: Calibri, sans-serif; mso-padding-bottom-alt: 0;} .Massive {color:red; font-size: 24px} "
				+ ".normal {font-weight: normal;} h2 {color:black; font-size: 16px;} h3 {color:black; font-size: 14px;} "
				+ "h4 {color:red; font-size: 16px;} p {color:black; font-size: 16px}</style><html><body><h1>"
				+ "<span style=\"color: #0000ff;\">Cheyenne TOC Lead Tech Shift Report</span></h1>");
		sb.append("<hr /><table><tbody><tr><td><strong>Date: </strong>" + obj.date + "</td>"
				+ "<td width=\"35%\"><strong>Author: </strong>" + obj.user + "</td>"
				+ "<td width=\"25%\"><strong>Shift: </strong>" + obj.shift + "</td>"
				+ "<td width=\"15%\"><strong>Site:</strong> Cheyenne</td>" + "</tr></tbody></table>");
		sb.append("<hr /><strong>MOC:  </strong>" + obj.mocValue + "<br />");
		sb.append("<strong>ODS:  </strong>" + obj.odsValue);
		sb.append("<h2>Daily System Checks and Responsibilities: </h2>");
		if (dailyChecksCompleted(obj)) {
			sb.append("<p style=\"color:blue;font-size:25px;\"> All complete! </p>");
		} else {
			sb.append("<h4> <div class=\"Massive\">System checks incomplete! </div></h4>");
		}
		sb.append("<h2>Call Outs: </h2>");
		if (obj.callins.equals("")) {
			sb.append("<p class=\"normal\">None</p>");
		} else {
			sb.append("<h4>" + obj.callins + "</h4>");
		}
		sb.append("<h2>On Duty Staff: </h2>");
		for (int i = 0; i < obj.employeeNames.getItemCount(); i++) {
			if (obj.employeeNames.getItem(i) != "None" && obj.getEmployees(i)) {
				sb.append(obj.employeeNames.getItem(i) + ", ");
			}
		}
		sb.delete((sb.length() - 2), (sb.length() - 1));

		sb.append("<h2>Ongoing Outages and Maintenance: </h2>");
		boolean checks = false;
		for (int i = 0; i < obj.tableData.getItemCount(); i += 2) {
			if (!obj.tableData.getItem(i).equals("")) {
				sb.append(
						"<h4>" + obj.tableData.getItem(i).toString() + " - " + obj.tableData.getItem(i + 1).toString() + "</h4>");
				checks = true;
			}
		}
		if (!checks) {
			sb.append("<p class=\"normal\">None</p>");
		}

		sb.append("<h2>Takedowns, Restores, and Other Channel Launch: </h2>");
//		System.out.println(obj.takedownText + " <--");
		if (obj.takedownText.equals("")) {
			sb.append("<p class=\"normal\">None</p>");
		} else {
			sb.append("<h4>" + obj.takedownText + "</h4>");
		}

		sb.append("<h2>Requests From Other Departments: </h2>");
		if (obj.idRequestText.equals("")) {
			sb.append("<p class=\"normal\">None</p>");
		} else {
			sb.append("<h4>" + obj.idRequestText + "</h4>");
		}

		sb.append("<h2>Equipment and/or Redundancy Issues: </h2>");
		if (obj.equipmentText.equals("")) {
			sb.append("<p class=\"normal\">None</p>");
		} else {
			sb.append("<h4>" + obj.equipmentText + "</h4>");
		}

		sb.append("<h2>Special Monitoring Requests: </h2>");
		if (obj.specialMonitoringText.equals("")) {
			sb.append("<p class=\"normal\">None</p>");
		} else {
			sb.append("<h4>" + obj.specialMonitoringText + "</h4>");
		}

		sb.append("<h2>Oncoming Shift Lead Tech: </h2>");
		sb.append("<p class=\"normal\">" + obj.oncomingLead + "</p>");
		if (obj.declinedChecked) {
			sb.append("<h4 class=\"Massive\">Passdown was declined!</h4>");
			sb.append("<h2>Reasoning:</h2> " + obj.declinedReason);
		}
		sb.append("</body></html>");

		setProperty(mail, "BodyFormat", 2 /* HTML */);
		setProperty(mail, "Subject", ("Lead Tech Shift Report - " + obj.shift  + " " + obj.date));
		setProperty(mail, "To", "Cheyenne-TOCTeamLeaders@dish.com");
		setProperty(mail, "HtmlBody", sb.toString().replaceAll("[\\n]", "<br />"));

// To include attachments, currently unused
//      if (null != attachmentPaths) {
//          for (String attachmentPath : attachmentPaths) {
//              File file = new File(attachmentPath);
//              if (file.exists()) {
//                  OleAutomation attachments = getProperty(mail, "Attachments");
//                  invoke(attachments, "Add", attachmentPath);
//              }
//          }
//      }

		invoke(mail, "Display" /* or "Send" */);

	}

	private static boolean dailyChecksCompleted(DataObject obj) {
		if (!obj.eaWaItxComplete || !obj.eaWaItxPlayoutComplete || !obj.channelLaunchComplete || !obj.weatherComplete
				|| !obj.interactiveComplete || !obj.dailySweeps || !obj.maintenanceComplete || !obj.turnerComplete
				|| !obj.kciComplete || !obj.skdlComplete || !obj.mcSwitchesComplete || !obj.maintenanceSigned) {
			return false;
		}
		return true;
	}

//	private static OleAutomation getProperty(OleAutomation auto, String name) {
//		Variant varResult = auto.getProperty(property(auto, name));
//		if (varResult != null && varResult.getType() != OLE.VT_EMPTY) {
//			OleAutomation result = varResult.getAutomation();
//			varResult.dispose();
//			return result;
//		}
//		return null;
//	}

//	private static Variant invoke(OleAutomation auto, String command, String value) {
//		return auto.invoke(property(auto, command), new Variant[] { new Variant(value) });
//	}

	private static Variant invoke(OleAutomation auto, String command) {
		return auto.invoke(property(auto, command));
	}

	private static Variant invoke(OleAutomation auto, String command, int value) {
		return auto.invoke(property(auto, command), new Variant[] { new Variant(value) });
	}

	private static boolean setProperty(OleAutomation auto, String name, String value) {
		return auto.setProperty(property(auto, name), new Variant(value));
	}

	private static boolean setProperty(OleAutomation auto, String name, int value) {
		return auto.setProperty(property(auto, name), new Variant(value));
	}

	private static int property(OleAutomation auto, String name) {
		return auto.getIDsOfNames(new String[] { name })[0];
	}
}