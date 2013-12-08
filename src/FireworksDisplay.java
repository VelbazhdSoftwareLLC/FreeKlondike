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
import java.util.Random;

/**
 * Class: FireworksDisplay
 * 
 * Description: The FireworksDisplay class manages the win animation for Four Row Solitaire.
 * 
 * @author Matt Stephen
 */
public class FireworksDisplay extends JPanel implements ActionListener
{
    public final int NUM_FIREWORKS;
    public final int FIREWORKS_SIZE;
    public static final int SET_DELAY = 10;
    public static final int FIREWORKS_TIME = 30;

    private int[] x;;
    private int[] y;
    private Color[] colors;

    private int[][] xx; //For firework burst
    private int[][] yy; //For firework burst

    private int num = 0;
    private int numSets = 0;
    private int startValue = 0;

    private Timer timer = new Timer(100, this);
    private Random random = new Random();

    public FireworksDisplay(int num, int size)
    {
        NUM_FIREWORKS = num;
        FIREWORKS_SIZE = size;

        x = new int[NUM_FIREWORKS];
        y = new int[NUM_FIREWORKS];
        colors = new Color[NUM_FIREWORKS];

        xx = new int[NUM_FIREWORKS][FIREWORKS_SIZE];
        yy = new int[NUM_FIREWORKS][FIREWORKS_SIZE];

        setBackground(Color.BLACK);
    }

    public void restartDisplay()
    {
        timer.stop();

        num = 0;

        for(int i = 0; i < x.length; i++)
        {
            x[i] = (int)(Math.random() * 300) + 300;

            for(int j = 0; j < FIREWORKS_SIZE; j++)
            {
                int xOffset = random.nextInt(151);
		double signCheck = Math.random();

		if(signCheck <= .5)
		{
                    xx[i][j] = -xOffset;
                }
		else
		{
                    xx[i][j] = xOffset;
		}
            }
        }

        for(int i = 0; i < y.length; i++)
        {
            y[i] = (int)(Math.random() * 200) + 300;

            for(int j = 0; j < FIREWORKS_SIZE; j++)
            {
                int yOffset = random.nextInt(151);
		double signCheck = Math.random();

		if(signCheck <= .5)
		{
                    yy[i][j] = -yOffset;
		}
		else
		{
                    yy[i][j] = yOffset;
		}
            }
        }

	for(int i = 0; i < colors.length; i++)
	{
            colors[i] = randomColor();
	}

        timer.start();
    }

    public Color randomColor()
    {
        double rand = Math.random();

        if(rand <= .1)
        {
            return Color.RED;
        }
        else if(rand <= .2)
        {
            return Color.BLUE;
        }
        else if(rand <= .3)
        {
            return Color.YELLOW;
        }
        else if(rand <= .4)
        {
            return Color.GREEN;
        }
        else if(rand <= .5)
        {
            return Color.ORANGE;
        }
        else if(rand <= .6)
        {
            return Color.CYAN;
        }
        else if(rand <= .7)
        {
            return Color.MAGENTA;
        }
        else if(rand <= .8)
        {
            return Color.PINK;
        }
        else if(rand <= .9)
        {
            return Color.WHITE;
        }
        else
        {
            return new Color(153,50,205);
        }
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());

        g.setColor(Color.RED);
        g.drawString("You Win! -- Click to Close.",340,550);

        //Longer set one
        if(numSets < 5)
        {
            for(int i = startValue; i < startValue + 2; i++)
            {
                if(num < 2 * FIREWORKS_TIME / 3)
                {
                    int x0 = 0;
                    int y0 = getHeight() - (num * y[i] / (2 * FIREWORKS_TIME / 3));

                    //Fire from left side
                    if(i%2 == 0)
                    {
                        x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }
                    //Fire from right side
                    else
                    {
                        x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }

                    g.setColor(colors[i]);
                    g.drawRect(x0, y0, 5, 5);
                }
                else
                {
                    num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

                    for(int j = 0; j < FIREWORKS_SIZE; j++)
                    {
                        g.setColor(colors[i]);

                        if(i%2 == 0)
                        {
                            g.drawLine(x[i], getHeight() - y[i], x[i] + (num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(x[i] + (num * xx[i][j] / 10), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }
                        else
                        {
                            g.drawLine(getWidth() - x[i], getHeight() - y[i], getWidth() - (x[i] + num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(getWidth() - (x[i] + (num * xx[i][j] / 10)), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }

                    }

                    num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
                }
            }
        }
        //Longer set two
        else if(numSets < 10)
        {
            for(int i = startValue; i < startValue + 3; i++)
            {
                if(num < 2 * FIREWORKS_TIME / 3)
                {
                    int x0 = 0;
                    int y0 = getHeight() - (num * y[i] / (2 * FIREWORKS_TIME / 3));

                    //Fire from left side
                    if(i%2 == 0)
                    {
                        x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }
                    //Fire from right side
                    else
                    {
                        x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }

                    g.setColor(colors[i]);
                    g.drawRect(x0, y0, 5, 5);
                }
                else
                {
                    num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

                    for(int j = 0; j < FIREWORKS_SIZE; j++)
                    {
                        g.setColor(colors[i]);

                        if(i%2 == 0)
                        {
                            g.drawLine(x[i], getHeight() - y[i], x[i] + (num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(x[i] + (num * xx[i][j] / 10), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }
                        else
                        {
                            g.drawLine(getWidth() - x[i], getHeight() - y[i], getWidth() - (x[i] + num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(getWidth() - (x[i] + (num * xx[i][j] / 10)), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }

                    }

                    num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
                }
            }
        }
        //Longer set three
        else if(numSets < 15)
        {
            for(int i = startValue; i < startValue + 4; i++)
            {
                if(num < 2 * FIREWORKS_TIME / 3)
                {
                    int x0 = 0;
                    int y0 = getHeight() - (num * y[i] / (2 * FIREWORKS_TIME / 3));

                    //Fire from left side
                    if(i%2 == 0)
                    {
                        x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }
                    //Fire from right side
                    else
                    {
                        x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }

                    g.setColor(colors[i]);
                    g.drawRect(x0, y0, 5, 5);
                }
                else
                {
                    num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

                    for(int j = 0; j < FIREWORKS_SIZE; j++)
                    {
                        g.setColor(colors[i]);

                        if(i%2 == 0)
                        {
                            g.drawLine(x[i], getHeight() - y[i], x[i] + (num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(x[i] + (num * xx[i][j] / 10), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }
                        else
                        {
                            g.drawLine(getWidth() - x[i], getHeight() - y[i], getWidth() - (x[i] + num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(getWidth() - (x[i] + (num * xx[i][j] / 10)), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }

                    }

                    num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
                }
            }
        }
        //Longer set four
        else if(numSets < 20)
        {
            for(int i = startValue; i < startValue + 5; i++)
            {
                if(num < 2 * FIREWORKS_TIME / 3)
                {
                    int x0 = 0;
                    int y0 = getHeight() - (num * y[i] / (2 * FIREWORKS_TIME / 3));

                    //Fire from left side
                    if(i%2 == 0)
                    {
                        x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }
                    //Fire from right side
                    else
                    {
                        x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }

                    g.setColor(colors[i]);
                    g.drawRect(x0, y0, 5, 5);
                }
                else
                {
                    num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

                    for(int j = 0; j < FIREWORKS_SIZE; j++)
                    {
                        g.setColor(colors[i]);

                        if(i%2 == 0)
                        {
                            g.drawLine(x[i], getHeight() - y[i], x[i] + (num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(x[i] + (num * xx[i][j] / 10), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }
                        else
                        {
                            g.drawLine(getWidth() - x[i], getHeight() - y[i], getWidth() - (x[i] + num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(getWidth() - (x[i] + (num * xx[i][j] / 10)), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }

                    }

                    num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
                }
            }
        }
        //Longer set five
        else if(numSets < 25)
        {
            for(int i = startValue; i < startValue + 10; i++)
            {
                if(num < 2 * FIREWORKS_TIME / 3)
                {
                    int x0 = 0;
                    int y0 = getHeight() - (num * y[i] / (2 * FIREWORKS_TIME / 3));

                    //Fire from left side
                    if(i%2 == 0)
                    {
                        x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }
                    //Fire from right side
                    else
                    {
                        x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }

                    g.setColor(colors[i]);
                    g.drawRect(x0, y0, 5, 5);
                }
                else
                {
                    num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

                    for(int j = 0; j < FIREWORKS_SIZE; j++)
                    {
                        g.setColor(colors[i]);

                        if(i%2 == 0)
                        {
                            g.drawLine(x[i], getHeight() - y[i], x[i] + (num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(x[i] + (num * xx[i][j] / 10), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }
                        else
                        {
                            g.drawLine(getWidth() - x[i], getHeight() - y[i], getWidth() - (x[i] + num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(getWidth() - (x[i] + (num * xx[i][j] / 10)), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }

                    }

                    num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
                }
            }
        }
        //Longer set six (finale)
        else if(numSets < 26)
        {
            for(int i = startValue; i < x.length; i++)
            {
                if(num < 2 * FIREWORKS_TIME / 3)
                {
                    int x0 = 0;
                    int y0 = getHeight() - (num * y[i] / (2 * FIREWORKS_TIME / 3));

                    //Fire from left side
                    if(i%2 == 0)
                    {
                        x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }
                    //Fire from right side
                    else
                    {
                        x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }

                    g.setColor(colors[i]);
                    g.drawRect(x0, y0, 5, 5);
                }
                else
                {
                    num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

                    for(int j = 0; j < FIREWORKS_SIZE; j++)
                    {
                        g.setColor(colors[i]);

                        if(i%2 == 0)
                        {
                            g.drawLine(x[i], getHeight() - y[i], x[i] + (num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(x[i] + (num * xx[i][j] / 10), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }
                        else
                        {
                            g.drawLine(getWidth() - x[i], getHeight() - y[i], getWidth() - (x[i] + num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(getWidth() - (x[i] + (num * xx[i][j] / 10)), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }

                    }

                    num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
                }
            }
        }

        //Shorter sets
        /*//Set one
        if(num <= FIREWORKS_TIME)
        {
            for(int i = 0; i < Math.ceil(NUM_FIREWORKS / 16.0); i++)
            {
                if(num < 2 * FIREWORKS_TIME / 3)
                {
                    int x0 = 0;
                    int y0 = getHeight() - (num * y[i] / (2 * FIREWORKS_TIME / 3));

                    //Fire from left side
                    if(i%2 == 0)
                    {
                        x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }
                    //Fire from right side
                    else
                    {
                        x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }

                    g.setColor(colors[i]);
                    g.drawRect(x0, y0, 5, 5);
                }
                else
                {
                    num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

                    for(int j = 0; j < FIREWORKS_SIZE; j++)
                    {
                        g.setColor(colors[i]);

                        if(i%2 == 0)
                        {
                            g.drawLine(x[i], getHeight() - y[i], x[i] + (num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(x[i] + (num * xx[i][j] / 10), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }
                        else
                        {
                            g.drawLine(getWidth() - x[i], getHeight() - y[i], getWidth() - (x[i] + num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(getWidth() - (x[i] + (num * xx[i][j] / 10)), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }
                        
                    }

                    num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
                }
            }
        }
        //Set two
        else if(num > (FIREWORKS_TIME + SET_DELAY) && num <= (2 * FIREWORKS_TIME + SET_DELAY))
        {
            num -= (FIREWORKS_TIME + SET_DELAY);

            for(int i = (int)Math.ceil(NUM_FIREWORKS / 16.0); i < Math.ceil(NUM_FIREWORKS / 8.0); i++)
            {
                if(num < 2 * FIREWORKS_TIME / 3)
                {
                    int x0 = 0;
                    int y0 = getHeight() - (num * y[i] / (2 * FIREWORKS_TIME / 3));

                    //Fire from left side
                    if(i%2 == 0)
                    {
                        x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }
                    //Fire from right side
                    else
                    {
                        x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }

                    g.setColor(colors[i]);
                    g.drawRect(x0, y0, 5, 5);
                }
                else
                {
                    num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

                    for(int j = 0; j < FIREWORKS_SIZE; j++)
                    {
                        g.setColor(colors[i]);

                        if(i%2 == 0)
                        {
                            g.drawLine(x[i], getHeight() - y[i], x[i] + (num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(x[i] + (num * xx[i][j] / 10), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }
                        else
                        {
                            g.drawLine(getWidth() - x[i], getHeight() - y[i], getWidth() - (x[i] + num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(getWidth() - (x[i] + (num * xx[i][j] / 10)), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }

                    }

                    num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
                }
            }

            num += (FIREWORKS_TIME + SET_DELAY);
        }
        //Set three
        else if(num > (2 * FIREWORKS_TIME + 2 * SET_DELAY) && num <= (3 * FIREWORKS_TIME + 2 * SET_DELAY))
        {
            num -= (2 * FIREWORKS_TIME + 2 * SET_DELAY);

            for(int i = (int)Math.ceil(NUM_FIREWORKS / 8.0); i < Math.ceil(NUM_FIREWORKS / 4.0); i++)
            {
                if(num < 2 * FIREWORKS_TIME / 3)
                {
                    int x0 = 0;
                    int y0 = getHeight() - (num * y[i] / (2 * FIREWORKS_TIME / 3));

                    //Fire from left side
                    if(i%2 == 0)
                    {
                        x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }
                    //Fire from right side
                    else
                    {
                        x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }

                    g.setColor(colors[i]);
                    g.drawRect(x0, y0, 5, 5);
                }
                else
                {
                    num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

                    for(int j = 0; j < FIREWORKS_SIZE; j++)
                    {
                        g.setColor(colors[i]);

                        if(i%2 == 0)
                        {
                            g.drawLine(x[i], getHeight() - y[i], x[i] + (num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(x[i] + (num * xx[i][j] / 10), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }
                        else
                        {
                            g.drawLine(getWidth() - x[i], getHeight() - y[i], getWidth() - (x[i] + num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(getWidth() - (x[i] + (num * xx[i][j] / 10)), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }

                    }

                    num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
                }
            }

            num += (2 * FIREWORKS_TIME + 2 * SET_DELAY);
        }
        //Set four
        else if(num > (3 * FIREWORKS_TIME + 3 * SET_DELAY) && num <= (4 * FIREWORKS_TIME + 3 * SET_DELAY))
        {
            num -= (3 * FIREWORKS_TIME + 3 * SET_DELAY);

            for(int i = (int)Math.ceil(NUM_FIREWORKS / 4.0); i < Math.ceil(NUM_FIREWORKS / 2.0); i++)
            {
                if(num < 2 * FIREWORKS_TIME / 3)
                {
                    int x0 = 0;
                    int y0 = getHeight() - (num * y[i] / (2 * FIREWORKS_TIME / 3));

                    //Fire from left side
                    if(i%2 == 0)
                    {
                        x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }
                    //Fire from right side
                    else
                    {
                        x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }

                    g.setColor(colors[i]);
                    g.drawRect(x0, y0, 5, 5);
                }
                else
                {
                    num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

                    for(int j = 0; j < FIREWORKS_SIZE; j++)
                    {
                        g.setColor(colors[i]);

                        if(i%2 == 0)
                        {
                            g.drawLine(x[i], getHeight() - y[i], x[i] + (num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(x[i] + (num * xx[i][j] / 10), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }
                        else
                        {
                            g.drawLine(getWidth() - x[i], getHeight() - y[i], getWidth() - (x[i] + num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(getWidth() - (x[i] + (num * xx[i][j] / 10)), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }

                    }

                    num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
                }
            }

            num += (3 * FIREWORKS_TIME + 3 * SET_DELAY);
        }
        //Set five (finale)
        else
        {
            num -= (4 * FIREWORKS_TIME + 4 * SET_DELAY);

            for(int i = (int)Math.ceil(NUM_FIREWORKS / 2.0); i < NUM_FIREWORKS; i++)
            {
                if(num < 2 * FIREWORKS_TIME / 3)
                {
                    int x0 = 0;
                    int y0 = getHeight() - (num * y[i] / (2 * FIREWORKS_TIME / 3));

                    //Fire from left side
                    if(i%2 == 0)
                    {
                        x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }
                    //Fire from right side
                    else
                    {
                        x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
                    }

                    g.setColor(colors[i]);
                    g.drawRect(x0, y0, 5, 5);
                }
                else
                {
                    num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

                    for(int j = 0; j < FIREWORKS_SIZE; j++)
                    {
                        g.setColor(colors[i]);

                        if(i%2 == 0)
                        {
                            g.drawLine(x[i], getHeight() - y[i], x[i] + (num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(x[i] + (num * xx[i][j] / 10), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }
                        else
                        {
                            g.drawLine(getWidth() - x[i], getHeight() - y[i], getWidth() - (x[i] + num * xx[i][j] / (NUM_FIREWORKS / 3)), getHeight() - (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
                            //g.drawRect(getWidth() - (x[i] + (num * xx[i][j] / 10)), getHeight() - (y[i] + (num * yy[i][j] / 10)), 5, 5);
                        }

                    }

                    num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
                }
            }

            num += (4 * FIREWORKS_TIME + 4 * SET_DELAY);
        }*/
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == timer)
        {
            if(num >= FIREWORKS_TIME)
            {
                num = 0;
                numSets++;

                startValue = random.nextInt(x.length / 2);
            }

            num++;
            
            if(numSets >= 26)
            {
                timer.stop();
            }
            else
            {
                repaint();
            }

            //Shorter sets
            /*num++;

            if(num > 5 * FIREWORKS_TIME + 4 * SET_DELAY)
            {
                timer.stop();
            }

            repaint();*/
        }
    }
}