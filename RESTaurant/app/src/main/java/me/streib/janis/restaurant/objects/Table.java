package me.streib.janis.restaurant.objects;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;

public class Table {
	private int id;
	private int seats;
	private boolean atWindow;

	public Table(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			switch (name) {
				case "id":
					this.id = reader.nextInt();
					break;
				case "atWindow":
					this.atWindow = reader.nextBoolean();
					break;
				case "seats":
					this.seats = reader.nextInt();
					break;
				default:
					Log.d("API", "Unknown table attribute " + name);
					break;
			}
		}
		reader.endObject();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public boolean isAtWindow() {
		return atWindow;
	}

	public void setAtWindow(boolean atWindow) {
		this.atWindow = atWindow;
	}
}
