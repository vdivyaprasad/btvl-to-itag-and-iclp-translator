package com.apolloits.viaplus.btvltoitagandiclptranslator.model.CustomerLicensePlateFile;

import lombok.Data;

@Data
public class LicensePlateFileHeader {
    private String fileType;
    private String fromAgencyId;
    private String fileDate;
    private String fileTime;
    private String recordCount;

}
