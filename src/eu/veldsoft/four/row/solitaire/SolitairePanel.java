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

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Class: SolitairePanel
 * 
 * Description: The Solitaire Panel is the main playing field view.
 * 
 * @author Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov
 */
public class SolitairePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Board background.
	 */
	private Image background;

	/**
	 * Sets the field image.
	 */
	public SolitairePanel() {
		URL imageURL = this.getClass().getResource(
				"images/backgrounds/background"
						+ SolitaireBoard.backgroundNumber + ".jpg");

		if (imageURL != null) {
			background = new ImageIcon(imageURL).getImage();
		}
	}

	/**
	 * Used to change the background image, based on the argument back. The
	 * number represents a certain background image.
	 * 
	 * @param back
	 */
	public void changeBackground(int back) {
		SolitaireBoard.backgroundNumber = back;

		URL imageURL = this.getClass().getResource(
				"images/backgrounds/background" + back + ".jpg");

		if (imageURL != null) {
			background = new ImageIcon(imageURL).getImage();
		}

		repaint();
	}

	/**
	 * Draws the board's background.
	 * 
	 * @param g
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}
}
