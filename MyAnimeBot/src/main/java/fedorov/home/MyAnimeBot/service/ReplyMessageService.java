package fedorov.home.MyAnimeBot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ReplyMessageService {


    public SendMessage getReplyMessage(long chat_id, String replyMessage) {
        return new SendMessage(chat_id, replyMessage);
    }
}
