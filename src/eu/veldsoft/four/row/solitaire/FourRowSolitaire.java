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

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

/**
 * Class: FourRowSolitaire
 * 
 * Description: The FourRowSolitaire class adds a menu to the SolitaireBoard
 * Frame.
 * 
 * @author Matt Stephen
 */
public class FourRowSolitaire extends SolitaireFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String version = ".63";

	/**
	 * Menu bar.
	 */
	private JMenuBar menuBar = new JMenuBar();

	/**
	 * Game menu.
	 */
	private JMenu game = new JMenu("Game");

	/**
	 * Help menu.
	 */
	private JMenu helpMenu = new JMenu("Help");

	/**
	 * Submenus.
	 * 
	 * New game.
	 */
	private JMenuItem newGame = new JMenuItem("New Game");

	/**
	 * Undo
	 */
	private JMenuItem undo = new JMenuItem("Undo Last Move");

	/**
	 * Hint.
	 */
	private JMenuItem hint = new JMenuItem("Hint");

	/**
	 * Statistics.
	 */
	private JMenuItem statistics = new JMenuItem("Statistics");

	/**
	 * Options.
	 */
	private JMenuItem options = new JMenuItem("Options");

	/**
	 * Change Appearance.
	 */
	private JMenuItem appearance = new JMenuItem("Change Appearance");

	/**
	 * Best Times.
	 */
	private JMenuItem topTimes = new JMenuItem("Best Times");

	/**
	 * Exit.
	 */
	private JMenuItem exit = new JMenuItem("Exit");

	/**
	 * Submenus.
	 * 
	 * View Help.
	 */
	private JMenuItem help = new JMenuItem("View Help");

	/**
	 * About Game.
	 */
	private JMenuItem about = new JMenuItem("About Game");

	/**
	 * Check for Updates.
	 */
	private JMenuItem checkUpdate = new JMenuItem("Check for Updates");

	/**
	 * Manages the game menu bar.
	 * 
	 * @author Todor Balabanov
	 */
	public FourRowSolitaire() {
		checkForUpdate();

		game.add(newGame);
		game.addSeparator();
		game.add(undo);
		game.add(hint);
		game.addSeparator();
		game.add(statistics);
		game.add(options);
		game.add(appearance);
		game.add(topTimes);
		game.addSeparator();
		game.add(exit);

		newGame.addActionListener(this);
		undo.addActionListener(this);
		hint.addActionListener(this);
		statistics.addActionListener(this);
		options.addActionListener(this);
		appearance.addActionListener(this);
		topTimes.addActionListener(this);
		exit.addActionListener(this);

		helpMenu.add(help);
		helpMenu.add(about);
		helpMenu.add(checkUpdate);

		help.addActionListener(this);
		about.addActionListener(this);
		checkUpdate.addActionListener(this);

		menuBar.add(game);
		menuBar.add(helpMenu);

		setJMenuBar(menuBar);

		newGame.setMnemonic('N');
		newGame.setAccelerator(KeyStroke.getKeyStroke("F2"));
		undo.setMnemonic('u');
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				KeyEvent.CTRL_MASK));
		hint.setMnemonic('h');
		hint.setAccelerator(KeyStroke.getKeyStroke('h'));
		statistics.setMnemonic('s');
		statistics.setAccelerator(KeyStroke.getKeyStroke("F4"));
		options.setMnemonic('o');
		options.setAccelerator(KeyStroke.getKeyStroke("F5"));
		appearance.setMnemonic('a');
		appearance.setAccelerator(KeyStroke.getKeyStroke("F7"));
		topTimes.setMnemonic('b');
		topTimes.setAccelerator(KeyStroke.getKeyStroke("F8"));
		exit.setMnemonic('x');

		help.setMnemonic('v');
		help.setAccelerator(KeyStroke.getKeyStroke("F1"));
		about.setMnemonic('a');

		loadData();
	}

	/**
	 * Checks for game updates. Notifies the user.
	 * 
	 * @author Todor Balabanov
	 */
	private void checkForUpdate() {
		try {
			URL url = new URL(
					"http://www.mastadisasta.com/FourRowSolitaire/version.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));

			String inputLine = in.readLine();
			in.close();

			/*
			 * Sometimes reads an html document if disconnected from internet.
			 */
			if (!inputLine.contains("DOCTYPE") && !version.equals(inputLine)) {
				JOptionPane.showMessageDialog(this,
						"There is a newer version available, "
								+ "click help > check for updates to get it.");
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * Used to load the game data like difficulty, sounds, animations,
	 * previously saved games etc.
	 * 
	 * @author Todor Balabanov
	 */
	private void loadData() {
		String fileLocation = System.getProperty("user.home")
				+ System.getProperty("file.separator");
		int count = 0, temp = 0, correctedStatistics = -2;
		int newDrawCount = 1, timerStatus = 0, deckNumber = 3, backgroundNumber = 2;
		int saved = 0, winAnimation = 0, winSounds = 0, drawCount = 1, deckThroughs = 1;
		int difficulty = 2, newDifficulty = 2;
		int numViewableCards = 1;

		try {
			File file = new File(fileLocation + "frs-statistics.dat");
			file.createNewFile();
			DataInputStream input = new DataInputStream(new FileInputStream(
					file));

			if (input.available() > 0) {
				correctedStatistics = input.readInt();
				count++;
			}

			if (correctedStatistics == -2) {
				/*
				 * No statistics file found.
				 */
			} else if (correctedStatistics == -1) {
				/*
				 * Statistics file is formatted to the new style of saving
				 * statistics.
				 */
				while ((input.available() > 0) && count < 43) {
					temp = input.readInt();
					switch (count) {
					case 31:
						drawCount = temp;
						break;
					case 32:
						newDrawCount = temp;
						break;
					case 33:
						deckNumber = temp;
						break;
					case 34:
						backgroundNumber = temp;
						break;
					case 35:
						timerStatus = temp;
						break;
					case 36:
						winAnimation = temp;
						break;
					case 37:
						winSounds = temp;
						break;
					case 38:
						deckThroughs = temp;
						break;
					case 39:
						difficulty = temp;
						break;
					case 40:
						newDifficulty = temp;
						break;
					case 41:
						numViewableCards = temp;
						break;
					case 42:
						saved = temp;
						break;

					default:
						break;
					}

					count++;
				}
			} else {
				/*
				 * Statistics file is formatted to the old style of saving
				 * statistics.
				 */
				while ((input.available() > 0) && count < 14) {
					temp = input.readInt();
					switch (count) {
					case 5:
						newDrawCount = temp;
						break;
					case 6:
						timerStatus = temp;
						break;
					case 7:
						deckNumber = temp;
						break;
					case 8:
						backgroundNumber = temp;
						break;
					case 9:
						saved = temp;
						break;
					case 10:
						winAnimation = temp;
						break;
					case 11:
						winSounds = temp;
						break;
					case 12:
						drawCount = temp;
						break;
					case 13:
						deckThroughs = temp;
						break;

					default:
						break;
					}

					count++;
				}
			}

			input.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		super.setDeckNumber(deckNumber);
		super.setBackgroundNumber(backgroundNumber);
		super.setTimerStatus(timerStatus);
		super.setNewDrawCount(newDrawCount);
		WinScreen.animation = winAnimation;
		WinScreen.sounds = winSounds;
		super.setDrawCount(drawCount);

		if (GameDifficulty.EASY.getValue() == difficulty) {
			super.setDifficulty(GameDifficulty.EASY);
		} else if (GameDifficulty.MEDIUM.getValue() == difficulty) {
			super.setDifficulty(GameDifficulty.MEDIUM);
		} else if (GameDifficulty.HARD.getValue() == difficulty) {
			super.setDifficulty(GameDifficulty.HARD);
		}

		if (GameDifficulty.EASY.getValue() == newDifficulty) {
			super.setNewDifficulty(GameDifficulty.EASY);
		} else if (GameDifficulty.MEDIUM.getValue() == newDifficulty) {
			super.setNewDifficulty(GameDifficulty.MEDIUM);
		} else if (GameDifficulty.HARD.getValue() == newDifficulty) {
			super.setNewDifficulty(GameDifficulty.HARD);
		}

		if (saved == 1) {
			super.setDeckThroughs(deckThroughs);
			LinkedList<Integer> cards = new LinkedList<Integer>();

			DataInputStream input = null;
			try {
				File file = new File(fileLocation + "frs-savedgame.dat");
				file.createNewFile();
				input = new DataInputStream(new FileInputStream(file));

				while (input.available() > 0) {
					cards.add(input.readInt());
				}

				if (cards.size() == 67) {
					super.setTimer(cards.pollLast());
					super.createBoard(cards, numViewableCards);
				} else {
					System.err
							.println("Problem Loading Saved Game (More or Less Than 52 Cards Stored)... Starting New Game");
					super.createBoard(null, 1);
				}
			} catch (Exception ex) {
				System.err
						.println("Problem Loading Saved Game (Unknown Error)... Starting New Game");
				super.createBoard(null, 1);
			} finally {
				try {
					input.close();
				} catch (Exception ex) {
				}
			}
		} else {
			super.createBoard(null, 1);
		}

		if (correctedStatistics != -1) {
			super.saveOptions();
		}
	}

	/**
	 * Main function. Executes the game.
	 * 
	 * @param args
	 * 
	 * @author Todor Balabanov
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		FourRowSolitaire fourRowSolitaire = new FourRowSolitaire();
	}

	/**
	 * Manages the game and the help menus.
	 * 
	 * @param e
	 * 
	 * @author Todor Balabanov
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newGame) {
			super.newGame(GameState.GAME_LOST);
		} else if (e.getSource() == undo) {
			super.undoMove();
		} else if (e.getSource() == hint) {
			super.getHint();
		} else if (e.getSource() == statistics) {
			String fileLocation = System.getProperty("user.home")
					+ System.getProperty("file.separator")
					+ "frs-statistics.dat";

			int count = 0, temp = 0;
			int gamesPlayed1e = 0, gamesWon1e = 0, winStreak1e = 0, lossStreak1e = 0, currentStreak1e = 0;
			int gamesPlayed1m = 0, gamesWon1m = 0, winStreak1m = 0, lossStreak1m = 0, currentStreak1m = 0;
			int gamesPlayed1h = 0, gamesWon1h = 0, winStreak1h = 0, lossStreak1h = 0, currentStreak1h = 0;
			int gamesPlayed3e = 0, gamesWon3e = 0, winStreak3e = 0, lossStreak3e = 0, currentStreak3e = 0;
			int gamesPlayed3m = 0, gamesWon3m = 0, winStreak3m = 0, lossStreak3m = 0, currentStreak3m = 0;
			int gamesPlayed3h = 0, gamesWon3h = 0, winStreak3h = 0, lossStreak3h = 0, currentStreak3h = 0;

			try {
				File file = new File(fileLocation);
				file.createNewFile();
				DataInputStream input = new DataInputStream(
						new FileInputStream(file));

				while ((input.available() > 0) && count < 31) {
					temp = input.readInt();
					switch (count) {
					case 1:
						gamesPlayed1e = temp;
						break;
					case 2:
						gamesWon1e = temp;
						break;
					case 3:
						winStreak1e = temp;
						break;
					case 4:
						lossStreak1e = temp;
						break;
					case 5:
						currentStreak1e = temp;
						break;

					case 6:
						gamesPlayed1m = temp;
						break;
					case 7:
						gamesWon1m = temp;
						break;
					case 8:
						winStreak1m = temp;
						break;
					case 9:
						lossStreak1m = temp;
						break;
					case 10:
						currentStreak1m = temp;
						break;

					case 11:
						gamesPlayed1h = temp;
						break;
					case 12:
						gamesWon1h = temp;
						break;
					case 13:
						winStreak1h = temp;
						break;
					case 14:
						lossStreak1h = temp;
						break;
					case 15:
						currentStreak1h = temp;
						break;

					case 16:
						gamesPlayed3e = temp;
						break;
					case 17:
						gamesWon3e = temp;
						break;
					case 18:
						winStreak3e = temp;
						break;
					case 19:
						lossStreak3e = temp;
						break;
					case 20:
						currentStreak3e = temp;
						break;

					case 21:
						gamesPlayed3m = temp;
						break;
					case 22:
						gamesWon3m = temp;
						break;
					case 23:
						winStreak3m = temp;
						break;
					case 24:
						lossStreak3m = temp;
						break;
					case 25:
						currentStreak3m = temp;
						break;

					case 26:
						gamesPlayed3h = temp;
						break;
					case 27:
						gamesWon3h = temp;
						break;
					case 28:
						winStreak3h = temp;
						break;
					case 29:
						lossStreak3h = temp;
						break;
					case 30:
						currentStreak3h = temp;
						break;

					default:
						break;
					}

					count++;
				}

				input.close();
			} catch (Exception ex) {
				System.out.println(ex);
			}

			int winPercentage1e;
			int winPercentage1m;
			int winPercentage1h;
			int winPercentage3e;
			int winPercentage3m;
			int winPercentage3h;

			if (gamesPlayed1e == 0) {
				winPercentage1e = 0;
			} else {
				winPercentage1e = 100 * gamesWon1e / gamesPlayed1e;
			}

			if (gamesPlayed1m == 0) {
				winPercentage1m = 0;
			} else {
				winPercentage1m = 100 * gamesWon1m / gamesPlayed1m;
			}

			if (gamesPlayed1h == 0) {
				winPercentage1h = 0;
			} else {
				winPercentage1h = 100 * gamesWon1h / gamesPlayed1h;
			}

			if (gamesPlayed3e == 0) {
				winPercentage3e = 0;
			} else {
				winPercentage3e = 100 * gamesWon3e / gamesPlayed3e;
			}

			if (gamesPlayed3m == 0) {
				winPercentage3m = 0;
			} else {
				winPercentage3m = 100 * gamesWon3m / gamesPlayed3m;
			}

			if (gamesPlayed3h == 0) {
				winPercentage3h = 0;
			} else {
				winPercentage3h = 100 * gamesWon3h / gamesPlayed3h;
			}

			JTextArea display = new JTextArea();
			display.append("One-Card Draw (Easy)\t\tThree-Card Draw (Easy)\n"
					+ "Games Played: "
					+ gamesPlayed1e
					+ "\t\tGames Played: "
					+ gamesPlayed3e
					+ "\nGames Won: "
					+ gamesWon1e
					+ "\t\t\tGames Won: "
					+ gamesWon3e
					+ "\n"
					+ "Win Percentage: "
					+ winPercentage1e
					+ "%\t\tWin Percentage: "
					+ winPercentage3e
					+ "%\n\nBest Streak: "
					+ winStreak1e
					+ "\t\t\tBest Streak: "
					+ winStreak3e
					+ "\n"
					+ "Worst Streak: "
					+ lossStreak1e
					+ "\t\tWorst Streak: "
					+ lossStreak3e
					+ "\n"
					+ "Current Streak: "
					+ currentStreak1e
					+ "\t\tCurrent Streak: "
					+ currentStreak3e
					+ "\n"
					+ "===================================================================\n");

			display.append("One-Card Draw (Medium)\t\tThree-Card Draw (Medium)\n"
					+ "Games Played: "
					+ gamesPlayed1m
					+ "\t\tGames Played: "
					+ gamesPlayed3m
					+ "\nGames Won: "
					+ gamesWon1m
					+ "\t\t\tGames Won: "
					+ gamesWon3m
					+ "\n"
					+ "Win Percentage: "
					+ winPercentage1m
					+ "%\t\tWin Percentage: "
					+ winPercentage3m
					+ "%\n\nBest Streak: "
					+ winStreak1m
					+ "\t\t\tBest Streak: "
					+ winStreak3m
					+ "\n"
					+ "Worst Streak: "
					+ lossStreak1m
					+ "\t\tWorst Streak: "
					+ lossStreak3m
					+ "\n"
					+ "Current Streak: "
					+ currentStreak1m
					+ "\t\tCurrent Streak: "
					+ currentStreak3m
					+ "\n"
					+ "===================================================================\n");

			display.append("One-Card Draw (Hard)\t\tThree-Card Draw (Hard)\n"
					+ "Games Played: "
					+ gamesPlayed1h
					+ "\t\tGames Played: "
					+ gamesPlayed3h
					+ "\nGames Won: "
					+ gamesWon1h
					+ "\t\t\tGames Won: "
					+ gamesWon3h
					+ "\n"
					+ "Win Percentage: "
					+ winPercentage1h
					+ "%\t\tWin Percentage: "
					+ winPercentage3h
					+ "%\n\nBest Streak: "
					+ winStreak1h
					+ "\t\t\tBest Streak: "
					+ winStreak3h
					+ "\n"
					+ "Worst Streak: "
					+ lossStreak1h
					+ "\t\tWorst Streak: "
					+ lossStreak3h
					+ "\n"
					+ "Current Streak: "
					+ currentStreak1h
					+ "\t\tCurrent Streak: "
					+ currentStreak3h);

			display.setOpaque(false);
			display.setBorder(null);
			display.setFont(UIManager.getFont("Label.font"));

			Object[] buttons = { "Close", "Reset" };
			int check = JOptionPane.showOptionDialog(this, display,
					"Statistics", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);

			if (check == 1) {
				/*
				 * Reset stats.
				 */
				super.resetStats();
			} else {
				/*
				 * Close.
				 */
				return;
			}
		} else if (e.getSource() == options) {
			ChangeOptions co = new ChangeOptions(this, super.getNewDrawCount(),
					super.getTimerNextGameStatus(), WinScreen.animation,
					WinScreen.sounds, super.getNewDifficulty());
			int drawCount = co.getDrawCount();
			int timerStatus = co.getTimer();
			int animationStatus = co.getAnimation();
			int soundsStatus = co.getSounds();
			int difficulty = co.getDifficulty();

			if (drawCount != -1) {
				super.setNewDrawCount(drawCount);
				super.setTimerStatus(timerStatus);
				WinScreen.animation = animationStatus;
				WinScreen.sounds = soundsStatus;
				if (GameDifficulty.EASY.getValue() == difficulty) {
					super.setDifficulty(GameDifficulty.EASY);
				} else if (GameDifficulty.MEDIUM.getValue() == difficulty) {
					super.setDifficulty(GameDifficulty.MEDIUM);
				} else if (GameDifficulty.HARD.getValue() == difficulty) {
					super.setDifficulty(GameDifficulty.HARD);
				}
			}

			super.saveOptions();
		} else if (e.getSource() == appearance) {
			ChangeAppearance ca = new ChangeAppearance(this,
					super.getDeckNumber(), super.getBackgroundNumber());
			int deckNumber = ca.getDeckNumber();
			int backgroundNumber = ca.getBackgroundNumber();
			ca.dispose();

			if (deckNumber != -1) {
				super.setAppearance(deckNumber, backgroundNumber);
			}

			super.saveOptions();
		} else if (e.getSource() == topTimes) {
			TopTimes top = new TopTimes();
			top.setVisible(true);
		} else if (e.getSource() == exit) {
			super.wl.windowClosing(null);
		}

		else if (e.getSource() == help) {
			JOptionPane
					.showMessageDialog(
							this,
							"This game is a mixture of FreeCell and normal (Klondike) Solitaire.\n"
									+ "\nSimilarities to FreeCell:\n"
									+ "   -The Cards in the columns are always visible\n"
									+ "   -There are four individual cells at the top\n"
									+ "\nSimilarities to Solitaire:\n"
									+ "   -There is a deck and discard pile to draw cards from\n"
									+ "   -Only Kings may be placed at the top of an empty column\n"
									+ "   -Cards may be removed from the Ace piles and placed back onto the playing field\n"
									+ "   -Any number of cards can be moved in one move (as long as they are stacked as in Solitaire\n"
									+ "\nFeatures unique to this game:\n"
									+ "   -The four individual cells start with cards in them\n"
									+ "   -You may only go through the deck twice on draw one and three times on draw three\n"
									+ "   -The obvious: there are only four columns, not 7 or 8 as in Solitaire and FreeCell, respectively",
							"Help!", JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource() == about) {
			JOptionPane
					.showMessageDialog(
							this,
							"Four Row Solitaire was created by Matt Stephen and\n"
									+ "programmed by Matt Stephen, Todor Balabanov, Konstantin Tsanov and Ventsislav Medarov.\n"
									+ "\nYou can modify this code in accordance with GPL v3.0.\n"
									+ "\nTo check if there is a newer version, go to:\n"
									+ "github.com/TodorBalabanov/FourRowSolitaire",
							"About Game", JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource() == checkUpdate) {
			try {
				Desktop.getDesktop()
						.browse(new URI(
								"http://github.com/TodorBalabanov/FourRowSolitaire/"));
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}
}