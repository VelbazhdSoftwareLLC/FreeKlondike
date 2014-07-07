/*
 This file is a part of Four Row Solitaire

 Copyright (C) 2010-2014 by Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov

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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;

/**
 * Class: SolitaireBoard
 * 
 * Description: The SolitaireBoard class manages the entire playing field.
 * 
 * @author Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov
 */
public class SolitaireBoard extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final int INITIAL_CARDS_NUMBER_IN_COLUMN = 5;

	/**
	 * Find better OOP modeling alternative! Use enumerated type for card back.
	 */
	static int deckNumber = 3;

	/**
	 * Can be 1 or 3. Should be only here!
	 */
	static int drawCount = 1;

	/**
	 * Find better OOP modeling alternative! Use enumerated type for card back.
	 */
	static int backgroundNumber = 2;

	/**
	 * To store new option selection for next new game, otherwise the count
	 * would be changed at next click of the deck (in the middle of the game).
	 */
	private int newDrawCount = drawCount;

	/**
	 * The four columns for the main playing field.
	 */
	private Column[] columns = new Column[4];

	/**
	 * The discard pile.
	 */
	private DiscardPile discardPile = new DiscardPile();

	/**
	 * The deal pile.
	 */
	private DealDeck dealDeck = new DealDeck(discardPile);

	/**
	 * The four ace piles (to stack Ace - King of a single suit).
	 */
	private AcePile[] acePiles = new AcePile[4];

	/**
	 * The four top individual cells.
	 */
	private SingleCell[] cells = new SingleCell[4];

	/**
	 * 
	 */
	private SolitairePanel mainPanel;

	/**
	 * 
	 */
	private MyMouseListener ml = new MyMouseListener();

	/**
	 * 
	 */
	public MyWindowListener wl = new MyWindowListener();

	/**
	 * Timer.
	 */
	private Timer timer = new Timer(1000, new TimerListener());

	/**
	 * Status bar.
	 */
	private JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.CENTER));

	/**
	 * Timer label.
	 */
	private JLabel timerLabel = new JLabel("Time: OFF");

	/**
	 * Timer count.
	 */
	private int timerCount = 0;

	/**
	 * Timer to next game.
	 */
	private int timerToRunNextGame = 0;

	/**
	 * Timer to run.
	 */
	private boolean timerToRun = false;

	/**
	 * 1 = easy, 2 = medium, 3 = hard Should be only here!
	 */
	private GameDifficulty difficulty = GameDifficulty.MEDIUM;

	/**
	 * Game difficulty.
	 */
	private GameDifficulty newDifficulty = difficulty;

	/**
	 * 
	 */
	private LinkedList<CardStack> sourceList = new LinkedList<CardStack>();

	/**
	 * 
	 */
	private LinkedList<CardStack> destinationList = new LinkedList<CardStack>();

	/**
	 * 
	 */
	private LinkedList<Integer> numCards = new LinkedList<Integer>();

	/**
	 * 
	 */
	private LinkedList<Integer> numCardsInDiscardView = new LinkedList<Integer>();

	/**
	 * Sets the board's window name, size, location, close button option, makes
	 * it unresizable and puts the logo on it.
	 */
	public SolitaireBoard() {
		setTitle("Four Row Solitaire");
		setSize(806, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setIconImage(new ImageIcon(getClass().getResource("images/logo.png"))
				.getImage());

		setVisible(true);

		addWindowListener(wl);
	}

	/**
	 * Creates the solitaire board.
	 * 
	 * @param cards
	 * 
	 * @param numViewableCards
	 */
	public void createBoard(LinkedList<Integer> cards, int numViewableCards) {
		mainPanel = new SolitairePanel();
		mainPanel.setLayout(new SolitaireLayout());

		mainPanel.changeBackground(backgroundNumber);

		for (int i = 0; i < columns.length; i++) {
			columns[i] = new Column();
			columns[i].addMouseListener(ml);
		}

		mainPanel.add(columns[0], SolitaireLayout.COLUMN_ONE);
		mainPanel.add(columns[1], SolitaireLayout.COLUMN_TWO);
		mainPanel.add(columns[2], SolitaireLayout.COLUMN_THREE);
		mainPanel.add(columns[3], SolitaireLayout.COLUMN_FOUR);

		for (int i = 0; i < cells.length; i++) {
			cells[i] = new SingleCell();
			cells[i].addMouseListener(ml);
		}

		mainPanel.add(cells[0], SolitaireLayout.CELL_ONE);
		mainPanel.add(cells[1], SolitaireLayout.CELL_TWO);
		mainPanel.add(cells[2], SolitaireLayout.CELL_THREE);
		mainPanel.add(cells[3], SolitaireLayout.CELL_FOUR);

		dealDeck.addMouseListener(ml);
		discardPile.addMouseListener(ml);

		mainPanel.add(dealDeck, SolitaireLayout.DECK);
		mainPanel.add(discardPile, SolitaireLayout.DISCARD_PILE);

		for (int i = 0; i < acePiles.length; i++) {
			switch (i) {
			case 0:
				acePiles[i] = new AcePile(CardSuit.SPADES);
				break;
			case 1:
				acePiles[i] = new AcePile(CardSuit.CLUBS);
				break;
			case 2:
				acePiles[i] = new AcePile(CardSuit.DIAMONDS);
				break;
			case 3:
				acePiles[i] = new AcePile(CardSuit.HEARTS);
				break;

			default:
				break;
			}

			acePiles[i].addMouseListener(ml);
		}

		mainPanel.add(acePiles[0], SolitaireLayout.SPADES_ACE_PILE);
		mainPanel.add(acePiles[1], SolitaireLayout.CLUBS_ACE_PILE);
		mainPanel.add(acePiles[2], SolitaireLayout.DIAMONDS_ACE_PILE);
		mainPanel.add(acePiles[3], SolitaireLayout.HEARTS_ACE_PILE);

		statusBar.add(timerLabel);

		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(mainPanel, BorderLayout.CENTER);
		p1.add(statusBar, BorderLayout.SOUTH);
		add(p1);

		if (cards != null) {
			dealOutCustomBoard(cards, numViewableCards);
		} else {
			dealOutBoard();
		}
	}

	/**
	 * Deals the cards.
	 */
	private void dealOutBoard() {
		LinkedList<Card> cards = (LinkedList<Card>) Deck.getFullShuffledDeck();

		/*
		 * Fill five cards by column.
		 */
		for (int i = 0; i < INITIAL_CARDS_NUMBER_IN_COLUMN; i++) {
			for (int j = 0; j < columns.length && j < cells.length; j++) {
				Card card = cards.getLast();
				cards.removeLast();
				columns[j].addCard(card);
			}
		}

		/*
		 * Fill cards in buffer area.
		 */
		for (int j = 0; j < columns.length && j < cells.length; j++) {
			Card card = cards.getLast();
			cards.removeLast();
			cells[j].addCard(card);
		}

		dealDeck.setDrawCount(newDrawCount);
		dealDeck.setDifficulty(newDifficulty);

		if (newDrawCount != drawCount) {
			drawCount = newDrawCount;
		}

		if (newDifficulty != difficulty) {
			difficulty = newDifficulty;
		}

		dealDeck.setDeck(cards);

		timerCount = 0;

		if (timerToRunNextGame == 1) {
			timer.stop();
			timerLabel.setText("Timer: 0");
			timerToRun = true;
		} else {
			timer.stop();
			timerLabel.setText("Time: OFF");

			timerToRun = false;
		}

		mainPanel.revalidate();
	}

	/**
	 * Used to deal the cards on the board after opening a saved game.
	 * 
	 * @param numbers
	 * 
	 * @param numViewableCards
	 */
	private void dealOutCustomBoard(LinkedList<Integer> numbers,
			int numViewableCards) {
		LinkedList<Card> cards = (LinkedList<Card>) Deck
				.getDeckSubsetByCardNumbers(numbers);

		int pileNumber = 0;
		int cardNumber = -1;

		dealDeck.setDrawCount(drawCount);
		dealDeck.setDifficulty(difficulty);

		for (int i = 0; i < numbers.size(); i++) {
			if (numbers.get(i) > 0) {
				cardNumber++;
			} else {
				pileNumber++;
				continue;
			}

			if (0 <= pileNumber && pileNumber <= 3) {
				cells[pileNumber % 4].addCard(cards.get(cardNumber));
			} else if (4 <= pileNumber && pileNumber <= 7) {
				columns[pileNumber % 4].addCard(cards.get(cardNumber));
			} else if (8 <= pileNumber && pileNumber <= 11) {
				acePiles[pileNumber % 4].addCard(cards.get(cardNumber));
			} else if (pileNumber == 12) {
				Card card = cards.get(cardNumber);
				card.setFaceDown();
				dealDeck.addCard(card);
			} else if (pileNumber == 13) {
				discardPile.push(cards.get(cardNumber));
			}
		}

		discardPile.setView(numViewableCards);

		if (timerToRunNextGame == 1) {
			timer.stop();
			timerLabel.setText("Time: " + (timerCount == -1 ? 0 : timerCount));

			timerToRun = true;
		} else {
			timer.stop();
			timerLabel.setText("Time: OFF");

			timerToRun = false;
		}

		mainPanel.revalidate();
	}

	/**
	 * Clears the board.
	 */
	private void clearBoard() {
		for (int i = 0; i < columns.length; i++) {
			while (columns[i].isEmpty() == false) {
				columns[i].pop();
			}

			columns[i].repaint();
		}

		for (int i = 0; i < cells.length; i++) {
			while (cells[i].isEmpty() == false) {
				cells[i].pop();
			}

			cells[i].repaint();
		}

		for (int i = 0; i < acePiles.length; i++) {
			while (acePiles[i].isEmpty() == false) {
				acePiles[i].pop();
			}

			acePiles[i].repaint();
		}

		while (dealDeck.isEmpty() == false) {
			dealDeck.pop();
		}

		dealDeck.repaint();

		while (discardPile.isEmpty() == false) {
			discardPile.pop();
		}

		discardPile.repaint();
	}

	/**
	 * For starting a new game.
	 * 
	 * @param winOrLoss
	 */
	public void newGame(GameState winOrLoss) {
		/*
		 * If the game was won, the win was already reported.
		 */
		if (winOrLoss != GameState.GAME_WON
				&& winOrLoss != GameState.DO_NOTHING) {
			int check = JOptionPane.showConfirmDialog(this,
					"Quitting the current game will result in a loss.\n"
							+ "Do you wish to continue?", "Continue?",
					JOptionPane.PLAIN_MESSAGE);

			if (check == JOptionPane.YES_OPTION) {
				recordGame(GameState.GAME_LOST);
			} else {
				/*
				 * If player wants to continue game.
				 */
				return;
			}
		}

		/*
		 * Remove cards from ace piles. Set numTimesThroughDeck back to 1.
		 */
		clearBoard();
		dealDeck.reset();
		dealOutBoard();

		sourceList.clear();
		destinationList.clear();
		numCards.clear();
		numCardsInDiscardView.clear();
	}

	/**
	 * Used to reset the stats.
	 */
	public void resetStats() {
		recordGame(GameState.RESET_STATS);
	}

	/**
	 * Save options.
	 */
	public void saveOptions() {
		recordGame(GameState.DO_NOTHING);
	}

	/**
	 * Manages the game states.
	 * 
	 * @param winOrLoss
	 */
	private void recordGame(GameState winOrLoss) {
		int count = 0, temp = 0;
		int gamesPlayed1e = 0, gamesWon1e = 0, winStreak1e = 0, lossStreak1e = 0, currentStreak1e = 0;
		int gamesPlayed1m = 0, gamesWon1m = 0, winStreak1m = 0, lossStreak1m = 0, currentStreak1m = 0;
		int gamesPlayed1h = 0, gamesWon1h = 0, winStreak1h = 0, lossStreak1h = 0, currentStreak1h = 0;
		int gamesPlayed3e = 0, gamesWon3e = 0, winStreak3e = 0, lossStreak3e = 0, currentStreak3e = 0;
		int gamesPlayed3m = 0, gamesWon3m = 0, winStreak3m = 0, lossStreak3m = 0, currentStreak3m = 0;
		int gamesPlayed3h = 0, gamesWon3h = 0, winStreak3h = 0, lossStreak3h = 0, currentStreak3h = 0;

		String fileLocation = System.getProperty("user.home")
				+ System.getProperty("file.separator");
		File file = new File(fileLocation + "frs-statistics.dat");

		try {
			file.createNewFile();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		try {
			DataInputStream input = new DataInputStream(new FileInputStream(
					file));

			if (input.available() > 0) {
				temp = input.readInt();
				count++;
			}

			if (temp != -1) {
				gamesPlayed1m = temp;

				while ((input.available() > 0) && count < 5) {
					temp = input.readInt();
					switch (count) {
					/*
					 * Case 0 is the format checker.
					 */
					case 1:
						gamesWon1m = temp;
						break;
					case 2:
						winStreak1m = temp;
						break;
					case 3:
						lossStreak1m = temp;
						break;
					case 4:
						currentStreak1m = temp;
						break;

					default:
						break;
					}

					count++;
				}
			} else {
				while ((input.available() > 0) && count < 31) {
					temp = input.readInt();
					switch (count) {
					/*
					 * Case 0 is the format checker.
					 */
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
			}

			input.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		try {
			DataOutputStream output = new DataOutputStream(
					new FileOutputStream(file));

			if (winOrLoss == GameState.GAME_WON) {
				if (drawCount == 3) {
					if (difficulty == GameDifficulty.EASY) {
						gamesPlayed3e++;
						gamesWon3e++;

						if (currentStreak3e >= 0) {
							currentStreak3e++;
						} else {
							currentStreak3e = 1;
						}

						if (winStreak3e < currentStreak3e) {
							winStreak3e = currentStreak3e;
						}
					} else if (difficulty == GameDifficulty.MEDIUM) {
						gamesPlayed3m++;
						gamesWon3m++;

						if (currentStreak3m >= 0) {
							currentStreak3m++;
						} else {
							currentStreak3m = 1;
						}

						if (winStreak3m < currentStreak3m) {
							winStreak3m = currentStreak3m;
						}
					} else {
						gamesPlayed3h++;
						gamesWon3h++;

						if (currentStreak3h >= 0) {
							currentStreak3h++;
						} else {
							currentStreak3h = 1;
						}

						if (winStreak3h < currentStreak3h) {
							winStreak3h = currentStreak3h;
						}
					}
				} else if (drawCount == 1) {
					if (difficulty == GameDifficulty.EASY) {
						gamesPlayed1e++;
						gamesWon1e++;

						if (currentStreak1e >= 0) {
							currentStreak1e++;
						} else {
							currentStreak1e = 1;
						}

						if (winStreak1e < currentStreak1e) {
							winStreak1e = currentStreak1e;
						}
					} else if (difficulty == GameDifficulty.MEDIUM) {
						gamesPlayed1m++;
						gamesWon1m++;

						if (currentStreak1m >= 0) {
							currentStreak1m++;
						} else {
							currentStreak1m = 1;
						}

						if (winStreak1m < currentStreak1m) {
							winStreak1m = currentStreak1m;
						}
					} else {
						gamesPlayed1h++;
						gamesWon1h++;

						if (currentStreak1h >= 0) {
							currentStreak1h++;
						} else {
							currentStreak1h = 1;
						}

						if (winStreak1h < currentStreak1h) {
							winStreak1h = currentStreak1h;
						}
					}
				}
			} else if (winOrLoss == GameState.RESET_STATS) {
				gamesWon1e = 0;
				gamesPlayed1e = 0;
				currentStreak1e = 0;
				winStreak1e = 0;
				lossStreak1e = 0;

				gamesWon1m = 0;
				gamesPlayed1m = 0;
				currentStreak1m = 0;
				winStreak1m = 0;
				lossStreak1m = 0;

				gamesWon1h = 0;
				gamesPlayed1h = 0;
				currentStreak1h = 0;
				winStreak1h = 0;
				lossStreak1h = 0;

				gamesWon3e = 0;
				gamesPlayed3e = 0;
				currentStreak3e = 0;
				winStreak3e = 0;
				lossStreak3e = 0;

				gamesWon3m = 0;
				gamesPlayed3m = 0;
				currentStreak3m = 0;
				winStreak3m = 0;
				lossStreak3m = 0;

				gamesWon3h = 0;
				gamesPlayed3h = 0;
				currentStreak3h = 0;
				winStreak3h = 0;
				lossStreak3h = 0;
			} else if (winOrLoss == GameState.DO_NOTHING
					|| winOrLoss == GameState.GAME_SAVED) {
				/*
				 * Just updating options.
				 */
			} else {
				if (drawCount == 3) {
					if (difficulty == GameDifficulty.EASY) {
						gamesPlayed3e++;

						if (currentStreak3e <= 0) {
							currentStreak3e--;
						} else {
							currentStreak3e = -1;
						}

						if (lossStreak3e > currentStreak3e) {
							lossStreak3e = currentStreak3e;
						}
					} else if (difficulty == GameDifficulty.MEDIUM) {
						gamesPlayed3m++;

						if (currentStreak3m <= 0) {
							currentStreak3m--;
						} else {
							currentStreak3m = -1;
						}

						if (lossStreak3m > currentStreak3m) {
							lossStreak3m = currentStreak3m;
						}
					} else {
						gamesPlayed3h++;

						if (currentStreak3h <= 0) {
							currentStreak3h--;
						} else {
							currentStreak3h = -1;
						}

						if (lossStreak3h > currentStreak3h) {
							lossStreak3h = currentStreak3h;
						}
					}
				} else if (drawCount == 1) {
					if (difficulty == GameDifficulty.EASY) {
						gamesPlayed1e++;

						if (currentStreak1e <= 0) {
							currentStreak1e--;
						} else {
							currentStreak1e = -1;
						}

						if (lossStreak1e > currentStreak1e) {
							lossStreak1e = currentStreak1e;
						}
					} else if (difficulty == GameDifficulty.MEDIUM) {
						gamesPlayed1m++;

						if (currentStreak1m <= 0) {
							currentStreak1m--;
						} else {
							currentStreak1m = -1;
						}

						if (lossStreak1m > currentStreak1m) {
							lossStreak1m = currentStreak1m;
						}
					} else {
						gamesPlayed1h++;

						if (currentStreak1h <= 0) {
							currentStreak1h--;
						} else {
							currentStreak1h = -1;
						}

						if (lossStreak1h > currentStreak1h) {
							lossStreak1h = currentStreak1h;
						}
					}
				}
			}

			/*
			 * New format indicator.
			 */
			output.writeInt(-1);

			output.writeInt(gamesPlayed1e);
			output.writeInt(gamesWon1e);
			output.writeInt(winStreak1e);
			output.writeInt(lossStreak1e);
			output.writeInt(currentStreak1e);

			output.writeInt(gamesPlayed1m);
			output.writeInt(gamesWon1m);
			output.writeInt(winStreak1m);
			output.writeInt(lossStreak1m);
			output.writeInt(currentStreak1m);

			output.writeInt(gamesPlayed1h);
			output.writeInt(gamesWon1h);
			output.writeInt(winStreak1h);
			output.writeInt(lossStreak1h);
			output.writeInt(currentStreak1h);

			output.writeInt(gamesPlayed3e);
			output.writeInt(gamesWon3e);
			output.writeInt(winStreak3e);
			output.writeInt(lossStreak3e);
			output.writeInt(currentStreak3e);

			output.writeInt(gamesPlayed3m);
			output.writeInt(gamesWon3m);
			output.writeInt(winStreak3m);
			output.writeInt(lossStreak3m);
			output.writeInt(currentStreak3m);

			output.writeInt(gamesPlayed3h);
			output.writeInt(gamesWon3h);
			output.writeInt(winStreak3h);
			output.writeInt(lossStreak3h);
			output.writeInt(currentStreak3h);

			output.writeInt(drawCount);
			output.writeInt(newDrawCount);
			output.writeInt(deckNumber);
			output.writeInt(backgroundNumber);
			output.writeInt(timerToRunNextGame);

			/*
			 * Finish saving options.
			 */
			output.writeInt(WinScreen.animation);
			output.writeInt(WinScreen.sounds);
			output.writeInt(dealDeck.getDeckThroughs());
			output.writeInt(difficulty.getValue());
			output.writeInt(newDifficulty.getValue());
			output.writeInt(discardPile.getNumViewableCards());

			File savedFile = new File(fileLocation + "frs-savedgame.dat");
			DataOutputStream saved = new DataOutputStream(new FileOutputStream(
					savedFile));

			if (winOrLoss == GameState.GAME_SAVED) {
				/*
				 * Saved.
				 */
				output.writeInt(1);

				for (int i = 0; i < cells.length; i++) {
					if (!cells[i].isEmpty()) {
						saved.writeInt(cells[i].peek().getFullNumber());
						saved.writeInt(-1);
					} else {
						saved.writeInt(-1);
					}
				}

				for (int i = 0; i < columns.length; i++) {
					if (!columns[i].isEmpty()) {
						for (int j = 0; j < columns[i].length(); j++) {
							saved.writeInt(columns[i].getCardAtLocation(j)
									.getFullNumber());
						}

						saved.writeInt(-1);
					} else {
						saved.writeInt(-1);
					}
				}

				for (int i = 0; i < acePiles.length; i++) {
					if (!acePiles[i].isEmpty()) {
						for (int j = 0; j < acePiles[i].length(); j++) {
							saved.writeInt(acePiles[i].getCardAtLocation(j)
									.getFullNumber());
						}

						saved.writeInt(-1);
					} else {
						saved.writeInt(-1);
					}
				}

				if (!dealDeck.isEmpty()) {
					for (int j = 0; j < dealDeck.length(); j++) {
						saved.writeInt(dealDeck.getCardAtLocation(j)
								.getFullNumber());
					}

					saved.writeInt(-1);
				} else {
					saved.writeInt(-1);
				}

				if (!discardPile.isEmpty()) {
					for (int j = 0; j < discardPile.length(); j++) {
						saved.writeInt(discardPile.getCardAtLocation(j)
								.getFullNumber());
					}

					saved.writeInt(-1);
				} else {
					saved.writeInt(-1);
				}

				if (timerToRun == true)
					saved.writeInt(timerCount);
				else
					saved.writeInt(-1);
			} else {
				/*
				 * Not saved.
				 */
				output.writeInt(0);
				savedFile.delete();
			}

			output.close();
			saved.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Manages the appaearance.
	 * 
	 * @param deck
	 * 
	 * @param background
	 */
	public void setAppearance(int deck, int background) {
		deckNumber = deck;
		backgroundNumber = background;

		mainPanel.changeBackground(backgroundNumber);
	}

	/**
	 * Returns the draw count.
	 * 
	 * @return
	 */
	public int getDrawCount() {
		return drawCount;
	}

	/**
	 * Sets draw count.
	 * 
	 * @param draw
	 */
	public void setDrawCount(int draw) {
		drawCount = draw;

		if (drawCount != 3 && drawCount != 1) {
			drawCount = 1;
		}
	}

	/**
	 * Returns the new draw count.
	 * 
	 * @return
	 */
	public int getNewDrawCount() {
		return newDrawCount;
	}

	/**
	 * Sets the new draw count.
	 * 
	 * @param draw
	 */
	public void setNewDrawCount(int draw) {
		newDrawCount = draw;

		if (newDrawCount != 3 && newDrawCount != 1) {
			newDrawCount = 1;
		}
	}

	/**
	 * Returns the deck number.
	 * 
	 * @return
	 */
	public int getDeckNumber() {
		return deckNumber;
	}

	/**
	 * Sets the deck number.
	 * 
	 * @param deckNum
	 */
	public void setDeckNumber(int deckNum) {
		deckNumber = deckNum;

		if (deckNumber > ChangeAppearance.NUM_DECKS || deckNumber <= 0) {
			deckNumber = ChangeAppearance.FRS_DECK;
		}
	}

	/**
	 * Returns the background number.
	 * 
	 * @return
	 */
	public int getBackgroundNumber() {
		return backgroundNumber;
	}

	/**
	 * Sets the background number.
	 * 
	 * @param backNum
	 */
	public void setBackgroundNumber(int backNum) {
		backgroundNumber = backNum;

		if (backgroundNumber > ChangeAppearance.NUM_BACKGROUNDS
				|| backgroundNumber <= 0) {
			backgroundNumber = ChangeAppearance.FRS_BACKGROUND;
		}
	}

	/**
	 * Returns next game timer status.
	 * 
	 * @return
	 */
	public int getTimerNextGameStatus() {
		return timerToRunNextGame;
	}

	/**
	 * Returns the timer status.
	 * 
	 * @return
	 */
	public int getTimerStatus() {
		if (timer.isRunning()) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Sets timer status.
	 * 
	 * @param timerInt
	 */
	public void setTimerStatus(int timerInt) {
		if (timerInt == 1) {
			timerToRunNextGame = 1;
		} else if (timerInt == 0) {
			timerToRunNextGame = 0;
		}

		if (timerInt != 0 && timerInt != 1) {
			timerToRunNextGame = 0;
		}
	}

	/**
	 * Sets the timer counter.
	 * 
	 * @param time
	 */
	public void setTimer(int time) {
		timerCount = time;
	}

	/**
	 * Returns game difficulty.
	 * 
	 * @return
	 */
	public GameDifficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * Sets game difficulty.
	 * 
	 * @param difficulty
	 */
	public void setDifficulty(GameDifficulty difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * Returns the new difficulty.
	 * 
	 * @return
	 */
	public GameDifficulty getNewDifficulty() {
		return newDifficulty;
	}

	/**
	 * Sets the new difficulty.
	 * 
	 * @param newDifficulty
	 */
	public void setNewDifficulty(GameDifficulty newDifficulty) {
		this.newDifficulty = newDifficulty;
	}

	/**
	 * Sets the number of times through deck.
	 * 
	 * @param deckThroughs
	 */
	public void setDeckThroughs(int deckThroughs) {
		dealDeck.setDeckThroughs(deckThroughs);
	}

	/**
	 * Used to undo a move.
	 */
	public synchronized void undoMove() {
		if (sourceList.isEmpty()) {
			return;
		}
		/*
		 * If player is holding on to a card.
		 */
		if (sourceList.size() > destinationList.size()) {
			CardStack tempSource = sourceList.getLast();
			sourceList.removeLast();

			int num = numCards.getLast();
			numCards.removeLast();

			int numDiscard = numCardsInDiscardView.getLast();
			numCardsInDiscardView.removeLast();

			if (num == 1) {
				discardPile.setView(numDiscard);
				tempSource.peek().unhighlight();

				ml.clickedCard = null;
				ml.hasSelected = false;
				ml.singleCardSelected = false;
				ml.temp = null;
			} else {
				for (int i = 0; i < num; i++) {
					tempSource.getCardAtLocation(tempSource.length() - i - 1)
							.unhighlight();
				}

				ml.clickedCard = null;
				ml.hasSelected = false;
				ml.temp = null;
			}

			tempSource.repaint();
		} else if (!(sourceList.getLast() instanceof DealDeck)) {
			CardStack tempSource = sourceList.getLast();
			CardStack tempDest = destinationList.getLast();
			int num = numCards.getLast();
			int numDiscard = numCardsInDiscardView.getLast();

			sourceList.removeLast();
			destinationList.removeLast();
			numCards.removeLast();
			numCardsInDiscardView.removeLast();

			if (num == 1) {
				tempSource.addCard(tempDest.pop());
			} else {
				CardStack temp = tempDest.undoStack(num);
				tempSource.addStack(temp);
			}

			discardPile.setView(numDiscard);
			tempSource.repaint();
			tempDest.repaint();
		}
		/*
		 * The last draw from the deck didn't reset the discard pile to make it
		 * an empty pile.
		 */
		else if (sourceList.getLast() instanceof DealDeck
				&& !destinationList.getLast().isEmpty()) {
			int num = numCards.getLast();
			int numDiscard = numCardsInDiscardView.getLast();

			sourceList.removeLast();
			destinationList.removeLast();
			numCards.removeLast();
			numCardsInDiscardView.removeLast();

			for (int i = 0; i < num; i++) {
				Card card = discardPile.undoPop();
				card.setFaceDown();
				dealDeck.addCard(card);
			}

			discardPile.setView(numDiscard);
			dealDeck.repaint();
			discardPile.repaint();
		}
		/*
		 * Last move was a reset on the discard pile.
		 */
		else if (sourceList.getLast() instanceof DealDeck) {
			dealDeck.undoPop();

			int numDiscard = numCardsInDiscardView.getLast();
			discardPile.setView(numDiscard);

			discardPile.repaint();
			discardPile.revalidate();
			dealDeck.repaint();

			sourceList.removeLast();
			destinationList.removeLast();
			numCards.removeLast();
			numCardsInDiscardView.removeLast();
		}
	}

	/**
	 * Manages the hints.
	 */
	@SuppressWarnings("fallthrough")
	public void getHint() {
		CardStack source = new CardStack();
		CardStack destination = new CardStack();
		CardStack temp = new CardStack();

		LinkedList<String> hints = new LinkedList<String>();
		String sourceString = "";

		for (int i = 0; i < 9; i++) {
			switch (i) {
			case 0:
			case 1:
			case 2:
			case 3: {
				source = columns[i];
				sourceString = "Column " + (i + 1);
			}
				break;
			case 4:
			case 5:
			case 6:
			case 7: {
				source = cells[i - 4];
				sourceString = "Cell " + (i - 3);
			}
				break;
			case 8: {
				source = discardPile;
				sourceString = "the Discard Pile";
			}
				break;

			default:
				break;
			}

			if (source != null && !source.isEmpty()) {
				temp = source.getAvailableCards();
				String destinationString = "";

				for (int j = 0; j < 8; j++) {
					switch (j) {
					case 0:
					case 1:
					case 2:
					case 3: {
						destination = columns[j];
						destinationString = "Column " + (j + 1);
					}
						break;
					case 4:
					case 5:
					case 6:
					case 7: {
						destination = acePiles[j - 4];
						destinationString = "its Ace Pile";
					}
						break;

					default:
						break;
					}

					if (destination != null && !destination.isEmpty()
							&& destination != source
							&& !(destination instanceof SingleCell)) {
						for (int k = temp.length() - 1; k >= 0; k--) {
							Card card = temp.getCardAtLocation(k);

							if (((destination instanceof AcePile)
									&& card.getSuit().equals(
											((AcePile) destination).getSuit())
									&& card.getNumber().isLessByOneThan(
											destination.peek().getNumber()) && k == 0)
									|| (!(destination instanceof AcePile)
											&& card.getColor() != destination
													.peek().getColor() && card
											.getNumber().isGreaterByOneThan(
													destination.peek()
															.getNumber()))) {
								String hintString = "Move the ";

								if (card.getNumber().equals(CardRank.JACK)) {
									hintString += "Jack";
								} else if (card.getNumber().equals(
										CardRank.QUEEN)) {
									hintString += "Queen";
								} else if (card.getNumber().equals(
										CardRank.KING)) {
									hintString += "King";
								} else if (card.getNumber()
										.equals(CardRank.ACE)) {
									hintString += "Ace";
								} else {
									hintString += card.getNumber();
								}

								hintString += " of " + card.getSuit() + " in "
										+ sourceString + " to the ";

								if (destination.peek().getNumber()
										.equals(CardRank.JACK)) {
									hintString += "Jack";
								} else if (destination.peek().getNumber()
										.equals(CardRank.QUEEN)) {
									hintString += "Queen";
								} else if (destination.peek().getNumber()
										.equals(CardRank.KING)) {
									hintString += "King";
								} else if (destination.peek().getNumber()
										.equals(CardRank.ACE)) {
									hintString += "Ace";
								} else {
									hintString += destination.peek()
											.getNumber();
								}

								hintString += " of "
										+ destination.peek().getSuit() + " in "
										+ destinationString;

								hints.add(hintString);
								/*
								 * Once a move is found from a source to
								 * destination, stop looking for more.
								 */
								break;
							}
						}
					} else if (destination != null
							&& destination != source
							&& (destination instanceof Column)
							&& destination.isEmpty()
							&& (source.getBottom().getNumber()
									.equals(CardRank.KING) == false || source instanceof SingleCell)) {
						for (int k = 0; k < temp.length(); k++) {
							Card card = temp.getCardAtLocation(k);

							if (card.getNumber().equals(CardRank.KING)) {
								String hintString = "Move the King of "
										+ card.getSuit() + " in "
										+ sourceString + " to the empty "
										+ destinationString;

								hints.add(hintString);
								/*
								 * Once a move is found from a source to
								 * destination, stop looking for more.
								 */
								break;
							}
						}
					} else if (destination != null && destination != source
							&& (destination instanceof AcePile)
							&& destination.isEmpty()) {
						Card card = temp.peek();

						if (card.getNumber().equals(CardRank.ACE)
								&& card.getSuit().equals(
										((AcePile) destination).getSuit())) {
							String hintString = "Move the Ace of "
									+ card.getSuit() + " in " + sourceString
									+ " to " + destinationString;

							hints.add(hintString);
							/*
							 * Once a move is found from a source to
							 * destination, stop looking for more.
							 */
							break;
						}
					}
				}
			}
		}

		for (int i = 0; i < cells.length; i++) {
			if (cells[i].isEmpty() == true) {
				String hintString = "Move any available card to Cell "
						+ (i + 1);
				hints.add(hintString);
			}
		}

		if (hints.isEmpty() == false) {
			String string = "";

			for (int i = 0; i < hints.size(); i++) {
				string += hints.get(i) + "\n";
			}

			JOptionPane.showMessageDialog(this, string, "Hints Galore",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this,
					"There are no moves on the field.\n"
							+ "Either deal more cards or start a new game",
					"Hints", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Manages the mouse events.
	 * 
	 * @author Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav
	 *         Medarov
	 * 
	 */
	private class MyMouseListener extends MouseInputAdapter {
		/**
		 * If true, the player hasn't completed a move.
		 */
		private boolean hasSelected = false;

		/**
		 * If true, the selected stack is only one card.
		 */
		private boolean singleCardSelected = false;

		/**
		 * 
		 */
		private Card clickedCard;
		/**
		 * 
		 */
		private CardStack source;
		/**
		 * 
		 */
		private CardStack destination;
		/**
		 * 
		 */
		private CardStack temp;

		/**
		 * For right clicking discard pile view.
		 */
		private Card tempCard;

		/**
		 * To prevent clicking cards from the right click view.
		 */
		private boolean rightClicked = false;

		/**
		 * Checks if the game is won
		 */
		private void checkWin() {
			for (int i = 0; i < acePiles.length; i++) {
				if (acePiles[i].isEmpty()
						|| acePiles[i].peek().getNumber().equals(CardRank.KING) == false) {
					return;
				}
			}

			if (WinScreen.animation != 0 || WinScreen.sounds != 0) {
				new WinScreen();
			}

			if (timerToRun) {
				final TopTimes top = new TopTimes();
				int pos = top.IsTopTime(timerCount);

				top.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						top.setVisible(false);
						int playAgain = JOptionPane.showConfirmDialog(
								SolitaireBoard.this, "Play Again?", "You Won!",
								JOptionPane.YES_NO_OPTION);

						if (playAgain == JOptionPane.YES_OPTION) {
							recordGame(GameState.GAME_WON);
							newGame(GameState.GAME_WON);
						} else if (playAgain == JOptionPane.NO_OPTION) {
							recordGame(GameState.GAME_WON);
							System.exit(0);
						}
					}
				});

				if (pos >= 0) {
					top.setProperties(timerCount);
					top.setVisible(true);
				} else {
					top.dispose();
				}
			}

			int playAgain = JOptionPane.showConfirmDialog(SolitaireBoard.this,
					"Play Again?", "You Won!", JOptionPane.YES_NO_OPTION);

			if (playAgain == JOptionPane.YES_OPTION) {
				recordGame(GameState.GAME_WON);
				newGame(GameState.GAME_WON);
			} else if (playAgain == JOptionPane.NO_OPTION) {
				recordGame(GameState.GAME_WON);
				System.exit(0);
			}
		}

		/**
		 * Mouse-pressed event.
		 * 
		 * @param e
		 */
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3
					&& e.getSource() == discardPile) {
				if (discardPile.getNumViewableCards() == 1
						|| (discardPile.getNumViewableCards() == 0 && !discardPile
								.isEmpty())) {
					tempCard = discardPile.pop();
					discardPile.repaint();
					rightClicked = true;
				}
			}
		}

		/**
		 * Mouse-released event.
		 * 
		 * @param e
		 */
		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3 && tempCard != null) {
				discardPile.push(tempCard);
				discardPile.repaint();
				rightClicked = false;
				tempCard = null;
			}
		}

		/**
		 * Mouse-clicked event.
		 * 
		 * @param e
		 */
		public void mouseClicked(MouseEvent e) {
			discardPile.repaint();
			discardPile.revalidate();

			if (!timer.isRunning() && timerToRun) {
				timer.start();
			}

			if ((e.getButton() != MouseEvent.BUTTON1) || rightClicked) {
				return;
			}

			else if (e.getClickCount() == 2 && hasSelected
					&& singleCardSelected) {
				if (source.peek().getNumber().equals(CardRank.ACE)) {
					if (source.peek().getSuit().equals(CardSuit.SPADES)) {
						Card card = source.pop();
						card.unhighlight();
						acePiles[0].push(card);
						destinationList.add(acePiles[0]);
					} else if (source.peek().getSuit().equals(CardSuit.CLUBS)) {
						Card card = source.pop();
						card.unhighlight();
						acePiles[1].push(card);
						destinationList.add(acePiles[1]);
					} else if (source.peek().getSuit()
							.equals(CardSuit.DIAMONDS)) {
						Card card = source.pop();
						card.unhighlight();
						acePiles[2].push(card);
						destinationList.add(acePiles[2]);
					} else if (source.peek().getSuit().equals(CardSuit.HEARTS)) {
						Card card = source.pop();
						card.unhighlight();
						acePiles[3].push(card);
						destinationList.add(acePiles[3]);
					}

					hasSelected = false;
					source.repaint();
					repaint();
					return;
				}

				for (int i = 0; i < acePiles.length; i++) {
					if (!acePiles[i].isEmpty()
							&& source.peek().getSuit()
									.equals(acePiles[i].peek().getSuit())
							&& source
									.peek()
									.getNumber()
									.isLessByOneThan(
											(acePiles[i].peek().getNumber()))) {
						Card card = source.pop();
						card.unhighlight();
						acePiles[i].push(card);

						destinationList.add(acePiles[i]);
						hasSelected = false;

						source.repaint();
						repaint();

						if (card.getNumber().equals(CardRank.KING)) {
							checkWin();
						}

						return;
					}
				}

				for (int i = 0; i < cells.length; i++) {
					if (cells[i].isEmpty()) {
						Card card = source.pop();
						card.unhighlight();
						cells[i].push(card);

						destinationList.add(cells[i]);
						hasSelected = false;

						source.repaint();
						repaint();
						return;
					}
				}

				source.peek().unhighlight();

				source.repaint();
				repaint();
				return;
			} else if (e.getClickCount() == 2 && hasSelected) {
				hasSelected = false;

				if (temp.length() > 0) {
					for (int i = 0; i < temp.length(); i++) {
						source.getCardAtLocation(source.length() - i - 1)
								.unhighlight();
					}
				}

				sourceList.removeLast();
				numCardsInDiscardView.removeLast();
				numCards.removeLast();
			}

			else if (!hasSelected && e.getClickCount() == 1
					|| (e.getSource() instanceof DealDeck)) {
				source = (CardStack) e.getSource();

				if (source instanceof DealDeck) {
					if (hasSelected) {
						hasSelected = false;

						if (temp.length() > 0) {
							for (int i = 0; i < temp.length(); i++) {
								sourceList
										.getLast()
										.getCardAtLocation(
												sourceList.getLast().length()
														- i - 1).unhighlight();
							}
						} else {
							sourceList.getLast().peek().unhighlight();
						}

						sourceList.getLast().repaint();
						repaint();
						sourceList.removeLast();
						numCardsInDiscardView.removeLast();
						numCards.removeLast();
					}

					numCardsInDiscardView
							.add(discardPile.getNumViewableCards());
					clickedCard = source.pop();

					if (clickedCard != null) {
						sourceList.add(dealDeck);
						destinationList.add(discardPile);
						numCards.add(discardPile.getNumViewableCards());
					}
					/*
					 * The deck was reset but the player hasn't used up the
					 * times through the deck.
					 */
					else if (dealDeck.hasDealsLeft()) {
						sourceList.add(dealDeck);
						destinationList.add(discardPile);
						numCards.add(0);
					} else {
						numCardsInDiscardView.removeLast();
					}

					return;
				}

				numCardsInDiscardView.add(discardPile.getNumViewableCards());
				clickedCard = source.getCardAtLocation(e.getPoint());

				if (clickedCard != null) {
					hasSelected = true;
					temp = source.getStack(clickedCard);

					sourceList.add(source);
					numCards.add(temp.length());

					if (temp.length() > 1) {
						singleCardSelected = false;
					} else {
						singleCardSelected = true;
					}
				} else {
					numCardsInDiscardView.removeLast();
					hasSelected = false;
					return;
				}
			}
			/*
			 * Stack/card already selected.
			 */
			else if (e.getClickCount() == 1 && hasSelected) {
				destination = (CardStack) e.getSource();

				if (singleCardSelected) {
					if (destination.isValidMove(clickedCard)) {
						Card card = source.pop();
						card.unhighlight();
						destination.push(card);

						/*
						 * If move is valid, add destination info for undo.
						 */
						destinationList.add(destination);

						if (destination instanceof AcePile
								&& clickedCard.getNumber()
										.equals(CardRank.KING)) {
							repaint();
							checkWin();
						}
					} else {
						/*
						 * Not needed with highlighting version.
						 */
						source.peek().unhighlight();

						/*
						 * Upon invalid move, remove undo information for cards.
						 */
						sourceList.removeLast();
						numCards.removeLast();
						numCardsInDiscardView.removeLast();
					}
				} else {
					if (destination.isValidMove(temp)) {
						CardStack stack = new CardStack();

						for (int i = temp.length(); i > 0; i--) {
							Card card = source.pop();
							card.unhighlight();

							stack.push(card);
						}

						destination.push(stack);

						/*
						 * If move is valid, add destination info for undo.
						 */
						destinationList.add(destination);
					} else {
						for (int i = temp.length() - 1; i >= 0; i--) {
							source.getCardAtLocation(source.length() - i - 1)
									.unhighlight();
						}

						/*
						 * Upon invalid move, remove undo information for cards.
						 */
						sourceList.removeLast();
						numCards.removeLast();
						numCardsInDiscardView.removeLast();
					}
				}

				singleCardSelected = false;
				hasSelected = false;
				temp = null;
				clickedCard = null;
			}

			repaint();
		}
	}

	/**
	 * Timer displaying.
	 * 
	 * @author Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav
	 *         Medarov
	 * 
	 */
	private class TimerListener implements ActionListener {

		/**
		 * Action performed
		 * 
		 * @param e
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() != timer) {
				return;
			}
			timerCount++;
			timerLabel.setText("Time: " + timerCount);
			statusBar.repaint();
		}
	}

	/**
	 * Manages the window events.
	 * 
	 * @author Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav
	 *         Medarov
	 * 
	 */
	public class MyWindowListener extends WindowAdapter {

		/**
		 * On closing the main window:
		 * 
		 * @param e
		 */
		public void windowClosing(WindowEvent e) {
			int save = JOptionPane
					.showConfirmDialog(
							SolitaireBoard.this,
							"Closing without saving will result "
									+ "in a loss, would you like to save the current game?",
							"Save Game?", JOptionPane.YES_NO_OPTION);

			if (save == JOptionPane.YES_OPTION) {
				recordGame(GameState.GAME_SAVED);
				System.exit(0);
			} else if (save == JOptionPane.NO_OPTION) {
				recordGame(GameState.GAME_LOST);
				System.exit(0);
			}
		}
	}
}
