/*
 This file is a part of Four Row Solitaire

 Copyright (C) 2010-2014 by Matt Stephen, Todor Balabanov

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
import java.awt.Image;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JLayeredPane;

/**
 * Class: CardStack
 * 
 * Description: The Cardstack class manages a location for cards to be placed.
 * 
 * @author Matt Stephen
 */
public class CardStack extends JLayeredPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected Vector<Card> cards = new Vector<Card>();

	/**
	 * For starting the game.
	 * 
	 * @param card
	 */
	public void addCard(Card card) {
		cards.add(card);
		card.setBounds(0, 0, 72, 96);
		add(card, 0);
	}

	public void addStack(CardStack stack) {
		while (stack.isEmpty() == false) {
			Card card = stack.pop();
			addCard(card);
		}
	}

	public Card push(Card card) {
		addCard(card);
		
		return card;
	}

	public CardStack push(CardStack stack) {
		addStack(stack);
		
		/*
		 * Returns empty stack.
		 */
		return stack;
	}

	public synchronized Card pop() {
		Card card = peek();

		this.remove(card);
		cards.remove(cards.size() - 1);

		return card;
	}

	public CardStack pop(CardStack stack) {
		/*
		 * Temporary reverse pop of entire stack transfer.
		 */
		CardStack temp = new CardStack();

		while (!stack.isEmpty()) {
			Card card = stack.pop();
			temp.push(card);
			this.remove(card);
		}

		return temp;
	}

	public synchronized Card peek() {
		if (cards.isEmpty()) {
			return null;
		}

		return cards.lastElement();
	}

	public boolean isEmpty() {
		return cards.size() == 0;
	}

	public int length() {
		return cards.size();
	}

	public synchronized int search(Card card) {
		int i = cards.lastIndexOf(card);

		if (i >= 0) {
			return cards.size() - i;
		}

		return -1;
	}

	public Card getCardAtLocation(int index) {
		if (index < cards.size()) {
			return cards.get(index);
		}

		return null;
	}

	public Card getCardAtLocation(Point p) {
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
	 * @return
	 */
	private boolean isValidCard(int index) {
		if (index >= cards.size()) {
			return false;
		}

		for (int i = index; i < cards.size() - 1; i++) {
			/*
			 * Cards are not opposite colors or decreasing in value correctly.
			 */
			if (cards.get(i).getColor() == cards.get(i + 1).getColor()
					|| cards.get(i).getNumber()
							.isLessByOneThan(cards.get(i + 1).getNumber()) == false) {
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

	public CardStack getStack(Card card) {
		CardStack temp = new CardStack();
		int index = search(card);

		for (int i = 0; i < index; i++) {
			temp.push(getCardAtLocation(cards.size() - i - 1).clone());
			getCardAtLocation(cards.size() - i - 1).highlight();
		}

		return temp;
	}

	public CardStack getStack(int numCards) {
		CardStack temp = new CardStack();
		int index = length() - numCards;

		for (int i = length(); i > index; i--) {
			temp.push(getCardAtLocation(cards.size() - i - 1).clone());
			getCardAtLocation(cards.size() - i - 1).highlight();
		}

		return temp;
	}

	public CardStack undoStack(int numCards) {
		CardStack temp = new CardStack();

		for (int i = 0; i < numCards; i++) {
			temp.push(pop());
		}

		return temp;
	}

	public boolean isValidMove(Card card) {
		return false;
	}

	public boolean isValidMove(CardStack stack) {
		return false;
	}

	public Card getBottom() {
		return cards.firstElement();
	}

	public CardStack getAvailableCards() {
		return null;
	}

	public void paint(Graphics g) {
		super.paint(g);

		if (isEmpty()) {
			return;
		}

		for (int i = 0; i < cards.size(); i++) {
			Image image = cards.get(i).getImage();
			g.drawImage(image, 0, i * 25, null);
		}
	}
}