/*
package com.apolloits.viaplus.btvltoitagandiclptranslator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity(name = "TvlHome")
@IdClass(EntityCompositeDetails.class)
public class TvlHomeEntity {

    //fields fileId, tagAgencyID and tagSerialNumber are the composite primary key.
    // Have to decide whether to use @Id or @EmbeddedId

    @Getter
    @Setter
    @Id
    private Integer fileId;
    @Id
    private String tagAgencyId;
    @Id
    private String tagSerialNumber;
    private String homeAgencyId;
    private String tagStatus;
    private String tagType;
    private Integer tagClass;
    private String accountNumber;
    private String fleetIndicator;

    public TvlHomeEntity() {
    }
}
*/
