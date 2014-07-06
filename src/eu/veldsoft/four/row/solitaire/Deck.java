/*
 This file is a part of Four Row Solitaire

 Copyright (C) 2010-2014 by Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov

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

/**
 * Class: Deck
 * 
 * Description: The Deck class holds all the Cards to form a 52 card deck.
 * 
 * @author Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov
 */
public class Deck {
	/**
	 * 
	 */
	private Deck() {
	}

	/**
	 * Shuffles the integers in the array. Each integer represents a card. Thus
	 * shuffling the cards. Then fills the deck with cards, equal to the
	 * elements in the array.
	 * 
	 * @param deck
	 */
	private static void shuffle(List<Card> deck) {
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
			deck.add(Card.valueBy(numbers[i]));
		}
	}

	/**
	 * Creates a deck, shuffles it and returns it.
	 * 
	 * @return
	 */
	public static List<Card> getFullShuffledDeck() {
		List<Card> deck = new LinkedList<Card>();
		shuffle(deck);

		return deck;
	}

	/**
	 * Creates a deck, then fills it with cards, based on the argument (list of
	 * integers). Each integer will represent a card in the deck. Then returns
	 * the deck.
	 * 
	 * @param numbers
	 * 
	 * @return
	 */
	public static List<Card> getDeckSubsetByCardNumbers(
			LinkedList<Integer> numbers) {
		List<Card> deck = new LinkedList<Card>();

		for (int i = 0; i < numbers.size(); i++) {
			if (numbers.get(i) > 0) {
				deck.add(Card.valueBy(numbers.get(i)));
			}
		}

		return deck;
	}
}
