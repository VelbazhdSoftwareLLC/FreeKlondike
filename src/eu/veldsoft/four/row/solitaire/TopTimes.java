/*
 This file is a part of Four Row Solitaire

 Copyright (C) 2010-2014 by Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov, 2012 by pavlosn

 Four Row Solitaire is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Four Row Solitaire is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with FourRowSolitaire.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.veldsoft.four.row.solitaire;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 * Class TopTimes
 * 
 * Description: The TopTimes class manages the scoreboard, saves names and
 * times, also discards them.
 * 
 * @author Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov
 */
public class TopTimes extends JFrame implements ActionListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final int NUM_OF_RECORDS = 10;

	private JTextField names[] = new JTextField[NUM_OF_RECORDS];

	private JLabel times[] = new JLabel[NUM_OF_RECORDS];

	private JLabel jLabel[] = new JLabel[NUM_OF_RECORDS];

	private JSeparator jSeparator[] = new JSeparator[NUM_OF_RECORDS];

	private int pos;

	private JButton jButton;

	/**
	 * Constructor.
	 */
	public TopTimes() {
		initComponents();
	}

	/**
	 * Initializes the GUI components.
	 */
	private void initComponents() {
		for (int i = 0; i < names.length; i++) {
			jSeparator[i] = new JSeparator();
			jLabel[i] = new JLabel((i + 1) + ".");
			names[i] = new JTextField();
			names[i].setBorder(null);
			names[i].setColumns(10);
			names[i].setEditable(false);
			times[i] = new JLabel();
			times[i].setText(" ");
		}

		loadData();

		jButton = new JButton("Clear Times");
		jButton.addActionListener(this);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Best Times");
		toFront();
		setAlwaysOnTop(true);
		requestFocus();

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jSeparator[0])
				.addComponent(jSeparator[1])
				.addComponent(jSeparator[2],
						javax.swing.GroupLayout.Alignment.TRAILING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jSeparator[3])
												.addComponent(
														jSeparator[6],
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														483, Short.MAX_VALUE)
												.addComponent(
														jSeparator[5],
														javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jSeparator[4],
														javax.swing.GroupLayout.Alignment.LEADING)))
				.addComponent(jSeparator[7],
						javax.swing.GroupLayout.Alignment.TRAILING)
				.addComponent(jSeparator[8],
						javax.swing.GroupLayout.Alignment.TRAILING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel[0])
																.addGap(18, 18,
																		18)
																.addComponent(
																		names[0],
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		times[0])
																.addGap(38, 38,
																		38))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel[1])
																.addGap(18, 18,
																		18)
																.addComponent(
																		names[1],
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		times[1])
																.addGap(37, 37,
																		37))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel[2])
																.addGap(18, 18,
																		18)
																.addComponent(
																		names[2],
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		times[2])
																.addGap(38, 38,
																		38))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel[3])
																.addGap(18, 18,
																		18)
																.addComponent(
																		names[3],
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		times[3])
																.addGap(38, 38,
																		38))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel[4])
																.addGap(18, 18,
																		18)
																.addComponent(
																		names[4],
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		times[4])
																.addGap(38, 38,
																		38))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel[5])
																.addGap(18, 18,
																		18)
																.addComponent(
																		names[5],
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		times[5])
																.addGap(38, 38,
																		38))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel[6])
																.addGap(18, 18,
																		18)
																.addComponent(
																		names[6],
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		times[6])
																.addGap(38, 38,
																		38))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel[7])
																.addGap(18, 18,
																		18)
																.addComponent(
																		names[7],
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		times[7])
																.addGap(38, 38,
																		38))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel[8])
																.addGap(18, 18,
																		18)
																.addComponent(
																		names[8],
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		times[8])
																.addGap(38, 38,
																		38))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel[9])
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		names[9],
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		times[9])
																.addGap(38, 38,
																		38))))
				.addComponent(jSeparator[9])
				.addGroup(
						layout.createSequentialGroup().addGap(185, 185, 185)
								.addComponent(jButton)
								.addGap(0, 0, Short.MAX_VALUE)));

		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel[0])
												.addComponent(times[0])
												.addComponent(
														names[0],
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSeparator[0],
										javax.swing.GroupLayout.PREFERRED_SIZE,
										11,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel[1])
												.addComponent(times[1])
												.addComponent(
														names[1],
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSeparator[1],
										javax.swing.GroupLayout.PREFERRED_SIZE,
										10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel[2])
												.addComponent(times[2])
												.addComponent(
														names[2],
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSeparator[2],
										javax.swing.GroupLayout.PREFERRED_SIZE,
										10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel[3])
												.addComponent(times[3])
												.addComponent(
														names[3],
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSeparator[3],
										javax.swing.GroupLayout.PREFERRED_SIZE,
										10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel[4])
												.addComponent(times[4])
												.addComponent(
														names[4],
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSeparator[4],
										javax.swing.GroupLayout.PREFERRED_SIZE,
										10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel[5])
												.addComponent(
														names[5],
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(times[5]))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSeparator[5],
										javax.swing.GroupLayout.PREFERRED_SIZE,
										10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel[6])
												.addComponent(
														names[6],
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(times[6]))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSeparator[6],
										javax.swing.GroupLayout.PREFERRED_SIZE,
										10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel[7])
												.addComponent(
														names[7],
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(times[7]))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSeparator[7],
										javax.swing.GroupLayout.PREFERRED_SIZE,
										10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel[8])
												.addComponent(
														names[8],
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(times[8]))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSeparator[8],
										javax.swing.GroupLayout.PREFERRED_SIZE,
										10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel[9])
												.addComponent(
														names[9],
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(times[9]))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSeparator[9],
										javax.swing.GroupLayout.PREFERRED_SIZE,
										10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jButton)
								.addContainerGap(13, Short.MAX_VALUE)));

		pack();
	}

	/**
	 * Loads the saved times and names.
	 */
	public void loadData() {
		String fileLocation = System.getProperty("user.home")
				+ System.getProperty("file.separator") + "frs-topTimes.dat";
		int count = 0;
		String temp = " ";

		try {
			FileReader file = new FileReader(fileLocation);
			BufferedReader input = new BufferedReader(file);
			while (count < NUM_OF_RECORDS) {
				temp = input.readLine();
				names[count].setText(temp);
				temp = input.readLine();
				times[count].setText(temp == null ? " " : temp);
				count++;
			}

			input.close();
		} catch (Exception ex) {
		}
	}

	/**
	 * Action performed.
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * Clear data button selected.
		 */
		if (e.getSource() == jButton) {
			clearData();
			repaint();
		}
	}

	/**
	 * Discard all the data and saves the changes.
	 */
	private void clearData() {
		String fileLocation = System.getProperty("user.home")
				+ System.getProperty("file.separator") + "frs-topTimes.dat";

		try {
			FileWriter file = new FileWriter(fileLocation);
			PrintWriter output = new PrintWriter(file);
			for (int i = 0; i < NUM_OF_RECORDS; i++) {
				output.println(" ");
				output.println(" ");
				names[i].setText(" ");
				times[i].setText(" ");
			}
			output.close();

		} catch (Exception ex) {
		}
	}

	/**
	 * Saves the data into frs-topTimes file.
	 */
	private void saveData() {
		String fileLocation = System.getProperty("user.home")
				+ System.getProperty("file.separator") + "frs-topTimes.dat";

		try {
			FileWriter file = new FileWriter(fileLocation);
			PrintWriter output = new PrintWriter(file);
			for (int i = 0; i < NUM_OF_RECORDS; i++) {
				output.println(names[i].getText());
				output.println(times[i].getText());
			}
			output.close();
		} catch (Exception ex) {
		}
	}

	/**
	 * It makes the Jtextfield, which corresponds to the position that the time
	 * is going to save, editable.
	 * 
	 * @param newTime
	 *            this parameter is the new time to save.
	 */
	public void setProperties(int newTime) {
		for (int j = NUM_OF_RECORDS - 1; (j >= 1) && (j > pos); j--) {
			names[j].setText(names[j - 1].getText());
			times[j].setText(times[j - 1].getText());
		}
		names[pos].setEditable(true);
		names[pos].getCaret().moveDot(names[pos].getText().length());
		names[pos].setBorder(BorderFactory.createLineBorder(Color.black));
		times[pos].setText(newTime + "");
		names[pos].getCaret().setVisible(true);
		names[pos].requestFocus();
		names[pos].select(0, names[pos].getText().length());
		names[pos].addKeyListener(this);
		jButton.setEnabled(false);
	}

	/**
	 * 
	 * @param
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * if a enter is pressed in a JtextField then it becomes no-editable. Then
	 * the function stores the new name.
	 * 
	 * @param e
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() != KeyEvent.VK_ENTER) {
			return;
		}

		names[pos] = ((JTextField) e.getSource());
		names[pos].setEditable(false);
		names[pos].setBorder(null);
		names[pos].getCaret().setVisible(false);
		names[pos].removeKeyListener(this);
		saveData();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * Checks if the parameter given is in the top ten times.
	 * 
	 * @param newTime
	 *            The time that program need to find out if it belongs to top
	 *            ten times.
	 * 
	 * @return
	 */
	public int IsTopTime(int newTime) {
		for (int i = 0; i < NUM_OF_RECORDS; i++) {
			if (!times[i].getText().isEmpty()) {
				if (newTime < (times[i].getText().equals(" ") ? Integer.MAX_VALUE
						: Integer.valueOf(times[i].getText()))) {
					pos = i;
					return i;
				}
			} else {
				pos = i;
				return i;
			}
		}
		pos = -1;
		return -1;
	}
}