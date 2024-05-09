package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.writer;

import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.FileHeaderDetailEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.repository.TVLHeaderRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

public class TvlHeaderWriter implements ItemWriter<FileHeaderDetailEntity> {

    @Autowired
    private TVLHeaderRepository tvlHeaderRepository;

    @Override
    public void write(Chunk<? extends FileHeaderDetailEntity> chunk) throws Exception {
        tvlHeaderRepository.saveAll(chunk);
    }
}
