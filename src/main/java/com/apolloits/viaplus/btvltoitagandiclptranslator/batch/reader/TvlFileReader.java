/*
package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.reader;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TagValidationList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.io.IOException;

@Slf4j
public class TvlFileReader implements ItemReader<TagValidationList> {

    */
/*private final Resource resource;
    private final Unmarshaller unmarshaller;
    private TagValidationList nextTagValidationList;

    public TvlFileReader(Resource resource) throws JAXBException, IOException {
        this.resource = resource;
        log.info("File exists or not{}",resource.getFile().exists());
        JAXBContext jaxbContext = JAXBContext.newInstance(TagValidationList.class);
        this.unmarshaller = jaxbContext.createUnmarshaller();
    }

    @Override
    public TagValidationList read() throws Exception {
        if (nextTagValidationList == null) {
            nextTagValidationList = getNextTagValidationList();
        }
        TagValidationList tagValidationList = nextTagValidationList;
        nextTagValidationList = null;
        return tagValidationList;
    }

    private TagValidationList getNextTagValidationList() throws JAXBException {
        Object obj = null;
        try {
            obj = unmarshaller.unmarshal(resource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (obj instanceof TagValidationList) {
            return (TagValidationList) obj;
        } else {
            return null; // or throw an exception if the object is not of the expected type
        }
    }*//*

   private final StaxEventItemReader<TagValidationList> delegate;

    public TvlFileReader(Resource resource) {
        this.delegate = new StaxEventItemReader<>();
        this.delegate.setResource(resource);
        this.delegate.setFragmentRootElementName("TagValidationList");
        this.delegate.setUnmarshaller(jaxb2Marshaller());
    }

    private Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(TagValidationList.class);
        return marshaller;
    }

    @Override
    public TagValidationList read() throws Exception {
        return delegate.read();
    }
}
*/
