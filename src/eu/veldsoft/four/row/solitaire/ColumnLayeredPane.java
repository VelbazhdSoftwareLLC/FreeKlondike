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
class ColumnLayeredPane extends JLayeredPane implements CardStackLayeredPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	Column column = null;

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public Column getColumn() {
		return column;
	}

	/**
	 * Returns the available cards from a deck. This method is overriden by the
	 * child classes.
	 * 
	 * @return Null.
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack getAvailableCards() {
		return (column.getAvailableCards());
	}

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
	public Card getCardAtLocation(Point p) {
		if (column.getCards().isEmpty()) {
			return null;
		}

		if (isValidClick(p) == false) {
			return (null);
		}

		int index;
		int y = (int) p.getY();
		if (y > 25 * (column.getCards().size() - 1)) {
			index = column.getCards().size() - 1;
		} else {
			index = y / 25;
		}

		if (column.isValidCard(index) == true) {
			return column.getCards().get(index);
		}

		return (null);
	}

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
	public Card getCardAtLocation(int index) {
		Card result = column.getCardAtLocation(index);

		if (result != null) {
			return (result);
		}

		return null;
	}

	/**
	 * Checks if clicked area is defined on a card in the stack.
	 * 
	 * @param p
	 *            Location of the click.
	 * @return True or false.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidClick(Point p) {
		int y = (int) p.getY();
		if (isEmpty() == true) {
			return (true);
		}

		if (y > 25
				* (column.getCards().size() - 1)
				+ CardComponent.cardsMapping
						.get(column.getCards().lastElement()).getBounds()
						.getHeight()) {
			return (false);
		}

		return (true);
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isEmpty() {
		return (column.isEmpty());
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public int length() {
		return (column.length());
	}

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
	public void addCard(Card card) {
		column.addCard(card);
		CardComponent.cardsMapping.get(card).setBounds(0, 0, 72, 96);
		add(CardComponent.cardsMapping.get(card), 0);
	}

	/**
	 * Used to add a bunch of cards to a stack.
	 * 
	 * @param stack
	 *            Stack to be added.
	 * 
	 * @author Todor Balabanov
	 */
	public void addStack(CardStack stack) {
		while (stack.isEmpty() == false) {
			addCard(stack.pop());
		}
	}

	/**
	 * Used to add a bunch of cards to a stack.
	 * 
	 * @param stack
	 *            Stack to be added.
	 * 
	 * @author Todor Balabanov
	 */
	public void addStack(Vector<Card> stack) {
		for (int i = stack.size() - 1; i >= 0; i--) {
			addCard(stack.elementAt(i));
		}
	}

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
	public Vector<Card> getStack(Card card) {
		Vector<Card> temp = new Vector<Card>();
		int index = column.search(card);

		for (int i = 0; i < index; i++) {
			temp.add(getCardAtLocation(column.getCards().size() - i - 1));
			getCardAtLocation(column.getCards().size() - i - 1).highlight();
		}

		return temp;
	}

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
	public CardStack getStack(int numCards) {
		Column temp = new Column();
		int index = length() - numCards;

		for (int i = length(); i > index; i--) {
			temp.push(getCardAtLocation(column.getCards().size() - i - 1)
					.clone());
			getCardAtLocation(column.getCards().size() - i - 1).highlight();
		}

		return temp;
	}

	/**
	 * Pops the top card out of a stack if possible. If not - returns null.
	 * 
	 * @return Card or null.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized Card peek() {
		return column.peek();
	}

	/**
	 * Pops the top card out of a stack.
	 * 
	 * @return card The popped card.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized Card pop() {
		Card card = column.pop();

		if (card != null) {
			remove(CardComponent.cardsMapping.get(card));
		}

		return card;
	}

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
	public CardStack pop(CardStack stack) {
		/*
		 * Temporary reverse pop of entire stack transfer.
		 */
		Column temp = new Column();

		while (!stack.isEmpty()) {
			Card card = stack.pop();
			temp.push(card);
			remove(CardComponent.cardsMapping.get(card));
		}

		return temp;
	}

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
	public Card push(Card card) {
		addCard(card);

		return card;
	}

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
	public CardStack push(CardStack stack) {
		addStack(stack);

		/*
		 * Returns empty stack.
		 */
		return stack;
	}

	/**
	 * Used to add a bunch of cards to a card stack and then to return empty
	 * stack.
	 * 
	 * @param stack
	 *            Stack to be added.
	 * 
	 * @author Todor Balabanov
	 */
	public void push(Vector<Card> stack) {
		addStack(stack);
	}

	/**
	 * Returns the first card from a stack.
	 * 
	 * @return card The first card from the stack of cards.
	 * 
	 * @author Todor Balabanov
	 */
	public Card getBottom() {
		return column.getBottom();
	}

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
	public CardStack undoStack(int numCards) {
		Column temp = new Column();

		for (int i = 0; i < numCards; i++) {
			temp.push(pop());
		}

		column.undoStack(numCards);

		return temp;
	}

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
	public boolean isValidMove(Card card) {
		return (column.isValidMove(card));
	}

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
	public boolean isValidMove(CardStack stack) {
		if (stack instanceof AcePile) {
			return (column.isValidMove(((AcePile) stack)));
		} else if (stack instanceof DealDeck) {
			return (column.isValidMove(((DealDeck) stack)));
		} else if (stack instanceof DiscardPile) {
			return (column.isValidMove(((DiscardPile) stack)));
		} else if (stack instanceof Column) {
			return (column.isValidMove(((Column) stack)));
		} else if (stack instanceof SingleCell) {
			return (column.isValidMove(((SingleCell) stack)));
		}

		return (false);
	}

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
	public boolean isValidMove(Vector<Card> stack) {
		return (column.isValidMove(stack));
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

		if (CardComponent.cardsMapping.isEmpty() == true) {
			return;
		}

		if (column.isEmpty()) {
			return;
		}

		for (int i = 0; i < column.getCards().size(); i++) {
			CardComponent.cardsMapping.get(column.getCards().get(i))
					.updateImage();
			g.drawImage(CardComponent.cardsMapping
					.get(column.getCards().get(i)).getImage(), 0, i * 25, null);
		}
	}
}