/*
 This file is a part of Four Row Solitaire

 Copyright (C) 2010 by Matt Stephen

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
 * Class: AcePile
 * 
 * Description: The AcePile class manages one of the four foundation stacks.
 * 
 * @author Matt Stephen
 */
public class AcePile extends CardStack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Pile suit.
	 */
	private CardSuit suit;

	public AcePile(CardSuit suit) {
		this.suit = suit;
	}

	public CardSuit getSuit() {
		return suit;
	}

	public Card push(Card card) {
		if (isValidMove(card) == true) {
			super.push(card);
			return (card);
		}

		return null;
	}

	public Card getCardAtLocation(Point p) {
		return peek();
	}

	public boolean isValidMove(Card card) {
		if (card.getSuit().equals(suit) == false) {
			return false;
		}

		if (isEmpty() && card.getNumber().equals(CardRank.ACE)) {
			return true;
		} else if (card.getNumber().isLessByOneThan(peek().getNumber())) {
			return true;
		}

		return false;
	}

	public boolean isValidMove(CardStack stack) {
		return false;
	}

	public void paint(Graphics g) {
		super.paint(g);

		for (int i = 0; i < length(); i++) {
			Image image = getCardAtLocation(i).getImage();
			g.drawImage(image, 0, 0, null);
		}
	}
}
