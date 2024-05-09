package com.apolloits.viaplus.btvltoitagandiclptranslator;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BtvlToItagAndIclpTranslatorApplication  {

	private JobLauncher jobLauncher;
	private Job runJob;
	public static void main(String[] args) {
		System.out.println("Hello World");

		ApplicationContext context = SpringApplication.run(BtvlToItagAndIclpTranslatorApplication.class, args);

		JobLauncher jobLauncher = context.getBean(JobLauncher.class);
		Job job = context.getBean("runJob", Job.class);

		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("startAt", System.currentTimeMillis())
				.toJobParameters();

		try {
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
