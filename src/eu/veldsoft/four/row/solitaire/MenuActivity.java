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

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * @author Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov
 */
public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		((Button) findViewById(R.id.new_game_button))
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MenuActivity.this.startActivity(new Intent(
						MenuActivity.this, GameActivity.class));
			}
		});
		
		((Button) findViewById(R.id.statistics_button))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						MenuActivity.this.startActivity(new Intent(
								MenuActivity.this, StatisticsActivity.class));
					}
				});

		((Button) findViewById(R.id.best_times_button))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						MenuActivity.this.startActivity(new Intent(
								MenuActivity.this, BestTimesActivity.class));
					}
				});

		((Button) findViewById(R.id.settings_button))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						MenuActivity.this.startActivity(new Intent(
								MenuActivity.this, SettingsActivity.class));
					}
				});

		((Button) findViewById(R.id.view_help_button))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						MenuActivity.this.startActivity(new Intent(
								MenuActivity.this, HelpActivity.class));
					}
				});

		((Button) findViewById(R.id.about_game_button))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						MenuActivity.this.startActivity(new Intent(
								MenuActivity.this, AboutGameActivity.class));
					}
				});

		((Button) findViewById(R.id.check_for_updates_button))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						MenuActivity.this.startActivity(new Intent(
								Intent.ACTION_VIEW,
								Uri.parse(getResources().getString(
										R.string.check_for_updates_url))));
					}
				});

		((Button) findViewById(R.id.exit_button))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
						System.exit(0);
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
