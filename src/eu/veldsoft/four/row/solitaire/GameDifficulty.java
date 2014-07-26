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
 * 
 * @author Todor Balabanov
 */
enum GameDifficulty {

	/**
	 * Easy enum constant.
	 */
	EASY(1),

	/**
	 * Medium enum constant.
	 */
	MEDIUM(2),

	/**
	 * Hard enum constant.
	 */
	HARD(3);

	/**
	 * Difficulty value.
	 */
	private int value;

	/**
	 * Sets the game difficulty based on the argument value.
	 * 
	 * @param value
	 * 		Index to be used for the difficulty.
	 * 
	 * @author Todor Balabanov
	 */
	private GameDifficulty(int value) {
		this.value = value;
	}

	/**
	 * Returns the game difficulty value.
	 * 
	 * @return
	 * 		Difficulty value.
	 * 
	 * @author Todor Balabanov
	 */
	public int getValue() {
		return value;
	}
}
