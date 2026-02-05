package com.project.e_ganun.service;

import com.project.e_ganun.model.Ganun;
import com.project.e_ganun.repository.GanunRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class GanunService {

    private final GanunRepo ganunRepo;

    public List<Ganun> searchByGanunNo(String ganunNo) {
        return ganunRepo.findByGanunNoStartingWith(ganunNo);
    }

    public Optional<Ganun> getExactGanun(String ganunNo) {
        return ganunRepo.findByExactGanunNo(ganunNo);
    }
}
