package com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile;

import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@XmlRootElement(name = "TVLPlateDetails")
@XmlType(propOrder = {"plateCountry", "plateState", "plateNumber", "plateEffectiveFrom", "plateEffectiveTo", "plateType"})
public class TVLPlateDetails {

    @XmlElement(name = "PlateCountry")
    private String plateCountry;
    @XmlElement(name = "PlateState")
    private String plateState;
    @XmlElement(name = "PlateNumber")
    private String plateNumber;
    @XmlElement(name = "PlateEffectiveFrom")
    private String plateEffectiveFrom;
    @XmlElement(name = "PlateEffectiveTo")
    private String plateEffectiveTo;
    @XmlElement(name = "PlateType")
    private String plateType;
}
