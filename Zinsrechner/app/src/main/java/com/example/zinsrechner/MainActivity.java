package com.example.zinsrechner;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText kapital;
	private EditText zinssatz;
	private EditText laufzeit;
	private TextView jahre;
	private double oldKapital = 0;
	private double oldZinssatz = 0;
	private int oldLaufzeit = 0;
	private int summeJahre = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		kapital = (EditText) findViewById(R.id.kapital);
		zinssatz = (EditText) findViewById(R.id.zinssatz);
		laufzeit = (EditText) findViewById(R.id.laufzeit);
		jahre = (TextView) findViewById(R.id.jahre);
		Button berechnen = (Button) findViewById(R.id.berechnen);
		berechnen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					boolean isNew = false;
					double k = Double.parseDouble(kapital.getText().toString());
					if (k != oldKapital) {
						isNew = true;
					}
					double z = Double
							.parseDouble(zinssatz.getText().toString());
					if (z != oldZinssatz) {
						isNew = true;
						oldZinssatz = z;
					}
					int l = Integer.parseInt(laufzeit.getText().toString());
					if (l != oldLaufzeit) {
						isNew = true;
						oldLaufzeit = l;
					}
					// Kapital linear ausrechnen und auf zwei Nchkommastellen
					// runden
					double newKapital = Math
							.round(((k + k * (z / 100) * l)) * 100) / 100d;
					oldKapital = newKapital;
					kapital.setText(newKapital + "");
					if (isNew) {
						summeJahre = 0;
					}
					summeJahre += l;
					jahre.setText(summeJahre + " Jahre");
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