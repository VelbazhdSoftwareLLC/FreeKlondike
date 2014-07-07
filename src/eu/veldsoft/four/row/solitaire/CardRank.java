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

/**
 * 
 * @author Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov
 */
public enum CardRank {
	/**
	 * Enum list.
	 */
	ACE(1),
	/**
	 * 
	 */
	TWO(2),
	/**
	 * 
	 */
	THREE(3),
	/**
	 * 
	 */
	FOUR(4),
	/**
	 * 
	 */
	FIVE(5),
	/**
	 * 
	 */
	SIX(6),
	/**
	 * 
	 */
	SEVEN(7),
	/**
	 * 
	 */
	EIGHT(8),
	/**
	 * 
	 */
	NINE(9),
	/**
	 * 
	 */
	TEN(10),
	/**
	 * 
	 */
	JACK(11),
	/**
	 * 
	 */
	QUEEN(12),
	/**
	 * 
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
	 * 
	 * @return
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
	 */
	private CardRank(int index) {
		this.index = index;
	}

	/**
	 * Returns the card's rank.
	 * 
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Checks if the current card we're holding is lesser than the current top
	 * card on one of the columns.
	 * 
	 * @param card
	 * 
	 * @return
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
	 * Checks if the current card we're holding is greater than the top card on
	 * one of the foundation piles.
	 * 
	 * @param card
	 * 
	 * @return
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
