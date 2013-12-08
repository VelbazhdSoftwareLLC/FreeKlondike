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

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 * Class: DealDeck
 *
 * Description: The DealDeck class manages the leftover cards after the deal out.
 *
 * @author Matt Stephen
 */
public class DealDeck extends CardStack
{
    private DiscardPile discardPile;
    private int numTimesThroughDeck = 1;

    private int drawCount = 1;
    private int difficulty = 2; //1, 2, or 3

    //private static final int DRAW_ONE_THROUGH_LIMIT = 2;
    //private static final int DRAW_THREE_THROUGH_LIMIT = 3;

    private static final int EASY_THROUGH_LIMIT = 3; //Number of deck throughs for each difficulty
    private static final int MEDIUM_THROUGH_LIMIT = 2; //Three card draw adds 1 to each
    private static final int HARD_THROUGH_LIMIT = 1;

    private int deckThroughLimit;
    private boolean redealable = true;

    public DealDeck(DiscardPile discard, int drawCount)
    {
        discardPile = discard;
        this.drawCount = drawCount;

        if(drawCount == 3)
        {
            deckThroughLimit = MEDIUM_THROUGH_LIMIT + 1;
        }
        else
        {
            deckThroughLimit = MEDIUM_THROUGH_LIMIT;
        }

        discard.setDrawCount(drawCount);
    }

    public void reset()
    {
        numTimesThroughDeck = 1;
    }

    private void undone()
    {
        numTimesThroughDeck--;
    }

    public int getDeckThroughs()
    {
        return numTimesThroughDeck;
    }

    public void setDeckThroughs(int throughs)
    {
        numTimesThroughDeck = throughs;
    }

    public void setDeck(LinkedList<Card> cards)
    {
        for(int i = 0; i < cards.size(); i++)
        {
            cards.get(i).setFaceDown();
            addCard(cards.get(i));
        }
    }

    public void setDrawCount(int draw)
    {
        drawCount = draw;
        discardPile.setDrawCount(draw);

        if(drawCount == 3)
        {
            deckThroughLimit = MEDIUM_THROUGH_LIMIT + 1;
        }
        else
        {
            deckThroughLimit = MEDIUM_THROUGH_LIMIT;
        }
    }

    public void setDifficulty(int difficulty)
    {
        this.difficulty = difficulty;

        if(difficulty == 1)
        {
            deckThroughLimit = EASY_THROUGH_LIMIT;
        }
        else if(difficulty == 3)
        {
            deckThroughLimit = HARD_THROUGH_LIMIT;
        }
        else //if(difficulty == 2)
        {
            deckThroughLimit = MEDIUM_THROUGH_LIMIT;
        }

        if(drawCount == 3)
        {
            deckThroughLimit++; //draw three has an extra deck through on top of the single card setting
        }
    }

    public boolean hasDealsLeft()
    {
        return redealable;
    }

    public synchronized Card pop()
    {
        if(!isEmpty())
        {
            //Verify there are still cards remaining
            if(drawCount == 1)
            {
                Card card = super.pop();

                card.setFaceUp();
                discardPile.push(card);

                this.repaint();
                return card;
            }
            else
            {
                int tempDrawCount = drawCount;
                CardStack tempStack = new CardStack();

                while(drawCount > 1 && tempDrawCount > 0 && !isEmpty())
                {
                    Card card = super.pop();

                    card.setFaceUp();
                    tempStack.push(card);

                    tempDrawCount--;
                }

                CardStack tempStack2 = new CardStack(); //To put the cards back in order because the previous step reversed them

                for(int i = tempStack.length(); i > 0; i--)
                {
                    tempStack2.push(tempStack.pop());
                }

                discardPile.push(tempStack2);

                this.repaint();
                return discardPile.peek();
            }
        }
        else if(!discardPile.isEmpty() && numTimesThroughDeck < deckThroughLimit)
        {
            for(int i = discardPile.length(); i > 0; i--)
            {
                Card card = discardPile.pop();
                card.setFaceDown();
                card.setSource("Deck");
                push(card);
            }

            discardPile.repaint();
            numTimesThroughDeck++;
        }
        else if(numTimesThroughDeck >= deckThroughLimit)
        {
            redealable = false;
            JOptionPane.showMessageDialog(null, "You have reached your deck through limit.");
        }

        this.repaint();
        return null;
    }

    public synchronized void undoPop()
    {
        while(!isEmpty())
        {
            Card card = super.pop();
            card.setFaceUp();
            discardPile.push(card);
        }

        undone();

        if(!redealable)
        {
            redealable = true;
        }
        
        discardPile.repaint();
        this.repaint();
    }

    public Card getCardAtLocation(Point p)
    {
        return peek();
    }

    public boolean isValidMove(Card card)
    {
        return false;
    }

    public boolean isValidMove(CardStack stack)
    {
        return false;
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        
        if(!isEmpty())
        {
            for(int i = 0; i < length(); i++)
            {
                Image image = getCardAtLocation(i).getImage();
                g.drawImage(image, 0, 0, null);
            }
        }
    }
}