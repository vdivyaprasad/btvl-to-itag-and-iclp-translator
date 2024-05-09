package com.apolloits.viaplus.btvltoitagandiclptranslator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="FileHeaderDetail")
public class FileHeaderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer fileId;
    private String fileType;
    private String version;
    private String fromAgencyId;
    private String fileDate;
    private String fileTime;
    private String recordCount;
    private String tagAgencyId;
    private String ictxFileNum;
    private String itxcFileNum;
    private String countStat1;
    private String countStat2;
    private String countStat3;
    private String countStat4;
    private String niopHubId;
    private String bulkIndicator;
    private Integer bulkIdentifier;
    private String accountNumber;
    private String fleetIndicator;
    private Integer transactionDataSequenceNumber;
    private Integer correctionDataSequenceNumber;

}

