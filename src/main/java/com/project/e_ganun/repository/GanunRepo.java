package com.project.e_ganun.repository;

import com.project.e_ganun.model.Ganun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GanunRepo extends JpaRepository<Ganun, String> {

    @Query(value = "SELECT * FROM ganun.ganun WHERE ganun_no LIKE :ganunNo || '%'",
            nativeQuery = true)
    List<Ganun> findByGanunNoStartingWith(@Param("ganunNo") String ganunNo);

    @Query(value = "SELECT * FROM ganun.ganun WHERE ganun_no = :ganunNo",
            nativeQuery = true)
    Optional<Ganun> findByExactGanunNo(@Param("ganunNo") String ganunNo);
}
