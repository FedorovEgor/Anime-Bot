package fedorov.home.MyAnimeBot.botApi.handlers;

import fedorov.home.MyAnimeBot.botApi.util.JsonDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class TitleHandler {

    @Autowired
    private JsonDownloader jsonDownloader;

    private final static String API_FIND_BY_TITLE = "https://api.jikan.moe/v3/search/anime?q=";
    private final static String API_PAGE = "&page=1";

    public String getJsonResponseAsString(String attribute) throws IOException {
        if (attribute.split(" ").length > 1) {
            attribute = attribute.replace(" ", "%20");
        }
        String urlString = API_FIND_BY_TITLE + attribute + API_PAGE;
        URL urlObject = new URL(urlString);
        StringBuilder response = new StringBuilder();
        try {
            response = jsonDownloader.getRawJsonResponse(urlObject);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }
}
