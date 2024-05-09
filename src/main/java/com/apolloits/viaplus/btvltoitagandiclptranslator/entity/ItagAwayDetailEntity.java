package com.apolloits.viaplus.btvltoitagandiclptranslator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="ItagAwayDetail")
public class ItagAwayDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fileId;
    private String tagAgencyId;
    private String tagSerialNumber;
    private String tagStatus;
    private String tagAcctInfo;
    private String tagHomeAgency;
    private String tagAcTypeInd;
    private String tagAccountNo;
    private String tagProtocal;
    private String tagType;
    private String tagMount;
    private String tagClass;
}
