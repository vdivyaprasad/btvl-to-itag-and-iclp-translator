package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.writer;

import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TVLHeader;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileHeader;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;

import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItagItemWriter extends FlatFileItemWriter<ITagStatusFileDetail>{

    public ItagItemWriter() {
        // Set the output file
        String outputPath = "C://Users/ApolloViaplus/Downloads/Output/05102024_ITAG_FILE.ITAG";
        this.setResource(new FileSystemResource(outputPath));
        this.setLineAggregator(new FixedLengthLineAggregator<>(ITagStatusFileDetail.class,
                new String[]{"tagAgencyId", "tagSerialNumber", "tagStatus", "tagAccountInfo", "tagHomeAgencyId", "tagAccountTypeIndicator", "tagAccountNumber",
                        "tagProtocolType", "tagType", "tagMount", "tagClass"},
                new String[]{"%s", "%s", "%s",
                        "%s", "%s",
                        "%s", "%s",
                        "%s", "%s", "%s", "%s"}));

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
