HttpURLConnection connection = (HttpURLConnection) new URL(
        "http://example.com/delete/thing").openConnection();
connection.setRequestMethod("DELETE");
BufferedReader read = new BufferedReader(new InputStreamReader(
            connection.getInputStream()));
// Get reponse code
int responseCode = connection.getResponceCode();
// Put some code here...
read.close();

