package com.apolloits.viaplus.btvltoitagandiclptranslator.mapper;

import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.ItagAwayDetailEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TagValidationList;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CustomerLicensePlateFile.LicensePlateFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileDetail;
import com.thoughtworks.xstream.core.ReferenceByIdMarshaller;
import org.glassfish.jaxb.core.v2.model.core.ID;
import org.springframework.stereotype.Component;

@Component
public class ItagEntityMapper {


    public ItagAwayDetailEntity mapToItagEntity(ITagStatusFileDetail iTagStatusFileDetail) {

        ItagAwayDetailEntity itagAwayDetailEntity = new ItagAwayDetailEntity();
        itagAwayDetailEntity.setTagStatus(iTagStatusFileDetail.getTagStatus());
        itagAwayDetailEntity.setTagHomeAgency(iTagStatusFileDetail.getTagHomeAgencyId());
        itagAwayDetailEntity.setTagAgencyId(iTagStatusFileDetail.getTagAgencyId());
        itagAwayDetailEntity.setTagAccountNo(iTagStatusFileDetail.getTagAccountInfo());
        itagAwayDetailEntity.setTagClass(itagAwayDetailEntity.getTagClass());
        itagAwayDetailEntity.setTagAcTypeInd(iTagStatusFileDetail.getTagAccountTypeIndicator());
        itagAwayDetailEntity.setTagMount(iTagStatusFileDetail.getTagMount());
        itagAwayDetailEntity.setTagProtocal(iTagStatusFileDetail.getTagProtocolType());
        itagAwayDetailEntity.setTagSerialNumber(iTagStatusFileDetail.getTagSerialNumber());

        return itagAwayDetailEntity;

    }

    /*public IclpAwayDetailEntity mapToIclpEntity(LicensePlateFileDetail detail) {
        IclpAwayDetailEntity iclpAwayDetailEntity = new IclpAwayDetailEntity();
        iclpAwayDetailEntity.setLicenseAccountNumber(detail.getLicensePlateTagSerialNumber());
        iclpAwayDetailEntity.setLicenseType(detail.getLicensePlateType());
        return iclpAwayDetailEntity;
    }*/
}
