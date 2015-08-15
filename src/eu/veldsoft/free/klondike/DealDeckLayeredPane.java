/*
 This file is a part of Free Klondike

 Copyright (C) 2010-2014 by Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov, Vanya Gyaurova, Plamena Popova, Hristiana Kalcheva, Yana Genova

 Free Klondike is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Free Klondike is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with FreeKlondike.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.veldsoft.free.klondike;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

/**
 * 
 * @author Todor Balabanov
 */
class DealDeckLayeredPane extends JLayeredPane implements CardStackLayeredPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	DealDeck dealDeck = null;

	/**
	 * 
	 */
	DiscardPileLayeredPane discard = null;

	/**
	 * Sets the discard pile and the deck through limit, based on the draw
	 * count.
	 * 
	 * @param discard
	 *            Sets the discard pile.
	 * 
	 * @author Todor Balabanov
	 */
	public DealDeckLayeredPane(DiscardPileLayeredPane discard) {
		this.discard = discard;
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public DealDeck getDealDeck() {
		return dealDeck;
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
		return (dealDeck.getAvailableCards());
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
		if (dealDeck.getCards().isEmpty()) {
			return null;
		}

		if (isValidClick(p)) {
			int y = (int) p.getY();

			int index;

			if (y > 25 * (dealDeck.getCards().size() - 1)) {
				index = dealDeck.getCards().size() - 1;
			} else {
				index = y / 25;
			}

			if (dealDeck.isValidCard(index)) {
				return dealDeck.getCards().get(index);
			}
		}

		return null;
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
		Card result = dealDeck.getCardAtLocation(index);

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

		if (!isEmpty()) {
			if (y > 25
					* (dealDeck.getCards().size() - 1)
					+ CardComponent.cardsMapping
							.get(dealDeck.getCards().lastElement()).getBounds()
							.getHeight()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isEmpty() {
		return (dealDeck.isEmpty());
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public int length() {
		return (dealDeck.length());
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
		dealDeck.addCard(card);
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
		int index = dealDeck.search(card);

		for (int i = 0; i < index; i++) {
			temp.add(getCardAtLocation(dealDeck.getCards().size() - i - 1));
			getCardAtLocation(dealDeck.getCards().size() - i - 1).highlight();
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
		DealDeck temp = new DealDeck(discard.getDiscardPile());
		int index = length() - numCards;

		for (int i = length(); i > index; i--) {
			temp.push(getCardAtLocation(dealDeck.getCards().size() - i - 1)
					.clone());
			getCardAtLocation(dealDeck.getCards().size() - i - 1).highlight();
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
		return dealDeck.peek();
	}

	/**
	 * Pops card(s) out of the deal deck based on the draw count. Pushes the
	 * card(s) into the discard pile. When the deck through limit has been
	 * reached, displays an error dialog, that notifies the user. Then forbids
	 * the pops from the deal deck.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized Card pop() {
		Card result = dealDeck.pop();

		if (isEmpty() == true) {
			return (null);
		}

		repaint();
		revalidate();
		discard.repaint();
		discard.revalidate();

		if (dealDeck.numTimesThroughDeck >= dealDeck.deckThroughLimit) {
			JOptionPane.showMessageDialog(null,
					"You have reached your deck through limit.");
		}

		return (result);
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
		DealDeck temp = new DealDeck(discard.getDiscardPile());

		while (!stack.isEmpty()) {
			Card card = stack.pop();
			temp.push(card);
			remove(CardComponent.cardsMapping.get(card));
		}

		return temp;
	}

	/**
	 * Used to undo the last move if it was a reset on the discard pile.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized void undoPop() {
		dealDeck.undoPop();

		discard.repaint();
		discard.revalidate();
		repaint();
		revalidate();
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
		return dealDeck.getBottom();
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
		DealDeck temp = new DealDeck(discard.getDiscardPile());

		for (int i = 0; i < numCards; i++) {
			temp.push(pop());
		}

		dealDeck.undoStack(numCards);

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
		return (dealDeck.isValidMove(card));
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
		return (dealDeck.isValidMove((CardStack) null));
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
		return (dealDeck.isValidMove((CardStack) null));
	}

	/**
	 * @author Todor Balabanov
	 */
	public void allFaceDown() {
		dealDeck.allFaceDown();
	}

	/**
	 * Paint procedure.
	 * 
	 * @param g
	 *            Paint.
	 * 
	 * @author Todor Balabanov
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (CardComponent.cardsMapping.isEmpty() == true) {
			return;
		}

		if (dealDeck.isEmpty() == true) {
			g.drawImage(null, 0, 0, null);
			return;
		}

		dealDeck.getCardAtLocation(dealDeck.length() - 1).setFaceDown();
		CardComponent.cardsMapping.get(
				dealDeck.getCardAtLocation(dealDeck.length() - 1))
				.updateImage();

		g.drawImage(
				CardComponent.cardsMapping.get(
						dealDeck.getCardAtLocation(dealDeck.length() - 1))
						.getImage(), 0, 0, null);
	}
}