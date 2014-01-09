/*
 This file is a part of Four Row Solitaire

 Copyright (C) 2010 by Matt Stephen

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

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Class: Deck
 * 
 * Description: The Deck class holds all the Cards to form a 52 card deck.
 * 
 * @author Matt Stephen
 */
public class Deck {
	private static final Logger LOGGER = Logger.getLogger(Class.class
			.toString());

	private int deckNumber;

	private List<Card> deck = new LinkedList<Card>();

	public Deck(int deckNumber) {
		this.deckNumber = deckNumber;
		shuffle();
	}

	public List<Card> getDeck() {
		return deck;
	}

	public List<Card> getDeck(LinkedList<Integer> numbers) {
		deck = new LinkedList<Card>();

		for (int i = 0; i < numbers.size(); i++) {
			if (numbers.get(i) > 0) {
				createCard(numbers.get(i));
			}
		}

		return deck;
	}

	public void shuffle() {
		//TODO Use stronger shuffling algorithm.
		List<Integer> numberList = new LinkedList<Integer>();

		for (int i = 1; i <= 52; i++) {
			numberList.add(i);
		}

		while (numberList.isEmpty() == false) {
			int num = Common.PRNG.nextInt(numberList.size());

			int cardNumber = numberList.get(num);
			numberList.remove(num);

			createCard(cardNumber);
		}
	}

	private void createCard(int cardNumber) {
		if (cardNumber >= 1 && cardNumber <= 13) {
			/*
			 * To make the cardNumber 1-13 you do not need to do anything.
			 */
			deck.add(new Card(CardSuit.SPADES, CardRank.getValue(cardNumber),
					deckNumber, cardNumber));
		} else if (cardNumber >= 14 && cardNumber <= 26) {
			/*
			 * To make the cardNumber 1-13 instead of 14-26.
			 */
			cardNumber -= 13;
			deck.add(new Card(CardSuit.CLUBS, CardRank.getValue(cardNumber),
					deckNumber, cardNumber + 13));
		} else if (cardNumber >= 27 && cardNumber <= 39) {
			/*
			 * To make the cardNumber 1-13 instead of 27-39.
			 */
			cardNumber -= 26;
			deck.add(new Card(CardSuit.DIAMONDS, CardRank.getValue(cardNumber),
					deckNumber, cardNumber + 26));
		} else if (cardNumber >= 40 && cardNumber <= 52) {
			/*
			 * To make the cardNumber 1-13 instead of 40-52.
			 */
			cardNumber -= 39;
			deck.add(new Card(CardSuit.HEARTS, CardRank.getValue(cardNumber),
					deckNumber, cardNumber + 39));
		} else {
			/*
			 * Let user know the card is invalid.
			 */
			LOGGER.info("Invalid card!");
		}
	}
}