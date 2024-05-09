package com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile;

import lombok.Data;

@Data
public class ITagStatusFileHeader {
    private String fileType;
    private String fromAgencyId;
    private String fileDate;
    private String fileTime;
    private String recordCount;
    private String countStat1;
    private String countStat2;
    private String countStat3;
    private String countStat4;
}
