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
import java.net.URL;
import javax.swing.*;

/**
 * Class: SolitairePanel
 *
 * Description: The Solitaire Panel is the main playing field view.
 *
 * @author Matt Stephen
 */
public class SolitairePanel extends JPanel
{
    private int backgroundNumber = 2;
    private Image background;

    public SolitairePanel()
    {
        URL imageURL = this.getClass().getResource("images/backgrounds/background" + backgroundNumber + ".jpg");

        if (imageURL != null)
        {
            background = new ImageIcon(imageURL).getImage();
        }
    }

    public void changeBackground(int back)
    {
        backgroundNumber = back;
        
        URL imageURL = this.getClass().getResource("images/backgrounds/background" + back + ".jpg");

        if (imageURL != null)
        {
            background = new ImageIcon(imageURL).getImage();
        }

        repaint();
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background,0,0,null);
    }
}