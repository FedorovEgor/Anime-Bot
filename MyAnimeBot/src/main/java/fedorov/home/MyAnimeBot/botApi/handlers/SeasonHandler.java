package fedorov.home.MyAnimeBot.botApi.handlers;

import fedorov.home.MyAnimeBot.botApi.util.JsonDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Component
public class SeasonHandler {

    private String inputMessage;
    private String[] seasonSearch;
    private Set<String> seasons = new HashSet<>();

    {
        seasons.add("winter");
        seasons.add("spring");
        seasons.add("summer");
        seasons.add("fall");
    }

    private static final String API_SEASON_REQUEST = "https://myanimelist.net/anime/season/%s/%s";

    public String getSeasonUrl(String year, String seasonName) {
        Formatter formatter = new Formatter();
        String urlString = formatter.format(API_SEASON_REQUEST, year, seasonName.toLowerCase()).toString();

        return urlString;
    }

    public void parseInputMessage(String inputMessage) {
        this.inputMessage = inputMessage;
        seasonSearch = inputMessage.split(" ");
    }

    public String getSeasonYear() {
        return seasonSearch[1];
    }

    public String getSeasonName() {
        return seasonSearch[2];
    }

    public boolean checkYear(String year) {
        year = getSeasonYear();
        try {
            Integer.parseInt(year);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean checkSeason(String seasonName) {
        seasonName = getSeasonName();
        if (seasons.contains(seasonName.toLowerCase())) {
            return true;
        }
        return false;
    }
}
