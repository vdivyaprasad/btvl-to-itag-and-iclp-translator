package com.apolloits.viaplus.btvltoitagandiclptranslator.model;

import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CustomerLicensePlateFile.LicensePlateFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CustomerLicensePlateFile.LicensePlateFileHeader;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileHeader;
import lombok.Data;

import java.util.List;

@Data
public class CompositeTagDetail {
    private ITagStatusFileHeader iTagStatusFileHeader;
    private LicensePlateFileHeader licensePlateFileHeader;
    private List<ITagStatusFileDetail> iTagStatusFileDetail;
    private List<LicensePlateFileDetail> licensePlateFileDetail;
}
