package com.example.test;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends Activity {
	private static final String TEXT_INPUT_STATE = "textInput1";
	private EditText input;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		input = (EditText) findViewById(R.id.editText1);
	}
	// Speicherung in Key-Value-Paaren
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(TEXT_INPUT_STATE, input.getText().toString());
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		input.setText(savedInstanceState.getString(TEXT_INPUT_STATE));
		super.onRestoreInstanceState(savedInstanceState);
	}
}
