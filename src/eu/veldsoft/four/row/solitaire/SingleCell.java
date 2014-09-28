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

import java.util.Vector;

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
	 * Stack of cards.
	 */
	private Vector<Card> cards = new Vector<Card>();

	/**
	 * Returns the pile's suit.
	 * 
	 * @return suit The pile's suit.
	 * 
	 * @author Todor Balabanov
	 */
	public Vector<Card> getCards() {
		return cards;
	}

	/**
	 * For starting the game.
	 * 
	 * Used to add a card to a stack.
	 * 
	 * @param card
	 *            Card to be added.
	 * 
	 * @author Todor Balabanov
	 */
	public void addCard(Card card) {
		card.setFaceUp();
		cards.add(card);
	}

	/**
	 * Pops the top card out of a stack.
	 * 
	 * @return card The popped card.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized Card pop() {
		Card card = peek();
		cards.remove(cards.size() - 1);

		return card;
	}

	/**
	 * Pops the top card out of a stack if possible. If not - returns null.
	 * 
	 * @return Card or null.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized Card peek() {
		if (cards.isEmpty()) {
			return null;
		}

		return cards.lastElement();
	}

	/**
	 * Checks if a stack is empty (has no cards inside).
	 * 
	 * @return True or false, based on if the stack is empty or not.
	 */
	public boolean isEmpty() {
		return cards.size() == 0;
	}

	/**
	 * Returns the stack's length.
	 * 
	 * @return Stack's length.
	 * 
	 * @author Todor Balabanov
	 */
	public int length() {
		return cards.size();
	}

	/**
	 * Searches the stack for a specific card and returns its location in the
	 * stack.
	 * 
	 * @param card
	 *            The card to be matched.
	 * 
	 * @return The location of the card or -1 if the card can't be found within
	 *         the stack.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized int search(Card card) {
		int i = cards.lastIndexOf(card);

		if (i >= 0) {
			return cards.size() - i;
		}

		return -1;
	}

	/**
	 * Returns the card located at a specified location within the stack.
	 * 
	 * @param index
	 *            Location within the stack.
	 * 
	 * @return The card at this location. Or null if the index is greater than
	 *         the stack's size.
	 * 
	 * @author Todor Balabanov
	 */
	public Card getCardAtLocation(int index) {
		if (index < cards.size()) {
			return cards.get(index);
		}

		return null;
	}

	/**
	 * Verifies that the card is a part of a valid stack.
	 * 
	 * @param index
	 *            Index of the card to be verified.
	 * 
	 * @return true or false
	 * 
	 * @author Todor Balabanov
	 */
	boolean isValidCard(int index) {
		if (index >= cards.size()) {
			return false;
		}

		for (int i = index; i < cards.size() - 1; i++) {
			/*
			 * Cards are not opposite colors or decreasing in value correctly.
			 */
			if (cards.get(i).getColor() == cards.get(i + 1).getColor()
					|| cards.get(i).getRank()
							.isLessByOneThan(cards.get(i + 1).getRank()) == false) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Searches the stack for a specific card. Creates a new temporary stack.
	 * Clones the cards from the end towards the beginning of the stack into the
	 * temp stack. Stops after it reaches the specific card.
	 * 
	 * @param card
	 *            Card to look for.
	 * 
	 * @return Stack of cards.
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack getStack(Card card) {
		CardStack temp = new SingleCell();
		int index = search(card);

		for (int i = 0; i < index; i++) {
			temp.push(getCardAtLocation(cards.size() - i - 1).clone());
			getCardAtLocation(cards.size() - i - 1).highlight();
		}

		return temp;
	}

	/**
	 * Searches the stack for a specified location, creates a temporary stack,
	 * Clones the cards from the end towards the begining of the stack, stops
	 * when it reaches the specified location.
	 * 
	 * @param numCards
	 *            Index.
	 * 
	 * @return Stack of cards.
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack getStack(int numCards) {
		CardStack temp = new SingleCell();
		int index = length() - numCards;

		for (int i = length(); i > index; i--) {
			temp.push(getCardAtLocation(cards.size() - i - 1).clone());
			getCardAtLocation(cards.size() - i - 1).highlight();
		}

		return temp;
	}

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
		card.setFaceUp();
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

		CardStack stack = new SingleCell();
		stack.addCard(peek());

		return stack;
	}

	/**
	 * Returns the first card from a stack.
	 * 
	 * @return card The first card from the stack of cards.
	 * 
	 * @author Todor Balabanov
	 */
	public Card getBottom() {
		return cards.firstElement();
	}

	/**
	 * Highlight cards according stack rules.
	 * 
	 * @author Todor Balabanov
	 */
	void highlight(int index) {
		if (isEmpty() == false) {
			peek().highlight();
		}
	}
}
