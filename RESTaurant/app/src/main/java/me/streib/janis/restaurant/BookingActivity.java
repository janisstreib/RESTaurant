package me.streib.janis.restaurant;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import me.streib.janis.restaurant.objects.Booking;
import me.streib.janis.restaurant.objects.BookingException;

public class BookingActivity extends AppCompatActivity {
	private EditText etName;
	private TextView tvStart;
	private TextView tvEnd;
	private EditText etSeats;
	private CheckBox chkWindow;
	private Button btnReserve;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		etName = findViewById(R.id.etName);
		tvStart = findViewById(R.id.tvStart);
		tvEnd = findViewById(R.id.tvEnd);
		etSeats = findViewById(R.id.etSeats);
		chkWindow = findViewById(R.id.chk_window);
		btnReserve = findViewById(R.id.btnReserve);
		btnReserve.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new AsyncTask<Void, Void, Booking>() {
					@Override
					protected Booking doInBackground(Void... voids) {
						try {
							// TODO: Datum parsen
							return Booking.book("http://10.0.0.1:8081/", null, null, chkWindow.isChecked(), etName.getText().toString());
						} catch (IOException e) {
							e.printStackTrace();
						} catch (BookingException e) {
							e.printStackTrace();
						}
						return null;
					}

					@Override
					protected void onPostExecute(Booking booking) {
						super.onPostExecute(booking);
						// Todo: Booking speichern
						finish();
					}
				}.execute();
			}
		});

	}
}
