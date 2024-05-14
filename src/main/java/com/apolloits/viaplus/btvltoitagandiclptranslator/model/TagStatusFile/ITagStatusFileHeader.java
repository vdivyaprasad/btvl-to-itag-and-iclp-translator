package com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile;

import lombok.Data;

@Data
public class ITagStatusFileHeader {
    private String fileType;
    private String version;
    private String fromAgencyId;
    private String fileDateTime;
    private String recordCount;

}
