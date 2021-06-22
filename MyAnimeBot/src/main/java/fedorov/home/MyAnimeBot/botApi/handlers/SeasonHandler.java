package fedorov.home.MyAnimeBot.botApi.handlers;

import fedorov.home.MyAnimeBot.botApi.util.ButtonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Formatter;
import java.util.HashSet;
import java.util.Set;

@Component
public class SeasonHandler {

    private String inputMessage;
    private String[] seasonSearch;
    private Set<String> seasons = new HashSet<>();
    
    @Autowired
    private ButtonHandler buttonHandler;

    {
        seasons.add("winter");
        seasons.add("spring");
        seasons.add("summer");
        seasons.add("fall");
    }

    private static final String API_SEASON_REQUEST = "https://myanimelist.net/anime/season/%s/%s";
    private static final String ERROR_MESSAGE = "Sorry, couldn't find anything. " +
            "\nPlease use the following search pattern : " +
            "\n\"/season year seasonName\"" +
            "\n where \"seasonName\" can be: \"winter, spring, summer, fall\" and is not case sensitive.";

    public String getSeasonUrl(String year, String seasonName) {
        Formatter formatter = new Formatter();
        String urlString = formatter.format(API_SEASON_REQUEST, year, seasonName.toLowerCase()).toString();

        return urlString;
    }

    public void parseInputMessage(String inputMessage) {
        this.inputMessage = inputMessage;
        seasonSearch = inputMessage.split(" ");
    }

    public SendMessage checkAndGetReplyMessage(long userId, String year, String seasonName) {
        String seasonUrl;
        InlineKeyboardMarkup keyboardMarkup;
        SendMessage messageToReturn;

        if (!checkYear(year) || !checkSeason(seasonName)) {
            return new SendMessage(userId, ERROR_MESSAGE);
        }

        seasonUrl = getSeasonUrl(year, seasonName);
        keyboardMarkup = buttonHandler.getKeyboardMarkup(seasonUrl);
        Formatter formatter = new Formatter();
        String replyString = formatter.format("Here's you link to MAL which contains %s's %s season :", year, seasonName.toLowerCase()).toString();

        messageToReturn = new SendMessage(userId, replyString);
        messageToReturn.setReplyMarkup(keyboardMarkup);
        return messageToReturn;
    }


    public String getSeasonYear() {
        return seasonSearch[1];
    }

    public String getSeasonName() {
        return seasonSearch[2];
    }

    private boolean checkYear(String year) {
        try {
            Integer.parseInt(year);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean checkSeason(String seasonName) {
        if (seasons.contains(seasonName.toLowerCase())) {
            return true;
        }
        return false;
    }
}
