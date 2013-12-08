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
import javax.swing.border.TitledBorder;

/**
 * Class: ChangeOptions
 * 
 * Description: The ChangeOptions class manages several game options, such as the draw count (1 or 3).
 * 
 * @author Matt Stephen
 */
public class ChangeOptions extends JDialog implements ActionListener
{
    private int drawCount = 1;

    private JRadioButton drawOne = new JRadioButton("Draw One");
    private JRadioButton drawThree = new JRadioButton("Draw Three");

    private JCheckBox timerCheck = new JCheckBox("Timer");
    private int timer = 0; //0 = off, 1 = on

    private JCheckBox winAnimationCheck = new JCheckBox("Win Animation");
    private int animation = 0; //0 = off, 1 = on
    private JCheckBox winSoundsCheck = new JCheckBox("Win Sounds");
    private int sounds = 0; //0 = off, 1 = on

    private int difficulty = 2; //1 = easy, 2 = medium, 3 = hard

    private JRadioButton easy = new JRadioButton("Easy");
    private JRadioButton medium = new JRadioButton("Medium", true);
    private JRadioButton hard = new JRadioButton("Hard");

    private JButton ok = new JButton("Accept Options");

    private boolean exited = true;
    
    public ChangeOptions(JFrame parent, int currentDraw, int timer, int animation, int sounds, int difficulty)
    {
        setTitle("Options");
        setSize(340,190);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(parent);

        drawCount = currentDraw;
        this.timer = timer;
        this.animation = animation;
        this.sounds = sounds;
        this.difficulty = difficulty;
        setup();

        setVisible(true);
    }

    private void setup()
    {
        ButtonGroup drawCards = new ButtonGroup();
        drawCards.add(drawOne);
        drawCards.add(drawThree);

        JPanel drawPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        drawPanel.setBorder(new TitledBorder("Card Draw"));
        drawPanel.add(drawOne);
        drawPanel.add(drawThree);

        drawPanel.setMaximumSize(new Dimension(110,80));
        drawPanel.setMinimumSize(drawPanel.getMaximumSize());
        drawPanel.setPreferredSize(drawPanel.getMaximumSize());

        if(drawCount == 3)
        {
            drawThree.setSelected(true);
        }
        else
        {
            drawOne.setSelected(true);
        }

        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkBoxPanel.setBorder(new TitledBorder("Extra Settings"));
        checkBoxPanel.add(timerCheck);
        checkBoxPanel.add(winAnimationCheck);
        checkBoxPanel.add(winSoundsCheck);

        checkBoxPanel.setMaximumSize(new Dimension(120,80));
        checkBoxPanel.setMinimumSize(checkBoxPanel.getMaximumSize());
        checkBoxPanel.setPreferredSize(checkBoxPanel.getMaximumSize());

        if(timer == 1)
        {
            timerCheck.setSelected(true);
        }
        else
        {
            timerCheck.setSelected(false);
        }

        if(animation == 1)
        {
            winAnimationCheck.setSelected(true);
        }
        else
        {
            winAnimationCheck.setSelected(false);
        }

        if(sounds == 1)
        {
            winSoundsCheck.setSelected(true);
        }
        else
        {
            winSoundsCheck.setSelected(false);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(ok);

        ButtonGroup difficulties = new ButtonGroup();
        difficulties.add(easy);
        difficulties.add(medium);
        difficulties.add(hard);

        JPanel difficultyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        difficultyPanel.setBorder(new TitledBorder("Difficulty"));
        difficultyPanel.add(easy);
        difficultyPanel.add(medium);
        difficultyPanel.add(hard);

        difficultyPanel.setMaximumSize(new Dimension(110,80));
        difficultyPanel.setMinimumSize(drawPanel.getMaximumSize());
        difficultyPanel.setPreferredSize(drawPanel.getMaximumSize());

        if(difficulty == 1)
        {
            easy.setSelected(true);
        }
        else if(difficulty == 3)
        {
            hard.setSelected(true);
        }
        else
        {
            medium.setSelected(true);
        }

        JPanel p1 = new JPanel(new BorderLayout());
        p1.add(drawPanel, BorderLayout.WEST);
        p1.add(checkBoxPanel, BorderLayout.EAST);
        p1.add(difficultyPanel, BorderLayout.CENTER);
        p1.add(buttonPanel, BorderLayout.SOUTH);

        add(p1);

        drawOne.addActionListener(this);
        drawThree.addActionListener(this);
        timerCheck.addActionListener(this);
        winAnimationCheck.addActionListener(this);
        winSoundsCheck.addActionListener(this);
        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        ok.addActionListener(this);
    }

    public int getDrawCount()
    {
        if(!exited)
        {
            return drawCount;
        }

        return -1;
        
    }

    public int getTimer()
    {
        if(!exited)
        {
            return timer;
        }

        return -1;
    }

    public int getAnimation()
    {
        if(!exited)
        {
            return animation;
        }

        return -1;
    }

    public int getSounds()
    {
        if(!exited)
        {
            return sounds;
        }

        return -1;
    }

    public int getDifficulty()
    {
        return difficulty;
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == drawOne)
        {
            drawCount = 1;
        }
        else if(e.getSource() == drawThree)
        {
            drawCount = 3;
        }

        else if(e.getSource() == ok)
        {
            JOptionPane.showMessageDialog(null, "Note: Some options will take " +
                    "affect on the next game.", "Note", JOptionPane.PLAIN_MESSAGE);
            exited = false;
            setVisible(false);
        }

        else if(e.getSource() == timerCheck)
        {
            if(timerCheck.isSelected())
            {
                timer = 1;
            }
            else
            {
                timer = 0;
            }
        }
        else if(e.getSource() == winAnimationCheck)
        {
            if(winAnimationCheck.isSelected())
            {
                animation = 1;
            }
            else
            {
                animation = 0;
            }
        }
        else if(e.getSource() == winSoundsCheck)
        {
            if(winSoundsCheck.isSelected())
            {
                sounds = 1;
            }
            else
            {
                sounds = 0;
            }
        }

        else if(e.getSource() == easy)
        {
            difficulty = 1;
        }
        else if(e.getSource() == medium)
        {
            difficulty = 2;
        }
        else if(e.getSource() == hard)
        {
            difficulty = 3;
        }
    }
}