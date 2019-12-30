package waycare;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonUtils {

    private static int eventsCounter = 1;

    public static String createResponseAsString(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public static void parseCurrentJson(String jsonAsString, String currentDate) {
        try {
            JSONObject jsonObject = (JSONObject) ((JSONObject) JSONValue.parseWithException(jsonAsString)).get("_embedded");
            if (jsonObject != null) {
                JSONArray eventsArray = (JSONArray) jsonObject.get("events");
                if (eventsArray.size() != 0) {
                    for (int i = 0; i < eventsArray.size(); i++) {
                        JSONObject eventData = (JSONObject) eventsArray.get(i);
                        JSONObject eventStart = (JSONObject) ((JSONObject) eventData.get("dates")).get("start");
                        System.out.println("Event №:" + eventsCounter + "\n" + eventData.get("name") + "\nDate: " + eventStart.get("localDate") + "; Time: " + eventStart.get("localTime"));
                        System.out.println();
                        eventsCounter++;
//                        if (eventStart.get("localDate").equals(currentDate)) {
//                            System.out.println("Event №:" + eventsCounter + "\n" + eventData.get("name") + "\nDate: " + eventStart.get("localDate") + "; Time: " + eventStart.get("localTime"));
//                            System.out.println();
//                            eventsCounter++;
//                        }
                    }
                } else {
                    System.out.println("There are no events!");
                }
            } else {
                System.out.println("Something wrong! Probably out of rate limits!");
            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }
}