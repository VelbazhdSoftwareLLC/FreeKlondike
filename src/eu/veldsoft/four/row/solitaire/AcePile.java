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
 * Class: AcePile
 * 
 * Description: The AcePile class manages one of the four foundation stacks.
 * 
 * @author Matt Stephen
 */
class AcePile extends CardStack {

	/**
	 * Pile suit.
	 */
	private CardSuit suit;

	/**
	 * Sets the pile's suit to be equal to the argument suit. Can be Spades,
	 * Clubs, Hearts or Diamonds
	 * 
	 * @param suit
	 *            Suit to be used for the ace pile.
	 * 
	 * @author Todor Balabanov
	 */
	public AcePile(CardSuit suit) {
		this.suit = suit;
	}

	/**
	 * Returns the pile's suit.
	 * 
	 * @return suit The pile's suit.
	 * 
	 * @author Todor Balabanov
	 */
	public CardSuit getSuit() {
		return suit;
	}

	/**
	 * Pushes a card onto the foundation stack if possible.
	 * 
	 * @param card
	 *            Card to be pushed to the ace pile.
	 * 
	 * @return card The card that was pushed into the ace pile.
	 * 
	 * @author Todor Balabanov
	 */
	public Card push(Card card) {
		if (isValidMove(card) == false) {
			return null;
		}
		super.push(card);
		return (card);
	}

	/**
	 * Checks if its possible to put a certain card on top of a foundation pile.
	 * 
	 * @param card
	 *            Card to be checked.
	 * 
	 * @return true or false
	 * 
	 * @author Todor Balabanov
	 */
	@Override
	public boolean isValidMove(Card card) {
		if (card.getSuit().equals(suit) == false) {
			return false;
		}

		if (isEmpty() && card.getRank().equals(CardRank.ACE)) {
			return true;
		} else if (card.getRank().isLessByOneThan(peek().getRank())) {
			return true;
		}

		return false;
	}

	/**
	 * If trying to move an entire stack of two or more cards on top of one of
	 * the foundation piles returns false.
	 * 
	 * @param stack
	 *            Stack of cards.
	 * 
	 * @return false Always returns false because of the order of the cards.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(CardStack stack) {
		return false;
	}

	/**
	 * Checks if an ace pile is full.
	 * 
	 * @return true or false.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isFull() {
		if (isEmpty() == true || length() != 13) {
			return (false);
		}

		for (int i = 0; i < cards.size() - 1; i++) {
			if (cards.get(i).getColor() != cards.get(i + 1).getColor()) {
				return (false);
			}

			if (cards.get(i).getRank()
					.isLessByOneThan(cards.get(i + 1).getRank()) == true) {
				return (false);
			}
		}

		return (true);
	}
}
