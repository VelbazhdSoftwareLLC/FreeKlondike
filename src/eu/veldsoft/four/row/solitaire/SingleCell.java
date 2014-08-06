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

/**
 * Class: SingleCell
 * 
 * Description: The SingleCell class manages an individual cell that can only
 * hold one card.
 * 
 * @author Matt Stephen
 */
class SingleCell extends CardStack {

	/**
	 * If the move is valid, pushes a card into the cell.
	 * 
	 * @param card
	 *            Card to be checked and pushed.
	 * 
	 * @return Card if successful, otherwise null.
	 * 
	 * @author Todor Balabanov
	 */
	public Card push(Card card) {
		if (isEmpty() == true) {
			super.push(card);
			return card;
		}

		return null;
	}

	/**
	 * Checks if the move is valid. If the cell is empty returns true, otherwise
	 * returns false.
	 * 
	 * @param card
	 *            Card to be checked.
	 * 
	 * @return True if valid or false if invalid.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(Card card) {
		if (isEmpty() == true) {
			return true;
		}

		return false;
	}

	/**
	 * If trying to move more than one card into the cell returns false.
	 * 
	 * @param stack
	 *            Stack of cards.
	 * 
	 * @return False, because the cell can hold maximum of one card.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(CardStack stack) {
		return false;
	}

	/**
	 * Returns the card from the cell. If there is no card - returns null.
	 * 
	 * @return Stack with card.
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack getAvailableCards() {
		if (isEmpty() == true) {
			return (null);
		}

		CardStack stack = new CardStack();
		stack.addCard(peek());

		return stack;
	}
}
