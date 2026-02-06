package com.project.e_ganun.repository;

import com.project.e_ganun.model.BotUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotUserRepo extends CrudRepository<BotUser, Long> {

    Optional<BotUser> findByTelegramId(Long telegramId);

    boolean existsByTelegramId(Long telegramId);
}
