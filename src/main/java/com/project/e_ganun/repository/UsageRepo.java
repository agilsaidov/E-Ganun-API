package com.project.e_ganun.repository;

import com.project.e_ganun.model.Usage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsageRepo extends CrudRepository<Usage, Long> {

    Optional<Usage> findByTelegramId(Long telegramId);

    @Query(value = "SELECT SUM(total_searches) FROM ganun.usage", nativeQuery = true)
    Long getTotalSearchesAllUsers();

    @Query(value = "SELECT COUNT(*) FROM ganun.usage " +
            "WHERE last_search_date > NOW() - INTERVAL '1 day'",
            nativeQuery = true)
    Long getActiveUsersToday();
}
