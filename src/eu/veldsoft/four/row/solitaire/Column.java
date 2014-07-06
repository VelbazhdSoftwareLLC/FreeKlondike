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

/**
 * Class: Column
 * 
 * Description: The Column class manages a single column on the board.
 * 
 * @author Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov
 */
public class Column extends CardStack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * If its possible to move a card to the top of the column - moves it
	 * otherwise returns null.
	 * 
	 * @param card
	 * 
	 * @return
	 */
	public Card push(Card card) {
		if (isValidMove(card) == true) {
			super.push(card);
			return card;
		}

		return null;
	}

	/**
	 * Checks if its possible to move a card to the top of one of the four
	 * columns. The card must be lesser by one and to have different color than
	 * the current top card.
	 * 
	 * @param card
	 * 
	 * @return
	 */
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

	/**
	 * Checks if its possible to move a stack of cards on top of one of the four
	 * columns. The bottom card from the stack must be lesser by one than the
	 * top card from the column.
	 * 
	 * @param stack
	 * 
	 * @return
	 */
	public boolean isValidMove(CardStack stack) {
		return isValidMove(stack.peek());
	}

	/**
	 * Creates a stack and adds all of the available cards from a column into
	 * it. The available cards are: the first card and every other after the
	 * first one that is lesser and with different color than the previous one.
	 * Then returns the stack.
	 * 
	 * @return
	 */
	public CardStack getAvailableCards() {
		if (isEmpty() == true) {
			return (null);
		}

		CardStack stack = new CardStack();
		stack.addCard(cards.get(length() - 1));

		for (int index = length() - 2; index >= 0; index--) {
			Card card = cards.get(index);

			if (card.getColor() != stack.peek().getColor()
					&& card.getNumber().isLessByOneThan(
							stack.peek().getNumber())) {
				stack.addCard(card);
			} else {
				break;
			}
		}

		return stack;
	}
}
