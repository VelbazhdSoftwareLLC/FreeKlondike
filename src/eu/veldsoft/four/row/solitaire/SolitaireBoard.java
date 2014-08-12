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

import java.awt.Component;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Class: SolitaireBoard
 * 
 * Description: The SolitaireBoard class manages the entire playing field.
 * 
 * @author Matt Stephen
 */
class SolitaireBoard {

	/**
	 * 
	 */
	private static final int INITIAL_CARDS_NUMBER_IN_COLUMN = 5;

	/**
	 * Can be 1 or 3. Should be only here!
	 */
	static int drawCount = 1;

	/**
	 * To store new option selection for next new game, otherwise the count
	 * would be changed at next click of the deck (in the middle of the game).
	 */
	private int newDrawCount = drawCount;

	/**
	 * The four columns for the main playing field.
	 */
	// TODO Should be private.
	ColumnLayeredPane[] columns = new ColumnLayeredPane[4];

	/**
	 * The discard pile.
	 */
	// TODO Should be private.
	DiscardPileLayeredPane discardPile = new DiscardPileLayeredPane();

	/**
	 * The deal pile.
	 */
	// TODO Should be private.
	DealDeckLayeredPane dealDeck = new DealDeckLayeredPane(discardPile);

	/**
	 * The four ace piles (to stack Ace - King of a single suit).
	 */
	// TODO Should be private.
	AcePileLayeredPane[] acePiles = new AcePileLayeredPane[4];

	/**
	 * The four top individual cells.
	 */
	// TODO Should be private.
	SingleCellLayeredPane[] cells = new SingleCellLayeredPane[4];

	/**
	 * 1 = easy, 2 = medium, 3 = hard Should be only here!
	 */
	private GameDifficulty difficulty = GameDifficulty.MEDIUM;

	/**
	 * Game difficulty.
	 */
	private GameDifficulty newDifficulty = difficulty;

	/**
	 * Source.
	 */
	// TODO Should be private.
	LinkedList<CardStackLayeredPane> sourceList = new LinkedList<CardStackLayeredPane>();

	/**
	 * Destination.
	 */
	// TODO Should be private.
	LinkedList<CardStackLayeredPane> destinationList = new LinkedList<CardStackLayeredPane>();

	/**
	 * Card numbers.
	 */
	// TODO Should be private.
	LinkedList<Integer> numCards = new LinkedList<Integer>();

	/**
	 * The cards from the discard pile.
	 */
	LinkedList<Integer> numCardsInDiscardView = new LinkedList<Integer>();

	/**
	 * Sets the board's window name, size, location, close button option, makes
	 * it unresizable and puts the logo on it.
	 * 
	 * @author Todor Balabanov
	 */
	public SolitaireBoard() {
	}

	/**
	 * Creates the solitaire board.
	 * 
	 * @param cards
	 *            List of cards.
	 * 
	 * @param numViewableCards
	 *            Number of viewable cards.
	 * 
	 * @author Todor Balabanov
	 */
	public void createBoard(LinkedList<Integer> cards, int numViewableCards) {
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new ColumnLayeredPane();
		}

		for (int i = 0; i < cells.length; i++) {
			cells[i] = new SingleCellLayeredPane();
		}

		for (int i = 0; i < acePiles.length; i++) {
			switch (i) {
			case 0:
				acePiles[i] = new AcePileLayeredPane(CardSuit.SPADES);
				break;
			case 1:
				acePiles[i] = new AcePileLayeredPane(CardSuit.CLUBS);
				break;
			case 2:
				acePiles[i] = new AcePileLayeredPane(CardSuit.DIAMONDS);
				break;
			case 3:
				acePiles[i] = new AcePileLayeredPane(CardSuit.HEARTS);
				break;

			default:
				break;
			}
		}
	}

	/**
	 * Deals the cards.
	 * 
	 * @author Todor Balabanov
	 */
	void dealOutBoard() {
		LinkedList<Card> cards = (LinkedList<Card>) Deck.getFullShuffledDeck();

		/*
		 * Fill five cards by column.
		 */
		for (int i = 0; i < INITIAL_CARDS_NUMBER_IN_COLUMN; i++) {
			for (int j = 0; j < columns.length; j++) {
				columns[j].column.addCard(cards.getLast());
				cards.removeLast();
			}
		}

		/*
		 * Fill cards in buffer area.
		 */
		for (int j = 0; j < cells.length; j++) {
			cells[j].singleCell.addCard(cards.getLast());
			cards.removeLast();
		}

		dealDeck.getDealDeck().setDrawCount(newDrawCount);
		dealDeck.getDealDeck().setDifficulty(newDifficulty);

		if (newDrawCount != drawCount) {
			drawCount = newDrawCount;
		}

		if (newDifficulty != difficulty) {
			difficulty = newDifficulty;
		}

		dealDeck.getDealDeck().setDeck(cards);
	}

	/**
	 * Used to deal the cards on the board after opening a saved game.
	 * 
	 * @param numbers
	 *            List of integers.
	 * 
	 * @param numViewableCards
	 *            Number of viewable cards.
	 * 
	 * @author Todor Balabanov
	 */
	void dealOutCustomBoard(LinkedList<Integer> numbers, int numViewableCards) {
		List<Card> cards = Deck.getDeckSubsetByCardNumbers(numbers);

		int pileNumber = 0;
		int cardNumber = -1;

		dealDeck.getDealDeck().setDrawCount(drawCount);
		dealDeck.getDealDeck().setDifficulty(difficulty);

		for (int i = 0; i < numbers.size(); i++) {
			if (numbers.get(i) > 0) {
				cardNumber++;
			} else {
				pileNumber++;
				continue;
			}

			if (0 <= pileNumber && pileNumber <= 3) {
				cells[pileNumber % 4].singleCell.addCard(cards.get(cardNumber));
			} else if (4 <= pileNumber && pileNumber <= 7) {
				columns[pileNumber % 4].column.addCard(cards.get(cardNumber));
			} else if (8 <= pileNumber && pileNumber <= 11) {
				acePiles[pileNumber % 4].acePile.addCard(cards.get(cardNumber));
			} else if (pileNumber == 12) {
				Card card = cards.get(cardNumber);
				card.setFaceDown();
				dealDeck.dealDeck.addCard(card);
			} else if (pileNumber == 13) {
				discardPile.getDiscardPile().push(cards.get(cardNumber));
			}
		}

		discardPile.getDiscardPile().setView(numViewableCards);
	}

	/**
	 * Clears the board.
	 * 
	 * @author Todor Balabanov
	 */
	private void clearBoard() {
		for (int i = 0; i < columns.length; i++) {
			while (columns[i].getColumn().isEmpty() == false) {
				columns[i].pop();
			}

			columns[i].repaint();
		}

		for (int i = 0; i < cells.length; i++) {
			while (cells[i].getSingleCell().isEmpty() == false) {
				cells[i].pop();
			}

			cells[i].repaint();
		}

		for (int i = 0; i < acePiles.length; i++) {
			while (acePiles[i].getAcePile().isEmpty() == false) {
				acePiles[i].pop();
			}

			acePiles[i].repaint();
		}

		while (dealDeck.getDealDeck().isEmpty() == false) {
			dealDeck.pop();
		}

		dealDeck.repaint();

		while (discardPile.getDiscardPile().isEmpty() == false) {
			discardPile.pop();
		}

		discardPile.repaint();
	}

	/**
	 * For starting a new game.
	 * 
	 * @param winOrLoss
	 *            Game state.
	 * 
	 * @author Todor Balabanov
	 */
	public void newGame(GameState winOrLoss) {
		/*
		 * Remove cards from ace piles. Set numTimesThroughDeck back to 1.
		 */
		clearBoard();
		dealDeck.getDealDeck().reset();

		sourceList.clear();
		destinationList.clear();
		numCards.clear();
		numCardsInDiscardView.clear();
	}

	/**
	 * Manages the game states.
	 * 
	 * @param winOrLoss
	 *            Game state.
	 * 
	 * @param timerCount
	 *            Timer.
	 * 
	 * @param backgroundNumber
	 *            Background number.
	 * 
	 * @param deckNumber
	 *            Card back number.
	 * 
	 * @param timerToRunNextGame
	 *            Timer to next game.
	 * 
	 * @param timerToRun
	 *            Timer to run.
	 * 
	 * @author Todor Balabanov
	 */
	void recordGame(GameState winOrLoss, int deckNumber, int backgroundNumber,
			int timerCount, int timerToRunNextGame, boolean timerToRun) {
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
			output.writeInt(dealDeck.getDealDeck().getDeckThroughs());
			output.writeInt(difficulty.getValue());
			output.writeInt(newDifficulty.getValue());
			output.writeInt(discardPile.getDiscardPile().getNumViewableCards());

			File savedFile = new File(fileLocation + "frs-savedgame.dat");
			DataOutputStream saved = new DataOutputStream(new FileOutputStream(
					savedFile));

			if (winOrLoss == GameState.GAME_SAVED) {
				/*
				 * Saved.
				 */
				output.writeInt(1);

				for (int i = 0; i < cells.length; i++) {
					if (!cells[i].getSingleCell().isEmpty()) {
						saved.writeInt(cells[i].getSingleCell().peek()
								.getFullNumber());
						saved.writeInt(-1);
					} else {
						saved.writeInt(-1);
					}
				}

				for (int i = 0; i < columns.length; i++) {
					if (!columns[i].getColumn().isEmpty()) {
						for (int j = 0; j < columns[i].getColumn().length(); j++) {
							saved.writeInt(columns[i].getColumn()
									.getCardAtLocation(j).getFullNumber());
						}

						saved.writeInt(-1);
					} else {
						saved.writeInt(-1);
					}
				}

				for (int i = 0; i < acePiles.length; i++) {
					if (!acePiles[i].getAcePile().isEmpty()) {
						for (int j = 0; j < acePiles[i].getAcePile().length(); j++) {
							saved.writeInt(acePiles[i].getAcePile()
									.getCardAtLocation(j).getFullNumber());
						}

						saved.writeInt(-1);
					} else {
						saved.writeInt(-1);
					}
				}

				if (!dealDeck.getDealDeck().isEmpty()) {
					for (int j = 0; j < dealDeck.getDealDeck().length(); j++) {
						saved.writeInt(dealDeck.getDealDeck()
								.getCardAtLocation(j).getFullNumber());
					}

					saved.writeInt(-1);
				} else {
					saved.writeInt(-1);
				}

				if (!discardPile.getDiscardPile().isEmpty()) {
					for (int j = 0; j < discardPile.getDiscardPile().length(); j++) {
						saved.writeInt(discardPile.getDiscardPile()
								.getCardAtLocation(j).getFullNumber());
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
	 * Returns the draw count.
	 * 
	 * @return drawCount Current draw count.
	 * 
	 * @author Todor Balabanov
	 */
	public int getDrawCount() {
		return drawCount;
	}

	/**
	 * Sets draw count.
	 * 
	 * @param draw
	 *            Sets the new draw count.
	 * 
	 * @author Todor Balabanov
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
	 * @return newDrawCount New draw count.
	 * 
	 * @author Todor Balabanov
	 */
	public int getNewDrawCount() {
		return newDrawCount;
	}

	/**
	 * Sets the new draw count.
	 * 
	 * @param draw
	 *            New draw count to be set.
	 * 
	 * @author Todor Balabanov
	 */
	public void setNewDrawCount(int draw) {
		newDrawCount = draw;

		if (newDrawCount != 3 && newDrawCount != 1) {
			newDrawCount = 1;
		}
	}

	/**
	 * Returns game difficulty.
	 * 
	 * @return difficulty Current game difficulty.
	 * 
	 * @author Todor Balabanov
	 */
	public GameDifficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * Sets game difficulty.
	 * 
	 * @param difficulty
	 *            The new game difficulty.
	 * 
	 * @author Todor Balabanov
	 */
	public void setDifficulty(GameDifficulty difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * Returns the new difficulty.
	 * 
	 * @return newDifficulty New difficulty.
	 * 
	 * @author Todor Balabanov
	 */
	public GameDifficulty getNewDifficulty() {
		return newDifficulty;
	}

	/**
	 * Sets the new difficulty.
	 * 
	 * @param newDifficulty
	 *            New difficulty to be set.
	 * 
	 * @author Todor Balabanov
	 */
	public void setNewDifficulty(GameDifficulty newDifficulty) {
		this.newDifficulty = newDifficulty;
	}

	/**
	 * Sets the number of times through deck.
	 * 
	 * @param deckThroughs
	 *            Deck throughs to be set.
	 * 
	 * @author Todor Balabanov
	 */
	public void setDeckThroughs(int deckThroughs) {
		dealDeck.getDealDeck().setDeckThroughs(deckThroughs);
	}

	/**
	 * Used to undo a move.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized void undoMove() {
		if (sourceList.isEmpty()) {
			return;
		}

		/*
		 * If player is holding on to a card.
		 */
		if (sourceList.size() > destinationList.size()) {
			CardStackLayeredPane tempSource = sourceList.getLast();
			sourceList.removeLast();

			int num = numCards.getLast();
			numCards.removeLast();

			int numDiscard = numCardsInDiscardView.getLast();
			numCardsInDiscardView.removeLast();

			if (num == 1) {
				discardPile.getDiscardPile().setView(numDiscard);
				tempSource.peek().unhighlight();
			} else {
				for (int i = 0; i < num; i++) {
					tempSource.getCardAtLocation(tempSource.length() - i - 1)
							.unhighlight();
				}
			}

			((Component) tempSource).repaint();
		} else if (!(sourceList.getLast() instanceof DealDeckLayeredPane)) {
			CardStackLayeredPane tempSource = sourceList.getLast();
			CardStackLayeredPane tempDest = destinationList.getLast();
			int num = numCards.getLast();
			int numDiscard = numCardsInDiscardView.getLast();

			sourceList.removeLast();
			destinationList.removeLast();
			numCards.removeLast();
			numCardsInDiscardView.removeLast();

			if (num == 1) {
				tempSource.addCard(tempDest.pop());
			} else {
				CardStackLayeredPane temp = tempDest.undoStack(num);
				tempSource.addStack(temp);
			}

			discardPile.getDiscardPile().setView(numDiscard);
			((Component) tempSource).repaint();
			((Component) tempDest).repaint();
		}
		/*
		 * The last draw from the deck didn't reset the discard pile to make it
		 * an empty pile.
		 */
		else if (sourceList.getLast() instanceof DealDeckLayeredPane
				&& !destinationList.getLast().isEmpty()) {
			int num = numCards.getLast();
			int numDiscard = numCardsInDiscardView.getLast();

			sourceList.removeLast();
			destinationList.removeLast();
			numCards.removeLast();
			numCardsInDiscardView.removeLast();

			for (int i = 0; i < num; i++) {
				CardComponent card = CardComponent.cardsMapping.get(discardPile
						.getDiscardPile().undoPop());
				card.setFaceDown();
				dealDeck.addCard(card);
			}

			discardPile.getDiscardPile().setView(numDiscard);
			dealDeck.repaint();
			discardPile.repaint();
		}
		/*
		 * Last move was a reset on the discard pile.
		 */
		else if (sourceList.getLast() instanceof DealDeckLayeredPane) {
			dealDeck.getDealDeck().undoPop();

			int numDiscard = numCardsInDiscardView.getLast();
			discardPile.getDiscardPile().setView(numDiscard);

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
	 * 
	 * @return Hint structure.
	 * 
	 * @author Todor Balabanov
	 */
	@SuppressWarnings("fallthrough")
	public String[] getHint() {
		CardStackLayeredPane source = null;
		CardStackLayeredPane destination = null;
		CardStack temp = null;

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
							CardComponent card = CardComponent.cardsMapping
									.get(temp.getCardAtLocation(k));

							if (((destination instanceof AcePileLayeredPane)
									&& card.getCard()
											.getSuit()
											.equals(((AcePileLayeredPane) destination)
													.getAcePile().getSuit())
									&& card.getCard()
											.getRank()
											.isLessByOneThan(
													((AcePileLayeredPane) (destination))
															.getAcePile()
															.peek().getRank()) && k == 0)
									|| (!(destination instanceof AcePileLayeredPane)
											&& card.getCard().getColor() != ((AcePileLayeredPane) destination)
													.getAcePile().peek()
													.getColor() && card
											.getCard()
											.getRank()
											.isGreaterByOneThan(
													(((AcePileLayeredPane) destination)
															.getAcePile()
															.peek()).getRank()))) {
								String hintString = "Move the ";

								if (card.getCard().getRank()
										.equals(CardRank.JACK)) {
									hintString += "Jack";
								} else if (card.getCard().getRank()
										.equals(CardRank.QUEEN)) {
									hintString += "Queen";
								} else if (card.getCard().getRank()
										.equals(CardRank.KING)) {
									hintString += "King";
								} else if (card.getCard().getRank()
										.equals(CardRank.ACE)) {
									hintString += "Ace";
								} else {
									hintString += card.getCard().getRank();
								}

								hintString += " of " + card.getCard().getSuit()
										+ " in " + sourceString + " to the ";

								if (((AcePileLayeredPane) (destination))
										.getAcePile().peek().getRank()
										.equals(CardRank.JACK)) {
									hintString += "Jack";
								} else if (((AcePileLayeredPane) (destination))
										.getAcePile().peek().getRank()
										.equals(CardRank.QUEEN)) {
									hintString += "Queen";
								} else if (((AcePileLayeredPane) (destination))
										.getAcePile().peek().getRank()
										.equals(CardRank.KING)) {
									hintString += "King";
								} else if (((AcePileLayeredPane) (destination))
										.getAcePile().peek().getRank()
										.equals(CardRank.ACE)) {
									hintString += "Ace";
								} else {
									hintString += ((AcePileLayeredPane) (destination))
											.getAcePile().peek().getRank();
								}

								hintString += " of "
										+ ((AcePileLayeredPane) (destination))
												.getAcePile().peek().getSuit()
										+ " in " + destinationString;

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
							&& (destination instanceof ColumnLayeredPane)
							&& ((ColumnLayeredPane) destination).getColumn()
									.isEmpty()
							&& (source.getBottom().getCard().getRank()
									.equals(CardRank.KING) == false || source instanceof SingleCellLayeredPane)) {
						for (int k = 0; k < temp.length(); k++) {
							CardComponent card = CardComponent.cardsMapping
									.get(temp.getCardAtLocation(k));

							if (card.getCard().getRank().equals(CardRank.KING)) {
								String hintString = "Move the King of "
										+ card.getCard().getSuit() + " in "
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
					} else if (destination != null
							&& destination != source
							&& (destination instanceof AcePileLayeredPane)
							&& ((AcePileLayeredPane) destination).getAcePile()
									.isEmpty()) {
						CardComponent card = CardComponent.cardsMapping
								.get(temp.peek());

						if (card.getCard().getRank().equals(CardRank.ACE)
								&& card.getCard()
										.getSuit()
										.equals(((AcePileLayeredPane) destination)
												.getAcePile().getSuit())) {
							String hintString = "Move the Ace of "
									+ card.getCard().getSuit() + " in "
									+ sourceString + " to " + destinationString;

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
			if (cells[i].getSingleCell().isEmpty() == true) {
				String hintString = "Move any available card to Cell "
						+ (i + 1);
				hints.add(hintString);
			}
		}

		String hint[] = { "", "" };
		if (hints.isEmpty() == false) {
			for (int i = 0; i < hints.size(); i++) {
				hint[0] += hints.get(i) + "\n";
			}
			hint[1] = "Hints Galore";
		} else {
			hint[0] = "There are no moves on the field.\n"
					+ "Either deal more cards or start a new game";
			hint[1] = "Hints";
		}

		return (hint);
	}
}
