package com.apolloits.viaplus.btvltoitagandiclptranslator.repository;

import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.FileHeaderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TVLHeaderRepository extends JpaRepository<FileHeaderDetailEntity, Integer> {

}
