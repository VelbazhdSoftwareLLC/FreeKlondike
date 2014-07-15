package eu.veldsoft.four.row.solitaire;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class StatisticsActivity extends TabActivity {

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
