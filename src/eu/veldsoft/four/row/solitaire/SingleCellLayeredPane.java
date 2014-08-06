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

/**
 * 
 * @author Todor Balabanov
 */
class SingleCellLayeredPane extends CardStackLayeredPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	SingleCell singleCell = new SingleCell();

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public SingleCell getSingleCell() {
		return singleCell;
	}

	/**
	 * Returns the card located at the coordinates of a mouse click.
	 * 
	 * @param p
	 *            Location of a mouse click.
	 * 
	 * @return peek() Card.
	 * 
	 * @author Todor Balabanov
	 */
	public CardComponent getCardAtLocation(Point p) {
		return CardComponent.cardsMapping.get(singleCell.peek());
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isEmpty() {
		return (singleCell.isEmpty());
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public int length() {
		return (singleCell.length());
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

		if (singleCell.isEmpty()) {
			return;
		}

		g.drawImage(
				CardComponent.cardsMapping.get(
						singleCell.cards.get(singleCell.cards.size() - 1))
						.getImage(), 0, 0, null);
	}
}