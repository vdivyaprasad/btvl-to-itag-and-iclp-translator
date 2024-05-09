package com.apolloits.viaplus.btvltoitagandiclptranslator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name="XferFileControl")
public class XferFileControlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fileId;
    private String incomingFileName;
    private Integer fileConfigId;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
