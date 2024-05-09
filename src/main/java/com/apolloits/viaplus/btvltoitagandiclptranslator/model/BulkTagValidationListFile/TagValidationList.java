package com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile;

import lombok.Data;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TVLHeader;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@XmlRootElement(name = "TagValidationList")
public class TagValidationList {
    @XmlElement(name = "TVLHeader")
    private TVLHeader tvlHeader;
    @XmlElement(name = "TVLDetail")
    private List<TVLDetailList> tvlDetail;

    @Override
    public String toString() {
        return "Tvl [TVLHeader=" + tvlHeader + ", TVLDetail=" + tvlDetail + "]";
    }
    public TagValidationList(TVLHeader tvlHeader, List<TVLDetailList> tvlDetail) {
        this.tvlHeader = tvlHeader;
        this.tvlDetail = tvlDetail;
    }
    public TagValidationList() {
    }

}
