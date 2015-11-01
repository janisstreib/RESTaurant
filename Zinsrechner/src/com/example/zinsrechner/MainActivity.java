package com.example.zinsrechner;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText kapital;
	private EditText zinsSatz;
	private EditText laufZeit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		kapital = (EditText) findViewById(R.id.kapital);
		zinsSatz = (EditText) findViewById(R.id.zinssatz);
		laufZeit = (EditText) findViewById(R.id.laufzeit);
		Button berechnen = (Button) findViewById(R.id.berechnen);
		berechnen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					double k = Double.parseDouble(kapital.getText().toString());
					double z = Double
							.parseDouble(zinsSatz.getText().toString());
					int l = Integer.parseInt(laufZeit.getText().toString());
					kapital.setText(Math.round(((k + k * (z / 100) * l)) * 100)
							/ 100d + "");
				} catch (NumberFormatException e) {
					Toast.makeText(
							getApplicationContext(),
							"Bitte geben Sie in alle Felder gültige Werte ein!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}
}
