package com.github.erf88.model.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ClienteOut implements Serializable {

    private Integer id;
    private String nomeCompleto;
    private String email;
    private String dataProcessamento;

}
