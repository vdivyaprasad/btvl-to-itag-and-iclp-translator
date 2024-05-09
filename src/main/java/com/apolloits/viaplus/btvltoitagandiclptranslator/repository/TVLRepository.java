package com.apolloits.viaplus.btvltoitagandiclptranslator.repository;

import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.XferFileControlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TVLRepository extends JpaRepository<XferFileControlEntity, Integer> {
}
