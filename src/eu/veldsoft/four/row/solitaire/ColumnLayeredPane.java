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

/**
 * 
 * @author Todor Balabanov
 */
class ColumnLayeredPane extends CardStackLayeredPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	Column column = new Column();

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public Column getColumn() {
		return column;
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isEmpty() {
		return (column.isEmpty());
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public int length() {
		return (column.length());
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

		if (column.isEmpty()) {
			return;
		}

		for (int i = 0; i < column.cards.size(); i++) {
			Image image = CardComponent.cardsMapping.get(column.cards.get(i))
					.getImage();
			g.drawImage(image, 0, i * 25, null);
		}
	}
}