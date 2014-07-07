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

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Class: Card
 * 
 * Description: The Card class holds information pertaining to 1 out of the 52
 * cards per deck.
 * 
 * @author Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov
 */
public class Card extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(Class.class
			.toString());

	/**
	 * Card instances.
	 */
	private static Card cards[] = { null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null };

	/**
	 * Card suit.
	 */
	private CardSuit cardSuit;

	/**
	 * Card number.
	 */
	private CardRank cardNumber;

	/**
	 * Card color.
	 */
	private CardColor cardColor;

	/**
	 * 1 - 52
	 */
	private int fullCardNumber;

	/**
	 * Takes either card back or front.
	 */
	private BufferedImage image;

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
	 * Is the card currently set face-up.
	 */
	private boolean faceUp = false;

	/**
	 * Is the card currently highlighted.
	 */
	private boolean highlighted = false;

	/**
	 * To notify the discard pile of moves from the deck.
	 */
	private String location = "";

	/**
	 * It is used instead of constructor. Implement lazy initialization.
	 * 
	 * @param number
	 * 
	 * @return
	 */
	public static Card valueBy(int number) {
		int index = number - 1;

		if (cards[index] == null) {
			if (number >= 1 && number <= 13) {
				/*
				 * To make the cardNumber 1-13 you do not need to do anything.
				 */
				cards[index] = new Card(CardSuit.SPADES,
						CardRank.getValue(number), number);
			} else if (number >= 14 && number <= 26) {
				/*
				 * To make the cardNumber 1-13 instead of 14-26.
				 */
				cards[index] = new Card(CardSuit.CLUBS,
						CardRank.getValue(number - 13), number);
			} else if (number >= 27 && number <= 39) {
				/*
				 * To make the cardNumber 1-13 instead of 27-39.
				 */
				cards[index] = new Card(CardSuit.DIAMONDS,
						CardRank.getValue(number - 26), number);
			} else if (number >= 40 && number <= 52) {
				/*
				 * To make the cardNumber 1-13 instead of 40-52.
				 */
				cards[index] = new Card(CardSuit.HEARTS,
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
	 * Private card constructor Sets the card's suit, number, full number and
	 * back image. Also sets it face-up.
	 * 
	 * @param suit
	 * 
	 * @param number
	 * 
	 * @param deckNumber
	 * 
	 * @param fullNumber
	 */
	private Card(CardSuit suit, CardRank number, int fullNumber) {
		this.cardSuit = suit;
		this.cardNumber = number;
		this.fullCardNumber = fullNumber;

		if (SolitaireBoard.deckNumber >= 1
				&& SolitaireBoard.deckNumber <= ChangeAppearance.NUM_DECKS) {
			cardBack = "images/cardbacks/cardback" + SolitaireBoard.deckNumber
					+ ".png";
		} else {
			cardBack = "images/cardbacks/cardback3.png";
		}

		initializeCardImageString();

		setFaceUp();
	}

	/**
	 * Sets the card's highlighted front image.
	 */
	public void highlight() {
		highlighted = true;

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
	 */
	public void unhighlight() {
		highlighted = false;

		setFaceUp();
	}

	/**
	 * Checks if the card is highlighted and returns the result(true/false).
	 * 
	 * @return
	 */
	public boolean isHighlighted() {
		return highlighted;
	}

	/**
	 * Sets the card face-up and sets its face image.
	 */
	public void setFaceUp() {
		faceUp = true;

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
	 */
	public void setFaceDown() {
		faceUp = false;

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
	 * Checks if the card is facing up and returns the result(true/false).
	 * 
	 * @return
	 */
	public boolean isFaceUp() {
		return faceUp;
	}

	/**
	 * Sets the card's face image and highlighted face image based on its suit
	 * and rank.
	 * 
	 */
	private void initializeCardImageString() {
		cardImageString = "images/cardfaces/";
		cardHighlighted = "images/highlightedfaces/";

		if (cardSuit.equals(CardSuit.SPADES)) {
			cardImageString += "s";
			cardHighlighted += "s";
			cardColor = CardColor.BLACK;
		} else if (cardSuit.equals(CardSuit.CLUBS)) {
			cardImageString += "c";
			cardHighlighted += "c";
			cardColor = CardColor.BLACK;
		} else if (cardSuit.equals(CardSuit.DIAMONDS)) {
			cardImageString += "d";
			cardHighlighted += "d";
			cardColor = CardColor.RED;
		} else if (cardSuit.equals(CardSuit.HEARTS)) {
			cardImageString += "h";
			cardHighlighted += "h";
			cardColor = CardColor.RED;
		}

		if (cardNumber.equals(CardRank.ACE)) {
			cardImageString += "Ace";
			cardHighlighted += "Ace";
		} else if (cardNumber.equals(CardRank.TWO)) {
			cardImageString += "Two";
			cardHighlighted += "Two";
		} else if (cardNumber.equals(CardRank.THREE)) {
			cardImageString += "Three";
			cardHighlighted += "Three";
		} else if (cardNumber.equals(CardRank.FOUR)) {
			cardImageString += "Four";
			cardHighlighted += "Four";
		} else if (cardNumber.equals(CardRank.FIVE)) {
			cardImageString += "Five";
			cardHighlighted += "Five";
		} else if (cardNumber.equals(CardRank.SIX)) {
			cardImageString += "Six";
			cardHighlighted += "Six";
		} else if (cardNumber.equals(CardRank.SEVEN)) {
			cardImageString += "Seven";
			cardHighlighted += "Seven";
		} else if (cardNumber.equals(CardRank.EIGHT)) {
			cardImageString += "Eight";
			cardHighlighted += "Eight";
		} else if (cardNumber.equals(CardRank.NINE)) {
			cardImageString += "Nine";
			cardHighlighted += "Nine";
		} else if (cardNumber.equals(CardRank.TEN)) {
			cardImageString += "Ten";
			cardHighlighted += "Ten";
		} else if (cardNumber.equals(CardRank.JACK)) {
			cardImageString += "Jack";
			cardHighlighted += "Jack";
		} else if (cardNumber.equals(CardRank.QUEEN)) {
			cardImageString += "Queen";
			cardHighlighted += "Queen";
		} else if (cardNumber.equals(CardRank.KING)) {
			cardImageString += "King";
			cardHighlighted += "King";
		}

		cardImageString += ".png";
		cardHighlighted += "H.png";
	}

	/**
	 * Returns the card's buffered image (either back or front).
	 * 
	 * @return
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Returns the card's number.
	 * 
	 * @return
	 */
	public CardRank getNumber() {
		return cardNumber;
	}

	/**
	 * Returns the card's suit.
	 * 
	 * @return
	 */
	public CardSuit getSuit() {
		return cardSuit;
	}

	/**
	 * Returns the card's color.
	 * 
	 * @return
	 */
	public CardColor getColor() {
		return cardColor;
	}

	/**
	 * Returns the card's full number.
	 * 
	 * @return
	 */
	public int getFullNumber() {
		return fullCardNumber;
	}

	/**
	 * Notifies the discard pile of moves from the deck.
	 * 
	 * @return
	 */
	public String getSource() {
		return location;
	}

	/**
	 * Used to set the location of a moved card.
	 * 
	 * @param source
	 */
	public void setSource(String source) {
		location = source;
	}

	/**
	 * Paint procedure.
	 * 
	 * @param g
	 *            Graphic context.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	/**
	 * Clone a card, that includes the card's suit, number and full number.
	 * 
	 * @return
	 */
	public Card clone() {
		return new Card(cardSuit, cardNumber, fullCardNumber);
	}
}
