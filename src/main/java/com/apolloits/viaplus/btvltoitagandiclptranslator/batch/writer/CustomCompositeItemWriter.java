package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.writer;

import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CompositeTagDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CustomerLicensePlateFile.LicensePlateFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CustomerLicensePlateFile.LicensePlateFileHeader;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.TagStatusFile.ITagStatusFileHeader;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomCompositeItemWriter implements ItemWriter<CompositeTagDetail> {
    private TvlItemWriter tvlItemWriter;
    private ItagItemWriter itagItemWriter;
    private IclpWriter iclpWriter;

    public CustomCompositeItemWriter(TvlItemWriter tvlItemWriter,ItagItemWriter itagItemWriter,IclpWriter iclpWriter) {
        this.tvlItemWriter= tvlItemWriter;
        this.itagItemWriter = itagItemWriter;
        this.iclpWriter=iclpWriter;
    }


    @Override
    public void write(Chunk<? extends CompositeTagDetail> chunk) throws Exception {
        for (CompositeTagDetail item : chunk) {

            //write to DB

            LicensePlateFileHeader licensePlateFileHeader = item.getLicensePlateFileHeader();
            ITagStatusFileHeader iTagStatusFileHeader = item.getITagStatusFileHeader();
            List<ITagStatusFileDetail> itagList = item.getITagStatusFileDetail();
            List<LicensePlateFileDetail> iclpList = item.getLicensePlateFileDetail();
            Chunk<LicensePlateFileDetail> iclpFileDetailChunk = new Chunk<>();
            Chunk<ITagStatusFileDetail> iTagStatusFileDetailChunk = new Chunk<>();
            iTagStatusFileDetailChunk.addAll(itagList);
            itagItemWriter.setHeaderCallback(writer ->
                    writer.write("ITAG"+"01.60.02"+item.getITagStatusFileHeader().getFromAgencyId()+item.getITagStatusFileHeader().getFileDateTime()
                            +item.getITagStatusFileHeader().getRecordCount()));
            itagItemWriter.open(new ExecutionContext());

            itagItemWriter.write(iTagStatusFileDetailChunk);
            iclpFileDetailChunk.addAll(iclpList);

            iclpWriter.setHeaderCallback(writer ->
                    writer.write("ICLP"+"01.60.02"+item.getLicensePlateFileHeader().getFromAgencyId()+item.getLicensePlateFileHeader().getFileDateTime()
                            +item.getLicensePlateFileHeader().getRecordCount()));
            iclpWriter.open(new ExecutionContext());
            iclpWriter.write(iclpFileDetailChunk);
           // tvlItemWriter.write(chunk);


        }
    }
}
