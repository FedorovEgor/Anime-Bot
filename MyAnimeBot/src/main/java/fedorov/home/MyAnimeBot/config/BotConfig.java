package fedorov.home.MyAnimeBot.config;

import fedorov.home.MyAnimeBot.MyAnimeTelegramBot;
import fedorov.home.MyAnimeBot.botApi.TelegramFacade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {

    private String botUserName;
    private String botToken;
    private String webHookPath;

    @Bean
    public MyAnimeTelegramBot myAnimeTelegramBot(TelegramFacade telegramFacade) {
        DefaultBotOptions options = ApiContext
                .getInstance(DefaultBotOptions.class);

        MyAnimeTelegramBot myAnimeTelegramBot = new MyAnimeTelegramBot(options, telegramFacade);
        myAnimeTelegramBot.setBotUserName(botUserName);
        myAnimeTelegramBot.setBotToken(botToken);
        myAnimeTelegramBot.setWebHookPath(webHookPath);

        return myAnimeTelegramBot;
    }

}
