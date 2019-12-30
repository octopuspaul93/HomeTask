package waycare;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    private static final String LINK = "https://app.ticketmaster.com/discovery/v2/events.json?page=%d&city=%s&apikey=%s";
    private static final String CITY = "Manchester";
    private static final String API_KEY = "QRk0JdS0KyfBvAAjYzeaPmcAMWTEU4V1";
    private static final String CURRENT_DATE = new SimpleDateFormat("y-MM-d").format(new Date());
    private static final String DATE = "2020-03-28";

    public static void main(String[] args) {
        int i = 0;
        while (i < 50) {
            try {
                JsonUtils.parseCurrentJson(JsonUtils.createResponseAsString(String.format(LINK, i, CITY, API_KEY)), DATE);
                i++;
            } catch (IOException ignored) {
            }
        }
    }
}