package com.github.erf88.writer;

import com.github.erf88.model.out.ClienteOut;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

import java.io.IOException;

@Configuration
public class ArquivoClienteWriter {

    @Value("${spring.batch.file.clientes.out}")
    private Resource resource;

    @Bean
    public ItemStreamWriter<ClienteOut> clienteWriter() throws IOException {
        WritableResource clienteResource = new FileSystemResource(resource.getFile());
        return new ClienteWriter(clienteResource);
    }

}
