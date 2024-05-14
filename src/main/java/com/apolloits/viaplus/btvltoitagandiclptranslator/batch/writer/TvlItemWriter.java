package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.writer;

import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.ItagAwayDetailEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.mapper.ItagEntityMapper;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CompositeTagDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CustomerLicensePlateFile.LicensePlateFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.service.DatabaseLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TvlItemWriter implements ItemWriter<CompositeTagDetail> {
    private final DatabaseLogger databaseLogger;
    private final ItagEntityMapper itagEntityMapper;

    public TvlItemWriter(DatabaseLogger databaseLogger, ItagEntityMapper itagEntityMapper) {
        this.databaseLogger = databaseLogger;
        this.itagEntityMapper = itagEntityMapper;
    }

    @Override
    public void write(Chunk<? extends CompositeTagDetail> compositeTagDetails) throws Exception {
        List<ItagAwayDetailEntity> itagStatusEntities = new ArrayList<>();
        /*List<IclpAwayDetailEntity> iclpAwayDetailEntities = new ArrayList<>();

        compositeTagDetails.forEach(compositeTagDetail -> {
            for (LicensePlateFileDetail detail : compositeTagDetail.getLicensePlateFileDetail()) {
                IclpAwayDetailEntity iclpEntity = itagEntityMapper.mapToIclpEntity(detail);
                iclpAwayDetailEntities.add(iclpEntity);
                if (iclpAwayDetailEntities.size() == 1000) {
                    System.out.println("Writing to iclpAwayEntity of size" + iclpAwayDetailEntities.size());
                    databaseLogger.insertToIclpAwayEntity(iclpAwayDetailEntities);
                    iclpAwayDetailEntities.clear();
                }
                if (!iclpAwayDetailEntities.isEmpty()) {
                    databaseLogger.insertToIclpAwayEntity(iclpAwayDetailEntities);
                }
            }
        });*/

        compositeTagDetails.forEach(compositeTagDetail -> {
            for (ITagStatusFileDetail detail : compositeTagDetail.getITagStatusFileDetail()) {
                ItagAwayDetailEntity entity = itagEntityMapper.mapToItagEntity(detail);
                itagStatusEntities.add(entity);
                /*if (itagStatusEntities.size() == 500) {
                    System.out.println("Writing to database of size" + itagStatusEntities.size());
                    databaseLogger.insertToItagStatusEntity(itagStatusEntities);
                    itagStatusEntities.clear();
                }
                if (!itagStatusEntities.isEmpty()) {
                    databaseLogger.insertToItagStatusEntity(itagStatusEntities);

                }*/
            }
        });
        databaseLogger.insertToItagStatusEntity(itagStatusEntities);
    }

}
