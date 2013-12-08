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
import java.awt.event.*;
import javax.swing.*;

/**
 * Class: ChangeAppearance
 * 
 * Description: The ChangeAppearance class manages the ability to change the background or card backs.
 * 
 * @author Matt Stephen
 */
public class ChangeAppearance extends JDialog implements ActionListener
{
    public static final int NUM_DECKS = 3;
    public static final int NUM_BACKGROUNDS = 3;

    public static final int FRS_DECK = 3;
    public static final int FRS_BACKGROUND = 2;

    private JRadioButton[] decks = new JRadioButton[NUM_DECKS];

    private JRadioButton[] backgrounds = new JRadioButton[NUM_BACKGROUNDS];

    private JButton ok = new JButton("Choose This Setup");

    public int deckNumber = 3;
    public int backgroundNumber = 2;

    private boolean exited = true;

    //To hold the image previews
    private JLabel cardBackLabel = new JLabel();
    private JLabel backgroundLabel = new JLabel();
    
    public ChangeAppearance(JFrame parent, int deckNumber, int backgroundNumber)
    {
        setTitle("Change Appearance");
        setSize(400,300);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(parent);

        this.deckNumber = deckNumber;
        this.backgroundNumber = backgroundNumber;
        setup();

        setVisible(true);
    }

    private void setup()
    {
        //Card backs
        JPanel cardBackPanel = new JPanel();
        cardBackPanel.setLayout(new FlowLayout());

        ButtonGroup cardBacks = new ButtonGroup();

        for(int i = 0; i < NUM_DECKS; i++)
        {
            decks[i] = new JRadioButton("Deck " + (i + 1));
            cardBacks.add(decks[i]);
            cardBackPanel.add(decks[i]);
            decks[i].addActionListener(this);
        }

        if(deckNumber <= decks.length)
        {
            decks[deckNumber - 1].setSelected(true);
        }
        else
        {
            decks[2].setSelected(true);
        }

        //Backgrounds
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new FlowLayout());

        ButtonGroup backgroundsBG = new ButtonGroup();

        for(int i = 0; i < NUM_BACKGROUNDS; i++)
        {
            backgrounds[i] = new JRadioButton("Background " + (i + 1));
            backgroundsBG.add(backgrounds[i]);
            backgroundPanel.add(backgrounds[i]);
            backgrounds[i].addActionListener(this);
        }

        if(backgroundNumber <= NUM_BACKGROUNDS)
        {
            backgrounds[backgroundNumber - 1].setSelected(true);
        }
        else
        {
            backgrounds[0].setSelected(true);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(ok);

        cardBackLabel.setIcon(new ImageIcon(this.getClass().getResource
                ("images/cardbacks/cardback" + deckNumber + ".png")));
        JPanel cardBackViewer = new JPanel();
        cardBackViewer.add(cardBackLabel);

        backgroundLabel.setIcon(new ImageIcon(this.getClass().getResource
                ("images/backgrounds/background" + backgroundNumber + "small.jpg")));
        JPanel backgroundViewer = new JPanel();
        backgroundViewer.add(backgroundLabel);

        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(2, 2, 0, 0));
        p2.add(cardBackPanel);
        p2.add(backgroundPanel);
        p2.add(cardBackViewer);
        p2.add(backgroundViewer);

        //Put everything together
        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());

        JLabel note = new JLabel("Note: Deck changes will take effect on new game");
        note.setHorizontalAlignment(JLabel.CENTER);

        p1.add(note, BorderLayout.NORTH);
        p1.add(p2, BorderLayout.CENTER);
        p1.add(buttonPanel, BorderLayout.SOUTH);

        add(p1);

        ok.addActionListener(this);
    }

    public int getDeckNumber()
    {
        if(!exited)
        {
            return deckNumber;
        }

        return -1;
    }

    public int getBackgroundNumber()
    {
        if(!exited)
        {
            return backgroundNumber;
        }

        return -1;
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == decks[0])
        {
            deckNumber = 1;
            cardBackLabel.setIcon(new ImageIcon(this.getClass().getResource
                    ("images/cardbacks/cardback" + deckNumber + ".png")));
        }
        else if(e.getSource() == decks[1])
        {
            deckNumber = 2;
            cardBackLabel.setIcon(new ImageIcon(this.getClass().getResource
                    ("images/cardbacks/cardback" + deckNumber + ".png")));
        }
        else if(e.getSource() == decks[2])
        {
            deckNumber = 3;
            cardBackLabel.setIcon(new ImageIcon(this.getClass().getResource
                    ("images/cardbacks/cardback" + deckNumber + ".png")));
        }

        else if(e.getSource() == backgrounds[0])
        {
            backgroundNumber = 1;
            backgroundLabel.setIcon(new ImageIcon(this.getClass().getResource
                ("images/backgrounds/background" + backgroundNumber + "small.jpg")));
        }
        else if(e.getSource() == backgrounds[1])
        {
            backgroundNumber = 2;
            backgroundLabel.setIcon(new ImageIcon(this.getClass().getResource
                ("images/backgrounds/background" + backgroundNumber + "small.jpg")));
        }
        else if(e.getSource() == backgrounds[2])
        {
            backgroundNumber = 3;
            backgroundLabel.setIcon(new ImageIcon(this.getClass().getResource
                ("images/backgrounds/background" + backgroundNumber + "small.jpg")));
        }

        else if(e.getSource() == ok)
        {
            exited = false;
            setVisible(false);
        }
    }
}