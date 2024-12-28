package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DateSelectionView extends JFrame {
	private JTextField dateField;
	private JComboBox<String> shiftCombo;
	private JButton searchButton;

	public DateSelectionView() {
		setTitle("Select Date and Shift");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(3, 2, 10, 10));

		add(new JLabel("Date (YYYY-MM-DD):"));
		dateField = new JTextField();
		add(dateField);

		add(new JLabel("Shift:"));
		shiftCombo = new JComboBox<>(new String[] {"Morning", "Evening", "Night"});
		add(shiftCombo);

		searchButton = new JButton("Search");
		add(new JLabel(""));
		add(searchButton);

		pack();
		setLocationRelativeTo(null);
	}

	public void addSearchListener(ActionListener listener) {
		searchButton.addActionListener(listener);
	}

	public String getDateText() {
		return dateField.getText();
	}

	public String getSelectedShift() {
		return (String) shiftCombo.getSelectedItem();
	}

	public void open() {
		SwingUtilities.invokeLater(() -> setVisible(true));
	}
}
