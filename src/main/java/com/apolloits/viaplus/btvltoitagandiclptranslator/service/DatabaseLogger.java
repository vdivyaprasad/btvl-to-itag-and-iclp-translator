package com.apolloits.viaplus.btvltoitagandiclptranslator.service;

//import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.IclpAwayDetailEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.ItagAwayDetailEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.XferFileControlEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.repository.ItagRepository;
import com.apolloits.viaplus.btvltoitagandiclptranslator.repository.TVLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseLogger {

    private final TVLRepository tvlRepository;
    private final ItagRepository itagRepository;
    //private final IclpRepository iclpRepository;

    @Autowired
    public DatabaseLogger(TVLRepository tvlRepository, ItagRepository itagRepository) {
        this.tvlRepository = tvlRepository;
        this.itagRepository = itagRepository;
        //this.iclpRepository = iclpRepository;
    }
    public void insertToFileControlEntity(XferFileControlEntity entity){
        // Insert to file control entity
        tvlRepository.save(entity);

    }

    public void insertToItagStatusEntity(List<ItagAwayDetailEntity> itagAwayDetailEntity) {
        System.out.println("DatabaseLogger: Size of itagAwayDetailEntity"+itagAwayDetailEntity.size());
        itagRepository.saveAll(itagAwayDetailEntity);
    }

    /*public void insertToIclpAwayEntity(List<IclpAwayDetailEntity> iclpAwayDetailEntities) {
        System.out.println("DatabaseLogger: Size of iclpAwayDetailEntities"+iclpAwayDetailEntities.size());
        iclpRepository.saveAll(iclpAwayDetailEntities);
    }*/
}
