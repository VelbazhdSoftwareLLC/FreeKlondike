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
import java.awt.Image;

/**
 * Class: Column
 * 
 * Description: The Column class manages a single column on the board.
 * 
 * @author Matt Stephen
 */
class Column extends CardStack {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * If its possible to move a card to the top of the column - moves it
	 * otherwise returns null.
	 * 
	 * @param card
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public CardComponent push(CardComponent card) {
		if (isValidMove(card) == true) {
			super.push(card);
			return card;
		}

		return null;
	}

	/**
	 * Checks if its possible to move a card to the top of one of the four
	 * columns. The card must be lesser by one and to have different color than
	 * the current top card.
	 * 
	 * @param card
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(CardComponent card) {
		if (isEmpty() == true
				&& card.getCard().getNumber().equals(CardRank.KING)) {
			return true;
		}

		if (isEmpty() == false
				&& card.getCard().getColor() != peek().getCard().getColor()
				&& card.getCard().getNumber()
						.isGreaterByOneThan(peek().getCard().getNumber())) {
			return true;
		}

		return false;
	}

	/**
	 * Checks if its possible to move a stack of cards on top of one of the four
	 * columns. The bottom card from the stack must be lesser by one than the
	 * top card from the column.
	 * 
	 * @param stack
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isValidMove(CardStack stack) {
		return isValidMove(stack.peek());
	}

	/**
	 * Creates a stack and adds all of the available cards from a column into
	 * it. The available cards are: the first card and every other after the
	 * first one that is lesser and with different color than the previous one.
	 * Then returns the stack.
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public CardStack getAvailableCards() {
		if (isEmpty() == true) {
			return (null);
		}

		CardStack stack = new CardStack();
		stack.addCard(cards.get(length() - 1));

		for (int index = length() - 2; index >= 0; index--) {
			CardComponent card = cards.get(index);

			if (card.getCard().getColor() != stack.peek().getCard().getColor()
					&& card.getCard()
							.getNumber()
							.isLessByOneThan(stack.peek().getCard().getNumber())) {
				stack.addCard(card);
			} else {
				break;
			}
		}

		return stack;
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

		if (isEmpty()) {
			return;
		}

		for (int i = 0; i < cards.size(); i++) {
			Image image = cards.get(i).getImage();
			g.drawImage(image, 0, i * 25, null);
		}
	}

}
