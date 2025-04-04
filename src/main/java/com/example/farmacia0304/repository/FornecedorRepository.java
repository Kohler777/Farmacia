package com.example.farmacia0304.repository;

import com.example.farmacia0304.model.Fornecedor;
import com.example.farmacia0304.Util.CSVUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FornecedorRepository {
    private final List<Fornecedor> fornecedores = new ArrayList<>();
    private final String arquivoCSV = "fornecedores.csv";

    public FornecedorRepository() {
        carregar();
    }

    public void adicionar(Fornecedor f) {
        if (!fornecedores.contains(f)) {
            fornecedores.add(f);
            salvar();
        }
    }

    public List<Fornecedor> listarTodos() {
        return new ArrayList<>(fornecedores);
    }

    private void salvar() {
        List<String> linhas = fornecedores.stream()
                .map(f -> String.join(";",
                        f.getCnpj(),
                        f.getRazaoSocial(),
                        f.getTelefone(),
                        f.getEmail(),
                        f.getCidade(),
                        f.getEstado()
                )).toList();
        try {
            CSVUtil.escreverCSV(arquivoCSV, linhas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregar() {
        try {
            List<String[]> dados = CSVUtil.lerCSV(arquivoCSV);
            for (String[] linha : dados) {
                if (linha.length >= 6) {
                    Fornecedor f = new Fornecedor(
                            linha[0], // CNPJ
                            linha[1], // Razão Social
                            linha[2], // Telefone
                            linha[3], // Email
                            linha[4], // Cidade
                            linha[5]  // Estado
                    );
                    fornecedores.add(f);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar fornecedores: " + e.getMessage());
        }
    }

    public void recarregar() {
        fornecedores.clear();
        carregar();
    }



    public Fornecedor buscarPorCnpj(String cnpj) {
        return fornecedores.stream()
                .filter(f -> f.getCnpj().equalsIgnoreCase(cnpj))
                .findFirst()
                .orElse(null);
    }

}
