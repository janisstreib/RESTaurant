package me.streib.janis.restaurant;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import me.streib.janis.restaurant.objects.Booking;
import me.streib.janis.restaurant.objects.BookingException;

public class BookingActivity extends AppCompatActivity {
	public static final String STORE_START_DATE = "startDate";
	public static final String STORE_END_DATE = "endDate";
	private EditText etName;
	private TextView tvStartTime;
	private TextView tvEndTime;
	private TextView tvStartDate;
	private EditText etSeats;
	private CheckBox chkWindow;
	private Button btnReserve;
	private Button btnSelectStartDate;
	private Button btnSelectStartTime;
	private Button btnSelectEndTime;
	private Calendar startDate = new GregorianCalendar(), endDate = new GregorianCalendar();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		btnSelectStartDate = findViewById(R.id.start_date_picker);
		btnSelectEndTime = findViewById(R.id.end_time_picker);
		btnSelectStartTime = findViewById(R.id.start_time_picker);
		etName = findViewById(R.id.etName);
		tvStartDate = findViewById(R.id.tvStart);
		tvStartTime = findViewById(R.id.tv_start_time);
		tvEndTime = findViewById(R.id.tv_end_time);
		etSeats = findViewById(R.id.etSeats);
		chkWindow = findViewById(R.id.chk_window);
		btnReserve = findViewById(R.id.btnReserve);
		updateDate();
		// Kalender holen
		Calendar c = Calendar.getInstance();

		final TimePickerDialog tPStart = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker timePicker, int hour, int minute) {
				startDate.set(Calendar.HOUR, hour);
				startDate.set(Calendar.MINUTE, minute);
				updateDate();
			}
		}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
		btnSelectStartTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				tPStart.show();
			}
		});

		final TimePickerDialog tPEnd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker timePicker, int hour, int minute) {
				endDate.set(Calendar.HOUR, hour);
				endDate.set(Calendar.MINUTE, minute);
				tvEndTime.setText(hour + ":" + minute);
			}
		}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
		btnSelectEndTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				tPEnd.show();
			}
		});

		// DatePickerDialog erzeugen
		final DatePickerDialog dPStart = new DatePickerDialog(this, // Die Activity, die den Dialog starten soll
				new DatePickerDialog.OnDateSetListener() { // Der Listener, wenn ein Datum eingegeben wurde
					@Override
					public void onDateSet(DatePicker datePicker, int year, int month, int day) {
						startDate.set(Calendar.YEAR, year);
						startDate.set(Calendar.MONTH, month);
						startDate.set(Calendar.DAY_OF_MONTH, day);
						endDate.set(Calendar.YEAR, year);
						endDate.set(Calendar.MONTH, month);
						endDate.set(Calendar.DAY_OF_MONTH, day);
						updateDate();
					}
				},
				c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)); // Startdatum

		btnSelectStartDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Dialog anzeigen
				dPStart.show();
			}
		});

		btnReserve.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new AsyncTask<Void, Void, Booking>() {
					@Override
					protected Booking doInBackground(Void... voids) {
						try {
							// TODO: Datum parsen
							// TODO: Richtiger Server
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

	private void updateDate() {
		DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this);
		DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(this);
		tvStartDate.setText(dateFormat.format(startDate.getTime()));
		tvStartTime.setText(timeFormat.format(endDate));
		tvEndTime.setText(timeFormat.format(endDate.getTime()));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putLong(STORE_START_DATE, startDate.getTimeInMillis());
		outState.putLong(STORE_END_DATE, endDate.getTimeInMillis());
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		startDate.setTimeInMillis(savedInstanceState.getLong(STORE_START_DATE));
		endDate.setTimeInMillis(savedInstanceState.getLong(STORE_END_DATE));
	}
}
