package fedorov.home.MyAnimeBot.botApi.handlers;

import fedorov.home.MyAnimeBot.botApi.util.JsonDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

@Component
public class TitleHandler {

    @Autowired
    private JsonDownloader jsonDownloader;

    private final static String API_FIND_BY_TITLE = "https://api.jikan.moe/v3/search/anime?q=";
    private final static String API_LIMIT = "&limit=1";
    private final static String API_RANDOM = "https://api.jikan.moe/v3/search/anime?genre=";
    private final static String API_PAGE = "&page=";

    public String getJsonResponseForTitle(String attribute) throws IOException {
        if (attribute.split(" ").length > 1) {
            attribute = attribute.replace(" ", "%20");
        }
        String urlString = API_FIND_BY_TITLE + attribute + API_LIMIT;
        return getResponse(urlString);
    }

    public String getJsonResponseForRandom() throws IOException {
        String urlString = API_RANDOM + getRandomNumber() + API_PAGE + getRandomPage();
        return getResponse(urlString);
    }

    private String getResponse(String urlString) throws MalformedURLException {
        URL urlObject = new URL(urlString);
        StringBuilder response = new StringBuilder();
        try {
            response = jsonDownloader.getRawJsonResponse(urlObject);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    private int getRandomNumber() {
        int lowerBound = 1;
        int higherBound = 43;

        Random random = new Random();
        return random.nextInt(higherBound - lowerBound) + lowerBound;
    }

    private int getRandomPage() {
        int lowerBound = 1;
        int higherBound = 20;

        Random random = new Random();
        return random.nextInt(higherBound - lowerBound) + lowerBound;
    }
}
