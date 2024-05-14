package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.processor;

import com.apolloits.viaplus.btvltoitagandiclptranslator.batch.validator.TvlFileValidator;
import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.FileHeaderDetailEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.XferFileControlEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TVLHeader;
import com.apolloits.viaplus.btvltoitagandiclptranslator.service.DatabaseLogger;
import com.apolloits.viaplus.btvltoitagandiclptranslator.utility.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class TvlHeaderProcessor implements ItemProcessor<TVLHeader, FileHeaderDetailEntity>, ItemStream {
    private static final Logger log = LoggerFactory.getLogger(TagValidationFileProcessor.class);
    @Autowired
    private TvlFileValidator tvlFileValidator;
    @Autowired
    private DatabaseLogger databaseLogger;

    private ExecutionContext executionContext;

    @Value("${config.source-file-name}")
    private String incomingFileName;

    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.executionContext = executionContext;
    }

    @BeforeProcess
    public void beforeProcess( TVLHeader tvlHeader) {

        XferFileControlEntity xferFileControlEntity = new XferFileControlEntity();
        //write detail info to DB table "FileDetail"
        xferFileControlEntity.setStatus("S");
        xferFileControlEntity.setFileConfigId(1);
        xferFileControlEntity.setCreatedDate(OffsetDateTime.parse(tvlHeader.getSubmittedDateTime()).toLocalDateTime());
        xferFileControlEntity.setIncomingFileName(incomingFileName);
        //xferFileControlEntity.setLastModifiedDate(OffsetDateTime.parse(DateTimeFormatter.getFormattedNow()).toLocalDateTime());
        databaseLogger.insertToFileControlEntity(xferFileControlEntity);

    }

    @Override
    public FileHeaderDetailEntity process(TVLHeader tvlHeader) {
        //code to process the file
        Boolean headerFlag = tvlFileValidator.validateTVLHeader(tvlHeader);
        log.info("TVL Header validation:{}",headerFlag+"for the file submitted on: {}" ,tvlHeader.getSubmittedDateTime());
        executionContext.put("tvlHeader", tvlHeader);

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
