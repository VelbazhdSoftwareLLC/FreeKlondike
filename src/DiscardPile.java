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

/**
 * Class: DiscardPile
 *
 * Description: The DiscardPile class manages the stack of Cards discarded from the deck.
 *
 * @author Matt Stephen
 */
public class DiscardPile extends CardStack
{
    private int drawCount = 1;
    private int cardsLeftFromDraw = 0;

    public DiscardPile(int draw)
    {
        drawCount = draw;
    }

    public void setDrawCount(int draw)
    {
        drawCount = draw;
    }

    public int getNumViewableCards()
    {
        return cardsLeftFromDraw;
    }

    public void setView(int numViewableCards)
    {
        cardsLeftFromDraw = numViewableCards;
    }

    public void addCard(Card card)
    {
        cardsLeftFromDraw++;
        super.addCard(card);
    }

    public void addStack(CardStack stack)
    {
        for(int i = stack.length(); i > 0; i--)
        {
            Card card = stack.pop();
            addCard(card);
        }
    }

    public Card push(Card card)
    {
        if(drawCount == 1)
        {
            cardsLeftFromDraw = 0;
        }
        
        addCard(card);
        card.setSource("");
        return card;
    }

    public CardStack push(CardStack stack)
    {
        if(drawCount != 1 || (drawCount == 1 && stack.length() == 1))
        {
            cardsLeftFromDraw = 0;
            
            for(int i = 0; !stack.isEmpty(); i++)
            {
                push(stack.pop());
            }
        }

        return stack;
    }

    public synchronized Card pop()
    {
	Card card = super.pop();

        //To make the display of multiple cards correct
        //(After a player removes the top card of draw 3, it shouldn't display the top 3 cards)
        if(cardsLeftFromDraw > 0)
        {
            cardsLeftFromDraw--;
        }
        else
        {
            cardsLeftFromDraw = 0;
        }

        return card;
    }

    public synchronized Card undoPop()
    {
	Card card = super.pop();
        return card;
    }

    public Card getCardAtLocation(Point p)
    {
        return peek();
    }

    public boolean isValidMove(Card card)
    {
        if(card.getSource().equals("Deck"))
        {
           return true;
        }

        return false;
    }

    public boolean isValidMove(CardStack stack)
    {
        return false;
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        
        if(!isEmpty() && drawCount == 1)
        {
            for(int i = 0; i < length(); i++)
            {
                Image image = getCardAtLocation(i).getImage();
                g.drawImage(image, 0, 0, null);
            }
        }
        else if(!isEmpty() && drawCount == 3)
        {
            if(cardsLeftFromDraw > 0)
            {
                for(int i = 0; i < length() - cardsLeftFromDraw + 1; i++)
                {
                    Image image = getCardAtLocation(i).getImage();
                    g.drawImage(image, 0, 0, null);
                }

                for(int i = length() - cardsLeftFromDraw + 1; i < length(); i++)
                {
                    Image image = getCardAtLocation(i).getImage();

                    if((cardsLeftFromDraw == 3 && i == length() - 2) || (cardsLeftFromDraw == 2 && i == length() - 1))
                    {
                        g.drawImage(image, 15, 0, null);
                    }
                    else if(cardsLeftFromDraw == 3 && i == length() - 1)
                    {
                        g.drawImage(image, 30, 0, null);
                    }
                }
            }
            else
            {
                for(int i = 0; i < length(); i++)
                {
                    Image image = getCardAtLocation(i).getImage();
                    g.drawImage(image, 0, 0, null);
                }
            }
        }
    }
}