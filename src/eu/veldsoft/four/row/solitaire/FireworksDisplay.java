/*

 This file is a part of Four Row Solitaire

 Copyright (C) 2010-2014 by Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov, Vanya Gyaurova, Plamena Popova, Hristiana Kalcheva

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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Class: FireworksDisplay
 * 
 * Description: The FireworksDisplay class manages the win animation for Four
 * Row Solitaire.
 * 
 * @author Matt Stephen
 */
class FireworksDisplay extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Number of fireworks.
	 */
	public final int NUM_FIREWORKS;

	/**
	 * Fireworks size.
	 */
	public final int FIREWORKS_SIZE;

	/**
	 * Delay.
	 */
	public static final int SET_DELAY = 10;

	/**
	 * 
	 */
	public static final int FIREWORKS_TIME = 30;

	/**
	 * Custom color.
	 */
	private static final Color CUSTOM_COLOR_1 = new Color(153, 50, 205);

	/**
	 * Fireworks.
	 */
	private int[] x;

	/**
	 * Fireworks.
	 */
	private int[] y;

	/**
	 * Array of colors.
	 */
	private Color[] colors;

	/**
	 * For firework burst.
	 */
	private int[][] xx;

	/**
	 * For firework burst.
	 */
	private int[][] yy;

	/**
	 * 
	 */
	private int num = 0;

	/**
	 * 
	 */
	private int numSets = 0;

	/**
	 * 
	 */
	private int startValue = 0;

	/**
	 * Timer.
	 */
	private Timer timer = new Timer(100, this);

	/**
	 * Sets the number of fireworks to be fired as well as their size.
	 * 
	 * @param num
	 * 
	 * @param size
	 * 
	 * @author Todor Balabanov
	 */
	public FireworksDisplay(int num, int size) {
		NUM_FIREWORKS = num;
		FIREWORKS_SIZE = size;

		x = new int[NUM_FIREWORKS];
		y = new int[NUM_FIREWORKS];
		colors = new Color[NUM_FIREWORKS];

		xx = new int[NUM_FIREWORKS][FIREWORKS_SIZE];
		yy = new int[NUM_FIREWORKS][FIREWORKS_SIZE];

		setBackground(Color.BLACK);
	}

	/**
	 * Fires the fireworks.
	 * 
	 * @author Todor Balabanov
	 */
	public void restartDisplay() {
		timer.stop();

		num = 0;

		for (int i = 0; i < x.length; i++) {
			x[i] = Common.PRNG.nextInt(300) + 300;

			for (int j = 0; j < FIREWORKS_SIZE; j++) {
				int xOffset = Common.PRNG.nextInt(151);
				int signCheck = Common.PRNG.nextInt();

				if (signCheck <= 0) {
					xx[i][j] = -xOffset;
				} else {
					xx[i][j] = xOffset;
				}
			}
		}

		for (int i = 0; i < y.length; i++) {
			y[i] = Common.PRNG.nextInt(200) + 300;

			for (int j = 0; j < FIREWORKS_SIZE; j++) {
				int yOffset = Common.PRNG.nextInt(151);
				int signCheck = Common.PRNG.nextInt();

				if (signCheck <= 0) {
					yy[i][j] = -yOffset;
				} else {
					yy[i][j] = yOffset;
				}
			}
		}

		for (int i = 0; i < colors.length; i++) {
			colors[i] = randomColor();
		}

		timer.start();
	}

	/**
	 * Generates a random number representing one of the colors below.
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public Color randomColor() {
		switch (Common.PRNG.nextInt(10)) {
		case 0:
			return Color.RED;
		case 1:
			return Color.BLUE;
		case 2:
			return Color.YELLOW;
		case 3:
			return Color.GREEN;
		case 4:
			return Color.ORANGE;
		case 5:
			return Color.CYAN;
		case 6:
			return Color.MAGENTA;
		case 7:
			return Color.PINK;
		case 8:
			return Color.WHITE;
		case 9:
			return CUSTOM_COLOR_1;
		}

		return null;
	}

	/**
	 * Paint procedure.
	 * 
	 * @param g
	 * 
	 * @author Todor Balabanov
	 */
	public void paint(Graphics g) {
		super.paint(g);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.RED);
		g.drawString("You Win! -- Click to Close.", 340, 550);

		/*
		 * Longer set one.
		 */
		if (numSets < 5) {
			for (int i = startValue; i < startValue + 2; i++) {
				if (num < 2 * FIREWORKS_TIME / 3) {
					int x0 = 0;
					int y0 = getHeight()
							- (num * y[i] / (2 * FIREWORKS_TIME / 3));

					/*
					 * Fire from left side.
					 */
					if (i % 2 == 0) {
						x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
					}
					/*
					 * Fire from right side.
					 */
					else {
						x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
					}

					g.setColor(colors[i]);
					g.drawRect(x0, y0, 5, 5);
				} else {
					num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

					for (int j = 0; j < FIREWORKS_SIZE; j++) {
						g.setColor(colors[i]);

						if (i % 2 == 0) {
							g.drawLine(
									x[i],
									getHeight() - y[i],
									x[i]
											+ (num * xx[i][j] / (NUM_FIREWORKS / 3)),
									getHeight()
											- (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
						} else {
							g.drawLine(
									getWidth() - x[i],
									getHeight() - y[i],
									getWidth()
											- (x[i] + num * xx[i][j]
													/ (NUM_FIREWORKS / 3)),
									getHeight()
											- (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
						}
					}

					num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
				}
			}
		}
		/*
		 * Longer set two.
		 */
		else if (numSets < 10) {
			for (int i = startValue; i < startValue + 3; i++) {
				if (num < 2 * FIREWORKS_TIME / 3) {
					int x0 = 0;
					int y0 = getHeight()
							- (num * y[i] / (2 * FIREWORKS_TIME / 3));

					/*
					 * Fire from left side.
					 */
					if (i % 2 == 0) {
						x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
					}
					/*
					 * Fire from right side.
					 */
					else {
						x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
					}

					g.setColor(colors[i]);
					g.drawRect(x0, y0, 5, 5);
				} else {
					num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

					for (int j = 0; j < FIREWORKS_SIZE; j++) {
						g.setColor(colors[i]);

						if (i % 2 == 0) {
							g.drawLine(
									x[i],
									getHeight() - y[i],
									x[i]
											+ (num * xx[i][j] / (NUM_FIREWORKS / 3)),
									getHeight()
											- (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
						} else {
							g.drawLine(
									getWidth() - x[i],
									getHeight() - y[i],
									getWidth()
											- (x[i] + num * xx[i][j]
													/ (NUM_FIREWORKS / 3)),
									getHeight()
											- (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
						}
					}

					num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
				}
			}
		}
		/*
		 * Longer set three.
		 */
		else if (numSets < 15) {
			for (int i = startValue; i < startValue + 4; i++) {
				if (num < 2 * FIREWORKS_TIME / 3) {
					int x0 = 0;
					int y0 = getHeight()
							- (num * y[i] / (2 * FIREWORKS_TIME / 3));

					/*
					 * Fire from left side.
					 */
					if (i % 2 == 0) {
						x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
					}
					/*
					 * Fire from right side.
					 */
					else {
						x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
					}

					g.setColor(colors[i]);
					g.drawRect(x0, y0, 5, 5);
				} else {
					num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

					for (int j = 0; j < FIREWORKS_SIZE; j++) {
						g.setColor(colors[i]);

						if (i % 2 == 0) {
							g.drawLine(
									x[i],
									getHeight() - y[i],
									x[i]
											+ (num * xx[i][j] / (NUM_FIREWORKS / 3)),
									getHeight()
											- (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
						} else {
							g.drawLine(
									getWidth() - x[i],
									getHeight() - y[i],
									getWidth()
											- (x[i] + num * xx[i][j]
													/ (NUM_FIREWORKS / 3)),
									getHeight()
											- (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
						}

					}

					num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
				}
			}
		}
		/*
		 * Longer set four.
		 */
		else if (numSets < 20) {
			for (int i = startValue; i < startValue + 5; i++) {
				if (num < 2 * FIREWORKS_TIME / 3) {
					int x0 = 0;
					int y0 = getHeight()
							- (num * y[i] / (2 * FIREWORKS_TIME / 3));

					/*
					 * Fire from left side.
					 */
					if (i % 2 == 0) {
						x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
					}
					/*
					 * Fire from right side.
					 */
					else {
						x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
					}

					g.setColor(colors[i]);
					g.drawRect(x0, y0, 5, 5);
				} else {
					num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

					for (int j = 0; j < FIREWORKS_SIZE; j++) {
						g.setColor(colors[i]);

						if (i % 2 == 0) {
							g.drawLine(
									x[i],
									getHeight() - y[i],
									x[i]
											+ (num * xx[i][j] / (NUM_FIREWORKS / 3)),
									getHeight()
											- (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
						} else {
							g.drawLine(
									getWidth() - x[i],
									getHeight() - y[i],
									getWidth()
											- (x[i] + num * xx[i][j]
													/ (NUM_FIREWORKS / 3)),
									getHeight()
											- (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
						}
					}

					num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
				}
			}
		}
		/*
		 * Longer set five.
		 */
		else if (numSets < 25) {
			for (int i = startValue; i < startValue + 10; i++) {
				if (num < 2 * FIREWORKS_TIME / 3) {
					int x0 = 0;
					int y0 = getHeight()
							- (num * y[i] / (2 * FIREWORKS_TIME / 3));

					/*
					 * Fire from left side.
					 */
					if (i % 2 == 0) {
						x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
					}
					/*
					 * Fire from right side.
					 */
					else {
						x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
					}

					g.setColor(colors[i]);
					g.drawRect(x0, y0, 5, 5);
				} else {
					num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

					for (int j = 0; j < FIREWORKS_SIZE; j++) {
						g.setColor(colors[i]);

						if (i % 2 == 0) {
							g.drawLine(
									x[i],
									getHeight() - y[i],
									x[i]
											+ (num * xx[i][j] / (NUM_FIREWORKS / 3)),
									getHeight()
											- (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
						} else {
							g.drawLine(
									getWidth() - x[i],
									getHeight() - y[i],
									getWidth()
											- (x[i] + num * xx[i][j]
													/ (NUM_FIREWORKS / 3)),
									getHeight()
											- (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
						}
					}

					num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
				}
			}
		}
		/*
		 * Longer set six (finale).
		 */
		else if (numSets < 26) {
			for (int i = startValue; i < x.length; i++) {
				if (num < 2 * FIREWORKS_TIME / 3) {
					int x0 = 0;
					int y0 = getHeight()
							- (num * y[i] / (2 * FIREWORKS_TIME / 3));

					/*
					 * Fire from left side.
					 */
					if (i % 2 == 0) {
						x0 = num * x[i] / (2 * FIREWORKS_TIME / 3);
					}
					/*
					 * Fire from right side.
					 */
					else {
						x0 = getWidth() - num * x[i] / (2 * FIREWORKS_TIME / 3);
					}

					g.setColor(colors[i]);
					g.drawRect(x0, y0, 5, 5);
				} else {
					num -= Math.ceil(2 * FIREWORKS_TIME / 3.0);

					for (int j = 0; j < FIREWORKS_SIZE; j++) {
						g.setColor(colors[i]);

						if (i % 2 == 0) {
							g.drawLine(
									x[i],
									getHeight() - y[i],
									x[i]
											+ (num * xx[i][j] / (NUM_FIREWORKS / 3)),
									getHeight()
											- (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
						} else {
							g.drawLine(
									getWidth() - x[i],
									getHeight() - y[i],
									getWidth()
											- (x[i] + num * xx[i][j]
													/ (NUM_FIREWORKS / 3)),
									getHeight()
											- (y[i] + (num * yy[i][j] / (NUM_FIREWORKS / 3))));
						}
					}

					num += Math.ceil(2 * FIREWORKS_TIME / 3.0);
				}
			}
		}
	}

	/**
	 * Action performed.
	 * 
	 * @param e
	 * 
	 * @author Todor Balabanov
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() != timer) {
			return;
		}
		if (num >= FIREWORKS_TIME) {
			num = 0;
			numSets++;

			startValue = Common.PRNG.nextInt(x.length / 2);
		}

		num++;

		if (numSets >= 26) {
			timer.stop();
		} else {
			repaint();
		}
	}
}
