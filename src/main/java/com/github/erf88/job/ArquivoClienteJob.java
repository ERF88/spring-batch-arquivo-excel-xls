package com.github.erf88.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class ArquivoClienteJob {

	@Bean
	public Job clienteJob(
			JobBuilderFactory jobBuilderFactory,
			Step clienteStep) {

		return jobBuilderFactory
				.get("clienteJob")
				.incrementer(new RunIdIncrementer())
				.start(clienteStep)
				.build();
	}

}
