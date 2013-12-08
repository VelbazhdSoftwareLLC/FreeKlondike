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

package FourRowSolitaire;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Class: Card
 *
 * Description: The Card class holds information pertaining to 1 out of the 52 cards per deck.
 *
 * @author Matt Stephen
 */
public class Card extends JComponent
{
    public static final String SPADES_SUIT = "Spades";
    public static final String CLUBS_SUIT = "Clubs";
    public static final String HEARTS_SUIT = "Hearts";
    public static final String DIAMONDS_SUIT = "Diamonds";
    public static final String INVALID_SUIT = "Invalid Suit";

    public static final int ACE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int TEN = 10;
    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;
    public static final int INVALID_NUMBER = -1;

    private String cardSuit;
    private int cardNumber;
    private int fullCardNumber; // 1 - 52
    private int cardColor; //0 = black, red = 1

    private int deckNumber;

    private BufferedImage image; //Takes either card back or front

    private String cardBack; //The back design
    private String cardImageString; //The card front
    private String cardHighlighted; //The highlighted card front

    private boolean faceUp = false;
    private boolean highlighted = false;

    private String location = ""; //To notify the discard pile of moves from the deck

    public Card(String suit, int number, int deckNumber, int fullNumber)
    {
        if(isValidSuit(suit) && (number >= 1 && number <= 13))
        {
            cardSuit = suit;
            cardNumber = number;
            fullCardNumber = fullNumber;
            this.deckNumber = deckNumber;

            if(deckNumber >= 1 && deckNumber <= ChangeAppearance.NUM_DECKS)
            {
                cardBack = "images/cardbacks/cardback" + deckNumber + ".png";
            }
            else
            {
                cardBack = "images/cardbacks/cardback3.png";
            }

            initializeCardImageString();
        }
        else
        {
            cardSuit = INVALID_SUIT;
            cardNumber = INVALID_NUMBER;

            cardImageString = "images/invalidcard.png";
        }

        setFaceUp();
    }

    public void highlight()
    {
        highlighted = true;

        try
        {
            URL imageURL = this.getClass().getResource(cardHighlighted);

            if (imageURL != null)
            {
                image = ImageIO.read(imageURL);
            }
        }
        catch(IOException ex)
        {
            System.err.println("Error in creating highlighted card face image.");
        }

        repaint();
    }

    public void unhighlight()
    {
        highlighted = false;

        setFaceUp();
    }

    public boolean isHighlighted()
    {
        return highlighted;
    }

    public void setFaceUp()
    {
        faceUp = true;

        try
        {
            URL imageURL = this.getClass().getResource(cardImageString);
            
            if (imageURL != null)
            {
                image = ImageIO.read(imageURL);
            }
        }
        catch(IOException ex)
        {
            System.err.println("Error in creating card face image.");
        }
    }

    public void setFaceDown()
    {
        faceUp = false;

        try
        {
            URL imageURL = this.getClass().getResource(cardBack);

            if (imageURL != null)
            {
                image = ImageIO.read(imageURL);
            }
        }
        catch(IOException ex)
        {
            System.err.println("Error in creating card back image.");
        }
    }

    public boolean isFaceUp()
    {
        return faceUp;
    }

    public boolean isValidSuit(String suit)
    {
        if(suit.equals(SPADES_SUIT) || suit.equals(DIAMONDS_SUIT) ||
                suit.equals(HEARTS_SUIT) || suit.equals(CLUBS_SUIT))
        {
            return true;
        }

        return false;
    }

    private void initializeCardImageString()
    {
        cardImageString = "images/cardfaces/";
        cardHighlighted = "images/highlightedfaces/";

        if(cardSuit.equals(SPADES_SUIT))
        {
            cardImageString += "s";
            cardHighlighted += "s";
            cardColor = 0;
        }
        else if(cardSuit.equals(CLUBS_SUIT))
        {
            cardImageString += "c";
            cardHighlighted += "c";
            cardColor = 0;
        }
        else if(cardSuit.equals(DIAMONDS_SUIT))
        {
            cardImageString += "d";
            cardHighlighted += "d";
            cardColor = 1;
        }
        else //if(cardSuit.equals(HEARTS_SUIT))
        {
            cardImageString += "h";
            cardHighlighted += "h";
            cardColor = 1;
        }

        if(cardNumber == ACE)
        {
            cardImageString += "Ace";
            cardHighlighted += "Ace";
        }
        else if(cardNumber == TWO)
        {
            cardImageString += "Two";
            cardHighlighted += "Two";
        }
        else if(cardNumber == THREE)
        {
            cardImageString += "Three";
            cardHighlighted += "Three";
        }
        else if(cardNumber == FOUR)
        {
            cardImageString += "Four";
            cardHighlighted += "Four";
        }
        else if(cardNumber == FIVE)
        {
            cardImageString += "Five";
            cardHighlighted += "Five";
        }
        else if(cardNumber == SIX)
        {
            cardImageString += "Six";
            cardHighlighted += "Six";
        }
        else if(cardNumber == SEVEN)
        {
            cardImageString += "Seven";
            cardHighlighted += "Seven";
        }
        else if(cardNumber == EIGHT)
        {
            cardImageString += "Eight";
            cardHighlighted += "Eight";
        }
        else if(cardNumber == NINE)
        {
            cardImageString += "Nine";
            cardHighlighted += "Nine";
        }
        else if(cardNumber == TEN)
        {
            cardImageString += "Ten";
            cardHighlighted += "Ten";
        }
        else if(cardNumber == JACK)
        {
            cardImageString += "Jack";
            cardHighlighted += "Jack";
        }
        else if(cardNumber == QUEEN)
        {
            cardImageString += "Queen";
            cardHighlighted += "Queen";
        }
        else //if(cardNumber == KING)
        {
            cardImageString += "King";
            cardHighlighted += "King";
        }

        cardImageString += ".png";
        cardHighlighted += "H.png";
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public int getNumber()
    {
        return cardNumber;
    }

    public String getSuit()
    {
        return cardSuit;
    }

    public int getColor()
    {
        return cardColor;
    }

    public int getFullNumber()
    {
        return fullCardNumber;
    }

    public String getSource()
    {
        return location;
    }

    public void setSource(String source)
    {
        location = source;
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public Card clone()
    {
        Card card = new Card(cardSuit, cardNumber, deckNumber, fullCardNumber);
        return card;
    }
}