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

/**
 * Class: SolitaireLayout
 *
 * Description: The SolitaireLayout class manages the layout for the Solitaire board.
 *
 * @author Matt Stephen
 */
public class SolitaireLayout implements LayoutManager
{
    public static final String COLUMN_ONE = "Column One";
    public static final String COLUMN_TWO = "Column Two";
    public static final String COLUMN_THREE = "Column Three";
    public static final String COLUMN_FOUR = "Column Four";

    public static final String SPADES_ACE_PILE = "Spaces Ace Pile";
    public static final String CLUBS_ACE_PILE = "Clubs Ace Pile";
    public static final String DIAMONDS_ACE_PILE = "Diamonds Ace Pile";
    public static final String HEARTS_ACE_PILE = "Hears Ace Pile";

    public static final String DISCARD_PILE = "Discard Pile";
    public static final String DECK = "Deck";

    public static final String CELL_ONE = "Cell One";
    public static final String CELL_TWO = "Cell Two";
    public static final String CELL_THREE = "Cell Three";
    public static final String CELL_FOUR = "Cell Four";

    private Component colOne;
    private Component colTwo;
    private Component colThree;
    private Component colFour;

    private Component aceSpades;
    private Component aceClubs;
    private Component aceDiamonds;
    private Component aceHearts;

    private Component discardPile;
    private Component deck;

    private Component cellOne;
    private Component cellTwo;
    private Component cellThree;
    private Component cellFour;

    public SolitaireLayout() {}

    public void addLayoutComponent(String name,Component comp)
    {
        if(name.equals(COLUMN_ONE))
        {
            colOne = comp;
        }
        else if(name.equals(COLUMN_TWO))
        {
            colTwo = comp;
        }
        else if(name.equals(COLUMN_THREE))
        {
            colThree = comp;
        }
        else if(name.equals(COLUMN_FOUR))
        {
            colFour = comp;
        }

        else if(name.equals(SPADES_ACE_PILE))
        {
            aceSpades = comp;
        }
        else if(name.equals(CLUBS_ACE_PILE))
        {
            aceClubs = comp;
        }
        else if(name.equals(DIAMONDS_ACE_PILE))
        {
            aceDiamonds = comp;
        }
        else if(name.equals(HEARTS_ACE_PILE))
        {
            aceHearts = comp;
        }

        else if(name.equals(DISCARD_PILE))
        {
            discardPile = comp;
        }
        else if(name.equals(DECK))
        {
            deck = comp;
        }

        else if(name.equals(CELL_ONE))
        {
            cellOne = comp;
        }
        else if(name.equals(CELL_TWO))
        {
            cellTwo = comp;
        }
        else if(name.equals(CELL_THREE))
        {
            cellThree = comp;
        }
        else if(name.equals(CELL_FOUR))
        {
            cellFour = comp;
        }
    }

    public void removeLayoutComponent(Component comp)
    {
        if(colOne == comp)
        {
            colOne = null;
        }
        else if(colTwo == comp)
        {
            colTwo = null;
        }
        else if(colThree == comp)
        {
            colThree = null;
        }
        else if(colFour == comp)
        {
            colFour = null;
        }

        else if(aceSpades == comp)
        {
            aceSpades = null;
        }
        else if(aceClubs == comp)
        {
            aceClubs = null;
        }
        else if(aceDiamonds == comp)
        {
            aceDiamonds = null;
        }
        else if(aceHearts == comp)
        {
            aceHearts = null;
        }

        else if(discardPile == comp)
        {
            discardPile = null;
        }
        else if(deck == comp)
        {
            deck = null;
        }

        else if(cellOne == comp)
        {
            cellOne = null;
        }
        else if(cellTwo == comp)
        {
            cellTwo = null;
        }
        else if(cellThree == comp)
        {
            cellThree = null;
        }
        else if(cellFour == comp)
        {
            cellFour = null;
        }
    }

    public Dimension preferredLayoutSize(Container parent)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Dimension minimumLayoutSize(Container parent)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void layoutContainer(Container parent)
    {
        Component comp;

        if((comp = colOne) != null)
        {
            comp.setBounds(81,115,72,800);
        }
        if((comp = colTwo) != null)
        {
            comp.setBounds(164,115,72,800);
        }
        if((comp = colThree) != null)
        {
            comp.setBounds(247,115,72,800);
        }
        if((comp = colFour) != null)
        {
            comp.setBounds(330,115,72,800);
        }

        if((comp = aceSpades) != null)
        {
            comp.setBounds(568,3,72,96);
        }
        if((comp = aceClubs) != null)
        {
            comp.setBounds(650,3,72,96);
        }
        if((comp = aceDiamonds) != null)
        {
            comp.setBounds(568,110,72,96);
        }
        if((comp = aceHearts) != null)
        {
            comp.setBounds(650,110,72,96);
        }

        if((comp = discardPile) != null)
        {
            comp.setBounds(650,318,102,96);
        }
        if((comp = deck) != null)
        {
            comp.setBounds(568,318,72,96);
        }

        if((comp = cellOne) != null)
        {
            comp.setBounds(81,3,72,96);
        }
        if((comp = cellTwo) != null)
        {
            comp.setBounds(164,3,72,96);
        }
        if((comp = cellThree) != null)
        {
            comp.setBounds(247,3,72,96);
        }
        if((comp = cellFour) != null)
        {
            comp.setBounds(330,3,72,96);
        }
    }
}