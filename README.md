# 💊 Cadastro de Medicamentos - JavaFX

Este projeto é um sistema de gerenciamento de medicamentos desenvolvido com JavaFX. Ele permite o **cadastro**, **armazenamento em arquivo `.csv`** e **visualização de estoque** com uma interface gráfica amigável.

## 🛠 Tecnologias Utilizadas

- Java 17 (Amazon Corretto)
- JavaFX 17.0.6
- Maven
- IntelliJ IDEA
- CSV (para persistência de dados)
- JavaFX FXML (para interface gráfica)

---

## 🚀 Como Executar

### 1. **Pré-requisitos**

- JDK 17 (Amazon Corretto recomendado)
- JavaFX SDK 17.0.6 (já está configurado via Maven)
- IntelliJ IDEA (ou outro IDE com suporte a JavaFX)

---

### 2. **Clonar o repositório**

```bash
git clone https://github.com/kohler777/Farmacia.git
cd Farmacia
```

---

### 3. **Executar o projeto no IntelliJ IDEA**

1. **Abrir o projeto** no IntelliJ como projeto Maven.
2. **Conferir o JDK:** Vá em `File > Project Structure > Project` e selecione `17 (corretto)` como versão do SDK.
3. **Adicionar as VM Options para JavaFX (se necessário):**

Vá em `Run > Edit Configurations` e adicione no campo *VM options*:

```bash
--module-path "caminho/para/javafx-sdk-17.0.6/lib" --add-modules javafx.controls,javafx.fxml
```

> ⚠️ *Se você estiver usando Maven com JavaFX já empacotado, essa configuração extra pode não ser necessária.*

---

## 📂 Estrutura do Projeto

```bash
src/
└── main/
    ├── java/
    │   └── com/example/farmaciamedicamentos/
    │       ├── controller/        # Controladores JavaFX
    │       ├── model/             # Classes de domínio: Medicamento, Fornecedor
    │       └── Main.java          # Classe principal (ponto de entrada)
    └── resources/
        └── com/example/farmaciamedicamentos/view/
            └── medicamento.fxml   # Interface da aplicação
```

---

## 📌 Funcionalidades

- [x] Cadastro de Medicamentos
- [x] Associação a um Fornecedor
- [x] Validações (CNPJ,código alfanúmerico, nome não pode ser vazio,data => atual, quantidade não negativa,preço em valor positivo)
- [x] Tabela com medicamentos do estoque
- [x] Persistência em arquivo `medicamento.csv`
- [x] Exibição de mensagens de erro e sucesso

---

## 📎 Observações

- Os medicamentos são salvos no arquivo `medicamento.csv` na raiz do projeto.
- Os fornecedores são carregados diretamente no ComboBox e verificados por CNPJ para evitar duplicação.

---

## 🤝 Contribuição

Pull requests são bem-vindos! Para mudanças significativas, abra uma issue antes para discutir o que você gostaria de modificar.

---

## 🧑‍💻 Autor

Desenvolvido por [Köhler Nunes]  
Email: Kohlernalmeida@gmail.com
