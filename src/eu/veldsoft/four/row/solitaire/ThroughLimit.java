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
 * Number of deck throughs for each difficulty. Three card draw adds 1 to each.
 * 
 * @author Todor Balabanov
 */
enum ThroughLimit {

	/**
	 * 
	 */
	EASY(3),

	/**
	 * 
	 */
	MEDIUM(2),

	/**
	 * 
	 */
	HARD(1);

	/**
	 * Number of allowed throughs.
	 */
	private int throughs;

	/**
	 * Sets the number of deck throughs.
	 * 
	 * @param throughs
	 * 
	 * @author Todor Balabanov
	 */
	private ThroughLimit(int throughs) {
		this.throughs = throughs;
	}

	/**
	 * Returns the number of deck throughs.
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public int getThroughs() {
		return throughs;
	}
}
