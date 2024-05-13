package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.processor;

import com.apolloits.viaplus.btvltoitagandiclptranslator.batch.validator.TvlFileValidator;
import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.ItagAwayDetailEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.XferFileControlEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.mapper.ItagEntityMapper;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TVLHeader;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TVLTagDetails;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TagValidationList;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CompositeTagDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CustomerLicensePlateFile.LicensePlateFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.service.DatabaseLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class TagValidationFileProcessor implements ItemProcessor<TVLTagDetails,CompositeTagDetail> {

    private static final Logger log = LoggerFactory.getLogger(TagValidationFileProcessor.class);
    @Autowired
    private TvlFileValidator tvlFileValidator;
    @Autowired
    private DatabaseLogger databaseLogger;
    @Autowired
    private ItagEntityMapper itagEntityMapper;
    @Override
    public CompositeTagDetail process(TVLTagDetails tvlDetailList) {

        CompositeTagDetail compositeTagDetail = new CompositeTagDetail();
        List<ITagStatusFileDetail> iTagStatusFileDetailList=new ArrayList<>();
        ITagStatusFileDetail iTagStatusFileDetail = new ITagStatusFileDetail();
        iTagStatusFileDetail.setTagAgencyId(tvlDetailList.getTagAgencyId());
        iTagStatusFileDetail.setTagSerialNumber(tvlDetailList.getTagSerialNumber());
        iTagStatusFileDetail.setTagStatus("1");
        iTagStatusFileDetail.setTagAccountInfo("0");
        iTagStatusFileDetail.setTagHomeAgencyId("0440");
        iTagStatusFileDetail.setTagMount("1");
        iTagStatusFileDetail.setTagAccountTypeIndicator("Y");
        iTagStatusFileDetail.setTagAccountNumber(tvlDetailList.getTvlAccountDetails().getAccountNumber());
        iTagStatusFileDetail.setTagProtocolType("T6");
       // iTagStatusFileDetail.setTagAccountInfo("TagAccountInfo");
        iTagStatusFileDetail.setTagType("L");
        iTagStatusFileDetail.setTagClass(tvlDetailList.getTagClass());
        iTagStatusFileDetailList.add(iTagStatusFileDetail);

        List<LicensePlateFileDetail> licensePlateFileDetailList = new ArrayList<>();
        LicensePlateFileDetail licensePlateFileDetail = new LicensePlateFileDetail();
        licensePlateFileDetail.setLicensePlateNumber(tvlDetailList.getTvlPlateDetails().getPlateNumber());
        licensePlateFileDetail.setLicensePlateState(tvlDetailList.getTvlPlateDetails().getPlateState());
        licensePlateFileDetail.setLicensePlateType(tvlDetailList.getTvlPlateDetails().getPlateType());
        licensePlateFileDetail.setLicensePlateTagAgencyId(tvlDetailList.getTagAgencyId());
        licensePlateFileDetail.setLicensePlateTagSerialNumber(tvlDetailList.getTagSerialNumber());
        licensePlateFileDetailList.add(licensePlateFileDetail);


        compositeTagDetail.setITagStatusFileDetail(iTagStatusFileDetailList);
        compositeTagDetail.setLicensePlateFileDetail(licensePlateFileDetailList);

        System.out.println("TagValidationFileProcessor: Size of ITagDetail"+compositeTagDetail.getITagStatusFileDetail().size());
        System.out.println("TagValidationFileProcessor: Size of ICLP"+compositeTagDetail.getLicensePlateFileDetail().size());
        return compositeTagDetail;
    }
}
