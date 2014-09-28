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

import java.util.List;
import java.util.Vector;

/**
 * Class: DealDeck
 * 
 * Description: The DealDeck class manages the leftover cards after the deal
 * out.
 * 
 * @author Matt Stephen
 */
class DealDeck extends CardStack {

	/**
	 * Stack of cards.
	 */
	private Vector<Card> cards = new Vector<Card>();

	/**
	 * Discard pile reference.
	 */
	private DiscardPile discardPile;

	/**
	 * Counter. Counts how many times we've gone through the deck.
	 */
	int numTimesThroughDeck = 1;

	/**
	 * Deck through limit. An integer, representing the allowed number of deck
	 * throughs.
	 */
	int deckThroughLimit;

	/**
	 * True by default. Keeps track if the deck is redealable.
	 */
	private boolean redealable = true;

	/**
	 * Sets the discard pile and the deck through limit, based on the draw
	 * count.
	 * 
	 * @param discard
	 *            Sets the discard pile.
	 * 
	 * @author Todor Balabanov
	 */
	public DealDeck(DiscardPile discard) {
		discardPile = discard;

		if (SolitaireBoard.drawCount == 3) {
			deckThroughLimit = ThroughLimit.EASY.getThroughs() + 1;
		} else {
			deckThroughLimit = ThroughLimit.EASY.getThroughs();
		}
	}

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
	 * Used to reset the numTimesThroughDeck counter.
	 * 
	 * @author Todor Balabanov
	 */
	public void reset() {
		numTimesThroughDeck = 1;
	}

	/**
	 * Used to decrease the numTimesThroughDeck counter.
	 * 
	 * @author Todor Balabanov
	 */
	private void undone() {
		numTimesThroughDeck--;
	}

	/**
	 * Returns the numTimesThroughDeck counter.
	 * 
	 * @return numTimesThroughDeck The number of times we've gone through the
	 *         deck.
	 * 
	 * @author Todor Balabanov
	 */
	public int getDeckThroughs() {
		return numTimesThroughDeck;
	}

	/**
	 * Sets the numTimesThroughDeck counter to be equal to the argument
	 * throughs.
	 * 
	 * @param throughs
	 *            To set how many times we've gone through the deck.
	 * 
	 * @author Todor Balabanov
	 */
	public void setDeckThroughs(int throughs) {
		numTimesThroughDeck = throughs;
	}

	/**
	 * Used to set the deal pile. Accepts a list of shuffled cards.
	 * 
	 * @param cards
	 *            List of shuffled cards.
	 * 
	 * @author Todor Balabanov
	 */
	public void setDeck(List<Card> cards) {
		for (Card card : cards) {
			card.setFaceDown();
			addCard(card);
		}
	}

	/**
	 * Used to set the deck through limit based on the draw count.
	 * 
	 * @param draw
	 *            Integer, based on which the deck through limit is set.
	 * 
	 * @author Todor Balabanov
	 */
	public void setDrawCount(int draw) {
		if (SolitaireBoard.drawCount == 3) {
			deckThroughLimit = ThroughLimit.MEDIUM.getThroughs() + 1;
		} else {
			deckThroughLimit = ThroughLimit.MEDIUM.getThroughs();
		}
	}

	/**
	 * Used to set the deck through limit based on the game difficulty.
	 * 
	 * @param difficulty
	 *            Used to set the game difficulty.
	 * 
	 * 
	 * @author Todor Balabanov
	 */
	public void setDifficulty(GameDifficulty difficulty) {
		if (difficulty == GameDifficulty.EASY) {
			deckThroughLimit = ThroughLimit.EASY.getThroughs();
		} else if (difficulty == GameDifficulty.HARD) {
			deckThroughLimit = ThroughLimit.HARD.getThroughs();
		} else if (difficulty == GameDifficulty.MEDIUM) {
			deckThroughLimit = ThroughLimit.MEDIUM.getThroughs();
		}

		/*
		 * Draw three has an extra deck through on top of the single card
		 * setting.
		 */
		if (SolitaireBoard.drawCount == 3) {
			deckThroughLimit++;
		}
	}

	/**
	 * Returns false if the deck through limit has been reached (no deals left).
	 * Otherwise returns true (there are deals left).
	 * 
	 * @return redealable True if the deck is redealable, false if it isn't.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean hasDealsLeft() {
		return redealable;
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
		card.setFaceDown();
		cards.add(card);
	}

	/**
	 * Pops card(s) out of the deal deck based on the draw count. Pushes the
	 * card(s) into the discard pile. When the deck through limit has been
	 * reached, displays an error dialog, that notifies the user. Then forbids
	 * the pops from the deal deck.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized Card pop() {
		if (isEmpty() == false) {
			/*
			 * Verify there are still cards remaining.
			 */
			if (SolitaireBoard.drawCount == 1) {
				Card card = peek();
				cards.remove(cards.size() - 1);

				card.setFaceUp();
				discardPile.push(card);

				return card;
			} else {
				int tempDrawCount = SolitaireBoard.drawCount;
				CardStack tempStack = new DealDeck(discardPile);

				while (SolitaireBoard.drawCount > 1 && tempDrawCount > 0
						&& isEmpty() == false) {
					Card card = peek();
					cards.remove(cards.size() - 1);

					card.setFaceUp();
					tempStack.push(card);

					tempDrawCount--;
				}

				/*
				 * To put the cards back in order because the previous step
				 * reversed them.
				 */
				CardStack tempStack2 = new DealDeck(discardPile);

				for (int i = tempStack.length(); i > 0; i--) {
					tempStack2.push(tempStack.pop());
				}

				discardPile.push(tempStack2);

				return discardPile.peek();
			}
		} else if (discardPile.isEmpty() == false
				&& numTimesThroughDeck < deckThroughLimit) {
			for (int i = discardPile.length(); i > 0; i--) {
				Card card = discardPile.pop();
				card.setFaceDown();
				card.setSource("Deck");
				push(card);
			}

			numTimesThroughDeck++;
		} else if (numTimesThroughDeck >= deckThroughLimit) {
			redealable = false;
		}

		return null;
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
		CardStack temp = new DealDeck(discardPile);
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
		CardStack temp = new DealDeck(discardPile);
		int index = length() - numCards;

		for (int i = length(); i > index; i--) {
			temp.push(getCardAtLocation(cards.size() - i - 1).clone());
			getCardAtLocation(cards.size() - i - 1).highlight();
		}

		return temp;
	}

	/**
	 * Used to undo the last move if it was a reset on the discard pile.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized void undoPop() {
		while (isEmpty() == false) {
			Card card = super.pop();
			card.setFaceUp();
			discardPile.push(card);
		}

		undone();

		if (redealable == false) {
			redealable = true;
		}
	}

	/**
	 * Checks if a certain card move is valid. Always returns false.
	 * 
	 * @param card
	 *            Card to be checked.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(Card card) {
		return false;
	}

	/**
	 * Checks if a card stack move is valid. Always returns false.
	 * 
	 * @param stack
	 *            Stack to be checked.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(CardStack stack) {
		return false;
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
	 * @author Todor Balabanov
	 */
	public void allFaceDown() {
		for (Card card : cards) {
			card.setFaceDown();
		}
	}

	/**
	 * Highlight cards according stack rules.
	 * 
	 * @author Todor Balabanov
	 */
	void highlight(int index) {
		/*
		 * Deal deck has no highlighting. All cards are face down and are not
		 * selectable.
		 */
	}
}
