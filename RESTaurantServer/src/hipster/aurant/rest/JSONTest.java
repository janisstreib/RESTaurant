package hipster.aurant.rest;

import hipster.aurant.rest.dbobjects.Restaurant;
import hipster.aurant.rest.dbobjects.Table;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Date;

public class JSONTest {
	public static void main(String[] args) throws MalformedURLException,
			IOException, SQLException {
		DatabaseConnection.init(new RESTAurantConfiguration(
				new FileInputStream("conf/node.properties")));
		String name = "Peter";
		Table table = Table.getById(1, Restaurant.getById(1));
		Date begin = new Date();
		HttpURLConnection connection = (HttpURLConnection) new URL(
				"http://localhost:8081/booking/restaurant/1?name="
						+ URLEncoder.encode(name, "UTF-8") + "&begin="
						+ begin.getTime() + "&table=" + 1).openConnection();
		connection.setRequestMethod("PUT");
		connection.setDoOutput(true);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				connection.getOutputStream()));

		writer.write("name=" + URLEncoder.encode(name, "UTF-8") + "&begin="
				+ begin.getTime() + "&table=" + 1);
		writer.close();
		// key=value&key2=value2
		// Hans & Peter
		// key=Hand & Peter
		InputStream inputStream = connection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader read = new BufferedReader(inputStreamReader);
		String result = "";
		String zeile = read.readLine();
		while (zeile != null) {
			result += zeile;
			zeile = read.readLine();
		}
		read.close();

	}
}
