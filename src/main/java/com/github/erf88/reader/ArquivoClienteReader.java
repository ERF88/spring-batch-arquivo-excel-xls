package com.github.erf88.reader;

import com.github.erf88.model.in.ClienteIn;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ArquivoClienteReader {

    @Value("${spring.batch.file.clientes.in}")
    private Resource resource;

    @Bean
    public PoiItemReader<ClienteIn> clienteReader() {
        PoiItemReader<ClienteIn> reader = new PoiItemReader<>();
        reader.setName("clienteReader");
        reader.setResource(resource);
        reader.setLinesToSkip(1);
        reader.setRowMapper(rowMapper());
        return reader;
    }

    private RowMapper<ClienteIn> rowMapper() {
        BeanWrapperRowMapper<ClienteIn> rowMapper = new BeanWrapperRowMapper<>();
        rowMapper.setTargetType(ClienteIn.class);
        return rowMapper;
    }

}