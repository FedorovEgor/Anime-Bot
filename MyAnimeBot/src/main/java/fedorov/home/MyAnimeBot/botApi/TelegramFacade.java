package fedorov.home.MyAnimeBot.botApi;

import com.google.inject.internal.cglib.core.$ClassEmitter;
import fedorov.home.MyAnimeBot.botApi.handlers.SeasonHandler;
import fedorov.home.MyAnimeBot.botApi.handlers.TitleHandler;
import fedorov.home.MyAnimeBot.botApi.util.ButtonHandler;
import fedorov.home.MyAnimeBot.botApi.util.JsonToAnimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.IOException;
import java.util.Formatter;

@Component
public class TelegramFacade {

    private TitleHandler titleHandler;
    private SeasonHandler seasonHandler;
    private JsonToAnimeConverter jsonToAnimeConverter;
    private ButtonHandler buttonHandler;

    @Autowired
    public TelegramFacade(TitleHandler titleHandler,SeasonHandler seasonHandler,JsonToAnimeConverter jsonToAnimeConverter, ButtonHandler buttonHandler) {
        this.titleHandler = titleHandler;
        this.seasonHandler = seasonHandler;
        this.jsonToAnimeConverter = jsonToAnimeConverter;
        this.buttonHandler = buttonHandler;
    }

    public SendMessage handleUpdate(Update update) throws IOException {
        SendMessage replyMessage = null;

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) throws IOException {
        String inputMessage = message.getText();
        long userId = message.getFrom().getId();
        SendMessage replyMessage = new SendMessage(userId, "Not a valid command, please check the /help menu for the list of suitable commands for this bot.");

        if (inputMessage.startsWith("/find")) {
            String searchAttribute = inputMessage.substring(inputMessage.indexOf(" "));
            replyMessage = new SendMessage(userId, jsonToAnimeConverter.getAnimeObjectFromJson(titleHandler.getJsonResponseAsString(searchAttribute)).toString());
        }
        if (inputMessage.startsWith("/season")) {
            seasonHandler.parseInputMessage(inputMessage);

            String year = seasonHandler.getSeasonYear();
            String seasonName = seasonHandler.getSeasonName();

            replyMessage = checkAndGetInputMessage(userId, year, seasonName);
        }

        return replyMessage;
    }

    private SendMessage checkAndGetInputMessage(long userId, String year, String seasonName) {
        String seasonUrl;
        InlineKeyboardMarkup markup;
        Formatter formatter = new Formatter();
        SendMessage massageToReturn;

        if (!seasonHandler.checkYear(year) || !seasonHandler.checkSeason(seasonName)) {
            return new SendMessage(userId, "Sorry, couldn't find anything. " +
                    "\nPlease use the following search pattern : " +
                    "\n\"/season year seasonName\"" +
                    "\n where \"seasonName\" can be: \"winter, spring, summer, fall\" and is not case sensitive.");
        }

        seasonUrl = seasonHandler.getSeasonUrl(year, seasonName);
        markup = buttonHandler.getKeyboardMarkup(seasonUrl);
        String replyString = formatter.format("Here's you link to MAL which contains %s's %s season :", year, seasonName.toLowerCase()).toString();

        massageToReturn = new SendMessage(userId, replyString);
        massageToReturn.setReplyMarkup(markup);
        return massageToReturn;
    }

}

