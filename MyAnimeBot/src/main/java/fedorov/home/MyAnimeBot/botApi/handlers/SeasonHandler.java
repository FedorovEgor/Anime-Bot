package fedorov.home.MyAnimeBot.botApi.handlers;

import fedorov.home.MyAnimeBot.botApi.util.JsonDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Formatter;

@Component
public class SeasonHandler {

    @Autowired
    private JsonDownloader jsonDownloader;

    private static final String API_SEASON_REQUEST = "https://myanimelist.net/anime/season/%s/%s";

    public String getSeasonUrl(String year, String seasonName) {
        Formatter formatter = new Formatter();
        String urlString = formatter.format(API_SEASON_REQUEST, year, seasonName).toString();

        return urlString;
    }
}
