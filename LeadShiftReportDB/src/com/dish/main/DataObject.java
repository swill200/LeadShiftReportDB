package com.dish.main;

import java.awt.List;

public class DataObject {

	public String date;
	public String time;
	public String user;
	public String shift;
	public boolean employee, employee1, employee2, employee3, employee4, employee5, employee6, employee7, employee8,
			employee9;
	public String text, text1, text2, text3, text4, text5, text6, text7, text8, text9;
	public List tableData = new List();
	public boolean eaWaItxComplete, eaWaItxPlayoutComplete, channelLaunchComplete, weatherComplete, interactiveComplete,
			dailySweeps, maintenanceSigned, maintenanceComplete, turnerComplete, kciComplete, skdlComplete, mcSwitchesComplete;
	public String takedownText, idRequestText, equipmentText, specialMonitoringText;
	public String oncomingLead, declinedReason, callins;
	public boolean acceptedChecked, declinedChecked;
	public List employeeNames = new List();
	public String editTime;
	public boolean[] employees = new boolean[10];
	public boolean[] systemChecks = new boolean[13];
	public String mocValue, odsValue;
	public int mocIndex, odsIndex;
	
	public DataObject() {		
	}
	
	public void setSystemChecks(int index, boolean value) {
		systemChecks[index] = value;
	}
	
	public boolean getSystemChecks(int index) {
		return systemChecks[index];
	}
	
	public void setEmployees(int index, boolean value) {
		employees[index] = value;
	}
	
	public boolean getEmployees(int index) {
		return employees[index];
	}
	
	public String getTableData(int index) {
		return tableData.getItem(index);
	}
}

