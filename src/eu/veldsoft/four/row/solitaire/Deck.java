/*
 This file is a part of Four Row Solitaire

 Copyright (C) 2010-2014 by Matt Stephen, Todor Balabanov

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
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * Class: Deck
 * 
 * Description: The Deck class holds all the Cards to form a 52 card deck.
 * 
 * @author Matt Stephen
 */
public class Deck {
	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(Class.class
			.toString());

	/**
	 * 
	 */
	private List<Card> deck = new LinkedList<Card>();

	/**
	 * 
	 * @return
	 */
	public List<Card> getDeck() {
		return deck;
	}

	/**
	 * 
	 * @param numbers
	 * 
	 * @return
	 */
	public List<Card> getDeck(LinkedList<Integer> numbers) {
		deck = new LinkedList<Card>();

		for (int i = 0; i < numbers.size(); i++) {
			if (numbers.get(i) > 0) {
				createCard(numbers.get(i));
			}
		}

		return deck;
	}

	/**
	 * 
	 */
	public void shuffle() {
		int numbers[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
				16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31,
				32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47,
				48, 49, 50, 51, 52 };

		/*
		 * Shuffle integer values.
		 */
		for (int last = numbers.length - 1, r = -1, swap = -1; last > 0; last--) {
			r = Common.PRNG.nextInt(last + 1);
			swap = numbers[last];
			numbers[last] = numbers[r];
			numbers[r] = swap;
		}

		for (int i = 0; i < numbers.length; i++) {
			createCard(numbers[i]);
		}
	}

	/**
	 * 
	 * @param cardNumber
	 */
	private void createCard(int cardNumber) {
		if (cardNumber >= 1 && cardNumber <= 13) {
			/*
			 * To make the cardNumber 1-13 you do not need to do anything.
			 */
			deck.add(new Card(CardSuit.SPADES, CardRank.getValue(cardNumber),
					cardNumber));
		} else if (cardNumber >= 14 && cardNumber <= 26) {
			/*
			 * To make the cardNumber 1-13 instead of 14-26.
			 */
			cardNumber -= 13;
			deck.add(new Card(CardSuit.CLUBS, CardRank.getValue(cardNumber),
					cardNumber + 13));
		} else if (cardNumber >= 27 && cardNumber <= 39) {
			/*
			 * To make the cardNumber 1-13 instead of 27-39.
			 */
			cardNumber -= 26;
			deck.add(new Card(CardSuit.DIAMONDS, CardRank.getValue(cardNumber),
					cardNumber + 26));
		} else if (cardNumber >= 40 && cardNumber <= 52) {
			/*
			 * To make the cardNumber 1-13 instead of 40-52.
			 */
			cardNumber -= 39;
			deck.add(new Card(CardSuit.HEARTS, CardRank.getValue(cardNumber),
					cardNumber + 39));
		} else {
			/*
			 * Let user know the card is invalid.
			 */
			LOGGER.info("Invalid card!");
		}
	}
}
