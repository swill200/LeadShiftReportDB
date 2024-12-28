package view;

import java.awt.*;
import javax.swing.*;

import controller.MainController;

public class MainView extends JFrame {
	private MainController controller;

	public MainView() {
		setTitle("Lead Shift Report");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2, 1, 10, 10));

		JButton newReportButton = new JButton("Create New Report");
		newReportButton.addActionListener(e -> {
			if (controller != null) {
				controller.openNewReport();
			}
		});

		JButton retrieveReportButton = new JButton("Retrieve Previous Reports");
		retrieveReportButton.addActionListener(e -> {
			if (controller != null) {
				controller.openRetrieveReports();
			}
		});

		add(newReportButton);
		add(retrieveReportButton);

		pack();
		setLocationRelativeTo(null); // Center on screen
	}

	public void setController(MainController controller) {
		this.controller = controller;
	}

	public void open() {
		SwingUtilities.invokeLater(() -> setVisible(true));
	}
}
