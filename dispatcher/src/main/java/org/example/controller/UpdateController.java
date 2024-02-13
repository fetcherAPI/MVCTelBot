package org.example.controller;


import lombok.extern.log4j.Log4j;
import org.example.service.UpdateProducer;
import org.example.utils.MessageUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Log4j

public class UpdateController {
    private TelegramBot telegramBot;
    private final MessageUtils messageUtils;
    private final UpdateProducer updateProducer;
    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }
    public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
    }


    public void processUpdate(Update update) {
        if(update == null) {
            log.error("Received update is null");
        }
        if(update.getMessage() != null) {
            distributeMessageByType(update);
        } else {
            log.error("Received unsupported message type " + update);
        }
    }

    private void distributeMessageByType (Update update) {
        var message = update.getMessage();
        if(message.getText() != null) {
            processTextMessage(update);
        } else if(message.getDocument() != null) {
            processDocMessage(update);
        } else if (message.getPhoto() != null) {
            processPhotoMessage(update);
        } else {
            setUnsupportedMessage(update);
        }
    }

    private void setUnsupportedMessage(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update, "Unsupported type message");
        setView(sendMessage);
    }

    private void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }
    private void setFileIsReceivedView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update, "file received successful");
        setView(sendMessage);
    }

    private void processPhotoMessage(Update update) {
        updateProducer.produce("PHOTO_MESSAGE_UPDATE", update);
        setFileIsReceivedView(update);
    }



    private void processDocMessage(Update update) {
        updateProducer.produce("DOC_MESSAGE_UPDATE", update);
        setFileIsReceivedView(update);
    }

    private void processTextMessage(Update update) {
        updateProducer.produce("TEXT_MESSAGE_UPDATE", update);
    }
}
