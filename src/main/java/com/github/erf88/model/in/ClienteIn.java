package com.github.erf88.model.in;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@ToString
public class ClienteIn implements Serializable {

    @NotNull
    private BigDecimal id;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z\\u00C0-\\u00FF ]+", message = "Nome deve conter apenas letras")
    private String nome;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z\\u00C0-\\u00FF ]+", message = "Sobrenome deve conter apenas letras")
    private String sobrenome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String status;


}
