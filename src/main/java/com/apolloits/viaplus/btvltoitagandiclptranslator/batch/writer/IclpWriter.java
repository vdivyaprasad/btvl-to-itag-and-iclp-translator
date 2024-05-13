package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.writer;

import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CustomerLicensePlateFile.LicensePlateFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileDetail;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IclpWriter extends FlatFileItemWriter<LicensePlateFileDetail> {
    public IclpWriter() {
        // Set the output file
        String outputPath1 = "C://Users/ApolloViaplus/Downloads/Output/05102024_ICLP_FILE.ICLP";
        this.setResource(new FileSystemResource(outputPath1));
        this.setLineAggregator(new FixedLengthLineAggregator<>(LicensePlateFileDetail.class,
                new String[]{"licensePlateState", "licensePlateNumber", "licensePlateType",
                        "licensePlateTagAgencyId", "licensePlateTagSerialNumber"},
                new String[]{"%s", "%s", "%s",
                        "%s", "%s",
                       }));
    /*tagAgencyId=0034, tagSerialNumber=88811410, tagStatus=1,
     tagAccountInfo=TagAccountInfo, tagHomeAgencyId=null,
    tagAccountTypeIndicator=null, tagAccountNumber=3000003927,
    tagProtocolType=T6, tagType=L, tagMount=null, tagClass=2)*/
    }

    @Override
    public void write(Chunk<? extends LicensePlateFileDetail> chunk) throws Exception {
        List<LicensePlateFileDetail> iclpFileDetailList = new ArrayList<>();
        chunk.forEach(item -> {
            // Set default values for null fields
            if (item.getLicensePlateTagAgencyId() == null) {
                item.setLicensePlateTagAgencyId("");
            }
            if (item.getLicensePlateType() == null) {
                item.setLicensePlateType("");
            }
            if (item.getLicensePlateTagSerialNumber() == null) {
                item.setLicensePlateTagSerialNumber("");
            }
            iclpFileDetailList.add(item);
        });
        Chunk<LicensePlateFileDetail> iclpFileDetailChunk = new Chunk<>();
        iclpFileDetailChunk.addAll(iclpFileDetailList);
        super.write(iclpFileDetailChunk);
    }
}
