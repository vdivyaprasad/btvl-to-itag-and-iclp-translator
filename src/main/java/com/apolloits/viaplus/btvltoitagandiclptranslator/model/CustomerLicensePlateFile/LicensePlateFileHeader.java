package com.apolloits.viaplus.btvltoitagandiclptranslator.model.CustomerLicensePlateFile;

import lombok.Data;

@Data
public class LicensePlateFileHeader {
    private String fileType;
    private String version;
    private String fromAgencyId;
    private String fileDateTime;
    private String recordCount;

}
