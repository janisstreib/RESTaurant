HttpURLConnection connection = (HttpURLConnection) new URL(
        "http://example.com/get_thing?key=value").openConnection();
BufferedReader read = new BufferedReader(new InputStreamReader(
            connection.getInputStream()));
// Get reponse code
int responseCode = connection.getResponseCode();
// Put some code here...
read.close();

