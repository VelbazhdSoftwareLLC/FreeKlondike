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

import java.awt.Point;

/**
 * Class: SingleCell
 *
 * Description: The SingleCell class manages an individual cell that can only hold one card.
 *
 * @author Matt Stephen
 */
public class SingleCell extends CardStack
{
    public SingleCell() {}

    public Card push(Card card)
    {
        if(isEmpty())
        {
            super.push(card);
            return card;
        }

        return null;
    }

    public Card getCardAtLocation(Point p)
    {
        return peek();
    }

    public boolean isValidMove(Card card)
    {
        if(isEmpty())
        {
            return true;
        }

        return false;
    }

    public boolean isValidMove(CardStack stack)
    {
        return false;
    }
}