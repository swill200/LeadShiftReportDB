package controller;

import view.MainView;
import view.DateSelectionView;
import view.ReportEntryView;
import model.LeadShiftReportDAO;

public class MainController {
	private MainView mainView;
	private LeadShiftReportDAO dao;

	public MainController(MainView mainView) {
		this.mainView = mainView;
		this.mainView.setController(this);
		this.dao = new LeadShiftReportDAO();
	}

	public void handleCreateNewReport() {
		ReportEntryView entryView = new ReportEntryView();
		ReportEntryController entryController = new ReportEntryController(entryView, dao);
		entryView.setController(entryController);
		entryView.open();
	}

	public void handleRetrieveReports() {
		DateSelectionView dateView = new DateSelectionView();
		DateSelectionController dateController = new DateSelectionController(dateView, dao);
		dateView.setController(dateController);
		dateView.open();
	}
}
