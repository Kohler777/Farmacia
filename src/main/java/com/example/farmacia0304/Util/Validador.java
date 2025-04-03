package com.example.farmacia0304.Util;

import java.time.LocalDate;

public class Validador {

    public static boolean validarCodigo(String codigo) {
        return codigo != null && codigo.matches("[a-zA-Z0-9]{7}");
    }

    public static boolean validarNome(String nome) {
        return nome != null && nome.trim().length() >= 3;
    }

    public static boolean validarDescricao(String descricao) {
        return descricao != null && !descricao.trim().isEmpty();
    }

    public static boolean validarDataValidade(LocalDate dataValidade) {
        return dataValidade != null && !dataValidade.isBefore(LocalDate.now());
    }

    public static boolean validarQuantidade(int quantidade) {
        return quantidade >= 0;
    }

    public static boolean validarPreco(double preco) {
        return preco > 0;
    }

    public static boolean validarCNPJ(String cnpj) {
        if (cnpj == null) return false;

        // Aceita tanto formatado quanto apenas os dígitos
        return cnpj.matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}") || cnpj.matches("\\d{14}");
    }


    public static boolean validarEmail(String email) {
        return email != null && email.matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$");
    }

    public static boolean validarTelefone(String telefone) {
        return telefone != null && telefone.matches("\\d{10,11}");
    }
}
