package fedorov.home.MyAnimeBot.botApi;

import fedorov.home.MyAnimeBot.botApi.handlers.SeasonHandler;
import fedorov.home.MyAnimeBot.botApi.handlers.TitleHandler;
import fedorov.home.MyAnimeBot.botApi.util.ButtonHandler;
import fedorov.home.MyAnimeBot.botApi.util.JsonToAnimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

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
            replyMessage = new SendMessage(userId, jsonToAnimeConverter.getAnimeObjectFromTitleJson(titleHandler.getJsonResponseForTitle(searchAttribute)).toString());
        }

        if (inputMessage.startsWith("/season")) {
            seasonHandler.parseInputMessage(inputMessage);

            String year = seasonHandler.getSeasonYear();
            String seasonName = seasonHandler.getSeasonName();

            replyMessage = seasonHandler.checkAndGetReplyMessage(userId, year, seasonName);
        }

        if (inputMessage.startsWith("/random")) {
            replyMessage = new SendMessage(userId, jsonToAnimeConverter.getAnimeFromRandomJson(titleHandler.getJsonResponseForRandom()).toString());
        }

        return replyMessage;
    }
}

