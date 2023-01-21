package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotificationService {
    private static final Pattern NOTIFICATION_PATTERN =
            Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");

    public boolean processNotification(Long chatId, String message) {
        Matcher messageMatcher = NOTIFICATION_PATTERN.matcher(message);
        if (!messageMatcher.matches()){
            return false;
        }
    }
}
