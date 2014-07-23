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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * 
 * @author Todor Balabanov
 */
class CardComponent extends JComponent {

	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(Class.class
			.toString());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Card instances.
	 */
	private static CardComponent cards[] = { null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null };

	/**
	 * Buffer.
	 */
	private Card card = null;

	/**
	 * The back design.
	 */
	private String cardBack;

	/**
	 * The card front.
	 */
	private String cardImageString;

	/**
	 * The highlighted card front.
	 */
	private String cardHighlighted;

	/**
	 * Takes either card back or front.
	 */
	private BufferedImage image = null;

	/**
	 * It is used instead of constructor. Implement lazy initialization.
	 * 
	 * @param number
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public static CardComponent valueBy(int number) {
		int index = number - 1;

		if (cards[index] == null) {
			if (number >= 1 && number <= 13) {
				/*
				 * To make the cardNumber 1-13 you do not need to do anything.
				 */
				cards[index] = new CardComponent(CardSuit.SPADES,
						CardRank.getValue(number), number);
			} else if (number >= 14 && number <= 26) {
				/*
				 * To make the cardNumber 1-13 instead of 14-26.
				 */
				cards[index] = new CardComponent(CardSuit.CLUBS,
						CardRank.getValue(number - 13), number);
			} else if (number >= 27 && number <= 39) {
				/*
				 * To make the cardNumber 1-13 instead of 27-39.
				 */
				cards[index] = new CardComponent(CardSuit.DIAMONDS,
						CardRank.getValue(number - 26), number);
			} else if (number >= 40 && number <= 52) {
				/*
				 * To make the cardNumber 1-13 instead of 40-52.
				 */
				cards[index] = new CardComponent(CardSuit.HEARTS,
						CardRank.getValue(number - 39), number);
			} else {
				/*
				 * Let user know the card is invalid.
				 */
				LOGGER.info("Invalid card!");
			}
		}

		return (cards[index]);
	}

	/**
	 * Paint procedure.
	 * 
	 * @param g
	 *            Graphic context.
	 * @author Todor Balabanov
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	/**
	 * Constructor. Sets the card's parameters, sets it face up and gets its
	 * image.
	 * 
	 * @param suit
	 * 
	 * @param number
	 * 
	 * @param fullNumber
	 * 
	 * @author Todor Balabanov
	 */
	private CardComponent(CardSuit suit, CardRank number, int fullNumber) {
		if (SolitaireFrame.deckNumber >= 1
				&& SolitaireFrame.deckNumber <= ChangeAppearance.NUM_DECKS) {
			cardBack = "images/vanya/cardbacks/cardback"
					+ SolitaireFrame.deckNumber + ".png";
		} else {
			cardBack = "images/vanya/cardbacks/cardback3.png";
		}

		card = new Card(suit, number, fullNumber);

		initializeCardImageString();

		setFaceUp();
	}

	/**
	 * Returns the card's buffered image (either back or front).
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Sets the card's highlighted front image.
	 * 
	 * @author Todor Balabanov
	 */
	public void highlight() {
		card.highlight();

		try {
			URL imageURL = this.getClass().getResource(cardHighlighted);

			if (imageURL != null) {
				image = ImageIO.read(imageURL);
			}
		} catch (IOException ex) {
			System.err
					.println("Error in creating highlighted card face image.");
		}

		repaint();
	}

	/**
	 * Unhighlights a highlighted card. Sets back its unhighlighted face image.
	 * 
	 * @author Todor Balabanov
	 */
	public void unhighlight() {
		card.unhighlight();

		setFaceUp();
	}

	/**
	 * Sets the card face-up and sets its face image.
	 * 
	 * @author Todor Balabanov
	 */
	public void setFaceUp() {
		card.setFaceUp();

		try {
			URL imageURL = this.getClass().getResource(cardImageString);

			if (imageURL != null) {
				image = ImageIO.read(imageURL);
			}
		} catch (IOException ex) {
			System.err.println("Error in creating card face image.");
		}
	}

	/**
	 * Sets the card face-down and sets its back image.
	 * 
	 * @author Todor Balabanov
	 */
	public void setFaceDown() {
		card.setFaceDown();

		try {
			URL imageURL = this.getClass().getResource(cardBack);

			if (imageURL != null) {
				image = ImageIO.read(imageURL);
			}
		} catch (IOException ex) {
			System.err.println("Error in creating card back image.");
		}
	}

	/**
	 * Returns a card.
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public Card getCard() {
		return (card);
	}

	/**
	 * Sets the card's face image and highlighted face image based on its suit
	 * and rank.
	 * 
	 * @author Todor Balabanov
	 */
	private void initializeCardImageString() {
		cardImageString = "images/vanya/cardfaces/";
		cardHighlighted = "images/vanya/highlightedfaces/";

		if (card.getSuit().equals(CardSuit.SPADES)) {
			cardImageString += "s";
			cardHighlighted += "s";
			card.setColor(CardColor.BLACK);
		} else if (card.getSuit().equals(CardSuit.CLUBS)) {
			cardImageString += "c";
			cardHighlighted += "c";
			card.setColor(CardColor.BLACK);
		} else if (card.getSuit().equals(CardSuit.DIAMONDS)) {
			cardImageString += "d";
			cardHighlighted += "d";
			card.setColor(CardColor.RED);
		} else if (card.getSuit().equals(CardSuit.HEARTS)) {
			cardImageString += "h";
			cardHighlighted += "h";
			card.setColor(CardColor.RED);
		}

		if (card.getRank().equals(CardRank.ACE)) {
			cardImageString += "Ace";
			cardHighlighted += "Ace";
		} else if (card.getRank().equals(CardRank.TWO)) {
			cardImageString += "Two";
			cardHighlighted += "Two";
		} else if (card.getRank().equals(CardRank.THREE)) {
			cardImageString += "Three";
			cardHighlighted += "Three";
		} else if (card.getRank().equals(CardRank.FOUR)) {
			cardImageString += "Four";
			cardHighlighted += "Four";
		} else if (card.getRank().equals(CardRank.FIVE)) {
			cardImageString += "Five";
			cardHighlighted += "Five";
		} else if (card.getRank().equals(CardRank.SIX)) {
			cardImageString += "Six";
			cardHighlighted += "Six";
		} else if (card.getRank().equals(CardRank.SEVEN)) {
			cardImageString += "Seven";
			cardHighlighted += "Seven";
		} else if (card.getRank().equals(CardRank.EIGHT)) {
			cardImageString += "Eight";
			cardHighlighted += "Eight";
		} else if (card.getRank().equals(CardRank.NINE)) {
			cardImageString += "Nine";
			cardHighlighted += "Nine";
		} else if (card.getRank().equals(CardRank.TEN)) {
			cardImageString += "Ten";
			cardHighlighted += "Ten";
		} else if (card.getRank().equals(CardRank.JACK)) {
			cardImageString += "Jack";
			cardHighlighted += "Jack";
		} else if (card.getRank().equals(CardRank.QUEEN)) {
			cardImageString += "Queen";
			cardHighlighted += "Queen";
		} else if (card.getRank().equals(CardRank.KING)) {
			cardImageString += "King";
			cardHighlighted += "King";
		}

		cardImageString += ".png";
		cardHighlighted += "H.png";
	}

	/**
	 * Clone a card, that includes the card's suit, number and full number.
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public CardComponent clone() {
		return this;
	}
}