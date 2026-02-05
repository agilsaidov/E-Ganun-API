package com.project.e_ganun;

import com.project.e_ganun.bot.GanunBot;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@EnableJpaRepositories("com.project.e_ganun.repository")
public class EGanunApplication {

	public static void main(String[] args) {
		SpringApplication.run(EGanunApplication.class, args);
	}

    @Bean
    public ApplicationRunner initBot(GanunBot bot) {
        return args -> {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
            System.out.println("E-Ganun Bot started!");
        };
    }
}
