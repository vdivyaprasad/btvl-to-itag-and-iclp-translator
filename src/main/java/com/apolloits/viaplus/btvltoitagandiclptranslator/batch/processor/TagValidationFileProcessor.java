package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.processor;

import com.apolloits.viaplus.btvltoitagandiclptranslator.batch.validator.TvlFileValidator;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TVLHeader;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TVLTagDetails;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CompositeTagDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CustomerLicensePlateFile.LicensePlateFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CustomerLicensePlateFile.LicensePlateFileHeader;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileHeader;
import com.apolloits.viaplus.btvltoitagandiclptranslator.utility.IAGUtility;
import com.apolloits.viaplus.btvltoitagandiclptranslator.utility.ITagUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class TagValidationFileProcessor implements ItemProcessor<TVLTagDetails,CompositeTagDetail> , StepExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(TagValidationFileProcessor.class);
    @Autowired
    private TvlFileValidator tvlFileValidator;
    @Autowired
    private IAGUtility iagUtility;
    @Autowired
    private ITagUtility iTagUtility;
    private TVLHeader tvlHeader;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution){
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();

        this.tvlHeader = (TVLHeader) executionContext.get("tvlHeader");
    }
    @Override
    public CompositeTagDetail process(TVLTagDetails tvlDetailList) {

        CompositeTagDetail compositeTagDetail = new CompositeTagDetail();
        List<ITagStatusFileDetail> iTagStatusFileDetailList = getiTagStatusFileDetails(tvlDetailList);
        List<LicensePlateFileDetail> licensePlateFileDetailList = getLicensePlateFileDetails(tvlDetailList);

        LicensePlateFileHeader licensePlateFileHeaders=setLicensePlateFileHeader(tvlHeader);
        ITagStatusFileHeader iTagStatusFileHeader=getiTagFileHeader(tvlHeader);

        compositeTagDetail.setLicensePlateFileHeader(licensePlateFileHeaders);
        compositeTagDetail.setITagStatusFileHeader(iTagStatusFileHeader);
        compositeTagDetail.setITagStatusFileDetail(iTagStatusFileDetailList);
        compositeTagDetail.setLicensePlateFileDetail(licensePlateFileDetailList);

        return compositeTagDetail;
    }

    private ITagStatusFileHeader getiTagFileHeader(TVLHeader tvlHeader) {
        ITagStatusFileHeader iTagStatusFileHeader = new ITagStatusFileHeader();
        iTagStatusFileHeader.setFileType(IAGUtility.ITAG_FILE_TYPE);
        iTagStatusFileHeader.setVersion(IAGUtility.IAG_VERSION_NO);
        iTagStatusFileHeader.setFromAgencyId(tvlHeader.getHomeAgencyIdNumber());
        iTagStatusFileHeader.setFileDateTime(tvlHeader.getSubmittedDateTime());
        iTagStatusFileHeader.setRecordCount(tvlHeader.getTotalRecordCount());
        return iTagStatusFileHeader;
    }

    private LicensePlateFileHeader setLicensePlateFileHeader(TVLHeader tvlHeader) {
        LicensePlateFileHeader licensePlateFileHeader = new LicensePlateFileHeader();
        licensePlateFileHeader.setFileType(IAGUtility.ICLP_FILE_TYPE);
        licensePlateFileHeader.setVersion(IAGUtility.IAG_VERSION_NO);
        licensePlateFileHeader.setFromAgencyId(tvlHeader.getHomeAgencyIdNumber());
        licensePlateFileHeader.setFileDateTime(tvlHeader.getSubmittedDateTime());
        licensePlateFileHeader.setRecordCount(tvlHeader.getTotalRecordCount());
        return licensePlateFileHeader;
    }

    private static List<LicensePlateFileDetail> getLicensePlateFileDetails(TVLTagDetails tvlDetailList) {
        List<LicensePlateFileDetail> licensePlateFileDetailList = new ArrayList<>();
        LicensePlateFileDetail licensePlateFileDetail = new LicensePlateFileDetail();
        tvlDetailList.getTvlPlateDetails().forEach(licensePlate->{
            licensePlateFileDetail.setLicensePlateNumber(licensePlate.getPlateNumber());
            licensePlateFileDetail.setLicensePlateState(licensePlate.getPlateState());
            licensePlateFileDetail.setLicensePlateType(licensePlate.getPlateType());
            licensePlateFileDetail.setLicensePlateTagAgencyId(tvlDetailList.getTagAgencyId());
            licensePlateFileDetail.setLicensePlateTagSerialNumber(tvlDetailList.getTagSerialNumber());
            licensePlateFileDetailList.add(licensePlateFileDetail);
        });
        /*licensePlateFileDetail.setLicensePlateNumber(tvlDetailList.getTvlPlateDetails().getPlateNumber());
        licensePlateFileDetail.setLicensePlateState(tvlDetailList.getTvlPlateDetails().getPlateState());
        licensePlateFileDetail.setLicensePlateType(tvlDetailList.getTvlPlateDetails().getPlateType());
        licensePlateFileDetail.setLicensePlateTagAgencyId(tvlDetailList.getTagAgencyId());
        licensePlateFileDetail.setLicensePlateTagSerialNumber(tvlDetailList.getTagSerialNumber());
        licensePlateFileDetailList.add(licensePlateFileDetail);*/
        return licensePlateFileDetailList;
    }

    private List<ITagStatusFileDetail> getiTagStatusFileDetails(TVLTagDetails tvlDetailList) {
        List<ITagStatusFileDetail> iTagStatusFileDetailList=new ArrayList<>();
        ITagStatusFileDetail iTagStatusFileDetail = new ITagStatusFileDetail();
        iTagStatusFileDetail.setTagAgencyId(tvlDetailList.getTagAgencyId());
        iTagStatusFileDetail.setTagSerialNumber(tvlDetailList.getTagSerialNumber());
        iTagStatusFileDetail.setTagStatus(iTagUtility.getTagStatus(tvlDetailList.getTagStatus()));
        iTagStatusFileDetail.setTagAccountInfo("0");
        iTagStatusFileDetail.setTagHomeAgencyId(tvlHeader.getHomeAgencyIdNumber());
        iTagStatusFileDetail.setTagAccountTypeIndicator(iTagUtility.getAccountTypeIndicator(tvlDetailList.getTvlAccountDetails().getFleetIndicator()));
        iTagStatusFileDetail.setTagMount("*");
        iTagStatusFileDetail.setTagAccountNumber(tvlDetailList.getTvlAccountDetails().getAccountNumber());
        iTagStatusFileDetail.setTagProtocolType("***");
        iTagStatusFileDetail.setTagType("*");
        iTagStatusFileDetail.setTagClass(tvlDetailList.getTagClass()!=null?tvlDetailList.getTagClass():"2");
        iTagStatusFileDetailList.add(iTagStatusFileDetail);
        return iTagStatusFileDetailList;
    }
    @AfterStep
    public ExitStatus afterStep(StepExecution stepExecution){
        return null;

    }
}
