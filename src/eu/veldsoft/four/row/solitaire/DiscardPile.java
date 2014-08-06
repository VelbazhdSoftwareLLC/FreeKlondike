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
 * Class: DiscardPile
 * 
 * Description: The DiscardPile class manages the stack of Cards discarded from
 * the deck.
 * 
 * @author Matt Stephen
 */
class DiscardPile extends CardStack {

	/**
	 * Cards left from the last draw from the deal deck.
	 */
	int cardsLeftFromDraw = 0;

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
		cardsLeftFromDraw++;
		super.addCard(card);
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
		Card card = super.pop();

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

		CardStack stack = new CardStack();
		stack.addCard(peek());

		return stack;
	}
}
