/*
 This file is a part of Four Row Solitaire

 Copyright (C) 2010-2014 by Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov, Vanya Gyaurova, Plamena Popova, Hristiana Kalcheva

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
import java.awt.Point;

/**
 * 
 * @author Todor Balabanov
 */
class AcePileLayeredPane extends CardStackLayeredPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	AcePile acePile = null;

	/**
	 * Sets the pile's suit to be equal to the argument suit. Can be Spades,
	 * Clubs, Hearts or Diamonds
	 * 
	 * @param suit
	 *            Suit to be used for the ace pile.
	 * 
	 * @author Todor Balabanov
	 */
	public AcePileLayeredPane(CardSuit suit) {
		acePile = new AcePile(suit);
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public AcePile getAcePile() {
		return acePile;
	}

	/**
	 * Returns the top card from a stack of cards. The card must be clicked
	 * first.
	 * 
	 * @param point
	 *            The location of the mouse click.
	 * 
	 * @return card The card located at the location of the mouse click.
	 * 
	 * @author Todor Balabanov
	 */
	public CardComponent getCardAtLocation(Point point) {
		return CardComponent.cardsMapping.get(acePile.peek());
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isEmpty() {
		return (acePile.isEmpty());
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public int length() {
		return (acePile.length());
	}

	/**
	 * Paint procedure.
	 * 
	 * @param g
	 *            Graphic context.
	 * 
	 * @author Todor Balabanov
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		for (int i = 0; i < acePile.length(); i++) {
			Image image = CardComponent.cardsMapping.get(
					acePile.getCardAtLocation(i)).getImage();
			g.drawImage(image, 0, 0, null);
		}
	}
}