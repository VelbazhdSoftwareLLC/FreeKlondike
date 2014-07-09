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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * Class: SolitaireLayout
 * 
 * Description: The SolitaireLayout class manages the layout for the Solitaire
 * board.
 * 
 * @author Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov
 */
public class SolitaireLayout implements LayoutManager {
	/**
	 * Names of columns.
	 * 
	 * Column One.
	 */
	public static final String COLUMN_ONE = "Column One";
	/**
	 * Column Two.
	 */
	public static final String COLUMN_TWO = "Column Two";
	/**
	 * Column Three.
	 */
	public static final String COLUMN_THREE = "Column Three";
	/**
	 * Column Four.
	 */
	public static final String COLUMN_FOUR = "Column Four";

	/**
	 * Spaces Ace Pile.
	 */
	public static final String SPADES_ACE_PILE = "Spaces Ace Pile";
	/**
	 * Clubs Ace Pile.
	 */
	public static final String CLUBS_ACE_PILE = "Clubs Ace Pile";
	/**
	 * Diamonds Ace Pile.
	 */
	public static final String DIAMONDS_ACE_PILE = "Diamonds Ace Pile";
	/**
	 * Hears Ace Pile.
	 */
	public static final String HEARTS_ACE_PILE = "Hears Ace Pile";

	/**
	 * Discard Pile.
	 */
	public static final String DISCARD_PILE = "Discard Pile";
	/**
	 * Deck.
	 */
	public static final String DECK = "Deck";

	/**
	 * Cell One.
	 */
	public static final String CELL_ONE = "Cell One";
	/**
	 * Cell Two.
	 */
	public static final String CELL_TWO = "Cell Two";
	/**
	 * Cell Three.
	 */
	public static final String CELL_THREE = "Cell Three";
	/**
	 * Cell Four.
	 */
	public static final String CELL_FOUR = "Cell Four";

	/**
	 * Column components. Column one.
	 */
	private Component colOne;
	/**
	 * Column Two.
	 */
	private Component colTwo;
	/**
	 * Column Three.
	 */
	private Component colThree;
	/**
	 * Column Four.
	 */
	private Component colFour;

	/**
	 * Spades Ace Pile.
	 */
	private Component aceSpades;
	/**
	 * Clubs Ace Pile.
	 */
	private Component aceClubs;
	/**
	 * Diamonds Ace Pile.
	 */
	private Component aceDiamonds;
	/**
	 * Hearts Ace Pile.
	 */
	private Component aceHearts;

	/**
	 * Discard Pile.
	 */
	private Component discardPile;
	/**
	 * Deck.
	 */
	private Component deck;

	/**
	 * Cell One.
	 */
	private Component cellOne;
	/**
	 * Cell Two.
	 */
	private Component cellTwo;
	/**
	 * Cell Three.
	 */
	private Component cellThree;
	/**
	 * Cell Four.
	 */
	private Component cellFour;

	/**
	 * Adds components (cards) to the board. The user can interact with the
	 * cards.
	 * 
	 * @param name
	 * 
	 * @param component
	 */
	public void addLayoutComponent(String name, Component component) {
		if (name.equals(COLUMN_ONE)) {
			colOne = component;
		} else if (name.equals(COLUMN_TWO)) {
			colTwo = component;
		} else if (name.equals(COLUMN_THREE)) {
			colThree = component;
		} else if (name.equals(COLUMN_FOUR)) {
			colFour = component;
		}

		else if (name.equals(SPADES_ACE_PILE)) {
			aceSpades = component;
		} else if (name.equals(CLUBS_ACE_PILE)) {
			aceClubs = component;
		} else if (name.equals(DIAMONDS_ACE_PILE)) {
			aceDiamonds = component;
		} else if (name.equals(HEARTS_ACE_PILE)) {
			aceHearts = component;
		}

		else if (name.equals(DISCARD_PILE)) {
			discardPile = component;
		} else if (name.equals(DECK)) {
			deck = component;
		}

		else if (name.equals(CELL_ONE)) {
			cellOne = component;
		} else if (name.equals(CELL_TWO)) {
			cellTwo = component;
		} else if (name.equals(CELL_THREE)) {
			cellThree = component;
		} else if (name.equals(CELL_FOUR)) {
			cellFour = component;
		}
	}

	/**
	 * Removes a component from the game board.
	 * 
	 * @param component
	 */
	public void removeLayoutComponent(Component component) {
		if (colOne == component) {
			colOne = null;
		} else if (colTwo == component) {
			colTwo = null;
		} else if (colThree == component) {
			colThree = null;
		} else if (colFour == component) {
			colFour = null;
		}

		else if (aceSpades == component) {
			aceSpades = null;
		} else if (aceClubs == component) {
			aceClubs = null;
		} else if (aceDiamonds == component) {
			aceDiamonds = null;
		} else if (aceHearts == component) {
			aceHearts = null;
		}

		else if (discardPile == component) {
			discardPile = null;
		} else if (deck == component) {
			deck = null;
		}

		else if (cellOne == component) {
			cellOne = null;
		} else if (cellTwo == component) {
			cellTwo = null;
		} else if (cellThree == component) {
			cellThree = null;
		} else if (cellFour == component) {
			cellFour = null;
		}
	}

	/**
	 * Implemented, unsupported function.
	 * 
	 * @param parent
	 */
	public Dimension preferredLayoutSize(Container parent) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Implemented, unsupported function.
	 * 
	 * @param parent
	 *            .
	 */
	public Dimension minimumLayoutSize(Container parent) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Sets the component's (card's) bounds by x and y and sets their width and
	 * height.
	 * 
	 * @param parent
	 */
	public void layoutContainer(Container parent) {
		Component component;

		if ((component = colOne) != null) {
			component.setBounds(95, 151, 72, 800);
		}
		if ((component = colTwo) != null) {
			component.setBounds(191, 151, 72, 800);
		}
		if ((component = colThree) != null) {
			component.setBounds(287, 151, 72, 800);
		}
		if ((component = colFour) != null) {
			component.setBounds(383, 151, 72, 800);
		}

		if ((component = aceSpades) != null) {
			component.setBounds(568, 31, 72, 96);
		}
		if ((component = aceClubs) != null) {
			component.setBounds(664, 31, 72, 96);
		}
		if ((component = aceDiamonds) != null) {
			component.setBounds(568, 149, 72, 96);
		}
		if ((component = aceHearts) != null) {
			component.setBounds(664, 149, 72, 96);
		}

		if ((component = discardPile) != null) {
			component.setBounds(664, 333, 102, 96);
		}
		if ((component = deck) != null) {
			component.setBounds(568, 333, 72, 96);
		}

		if ((component = cellOne) != null) {
			component.setBounds(95, 31, 72, 96);
		}
		if ((component = cellTwo) != null) {
			component.setBounds(191, 31, 72, 96);
		}
		if ((component = cellThree) != null) {
			component.setBounds(287, 31, 72, 96);
		}
		if ((component = cellFour) != null) {
			component.setBounds(383, 31, 72, 96);
		}
	}
}
