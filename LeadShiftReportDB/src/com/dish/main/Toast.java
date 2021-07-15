package com.dish.main;

/* Code edited by swilliams from original source GeeksForGeeks
 * https://www.geeksforgeeks.org/java-swing-creating-toast-message/ */

// Java program that creates the toast message 
//(which is a selectively translucent JWindow) 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class Toast extends JFrame { 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//String of toast 
	String s; 

	// JWindow 
	JWindow w; 

	public Toast(String s, int x, int y) 
	{ 
		w = new JWindow(); 
		// make the background transparent 
		w.setBackground(new Color(0, 0, 0, 0));
		// create a panel 
		JPanel p = new JPanel() { 
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) 
			{ 
				g.setFont(new Font("Arial", Font.BOLD,30));
				int wid = g.getFontMetrics().stringWidth(s); 
				int hei = g.getFontMetrics().getHeight(); 
				
				// draw the boundary of the toast and fill it 
				g.setColor(new Color(0, 0, 0, 130)); 
				g.fillRect(10, 10, wid + 30, hei + 10); 
				g.setColor(new Color(0, 0, 0, 130));
				g.drawRect(10, 10, wid + 30, hei + 10); 

				// set the color of text 
				g.setColor(new Color(255, 255, 255, 240)); 
				g.drawString(s, 25, 42); 
				int t = 250; 

				// draw the shadow of the toast 
				for (int i = 0; i < 4; i++) { 
					t -= 60; 
					g.setColor(new Color(0, 0, 0, t)); 
					g.drawRect(10 - i, 10 - i, wid + 30 + i * 2, 
							hei + 10 + i * 2); 
				} 
			} 
		}; 

		w.add(p); 
		w.setLocationRelativeTo(null);
		w.setSize(300, 100); 
		w.setFocusable(false);
	} 

	// function to pop up the toast 
	public void showToast() 
	{ 
		try { 
			w.setOpacity(1); 
			w.setVisible(true); 

			// wait for some time 
			Thread.sleep(750); 

			// make the message disappear slowly 
			for (double d = 1.0; d > 0.2; d -= 0.1) { 
				Thread.sleep(100); 
				w.setOpacity((float)d); 
			} 

			// set the visibility to false 
			w.setVisible(false); 
		} 
		catch (Exception e) { 
			System.out.println(e.getMessage()); 
		} 
	} 
} 
