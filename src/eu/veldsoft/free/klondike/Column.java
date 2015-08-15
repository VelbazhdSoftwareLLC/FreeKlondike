/*
 This file is a part of Free Klondike

 Copyright (C) 2010-2014 by Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov, Vanya Gyaurova, Plamena Popova, Hristiana Kalcheva, Yana Genova

 Free Klondike is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Free Klondike is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with FreeKlondike.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.veldsoft.free.klondike;

import java.util.Vector;

/**
 * Class: Column
 * 
 * Description: The Column class manages a single column on the board.
 * 
 * @author Matt Stephen
 */
class Column extends CardStack {

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
		CardStack temp = new Column();
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
		CardStack temp = new Column();
		int index = length() - numCards;

		for (int i = length(); i > index; i--) {
			temp.push(getCardAtLocation(cards.size() - i - 1).clone());
			getCardAtLocation(cards.size() - i - 1).highlight();
		}

		return temp;
	}

	/**
	 * If its possible to move a card to the top of the column - moves it
	 * otherwise returns null.
	 * 
	 * @param card
	 *            Card to be moved.
	 * 
	 * @return Card if successful or null if not successful.
	 * 
	 * @author Todor Balabanov
	 */
	public Card push(Card card) {
		card.setFaceUp();
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
	 *            Card to be checked.
	 * 
	 * @return True or false.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(Card card) {
		if (isEmpty() == true && card.getRank().equals(CardRank.KING)) {
			return true;
		}

		if (isEmpty() == false && card.getColor() != peek().getColor()
				&& card.getRank().isGreaterByOneThan(peek().getRank())) {
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
	 *            Stack to be pushed.
	 * 
	 * @return True or false.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(CardStack stack) {
		return isValidMove(stack.peek());
	}

	/**
	 * Checks if its possible to move a stack of cards on top of one of the four
	 * columns. The bottom card from the stack must be lesser by one than the
	 * top card from the column.
	 * 
	 * @param stack
	 *            Stack to be pushed.
	 * 
	 * @return True or false.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(Vector<Card> stack) {
		return isValidMove(stack.lastElement());
	}

	/**
	 * Creates a stack and adds all of the available cards from a column into
	 * it. The available cards are: the first card and every other after the
	 * first one that is lesser and with different color than the previous one.
	 * Then returns the stack.
	 * 
	 * @return stack Stack of cards.
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack getAvailableCards() {
		if (isEmpty() == true) {
			return (null);
		}

		CardStack stack = new Column();
		stack.addCard(cards.get(length() - 1));

		for (int index = length() - 2; index >= 0; index--) {
			Card card = cards.get(index);

			if (card.getColor() != stack.peek().getColor()
					&& card.getRank().isLessByOneThan(stack.peek().getRank())) {
				stack.addCard(card);
			} else {
				break;
			}
		}

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
	 * Highlight cards according stack rules. In the columns variant it is very
	 * important all cards to be in the specific order.
	 * 
	 * @param index
	 *            Index of the card which should be leading the highlighting.
	 * 
	 * @author Todor Balabanov
	 */
	void highlight(int index) {
		if (isValidCard(index) == false) {
			return;
		}

		for (int i = index; i < cards.size(); i++) {
			cards.elementAt(i).highlight();
		}
	}
}
