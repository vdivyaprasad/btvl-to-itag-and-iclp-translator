package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.writer;

import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileDetail;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemWriter;

import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItagItemWriter extends FlatFileItemWriter<ITagStatusFileDetail> {

    public ItagItemWriter() {
        // Set the output file
        String outputPath1 = "C://Users/ApolloViaplus/Downloads/Output/05102024_ITAG_FILE.ITAG";
        this.setResource(new FileSystemResource(outputPath1));
        this.setLineAggregator(new FixedLengthLineAggregator<>(ITagStatusFileDetail.class,
                new String[]{"tagAgencyId", "tagSerialNumber", "tagStatus", "tagAccountInfo", "tagHomeAgencyId", "tagAccountTypeIndicator", "tagAccountNumber",
                        "tagProtocolType", "tagType", "tagMount", "tagClass"},
                new String[]{"%s", "%s", "%s",
                        "%s", "%s",
                        "%s", "%s",
                        "%s", "%s", "%s", "%s"}));
    /*tagAgencyId=0034, tagSerialNumber=88811410, tagStatus=1,
     tagAccountInfo=TagAccountInfo, tagHomeAgencyId=null,
    tagAccountTypeIndicator=null, tagAccountNumber=3000003927,
    tagProtocolType=T6, tagType=L, tagMount=null, tagClass=2)*/
    }

    @Override
    public void write(Chunk<? extends ITagStatusFileDetail> chunk) throws Exception {
        List<ITagStatusFileDetail> iTagStatusFileDetailList = new ArrayList<>();
        chunk.forEach(item -> {
            // Set default values for null fields
            if (item.getTagHomeAgencyId() == null) {
                item.setTagHomeAgencyId("");
            }
            if (item.getTagAccountTypeIndicator() == null) {
                item.setTagAccountTypeIndicator("");
            }
            if (item.getTagMount() == null) {
                item.setTagMount("");
            }
            iTagStatusFileDetailList.add(item);
        });
        Chunk<ITagStatusFileDetail> iTagStatusFileDetailChunk = new Chunk<>();
        iTagStatusFileDetailChunk.addAll(iTagStatusFileDetailList);
        super.write(iTagStatusFileDetailChunk);
    }

}
