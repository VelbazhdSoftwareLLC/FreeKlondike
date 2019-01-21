/*
 This file is a part of Free Klondike

 Copyright (C) 2010-2014 by Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov, Vanya Gyaurova, Plamena Popova, Hristiana Kalcheva, Yana Genova

 Free Klondike is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Free Klondike is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with FreeKlondike.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.veldsoft.free.klondike;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import eu.veldsoft.free.klondike.R;

/**
 * 
 * @author Konstantin Tsanov
 */
public class GameActivity extends Activity {
	// TODO Find better object model.

	/**
	 * It is used only for image views mapping as indexed arrays.
	 * 
	 * @author Todor Balabanov
	 */
	private static class CardsViews {
		static ImageView aces[] = new ImageView[4];
		static ImageView cells[] = new ImageView[4];
		static ImageView columns[][] = new ImageView[4][17];
		static ImageView deal;
		static ImageView discard[] = new ImageView[3];
		static ImageView all[] = null;
	}

	/**
	 * 
	 */
	private HashMap<Card, Integer> cardsImagesMapping = new HashMap<Card, Integer>();

	/**
	 * 
	 */
	private HashMap<Card, Integer> cardsHighlightedImagesMapping = new HashMap<Card, Integer>();

	/**
	 * 
	 */
	private SolitaireBoard board = new SolitaireBoard();

	private void resizeImageViews() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		for (ImageView view : CardsViews.all) {
		}
	}

	/**
	 * 
	 */
	private void updateImages() {
		for (int i = 0; i < board.acePiles.length; i++) {
			if (board.acePiles[i].isEmpty() == true) {
				switch (board.acePiles[i].getSuit()) {
				case SPADES:
					CardsViews.aces[i]
							.setImageResource(R.drawable.transparent_s_ace);
					break;
				case CLUBS:
					CardsViews.aces[i]
							.setImageResource(R.drawable.transparent_c_ace);
					break;
				case DIAMONDS:
					CardsViews.aces[i]
							.setImageResource(R.drawable.transparent_d_ace);
					break;
				case HEARTS:
					CardsViews.aces[i]
							.setImageResource(R.drawable.transparent_h_ace);
					break;
				}

				continue;
			}

			if (board.acePiles[i].peek().isFaceDown() == true) {
				CardsViews.aces[i].setImageResource(R.drawable.cardback1);
				continue;
			}

			if (board.acePiles[i].peek().isUnhighlighted() == true) {
				CardsViews.aces[i].setImageResource(cardsImagesMapping
						.get(board.acePiles[i].peek()));
			} else if (board.acePiles[i].peek().isHighlighted() == true) {
				CardsViews.aces[i]
						.setImageResource(cardsHighlightedImagesMapping
								.get(board.acePiles[i].peek()));
			}
		}

		for (int i = 0; i < board.cells.length; i++) {
			if (board.cells[i].isEmpty() == true) {
				CardsViews.cells[i].setImageResource(R.drawable.empty_card);
				continue;
			}

			if (board.cells[i].peek().isFaceDown() == true) {
				CardsViews.cells[i].setImageResource(R.drawable.cardback1);
				continue;
			}

			if (board.cells[i].peek().isUnhighlighted() == true) {
				CardsViews.cells[i].setImageResource(cardsImagesMapping
						.get(board.cells[i].peek()));
			} else if (board.cells[i].peek().isHighlighted() == true) {
				CardsViews.cells[i]
						.setImageResource(cardsHighlightedImagesMapping
								.get(board.cells[i].peek()));
			}
		}

		if (board.dealDeck.isEmpty() == false) {
			CardsViews.deal.setImageResource(R.drawable.cardback1);
		} else {
			CardsViews.deal.setImageResource(R.drawable.empty_card);
		}

		if (board.discardPile.isEmpty() == false) {
			if (board.discardPile.peek().isUnhighlighted() == true) {
				CardsViews.discard[0].setImageResource(cardsImagesMapping
						.get(board.discardPile.peek()));
			} else if (board.discardPile.peek().isHighlighted() == true) {
				CardsViews.discard[0]
						.setImageResource(cardsHighlightedImagesMapping
								.get(board.discardPile.peek()));
			}
		} else {
			CardsViews.discard[0].setImageResource(R.drawable.empty_card);
		}

		for (int i = 0; i < board.columns.length; i++) {
			/*
			 * Columns should be clickable even if they are empty.
			 */
			if (board.columns[i].isEmpty() == true) {
				for (ImageView view : CardsViews.columns[i]) {
					view.setImageBitmap(null);
				}
				CardsViews.columns[i][0]
						.setImageResource(R.drawable.empty_card);
				continue;
			}

			for (int j = 0; j < board.columns[i].length(); j++) {
				if (board.columns[i].getCardAtLocation(j).isFaceDown() == true) {
					CardsViews.columns[i][j]
							.setImageResource(R.drawable.cardback1);
					continue;
				}

				if (board.columns[i].getCardAtLocation(j).isUnhighlighted() == true) {
					CardsViews.columns[i][j]
							.setImageResource(cardsImagesMapping
									.get(board.columns[i].getCardAtLocation(j)));
				} else if (board.columns[i].getCardAtLocation(j)
						.isHighlighted() == true) {
					CardsViews.columns[i][j]
							.setImageResource(cardsHighlightedImagesMapping
									.get(board.columns[i].getCardAtLocation(j)));
				}
			}
			for (int j = board.columns[i].length(); j < CardsViews.columns[i].length; j++) {
				CardsViews.columns[i][j].setImageBitmap(null);
			}
		}
	}

	/**
	 * On creation.
	 * 
	 * @param menu
	 * 
	 * @return
	 * 
	 * @author Konstantin Tsanov
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game_option_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * On selection.
	 * 
	 * @param item
	 * 
	 * @return
	 * 
	 * @author Konstantin Tsanov
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.new_game:
			if (board.isSolved() == true) {
				board.newGame(GameState.GAME_WON);
				board.dealOutBoard();
				updateImages();

				break;
			}

			/*
			 * Ask the user for new game confirmation.
			 */
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setTitle("Confirm");
			builder.setMessage("Are you sure?");

			builder.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							board.newGame(GameState.GAME_WON);
							board.dealOutBoard();
							updateImages();
							dialog.dismiss();
						}

					});

			builder.setNegativeButton("NO",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			AlertDialog alert = builder.create();
			alert.show();
			break;

		//
		// case R.id.undo_last_move:
		// board.undoMove();
		// break;
		//
		// case R.id.hint:
		// String hint[] = board.getHint();
		// Toast.makeText(GameActivity.this, hint[0] + " " + hint[1],
		// Toast.LENGTH_SHORT).show();
		// break;
		}
		return true;
	}

	/**
	 * On creation.
	 * 
	 * @param savedInstanceState
	 * 
	 * @author Konstantin Tsanov
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		board.createBoard(null, 1);
		board.newGame(GameState.GAME_LOST);
		board.dealOutBoard();

		cardsImagesMapping.put(Card.valueBy(1), R.drawable.s_ace);
		cardsImagesMapping.put(Card.valueBy(2), R.drawable.s_two);
		cardsImagesMapping.put(Card.valueBy(3), R.drawable.s_three);
		cardsImagesMapping.put(Card.valueBy(4), R.drawable.s_four);
		cardsImagesMapping.put(Card.valueBy(5), R.drawable.s_five);
		cardsImagesMapping.put(Card.valueBy(6), R.drawable.s_six);
		cardsImagesMapping.put(Card.valueBy(7), R.drawable.s_seven);
		cardsImagesMapping.put(Card.valueBy(8), R.drawable.s_eight);
		cardsImagesMapping.put(Card.valueBy(9), R.drawable.s_nine);
		cardsImagesMapping.put(Card.valueBy(10), R.drawable.s_ten);
		cardsImagesMapping.put(Card.valueBy(11), R.drawable.s_jack);
		cardsImagesMapping.put(Card.valueBy(12), R.drawable.s_queen);
		cardsImagesMapping.put(Card.valueBy(13), R.drawable.s_king);
		cardsImagesMapping.put(Card.valueBy(14), R.drawable.c_ace);
		cardsImagesMapping.put(Card.valueBy(15), R.drawable.c_two);
		cardsImagesMapping.put(Card.valueBy(16), R.drawable.c_three);
		cardsImagesMapping.put(Card.valueBy(17), R.drawable.c_four);
		cardsImagesMapping.put(Card.valueBy(18), R.drawable.c_five);
		cardsImagesMapping.put(Card.valueBy(19), R.drawable.c_six);
		cardsImagesMapping.put(Card.valueBy(20), R.drawable.c_seven);
		cardsImagesMapping.put(Card.valueBy(21), R.drawable.c_eight);
		cardsImagesMapping.put(Card.valueBy(22), R.drawable.c_nine);
		cardsImagesMapping.put(Card.valueBy(23), R.drawable.c_ten);
		cardsImagesMapping.put(Card.valueBy(24), R.drawable.c_jack);
		cardsImagesMapping.put(Card.valueBy(25), R.drawable.c_queen);
		cardsImagesMapping.put(Card.valueBy(26), R.drawable.c_king);
		cardsImagesMapping.put(Card.valueBy(27), R.drawable.d_ace);
		cardsImagesMapping.put(Card.valueBy(28), R.drawable.d_two);
		cardsImagesMapping.put(Card.valueBy(29), R.drawable.d_three);
		cardsImagesMapping.put(Card.valueBy(30), R.drawable.d_four);
		cardsImagesMapping.put(Card.valueBy(31), R.drawable.d_five);
		cardsImagesMapping.put(Card.valueBy(32), R.drawable.d_six);
		cardsImagesMapping.put(Card.valueBy(33), R.drawable.d_seven);
		cardsImagesMapping.put(Card.valueBy(34), R.drawable.d_eight);
		cardsImagesMapping.put(Card.valueBy(35), R.drawable.d_nine);
		cardsImagesMapping.put(Card.valueBy(36), R.drawable.d_ten);
		cardsImagesMapping.put(Card.valueBy(37), R.drawable.d_jack);
		cardsImagesMapping.put(Card.valueBy(38), R.drawable.d_queen);
		cardsImagesMapping.put(Card.valueBy(39), R.drawable.d_king);
		cardsImagesMapping.put(Card.valueBy(40), R.drawable.h_ace);
		cardsImagesMapping.put(Card.valueBy(41), R.drawable.h_two);
		cardsImagesMapping.put(Card.valueBy(42), R.drawable.h_three);
		cardsImagesMapping.put(Card.valueBy(43), R.drawable.h_four);
		cardsImagesMapping.put(Card.valueBy(44), R.drawable.h_five);
		cardsImagesMapping.put(Card.valueBy(45), R.drawable.h_six);
		cardsImagesMapping.put(Card.valueBy(46), R.drawable.h_seven);
		cardsImagesMapping.put(Card.valueBy(47), R.drawable.h_eight);
		cardsImagesMapping.put(Card.valueBy(48), R.drawable.h_nine);
		cardsImagesMapping.put(Card.valueBy(49), R.drawable.h_ten);
		cardsImagesMapping.put(Card.valueBy(50), R.drawable.h_jack);
		cardsImagesMapping.put(Card.valueBy(51), R.drawable.h_queen);
		cardsImagesMapping.put(Card.valueBy(52), R.drawable.h_king);

		cardsHighlightedImagesMapping.put(Card.valueBy(1), R.drawable.s_ace_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(2), R.drawable.s_two_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(3), R.drawable.s_three_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(4), R.drawable.s_four_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(5), R.drawable.s_five_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(6), R.drawable.s_six_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(7), R.drawable.s_seven_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(8), R.drawable.s_eight_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(9), R.drawable.s_nine_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(10), R.drawable.s_ten_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(11), R.drawable.s_jack_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(12),
				R.drawable.s_queen_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(13), R.drawable.s_king_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(14), R.drawable.c_ace_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(15), R.drawable.c_two_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(16),
				R.drawable.c_three_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(17), R.drawable.c_four_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(18), R.drawable.c_five_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(19), R.drawable.c_six_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(20),
				R.drawable.c_seven_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(21),
				R.drawable.c_eight_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(22), R.drawable.c_nine_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(23), R.drawable.c_ten_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(24), R.drawable.c_jack_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(25),
				R.drawable.c_queen_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(26), R.drawable.c_king_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(27), R.drawable.d_ace_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(28), R.drawable.d_two_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(29),
				R.drawable.d_three_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(30), R.drawable.d_four_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(31), R.drawable.d_five_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(32), R.drawable.d_six_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(33),
				R.drawable.d_seven_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(34),
				R.drawable.d_eight_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(35), R.drawable.d_nine_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(36), R.drawable.d_ten_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(37), R.drawable.d_jack_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(38),
				R.drawable.d_queen_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(39), R.drawable.d_king_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(40), R.drawable.h_ace_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(41), R.drawable.h_two_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(42),
				R.drawable.h_three_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(43), R.drawable.h_four_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(44), R.drawable.h_five_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(45), R.drawable.h_six_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(46),
				R.drawable.h_seven_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(47),
				R.drawable.h_eight_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(48), R.drawable.h_nine_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(49), R.drawable.h_ten_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(50), R.drawable.h_jack_h);
		cardsHighlightedImagesMapping.put(Card.valueBy(51),
				R.drawable.h_queen_h);
		cardsHighlightedImagesMapping
				.put(Card.valueBy(52), R.drawable.h_king_h);

		CardsViews.aces[0] = ((ImageView) findViewById(R.id.imageView1));
		CardsViews.aces[1] = ((ImageView) findViewById(R.id.imageView2));
		CardsViews.aces[2] = ((ImageView) findViewById(R.id.imageView3));
		CardsViews.aces[3] = ((ImageView) findViewById(R.id.imageView4));
		CardsViews.cells[0] = ((ImageView) findViewById(R.id.imageView5));
		CardsViews.cells[1] = ((ImageView) findViewById(R.id.imageView6));
		CardsViews.cells[2] = ((ImageView) findViewById(R.id.imageView7));
		CardsViews.cells[3] = ((ImageView) findViewById(R.id.imageView8));
		CardsViews.columns[0][0] = ((ImageView) findViewById(R.id.imageView9));
		CardsViews.columns[0][1] = ((ImageView) findViewById(R.id.imageView10));
		CardsViews.columns[0][2] = ((ImageView) findViewById(R.id.imageView11));
		CardsViews.columns[0][3] = ((ImageView) findViewById(R.id.imageView12));
		CardsViews.columns[0][4] = ((ImageView) findViewById(R.id.imageView13));
		CardsViews.columns[0][5] = ((ImageView) findViewById(R.id.imageView14));
		CardsViews.columns[0][6] = ((ImageView) findViewById(R.id.imageView15));
		CardsViews.columns[0][7] = ((ImageView) findViewById(R.id.imageView16));
		CardsViews.columns[0][8] = ((ImageView) findViewById(R.id.imageView17));
		CardsViews.columns[0][9] = ((ImageView) findViewById(R.id.imageView18));
		CardsViews.columns[0][10] = ((ImageView) findViewById(R.id.imageView19));
		CardsViews.columns[0][11] = ((ImageView) findViewById(R.id.imageView20));
		CardsViews.columns[0][12] = ((ImageView) findViewById(R.id.imageView21));
		CardsViews.columns[0][13] = ((ImageView) findViewById(R.id.imageView22));
		CardsViews.columns[0][14] = ((ImageView) findViewById(R.id.imageView23));
		CardsViews.columns[0][15] = ((ImageView) findViewById(R.id.imageView24));
		CardsViews.columns[0][16] = ((ImageView) findViewById(R.id.imageView25));
		CardsViews.columns[1][0] = ((ImageView) findViewById(R.id.imageView26));
		CardsViews.columns[1][1] = ((ImageView) findViewById(R.id.imageView27));
		CardsViews.columns[1][2] = ((ImageView) findViewById(R.id.imageView28));
		CardsViews.columns[1][3] = ((ImageView) findViewById(R.id.imageView29));
		CardsViews.columns[1][4] = ((ImageView) findViewById(R.id.imageView30));
		CardsViews.columns[1][5] = ((ImageView) findViewById(R.id.imageView31));
		CardsViews.columns[1][6] = ((ImageView) findViewById(R.id.imageView32));
		CardsViews.columns[1][7] = ((ImageView) findViewById(R.id.imageView33));
		CardsViews.columns[1][8] = ((ImageView) findViewById(R.id.imageView34));
		CardsViews.columns[1][9] = ((ImageView) findViewById(R.id.imageView35));
		CardsViews.columns[1][10] = ((ImageView) findViewById(R.id.imageView36));
		CardsViews.columns[1][11] = ((ImageView) findViewById(R.id.imageView37));
		CardsViews.columns[1][12] = ((ImageView) findViewById(R.id.imageView38));
		CardsViews.columns[1][13] = ((ImageView) findViewById(R.id.imageView39));
		CardsViews.columns[1][14] = ((ImageView) findViewById(R.id.imageView40));
		CardsViews.columns[1][15] = ((ImageView) findViewById(R.id.imageView41));
		CardsViews.columns[1][16] = ((ImageView) findViewById(R.id.imageView42));
		CardsViews.columns[2][0] = ((ImageView) findViewById(R.id.imageView43));
		CardsViews.columns[2][1] = ((ImageView) findViewById(R.id.imageView44));
		CardsViews.columns[2][2] = ((ImageView) findViewById(R.id.imageView45));
		CardsViews.columns[2][3] = ((ImageView) findViewById(R.id.imageView46));
		CardsViews.columns[2][4] = ((ImageView) findViewById(R.id.imageView47));
		CardsViews.columns[2][5] = ((ImageView) findViewById(R.id.imageView48));
		CardsViews.columns[2][6] = ((ImageView) findViewById(R.id.imageView49));
		CardsViews.columns[2][7] = ((ImageView) findViewById(R.id.imageView50));
		CardsViews.columns[2][8] = ((ImageView) findViewById(R.id.imageView51));
		CardsViews.columns[2][9] = ((ImageView) findViewById(R.id.imageView52));
		CardsViews.columns[2][10] = ((ImageView) findViewById(R.id.imageView53));
		CardsViews.columns[2][11] = ((ImageView) findViewById(R.id.imageView54));
		CardsViews.columns[2][12] = ((ImageView) findViewById(R.id.imageView55));
		CardsViews.columns[2][13] = ((ImageView) findViewById(R.id.imageView56));
		CardsViews.columns[2][14] = ((ImageView) findViewById(R.id.imageView57));
		CardsViews.columns[2][15] = ((ImageView) findViewById(R.id.imageView58));
		CardsViews.columns[2][16] = ((ImageView) findViewById(R.id.imageView59));
		CardsViews.columns[3][0] = ((ImageView) findViewById(R.id.imageView60));
		CardsViews.columns[3][1] = ((ImageView) findViewById(R.id.imageView61));
		CardsViews.columns[3][2] = ((ImageView) findViewById(R.id.imageView62));
		CardsViews.columns[3][3] = ((ImageView) findViewById(R.id.imageView63));
		CardsViews.columns[3][4] = ((ImageView) findViewById(R.id.imageView64));
		CardsViews.columns[3][5] = ((ImageView) findViewById(R.id.imageView65));
		CardsViews.columns[3][6] = ((ImageView) findViewById(R.id.imageView66));
		CardsViews.columns[3][7] = ((ImageView) findViewById(R.id.imageView67));
		CardsViews.columns[3][8] = ((ImageView) findViewById(R.id.imageView68));
		CardsViews.columns[3][9] = ((ImageView) findViewById(R.id.imageView69));
		CardsViews.columns[3][10] = ((ImageView) findViewById(R.id.imageView70));
		CardsViews.columns[3][11] = ((ImageView) findViewById(R.id.imageView71));
		CardsViews.columns[3][12] = ((ImageView) findViewById(R.id.imageView72));
		CardsViews.columns[3][13] = ((ImageView) findViewById(R.id.imageView73));
		CardsViews.columns[3][14] = ((ImageView) findViewById(R.id.imageView74));
		CardsViews.columns[3][15] = ((ImageView) findViewById(R.id.imageView75));
		CardsViews.columns[3][16] = ((ImageView) findViewById(R.id.imageView76));
		CardsViews.deal = ((ImageView) findViewById(R.id.imageView77));
		CardsViews.discard[0] = ((ImageView) findViewById(R.id.imageView78));
		CardsViews.discard[1] = ((ImageView) findViewById(R.id.imageView79));
		CardsViews.discard[2] = ((ImageView) findViewById(R.id.imageView80));
		ImageView all[] = { CardsViews.aces[0], CardsViews.aces[1],
				CardsViews.aces[2], CardsViews.aces[3], CardsViews.cells[0],
				CardsViews.cells[1], CardsViews.cells[2], CardsViews.cells[3],
				CardsViews.columns[0][0], CardsViews.columns[0][1],
				CardsViews.columns[0][2], CardsViews.columns[0][3],
				CardsViews.columns[0][4], CardsViews.columns[0][5],
				CardsViews.columns[0][6], CardsViews.columns[0][7],
				CardsViews.columns[0][8], CardsViews.columns[0][9],
				CardsViews.columns[0][10], CardsViews.columns[0][11],
				CardsViews.columns[0][12], CardsViews.columns[0][13],
				CardsViews.columns[0][14], CardsViews.columns[0][15],
				CardsViews.columns[0][16], CardsViews.columns[1][0],
				CardsViews.columns[1][1], CardsViews.columns[1][2],
				CardsViews.columns[1][3], CardsViews.columns[1][4],
				CardsViews.columns[1][5], CardsViews.columns[1][6],
				CardsViews.columns[1][7], CardsViews.columns[1][8],
				CardsViews.columns[1][9], CardsViews.columns[1][10],
				CardsViews.columns[1][11], CardsViews.columns[1][12],
				CardsViews.columns[1][13], CardsViews.columns[1][14],
				CardsViews.columns[1][15], CardsViews.columns[1][16],
				CardsViews.columns[2][0], CardsViews.columns[2][1],
				CardsViews.columns[2][2], CardsViews.columns[2][3],
				CardsViews.columns[2][4], CardsViews.columns[2][5],
				CardsViews.columns[2][6], CardsViews.columns[2][7],
				CardsViews.columns[2][8], CardsViews.columns[2][9],
				CardsViews.columns[2][10], CardsViews.columns[2][11],
				CardsViews.columns[2][12], CardsViews.columns[2][13],
				CardsViews.columns[2][14], CardsViews.columns[2][15],
				CardsViews.columns[2][16], CardsViews.columns[3][0],
				CardsViews.columns[3][1], CardsViews.columns[3][2],
				CardsViews.columns[3][3], CardsViews.columns[3][4],
				CardsViews.columns[3][5], CardsViews.columns[3][6],
				CardsViews.columns[3][7], CardsViews.columns[3][8],
				CardsViews.columns[3][9], CardsViews.columns[3][10],
				CardsViews.columns[3][11], CardsViews.columns[3][12],
				CardsViews.columns[3][13], CardsViews.columns[3][14],
				CardsViews.columns[3][15], CardsViews.columns[3][16],
				CardsViews.deal, CardsViews.discard[0], CardsViews.discard[1],
				CardsViews.discard[2] };
		CardsViews.all = all;
		resizeImageViews();

		((ImageView) findViewById(R.id.imageView100))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						GameActivity.this.startActivity(new Intent(
								Intent.ACTION_VIEW, Uri.parse(getResources()
										.getString(R.string.ebinqo_url))));

					}
				});

		OnClickListener onClick = new OnClickListener() {
			public void onClick(View view) {
				/*
				 * Deal deck only flip cards. There is no selection.
				 */
				if (view == CardsViews.deal) {
					board.clearHighlighting();

					board.numCardsInDiscardView.add(board.discardPile
							.getNumViewableCards());
					Card clickedCard = board.dealDeck.pop();

					if (clickedCard != null) {
						board.sourceList.add(board.dealDeck);
						board.destinationList.add(board.discardPile);
						board.numCards.add(board.discardPile
								.getNumViewableCards());
					} else if (board.dealDeck.hasDealsLeft() == true) {
						/*
						 * The deck was reset but the player hasn't used up the
						 * times through the deck.
						 */
						board.sourceList.add(board.dealDeck);
						board.destinationList.add(board.discardPile);
						board.numCards.add(0);
					} else {
						board.numCardsInDiscardView.removeLast();
					}
				}

				/*
				 * Mark any card on the board as highlighted.
				 */
				int numberOfSelectedCards = board.highlightedCards();
				if (numberOfSelectedCards == 0) {
					for (int i = 0; i < CardsViews.aces.length; i++) {
						if (view != CardsViews.aces[i]) {
							continue;
						}

						board.acePiles[i].highlight(0);
						break;
					}

					for (int i = 0; i < CardsViews.cells.length; i++) {
						if (view != CardsViews.cells[i]) {
							continue;
						}

						board.cells[i].highlight(0);
						break;
					}

					for (int i = 0; i < CardsViews.columns.length; i++) {
						for (int j = 0; j < CardsViews.columns[i].length; j++) {
							if (view != CardsViews.columns[i][j]) {
								continue;
							}

							board.columns[i].highlight(j);
							break;
						}
					}

					for (int i = 0; i < CardsViews.discard.length; i++) {
						if (view != CardsViews.discard[i]) {
							continue;
						}

						board.discardPile.highlight(0);
						break;
					}
				}
				/*
				 * Singled card or many cards is/are highlighted.
				 */
				else {
					for (int i = 0; i < CardsViews.aces.length; i++) {
						if (view != CardsViews.aces[i]) {
							continue;
						}

						board.moveToAces(i, numberOfSelectedCards);
						break;
					}

					for (int i = 0; i < CardsViews.cells.length; i++) {
						if (view != CardsViews.cells[i]) {
							continue;
						}

						board.moveToCells(i, numberOfSelectedCards);
						break;
					}

					for (int i = 0; i < CardsViews.columns.length; i++) {
						for (int j = 0; j < CardsViews.columns[i].length; j++) {
							if (view != CardsViews.columns[i][j]) {
								continue;
							}

							board.moveToColumns(i, numberOfSelectedCards);
							i = CardsViews.columns.length;
							break;
						}
					}

					board.clearHighlighting();
				}

				if (board.isSolved() == true) {
					Toast.makeText(GameActivity.this, "You Win!",
							Toast.LENGTH_LONG).show();
				}

				updateImages();
			}
		};

		for (ImageView view : CardsViews.all) {
			view.setOnClickListener(onClick);
		}

		updateImages();
	}
}
