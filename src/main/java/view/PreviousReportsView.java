package view;

import javax.swing.*;
import java.awt.*;

public class PreviousReportsView extends JFrame {
	private JTextArea reportsArea;

	public PreviousReportsView() {
		setTitle("Previous Reports");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		reportsArea = new JTextArea(20, 40);
		reportsArea.setEditable(false);
		add(new JScrollPane(reportsArea), BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
	}

	public void setReportsText(String text) {
		reportsArea.setText(text);
	}

	public void open() {
		SwingUtilities.invokeLater(() -> setVisible(true));
	}
}
