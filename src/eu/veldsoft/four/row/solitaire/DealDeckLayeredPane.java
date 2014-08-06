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
import java.awt.Point;

import javax.swing.JOptionPane;

/**
 * 
 * @author Todor Balabanov
 */
class DealDeckLayeredPane extends CardStackLayeredPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	DealDeck dealDeck = null;

	/**
	 * 
	 */
	DiscardPileLayeredPane discard = null;

	/**
	 * Sets the discard pile and the deck through limit, based on the draw
	 * count.
	 * 
	 * @param discard
	 *            Sets the discard pile.
	 * 
	 * @author Todor Balabanov
	 */
	public DealDeckLayeredPane(DiscardPileLayeredPane discard) {
		this.discard = discard;
		dealDeck = new DealDeck(discard.getDiscardPile());
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public DealDeck getDealDeck() {
		return dealDeck;
	}

	/**
	 * Returns a clicked card.
	 * 
	 * @param point
	 *            The location of the mouse click.
	 * 
	 * @author Todor Balabanov
	 */
	public CardComponent getCardAtLocation(Point point) {
		return CardComponent.cardsMapping.get(dealDeck.peek());
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isEmpty() {
		return (dealDeck.isEmpty());
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public int length() {
		return (dealDeck.length());
	}

	/**
	 * Pops card(s) out of the deal deck based on the draw count. Pushes the
	 * card(s) into the discard pile. When the deck through limit has been
	 * reached, displays an error dialog, that notifies the user. Then forbids
	 * the pops from the deal deck.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized CardComponent pop() {
		CardComponent result = CardComponent.cardsMapping.get(dealDeck.pop());

		if (isEmpty() == true) {
			return (null);
		}

		repaint();
		discard.repaint();

		if (dealDeck.numTimesThroughDeck >= dealDeck.deckThroughLimit) {
			JOptionPane.showMessageDialog(null,
					"You have reached your deck through limit.");
		}

		return (result);
	}

	/**
	 * Used to undo the last move if it was a reset on the discard pile.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized void undoPop() {
		dealDeck.undoPop();

		discard.repaint();
		this.repaint();
	}

	/**
	 * Paint procedure.
	 * 
	 * @param g
	 *            Paint.
	 * 
	 * @author Todor Balabanov
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (dealDeck.isEmpty() == true) {
			return;
		}

		g.drawImage(
				CardComponent.cardsMapping.get(
						dealDeck.getCardAtLocation(dealDeck.length() - 1))
						.getImage(), 0, 0, null);
	}

}