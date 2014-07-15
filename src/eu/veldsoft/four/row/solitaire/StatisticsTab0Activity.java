package eu.veldsoft.four.row.solitaire;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class StatisticsTab0Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics_tab0);

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
						Toast.makeText(getApplicationContext(), "Reset 1!",
								Toast.LENGTH_SHORT).show();
					}
				});
	}
}
