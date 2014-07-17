package eu.veldsoft.four.row.solitaire;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;

/**
 * 
 * @author Konstantin Tsanov
 * 
 */
class SolitaireFrame extends JFrame {

	/**
	 * Manages the window events.
	 * 
	 * @author Todor Balabanov
	 */
	private class MyWindowListener extends WindowAdapter {

		/**
		 * On closing the main window:
		 * 
		 * @param e
		 */
		public void windowClosing(WindowEvent e) {
			int save = JOptionPane
					.showConfirmDialog(
							SolitaireFrame.this,
							"Closing without saving will result "
									+ "in a loss, would you like to save the current game?",
							"Save Game?", JOptionPane.YES_NO_OPTION);

			if (save == JOptionPane.YES_OPTION) {
				board.recordGame(GameState.GAME_SAVED, deckNumber,
						backgroundNumber, timerCount, timerToRunNextGame,
						timerToRun);
				System.exit(0);
			} else if (save == JOptionPane.NO_OPTION) {
				board.recordGame(GameState.GAME_LOST, deckNumber,
						backgroundNumber, timerCount, timerToRunNextGame,
						timerToRun);
				System.exit(0);
			}
		}
	}

	/**
	 * Timer displaying.
	 * 
	 * @author Todor Balabanov
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
	 * Manages the mouse events.
	 * 
	 * @author Todor Balabanov
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
			for (int i = 0; i < board.acePiles.length; i++) {
				if (board.acePiles[i].isEmpty()
						|| board.acePiles[i].peek().getNumber()
								.equals(CardRank.KING) == false) {
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
								SolitaireFrame.this, "Play Again?", "You Won!",
								JOptionPane.YES_NO_OPTION);

						if (playAgain == JOptionPane.YES_OPTION) {
							board.recordGame(GameState.GAME_WON, deckNumber,
									backgroundNumber, timerCount,
									timerToRunNextGame, timerToRun);
							board.newGame(GameState.GAME_WON);
						} else if (playAgain == JOptionPane.NO_OPTION) {
							board.recordGame(GameState.GAME_WON, deckNumber,
									backgroundNumber, timerCount,
									timerToRunNextGame, timerToRun);
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

			int playAgain = JOptionPane.showConfirmDialog(SolitaireFrame.this,
					"Play Again?", "You Won!", JOptionPane.YES_NO_OPTION);

			if (playAgain == JOptionPane.YES_OPTION) {
				board.recordGame(GameState.GAME_WON, deckNumber,
						backgroundNumber, timerCount, timerToRunNextGame,
						timerToRun);
				board.newGame(GameState.GAME_WON);
			} else if (playAgain == JOptionPane.NO_OPTION) {
				board.recordGame(GameState.GAME_WON, deckNumber,
						backgroundNumber, timerCount, timerToRunNextGame,
						timerToRun);
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
					&& e.getSource() == board.discardPile) {
				if (board.discardPile.getNumViewableCards() == 1
						|| (board.discardPile.getNumViewableCards() == 0 && !board.discardPile
								.isEmpty())) {
					tempCard = board.discardPile.pop();
					board.discardPile.repaint();
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
				board.discardPile.push(tempCard);
				board.discardPile.repaint();
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
			board.discardPile.repaint();
			board.discardPile.revalidate();

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
						board.acePiles[0].push(card);
						board.destinationList.add(board.acePiles[0]);
					} else if (source.peek().getSuit().equals(CardSuit.CLUBS)) {
						Card card = source.pop();
						card.unhighlight();
						board.acePiles[1].push(card);
						board.destinationList.add(board.acePiles[1]);
					} else if (source.peek().getSuit()
							.equals(CardSuit.DIAMONDS)) {
						Card card = source.pop();
						card.unhighlight();
						board.acePiles[2].push(card);
						board.destinationList.add(board.acePiles[2]);
					} else if (source.peek().getSuit().equals(CardSuit.HEARTS)) {
						Card card = source.pop();
						card.unhighlight();
						board.acePiles[3].push(card);
						board.destinationList.add(board.acePiles[3]);
					}

					hasSelected = false;
					source.repaint();
					repaint();
					return;
				}

				for (int i = 0; i < board.acePiles.length; i++) {
					if (!board.acePiles[i].isEmpty()
							&& source.peek().getSuit()
									.equals(board.acePiles[i].peek().getSuit())
							&& source
									.peek()
									.getNumber()
									.isLessByOneThan(
											(board.acePiles[i].peek()
													.getNumber()))) {
						Card card = source.pop();
						card.unhighlight();
						board.acePiles[i].push(card);

						board.destinationList.add(board.acePiles[i]);
						hasSelected = false;

						source.repaint();
						repaint();

						if (card.getNumber().equals(CardRank.KING)) {
							checkWin();
						}

						return;
					}
				}

				for (int i = 0; i < board.cells.length; i++) {
					if (board.cells[i].isEmpty()) {
						Card card = source.pop();
						card.unhighlight();
						board.cells[i].push(card);

						board.destinationList.add(board.cells[i]);
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

				board.sourceList.removeLast();
				board.numCardsInDiscardView.removeLast();
				board.numCards.removeLast();
			}

			else if (!hasSelected && e.getClickCount() == 1
					|| (e.getSource() instanceof DealDeck)) {
				source = (CardStack) e.getSource();

				if (source instanceof DealDeck) {
					if (hasSelected) {
						hasSelected = false;

						if (temp.length() > 0) {
							for (int i = 0; i < temp.length(); i++) {
								board.sourceList
										.getLast()
										.getCardAtLocation(
												board.sourceList.getLast()
														.length() - i - 1)
										.unhighlight();
							}
						} else {
							board.sourceList.getLast().peek().unhighlight();
						}

						board.sourceList.getLast().repaint();
						repaint();
						board.sourceList.removeLast();
						board.numCardsInDiscardView.removeLast();
						board.numCards.removeLast();
					}

					board.numCardsInDiscardView.add(board.discardPile
							.getNumViewableCards());
					clickedCard = source.pop();

					if (clickedCard != null) {
						board.sourceList.add(board.dealDeck);
						board.destinationList.add(board.discardPile);
						board.numCards.add(board.discardPile
								.getNumViewableCards());
					}
					/*
					 * The deck was reset but the player hasn't used up the
					 * times through the deck.
					 */
					else if (board.dealDeck.hasDealsLeft()) {
						board.sourceList.add(board.dealDeck);
						board.destinationList.add(board.discardPile);
						board.numCards.add(0);
					} else {
						board.numCardsInDiscardView.removeLast();
					}

					return;
				}

				board.numCardsInDiscardView.add(board.discardPile
						.getNumViewableCards());
				clickedCard = source.getCardAtLocation(e.getPoint());

				if (clickedCard != null) {
					hasSelected = true;
					temp = source.getStack(clickedCard);

					board.sourceList.add(source);
					board.numCards.add(temp.length());

					if (temp.length() > 1) {
						singleCardSelected = false;
					} else {
						singleCardSelected = true;
					}
				} else {
					board.numCardsInDiscardView.removeLast();
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
						board.destinationList.add(destination);

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
						board.sourceList.removeLast();
						board.numCards.removeLast();
						board.numCardsInDiscardView.removeLast();
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
						board.destinationList.add(destination);
					} else {
						for (int i = temp.length() - 1; i >= 0; i--) {
							source.getCardAtLocation(source.length() - i - 1)
									.unhighlight();
						}

						/*
						 * Upon invalid move, remove undo information for cards.
						 */
						board.sourceList.removeLast();
						board.numCards.removeLast();
						board.numCardsInDiscardView.removeLast();
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
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Find better OOP modeling alternative! Use enumerated type for card back.
	 */
	static int deckNumber = 3;

	/**
	 * Find better OOP modeling alternative! Use enumerated type for card back.
	 */
	static int backgroundNumber = 2;

	/**
	 * Solitaire Board.
	 */
	private SolitaireBoard board = new SolitaireBoard();

	/**
	 * Timer.
	 */
	private Timer timer = new Timer(1000, new TimerListener());

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
	 * 
	 */
	private SolitairePanel mainPanel = null;

	/**
	 * Status bar.
	 */
	private JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.CENTER));

	/**
	 * Timer label.
	 */
	private JLabel timerLabel = new JLabel("Time: OFF");

	/**
	 * 
	 */
	private MyMouseListener ml = new MyMouseListener();


	/**
	 * 
	 */
	protected WindowListener wl = new MyWindowListener();

	/**
	 * 
	 * @throws HeadlessException
	 */
	public SolitaireFrame() throws HeadlessException {
		super();
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
	 * Sets the timer counter.
	 * 
	 * @param time
	 */
	public void setTimer(int time) {
		timerCount = time;
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
	 * Returns next game timer status.
	 * 
	 * @return
	 */
	public int getTimerNextGameStatus() {
		return timerToRunNextGame;
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
	 * Manages the hints.
	 * 
	 * @author Todor Balabanov
	 */
	@SuppressWarnings("fallthrough")
	public void getHint() {
		String hint[] = board.getHint();
		JOptionPane.showMessageDialog(this, hint[0], hint[1],
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Used to undo a move.
	 * 
	 * @author Todor Balabanov
	 */
	public synchronized void undoMove() {
		if (board.sourceList.size() > board.destinationList.size()) {
			if (board.numCards.getLast() == 1) {
				ml.clickedCard = null;
				ml.hasSelected = false;
				ml.singleCardSelected = false;
				ml.temp = null;
			} else {
				ml.clickedCard = null;
				ml.hasSelected = false;
				ml.temp = null;
			}
		}

		board.undoMove();
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
			if (JOptionPane.showConfirmDialog(this,
					"Quitting the current game will result in a loss.\n"
							+ "Do you wish to continue?", "Continue?",
					JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION) {
				board.recordGame(GameState.GAME_LOST, deckNumber,
						backgroundNumber, timerCount, timerToRunNextGame,
						timerToRun);
				board.newGame(winOrLoss);
				dealOutBoard();
			}
		}

	}

	/**
	 * Used to reset the stats.
	 */
	public void resetStats() {
		board.recordGame(GameState.RESET_STATS, deckNumber, backgroundNumber,
				timerCount, timerToRunNextGame, timerToRun);
	}

	/**
	 * Save options.
	 */
	public void saveOptions() {
		board.recordGame(GameState.DO_NOTHING, deckNumber, backgroundNumber,
				timerCount, timerToRunNextGame, timerToRun);
	}

	/**
	 * Manages the game states.
	 * 
	 * @param winOrLoss
	 */
	void recordGame(GameState winOrLoss) {
		board.recordGame(winOrLoss, deckNumber, backgroundNumber, timerCount,
				timerToRunNextGame, timerToRun);
	}

	/**
	 * Used to deal the cards on the board after opening a saved game.
	 * 
	 * @param numbers
	 * 
	 * @param numViewableCards
	 * 
	 * @author Todor Balabanov
	 */
	private void dealOutCustomBoard(LinkedList<Integer> numbers,
			int numViewableCards) {
		board.dealOutCustomBoard(numbers, numViewableCards);

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
	 * Creates the solitaire board.
	 * 
	 * @param cards
	 * @param numViewableCards
	 * 
	 * @author Todor Balabanov
	 */
	public void createBoard(LinkedList<Integer> cards, int numViewableCards) {
		board.createBoard(cards, numViewableCards);

		mainPanel = new SolitairePanel();
		mainPanel.setLayout(new SolitaireLayout());

		mainPanel.changeBackground(backgroundNumber);

		for (int i = 0; i < board.columns.length; i++) {
			board.columns[i].addMouseListener(ml);
		}

		mainPanel.add(board.columns[0], SolitaireLayout.COLUMN_ONE);
		mainPanel.add(board.columns[1], SolitaireLayout.COLUMN_TWO);
		mainPanel.add(board.columns[2], SolitaireLayout.COLUMN_THREE);
		mainPanel.add(board.columns[3], SolitaireLayout.COLUMN_FOUR);

		for (int i = 0; i < board.cells.length; i++) {
			board.cells[i].addMouseListener(ml);
		}

		mainPanel.add(board.cells[0], SolitaireLayout.CELL_ONE);
		mainPanel.add(board.cells[1], SolitaireLayout.CELL_TWO);
		mainPanel.add(board.cells[2], SolitaireLayout.CELL_THREE);
		mainPanel.add(board.cells[3], SolitaireLayout.CELL_FOUR);

		board.dealDeck.addMouseListener(ml);
		board.discardPile.addMouseListener(ml);

		mainPanel.add(board.dealDeck, SolitaireLayout.DECK);
		mainPanel.add(board.discardPile, SolitaireLayout.DISCARD_PILE);

		for (int i = 0; i < board.acePiles.length; i++) {
			board.acePiles[i].addMouseListener(ml);
		}

		mainPanel.add(board.acePiles[0], SolitaireLayout.SPADES_ACE_PILE);
		mainPanel.add(board.acePiles[1], SolitaireLayout.CLUBS_ACE_PILE);
		mainPanel.add(board.acePiles[2], SolitaireLayout.DIAMONDS_ACE_PILE);
		mainPanel.add(board.acePiles[3], SolitaireLayout.HEARTS_ACE_PILE);

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
	 * 
	 * @author Todor Balabanov
	 */
	private void dealOutBoard() {
		board.dealOutBoard();

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
	 * 
	 * @param newDrawCount
	 * 
	 * @author Todor Balabanov
	 */
	public void setNewDrawCount(int newDrawCount) {
		board.setNewDrawCount(newDrawCount);
	}

	/**
	 * 
	 * @param drawCount
	 * 
	 * @author Todor Balabanov
	 */
	public void setDrawCount(int drawCount) {
		board.setDrawCount(drawCount);
	}

	/**
	 * 
	 * @param easy
	 * 
	 * @author Todor Balabanov
	 */
	public void setDifficulty(GameDifficulty easy) {
		board.setDifficulty(easy);
	}

	/**
	 * 
	 * @param easy
	 * 
	 * @author Todor Balabanov
	 */
	public void setNewDifficulty(GameDifficulty easy) {
		board.setNewDifficulty(easy);
	}

	/**
	 * 
	 * @param deckThroughs
	 * 
	 * @author Todor Balabanov
	 */
	public void setDeckThroughs(int deckThroughs) {
		board.setDeckThroughs(deckThroughs);
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public int getNewDrawCount() {
		return board.getNewDrawCount();
	}

	/**
	 * 
	 * @return
	 * 
	 * @author Todor Balabanov
	 */
	public int getNewDifficulty() {
		return board.getNewDifficulty().getValue();
	}
}
