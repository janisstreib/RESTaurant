package com.example.test;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends Activity {
	private static final String VAR_STATE = "tolleVariable";
	private int tolleVariable = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	// Speicherung in Key-Value-Paaren
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(VAR_STATE, tolleVariable);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		tolleVariable = savedInstanceState.getInt(VAR_STATE);
		super.onRestoreInstanceState(savedInstanceState);
	}
}
