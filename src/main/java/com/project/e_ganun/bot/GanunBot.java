package com.project.e_ganun.bot;

import com.project.e_ganun.config.BotConfig;
import com.project.e_ganun.model.Ganun;
import com.project.e_ganun.service.GanunService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GanunBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final GanunService ganunService;

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if(messageText.equals("/start")){
                sendWelcomeMessage(chatId);
            }if(messageText.equals("/haqqında") || messageText.equals("/haqqinda") || messageText.equals("/about")){
                sendAboutMessage(chatId);
            }
            else{
                searchGanun(chatId, messageText);
            }
        }

    }

    @Override
    public String getBotUsername() {
        return botConfig.getToken();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    //Helper Methods

    private void sendWelcomeMessage(Long chatId) {
        String welcome = "🇦🇿 E-Ganun botuna xoş gəlmisiniz!\n" +
                "Qanun nömrəsini daxil edin (məs: 241)";
        sendMessage(chatId, welcome);
    }

    private void sendAboutMessage(Long chatId) {
        String about =
                "ℹ️ *E-Ganun Botu*\n\n" +
                        "E-Ganun botu Azərbaycan Respublikası qanunvericiliyinə dair məlumatları rəsmi mənbələr əsasında təqdim etmək məqsədilə hazırlanmış köməkçi botdur.\n\n" +
                        "❗ *Qeyd:*\n" +
                        "Bot rəsmi hüquqi mənbə hesab edilmir. Məlumatlar əsasən rəsmi mənbələrə söykənsə də, mümkün texniki vəya məzmun xətalarına görə bot və onun yaradıcısı heç bir məsuliyyət daşımır.";
        sendMessage(chatId, about);
    }


    private void searchGanun(Long chatId, String ganunNo) {
        List<Ganun> results = ganunService.searchByGanunNo(ganunNo);

        if (results.isEmpty()) {
            sendMessage(chatId, "❌ Qanun tapılmadı");
            return;
        }

        String response = formatResults(results);
        sendMessage(chatId, response);
    }

    private String formatResults(List<Ganun> ganuns) {
        StringBuilder sb = new StringBuilder();
        sb.append("\uD83D\uDCDC Tapılan qanunlar:\n\n");

        for (Ganun ganun : ganuns) {
            sb.append("🔹 Maddə ").append(ganun.getGanunNo()).append("\n\n");
            sb.append(ganun.getGanunText()).append("\n");
            sb.append("─────────────────\n\n");
        }
        return sb.toString();
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        try {
            execute(sendMessage);
        }catch (TelegramApiException e){
            sendMessage(chatId, "❌ Xəta");
            e.printStackTrace();
        }
    }
}
