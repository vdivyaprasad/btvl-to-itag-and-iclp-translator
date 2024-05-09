package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.writer;

import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.ItagAwayDetailEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.XferFileControlEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.mapper.ItagEntityMapper;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CompositeTagDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CustomerLicensePlateFile.LicensePlateFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.repository.TVLRepository;
import com.apolloits.viaplus.btvltoitagandiclptranslator.service.DatabaseLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TvlItemWriter implements ItemWriter<CompositeTagDetail> {

    @Autowired
   private DatabaseLogger databaseLogger;
    @Autowired
   private ItagEntityMapper itagEntityMapper;
    @Override
    public void write(Chunk<? extends CompositeTagDetail> compositeTagDetails) throws Exception {
        List<ItagAwayDetailEntity> itagStatusEntities = new ArrayList<>();
        compositeTagDetails.forEach(compositeTagDetail -> {
            for (ITagStatusFileDetail detail : compositeTagDetail.getITagStatusFileDetail()) {
                ItagAwayDetailEntity entity = itagEntityMapper.mapToItagEntity(detail);
                itagStatusEntities.add(entity);
                if (itagStatusEntities.size() == 1000) {
                    System.out.println("Writing to database of size" + itagStatusEntities.size());
                    databaseLogger.insertToItagStatusEntity(itagStatusEntities);
                    itagStatusEntities.clear();
                }
                if (!itagStatusEntities.isEmpty()) {
                    databaseLogger.insertToItagStatusEntity(itagStatusEntities);
                }
            }
        });
    }

}
