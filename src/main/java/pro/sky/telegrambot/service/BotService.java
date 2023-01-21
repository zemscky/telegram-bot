package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class BotService implements UpdatesListener {

    private TelegramBot telegramBot;
    private NotificationService notificationService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    public BotService(TelegramBot telegramBot, NotificationService notificationService) {
        this.telegramBot = telegramBot;
        this.notificationService = notificationService;
        this.telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.stream()
                .filter(update -> update.message() != null)
                .filter(update -> update.message().text() != null)
                .forEach(this::processUpdate);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void processUpdate(Update update) {
        String userMessage = update.message().text();
        Long chatId = update.message().chat().id();
        if (userMessage.equals("/start")) {
            this.telegramBot.execute(new SendMessage(chatId, "Hi, i can remind you about the event"));
        } else {
            if (this.notificationService.processNotification(chatId, userMessage)){
            this.telegramBot.execute(new SendMessage(chatId, "Reminder created"));
            } else {
                this.telegramBot.execute(new SendMessage
                        (chatId, "I receive messages in the format '01.01.2022 20:00 Сделать домашнюю работу'"));
            }
        }
    }
}
