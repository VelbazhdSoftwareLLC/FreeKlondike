/*
 This file is a part of Four Row Solitaire

 Copyright (C) 2010-2014 by Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov, Vanya Gyaurova, Plamena Popova, Hristiana Kalcheva, Yana Genova

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
 * Card rank enum.
 * 
 * @author Todor Balabanov
 */
enum CardRank {

	/**
	 * Ace enum constant.
	 */
	ACE(1),

	/**
	 * Two enum constant.
	 */
	TWO(2),

	/**
	 * Three enum constant.
	 */
	THREE(3),

	/**
	 * Four enum constant.
	 */
	FOUR(4),

	/**
	 * Five enum constant.
	 */
	FIVE(5),

	/**
	 * Six enum constant.
	 */
	SIX(6),

	/**
	 * Seven enum constant.
	 */
	SEVEN(7),

	/**
	 * Eight enum constant.
	 */
	EIGHT(8),

	/**
	 * Nine enum constant.
	 */
	NINE(9),

	/**
	 * Ten enum constant.
	 */
	TEN(10),

	/**
	 * Jack enum constant.
	 */
	JACK(11),

	/**
	 * Queen enum constant.
	 */
	QUEEN(12),

	/**
	 * King enum constant.
	 */
	KING(13);

	/**
	 * Rank index.
	 */
	private int index;

	/**
	 * Returns the card's rank based on the argument index.
	 * 
	 * @param index
	 *            Index to be matched with enum constant.
	 * 
	 * @return Matched enum constant.
	 * 
	 * @author Todor Balabanov
	 */
	public static CardRank getValue(int index) {
		switch (index) {
		case 1:
			return ACE;
		case 2:
			return TWO;
		case 3:
			return THREE;
		case 4:
			return FOUR;
		case 5:
			return FIVE;
		case 6:
			return SIX;
		case 7:
			return SEVEN;
		case 8:
			return EIGHT;
		case 9:
			return NINE;
		case 10:
			return TEN;
		case 11:
			return JACK;
		case 12:
			return QUEEN;
		case 13:
			return KING;
		}

		return null;
	}

	/**
	 * Setting the card's rank based on the argument index. The argument
	 * represents one of the 13 ranks.
	 * 
	 * @param index
	 *            Index to be used for the rank.
	 * 
	 * @author Todor Balabanov
	 */
	private CardRank(int index) {
		this.index = index;
	}

	/**
	 * Returns the card's rank.
	 * 
	 * @return index Rank index.
	 * 
	 * @author Todor Balabanov
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Checks if the current card we're holding is lesser by one than the
	 * current top card on one of the columns.
	 * 
	 * @param card
	 *            Current card we're holding.
	 * 
	 * @return True if the current card is lesser by one than the top card on
	 *         the column, otherwise false.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isLessByOneThan(final CardRank card) {
		if (this == TWO && card == ACE) {
			return true;
		}
		if (this == THREE && card == TWO) {
			return true;
		}
		if (this == FOUR && card == THREE) {
			return true;
		}
		if (this == FIVE && card == FOUR) {
			return true;
		}
		if (this == SIX && card == FIVE) {
			return true;
		}
		if (this == SEVEN && card == SIX) {
			return true;
		}
		if (this == EIGHT && card == SEVEN) {
			return true;
		}
		if (this == NINE && card == EIGHT) {
			return true;
		}
		if (this == TEN && card == NINE) {
			return true;
		}
		if (this == JACK && card == TEN) {
			return true;
		}
		if (this == QUEEN && card == JACK) {
			return true;
		}
		if (this == KING && card == QUEEN) {
			return true;
		}

		return false;
	}

	/**
	 * Checks if the current card we're holding is greater by one than the top
	 * card on one of the foundation piles.
	 * 
	 * @param card
	 *            Current card we're holding.
	 * 
	 * @return True if the current card we're holding is greater by one than the
	 *         current top card on one of the foundation piles, otherwise false.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isGreaterByOneThan(final CardRank card) {
		if (card == TWO && this == ACE) {
			return true;
		}
		if (card == THREE && this == TWO) {
			return true;
		}
		if (card == FOUR && this == THREE) {
			return true;
		}
		if (card == FIVE && this == FOUR) {
			return true;
		}
		if (card == SIX && this == FIVE) {
			return true;
		}
		if (card == SEVEN && this == SIX) {
			return true;
		}
		if (card == EIGHT && this == SEVEN) {
			return true;
		}
		if (card == NINE && this == EIGHT) {
			return true;
		}
		if (card == TEN && this == NINE) {
			return true;
		}
		if (card == JACK && this == TEN) {
			return true;
		}
		if (card == QUEEN && this == JACK) {
			return true;
		}
		if (card == KING && this == QUEEN) {
			return true;
		}

		return false;
	}
}
