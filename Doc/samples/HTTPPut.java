HttpURLConnection connection = (HttpURLConnection) new URL(
        "http://example.com/put_thing").openConnection();
connection.setDoOutput(true);
connection.setRequestMethod("PUT");
connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
OutputStreamWriter out = new OutputStreamWriter(
    httpCon.getOutputStream());
out.write("key=value&key2=value2");
out.close();
BufferedReader read = new BufferedReader(new InputStreamReader(
            connection.getInputStream()));
// Get reponse code
int responseCode = connection.getResponseCode();
// Read Response...
read.close();

