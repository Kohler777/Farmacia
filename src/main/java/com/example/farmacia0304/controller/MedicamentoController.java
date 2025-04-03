package com.example.farmacia0304.controller;

import com.example.farmacia0304.model.Fornecedor;
import com.example.farmacia0304.model.Medicamento;
import com.example.farmacia0304.repository.MedicamentoRepository;
import com.example.farmacia0304.Util.Validador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MedicamentoController {

    @FXML private TextField tfCodigo;
    @FXML private TextField tfNome;
    @FXML private TextField tfDescricao;
    @FXML private TextField tfPrincipioAtivo;
    @FXML private DatePicker dpValidade;
    @FXML private Spinner<Integer> spQuantidade;
    @FXML private TextField tfPreco;
    @FXML private CheckBox cbControlado;
    @FXML private ComboBox<String> cbFiltro;

    // Fornecedor
    @FXML private TextField tfCnpj;
    @FXML private TextField tfRazaoSocial;
    @FXML private TextField tfTelefone;
    @FXML private TextField tfEmail;
    @FXML private TextField tfCidade;
    @FXML private TextField tfEstado;

    @FXML private TableView<Medicamento> tabelaMedicamentos;
    @FXML private TableColumn<Medicamento, String> colCodigo;
    @FXML private TableColumn<Medicamento, String> colNome;
    @FXML private TableColumn<Medicamento, Integer> colQuantidade;
    @FXML private TableColumn<Medicamento, LocalDate> colValidade;
    @FXML private TextField tfBuscarCodigo;

    private final MedicamentoRepository repository = new MedicamentoRepository();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarTabela();

        spQuantidade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));

        cbFiltro.getItems().addAll(
                "Todos",
                    "Medicamentos Controlados",
                    "Medicamentos não controlados",
                    "Medicamentos Próximos do Vencimento",
                    "Medicamentos com estoque Baixo"
        );
    }

    private void configurarTabela() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidadeEstoque"));
        colValidade.setCellValueFactory(new PropertyValueFactory<>("dataValidade"));
    }

    @FXML
    private void carregarTabela() {
        tabelaMedicamentos.getItems().setAll(repository.listarTodos());
    }

    @FXML
    private void adicionarMedicamento() {
        try {
            // Validação básica
            String codigo = tfCodigo.getText().trim();
            if (!Validador.validarCodigo(codigo)) throw new IllegalArgumentException("Código inválido!");

            String nome = tfNome.getText().trim();
            if (!Validador.validarNome(nome)) throw new IllegalArgumentException("Nome inválido!");

            LocalDate validade = dpValidade.getValue();
            if (!Validador.validarDataValidade(validade)) throw new IllegalArgumentException("Data inválida!");

            int quantidade = spQuantidade.getValue();
            double preco = Double.parseDouble(tfPreco.getText().replace(",", "."));
            if (!Validador.validarPreco(preco)) throw new IllegalArgumentException("Preço inválido!");

            boolean controlado = cbControlado.isSelected();

            String cnpj = tfCnpj.getText().trim();
            if (!Validador.validarCNPJ(cnpj)) {
                throw new IllegalArgumentException("CNPJ inválido! Use o formato 00.000.000/0001-00 ou 00000000000100");
            }

            Fornecedor fornecedor = new Fornecedor(
                    cnpj,
                    tfRazaoSocial.getText().trim(),
                    tfTelefone.getText().trim(),
                    tfEmail.getText().trim(),
                    tfCidade.getText().trim(),
                    tfEstado.getText().trim()
            );


            Medicamento medicamento = new Medicamento(
                    codigo, nome, tfDescricao.getText(), tfPrincipioAtivo.getText(),
                    validade, quantidade, BigDecimal.valueOf(preco),
                    controlado, fornecedor
            );

            repository.adicionar(medicamento);
            limparCampos();
            carregarTabela();

            mostrarMensagem("Medicamento adicionado com sucesso!");

        } catch (Exception e) {
            mostrarMensagem("Erro: " + e.getMessage());
        }
    }

    @FXML
    private void excluirMedicamento() {
        Medicamento selecionado = tabelaMedicamentos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            repository.remover(selecionado.getCodigo());
            carregarTabela();
            mostrarMensagem("Medicamento removido.");
        } else {
            mostrarMensagem("Selecione um medicamento na tabela.");
        }
    }

    private void limparCampos() {
        tfCodigo.clear();
        tfNome.clear();
        tfDescricao.clear();
        tfPrincipioAtivo.clear();
        dpValidade.setValue(null);
        spQuantidade.getValueFactory().setValue(0);
        tfPreco.clear();
        cbControlado.setSelected(false);

        tfCnpj.clear();
        tfRazaoSocial.clear();
        tfTelefone.clear();
        tfEmail.clear();
        tfCidade.clear();
        tfEstado.clear();
    }

    private void mostrarMensagem(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    private void aplicarFiltro() {
        String opcao = cbFiltro.getValue();

        switch (opcao) {
            case "Medicamentos Controlados":
                tabelaMedicamentos.getItems().setAll(
                        repository.agruparPorControlado().getOrDefault(true, List.of())
                );
                break;
            case "Medicamentos não controlados":
                tabelaMedicamentos.getItems().setAll(
                        repository.agruparPorControlado().getOrDefault(false, List.of())
                );
                break;
            case "Medicamentos Próximos do Vencimento":
                tabelaMedicamentos.getItems().setAll(
                        repository.medicamentosProximosVencimento()
                );
                break;
            case "Medicamentos com estoque Baixo":
                tabelaMedicamentos.getItems().setAll(
                        repository.medicamentosEstoqueBaixo()
                );
                break;
            default:
                carregarTabela(); // "Todos"
                break;
        }
    }

    public void buscarPorCodigo(ActionEvent actionEvent) {
        String codigo = tfBuscarCodigo.getText().trim();

        if (codigo.isEmpty()) {
            mostrarMensagem("Digite um código para buscar.");
            return;
        }

        Medicamento medicamento = repository.buscarPorCodigo(codigo);

        if (medicamento != null) {
            tabelaMedicamentos.getItems().setAll(medicamento);
        } else {
            mostrarMensagem("Medicamento não encontrado para o código: " + codigo);
            tabelaMedicamentos.getItems().clear();
        }
    }
}
