package me.streib.janis.restaurant.objects;

import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class Booking {
	private long id;
	private Date begin, end;
	private String name;
	private Table table;
	private Restaurant restaurant;
	private boolean canceled = false;

	public static Booking book(String baseUrl, Date begin, Date end, boolean atWindow, String name) throws IOException, BookingException {
		// TODO: static restaurant for now. Implement, if there is time
		HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl+"/booking/restaurant/"+1).openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		OutputStreamWriter out = new OutputStreamWriter(
				connection.getOutputStream());
		out.write("begin=" + begin.getTime() / 1000d + "&end=" + end.getTime() / 1000d + "&name=" + name + "&atWindow=" + (atWindow ? "true" : "false"));
		out.close();
		BufferedReader read = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		// Get reponse code
		int responseCode = connection.getResponseCode();
		if (responseCode == 409) {
			return null;
		} else if(responseCode == 401) {
			throw new BookingException("Table not existant!");
		} else if(responseCode != 201) {
			throw new BookingException("Error in request. Responsecode: "+ responseCode);
		}
		JsonReader jsonReader = new JsonReader(read);
		Booking res = new Booking(jsonReader);
		// Read Response...
		jsonReader.close();
		read.close();
		return res;
	}

	public Booking(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			switch (name) {
				case "id":
					this.id = reader.nextLong();
					break;
				case "begin":
					this.begin = new Date(reader.nextLong());
					break;
				case "end":
					this.end = new Date(reader.nextLong());
					break;
				case "name":
					this.name = reader.nextString();
					break;
				case "table":
					this.table = new Table(reader);
					break;
				case "restaurant":
					this.restaurant = new Restaurant(reader);
				default:
					Log.d("API", "Unknown booking attribute " + name);
					break;
			}
		}
		reader.endObject();
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public long getId() {
		return id;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin, String baseUrl) throws BookingException {
		if(canceled){
			throw new BookingException("Booking canceled!");
		}
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end, String baseUrl) throws BookingException {
		if(canceled){
			throw new BookingException("Booking canceled!");
		}
		this.end = end;
	}

	public String getName() {
		return name;
	}

	public void setName(String name, String baseUrl) throws BookingException {
		if(canceled){
			throw new BookingException("Booking canceled!");
		}
		this.name = name;
	}

	public Table getTable() {
		return table;
	}

	public void cancel(String baseUrl) throws IOException, BookingException {
		if(canceled) {
			throw new BookingException("Already canceled!");
		}
		HttpURLConnection connection = (HttpURLConnection) new URL(
				baseUrl+"/booking/"+this.id).openConnection();
		connection.setRequestMethod("DELETE");
		BufferedReader read = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		int responseCode = connection.getResponseCode();
		read.close();
		this.canceled = true;
	}
}
