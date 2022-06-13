package com.github.erf88.processor;

import com.github.erf88.model.in.ClienteIn;
import com.github.erf88.model.out.ClienteOut;
import org.springframework.batch.item.ItemProcessor;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClienteProcessor implements ItemProcessor<ClienteIn, ClienteOut> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final LocalDate dataProcessamento = LocalDate.now();

    @Override
    public ClienteOut process(ClienteIn clienteIn) {
        ClienteOut clienteOut = new ClienteOut();
        clienteOut.setId(clienteIn.getId().intValue());
        String nomeCompleto = getNomeCompleto(clienteIn);
        clienteOut.setNomeCompleto(nomeCompleto);
        clienteOut.setEmail(clienteIn.getEmail());
        clienteOut.setDataProcessamento(dataProcessamento.format(formatter));
        return clienteOut;
    }

    private String getNomeCompleto(ClienteIn clienteIn) {
        String nome = removeAcentos(clienteIn.getNome());
        String sobreNome = removeAcentos(clienteIn.getSobrenome());
        return nome + " " + sobreNome;
    }

    public static String removeAcentos(final String str) {
        String strSemAcentos = Normalizer.normalize(str, Normalizer.Form.NFD);
        return strSemAcentos.replaceAll("[^\\p{ASCII}]", "");
    }

}
