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

import java.awt.Point;
import java.util.Vector;

/**
 * 
 * @author Todor Balabanov
 */
interface CardStackLayeredPane {

	/**
	 * Searches the stack for a specific card. Creates a new temporary stack.
	 * Clones the cards from the end towards the beginning of the stack into the
	 * temp stack. Stops after it reaches the specific card.
	 * 
	 * @param card
	 *            Card to look for.
	 * 
	 * @return Stack of cards.
	 * 
	 * @author Todor Balabanov
	 */
	public Vector<Card> getStack(Card card);

	/**
	 * Searches the stack for a specified location, creates a temporary stack,
	 * Clones the cards from the end towards the begining of the stack, stops
	 * when it reaches the specified location.
	 * 
	 * @param numCards
	 *            Index.
	 * 
	 * @return Stack of cards.
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack getStack(int numCards);

	/**
	 * Returns the card located at the coordinates of a mouse click.
	 * 
	 * @param p
	 *            Location of a mouse click.
	 * 
	 * @return The card at this location.
	 * 
	 * @author Todor Balabanov
	 */
	public Card getCardAtLocation(Point p);

	/**
	 * Returns the card located at a specified location within the stack.
	 * 
	 * @param index
	 *            Location within the stack.
	 * 
	 * @return The card at this location. Or null if the index is greater than
	 *         the stack's size.
	 * 
	 * @author Todor Balabanov
	 */
	public Card getCardAtLocation(int index);

	/**
	 * Checks if clicked area is defined on a card in the stack.
	 * 
	 * @param p
	 *            Location of the click.
	 * @return True or false.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidClick(Point p);

	/**
	 * Pops the top card out of a stack if possible. If not - returns null.
	 * 
	 * @return Card or null.
	 * 
	 * @author Todor Balabanov
	 */
	public Card peek();

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isEmpty();

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public int length();

	/**
	 * Returns the available cards from a deck. This method is overriden by the
	 * child classes.
	 * 
	 * @return Null.
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack getAvailableCards();

	/**
	 * For starting the game.
	 * 
	 * Used to add a card to a stack.
	 * 
	 * @param card
	 *            Card to be added.
	 * 
	 * @author Todor Balabanov
	 */
	public void addCard(Card card);

	/**
	 * Pops the top card out of a stack.
	 * 
	 * @return card The popped card.
	 * 
	 * @author Todor Balabanov
	 */
	public Card pop();

	/**
	 * Temporary reverses the cards in a stack.
	 * 
	 * @param stack
	 *            Stack to be reversed.
	 * 
	 * @return The reversed stack.
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack pop(CardStack stack);

	/**
	 * Used to add a bunch of cards to a stack.
	 * 
	 * @param stack
	 *            Stack to be added.
	 * 
	 * @author Todor Balabanov
	 */
	public void addStack(CardStack stack);

	/**
	 * Used to add a card to a stack and then to return the moved card.
	 * 
	 * @param card
	 *            Card to be added.
	 * 
	 * @return Added card.
	 * 
	 * @author Todor Balabanov
	 */
	public Card push(Card card);

	/**
	 * Used to add a bunch of cards to a card stack and then to return empty
	 * stack.
	 * 
	 * @param stack
	 *            Stack to be added.
	 * 
	 * @return Empty stack.
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack push(CardStack stack);

	/**
	 * Used to add a bunch of cards to a card stack and then to return empty
	 * stack.
	 * 
	 * @param stack
	 *            Stack to be added.
	 * 
	 * @author Todor Balabanov
	 */
	public void push(Vector<Card> stack);

	/**
	 * Checks if the move is valid. Always returns false. The method is
	 * overridden by the child classes.
	 * 
	 * @param card
	 *            Card to be checked.
	 * 
	 * @return False
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(Card card);

	/**
	 * Checks if the move is valid. Always returns false. This method is
	 * overridden by the child classes.
	 * 
	 * @param stack
	 *            Stack of cards to be ckecked.
	 * 
	 * @return False.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(CardStack stack);

	/**
	 * Checks if the move is valid. Always returns false. This method is
	 * overridden by the child classes.
	 * 
	 * @param stack
	 *            Stack of cards to be ckecked.
	 * 
	 * @return False.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(Vector<Card> stack);

	/**
	 * Returns the first card from a stack.
	 * 
	 * @return card The first card from the stack of cards.
	 * 
	 * @author Todor Balabanov
	 */
	public Card getBottom();

	/**
	 * Used to undo the last stack move. Reverses the cards.
	 * 
	 * @param numCards
	 *            Number of cards in the stack.
	 * 
	 * @return Reversed stack.
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack undoStack(int numCards);
}
