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

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/**
 * Manages the "Statistics" android menu.
 * 
 * @author Todor Balabanov
 */
public class StatisticsActivity extends TabActivity {

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
		setContentView(R.layout.activity_statistics);

		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

		TabSpec tab0 = tabHost.newTabSpec("Tab0");
		TabSpec tab1 = tabHost.newTabSpec("Tab1");
		TabSpec tab2 = tabHost.newTabSpec("Tab2");
		TabSpec tab3 = tabHost.newTabSpec("Tab3");
		TabSpec tab4 = tabHost.newTabSpec("Tab4");
		TabSpec tab5 = tabHost.newTabSpec("Tab5");

		tab0.setIndicator("1E");
		tab0.setContent(new Intent(this, StatisticsTab0Activity.class));

		tab1.setIndicator("3E");
		tab1.setContent(new Intent(this, StatisticsTab1Activity.class));

		tab2.setIndicator("1M");
		tab2.setContent(new Intent(this, StatisticsTab2Activity.class));

		tab3.setIndicator("3M");
		tab3.setContent(new Intent(this, StatisticsTab3Activity.class));

		tab4.setIndicator("1H");
		tab4.setContent(new Intent(this, StatisticsTab4Activity.class));

		tab5.setIndicator("3H");
		tab5.setContent(new Intent(this, StatisticsTab5Activity.class));

		tabHost.addTab(tab0);
		tabHost.addTab(tab1);
		tabHost.addTab(tab2);
		tabHost.addTab(tab3);
		tabHost.addTab(tab4);
		tabHost.addTab(tab5);
	}
}
