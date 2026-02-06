package com.project.e_ganun.service;

import com.project.e_ganun.model.BotUser;
import com.project.e_ganun.model.Usage;
import com.project.e_ganun.repository.BotUserRepo;
import com.project.e_ganun.repository.UsageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BotUserService {

    private final BotUserRepo botUserRepo;
    private final UsageRepo usageRepo;

    @Transactional
    public void registerOrUpdateUSer(User telegramUser){
        Long telegramId = telegramUser.getId();

        BotUser user = botUserRepo.findByTelegramId(telegramId)
                .orElse(new BotUser());

        user.setTelegramId(telegramId);
        user.setUsername(telegramUser.getUserName());
        user.setFirstName(telegramUser.getFirstName());
        user.setLastName(telegramUser.getLastName());
        user.setIsBot(telegramUser.getIsBot());
        user.setIsActive(true);

        botUserRepo.save(user);

        if (!usageRepo.findByTelegramId(telegramId).isPresent()) {
            Usage usage = new Usage();
            usage.setTelegramId(telegramId);
            usage.setTotalSearches(0);
            usage.setTotalMessages(0);
            usageRepo.save(usage);
        }
    }

    @Transactional
    public void trackSearch(Long telegramId, String query){
        Usage usage = usageRepo.findByTelegramId(telegramId)
                .orElseThrow(() -> new RuntimeException("Usage record not found"));

        usage.setTotalSearches(usage.getTotalSearches() + 1);
        usage.setLastSearchDate(LocalDateTime.now());
        usage.setLastSearchQuery(query);

        usageRepo.save(usage);
    }

    @Transactional
    public void trackMessage(Long telegramId){
        Usage usage = usageRepo.findByTelegramId(telegramId)
                .orElseThrow(() -> new RuntimeException("Usage record not found"));

        usage.setTotalMessages(usage.getTotalMessages() + 1);
        usageRepo.save(usage);
    }

    public Usage getUserUsage(Long telegramId){
        return usageRepo.findByTelegramId(telegramId)
                .orElse(null);
    }
}
