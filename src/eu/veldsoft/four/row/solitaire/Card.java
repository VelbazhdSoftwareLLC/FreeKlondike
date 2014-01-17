/*
 This file is a part of Four Row Solitaire

 Copyright (C) 2010 by Matt Stephen

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

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Class: Card
 * 
 * Description: The Card class holds information pertaining to 1 out of the 52
 * cards per deck.
 * 
 * @author Matt Stephen
 */
public class Card extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private CardSuit cardSuit;

	/**
	 * 
	 */
	private CardRank cardNumber;

	/**
	 * 
	 */
	private CardColor cardColor;

	/**
	 * 1 - 52
	 */
	private int fullCardNumber;

	/**
	 * 
	 */
	private int deckNumber;

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
	 * 
	 */
	private boolean faceUp = false;

	/**
	 * 
	 */
	private boolean highlighted = false;

	/**
	 * To notify the discard pile of moves from the deck.
	 */
	private String location = "";

	/**
	 * 
	 * @param suit
	 * @param number
	 * @param deckNumber
	 * @param fullNumber
	 */
	public Card(CardSuit suit, CardRank number, int deckNumber, int fullNumber) {
		this.cardSuit = suit;
		this.cardNumber = number;
		this.fullCardNumber = fullNumber;
		this.deckNumber = deckNumber;

		if (deckNumber >= 1 && deckNumber <= ChangeAppearance.NUM_DECKS) {
			cardBack = "images/cardbacks/cardback" + deckNumber + ".png";
		} else {
			cardBack = "images/cardbacks/cardback3.png";
		}

		initializeCardImageString();

		setFaceUp();
	}

	/**
	 * 
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
	 * 
	 */
	public void unhighlight() {
		highlighted = false;

		setFaceUp();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isHighlighted() {
		return highlighted;
	}

	/**
	 * 
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
	 * 
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
	 * 
	 * @return
	 */
	public boolean isFaceUp() {
		return faceUp;
	}

	/**
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

	public BufferedImage getImage() {
		return image;
	}

	public CardRank getNumber() {
		return cardNumber;
	}

	public CardSuit getSuit() {
		return cardSuit;
	}

	public CardColor getColor() {
		return cardColor;
	}

	public int getFullNumber() {
		return fullCardNumber;
	}

	public String getSource() {
		return location;
	}

	public void setSource(String source) {
		location = source;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	public Card clone() {
		Card card = new Card(cardSuit, cardNumber, deckNumber, fullCardNumber);
		return card;
	}
}