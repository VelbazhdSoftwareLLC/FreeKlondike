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
import java.awt.Point;
import java.util.LinkedList;

import javax.swing.JOptionPane;

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
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Discard pile reference.
	 */
	private DiscardPile discardPile;

	/**
	 * Counter. Counts how many times we've gone through the deck.
	 */
	private int numTimesThroughDeck = 1;

	/**
	 * Deck through limit. An integer, representing the allowed number of deck
	 * throughs.
	 */
	private int deckThroughLimit;

	/**
	 * True by default. Keeps track if the deck is redealable.
	 */
	private boolean redealable = true;

	/**
	 * Sets the discard pile and the deck through limit, based on the draw
	 * count.
	 * 
	 * @param discard
	 * 
	 * @author Todor Balabanov
	 */
	public DealDeck(DiscardPile discard) {
		discardPile = discard;

		if (SolitaireBoard.drawCount == 3) {
			deckThroughLimit = ThroughLimit.MEDIUM.getThroughs() + 1;
		} else {
			deckThroughLimit = ThroughLimit.MEDIUM.getThroughs();
		}
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
	 * @return
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
	 * 
	 * @author Todor Balabanov
	 */
	public void setDeck(LinkedList<CardComponent> cards) {
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setFaceDown();
			addCard(cards.get(i));
		}
	}

	/**
	 * Used to set the deck through limit based on the draw count.
	 * 
	 * @param draw
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
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public boolean hasDealsLeft() {
		return redealable;
	}

	/**
	 * Pops card(s) out of the deal deck based on the draw count. Pushes the
	 * card(s) into the discard pile. When the deck through limit has been
	 * reached, displays an error dialog, that notifies the user. Then forbids
	 * the pops from the deal deck.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized CardComponent pop() {
		if (isEmpty() == false) {
			/*
			 * Verify there are still cards remaining.
			 */
			if (SolitaireBoard.drawCount == 1) {
				CardComponent card = super.pop();

				card.setFaceUp();
				discardPile.push(card);

				this.repaint();
				return card;
			} else {
				int tempDrawCount = SolitaireBoard.drawCount;
				CardStack tempStack = new CardStack();

				while (SolitaireBoard.drawCount > 1 && tempDrawCount > 0
						&& isEmpty() == false) {
					CardComponent card = super.pop();

					card.setFaceUp();
					tempStack.push(card);

					tempDrawCount--;
				}

				/*
				 * To put the cards back in order because the previous step
				 * reversed them.
				 */
				CardStack tempStack2 = new CardStack();

				for (int i = tempStack.length(); i > 0; i--) {
					tempStack2.push(tempStack.pop());
				}

				discardPile.push(tempStack2);

				this.repaint();
				return discardPile.peek();
			}
		} else if (discardPile.isEmpty() == false
				&& numTimesThroughDeck < deckThroughLimit) {
			for (int i = discardPile.length(); i > 0; i--) {
				CardComponent card = discardPile.pop();
				card.setFaceDown();
				card.getCard().setSource("Deck");
				push(card);
			}

			discardPile.repaint();
			numTimesThroughDeck++;
		} else if (numTimesThroughDeck >= deckThroughLimit) {
			redealable = false;
			JOptionPane.showMessageDialog(null,
					"You have reached your deck through limit.");
		}

		this.repaint();
		return null;
	}

	/**
	 * Used to undo the last move if it was a reset on the discard pile.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized void undoPop() {
		while (isEmpty() == false) {
			CardComponent card = super.pop();
			card.setFaceUp();
			discardPile.push(card);
		}

		undone();

		if (redealable == false) {
			redealable = true;
		}

		discardPile.repaint();
		this.repaint();
	}

	/**
	 * Returns a clicked card.
	 * 
	 * @param point
	 * 
	 * @author Todor Balabanov
	 */
	public CardComponent getCardAtLocation(Point point) {
		return peek();
	}

	/**
	 * Checks if a certain card move is valid. Always returns false.
	 * 
	 * @param card
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(CardComponent card) {
		return false;
	}

	/**
	 * Checks if a card stack move is valid. Always returns false.
	 * 
	 * @param stack
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(CardStack stack) {
		return false;
	}

	/**
	 * Paint procedure.
	 * 
	 * @param g
	 * 
	 * @author Todor Balabanov
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (isEmpty() == true) {
			return;
		}

		g.drawImage(getCardAtLocation(length() - 1).getImage(), 0, 0, null);
	}
}
