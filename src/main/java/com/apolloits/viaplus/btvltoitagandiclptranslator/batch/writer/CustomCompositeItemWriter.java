package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.writer;

import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CompositeTagDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CustomerLicensePlateFile.LicensePlateFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileDetail;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomCompositeItemWriter implements ItemWriter<CompositeTagDetail> {
    private ItagItemWriter itagItemWriter;
    private IclpWriter iclpWriter;

    public CustomCompositeItemWriter(ItagItemWriter itagItemWriter,IclpWriter iclpWriter) {
        this.itagItemWriter = itagItemWriter;
        this.iclpWriter=iclpWriter;
    }


    @Override
    public void write(Chunk<? extends CompositeTagDetail> chunk) throws Exception {
        for (CompositeTagDetail item : chunk) {
            List<ITagStatusFileDetail> itagList = item.getITagStatusFileDetail();
            List<LicensePlateFileDetail> iclpList = item.getLicensePlateFileDetail();
            Chunk<LicensePlateFileDetail> iclpFileDetailChunk = new Chunk<>();
            Chunk<ITagStatusFileDetail> iTagStatusFileDetailChunk = new Chunk<>();
            iTagStatusFileDetailChunk.addAll(itagList);
            itagItemWriter.open(new ExecutionContext());
            itagItemWriter.write(iTagStatusFileDetailChunk);
            iclpFileDetailChunk.addAll(iclpList);
            iclpWriter.open(new ExecutionContext());
            iclpWriter.write(iclpFileDetailChunk);

        }
    }
}
