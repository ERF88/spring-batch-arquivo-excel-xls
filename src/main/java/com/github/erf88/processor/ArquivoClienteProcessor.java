package com.github.erf88.processor;

import com.github.erf88.model.in.ClienteIn;
import com.github.erf88.model.out.ClienteOut;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ArquivoClienteProcessor {

    @Bean
    public ItemProcessor<ClienteIn, ClienteOut> clienteProcessor() throws Exception {
        BeanValidatingItemProcessor<ClienteIn> beanValidatingProcessor = beanValidatingProcessor();
        ValidatingItemProcessor<ClienteIn> statusValidatingProcessor = statusValidatingProcessor();
        ClienteProcessor arquivoClienteProcessor = new ClienteProcessor();
        return new CompositeItemProcessorBuilder<ClienteIn, ClienteOut>()
                .delegates(Arrays.asList(beanValidatingProcessor, statusValidatingProcessor, arquivoClienteProcessor))
                .build();
    }

    private BeanValidatingItemProcessor<ClienteIn> beanValidatingProcessor() throws Exception {
        BeanValidatingItemProcessor<ClienteIn> processor = new BeanValidatingItemProcessor<>();
        processor.setFilter(true);
        processor.afterPropertiesSet();
        return processor;
    }

    private ValidatingItemProcessor<ClienteIn> statusValidatingProcessor() {
        ValidatingItemProcessor<ClienteIn> processor = new ValidatingItemProcessor<>();
        processor.setValidator(this::validar);
        processor.setFilter(true);
        return processor;
    }

    private void validar(ClienteIn cliente) {

        if(cliente.getStatus().equals("INATIVO")) {
            throw new ValidationException("Cliente esta inativo.");
        }

    }
}
