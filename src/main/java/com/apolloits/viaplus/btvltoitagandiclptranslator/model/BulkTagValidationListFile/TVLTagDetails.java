package com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile;

import lombok.Data;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Getter
@XmlRootElement(name = "TVLTagDetails")
@XmlType(propOrder = {"homeAgencyId", "tagAgencyId", "tagSerialNumber", "tagStatus", "tagClass", "tvlPlateDetails", "tvlAccountDetails"})
public class TVLTagDetails {
    @XmlElement(name = "HomeAgencyID")
    private String homeAgencyId;
    @XmlElement(name = "TagAgencyID")
    private String tagAgencyId;
    @XmlElement(name = "TagSerialNumber")
    private String tagSerialNumber;
    @XmlElement(name = "TagStatus")
    private String tagStatus;
    @XmlElement(name = "TagClass")
    private String tagClass;
    @XmlElement(name = "TVLPlateDetails")
    private TVLPlateDetails tvlPlateDetails;
    @XmlElement(name = "TVLAccountDetails")
    private TVLAccountDetails tvlAccountDetails;

}
