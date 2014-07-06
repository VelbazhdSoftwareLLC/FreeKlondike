/*
 This file is a part of Four Row Solitaire

 Copyright (C) 2010-2014 by Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov

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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Class: ChangeAppearance
 * 
 * Description: The ChangeAppearance class manages the ability to change the
 * background or card backs.
 * 
 * @author Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov
 */
public class ChangeAppearance extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Number of card backs.
	 */
	public static final int NUM_DECKS = 4;
	/**
	 * Number of card backgrounds.
	 */
	public static final int NUM_BACKGROUNDS = 3;
	/**
	 * Default deck number.
	 */
	public static final int FRS_DECK = 3;
	/**
	 * Default background number.
	 */
	public static final int FRS_BACKGROUND = 2;

	/**
	 * Array of buttons, used to select card back.
	 */
	private JRadioButton[] decks = new JRadioButton[NUM_DECKS];

	/**
	 * Array of buttons, used to select background.
	 */
	private JRadioButton[] backgrounds = new JRadioButton[NUM_BACKGROUNDS];

	/**
	 * OK button.
	 */
	private JButton ok = new JButton("Choose This Setup");

	/**
	 * Default deck number.
	 */
	public int deckNumber = 3;
	/**
	 * Default background number.
	 */
	public int backgroundNumber = 2;

	/**
	 * Exited.
	 */
	private boolean exited = true;

	/**
	 * To hold the image previews.
	 */
	private JLabel cardBackLabel = new JLabel();
	/**
	 * To hold the image previews.
	 */
	private JLabel backgroundLabel = new JLabel();

	/**
	 * Manages the Change Appearance dialog box.
	 * 
	 * @param parent
	 * 
	 * @param deckNumber
	 * 
	 * @param backgroundNumber
	 */
	public ChangeAppearance(JFrame parent, int deckNumber, int backgroundNumber) {
		setTitle("Change Appearance");
		setSize(400, 300);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(parent);

		this.deckNumber = deckNumber;
		this.backgroundNumber = backgroundNumber;
		setup();

		setVisible(true);
	}

	/**
	 * Manages the settings that can be changed via the Change Appearance menu.
	 * The menu is used to set card back image and background image.
	 */
	private void setup() {
		/*
		 * Card backs.
		 */
		JPanel cardBackPanel = new JPanel();
		cardBackPanel.setLayout(new FlowLayout());

		ButtonGroup cardBacks = new ButtonGroup();

		for (int i = 0; i < decks.length; i++) {
			decks[i] = new JRadioButton("Deck " + (i + 1));
			cardBacks.add(decks[i]);
			cardBackPanel.add(decks[i]);
			decks[i].addActionListener(this);
		}

		if (deckNumber <= decks.length) {
			decks[deckNumber - 1].setSelected(true);
		} else {
			decks[2].setSelected(true);
		}

		/*
		 * Backgrounds.
		 */
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setLayout(new FlowLayout());

		ButtonGroup backgroundsBG = new ButtonGroup();
		for (int i = 0; i < backgrounds.length; i++) {
			backgrounds[i] = new JRadioButton("Background " + (i + 1));
			backgroundsBG.add(backgrounds[i]);
			backgroundPanel.add(backgrounds[i]);
			backgrounds[i].addActionListener(this);
		}

		if (backgroundNumber <= NUM_BACKGROUNDS) {
			backgrounds[backgroundNumber - 1].setSelected(true);
		} else {
			backgrounds[0].setSelected(true);
		}

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(ok);

		cardBackLabel.setIcon(new ImageIcon(this.getClass().getResource(
				"images/cardbacks/cardback" + deckNumber + ".png")));
		JPanel cardBackViewer = new JPanel();
		cardBackViewer.add(cardBackLabel);

		backgroundLabel.setIcon(new ImageIcon(this.getClass().getResource(
				"images/backgrounds/background" + backgroundNumber
						+ "small.jpg")));
		JPanel backgroundViewer = new JPanel();
		backgroundViewer.add(backgroundLabel);

		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(2, 2, 0, 0));
		p2.add(cardBackPanel);
		p2.add(backgroundPanel);
		p2.add(cardBackViewer);
		p2.add(backgroundViewer);

		/*
		 * Put everything together.
		 */
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());

		JLabel note = new JLabel(
				"Note: Deck changes will take effect on new game");
		note.setHorizontalAlignment(JLabel.CENTER);

		p1.add(note, BorderLayout.NORTH);
		p1.add(p2, BorderLayout.CENTER);
		p1.add(buttonPanel, BorderLayout.SOUTH);

		add(p1);

		ok.addActionListener(this);
	}

	/**
	 * Returns the number that is used to set the card back image. The number
	 * represents the one of the images.
	 * 
	 * @return
	 */
	public int getDeckNumber() {
		if (!exited) {
			return deckNumber;
		}

		return -1;
	}

	/**
	 * Returns the number that is used to set the background image. The number
	 * represents one of the images.
	 * 
	 * @return
	 */
	public int getBackgroundNumber() {
		if (!exited) {
			return backgroundNumber;
		}

		return -1;
	}

	/**
	 * Sets active card back and/or background, depending on what's been
	 * selected from the menu.
	 * 
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == decks[0]) {
			deckNumber = 1;
			cardBackLabel.setIcon(new ImageIcon(this.getClass().getResource(
					"images/cardbacks/cardback" + deckNumber + ".png")));
		} else if (e.getSource() == decks[1]) {
			deckNumber = 2;
			cardBackLabel.setIcon(new ImageIcon(this.getClass().getResource(
					"images/cardbacks/cardback" + deckNumber + ".png")));
		} else if (e.getSource() == decks[2]) {
			deckNumber = 3;
			cardBackLabel.setIcon(new ImageIcon(this.getClass().getResource(
					"images/cardbacks/cardback" + deckNumber + ".png")));
		} else if (e.getSource() == decks[3]) {
			deckNumber = 4;
			cardBackLabel.setIcon(new ImageIcon(this.getClass().getResource(
					"images/cardbacks/cardback" + deckNumber + ".png")));
		}

		else if (e.getSource() == backgrounds[0]) {
			backgroundNumber = 1;
			backgroundLabel.setIcon(new ImageIcon(this.getClass().getResource(
					"images/backgrounds/background" + backgroundNumber
							+ "small.jpg")));
		} else if (e.getSource() == backgrounds[1]) {
			backgroundNumber = 2;
			backgroundLabel.setIcon(new ImageIcon(this.getClass().getResource(
					"images/backgrounds/background" + backgroundNumber
							+ "small.jpg")));
		} else if (e.getSource() == backgrounds[2]) {
			backgroundNumber = 3;
			backgroundLabel.setIcon(new ImageIcon(this.getClass().getResource(
					"images/backgrounds/background" + backgroundNumber
							+ "small.jpg")));
		}

		else if (e.getSource() == ok) {
			exited = false;
			setVisible(false);
		}
	}
}
