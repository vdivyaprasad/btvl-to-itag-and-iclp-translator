package com.apolloits.viaplus.btvltoitagandiclptranslator.batch.config;

import com.apolloits.viaplus.btvltoitagandiclptranslator.batch.processor.TvlHeaderProcessor;
import com.apolloits.viaplus.btvltoitagandiclptranslator.batch.reader.CustomItemReader;
import com.apolloits.viaplus.btvltoitagandiclptranslator.batch.processor.TagValidationFileProcessor;
import com.apolloits.viaplus.btvltoitagandiclptranslator.batch.writer.*;
import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.FileHeaderDetailEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.entity.ItagAwayDetailEntity;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TVLHeader;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TVLTagDetails;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.BulkTagValidationListFile.TagValidationList;
import com.apolloits.viaplus.btvltoitagandiclptranslator.model.CompositeTagDetail;
import com.apolloits.viaplus.btvltoitagandiclptranslator.repository.TVLRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
@Slf4j
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final TVLRepository repository;

    private String sourceDirectoryLocation="C://Users/ApolloViaplus/Downloads/";
   // @Value("${config.source-filename}")
    private  String sourceFilename="9002_9002_20240417170713.BTVL";
    private final String rootElementName = "TVLTagDetails";
    private ItemProcessor<TagValidationList, CompositeTagDetail> processor;
    private ItemWriter<CompositeTagDetail> writer;
    private TaskExecutor executor;

    public StaxEventItemReader<TVLHeader> tvlFileHeaderReader() {
        StaxEventItemReader<TVLHeader> reader = new StaxEventItemReader<TVLHeader>();
        reader.setResource(new FileSystemResource("C://Users/ApolloViaplus/Downloads/Sample_9002_0041_20240314055944.BTVL"));
        reader.setFragmentRootElementName("TVLHeader"); // Root element name
        Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
        unmarshaller.setClassesToBeBound(TVLHeader.class);
        reader.setUnmarshaller(unmarshaller);
        return reader;
    }

    public StaxEventItemReader<TVLTagDetails> tvlFileReader() {
        StaxEventItemReader<TVLTagDetails> reader = new StaxEventItemReader<TVLTagDetails>();
        reader.setResource(new FileSystemResource("C://Users/ApolloViaplus/Downloads/Sample_9002_0041_20240314055944.BTVL"));
        reader.setFragmentRootElementName(rootElementName); // Root element name
        Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
        unmarshaller.setClassesToBeBound(TVLTagDetails.class);
        reader.setUnmarshaller(unmarshaller);
        return reader;
    }




   /* public ThreadLocal<StaxEventItemReader<TagValidationList>> tvlFileReader() {
    ThreadLocal<StaxEventItemReader<TagValidationList>> threadLocalReader = new ThreadLocal<StaxEventItemReader<TagValidationList>>() {
        @Override
        protected StaxEventItemReader<TagValidationList> initialValue() {
            StaxEventItemReader<TagValidationList> reader = new StaxEventItemReader<>();
            reader.setResource(new FileSystemResource("C://Users/ApolloViaplus/Downloads/Sample_9002_0041_20240314055944.BTVL"));
            reader.setFragmentRootElementName(rootElementName); // Root element name
            Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
            unmarshaller.setClassesToBeBound(TagValidationList.class);
            reader.setUnmarshaller(unmarshaller);
            return reader;
        }
    };
    return threadLocalReader;
   }*/
    @Bean
    public TagValidationFileProcessor processor() {
        return new TagValidationFileProcessor();
    }

    @Bean
    public TvlItemWriter tvlItemWriter() {
       return new TvlItemWriter();
    }
    @Bean
    public TvlHeaderProcessor tvlHeaderProcessor() {
        return new TvlHeaderProcessor();
    }

    @Bean
    public TvlHeaderWriter tvlHeaderWriter() {
        return new TvlHeaderWriter();
    }

    @Bean("myTaskExecutor")
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(3);
        return asyncTaskExecutor;
    }

    /*@Bean
    public CompositeItemWriter<CompositeTagDetail> compositeItemWriter() throws Exception {
        CompositeItemWriter<CompositeTagDetail> compositeItemWriter = new CompositeItemWriter<>();
        List<ItemWriter<?super CompositeTagDetail>> itemWriterList=new ArrayList<>();
        itemWriterList.add(tvlItemWriter());
        itemWriterList.add(itagItemWriter());
        //itemWriterList.add(iclpItemWriter());
        compositeItemWriter.setDelegates(itemWriterList);
        compositeItemWriter.afterPropertiesSet();
        return compositeItemWriter;
    }*/

    private CustomCompositeItemWriter compositeItemWriter() {
        return new CustomCompositeItemWriter(new ItagItemWriter(),new IclpWriter());

    }
    @Bean
    public FlatFileItemWriter<Integer> skippedItemWriter() {
        return new FlatFileItemWriterBuilder<Integer>()
                .name("skippedItemWriter")
                .resource(new FileSystemResource("C://Users/ApolloViaplus/Downloads/Output/skipped.txt"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();

    }

    public Step btvlFileHeaderIngestionStep(){
        return new StepBuilder("btvl-file-header-ingestion-step", jobRepository)
                .<TVLHeader, FileHeaderDetailEntity>chunk(300, platformTransactionManager)
                .reader(tvlFileHeaderReader())
                .processor(tvlHeaderProcessor())
                .writer(tvlHeaderWriter())
                .build();
    }


    @Bean
    public Step btvlFileIngestionStep() {
        return new StepBuilder("btvl-file-ingestion-step", jobRepository)
                .<TVLTagDetails, CompositeTagDetail>chunk(10000, platformTransactionManager)
                .reader(tvlFileReader())
                .processor(processor())
                .writer(tvlItemWriter())
                .writer(compositeItemWriter())
                .stream(skippedItemWriter())
                .taskExecutor(executor)
                .build();
    }

    @Bean
    public Job runJob() {
        return new JobBuilder("btvlFileIngestionJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(btvlFileHeaderIngestionStep())
                .next(btvlFileIngestionStep())
                .build();
    }

    /*private Step TranslateToITAGandICLPStep() {
        return new StepBuilder("TranslateToITAGandICLPStep", jobRepository)
                .<ItagAwayDetailEntity, ITagStatusFileDetail>chunk(300, platformTransactionManager)
                .reader(itagItemReader())
                .processor(processor2())
                .writer(itagItemWriter())
                .taskExecutor(taskExecutor())
                .build();
    }*/

    private CustomItemReader<ItagAwayDetailEntity> itagItemReader() {
        return new CustomItemReader<ItagAwayDetailEntity>();
    }
}

