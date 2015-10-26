//Imports zur Platzersparnis weggelassen
public class WebsiteDownloader {
	public static void main(String[] args) {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(
					"http://google.com").openConnection();
			File f = new File("output.html");
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			BufferedReader read = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String tmp;
			// Lese so lange, bis das Ende des Streams ereicht ist
			// (readLine gibt null zurueck, wenn das der Fall ist)
			while ((tmp = read.readLine()) != null) {
				writer.write(tmp + "\n");
			}
			// Cleanup
			writer.close();
			read.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
