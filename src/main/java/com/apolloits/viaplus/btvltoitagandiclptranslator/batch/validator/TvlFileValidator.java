package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.validator;

import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TVLDetailList;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TVLHeader;
import org.springframework.stereotype.Component;

@Component
public class TvlFileValidator {

    public static boolean validateTVLHeader(TVLHeader tvlHeader) {

        return tvlHeader.getSubmittedFileType().equals("STVL") &&
                tvlHeader.getSubmittedDateTime().length() == 20 &&
                tvlHeader.getSubmittedDateTime().matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z") &&
                tvlHeader.getSSIOPHubIdNumber().matches("\\d{4}") &&
                tvlHeader.getHomeAgencyIdNumber().matches("\\d{4}") &&
                (tvlHeader.getBulkInd().equals("B")
                        || tvlHeader.getBulkInd().equals("V")) &&
                tvlHeader.getBulkIdentifierValue().matches("\\d{9}") &&
                tvlHeader.getTotalRecordCount().matches("\\d{10}");

    }

    public boolean isValidTVLDetail(TVLDetailList tvlDetail) {
        /* 1. Do validation on mandatory fields/objects
           2. Do regex validation on fields
         */
        return false;
    }
}

