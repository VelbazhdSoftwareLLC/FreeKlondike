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

import java.util.logging.Logger;

/**
 * Class: Card
 * 
 * Description: The Card class holds information pertaining to 1 out of the 52
 * cards per deck.
 * 
 * @author Matt Stephen
 */
class Card {

	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(Class.class
			.toString());

	/**
	 * Card instances.
	 */
	private static Card cards[] = { null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, };

	/**
	 * Initialize static data.
	 */
	static {
		for (int i = 0; i < cards.length; i++) {
			cards[i] = valueBy(i + 1);
		}
	}

	/**
	 * Card suit.
	 */
	private CardSuit suit;

	/**
	 * Card number.
	 */
	private CardRank rank;

	/**
	 * Card color.
	 */
	private CardColor color;

	/**
	 * 1 - 52
	 */
	private int fullCardNumber;

	/**
	 * Is the card currently set face-up.
	 */
	private boolean faceUp = false;

	/**
	 * Is the card currently highlighted.
	 */
	private boolean highlighted = false;

	/**
	 * To notify the discard pile of moves from the deck.
	 */
	private String location = "";

	/**
	 * It is used instead of constructor. Implement lazy initialization.
	 * 
	 * @param number
	 *            Will be used to set the card's number.
	 * 
	 * @return Card with updated card number.
	 * 
	 * @author Todor Balabanov
	 */
	static Card valueBy(int number) {
		int index = number - 1;

		if (cards[index] == null) {
			if (number >= 1 && number <= 13) {
				/*
				 * To make the cardNumber 1-13 you do not need to do anything.
				 */
				cards[index] = new Card(CardSuit.SPADES,
						CardRank.getValue(number), number);
			} else if (number >= 14 && number <= 26) {
				/*
				 * To make the cardNumber 1-13 instead of 14-26.
				 */
				cards[index] = new Card(CardSuit.CLUBS,
						CardRank.getValue(number - 13), number);
			} else if (number >= 27 && number <= 39) {
				/*
				 * To make the cardNumber 1-13 instead of 27-39.
				 */
				cards[index] = new Card(CardSuit.DIAMONDS,
						CardRank.getValue(number - 26), number);
			} else if (number >= 40 && number <= 52) {
				/*
				 * To make the cardNumber 1-13 instead of 40-52.
				 */
				cards[index] = new Card(CardSuit.HEARTS,
						CardRank.getValue(number - 39), number);
			} else {
				/*
				 * Let user know the card is invalid.
				 */
				LOGGER.info("Invalid card!");
			}
		}

		return (cards[index]);
	}

	/**
	 * Card constructor Sets the card's suit, number, full number and back
	 * image. Also sets it face-up.
	 * 
	 * @param suit
	 *            Card suit to be set.
	 * 
	 * @param number
	 *            Card number to be set.
	 * 
	 * @param fullNumber
	 *            Full card number to be set.
	 * 
	 * @author Todor Balabanov
	 */
	public Card(CardSuit suit, CardRank number, int fullNumber) {
		this.suit = suit;
		this.rank = number;
		this.fullCardNumber = fullNumber;
	}

	/**
	 * Sets the card's highlighted front image.
	 * 
	 * @author Todor Balabanov
	 */
	public void highlight() {
		highlighted = true;
	}

	/**
	 * Unhighlights a highlighted card. Sets back its unhighlighted face image.
	 * 
	 * @author Todor Balabanov
	 */
	public void unhighlight() {
		highlighted = false;

		setFaceUp();
	}

	/**
	 * Checks if the card is highlighted and returns the result(true/false).
	 * 
	 * @return highlighted Whether the card is highlighted or not (true or
	 *         false).
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isHighlighted() {
		return highlighted;
	}

	/**
	 * Sets the card face-up.
	 * 
	 * @author Todor Balabanov
	 */
	public void setFaceUp() {
		faceUp = true;
	}

	/**
	 * Sets the card face-down.
	 * 
	 * @author Todor Balabanov
	 */
	public void setFaceDown() {
		faceUp = false;
	}

	/**
	 * Checks if the card is facing up and returns the result(true/false).
	 * 
	 * @return faceUp Whether the card is set face-up or not (true or false).
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isFaceUp() {
		return faceUp;
	}

	/**
	 * Returns the card's suit.
	 * 
	 * @return suit Card's suit.
	 * 
	 * @author Todor Balabanov
	 */
	public CardSuit getSuit() {
		return suit;
	}

	/**
	 * Returns the card's color.
	 * 
	 * @return color Card's color.
	 * 
	 * @author Todor Balabanov
	 */
	public CardColor getColor() {
		return color;
	}

	/**
	 * Returns the card's rank.
	 * 
	 * @return rank Card's rank.
	 * 
	 * @author Todor Balabanov
	 */
	public CardRank getRank() {
		return rank;
	}

	/**
	 * Sets the card's rank.
	 * 
	 * @param rank
	 *            Used to set the card's rank.
	 * 
	 * @author Todor Balabanov
	 */
	public void setRank(CardRank rank) {
		this.rank = rank;
	}

	/**
	 * Sets the card's suit.
	 * 
	 * @param suit
	 *            Used to set the card's suit.
	 * 
	 * @author Todor Balabanov
	 */
	public void setSuit(CardSuit suit) {
		this.suit = suit;
	}

	/**
	 * Sets the card's color.
	 * 
	 * @param color
	 *            Used to set the card's color.
	 * 
	 * @author Todor Balabanov
	 */
	public void setColor(CardColor color) {
		this.color = color;
	}

	/**
	 * Returns the card's full number.
	 * 
	 * @return fullCardNumber Card's full number (in range 1-52)
	 * 
	 * @author Todor Balabanov
	 */
	public int getFullNumber() {
		return fullCardNumber;
	}

	/**
	 * Notifies the discard pile of moves from the deck.
	 * 
	 * @return location Current card location.
	 * 
	 * @author Todor Balabanov
	 */
	public String getSource() {
		return location;
	}

	/**
	 * Used to set the location of a moved card.
	 * 
	 * @param source
	 *            Source to which the card has been moved.
	 * 
	 * @author Todor Balabanov
	 */
	public void setSource(String source) {
		location = source;
	}

	/**
	 * Clone a card, that includes the card's suit, number and full number.
	 * 
	 * @return this Copy of the card.
	 * 
	 * @author Todor Balabanov
	 */
	public Card clone() {
		return this;
	}
}
