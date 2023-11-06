package pro.sky.telegrembotshelters.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrembotshelters.service.TelegramBotSheltersKeyboard;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotSheltersUpdatesListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotSheltersUpdatesListener.class);

    private final TelegramBot telegramBot;

    private final TelegramBotSheltersKeyboard keyboard;


    //переменная для хранения выбранного приюта (кошачий/собачий)
    private String selectShelter = null;

    //переменная для хранения имени пользователя
    private String userName;

    public TelegramBotSheltersUpdatesListener(TelegramBot telegramBot, TelegramBotSheltersKeyboard keyboard) {
        this.telegramBot = telegramBot;
        this.keyboard = keyboard;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    //метод обработки команд
    @Override
    public int process(List<Update> updates) {

        updates.forEach(update -> {
            //Обрабатываем свои обновления здесь
            logger.info("Processing update: {}", update);
            Message message = update.message();
            String text;
            Long chatId;
            int messageId;

            if (message != null) {
                text = message.text();
                chatId = message.chat().id();
                messageId = message.messageId();
                userName = update.message().from().firstName();
            } else if (update.callbackQuery() != null) {
                text = update.callbackQuery().data();
                chatId = update.callbackQuery().message().chat().id();
                messageId = update.callbackQuery().message().messageId();
            } else {
                return;
            }
            logger.info(text);

            // clearMessage(chatId, messageId); пока под вопросом нужен он или нет

            if (text.equalsIgnoreCase("/start")) {
                keyboard.keyboardSelectionShelter(chatId);
            }
            if (text.equalsIgnoreCase("/dog")) {
                selectShelter = "/dog";
            } else if (text.equalsIgnoreCase("/cat")) {
                selectShelter = "/cat";
            }
            botAnswerUtils(text, chatId, selectShelter, userName);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    // метод для отправки сообщений в чат
    private void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text);
        telegramBot.execute(sendMessage);
    }

    // метод для обработки команд от клавиатуры
    private void botAnswerUtils(String receivedMessage, long chatId, String shelter, String userName) {
        switch (receivedMessage) {
            case "/cat", "/dog" -> keyboard.menuSelectionShelter(chatId, shelter);
            case "/about shelter" -> keyboard.menuSelectionInformationShelter(chatId, shelter, userName);
            case "/about" -> sendMessage(chatId, "Рассказать о приюте");//заглушка
            case "/take animal" -> keyboard.menuSelectionShelterCatAndDog(chatId, shelter, userName);
            case "/contacts shelter" -> sendMessage(chatId, "Схема проезда, режим работы, адрес");//заглушка
            case "/security contacts" -> sendMessage(chatId, "Контакты охраны для оформления пропуска");//заглушка
            case "/accept contact" -> sendMessage(chatId, "Принять контактные данные для связи");//заглушка
            case "/send report" -> sendMessage(chatId, "Прислать отчет о питомце");//заглушка
            case "/call volunteer" -> sendMessage(chatId, "Позвать волонтера");//заглушка
            case "/dating rules" -> sendMessage(chatId, "Правила знакомства с животным");//заглушка
            case "/documents" -> sendMessage(chatId, "Список документов чтобы взять животное");//заглушка
            case "/recommendations transportation" ->
                    sendMessage(chatId, "Рекомендаций по транспортировке животного");//заглушка
            case "/recommendations home improvement" ->
                    sendMessage(chatId, "Рекомендации по обустройству дома");//заглушка
            case "/recommendations home adult animal" ->
                    sendMessage(chatId, "Рекомендации по обустройству дома взрослого животного");//заглушка
            case "/recommendations home disabilities" ->
                    sendMessage(chatId, "Рекомендации по обустройству дома животного с ограниченными возможностями");//заглушка
            case "/refuse not you up" ->
                    sendMessage(chatId, "Список причин, почему могут отказать и не дать забрать");//заглушка
            case "/communication dog" -> sendMessage(chatId, "Кинолог по первичному общению с собакой");//заглушка
            case "/proven dog" ->
                    sendMessage(chatId, "Рекомендации по проверенным кинологам для дальнейшего обращения к ним");//заглушка

        }
    }

    //метод отчиски чата
    private void clearMessage(Long chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
        telegramBot.execute(deleteMessage);
        logger.info("clear");
    }
}
