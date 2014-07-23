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

import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JLayeredPane;

/**
 * 
 * @author Todor Balabanov
 */
class CardLayeredPane extends JLayeredPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// TODO Split GUI and business logic.
}

/**
 * Class: CardStack
 * 
 * Description: The Cardstack class manages a location for cards to be placed.
 * 
 * @author Matt Stephen
 */
class CardStack extends JLayeredPane {
	// TODO Parent class methods should not have source code!

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Stack of cards.
	 */
	protected Vector<CardComponent> cards = new Vector<CardComponent>();

	/**
	 * For starting the game.
	 * 
	 * Used to add a card to a stack.
	 * 
	 * @param card
	 * 
	 * @author Todor Balabanov
	 */
	public void addCard(CardComponent card) {
		cards.add(card);
		card.setBounds(0, 0, 72, 96);
		add(card, 0);
	}

	/**
	 * Used to add a bunch of cards to a stack.
	 * 
	 * @param stack
	 * 
	 * @author Todor Balabanov
	 */
	public void addStack(CardStack stack) {
		while (stack.isEmpty() == false) {
			addCard(stack.pop());
		}
	}

	/**
	 * Used to add a card to a stack and then to return the moved card.
	 * 
	 * @param card
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public CardComponent push(CardComponent card) {
		addCard(card);

		return card;
	}

	/**
	 * Used to add a bunch of cards to a card stack and then to return empty
	 * stack.
	 * 
	 * @param stack
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack push(CardStack stack) {
		addStack(stack);

		/*
		 * Returns empty stack.
		 */
		return stack;
	}

	/**
	 * Pops the top card out of a stack.
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized CardComponent pop() {
		CardComponent card = peek();

		this.remove(card);
		cards.remove(cards.size() - 1);

		return card;
	}

	/**
	 * Temporary reverses the cards in a stack.
	 * 
	 * @param stack
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack pop(CardStack stack) {
		/*
		 * Temporary reverse pop of entire stack transfer.
		 */
		CardStack temp = new CardStack();

		while (!stack.isEmpty()) {
			CardComponent card = stack.pop();
			temp.push(card);
			this.remove(card);
		}

		return temp;
	}

	/**
	 * Pops the top card out of a stack if possible. If not - returns null.
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized CardComponent peek() {
		if (cards.isEmpty()) {
			return null;
		}

		return cards.lastElement();
	}

	/**
	 * Checks if a stack is empty (has no cards inside).
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return cards.size() == 0;
	}

	/**
	 * Returns the stack's length.
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public int length() {
		return cards.size();
	}

	/**
	 * Searches the stack for a specific card and returns its location in the
	 * stack.
	 * 
	 * @param card
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized int search(CardComponent card) {
		int i = cards.lastIndexOf(card);

		if (i >= 0) {
			return cards.size() - i;
		}

		return -1;
	}

	/**
	 * Returns the card located at a specified location within the stack.
	 * 
	 * @param index
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public CardComponent getCardAtLocation(int index) {
		if (index < cards.size()) {
			return cards.get(index);
		}

		return null;
	}

	/**
	 * Returns the card located at the coordinates of a mouse click.
	 * 
	 * @param p
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public CardComponent getCardAtLocation(Point p) {
		if (cards.isEmpty()) {
			return null;
		}

		if (isValidClick(p)) {
			int y = (int) p.getY();

			int index;

			if (y > 25 * (cards.size() - 1)) {
				index = cards.size() - 1;
			} else {
				index = y / 25;
			}

			if (isValidCard(index)) {
				return cards.get(index);
			}
		}

		return null;
	}

	/**
	 * Verifies that the card is a part of a valid stack.
	 * 
	 * @param index
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	private boolean isValidCard(int index) {
		if (index >= cards.size()) {
			return false;
		}

		for (int i = index; i < cards.size() - 1; i++) {
			/*
			 * Cards are not opposite colors or decreasing in value correctly.
			 */
			if (cards.get(i).getCard().getColor() == cards.get(i + 1).getCard()
					.getColor()
					|| cards.get(i)
							.getCard()
							.getNumber()
							.isLessByOneThan(
									cards.get(i + 1).getCard().getNumber()) == false) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if clicked area is defined on a card in the stack.
	 * 
	 * @param p
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	private boolean isValidClick(Point p) {
		int y = (int) p.getY();

		if (!isEmpty()) {
			if (y > 25 * (cards.size() - 1)
					+ cards.lastElement().getBounds().getHeight()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Searches the stack for a specific card. Creates a new temporary stack.
	 * Clones the cards from the end towards the beginning of the stack into the
	 * temp stack. Stops after it reaches the specific card.
	 * 
	 * @param card
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack getStack(CardComponent card) {
		CardStack temp = new CardStack();
		int index = search(card);

		for (int i = 0; i < index; i++) {
			temp.push(getCardAtLocation(cards.size() - i - 1).clone());
			getCardAtLocation(cards.size() - i - 1).highlight();
		}

		return temp;
	}

	/**
	 * Searches the stack for a specified location, creates a temporary stack,
	 * Clones the cards from the end towards the begining of the stack, stops
	 * when it reaches the specified location.
	 * 
	 * @param numCards
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack getStack(int numCards) {
		CardStack temp = new CardStack();
		int index = length() - numCards;

		for (int i = length(); i > index; i--) {
			temp.push(getCardAtLocation(cards.size() - i - 1).clone());
			getCardAtLocation(cards.size() - i - 1).highlight();
		}

		return temp;
	}

	/**
	 * Used to undo the last stack move.
	 * 
	 * @param numCards
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack undoStack(int numCards) {
		CardStack temp = new CardStack();

		for (int i = 0; i < numCards; i++) {
			temp.push(pop());
		}

		return temp;
	}

	/**
	 * Checks if the move is valid. Always returns false.
	 * 
	 * @param card
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(CardComponent card) {
		return false;
	}

	/**
	 * Checks if the move is valid. Always returns false.
	 * 
	 * @param stack
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(CardStack stack) {
		return false;
	}

	/**
	 * Returns the first card from a stack.
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public CardComponent getBottom() {
		return cards.firstElement();
	}

	/**
	 * Returns the available cards from a deck.
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack getAvailableCards() {
		return null;
	}

	/**
	 * Paint procedure.
	 * 
	 * @param g
	 *            Graphic context.
	 * 
	 * @author Todor Balabanov
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
}
