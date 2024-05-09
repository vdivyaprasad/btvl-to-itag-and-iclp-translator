package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.processor;

import com.apolloits.viaplus.btvltoitagandiclptranslator.batch.validator.TvlFileValidator;
import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.FileHeaderDetailEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.XferFileControlEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.mapper.ItagEntityMapper;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TVLHeader;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TVLTagDetails;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TagValidationList;
import com.apolloits.viaplus.btvltoitagandiclptranslator.service.DatabaseLogger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class TvlHeaderProcessor implements ItemProcessor<TVLHeader, FileHeaderDetailEntity> {
    private static final Logger log = LoggerFactory.getLogger(TagValidationFileProcessor.class);
    @Autowired
    private TvlFileValidator tvlFileValidator;
    @Autowired
    private DatabaseLogger databaseLogger;

    @BeforeProcess
    public void beforeProcess( TVLHeader tvlHeader) {

        //dummy
        XferFileControlEntity xferFileControlEntity = new XferFileControlEntity();
        //write detail info to DB table "FileDetail"
        xferFileControlEntity.setStatus("InProgress");
        xferFileControlEntity.setIncomingFileName("SampleTVLFile");
        databaseLogger.insertToFileControlEntity(xferFileControlEntity);

    }

    @Override
    public FileHeaderDetailEntity process(TVLHeader tvlHeader) {
        //code to process the file
        Boolean headerFlag = TvlFileValidator.validateTVLHeader(tvlHeader);
        log.info("TVL Header validation: {}",headerFlag+" for the file submitted on: {}" ,tvlHeader.getSubmittedDateTime());

        FileHeaderDetailEntity fileHeaderDetailEntity = new FileHeaderDetailEntity();
        fileHeaderDetailEntity.setTagAgencyId(tvlHeader.getHomeAgencyIdNumber());
        fileHeaderDetailEntity.setRecordCount(tvlHeader.getTotalRecordCount());
        fileHeaderDetailEntity.setFileDate(tvlHeader.getSubmittedDateTime());
        fileHeaderDetailEntity.setFileTime(tvlHeader.getSubmittedDateTime());
        fileHeaderDetailEntity.setFileType(tvlHeader.getSubmittedFileType());
        //write header info to DB table "FileHeaderDetail"
        return fileHeaderDetailEntity;

    }

}
