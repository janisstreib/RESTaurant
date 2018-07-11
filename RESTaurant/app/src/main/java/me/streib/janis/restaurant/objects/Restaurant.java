package me.streib.janis.restaurant.objects;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;

public class Restaurant {
	private EatType eatType;
	private String owner;
	private String name;
	private boolean isAccessible;
	private int parkingSpace;
	private int id;

	public Restaurant(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			switch (name) {
				case "id":
					this.id = reader.nextInt();
					break;
				case "owner":
					this.owner = reader.nextString();
					break;
				case "accessible":
					this.isAccessible = reader.nextBoolean();
					break;
				case "name":
					this.name = reader.nextString();
					break;
				case "parkingSpace":
					this.parkingSpace = reader.nextInt();
					break;
				case "eatType":
					this.eatType = EatType.valueOf(reader.nextString());
					break;
				default:
					Log.d("API", "Unknown restaurant attribute " + name);
					break;
			}
		}
		reader.endObject();
	}

	public EatType getEatType() {
		return eatType;
	}

	public void setEatType(EatType eatType) {
		this.eatType = eatType;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAccessible() {
		return isAccessible;
	}

	public void setAccessible(boolean accessible) {
		isAccessible = accessible;
	}

	public int getParkingSpace() {
		return parkingSpace;
	}

	public void setParkingSpace(int parkingSpace) {
		this.parkingSpace = parkingSpace;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
