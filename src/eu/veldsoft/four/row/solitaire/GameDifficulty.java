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
public enum GameDifficulty {
	/**
	 * 
	 */
	EASY(1),
	/**
	 * 
	 */
	MEDIUM(2),
	/**
	 * 
	 */
	HARD(3);
	/**
	 * 
	 */
	private int value;

	/**
	 * Sets the game difficulty based on the argument value.
	 * 
	 * @param value
	 */
	private GameDifficulty(int value) {
		this.value = value;
	}

	/**
	 * Returns the game difficulty value.
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}
}
