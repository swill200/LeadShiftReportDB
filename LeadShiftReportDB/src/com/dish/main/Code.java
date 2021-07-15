package com.dish.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;

public class Code {
	protected static boolean ok;
	protected static String shift;
	protected static DataObject obj = new DataObject();;

	public static void main(String args[]) {
	}

	public static String[] checkShift(String shift) throws IOException {
		String[] empArray;
		switch (shift) {
		case "Days": {
			empArray = setEmployees(0);
			break;
		}
		case "Swings": {
			empArray = setEmployees(1);
			break;
		}
		case "Mids": {
			empArray = setEmployees(2);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + shift);
		}
		return empArray;
	};

	public static String getShiftText() {
		LocalTime dayShift = LocalTime.of(7, 0);
		LocalTime swingShift = LocalTime.of(15, 0);
		LocalTime midShift = LocalTime.of(23, 0);
		LocalTime timeComparison = LocalTime.now();

		if (timeComparison.equals(dayShift) || timeComparison.isAfter(dayShift) && timeComparison.isBefore(swingShift)) {
			shift = "Days";
		}
		else if (timeComparison.equals(swingShift)
				|| timeComparison.isAfter(swingShift) && timeComparison.isBefore(midShift)) {
			shift = "Swings";
		}
		else {
			shift = "Mids";
		}
		return shift;
	}

	public static String[] setEmployees(int shiftNumber) throws IOException {
		File shiftFile = null;
		switch (shiftNumber) {
		case 0: {
			shiftFile = new File("src\\datastore\\shift_days_employees");
			break;
		}
		case 1: {
			shiftFile = new File("src\\datastore\\shift_swings_employees");
			break;
		}
		case 2: {
			shiftFile = new File("src\\datastore\\shift_mids_employees");
			break;
		}
		default: {
			throw new IllegalArgumentException("Unexpected value: " + shiftNumber);
		}
		}
		FileReader reader = new FileReader(shiftFile);
		BufferedReader br = new BufferedReader(reader); // creates a buffering
																										// character input stream
		String line;
		String[] empArray = new String[10];
		int i = 0;
		while ((line = br.readLine()) != null) {
			empArray[i] = line;
			i++;
		}
		for (int j = 0; j < empArray.length; j++) {
			if (empArray[j] == null) {
				empArray[j] = "None";
			}
		}
		br.close();
		reader.close(); // closes the stream and release the resources
		return empArray;
	};

	public static String[] getMocList() throws IOException {
		File mocFile = new File("src\\datastore\\moc_list");
		FileReader reader = new FileReader(mocFile);
		BufferedReader br = new BufferedReader(reader);
		String line;
		String[] mocStrings = new String[15];
		for (int i = 0; i < mocStrings.length; i++) {
			mocStrings[i] = " ";
		}
		int i = 0;
		while ((line = br.readLine()) != null) {
			mocStrings[i] = line;
			i++;
		}
		br.close();
		reader.close();
		return mocStrings;
	}

	// Primary method to retrieve and load data into the DataObject for display in
	// the application window
	public static DataObject getPassdownData(String time, String shift) throws IOException {
		obj.date = null;
		ArrayList<String> list = new ArrayList<>();
		// Read all lines in the passdown_datastore
		for (String line : Files.readAllLines(Paths.get("src\\datastore\\passdown_datastore.pd"),
				Charset.defaultCharset())) {
			list.add(line);
		}
		setOk(false);
		String formattedDate = time;
		// Search the lines read above for the specific date requested
		for (String line : list) {
//			System.out.println(formattedDate);
			if (line.startsWith(formattedDate)) {
//				System.out.println(formattedDate + " DATE");
				line = line.replaceAll("[%]{4}", "\n");
				String[] splitLine = line.split(";--");
				setOk(true);
//				System.out.println(shift);
//				System.out.println(obj.shift + " OBJ.SHIFT");
				obj = setDataObject(obj, splitLine);
				// After the date has been found, check for the matching shift requested
				if (obj.shift.equals(shift)) {
//					System.out.println(obj.shift + " = " + shift);
					return obj;
				}
				// Create a new, empty DataObject and display a missing record error to
				// the user if the shift cannot be found on that date
				else {
					setOk(false);
					obj = new DataObject();
//					JOptionPane.showMessageDialog(null, "Record not found");
				}
			}
		}
		// Display a missing record error to the user if the date cannot be found in
		// the data-store
//		if (!getOk()) {
//			JOptionPane.showMessageDialog(null, "Record not found");
//		}
		return obj;
	}

	protected static void setOk(boolean okSet) {
		ok = okSet;
	}

	protected static boolean getOk() {
		return ok;
	}

//	// Setter for DataObject values
//	// TODO: Find a better way to process all these values. Consider a getter?
//	public static void setDataObject(DataObject obj, String[] splitLine) {
//		obj.date = splitLine[0];
//		obj.user = splitLine[1];
//		obj.shift = splitLine[2];
//		obj.employee = Boolean.parseBoolean(splitLine[3]);
//		obj.employee1 = Boolean.parseBoolean(splitLine[4]);
//		obj.employee2 = Boolean.parseBoolean(splitLine[5]);
//		obj.employee3 = Boolean.parseBoolean(splitLine[6]);
//		obj.employee4 = Boolean.parseBoolean(splitLine[7]);
//		obj.employee5 = Boolean.parseBoolean(splitLine[8]);
//		obj.employee6 = Boolean.parseBoolean(splitLine[9]);
//		obj.employee7 = Boolean.parseBoolean(splitLine[10]);
//		obj.employee8 = Boolean.parseBoolean(splitLine[11]);
//		obj.employee9 = Boolean.parseBoolean(splitLine[12]);
//		obj.text = splitLine[13];
//		obj.text1 = splitLine[14];
//		obj.text2 = splitLine[15];
//		obj.text3 = splitLine[16];
//		obj.text4 = splitLine[17];
//		obj.text5 = splitLine[18];
//		obj.text6 = splitLine[19];
//		obj.text7 = splitLine[20];
//		obj.text8 = splitLine[21];
//		obj.text9 = splitLine[22];
//		for (int i = 23; i < 45; i++) {
//			obj.tableData.add(splitLine[i]);
//		}
//		obj.takedownText = splitLine[45];
//		obj.idRequestText = splitLine[46];
//		obj.equipmentText = splitLine[47];
//		obj.specialMonitoringText = splitLine[48];
//		obj.eaWaItxComplete = Boolean.parseBoolean(splitLine[49]);
//		obj.eaWaItxPlayoutComplete = Boolean.parseBoolean(splitLine[50]);
//		obj.channelLaunchComplete = Boolean.parseBoolean(splitLine[51]);
//		obj.weatherComplete = Boolean.parseBoolean(splitLine[52]);
//		obj.interactiveComplete = Boolean.parseBoolean(splitLine[53]);
//		obj.dailySweeps = Boolean.parseBoolean(splitLine[54]);
//		obj.maintenanceComplete = Boolean.parseBoolean(splitLine[55]);
//		obj.turnerComplete = Boolean.parseBoolean(splitLine[56]);
//		obj.kciComplete = Boolean.parseBoolean(splitLine[57]);
//		obj.skdlComplete = Boolean.parseBoolean(splitLine[58]);
//		obj.mcSwitchesComplete = Boolean.parseBoolean(splitLine[59]);
//		obj.oncomingLead = splitLine[60];
//		obj.acceptedChecked = Boolean.parseBoolean(splitLine[61]);
//		obj.declinedChecked = Boolean.parseBoolean(splitLine[62]);
//		for (int i = 63; i < 73; i++) {
//			obj.employeeNames.add(splitLine[i]);
//		}
//		obj.time = splitLine[73];
//		if (splitLine.length > 74) {
//			obj.editTime = splitLine[74];
//		} else {
//			obj.editTime = "NULL";
//		}
//	}

	// Setter for DataObject values
	// TODO: Find a better way to process all these values. Consider a getter?
	public static DataObject setDataObject(DataObject obj, String[] splitLine) {
		obj.date = splitLine[0];
		obj.user = splitLine[1];
		obj.shift = splitLine[2];
		obj.employee = Boolean.parseBoolean(splitLine[3]);
		obj.employee1 = Boolean.parseBoolean(splitLine[4]);
		obj.employee2 = Boolean.parseBoolean(splitLine[5]);
		obj.employee3 = Boolean.parseBoolean(splitLine[6]);
		obj.employee4 = Boolean.parseBoolean(splitLine[7]);
		obj.employee5 = Boolean.parseBoolean(splitLine[8]);
		obj.employee6 = Boolean.parseBoolean(splitLine[9]);
		obj.employee7 = Boolean.parseBoolean(splitLine[10]);
		obj.employee8 = Boolean.parseBoolean(splitLine[11]);
		obj.employee9 = Boolean.parseBoolean(splitLine[12]);
		obj.text = splitLine[13];
		obj.text1 = splitLine[14];
		obj.text2 = splitLine[15];
		obj.text3 = splitLine[16];
		obj.text4 = splitLine[17];
		obj.text5 = splitLine[18];
		obj.text6 = splitLine[19];
		obj.text7 = splitLine[20];
		obj.text8 = splitLine[21];
		obj.text9 = splitLine[22];
		obj.tableData.removeAll();
		for (int i = 23; i < 45; i++) {
			obj.tableData.add(splitLine[i]);
		}
		obj.takedownText = splitLine[45];
		obj.idRequestText = splitLine[46];
		obj.equipmentText = splitLine[47];
		obj.specialMonitoringText = splitLine[48];
		obj.eaWaItxComplete = Boolean.parseBoolean(splitLine[49]);
		obj.eaWaItxPlayoutComplete = Boolean.parseBoolean(splitLine[50]);
		obj.channelLaunchComplete = Boolean.parseBoolean(splitLine[51]);
		obj.weatherComplete = Boolean.parseBoolean(splitLine[52]);
		obj.interactiveComplete = Boolean.parseBoolean(splitLine[53]);
		obj.dailySweeps = Boolean.parseBoolean(splitLine[54]);
		obj.maintenanceComplete = Boolean.parseBoolean(splitLine[55]);
		obj.turnerComplete = Boolean.parseBoolean(splitLine[56]);
		obj.kciComplete = Boolean.parseBoolean(splitLine[57]);
		obj.skdlComplete = Boolean.parseBoolean(splitLine[58]);
		obj.mcSwitchesComplete = Boolean.parseBoolean(splitLine[59]);
		obj.oncomingLead = splitLine[60];
		obj.acceptedChecked = Boolean.parseBoolean(splitLine[61]);
		obj.declinedChecked = Boolean.parseBoolean(splitLine[62]);
		obj.employeeNames.removeAll();
		for (int i = 63; i < 73; i++) {
			obj.employeeNames.add(splitLine[i]);
		}
		obj.time = splitLine[73];
		obj.editTime = splitLine[74];
		obj.declinedReason = splitLine[75];
		if (splitLine.length > 76) {
			obj.mocValue = splitLine[76];
			obj.mocIndex = Integer.parseInt(splitLine[77]);
			if (splitLine.length > 78) {
				obj.odsValue = splitLine[78];
				obj.odsIndex = Integer.parseInt(splitLine[79]);
				if (splitLine.length > 80) {
					obj.callins = splitLine[80];
					if (splitLine.length > 81) {
						obj.maintenanceSigned = Boolean.parseBoolean(splitLine[81]);
					}
				}
			}


		}
		else {
			obj.editTime = "NULL";
		}

		return obj;
	}
}
