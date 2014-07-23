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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 
 * @author Konstantin Tsanov
 */
public class GameActivity extends Activity {
	// TODO Find better object model.

	/**
	 * 
	 */
	private SolitaireBoard board = null;// new SolitaireBoard();

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
			/*
			 * Ask the user for new game confirmation.
			 */
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setTitle("Confirm");
			builder.setMessage("Are you sure?");

			builder.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// board.recordGame(GameState.GAME_LOST, 0, 0, 0, 0,
							// false);
							// board.newGame(GameState.GAME_LOST);
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
		case R.id.undo_last_move:
			// board.undoMove();
			break;
		case R.id.hint:
			// String hint[] = board.getHint();
			// Toast.makeText(GameActivity.this, hint[0] + " " + hint[1],
			// Toast.LENGTH_SHORT).show();
			break;
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
				switch (view.getId()) {
				case R.id.imageView1:
					Toast.makeText(GameActivity.this, "	1	", Toast.LENGTH_SHORT)
							.show();
					break;
				case R.id.imageView2:
					Toast.makeText(GameActivity.this, "	2	", Toast.LENGTH_SHORT)
							.show();
					break;
				case R.id.imageView3:
					Toast.makeText(GameActivity.this, "	3	", Toast.LENGTH_SHORT)
							.show();
					break;
				case R.id.imageView4:
					Toast.makeText(GameActivity.this, "	4	", Toast.LENGTH_SHORT)
							.show();
					break;
				case R.id.imageView5:
					Toast.makeText(GameActivity.this, "	5	", Toast.LENGTH_SHORT)
							.show();
					break;
				case R.id.imageView6:
					Toast.makeText(GameActivity.this, "	6	", Toast.LENGTH_SHORT)
							.show();
					break;
				case R.id.imageView7:
					Toast.makeText(GameActivity.this, "	7	", Toast.LENGTH_SHORT)
							.show();
					break;
				case R.id.imageView8:
					Toast.makeText(GameActivity.this, "	8	", Toast.LENGTH_SHORT)
							.show();
					break;
				case R.id.imageView9:
					Toast.makeText(GameActivity.this, "	9	", Toast.LENGTH_SHORT)
							.show();
					break;
				case R.id.imageView10:
					Toast.makeText(GameActivity.this, "	10	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView11:
					Toast.makeText(GameActivity.this, "	11	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView12:
					Toast.makeText(GameActivity.this, "	12	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView13:
					Toast.makeText(GameActivity.this, "	13	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView14:
					Toast.makeText(GameActivity.this, "	14	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView15:
					Toast.makeText(GameActivity.this, "	15	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView16:
					Toast.makeText(GameActivity.this, "	16	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView17:
					Toast.makeText(GameActivity.this, "	17	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView18:
					Toast.makeText(GameActivity.this, "	18	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView19:
					Toast.makeText(GameActivity.this, "	19	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView20:
					Toast.makeText(GameActivity.this, "	20	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView21:
					Toast.makeText(GameActivity.this, "	21	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView22:
					Toast.makeText(GameActivity.this, "	22	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView23:
					Toast.makeText(GameActivity.this, "	23	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView24:
					Toast.makeText(GameActivity.this, "	24	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView25:
					Toast.makeText(GameActivity.this, "	25	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView26:
					Toast.makeText(GameActivity.this, "	26	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView27:
					Toast.makeText(GameActivity.this, "	27	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView28:
					Toast.makeText(GameActivity.this, "	28	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView29:
					Toast.makeText(GameActivity.this, "	29	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView30:
					Toast.makeText(GameActivity.this, "	30	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView31:
					Toast.makeText(GameActivity.this, "	31	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView32:
					Toast.makeText(GameActivity.this, "	32	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView33:
					Toast.makeText(GameActivity.this, "	33	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView34:
					Toast.makeText(GameActivity.this, "	34	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView35:
					Toast.makeText(GameActivity.this, "	35	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView36:
					Toast.makeText(GameActivity.this, "	36	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView37:
					Toast.makeText(GameActivity.this, "	37	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView38:
					Toast.makeText(GameActivity.this, "	38	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView39:
					Toast.makeText(GameActivity.this, "	39	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView40:
					Toast.makeText(GameActivity.this, "	40	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView41:
					Toast.makeText(GameActivity.this, "	41	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView42:
					Toast.makeText(GameActivity.this, "	42	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView43:
					Toast.makeText(GameActivity.this, "	43	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView44:
					Toast.makeText(GameActivity.this, "	44	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView45:
					Toast.makeText(GameActivity.this, "	45	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView46:
					Toast.makeText(GameActivity.this, "	46	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView47:
					Toast.makeText(GameActivity.this, "	47	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView48:
					Toast.makeText(GameActivity.this, "	48	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView49:
					Toast.makeText(GameActivity.this, "	49	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView50:
					Toast.makeText(GameActivity.this, "	50	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView51:
					Toast.makeText(GameActivity.this, "	51	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView52:
					Toast.makeText(GameActivity.this, "	52	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView53:
					Toast.makeText(GameActivity.this, "	53	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView54:
					Toast.makeText(GameActivity.this, "	54	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView55:
					Toast.makeText(GameActivity.this, "	55	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView56:
					Toast.makeText(GameActivity.this, "	56	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView57:
					Toast.makeText(GameActivity.this, "	57	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView58:
					Toast.makeText(GameActivity.this, "	58	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView59:
					Toast.makeText(GameActivity.this, "	59	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView60:
					Toast.makeText(GameActivity.this, "	60	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView61:
					Toast.makeText(GameActivity.this, "	61	",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.imageView62:
					Toast.makeText(GameActivity.this, "	62	",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};

		((ImageView) findViewById(R.id.imageView1)).setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView2)).setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView3)).setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView4)).setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView5)).setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView6)).setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView7)).setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView8)).setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView9)).setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView10))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView11))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView12))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView13))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView14))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView15))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView16))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView17))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView18))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView19))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView20))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView21))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView22))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView23))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView24))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView25))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView26))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView27))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView28))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView29))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView30))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView31))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView32))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView33))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView34))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView35))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView36))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView37))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView38))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView39))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView40))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView41))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView42))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView43))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView44))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView45))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView46))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView47))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView48))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView49))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView50))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView51))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView52))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView53))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView54))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView55))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView56))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView57))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView58))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView59))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView60))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView61))
				.setOnClickListener(onClick);
		((ImageView) findViewById(R.id.imageView62))
				.setOnClickListener(onClick);
	}
}
