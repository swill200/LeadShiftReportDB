package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ReportEntryView extends JFrame {
	private JTextField technicianNameField;
	private JTextField dateField;
	private JComboBox<String> shiftCombo;
	private JTextArea reportContentArea;
	private JButton submitButton;

	public ReportEntryView() {
		setTitle("New Report Entry");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(5, 2, 10, 10));

		add(new JLabel("Technician Name:"));
		technicianNameField = new JTextField();
		add(technicianNameField);

		add(new JLabel("Date (YYYY-MM-DD):"));
		dateField = new JTextField();
		add(dateField);

		add(new JLabel("Shift:"));
		shiftCombo = new JComboBox<>(new String[] {"Morning", "Evening", "Night"});
		add(shiftCombo);

		add(new JLabel("Report Content:"));
		reportContentArea = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(reportContentArea);
		add(scrollPane);

		submitButton = new JButton("Submit");
		add(new JLabel(""));
		add(submitButton);

		pack();
		setLocationRelativeTo(null);
	}

	public void addSubmitListener(ActionListener listener) {
		submitButton.addActionListener(listener);
	}

	public String getTechnicianName() {
		return technicianNameField.getText();
	}

	public String getDateText() {
		return dateField.getText();
	}

	public String getSelectedShift() {
		return (String) shiftCombo.getSelectedItem();
	}

	public String getReportContent() {
		return reportContentArea.getText();
	}

	public void open() {
		SwingUtilities.invokeLater(() -> setVisible(true));
	}
}
