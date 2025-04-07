package com.example.farmacia0304.repository;

import com.example.farmacia0304.model.Fornecedor;
import com.example.farmacia0304.model.Medicamento;
import com.example.farmacia0304.Util.CSVUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MedicamentoRepository {
    private final List<Medicamento> medicamentos = new ArrayList<>();
    private final String arquivoCSV = "medicamentos.csv";

    private final FornecedorRepository fornecedorRepo = new FornecedorRepository();

    public MedicamentoRepository() {
        carregar();
    }

    public void adicionar(Medicamento medicamento) {
        medicamentos.add(medicamento);
        fornecedorRepo.adicionar(medicamento.getFornecedor());
        salvar();
    }

    public void remover(String codigo) {
        medicamentos.removeIf(m -> m.getCodigo().equalsIgnoreCase(codigo));
        salvar();
    }

    public Medicamento buscarPorCodigo(String codigo) {
        return medicamentos.stream()
                .filter(m -> m.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    public List<Medicamento> listarTodos() {
        return new ArrayList<>(medicamentos);
    }

    public void salvar() {
        List<String> linhas = medicamentos.stream().map(m -> String.join(";",
                m.getCodigo(),
                m.getNome(),
                m.getDescricao(),
                m.getPrincipioAtivo(),
                m.getDataValidade().toString(),
                String.valueOf(m.getQuantidadeEstoque()),
                m.getPreco().toString(),
                String.valueOf(m.isControlado()),
                m.getFornecedor().getCnpj()
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
                if (linha.length < 9) {
                    System.out.println("Linha inválida ignorada: " + Arrays.toString(linha));
                    continue;
                }

                String cnpj = linha[8];
                Fornecedor f = fornecedorRepo.buscarPorCnpj(cnpj);

                if (f == null) {
                    System.out.println("Fornecedor não encontrado para o CNPJ: " + cnpj);
                    continue;
                }

                Medicamento m = new Medicamento(
                        linha[0],
                        linha[1],
                        linha[2],
                        linha[3],
                        LocalDate.parse(linha[4]),
                        Integer.parseInt(linha[5]),
                        new BigDecimal(linha[6]),
                        Boolean.parseBoolean(linha[7]),
                        f
                );

                medicamentos.add(m);
            }
        } catch (IOException e) {
            System.out.println("Arquivo CSV não encontrado ou vazio. Criando novo.");
        }
    }



    public List<Medicamento> medicamentosProximosVencimento() {
        LocalDate limite = LocalDate.now().plusDays(30);
        return medicamentos.stream()
                .filter(m -> !m.getDataValidade().isAfter(limite))
                .collect(Collectors.toList());
    }

    public List<Medicamento> medicamentosEstoqueBaixo() {
        return medicamentos.stream()
                .filter(m -> m.getQuantidadeEstoque() < 5)
                .collect(Collectors.toList());
    }

    public Map<String, BigDecimal> valorTotalPorFornecedor() {
        return medicamentos.stream()
                .collect(Collectors.groupingBy(
                        m -> m.getFornecedor().getRazaoSocial(),
                        Collectors.reducing(BigDecimal.ZERO, Medicamento::getPreco, BigDecimal::add)
                ));
    }

    public Map<Boolean, List<Medicamento>> agruparPorControlado() {
        return medicamentos.stream()
                .collect(Collectors.groupingBy(Medicamento::isControlado));
    }
}
