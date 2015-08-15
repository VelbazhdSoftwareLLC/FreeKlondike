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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import eu.veldsoft.free.klondike.R;

/**
 * 
 * @author Ina Baltadzhieva
 */
public class StatisticsTab4Activity extends Activity {

	/**
	 * On creation.
	 * 
	 * @param savedInstanceState
	 * 
	 * @author Ina Baltadzhieva
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics_tab4);

		((Button) findViewById(R.id.ok_statistics))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
		((Button) findViewById(R.id.reset_statistics))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(getApplicationContext(), "Reset 5!",
								Toast.LENGTH_SHORT).show();
					}
				});
	}
}
