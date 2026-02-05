package com.project.e_ganun.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

    @Data
    @Entity
    @Table(name = "ganun", schema = "ganun")
    public class Ganun {
        @Id
        @Column(name = "ganun_no")
        private String ganunNo;

        @Column(name = "ganun_text")
        private String ganunText;
    }
