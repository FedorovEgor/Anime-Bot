package fedorov.home.MyAnimeBot.botApi.util;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class ButtonHandler {

    public InlineKeyboardMarkup getKeyboardMarkup(String url) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Open Browser").setUrl(url));
        rowsInline.add(rowInline);

        markup.setKeyboard(rowsInline);
        return markup;
    }
}
