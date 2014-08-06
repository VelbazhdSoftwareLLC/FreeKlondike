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
class DiscardPileLayeredPane extends CardStackLayeredPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	DiscardPile discardPile = new DiscardPile();

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public DiscardPile getDiscardPile() {
		return discardPile;
	}

	/**
	 * Returns a card located at the position of a mouse click.
	 * 
	 * @param p
	 *            Location of a mouse click.
	 * 
	 * @return peek() Card.
	 * 
	 * @author Todor Balabanov
	 */
	public CardComponent getCardAtLocation(Point p) {
		return CardComponent.cardsMapping.get(discardPile.peek());
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isEmpty() {
		return (discardPile.isEmpty());
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public int length() {
		return (discardPile.length());
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

		if (discardPile.isEmpty() == false && SolitaireBoard.drawCount == 1) {
			for (int i = 0; i < discardPile.length(); i++) {
				Image image = CardComponent.cardsMapping.get(
						discardPile.getCardAtLocation(i)).getImage();
				g.drawImage(image, 0, 0, null);
			}
		} else if (discardPile.isEmpty() == false
				&& SolitaireBoard.drawCount == 3) {
			if (discardPile.cardsLeftFromDraw > 0) {
				for (int i = 0; i < discardPile.length()
						- discardPile.cardsLeftFromDraw + 1; i++) {
					Image image = CardComponent.cardsMapping.get(
							discardPile.getCardAtLocation(i)).getImage();
					g.drawImage(image, 0, 0, null);
				}

				for (int i = discardPile.length()
						- discardPile.cardsLeftFromDraw + 1; i < discardPile
						.length(); i++) {
					Image image = CardComponent.cardsMapping.get(
							discardPile.getCardAtLocation(i)).getImage();

					if ((discardPile.cardsLeftFromDraw == 3 && i == discardPile
							.length() - 2)
							|| (discardPile.cardsLeftFromDraw == 2 && i == discardPile
									.length() - 1)) {
						g.drawImage(image, 15, 0, null);
					} else if (discardPile.cardsLeftFromDraw == 3
							&& i == discardPile.length() - 1) {
						g.drawImage(image, 30, 0, null);
					}
				}
			} else {
				for (int i = 0; i < discardPile.length(); i++) {
					Image image = CardComponent.cardsMapping.get(
							discardPile.getCardAtLocation(i)).getImage();
					g.drawImage(image, 0, 0, null);
				}
			}
		}
	}
}