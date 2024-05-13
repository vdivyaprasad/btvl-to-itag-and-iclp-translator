package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.writer;

import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.batch.item.file.transform.LineAggregator;

public class FixedLengthLineAggregator<T> implements LineAggregator<T> {

    private final FormatterLineAggregator<T> delegate;

    public FixedLengthLineAggregator(Class<T> clazz, String[] fieldNames, String[] fieldFormats) {
        this.delegate = new FormatterLineAggregator<>();
        this.delegate.setFormat(String.join("", fieldFormats)); // Concatenate field formats
        this.delegate.setFieldExtractor(new BeanWrapperFieldExtractor<T>() {{
            setNames(fieldNames);
        }});
    }

    @Override
    public String aggregate(T item) {
        return delegate.aggregate(item);
    }
}
