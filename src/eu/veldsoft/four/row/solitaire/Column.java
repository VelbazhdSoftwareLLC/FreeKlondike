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

/**
 * Class: Column
 * 
 * Description: The Column class manages a single column on the board.
 * 
 * @author Matt Stephen
 */
public class Column extends CardStack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Card push(Card card) {
		if (isValidMove(card) == true) {
			super.push(card);
			return card;
		}

		return null;
	}

	public boolean isValidMove(Card card) {
		if (isEmpty() == true && card.getNumber().equals(CardRank.KING)) {
			return true;
		}

		if (isEmpty() == false && card.getColor() != peek().getColor()
				&& card.getNumber().isGreaterByOneThan(peek().getNumber())) {
			return true;
		}

		return false;
	}

	public boolean isValidMove(CardStack stack) {
		return isValidMove(stack.peek());
	}

	public CardStack getAvailableCards() {
		if (isEmpty() == true) {
			return (null);
		}

		CardStack stack = new CardStack();
		boolean cardsMatch = true;
		int index = length() - 1;

		stack.addCard(cards.get(index));

		do {
			index--;

			if (index >= 0) {
				Card card = cards.get(index);

				if (card.getColor() != stack.peek().getColor()
						&& card.getNumber().isLessByOneThan(
								stack.peek().getNumber())) {
					stack.addCard(card);
				} else {
					cardsMatch = false;
				}
			} else {
				cardsMatch = false;
			}

		} while (cardsMatch);

		return stack;
	}
}