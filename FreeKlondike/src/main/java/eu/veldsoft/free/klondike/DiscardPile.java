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
 * Class: DiscardPile
 * 
 * Description: The DiscardPile class manages the stack of Cards discarded from
 * the deck.
 * 
 * @author Matt Stephen
 */
class DiscardPile extends CardStack {

	/**
	 * Stack of cards.
	 */
	private Vector<Card> cards = new Vector<Card>();

	/**
	 * Cards left from the last draw from the deal deck.
	 */
	int cardsLeftFromDraw = 0;

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
	 * Returns the cards left from the last draw from the deal deck.
	 * 
	 * @return cardsLeftFromDraw Cards left.
	 * 
	 * @author Todor Balabanov
	 */
	public int getNumViewableCards() {
		return cardsLeftFromDraw;
	}

	/**
	 * Sets the number of cards left from the last draw from the deck.
	 * 
	 * @param numViewableCards
	 *            Sets the number of viewable cards.
	 * 
	 * @author Todor Balabanov
	 */
	public void setView(int numViewableCards) {
		cardsLeftFromDraw = numViewableCards;
	}

	/**
	 * Adds a card to the pile of currently viewable cards.
	 * 
	 * @param card
	 *            Card to be added.
	 * 
	 * @author Todor Balabanov
	 */
	public void addCard(Card card) {
		card.setFaceUp();
		cardsLeftFromDraw++;
		cards.add(card);
	}

	/**
	 * Adds stack of cards to the pile of currently viewable cards.
	 * 
	 * @param stack
	 *            Stack of cards to be added.
	 * 
	 * @author Todor Balabanov
	 */
	public void addStack(CardStack stack) {
		for (int i = stack.length(); i > 0; i--) {
			Card card = stack.pop();
			addCard(card);
		}
	}

	/**
	 * Adds a card to the pile of currently viewable cards and returns the card
	 * added.
	 * 
	 * @param card
	 *            Card to be added.
	 * 
	 * @return The added card.
	 * 
	 * @author Todor Balabanov
	 */
	public Card push(Card card) {
		card.setFaceUp();
		if (SolitaireBoard.drawCount == 1) {
			cardsLeftFromDraw = 0;
		}

		addCard(card);
		card.setSource("");
		return card;
	}

	/**
	 * Adds a stack of cards to the pile of currently viewable cards and returns
	 * the cards added.
	 * 
	 * @param stack
	 *            Stack to be added.
	 * 
	 * @return stack The added stack.
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack push(CardStack stack) {
		if (SolitaireBoard.drawCount != 1
				|| (SolitaireBoard.drawCount == 1 && stack.length() == 1)) {
			cardsLeftFromDraw = 0;

			while (stack.isEmpty() == false) {
				push(stack.pop());
			}
		}

		return stack;
	}

	/**
	 * Pops cards out of the stack of viewable cards.
	 * 
	 * @return card Popped card.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized Card pop() {
		Card card = peek();
		cards.remove(cards.size() - 1);

		/*
		 * To make the display of multiple cards correct (After a player removes
		 * the top card of draw 3, it shouldn't display the top 3 cards).
		 */
		if (cardsLeftFromDraw > 0) {
			cardsLeftFromDraw--;
		} else {
			cardsLeftFromDraw = 0;
		}

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
		CardStack temp = new DiscardPile();
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
		CardStack temp = new DiscardPile();
		int index = length() - numCards;

		for (int i = length(); i > index; i--) {
			temp.push(getCardAtLocation(cards.size() - i - 1).clone());
			getCardAtLocation(cards.size() - i - 1).highlight();
		}

		return temp;
	}

	/**
	 * Used to undo the pop.
	 * 
	 * @return The card to be returned.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized Card undoPop() {
		return super.pop();
	}

	/**
	 * Checks if a card move is valid.
	 * 
	 * @param card
	 *            Card to be checked.
	 * 
	 * @return True if the move is valid or false if it isn't.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(Card card) {
		if (card.getSource().equals("Deck")) {
			return true;
		}

		return false;
	}

	/**
	 * The stack moves are always false.
	 * 
	 * @param stack
	 *            Stack of cards to be checked.
	 * 
	 * @return False.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(CardStack stack) {
		return false;
	}

	/**
	 * Returns the stack of available cards.
	 * 
	 * @return stack Stack of cards.
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack getAvailableCards() {
		if (isEmpty() == true) {
			return (null);
		}

		CardStack stack = new DiscardPile();
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
